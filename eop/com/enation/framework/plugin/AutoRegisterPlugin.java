 package com.enation.framework.plugin;

 import java.util.List;
 import org.apache.log4j.Logger;

 public abstract class AutoRegisterPlugin implements IPlugin
 {
   protected final Logger logger = Logger.getLogger(getClass());
   protected List<IPluginBundle> bundleList;
   private boolean isEnable = false;

   public List<IPluginBundle> getBundleList() {
     return this.bundleList;
   }

   public void setBundleList(List<IPluginBundle> bundleList) {
     this.bundleList = bundleList;
   }

   public void disable() {
     this.isEnable = false;
   }

   public void enable() {
     this.isEnable = true;
   }

   public boolean getIsEnable() {
     return this.isEnable;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\plugin\AutoRegisterPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */