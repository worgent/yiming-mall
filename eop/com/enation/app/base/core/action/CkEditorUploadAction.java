 package com.enation.app.base.core.action;

 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.action.WWAction;
 import java.io.File;
 import javax.servlet.http.HttpServletRequest;


 public class CkEditorUploadAction
   extends WWAction
 {
   private File upload;
   private String uploadFileName;
   private String path;
   private String funcNum;

   public String execute()
   {
     this.funcNum = getRequest().getParameter("CKEditorFuncNum");
     if ((this.upload != null) && (this.uploadFileName != null)) {
       this.path = UploadUtil.upload(this.upload, this.uploadFileName, "ckeditor");
       this.path = UploadUtil.replacePath(this.path);
     }
     return "success";
   }

   public File getUpload()
   {
     return this.upload;
   }

   public void setUpload(File upload) {
     this.upload = upload;
   }

   public String getUploadFileName() {
     return this.uploadFileName;
   }

   public void setUploadFileName(String uploadFileName) {
     this.uploadFileName = uploadFileName;
   }

   public String getPath() {
     return this.path;
   }

   public void setPath(String path) {
     this.path = path;
   }

   public String getFuncNum() {
     return this.funcNum;
   }

   public void setFuncNum(String funcNum) {
     this.funcNum = funcNum;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\CkEditorUploadAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */