 package com.enation.app.base.core.service.solution.impl;

 import com.enation.app.base.core.service.solution.ISetupLoader;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.util.FileUtil;
 import java.io.File;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.io.SAXReader;





 public class SetupLoaderImpl
   implements ISetupLoader
 {
   public Document load(String productId)
   {
     String xmlFile = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/setup.xml";
     Document document = null;
     SAXReader saxReader = new SAXReader();
     try {
       if (FileUtil.exist(xmlFile)) {
         document = saxReader.read(new File(xmlFile));
       }
     }
     catch (DocumentException e) {}

     return document;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\solution\impl\SetupLoaderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */