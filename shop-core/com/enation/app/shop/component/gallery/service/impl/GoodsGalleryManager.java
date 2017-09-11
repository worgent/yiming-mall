 package com.enation.app.shop.component.gallery.service.impl;

 import com.enation.app.base.core.service.ISettingService;
 import com.enation.app.shop.component.gallery.model.GoodsGallery;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.image.IThumbnailCreator;
 import com.enation.framework.image.ThumbnailCreatorFactory;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.FileUtil;
 import com.enation.framework.util.ImageMagickMaskUtil;
 import com.enation.framework.util.StringUtil;
 import java.io.File;
 import java.io.PrintStream;
 import java.util.Date;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;

 @Component
 public class GoodsGalleryManager extends BaseSupport<GoodsGallery> implements com.enation.app.shop.component.gallery.service.IGoodsGalleryManager
 {
   private IGoodsManager goodsManager;
   private ISettingService settingService;

   @Transactional(propagation=Propagation.REQUIRED)
   public void add(GoodsGallery gallery)
   {
     this.baseDaoSupport.insert("goods_gallery", gallery);
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void delete(Integer[] goodsid)
   {
     String id_str = StringUtil.arrayToString(goodsid, ",");
     List<GoodsGallery> result = this.baseDaoSupport.queryForList("select * from goods_gallery where goods_id in (" + id_str + ")", GoodsGallery.class, new Object[0]);

     for (GoodsGallery gallery : result) {
       deletePohto(gallery.getOriginal());
       deletePohto(gallery.getBig());
       deletePohto(gallery.getSmall());
       deletePohto(gallery.getThumbnail());
       deletePohto(gallery.getTiny());
     }

     String sql = "delete from goods_gallery where goods_id in (" + id_str + ")";
     this.baseDaoSupport.execute(sql, new Object[0]);
   }








   @Transactional(propagation=Propagation.REQUIRED)
   public void delete(int goodsid)
   {
     this.baseDaoSupport.execute("delete from goods_gallery where goods_id=?", new Object[] { Integer.valueOf(goodsid) });
   }

   public List<GoodsGallery> list(int goods_id)
   {
     List<GoodsGallery> result = this.baseDaoSupport.queryForList("select * from goods_gallery where goods_id = ?", GoodsGallery.class, new Object[] { Integer.valueOf(goods_id) });
     for (GoodsGallery gallery : result) {
       if (!StringUtil.isEmpty(gallery.getOriginal()))
         gallery.setBig(UploadUtil.replacePath(gallery.getOriginal()));
       if (!StringUtil.isEmpty(gallery.getBig()))
         gallery.setBig(UploadUtil.replacePath(gallery.getBig()));
       if (!StringUtil.isEmpty(gallery.getSmall()))
         gallery.setSmall(UploadUtil.replacePath(gallery.getSmall()));
       if (!StringUtil.isEmpty(gallery.getThumbnail()))
         gallery.setThumbnail(UploadUtil.replacePath(gallery.getThumbnail()));
       if (!StringUtil.isEmpty(gallery.getTiny()))
         gallery.setTiny(UploadUtil.replacePath(gallery.getTiny()));
     }
     return result;
   }





   private void deletePohto(String photoName)
   {
     if (photoName != null) {
       photoName = UploadUtil.replacePath(photoName);
       photoName = photoName.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_PATH);
       photoName = photoName.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);

       if (photoName.startsWith("http")) {
         return;
       }
       FileUtil.delete(photoName);
     }
   }



   public void delete(String photoName)
   {
     this.baseDaoSupport.execute("delete from goods_gallery where original=?", new Object[] { photoName.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.FILE_STORE_PREFIX) });
     deletePohto(photoName);
   }








   public String upload(File file, String fileFileName)
   {
     String fileName = null;
     String filePath = "";

     String path = null;

     if ((file != null) && (fileFileName != null)) {
       String ext = FileUtil.getFileExt(fileFileName);
       fileName = DateUtil.toString(new Date(), "yyyyMMddHHmmss") + StringUtil.getRandStr(4) + "." + ext;
       filePath = EopSetting.IMG_SERVER_PATH + getContextFolder() + "/attachment/goods/";
       path = EopSetting.IMG_SERVER_DOMAIN + getContextFolder() + "/attachment/goods/" + fileName;
       filePath = filePath + fileName;
       FileUtil.createFile(file, filePath);

       String watermark = this.settingService.getSetting("photo", "watermark");
       String marktext = this.settingService.getSetting("photo", "marktext");
       String markpos = this.settingService.getSetting("photo", "markpos");
       String markcolor = this.settingService.getSetting("photo", "markcolor");
       String marksize = this.settingService.getSetting("photo", "marksize");

       marktext = StringUtil.isEmpty(marktext) ? "水印文字" : marktext;
       marksize = StringUtil.isEmpty(marksize) ? "12" : marksize;

       if ((watermark != null) && (watermark.equals("on"))) {
         ImageMagickMaskUtil magickMask = new ImageMagickMaskUtil();
         magickMask.mask(filePath, marktext, markcolor, Integer.valueOf(marksize).intValue(), Integer.valueOf(markpos).intValue(), EopSetting.EOP_PATH + "/font/st.TTF");
       }
     }
     return path;
   }

   public static String getContextFolder() {
     return EopContext.getContext().getContextPath();
   }





   public void createThumb(String filepath, String thumbName, int thumbnail_pic_width, int thumbnail_pic_height)
   {
     if (filepath != null)
     {
       String serverPath = EopSetting.IMG_SERVER_PATH.replaceAll("\\\\", "/");
       filepath = filepath.replaceAll(EopSetting.IMG_SERVER_DOMAIN, serverPath);
       filepath = filepath.replaceAll("\\\\", "/");
       thumbName = thumbName.replaceAll(EopSetting.IMG_SERVER_DOMAIN, serverPath);
       thumbName = thumbName.replaceAll("\\\\", "/");


       if (filepath.startsWith("http"))
       {
         return;
       }

       File tempFile = new File(thumbName);
       if (!tempFile.exists())
       {

         IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator(filepath, thumbName);
         thumbnailCreator.resize(thumbnail_pic_width, thumbnail_pic_height);
       }
     }
   }


   public int getTotal()
   {
     return this.goodsManager.list().size();
   }

   public void recreate(int start, int end)
   {
     int tiny_pic_width = 60;
     int tiny_pic_height = 60;
     int thumbnail_pic_width = 107;
     int thumbnail_pic_height = 107;
     int small_pic_width = 320;
     int small_pic_height = 240;
     int big_pic_width = 550;
     int big_pic_height = 412;



     try
     {
       tiny_pic_width = Integer.valueOf(getSettingValue("tiny_pic_width").toString()).intValue();
       tiny_pic_height = Integer.valueOf(getSettingValue("tiny_pic_height").toString()).intValue();
     } catch (Exception e) {
       e.printStackTrace();
     }
     try
     {
       thumbnail_pic_width = Integer.valueOf(getSettingValue("thumbnail_pic_width").toString()).intValue();
       thumbnail_pic_height = Integer.valueOf(getSettingValue("thumbnail_pic_height").toString()).intValue();
     } catch (Exception e) {
       e.printStackTrace();
     }
     try
     {
       small_pic_width = Integer.valueOf(getSettingValue("small_pic_width").toString()).intValue();
       small_pic_height = Integer.valueOf(getSettingValue("small_pic_height").toString()).intValue();
     } catch (Exception e) {
       e.printStackTrace();
     }
     try {
       big_pic_width = Integer.valueOf(getSettingValue("big_pic_width").toString()).intValue();
       big_pic_height = Integer.valueOf(getSettingValue("big_pic_height").toString()).intValue();
     } catch (Exception e) {
       e.printStackTrace();
     }

     List<Map> goodsList = this.goodsManager.list();

     for (int i = start - 1; i < end; i++) {
       Map goods = (Map)goodsList.get(i);

       int goodsid = ((Integer)goods.get("goods_id")).intValue();
       List<GoodsGallery> galleryList = this.baseDaoSupport.queryForList("select * from goods_gallery where goods_id = ?", GoodsGallery.class, new Object[] { Integer.valueOf(goodsid) });

       if (galleryList != null)
       {
         for (GoodsGallery gallery : galleryList) {
           String imgFile = gallery.getOriginal();
           String realPath = UploadUtil.replacePath(imgFile);
           realPath = realPath.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
           System.out.print("Create Image for file:" + realPath + "...");

           if (FileUtil.exist(realPath))
           {
             String thumbName = gallery.getTiny();
             createThumb1(realPath, thumbName, tiny_pic_width, tiny_pic_height);


             thumbName = gallery.getThumbnail();
             createThumb1(realPath, thumbName, thumbnail_pic_width, thumbnail_pic_height);


             thumbName = gallery.getSmall();
             createThumb1(realPath, thumbName, small_pic_width, small_pic_height);


             thumbName = gallery.getBig();
             createThumb1(realPath, thumbName, big_pic_width, big_pic_height);
           }
         }
       }
     }
   }




   private String getSettingValue(String code)
   {
     return this.settingService.getSetting("photo", code);
   }








   private void createThumb1(String filepath, String thumbName, int thumbnail_pic_width, int thumbnail_pic_height)
   {
     if (!StringUtil.isEmpty(filepath)) {
       String ctx = EopContext.getContext().getContextPath();
       filepath = filepath.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_PATH + ctx);
       thumbName = thumbName.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_PATH + ctx);
       IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator(filepath, thumbName);
       thumbnailCreator.resize(thumbnail_pic_width, thumbnail_pic_height);
     }
   }

   public ISettingService getSettingService() {
     return this.settingService;
   }

   public void setSettingService(ISettingService settingService) {
     this.settingService = settingService;
   }

   public IGoodsManager getGoodsManager() {
     return this.goodsManager;
   }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\gallery\service\impl\GoodsGalleryManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */