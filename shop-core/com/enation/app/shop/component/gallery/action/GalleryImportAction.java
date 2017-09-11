 package com.enation.app.shop.component.gallery.action;

 import com.enation.app.shop.component.gallery.model.GoodsGallery;
 import com.enation.app.shop.component.gallery.service.IGoodsGalleryManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.database.IDBRouter;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.util.FileUtil;
 import java.util.List;
 import java.util.Map;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;










 @ParentPackage("shop_default")
 @Namespace("/shop/admin")
 @Results({@org.apache.struts2.convention.annotation.Result(name="import", type="freemarker", location="/com/enation/app/shop/component/gallery/action/import.html")})
 @Action("galleryImport")
 public class GalleryImportAction
   extends WWAction
 {
   private IDaoSupport daoSupport;
   private IDBRouter baseDBRouter;
   private IGoodsGalleryManager goodsGalleryManager;

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

   public String execute() {
     return "import";
   }

   public String imported() {
     try {
       if (EopSetting.DBTYPE.equals("1")) {
         String sql = "update " + this.baseDBRouter.getTableName("goods") + " set original = image_default,big=replace(image_default,concat('.',substring_index(image_default, '.', -1)),concat('_big.',substring_index(image_default, '.', -1))),small=replace(image_default,concat('.',substring_index(image_default, '.', -1)),concat('_small.',substring_index(image_default, '.', -1))),thumbnail=replace(image_default,concat('.',substring_index(image_default, '.', -1)),concat('_thumbnail.',substring_index(image_default, '.', -1)))";
         this.daoSupport.execute(sql, new Object[0]);
       } else if (!EopSetting.DBTYPE.equals("2"))
       {

         this.daoSupport.execute("update " + this.baseDBRouter.getTableName("goods") + " set original = image_default", new Object[0]);
         this.daoSupport.execute("update " + this.baseDBRouter.getTableName("goods") + " set big=replace(original, '.', '_big.'),small=replace(original, '.', '_small.'),thumbnail=replace(original, '.', '_thumbnail.')", new Object[0]);
       }

       List<Map> image_files = this.daoSupport.queryForList("select goods_id, image_default,image_file from " + this.baseDBRouter.getTableName("goods"), new Object[0]);
       for (Map map : image_files)
         if ((map.get("image_default") != null) && (map.get("image_file") != null))
         {

           String image_file = map.get("image_file").toString();


           String image_default = map.get("image_default").toString();
           String[] files = image_file.split(",");
           for (String file : files) {
             String ext = FileUtil.getFileExt(file);
             GoodsGallery gallery = new GoodsGallery();
             gallery.setGoods_id(Integer.valueOf(map.get("goods_id").toString()).intValue());
             gallery.setOriginal(file);
             gallery.setBig(file.replaceAll("." + ext, "_big." + ext));
             gallery.setThumbnail(file.replaceAll("." + ext, "_thumbnail." + ext));
             gallery.setTiny(file.replaceAll("." + ext, "_thumbnail." + ext));
             gallery.setSmall(file.replaceAll("." + ext, "_small." + ext));
             if (image_default.equals(file))
               gallery.setIsdefault(1);
             this.goodsGalleryManager.add(gallery);
           }
         }
       this.json = "{\"result\":\"1\"}";
     } catch (Exception e) {
       this.json = "{\"result\":\"0\"}";
       e.printStackTrace();
     }
     return "json_message";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\gallery\action\GalleryImportAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */