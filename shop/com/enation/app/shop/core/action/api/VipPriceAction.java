 package com.enation.app.shop.core.action.api;

 import com.enation.app.base.core.model.MemberLv;
 import com.enation.app.shop.core.model.GoodsLvPrice;
 import com.enation.app.shop.core.model.Product;
 import com.enation.app.shop.core.service.IMemberLvManager;
 import com.enation.app.shop.core.service.IMemberPriceManager;
 import com.enation.app.shop.core.service.IProductManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.util.CurrencyUtil;
 import com.enation.framework.util.JsonMessageUtil;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;
















 @Component
 @Scope("prototype")
 @ParentPackage("eop_default")
 @Namespace("/api/shop")
 @Action("vipprice")
 public class VipPriceAction
   extends WWAction
 {
   private IMemberLvManager memberLvManager;
   private IMemberPriceManager memberPriceManager;
   private IProductManager productManager;
   private Integer productid;

   public String showVipPrice()
   {
     Product product = this.productManager.get(this.productid);
     double price = product.getPrice().doubleValue();
     double vipprice = price;
     List<MemberLv> memberLvList = this.memberLvManager.list();

     List<GoodsLvPrice> glpList = this.memberPriceManager.listPriceByPid(this.productid.intValue());
//     double discount;
     double discount; if ((glpList != null) && (glpList.size() > 0))
     {
       discount = 1.0D;
       for (MemberLv lv : memberLvList) {
         double lvprice1 = 0.0D;
         if (lv.getDiscount() != null) {
           discount = lv.getDiscount().intValue() / 100.0D;
           lvprice1 = CurrencyUtil.mul(price, discount).doubleValue();
         }
         double lvPrice = getMemberPrice(lv.getLv_id().intValue(), glpList);
         if (lvPrice == 0.0D) {
           lv.setLvPrice(Double.valueOf(lvprice1));
           lvPrice = lvprice1;
         } else {
           lv.setLvPrice(Double.valueOf(lvPrice));
         }
         if (vipprice > lvPrice) {
           vipprice = lvPrice;
         }
       }
     } else {
       discount = 1.0D;
       for (MemberLv lv : memberLvList) {
         if (lv.getDiscount() != null) {
           discount = lv.getDiscount().intValue() / 100.0D;
           double lvprice = CurrencyUtil.mul(price, discount).doubleValue();
           lv.setLvPrice(Double.valueOf(lvprice));
           if (vipprice > lvprice) {
             vipprice = lvprice;
           }
         }
       }
     }

     Map vip = new HashMap(2);
     vip.put("vipprice", Double.valueOf(vipprice));
     vip.put("weight", product.getWeight());
     this.json = JsonMessageUtil.getObjectJson(vip);
     return "json_message";
   }







   private double getMemberPrice(int lv_id, List<GoodsLvPrice> memPriceList)
   {
     for (GoodsLvPrice lvPrice : memPriceList) {
       if (lv_id == lvPrice.getLvid()) {
         return lvPrice.getPrice().doubleValue();
       }
     }
     return 0.0D;
   }

   public IMemberLvManager getMemberLvManager() {
     return this.memberLvManager;
   }

   public void setMemberLvManager(IMemberLvManager memberLvManager) {
     this.memberLvManager = memberLvManager;
   }

   public IMemberPriceManager getMemberPriceManager() {
     return this.memberPriceManager;
   }

   public void setMemberPriceManager(IMemberPriceManager memberPriceManager) {
     this.memberPriceManager = memberPriceManager;
   }

   public IProductManager getProductManager() {
     return this.productManager;
   }

   public void setProductManager(IProductManager productManager) {
     this.productManager = productManager;
   }

   public Integer getProductid() {
     return this.productid;
   }

   public void setProductid(Integer productid) {
     this.productid = productid;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\api\VipPriceAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */