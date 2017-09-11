 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.framework.action.WWAction;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import net.sf.json.JSONArray;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;




 @Component
 @Scope("prototype")
 @ParentPackage("shop_default")
 @Namespace("/shop/admin")
 @Action("goodsSelect")
 @Results({@org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/shop/admin/goods/goods_opt.html")})
 public class GoodsSelectAction
   extends WWAction
 {
   private IGoodsCatManager goodsCatManager;
   private IGoodsManager goodsManager;
   private Integer catid;
   private Integer sing;
   private Map goodsMap;

   public String list()
   {
     return "list";
   }

   public String listJson()
   {
     List catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
     String s = JSONArray.fromObject(catList).toString();
     this.json = s.replace("name", "text").replace("cat_id", "id");
     return "json_message";
   }

   public String listGoodsById()
   {
     this.goodsMap = new HashMap();
     this.goodsMap.put("catid", this.catid);
     List goodslist = this.goodsManager.searchGoods(this.goodsMap);
     showGridJson(goodslist);
     return "json_message";
   }

   public IGoodsCatManager getGoodsCatManager() {
     return this.goodsCatManager;
   }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
     this.goodsCatManager = goodsCatManager;
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

   public Integer getSing() {
     return this.sing;
   }

   public void setSing(Integer sing) {
     this.sing = sing;
   }

   public Map getGoodsMap() {
     return this.goodsMap;
   }

   public void setGoodsMap(Map goodsMap) {
     this.goodsMap = goodsMap;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\GoodsSelectAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */