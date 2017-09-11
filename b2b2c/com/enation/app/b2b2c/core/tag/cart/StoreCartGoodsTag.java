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
 import freemarker.template.TemplateModelException;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import org.springframework.stereotype.Component;















 @Component
 public class StoreCartGoodsTag
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
     List<Map> storeGoodsList = new ArrayList();
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String sessionid = request.getSession().getId();
     storeGoodsList = this.storeCartManager.storeListGoods(sessionid);

     Integer addrid = (Integer)params.get("addrid");
     Integer regionsid = Integer.valueOf(0);
     if ((addrid != null) && (addrid.intValue() != 0)) {
       regionsid = this.storeMemberAddressManager.getRegionid(addrid);
     }

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
       OrderPrice orderPrice = this.storeCartManager.countPrice(list, regionsid + "", type_id, Boolean.valueOf(false));

       map.put("storeprice", orderPrice.getOrderPrice());
       map.put("originalPrice", orderPrice.getOriginalPrice());
       map.put("weight", orderPrice.getWeight());
     }
     return storeGoodsList;
   }

   public IStoreCartManager getStoreCartManager() {
     return this.storeCartManager;
   }

   public void setStoreCartManager(IStoreCartManager storeCartManager) { this.storeCartManager = storeCartManager; }

   public IStoreDlyTypeManager getStoreDlyTypeManager()
   {
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


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\cart\StoreCartGoodsTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */