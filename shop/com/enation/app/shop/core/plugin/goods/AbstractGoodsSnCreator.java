 package com.enation.app.shop.core.plugin.goods;

 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.plugin.IPlugin;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;









 public abstract class AbstractGoodsSnCreator
   extends AutoRegisterPlugin
   implements IPlugin, IGoodsBeforeAddEvent, IGoodsBeforeEditEvent
 {
   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request)
   {
     if (goods.get("store_id") == null) {
       goods.put("sn", createSn(goods));
     }
   }

   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request)
   {
     if (goods.get("store_id") == null) {
       goods.put("sn", createSn(goods));
     }
   }

   public abstract String createSn(Map paramMap);
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\goods\AbstractGoodsSnCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */