 package com.enation.eop.sdk.listener;

 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.component.IComponentManager;
 import com.enation.framework.context.spring.SpringContextHolder;
 import javax.servlet.ServletContextEvent;
 import javax.servlet.ServletContextListener;












 public class EopContextLoaderListener
   implements ServletContextListener
 {
   public void contextDestroyed(ServletContextEvent event) {}

   public void contextInitialized(ServletContextEvent event)
   {
     if ((EopSetting.INSTALL_LOCK.toUpperCase().equals("YES")) && ("1".equals(EopSetting.RUNMODE))) {
       IComponentManager componentManager = (IComponentManager)SpringContextHolder.getBean("componentManager");
       componentManager.startComponents();
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\listener\EopContextLoaderListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */