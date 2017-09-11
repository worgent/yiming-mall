 package com.enation.framework.context.spring;

 import java.io.IOException;
 import org.springframework.beans.BeansException;
 import org.springframework.beans.factory.config.BeanPostProcessor;
 import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
 import org.springframework.beans.factory.support.BeanDefinitionRegistry;
 import org.springframework.beans.factory.xml.ResourceEntityResolver;
 import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
 import org.springframework.context.ApplicationContext;
 import org.springframework.context.ApplicationContextAware;
 import org.springframework.context.ConfigurableApplicationContext;







 public class SpringContextHolder
   implements ApplicationContextAware
 {
   private static ConfigurableApplicationContext applicationContext;

   public void setApplicationContext(ApplicationContext a)
   {
     applicationContext = (ConfigurableApplicationContext)a;
   }





   public static ApplicationContext getApplicationContext()
   {
     checkApplicationContext();
     return applicationContext;
   }




   public static <T> T getBean(String name)
   {
     checkApplicationContext();
     return (T)applicationContext.getBean(name);
   }




   public static <T> T getBean(Class<T> clazz)
   {
     checkApplicationContext();
     return (T) applicationContext.getBeansOfType(clazz);
   }

   private static void checkApplicationContext() {
     if (applicationContext == null)
       throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
   }

   public static void loadbean() {
     XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry)applicationContext.getBeanFactory());

     beanDefinitionReader.setResourceLoader(applicationContext);
     beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(applicationContext));
     try
     {
       beanDefinitionReader.loadBeanDefinitions(applicationContext.getResources("classpath:newspring/newApplicationContext.xml"));
       addBeanPostProcessor();
     } catch (BeansException e) {
       e.printStackTrace();
     } catch (IOException e) {
       e.printStackTrace();
     }
   }

   private static void addBeanPostProcessor() { String[] postProcessorNames = applicationContext.getBeanFactory().getBeanNamesForType(BeanPostProcessor.class, true, false);

     for (String postProcessorName : postProcessorNames) {
       applicationContext.getBeanFactory().addBeanPostProcessor((BeanPostProcessor)applicationContext.getBean(postProcessorName));
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\context\spring\SpringContextHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */