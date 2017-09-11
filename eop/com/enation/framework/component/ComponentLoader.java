 package com.enation.framework.component;

 import com.enation.framework.component.context.ComponentContext;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.plugin.IPluginBundle;
 import java.util.List;
 import org.springframework.beans.BeansException;
 import org.springframework.beans.factory.config.BeanPostProcessor;



 public class ComponentLoader
   implements BeanPostProcessor
 {
   public Object postProcessAfterInitialization(Object bean, String arg1)
     throws BeansException
   {
     return bean;
   }

   public Object postProcessBeforeInitialization(Object bean, String beanName)
     throws BeansException
   {
     AutoRegisterPlugin plugin;
     if ((bean instanceof AutoRegisterPlugin)) {
       plugin = (AutoRegisterPlugin)bean;
       if (plugin.getBundleList() != null)
       {


         List<IPluginBundle> pluginBundelList = plugin.getBundleList();
         for (IPluginBundle bundle : pluginBundelList) {
           bundle.registerPlugin(plugin);
         }
       }
     }



     if ((bean instanceof IComponent))
     {
       IComponent component = (IComponent)bean;
       ComponentView componentView = new ComponentView();
       componentView.setComponent(component);
       componentView.setComponentid(beanName);
       ComponentContext.registerComponent(componentView);
     }

     return bean;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\component\ComponentLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */