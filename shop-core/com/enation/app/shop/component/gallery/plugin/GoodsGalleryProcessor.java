 package com.enation.app.shop.component.gallery.plugin;

 import com.enation.app.base.core.service.ISettingService;
 import com.enation.app.shop.component.gallery.model.GoodsGallery;
 import com.enation.app.shop.component.gallery.service.IGoodsGalleryManager;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.jms.IJmsProcessor;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import java.util.Map;
 import org.apache.log4j.Logger;
 import org.springframework.stereotype.Component;






 @Component
 public class GoodsGalleryProcessor
   implements IJmsProcessor
 {
   private ISettingService settingService;
   private IGoodsGalleryManager goodsGalleryManager;
   private IDaoSupport baseDaoSupport;

   private String getSettingValue(String code)
   {
     return this.settingService.getSetting("photo", code);
   }

   private void createThumb(String filepath, String targetpath, int pic_width, int pic_height) {
     try {
       this.goodsGalleryManager.createThumb(filepath, targetpath, pic_width, pic_height);
     } catch (Exception e) {
       Logger logger = Logger.getLogger(getClass());
       logger.error(e.getMessage(), e);
     }
   }





   public void process(Object data)
   {
     int tiny_pic_width = 60;
     int tiny_pic_height = 60;
     int thumbnail_pic_width = 180;
     int thumbnail_pic_height = 180;
     int small_pic_width = 460;
     int small_pic_height = 460;
     int big_pic_width = 800;
     int big_pic_height = 800;

     String temp = getSettingValue("tiny_pic_width");
     tiny_pic_width = temp == null ? tiny_pic_width : StringUtil.toInt(temp, true);

     temp = getSettingValue("tiny_pic_height");
     tiny_pic_height = temp == null ? tiny_pic_height : StringUtil.toInt(temp, true);

     temp = getSettingValue("thumbnail_pic_width");
     thumbnail_pic_width = temp == null ? thumbnail_pic_width : StringUtil.toInt(temp, true);

     temp = getSettingValue("thumbnail_pic_height");
     thumbnail_pic_height = temp == null ? thumbnail_pic_height : StringUtil.toInt(temp, true);

     temp = getSettingValue("small_pic_width");
     small_pic_width = temp == null ? small_pic_width : StringUtil.toInt(temp, true);

     temp = getSettingValue("small_pic_height");
     small_pic_height = temp == null ? small_pic_height : StringUtil.toInt(temp, true);

     temp = getSettingValue("big_pic_width");
     big_pic_width = temp == null ? big_pic_width : StringUtil.toInt(temp, true);

     temp = getSettingValue("big_pic_height");
     big_pic_height = temp == null ? big_pic_height : StringUtil.toInt(temp, true);


     Map<String, Object> param = (Map)data;
     List<GoodsGallery> list = (List)param.get("galleryList");
     Map goods = (Map)param.get("goods");
     int goodsid = StringUtil.toInt(goods.get("goods_id").toString(), true);


     List<GoodsGallery> dbList = this.goodsGalleryManager.list(goodsid);

     GoodsGallery defaultGallery = null;

     for (GoodsGallery gallery : list)
     {
       String filepath = gallery.getOriginal();
       gallery.setOriginal(transformPath(filepath));


       if (gallery.getIsdefault() == 1) {
         defaultGallery = gallery;
       }


       if (!checkInDb(gallery.getOriginal(), dbList))
       {



         String targetpath = gallery.getBig();
         createThumb(filepath, targetpath, big_pic_width, big_pic_height);
         targetpath = transformPath(targetpath);
         gallery.setBig(targetpath);


         targetpath = gallery.getSmall();
         createThumb(filepath, targetpath, small_pic_width, small_pic_height);
         targetpath = transformPath(targetpath);
         gallery.setSmall(targetpath);


         targetpath = gallery.getThumbnail();
         createThumb(filepath, targetpath, thumbnail_pic_width, thumbnail_pic_height);
         targetpath = transformPath(targetpath);
         gallery.setThumbnail(targetpath);


         targetpath = gallery.getTiny();
         createThumb(filepath, targetpath, tiny_pic_width, tiny_pic_height);
         targetpath = transformPath(targetpath);
         gallery.setTiny(targetpath);


         gallery.setGoods_id(goodsid);
         this.goodsGalleryManager.add(gallery);
       }
     }

     if (defaultGallery != null) {
       this.baseDaoSupport.execute("update goods set original=?,big=?,small=?,thumbnail=? where goods_id=?", new Object[] { transformPath(defaultGallery.getOriginal()), transformPath(defaultGallery.getBig()), transformPath(defaultGallery.getSmall()), transformPath(defaultGallery.getThumbnail()), Integer.valueOf(goodsid) });






       this.baseDaoSupport.execute("update goods_gallery set isdefault=0 where goods_id=?", new Object[] { Integer.valueOf(goodsid) });
       this.baseDaoSupport.execute("update goods_gallery set isdefault=1 where goods_id=? and original=?", new Object[] { Integer.valueOf(goodsid), transformPath(defaultGallery.getOriginal()) });
     }
   }







   private boolean checkInDb(String path, List<GoodsGallery> dbList)
   {
     for (GoodsGallery gallery : dbList) {
       if (gallery.getOriginal().equals(path)) {
         return true;
       }
     }
     return false;
   }








   private String transformPath(String path)
   {
     String regx = EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath();
     path = path.replace(regx, EopSetting.FILE_STORE_PREFIX);
     return path;
   }

   public ISettingService getSettingService() {
     return this.settingService;
   }

   public void setSettingService(ISettingService settingService) {
     this.settingService = settingService;
   }

   public IGoodsGalleryManager getGoodsGalleryManager() {
     return this.goodsGalleryManager;
   }

   public void setGoodsGalleryManager(IGoodsGalleryManager goodsGalleryManager) {
     this.goodsGalleryManager = goodsGalleryManager;
   }

   public IDaoSupport getBaseDaoSupport() {
     return this.baseDaoSupport;
   }

   public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
     this.baseDaoSupport = baseDaoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\gallery\plugin\GoodsGalleryProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */