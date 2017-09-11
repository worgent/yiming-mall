 package com.enation.app.shop.core.service.impl.batchimport;

 import com.enation.app.shop.core.model.ImportDataSource;
 import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
 import java.util.Map;
 import org.w3c.dom.Element;









 public class GoodsFieldImporter
   implements IGoodsDataImporter
 {
   public void imported(Object value, Element node, ImportDataSource importConfig, Map goods)
   {
     String fieldname = node.getAttribute("fieldname");
     if (importConfig.isNewGoods()) {
       goods.put(fieldname, value);
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\batchimport\GoodsFieldImporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */