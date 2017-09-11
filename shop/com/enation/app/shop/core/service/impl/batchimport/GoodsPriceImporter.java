 package com.enation.app.shop.core.service.impl.batchimport;

 import com.enation.app.shop.core.model.ImportDataSource;
 import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
 import java.util.Map;
 import org.w3c.dom.Element;


 public class GoodsPriceImporter
   implements IGoodsDataImporter
 {
   public void imported(Object value, Element node, ImportDataSource importDs, Map goods)
   {
     if ((value == null) || (value.equals(""))) { value = Integer.valueOf(0);
     }
     if (importDs.isNewGoods()) {
       if ("mkprice".equals(node.getAttribute("type"))) {
         goods.put("mktprice", value);
       } else {
         goods.put("price", value);
       }
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\batchimport\GoodsPriceImporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */