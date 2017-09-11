 package com.enation.app.shop.component.gallery;

 import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
 import com.enation.app.shop.component.gallery.service.IGoodsGalleryManager;
 import com.enation.framework.component.IComponent;
 import com.enation.framework.database.IDBRouter;
 import com.enation.framework.database.IDaoSupport;
 import org.springframework.stereotype.Component;













 @Component
 public class GalleryComponent
   implements IComponent
 {
   private IDaoSupport daoSupport;
   private IDBRouter baseDBRouter;
   private IGoodsGalleryManager goodsGalleryManager;

   public void install()
   {
     DBSolutionFactory.dbImport("file:com/enation/app/shop/component/gallery/gallery_install.xml", "es_");
   }


   public void unInstall()
   {
     DBSolutionFactory.dbImport("file:com/enation/app/shop/component/gallery/gallery_install.xml", "es_");
   }

   public IDaoSupport getDaoSupport()
   {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }

   public IDBRouter getBaseDBRouter() {
     return this.baseDBRouter;
   }

   public void setBaseDBRouter(IDBRouter baseDBRouter) {
     this.baseDBRouter = baseDBRouter;
   }

   public IGoodsGalleryManager getGoodsGalleryManager() {
     return this.goodsGalleryManager;
   }

   public void setGoodsGalleryManager(IGoodsGalleryManager goodsGalleryManager) {
     this.goodsGalleryManager = goodsGalleryManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\gallery\GalleryComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */