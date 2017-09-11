 package com.enation.app.base.core.action.api;

 import com.enation.framework.action.WWAction;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.JsonMessageUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.Date;
 import org.apache.log4j.Logger;

 public class TagTestCreateAction extends WWAction
 {
   private String content;
   private String params;
   private String filename;

   public String execute()
   {
     try
     {
       if (StringUtil.isEmpty(this.filename)) {
         this.filename = (createFileName() + ".html");
       }
       String filepath = com.enation.eop.sdk.context.EopSetting.EOP_PATH + "/docs/tags/runtime/" + this.filename;

       if (this.content == null) {
         this.content = "";
       }
       com.enation.framework.util.FileUtil.write(filepath, this.content);
       if (this.params == null) {
         this.params = "";
       }
       this.json = JsonMessageUtil.getStringJson("url", this.filename);
     } catch (Throwable e) {
       this.logger.error("生成标签测试页面出错", e);
       showErrorJson("生成标签测试页面出错" + e.getMessage());
     }
     return "json_message";
   }

   private String createFileName() {
     String filename = DateUtil.toString(new Date(), "yyyyMMddHHmmss");

     return filename + StringUtil.getRandStr(4);
   }


   public String getContent()
   {
     return this.content;
   }

   public void setContent(String content) {
     this.content = content;
   }

   public String getParams() {
     return this.params;
   }

   public void setParams(String params) {
     this.params = params;
   }

   public String getFilename() {
     return this.filename;
   }

   public void setFilename(String filename) {
     this.filename = filename;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\api\TagTestCreateAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */