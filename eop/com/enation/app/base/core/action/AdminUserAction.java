 package com.enation.app.base.core.action;

 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.eop.processor.core.Response;
 import com.enation.eop.processor.core.StringResponse;
 import com.enation.eop.sdk.user.UserContext;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.util.HttpUtil;
 import com.enation.framework.util.StringUtil;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.log4j.Logger;











 public class AdminUserAction
   extends WWAction
 {
   private IAdminUserManager adminUserManager;
   private String username;
   private String password;
   private String valid_code;
   private String remember_login_name;

   public String login()
   {
     try
     {
       if (this.valid_code == null)
       {
         showErrorJson("验证码输入错误");
         return "json_message";
       }
       this.valid_code = this.valid_code.toLowerCase();
       WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
       Object realCode = ("" + sessonContext.getAttribute("valid_codeadmin")).toLowerCase();

       if (!this.valid_code.equals(realCode)) {
         showErrorJson("验证码输入错误");

         return "json_message";
       }





       this.adminUserManager.login(this.username, this.password);


       HttpServletResponse httpResponse = ThreadContextHolder.getHttpResponse();

       if (!StringUtil.isEmpty(this.remember_login_name)) {
         HttpUtil.addCookie(httpResponse, "loginname", this.username, 31536000);
       } else {
         HttpUtil.addCookie(httpResponse, "loginname", "", 0);
       }
       showSuccessJson("登录成功");
     }
     catch (Throwable exception) {
       this.logger.error(exception.getMessage(), exception);
       showErrorJson(exception.getMessage());
     }

     return "json_message";
   }

   public String logout() {
     WebSessionContext<UserContext> sessonContext = ThreadContextHolder.getSessionContext();
     Response response = new StringResponse();
     sessonContext.removeAttribute("usercontext");
     sessonContext.removeAttribute("admin_user_key");

     showSuccessJson("成功注销");
     return "json_message";
   }

   public IAdminUserManager getAdminUserManager() {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) { this.adminUserManager = adminUserManager; }

   public String getUsername() {
     return this.username;
   }

   public void setUsername(String username) { this.username = username; }

   public String getPassword() {
     return this.password;
   }

   public void setPassword(String password) { this.password = password; }

   public String getValid_code() {
     return this.valid_code;
   }

   public void setValid_code(String valid_code) { this.valid_code = valid_code; }

   public String getRemember_login_name()
   {
     return this.remember_login_name;
   }

   public void setRemember_login_name(String remember_login_name) {
     this.remember_login_name = remember_login_name;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\AdminUserAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */