 package com.enation.app.base.core.action;

 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.action.WWAction;
 import java.io.File;








 public class UploadAction
   extends WWAction
 {
   private String fileFileName;
   private File file;
   private int createThumb = 0;

   private String subFolder;

   private int ajax;

   private String type;

   private String picname;

   private int width;

   private int height;
   protected String path = null;

   public String getPicname() {
     return this.picname;
   }

   public void setPicname(String picname) {
     this.picname = picname;
   }

   public String execute() {
     return "input";
   }

   public String upload() {
     if ((this.file != null) && (this.fileFileName != null)) {
       if (this.createThumb == 1) {
         this.path = UploadUtil.upload(this.file, this.fileFileName, this.subFolder, this.width, this.height)[0];
       } else {
         this.path = UploadUtil.upload(this.file, this.fileFileName, this.subFolder);
       }

       if (this.path != null) {
         this.path = UploadUtil.replacePath(this.path);
       }
       if (this.ajax == 1) {
         this.json = ("{\"result\":1,\"path\":\"" + this.path + "\",\"thumbnail\":\"" + UploadUtil.getThumbPath(this.path, "_thumbnail") + "\",\"filename\":\"" + this.fileFileName + "\"}");
         return "json_message";
       }
     }
     return "success";
   }

   public String delete() {
     UploadUtil.deleteFile(this.picname);
     this.json = "{'result':0}";
     return "json_message";
   }

   public String getFileFileName() {
     return this.fileFileName;
   }

   public void setFileFileName(String fileFileName) {
     this.fileFileName = fileFileName;
   }

   public File getFile() {
     return this.file;
   }

   public void setFile(File file) {
     this.file = file;
   }

   public int getCreateThumb() {
     return this.createThumb;
   }

   public void setCreateThumb(int createThumb) {
     this.createThumb = createThumb;
   }

   public String getSubFolder() {
     return this.subFolder;
   }

   public void setSubFolder(String subFolder) {
     this.subFolder = subFolder;
   }

   public int getAjax() {
     return this.ajax;
   }

   public void setAjax(int ajax) {
     this.ajax = ajax;
   }

   public int getWidth() {
     return this.width;
   }

   public void setWidth(int width) {
     this.width = width;
   }

   public int getHeight() {
     return this.height;
   }

   public void setHeight(int height) {
     this.height = height;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\UploadAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */