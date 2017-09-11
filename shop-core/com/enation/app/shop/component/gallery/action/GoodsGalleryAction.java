 package com.enation.app.shop.component.gallery.action;

 import com.enation.app.shop.component.gallery.service.IGoodsGalleryManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.action.WWAction;
 import java.io.File;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;









 @ParentPackage("shop_default")
 @Namespace("/api/shop")
 @Action
 public class GoodsGalleryAction
   extends WWAction
 {
   private File filedata;
   private String filedataFileName;
   private String photoName;
   private IGoodsGalleryManager goodsGalleryManager;

   private boolean checkAdminPerm()
   {
     IUserService userService = UserServiceFactory.getUserService();
     if (!userService.isUserLoggedIn()) {
       showErrorJson("您无权限访问此API");
       return false;
     }
     return true;
   }




   public String upload()
   {
     if (this.filedata != null) {
       String name = this.goodsGalleryManager.upload(this.filedata, this.filedataFileName);
       this.json = name;
     }
     return "json_message";
   }

   public String delete() {
     if (!checkAdminPerm()) {
       return "json_message";
     }

     this.goodsGalleryManager.delete(this.photoName);
     showSuccessJson("图片删除成功");
     return "json_message";
   }

   public File getFiledata() {
     return this.filedata;
   }

   public void setFiledata(File filedata) {
     this.filedata = filedata;
   }

   public String getFiledataFileName() {
     return this.filedataFileName;
   }

   public void setFiledataFileName(String filedataFileName) {
     this.filedataFileName = filedataFileName;
   }

   public String getPhotoName() {
     return this.photoName;
   }

   public IGoodsGalleryManager getGoodsGalleryManager() {
     return this.goodsGalleryManager;
   }

   public void setGoodsGalleryManager(IGoodsGalleryManager goodsGalleryManager) {
     this.goodsGalleryManager = goodsGalleryManager;
   }

   public void setPhotoName(String photoName) {
     this.photoName = photoName;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\gallery\action\GoodsGalleryAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */