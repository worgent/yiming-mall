 package com.enation.app.shop.core.action.api;

 import com.enation.app.shop.core.model.Goods;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.framework.action.WWAction;
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
 @Action("goods")
 public class GoodsApiAction
   extends WWAction
 {
   private IGoodsManager goodsManager;
   private Integer catid;
   private String keyword;
   private Integer brandid;
   private List<Goods> goodslist;
   private Map goodsMap;

   public String search()
   {
     this.goodsMap = new HashMap();

     this.goodsMap.put("catid", this.catid);
     this.goodsMap.put("brandid", this.brandid);
     this.goodsMap.put("keyword", this.keyword);
     this.goodsMap.put("stype", Integer.valueOf(0));

     this.goodslist = this.goodsManager.searchGoods(this.goodsMap);
     this.json = JsonMessageUtil.getListJson(this.goodslist);
     return "json_message";
   }

   public IGoodsManager getGoodsManager() {
     return this.goodsManager;
   }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }

   public Integer getCatid() {
     return this.catid;
   }

   public void setCatid(Integer catid) {
     this.catid = catid;
   }

   public String getKeyword() {
     return this.keyword;
   }

   public void setKeyword(String keyword) {
     this.keyword = keyword;
   }

   public Integer getBrandid() {
     return this.brandid;
   }

   public void setBrandid(Integer brandid) {
     this.brandid = brandid;
   }

   public List<Goods> getGoodslist() {
     return this.goodslist;
   }

   public void setGoodslist(List<Goods> goodslist) {
     this.goodslist = goodslist;
   }

   public Map getGoodsMap() {
     return this.goodsMap;
   }

   public void setGoodsMap(Map goodsMap) {
     this.goodsMap = goodsMap;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\api\GoodsApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */