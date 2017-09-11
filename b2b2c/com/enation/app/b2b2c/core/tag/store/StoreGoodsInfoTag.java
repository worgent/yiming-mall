 package com.enation.app.b2b2c.core.tag.store;

 import com.enation.app.shop.component.gallery.model.GoodsGallery;
 import com.enation.app.shop.component.gallery.service.IGoodsGalleryManager;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;




 @Component
 public class StoreGoodsInfoTag
   extends BaseFreeMarkerTag
 {
   private IGoodsManager goodsManager;
   private IGoodsGalleryManager goodsGalleryManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Map result = new HashMap();
     List<GoodsGallery> galleryList = this.goodsGalleryManager.list(Integer.valueOf(params.get("goods_id").toString()).intValue());

     if ((galleryList != null) && (galleryList.size() > 0)) {
       for (GoodsGallery gallery : galleryList) {
         String image = gallery.getOriginal();
         if (!StringUtil.isEmpty(image)) {
           image = UploadUtil.replacePath(image);
           gallery.setOriginal(image);
         }
       }
     }
     result.put("galleryList", galleryList);
     result.put("goods", this.goodsManager.get(Integer.valueOf(params.get("goods_id").toString())));
     return result;
   }

   public IGoodsManager getGoodsManager() { return this.goodsManager; }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }

   public IGoodsGalleryManager getGoodsGalleryManager() { return this.goodsGalleryManager; }

   public void setGoodsGalleryManager(IGoodsGalleryManager goodsGalleryManager) {
     this.goodsGalleryManager = goodsGalleryManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\store\StoreGoodsInfoTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */