 package com.enation.framework.test;

 import org.junit.BeforeClass;
 import org.springframework.context.ApplicationContext;
 import org.springframework.context.support.ClassPathXmlApplicationContext;
 import org.springframework.jdbc.core.JdbcTemplate;
 import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;









 public class SpringTestSupport
 {
   private static ApplicationContext context;
   protected static SimpleJdbcTemplate simpleJdbcTemplate;
   protected static JdbcTemplate jdbcTemplate;

   @BeforeClass
   public static void setup()
   {
     context = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/*.xml", "classpath*:testspring/*.xml" });

     simpleJdbcTemplate = (SimpleJdbcTemplate)getBean("simpleJdbcTemplate");
     jdbcTemplate = (JdbcTemplate)getBean("jdbcTemplate");
   }


   protected static <T> T getBean(String name)
   {
     return (T)context.getBean(name);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\test\SpringTestSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */