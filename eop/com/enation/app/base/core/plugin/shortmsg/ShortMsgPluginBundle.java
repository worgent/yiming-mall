 package com.enation.app.base.core.plugin.shortmsg;

 import com.enation.app.base.core.model.ShortMsg;
 import com.enation.framework.plugin.AutoRegisterPluginsBundle;
 import com.enation.framework.plugin.IPlugin;
 import java.util.ArrayList;
 import java.util.List;






 public class ShortMsgPluginBundle
   extends AutoRegisterPluginsBundle
 {
   public String getName()
   {
     return "短消息插件桩";
   }

   public List<ShortMsg> getMessageList() {
     List<ShortMsg> msgList = new ArrayList();
     List<IPlugin> plugins = getPlugins();

     if (plugins != null) {
       for (IPlugin plugin : plugins) {
         if ((plugin instanceof IShortMessageEvent)) {
           IShortMessageEvent event = (IShortMessageEvent)plugin;
           List<ShortMsg> somemsgList = event.getMessage();
           msgList.addAll(somemsgList);
         }
       }
     }
     return msgList;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\plugin\shortmsg\ShortMsgPluginBundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */