 package com.enation.app.b2b2c.core.tag.goods;

 import com.enation.app.shop.component.gallery.model.GoodsGallery;
 import com.enation.app.shop.component.gallery.service.IGoodsGalleryManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;














 @Component
 @Scope("prototype")
 public class StoreGoodsGalleryTag
   extends BaseFreeMarkerTag
 {
   private IGoodsGalleryManager goodsGalleryManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = getRequest();
     Integer goods_id = (Integer)params.get("goodsid");

     List<GoodsGallery> galleryList = this.goodsGalleryManager.list(goods_id.intValue());
     if ((galleryList == null) || (galleryList.size() == 0)) {
       String img = EopSetting.DEFAULT_IMG_URL;
       GoodsGallery gallery = new GoodsGallery();
       gallery.setSmall(img);
       gallery.setBig(img);
       gallery.setThumbnail(img);
       gallery.setTiny(img);
       gallery.setOriginal(img);
       gallery.setIsdefault(1);
       galleryList.add(gallery);
     }
     return galleryList;
   }

   public IGoodsGalleryManager getGoodsGalleryManager() {
     return this.goodsGalleryManager;
   }

   public void setGoodsGalleryManager(IGoodsGalleryManager goodsGalleryManager) {
     this.goodsGalleryManager = goodsGalleryManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\goods\StoreGoodsGalleryTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */