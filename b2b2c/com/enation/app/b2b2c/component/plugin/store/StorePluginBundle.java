 package com.enation.app.b2b2c.component.plugin.store;

 import com.enation.app.b2b2c.core.model.store.Store;
 import com.enation.framework.plugin.AutoRegisterPluginsBundle;
 import com.enation.framework.plugin.IPlugin;
 import java.util.List;
 import org.apache.commons.logging.Log;
 import org.springframework.stereotype.Component;












 @Component
 public class StorePluginBundle
   extends AutoRegisterPluginsBundle
 {
   public String getName()
   {
     return "店铺插件桩";
   }

   public void onAfterPass(Store store) {
     try {
       List<IPlugin> plugins = getPlugins();

       if (plugins != null) {
         for (IPlugin plugin : plugins) {
           if ((plugin instanceof IAfterStorePassEvent)) {
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onAfterPass 开始...");
             }
             IAfterStorePassEvent event = (IAfterStorePassEvent)plugin;
             event.AfterStorePassEvent(store);
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onAfterPass 结束.");
             }
           }
         }
       }
     } catch (RuntimeException e) {
//       if (loger.isErrorEnabled())
         loger.error("调用店铺插件[店铺通过]事件错误", e);
       throw e;
     }
   }

   public void onAfterApply(Store store) {
     try { List<IPlugin> plugins = getPlugins();

       if (plugins != null) {
         for (IPlugin plugin : plugins) {
           if ((plugin instanceof IAfterStoreApplyEvent)) {
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onAfterPass 开始...");
             }
             IAfterStoreApplyEvent event = (IAfterStoreApplyEvent)plugin;
             event.IAfterStoreApplyEvent(store);
             if (loger.isDebugEnabled()) {
               loger.debug("调用插件 : " + plugin.getClass() + " onAfterPass 结束.");
             }
           }
         }
       }
     } catch (RuntimeException e) {
//       if (loger.isErrorEnabled())
         loger.error("调用订单插件[申请店铺]事件错误", e);
       throw e;
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\component\plugin\store\StorePluginBundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */