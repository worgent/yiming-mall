 package com.enation.app.shop.core.plugin.member;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.base.core.model.MemberAddress;
 import com.enation.app.shop.core.model.MemberComment;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.plugin.AutoRegisterPluginsBundle;
 import com.enation.framework.plugin.IPlugin;
 import java.util.List;
 import java.util.Map;
 import java.util.TreeMap;
 import org.apache.commons.logging.Log;








 public class MemberPluginBundle
   extends AutoRegisterPluginsBundle
 {
   public void onLogout(Member member)
   {
     try
     {
       List<IPlugin> plugins = getPlugins();

       if (plugins != null) {
         for (IPlugin plugin : plugins) {
           if ((plugin instanceof IMemberLogoutEvent)) {
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onLogout 开始...");
             }
             IMemberLogoutEvent event = (IMemberLogoutEvent)plugin;
             event.onLogout(member);
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onLogout 结束.");
             }
           }
         }
       }
     } catch (RuntimeException e) {
       //Logger logger = Logger.getLogger(
         loger.error("调用会员插件注销事件错误", e);
       throw e;
     }
   }




   public void onLogin(Member member, Long upLogintime)
   {
     try
     {
       List<IPlugin> plugins = getPlugins();

       if (plugins != null) {
         for (IPlugin plugin : plugins) {
           if ((plugin instanceof IMemberLoginEvent)) {
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onLogin 开始...");
             }
             IMemberLoginEvent event = (IMemberLoginEvent)plugin;
             event.onLogin(member, upLogintime);
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onLogin 结束.");
             }
           }
         }
       }
     } catch (RuntimeException e) {
       //Logger logger = Logger.getLogger(
         loger.error("调用会员插件登录事件错误", e);
       throw e;
     }
   }





   public void onRegister(Member member)
   {
     try
     {
       List<IPlugin> plugins = getPlugins();

       if (plugins != null) {
         for (IPlugin plugin : plugins) {
           if ((plugin instanceof IMemberRegisterEvent)) {
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onRegister 开始...");
             }
             IMemberRegisterEvent event = (IMemberRegisterEvent)plugin;
             event.onRegister(member);
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onRegister 结束.");
             }
           }
         }
       }
     } catch (RuntimeException e) {
       //Logger logger = Logger.getLogger(
         loger.error("调用会员插件注册事件错误", e);
       throw e;
     }
   }





   public void onEmailCheck(Member member)
   {
     try
     {
       List<IPlugin> plugins = getPlugins();

       if (plugins != null) {
         for (IPlugin plugin : plugins) {
           if ((plugin instanceof IMemberEmailCheckEvent)) {
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onRegister 开始...");
             }
             IMemberEmailCheckEvent event = (IMemberEmailCheckEvent)plugin;
             event.onEmailCheck(member);
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onRegister 结束.");
             }
           }
         }
       }
     } catch (RuntimeException e) {
       //Logger logger = Logger.getLogger(
         loger.error("调用会员插件邮件验证事件错误", e);
       throw e;
     }
   }






   public void onUpdatePassword(String password, int memberid)
   {
     try
     {
       List<IPlugin> plugins = getPlugins();

       if (plugins != null) {
         for (IPlugin plugin : plugins) {
           if ((plugin instanceof IMemberUpdatePasswordEvent)) {
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onUpdatePassword 开始...");
             }
             IMemberUpdatePasswordEvent event = (IMemberUpdatePasswordEvent)plugin;
             event.updatePassword(password, memberid);
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onUpdatePassword 结束.");
             }
           }
         }
       }
     } catch (RuntimeException e) {
       //Logger logger = Logger.getLogger(
         loger.error("调用会员更新密码事件错误", e);
       throw e;
     }
   }








   public Map<Integer, String> getTabList(Member member)
   {
     List<IPlugin> plugins = getPlugins();

     Map<Integer, String> tabMap = new TreeMap();
     if (plugins != null)
     {

       for (IPlugin plugin : plugins) {
         if ((plugin instanceof IMemberTabShowEvent))
         {

           IMemberTabShowEvent event = (IMemberTabShowEvent)plugin;




           if (event.canBeExecute(member))
           {



             String name = event.getTabName(member);
             tabMap.put(Integer.valueOf(event.getOrder()), name);
           }
         }
       }
     }



     return tabMap;
   }






   public Map<Integer, String> getDetailHtml(Member member)
   {
     Map<Integer, String> htmlMap = new TreeMap();
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.putData("member", member);
     List<IPlugin> plugins = getPlugins();

     if (plugins != null) {
       for (IPlugin plugin : plugins)
       {

         if ((plugin instanceof IMemberTabShowEvent)) {
           IMemberTabShowEvent event = (IMemberTabShowEvent)plugin;
           freeMarkerPaser.setClz(event.getClass());




           if (event.canBeExecute(member))
           {

             String html = event.onShowMemberDetailHtml(member);
             htmlMap.put(Integer.valueOf(event.getOrder()), html);
           }
         }
       }
     }


     return htmlMap;
   }

   public void onAddressAdd(MemberAddress address)
   {
     try {
       List<IPlugin> plugins = getPlugins();

       if (plugins != null) {
         for (IPlugin plugin : plugins) {
           if ((plugin instanceof IMemberAddressAddEvent)) {
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onAddressAdd 开始...");
             }
             IMemberAddressAddEvent event = (IMemberAddressAddEvent)plugin;
             event.addressAdd(address);
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onAddressAdd 结束.");
             }
           }
         }
       }
     } catch (RuntimeException e) {
       //Logger logger = Logger.getLogger(
         loger.error("调用会员添加地址事件错误", e);
       throw e;
     }
   }

   public void onComment(MemberComment comment) {
     try {
       List<IPlugin> plugins = getPlugins();

       if (plugins != null) {
         for (IPlugin plugin : plugins) {
           if ((plugin instanceof IMemberCommentEvent)) {
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onMemberComment 开始...");
             }
             IMemberCommentEvent event = (IMemberCommentEvent)plugin;
             event.onMemberComment(comment);
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onAddressAdd 结束.");
             }
           }
         }
       }
     } catch (RuntimeException e) {
       //Logger logger = Logger.getLogger(
         loger.error("调用会员添加地址事件错误", e);
       throw e;
     }
   }

   public String getName()
   {
     return "会员插件桩";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\member\MemberPluginBundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */