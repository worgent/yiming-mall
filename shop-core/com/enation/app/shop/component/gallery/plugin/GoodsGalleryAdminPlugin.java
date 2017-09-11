 package com.enation.app.shop.component.gallery.plugin;

 import com.enation.app.base.core.service.ISettingService;
 import com.enation.app.shop.component.gallery.model.GoodsGallery;
 import com.enation.app.shop.component.gallery.service.IGoodsGalleryManager;
 import com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin;
 import com.enation.app.shop.core.plugin.goods.IGoodsDeleteEvent;
 import com.enation.app.shop.core.plugin.goods.IGoodsTabShowEvent;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.jms.EopJmsMessage;
 import com.enation.framework.jms.EopProducer;
 import com.enation.framework.util.StringUtil;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;








 @Component
 public class GoodsGalleryAdminPlugin
   extends AbstractGoodsPlugin
   implements IGoodsDeleteEvent, IGoodsTabShowEvent
 {
   private ISettingService settingService;
   private IGoodsGalleryManager goodsGalleryManager;
   private EopProducer eopProducer;

   private String getSettingValue(String code)
   {
     return this.settingService.getSetting("photo", code);
   }








   protected void proessPhoto(String[] picnames, Map goods, String image_default)
   {
     if (picnames == null) {
       return;
     }


     List<GoodsGallery> galleryList = new ArrayList();

     for (int i = 0; i < picnames.length; i++) {
       GoodsGallery gallery = new GoodsGallery();

       String filepath = picnames[i];


       String tiny = getThumbPath(filepath, "_tiny");

       String thumbnail = getThumbPath(filepath, "_thumbnail");

       String small = getThumbPath(filepath, "_small");

       String big = getThumbPath(filepath, "_big");

       gallery.setOriginal(filepath);
       gallery.setBig(big);
       gallery.setSmall(small);
       gallery.setThumbnail(thumbnail);
       gallery.setTiny(tiny);
       galleryList.add(gallery);


       if ((!StringUtil.isEmpty(image_default)) && (image_default.equals(filepath))) {
         gallery.setIsdefault(1);
       }
     }
     Map<String, Object> param = new HashMap(2);
     param.put("galleryList", galleryList);
     param.put("goods", goods);

     EopJmsMessage eopJmsMessage = new EopJmsMessage();
     eopJmsMessage.setData(param);
     eopJmsMessage.setProcessorBeanId("goodsGalleryProcessor");

     this.eopProducer.send(eopJmsMessage);
   }

   private String getThumbPath(String filePath, String shortName) {
     return UploadUtil.getThumbPath(filePath, shortName);
   }


   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {}

   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
   {
     String[] picnames = request.getParameterValues("picnames");
     String image_default = request.getParameter("image_default");
     proessPhoto(picnames, goods, image_default);
   }


   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request) {}

   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
   {
     String[] picnames = request.getParameterValues("picnames");
     String image_default = request.getParameter("image_default");
     proessPhoto(picnames, goods, image_default);
   }

   public String getEditHtml(Map goods, HttpServletRequest request) {
     String contextPath = request.getContextPath();

     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();


     String image_default = (String)goods.get("original");
     if (!StringUtil.isEmpty(image_default)) {
       image_default = UploadUtil.replacePath(image_default);
     }

     List<GoodsGallery> galleryList = this.goodsGalleryManager.list(Integer.valueOf(goods.get("goods_id").toString()).intValue());

     if ((galleryList != null) && (galleryList.size() > 0)) {
       for (GoodsGallery gallery : galleryList) {
         String image = gallery.getOriginal();
         if (!StringUtil.isEmpty(image)) {
           image = UploadUtil.replacePath(image);
           gallery.setOriginal(image);
         }
       }
     }
     freeMarkerPaser.putData("ctx", contextPath);
     freeMarkerPaser.putData("image_default", image_default);
     freeMarkerPaser.putData("thumbnail_images", galleryList);

     freeMarkerPaser.setPageName("album");
     String html = freeMarkerPaser.proessPageContent();
     return html;
   }

   public String getAddHtml(HttpServletRequest request) {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setPageName("album");
     freeMarkerPaser.putData("image_default", null);
     freeMarkerPaser.putData("thumbnail_images", null);
     String html = freeMarkerPaser.proessPageContent();
     return html;
   }





   public Map<String, String> beforeSettingSave(HttpServletRequest request)
   {
     Map<String, String> settingMap = new HashMap();

     String tiny_pic_width = request.getParameter("photo.tiny_pic_width");
     String tiny_pic_height = request.getParameter("photo.tiny_pic_height");
     String thumbnail_pic_width = request.getParameter("photo.thumbnail_pic_width");
     String thumbnail_pic_height = request.getParameter("photo.thumbnail_pic_height");
     String detail_pic_width = request.getParameter("photo.detail_pic_width");
     String detail_pic_height = request.getParameter("photo.detail_pic_height");
     String album_pic_height = request.getParameter("photo.album_pic_height");
     String album_pic_width = request.getParameter("photo.album_pic_width");

     settingMap.put("tiny_pic_width", tiny_pic_width);
     settingMap.put("tiny_pic_height", tiny_pic_height);
     settingMap.put("thumbnail_pic_width", thumbnail_pic_width);
     settingMap.put("thumbnail_pic_height", thumbnail_pic_height);

     settingMap.put("detail_pic_width", detail_pic_width);
     settingMap.put("detail_pic_height", detail_pic_height);

     settingMap.put("album_pic_height", album_pic_height);
     settingMap.put("album_pic_width", album_pic_width);

     return settingMap;
   }






   public void onGoodsDelete(Integer[] goodsid)
   {
     this.goodsGalleryManager.delete(goodsid);
   }

   public String getAuthor() {
     return "lzf";
   }

   public String getType() {
     return "";
   }

   public String getVersion() {
     return "2.0";
   }

   public EopProducer getEopProducer() {
     return this.eopProducer;
   }

   public void setEopProducer(EopProducer eopProducer) {
     this.eopProducer = eopProducer;
   }

   public ISettingService getSettingService() {
     return this.settingService;
   }

   public IGoodsGalleryManager getGoodsGalleryManager() {
     return this.goodsGalleryManager;
   }


   public void perform(Object... params) {}

   public void setSettingService(ISettingService settingService)
   {
     this.settingService = settingService;
   }

   public void setGoodsGalleryManager(IGoodsGalleryManager goodsGalleryManager) {
     this.goodsGalleryManager = goodsGalleryManager;
   }

   public String getTabName()
   {
     return "相册";
   }

   public int getOrder()
   {
     return 3;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\gallery\plugin\GoodsGalleryAdminPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */