 package com.enation.app.shop.core.tag;

 import com.enation.app.base.core.model.MemberLv;
 import com.enation.app.shop.core.model.GoodsLvPrice;
 import com.enation.app.shop.core.service.IMemberLvManager;
 import com.enation.app.shop.core.service.IMemberPriceManager;
 import com.enation.app.shop.core.service.IProductManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.CurrencyUtil;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;













 @Component
 @Scope("prototype")
 public class GoodsVipPriceTag
   extends BaseFreeMarkerTag
 {
   private IMemberLvManager memberLvManager;
   private IMemberPriceManager memberPriceManager;
   private IProductManager productManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Map result = new HashMap(2);
     Map goods = (Map)ThreadContextHolder.getHttpRequest().getAttribute("goods");
     double price = Double.valueOf("" + goods.get("price")).doubleValue();
     double vipprice = price;
     if (price == 0.0D) {
       result.put("vipprice", Integer.valueOf(0));
     }
     List<MemberLv> memberLvList = this.memberLvManager.list();
     if ((memberLvList != null) && (memberLvList.size() > 0)) {
       List<GoodsLvPrice> glpList = this.memberPriceManager.listPriceByGid(Integer.valueOf(goods.get("goods_id").toString()).intValue());


       if ((glpList != null) && (glpList.size() > 0))
       {
         double discount = 1.0D;
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
         result.put("vipprice", Double.valueOf(vipprice));
       } else {
         double discount = 1.0D;
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
         result.put("vipprice", Double.valueOf(vipprice));
       }
     }
     result.put("memberLvList", memberLvList);
     return result;
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
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\GoodsVipPriceTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */