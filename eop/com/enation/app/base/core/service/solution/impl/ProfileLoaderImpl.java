 package com.enation.app.base.core.service.solution.impl;

 import com.enation.app.base.core.service.solution.IProfileLoader;
 import com.enation.eop.sdk.context.EopSetting;
 import javax.xml.parsers.DocumentBuilder;
 import javax.xml.parsers.DocumentBuilderFactory;
 import org.apache.log4j.Logger;
 import org.w3c.dom.Document;


 public class ProfileLoaderImpl
   implements IProfileLoader
 {
   protected final Logger logger = Logger.getLogger(getClass());

   public Document load(String productId) {
            String xmlFile = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/profile.xml";
     try {
       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

       DocumentBuilder builder = factory.newDocumentBuilder();
       return builder.parse("file:///" +xmlFile);
     }
     catch (Exception e)
     {
       this.logger.error(e);
       e.printStackTrace();
       throw new RuntimeException("load [" + productId + "] profile error");
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\solution\impl\ProfileLoaderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */