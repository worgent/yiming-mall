 package com.enation.app.b2b2c.core.tag.cart;

 import com.enation.app.b2b2c.core.service.IStoreDlyTypeManager;
 import com.enation.app.b2b2c.core.service.IStoreMemberAddressManager;
 import com.enation.app.b2b2c.core.service.IStoreTemplateManager;
 import com.enation.app.b2b2c.core.service.cart.IStoreCartManager;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsManager;
 import com.enation.app.shop.core.model.support.OrderPrice;
 import com.enation.app.shop.core.service.IDlyTypeManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.CurrencyUtil;
 import freemarker.template.TemplateModelException;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import org.springframework.stereotype.Component;








 @Component
 public class CartOrderTotlePriceTag
   extends BaseFreeMarkerTag
 {
   private IStoreCartManager storeCartManager;
   private IStoreDlyTypeManager storeDlyTypeManager;
   private IStoreMemberAddressManager storeMemberAddressManager;
   private IStoreGoodsManager storeGoodsManager;
   private IDlyTypeManager dlyTypeManager;
   private IStoreTemplateManager storeTemplateManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String sessionid = request.getSession().getId();
     Integer regionid = (Integer)params.get("regionId");


     List<Map> storeGoodsList = new ArrayList();
     storeGoodsList = this.storeCartManager.storeListGoods(sessionid);

     OrderPrice orderPrice = null;

     Double totleprice = Double.valueOf(0.0D);
     Double goodsprice = Double.valueOf(0.0D);
     Double shippingprice = Double.valueOf(0.0D);

     for (Map map : storeGoodsList)
     {
       Integer store_id = (Integer)map.get("store_id");
       List list = (List)map.get("goodslist");
       Integer tempid = this.storeTemplateManager.getDefTempid(store_id);
       Integer type_id = Integer.valueOf(0);
       if (tempid != null) {
         List<Map> dlyList = this.storeDlyTypeManager.getDlyTypeList(tempid);
         Map dlymap = (Map)dlyList.get(0);
         type_id = (Integer)dlymap.get("type_id");
       }
       orderPrice = this.storeCartManager.countPrice(list, regionid + "", type_id, Boolean.valueOf(false));

       totleprice = Double.valueOf(CurrencyUtil.add(totleprice.doubleValue(), orderPrice.getOrderPrice().doubleValue()));
       shippingprice = Double.valueOf(CurrencyUtil.add(shippingprice.doubleValue(), orderPrice.getShippingPrice().doubleValue()));
       goodsprice = Double.valueOf(CurrencyUtil.add(goodsprice.doubleValue(), orderPrice.getGoodsPrice().doubleValue()));
     }





     orderPrice.setOrderPrice(totleprice);
     orderPrice.setShippingPrice(shippingprice);
     orderPrice.setGoodsPrice(goodsprice);
     orderPrice.setNeedPayMoney(totleprice);

     return orderPrice;
   }

   public IStoreCartManager getStoreCartManager() {
     return this.storeCartManager;
   }

   public void setStoreCartManager(IStoreCartManager storeCartManager) {
     this.storeCartManager = storeCartManager;
   }

   public IStoreDlyTypeManager getStoreDlyTypeManager() {
     return this.storeDlyTypeManager;
   }

   public void setStoreDlyTypeManager(IStoreDlyTypeManager storeDlyTypeManager) {
     this.storeDlyTypeManager = storeDlyTypeManager;
   }

   public IStoreMemberAddressManager getStoreMemberAddressManager() {
     return this.storeMemberAddressManager;
   }

   public void setStoreMemberAddressManager(IStoreMemberAddressManager storeMemberAddressManager)
   {
     this.storeMemberAddressManager = storeMemberAddressManager;
   }

   public IStoreGoodsManager getStoreGoodsManager() {
     return this.storeGoodsManager;
   }

   public void setStoreGoodsManager(IStoreGoodsManager storeGoodsManager) {
     this.storeGoodsManager = storeGoodsManager;
   }

   public IDlyTypeManager getDlyTypeManager() {
     return this.dlyTypeManager;
   }

   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
     this.dlyTypeManager = dlyTypeManager;
   }

   public IStoreTemplateManager getStoreTemplateManager() {
     return this.storeTemplateManager;
   }

   public void setStoreTemplateManager(IStoreTemplateManager storeTemplateManager) {
     this.storeTemplateManager = storeTemplateManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\cart\CartOrderTotlePriceTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */