 package com.enation.app.shop.core.service;

 import com.enation.eop.processor.Processor;
 import com.enation.eop.processor.core.Response;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.EncryptionUtil1;
 import com.enation.framework.util.HttpUtil;
 import java.net.URLDecoder;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.sf.json.JSONObject;


 public class AutoLoginProcessor
   implements Processor
 {
   private IMemberManager memberManager;

   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
   {
     try
     {
       String url = httpRequest.getRequestURI();
       if (url != null) {
         url = url.toLowerCase();
         if ((url.endsWith("/")) || (url.endsWith(".html")) || (url.endsWith(".do")))
         {
           if (UserServiceFactory.getUserService().getCurrentMember() == null) {
             String cookieValue = HttpUtil.getCookieValue(ThreadContextHolder.getHttpRequest(), "JavaShopUser");

             if ((cookieValue != null) && (!cookieValue.equals(""))) {
               cookieValue = URLDecoder.decode(cookieValue, "UTF-8");

               cookieValue = EncryptionUtil1.authcode(cookieValue, "DECODE", "", 0);

               if ((cookieValue != null) && (!cookieValue.equals("")))
               {
                 Map map = (Map)JSONObject.toBean(JSONObject.fromObject(cookieValue), Map.class);


                 if (map != null) {
                   String username = map.get("username").toString();

                   String password = map.get("password").toString();

                   int result = this.memberManager.loginWithCookie(username, password);

                   if (result != 1) {
                     HttpUtil.addCookie(ThreadContextHolder.getHttpResponse(), "JavaShopUser", "", 0);
                   }
                 }
               }
             }
           }
         }
       }
     }
     catch (Exception ex) {}

     return null;
   }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }




   public static void main(String[] args)
   {
     String str1 = "db21SPFxwCWgshcLqsIxFzS9YeEusB/qzRdC1OKk2R47uLdLCuai1BPUMh5xNJhSkwuu1v09po2qNuLPsWjLg/+p4aaeZZ70LyCEGwcwMZGuHCY9zmKDv1sXBZKQ6OXjFV04MQ";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\AutoLoginProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */