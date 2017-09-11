 package com.enation.app.base.core.action;

 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.component.ComponentView;
 import com.enation.framework.component.IComponentManager;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import net.sf.json.JSONArray;
 import org.apache.log4j.Logger;







 public class ComponentAction
   extends WWAction
 {
   private IComponentManager componentManager;
   private List<ComponentView> componentList;
   private String componentid;

   public String list()
   {
     return "list";
   }

   public String listJson()
   {
     this.componentList = this.componentManager.list();

     List l = new ArrayList();
     for (ComponentView view : this.componentList) {
       Map map = new HashMap();
       map.put("id", Integer.valueOf(view.getId()));
       map.put("name", view.getName());
       map.put("install_state", Integer.valueOf(view.getInstall_state()));
       map.put("enable_state", Integer.valueOf(view.getEnable_state()));
       map.put("error_message", view.getError_message());
       map.put("componentid", view.getComponentid());

       int size = view.getPluginList().size();
       if (size != 0) {
         map.put("state", "closed");
         map.put("children", view.getPluginList());
       }
       l.add(map);
     }

     this.json = JSONArray.fromObject(l).toString();
     return "json_message";
   }






   public String install()
   {
     if (EopSetting.IS_DEMO_SITE) {
       showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }
     try
     {
       this.componentManager.install(this.componentid);
       showSuccessJson("安装成功");
     } catch (RuntimeException e) {
       this.logger.error("安装组件[" + this.componentid + "]", e);
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }





   public String unInstall()
   {
     if (EopSetting.IS_DEMO_SITE) {
       showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }
     try {
       this.componentManager.unInstall(this.componentid);
       showSuccessJson("卸载成功");
     } catch (RuntimeException e) {
       this.logger.error("卸载组件[" + this.componentid + "]", e);
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }






   public String start()
   {
     if (EopSetting.IS_DEMO_SITE) {
       showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }
     try
     {
       this.componentManager.start(this.componentid);
       showSuccessJson("启动成功");
     } catch (RuntimeException e) {
       this.logger.error("启动组件[" + this.componentid + "]", e);
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }






   public String stop()
   {
     if (EopSetting.IS_DEMO_SITE) {
       showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }
     try
     {
       this.componentManager.stop(this.componentid);
       showSuccessJson("停用成功");
     } catch (RuntimeException e) {
       this.logger.error("停用组件[" + this.componentid + "]", e);
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }

   public IComponentManager getComponentManager() {
     return this.componentManager;
   }

   public void setComponentManager(IComponentManager componentManager) {
     this.componentManager = componentManager;
   }

   public List<ComponentView> getComponentList() {
     return this.componentList;
   }

   public void setComponentList(List<ComponentView> componentList) {
     this.componentList = componentList;
   }

   public String getComponentid() {
     return this.componentid;
   }

   public void setComponentid(String componentid) {
     this.componentid = componentid;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\ComponentAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */