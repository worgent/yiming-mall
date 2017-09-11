 package com.enation.app.b2b2c.core.service.cart.impl;

 import com.enation.app.b2b2c.core.model.StoreProduct;
 import com.enation.app.b2b2c.core.service.cart.IStoreProductManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import org.springframework.stereotype.Component;

 @Component
 public class StoreProductManager
   extends BaseSupport implements IStoreProductManager
 {
   public StoreProduct getByGoodsId(Integer goodsid)
   {
     String sql = "select * from es_product where goods_id=?";
     List<StoreProduct> proList = this.daoSupport.queryForList(sql, StoreProduct.class, new Object[] { goodsid });
     if ((proList == null) || (proList.isEmpty())) {
       return null;
     }
     return (StoreProduct)proList.get(0);
   }

   public StoreProduct get(Integer productid) {
     String sql = "select * from es_product where product_id=?";
     return (StoreProduct)this.daoSupport.queryForObject(sql, StoreProduct.class, new Object[] { productid });
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\cart\impl\StoreProductManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */