 package com.enation.app.base.core.service.solution;

 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import java.util.List;

 public class InstallUtil
 {
   public static String installing = "installing";

   public static void putMessaage(String msg) {
     if (ThreadContextHolder.getSessionContext() != null) {
       List msgList = (List)ThreadContextHolder.getSessionContext().getAttribute("installMsg");
       if (msgList == null) {
         msgList = new java.util.ArrayList();
       }
       msgList.add(msg);
       ThreadContextHolder.getSessionContext().setAttribute("installMsg", msgList);
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\solution\InstallUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */