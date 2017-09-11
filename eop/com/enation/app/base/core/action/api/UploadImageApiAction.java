 package com.enation.app.base.core.action.api;

 import com.enation.eop.sdk.utils.UploadUtil;
 import java.io.File;

 public class UploadImageApiAction extends com.enation.framework.action.WWAction
 {
   private File image;
   private String imageFileName;
   private String subFolder;

   public String execute()
   {
     try
     {
       String fsImgPath = UploadUtil.upload(this.image, this.imageFileName, this.subFolder);
       String path = "{\"img\":\"" + UploadUtil.replacePath(fsImgPath) + "\",\"fsimg\":\"" + fsImgPath + "\"}";
       this.json = path;
     } catch (Throwable e) {
       showErrorJson("上传出错" + e.getLocalizedMessage());
     }
     return "json_message";
   }

   public File getImage() { return this.image; }

   public void setImage(File image) {
     this.image = image;
   }

   public String getImageFileName() { return this.imageFileName; }

   public void setImageFileName(String imageFileName) {
     this.imageFileName = imageFileName;
   }

   public String getSubFolder() { return this.subFolder; }

   public void setSubFolder(String subFolder) {
     this.subFolder = subFolder;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\api\UploadImageApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */