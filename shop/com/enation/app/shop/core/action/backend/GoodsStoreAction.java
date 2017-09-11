 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.core.service.IDepotManager;
 import com.enation.app.shop.core.service.IGoodsStoreManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.database.Page;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import net.sf.json.JSONArray;
 import org.apache.log4j.Logger;
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
 @Action("goodsStore")
 @Results({@org.apache.struts2.convention.annotation.Result(name="dialog_html", type="freemarker", location="/shop/admin/goodsstore/dialog.html"), @org.apache.struts2.convention.annotation.Result(name="goodsstore_list", type="freemarker", location="/shop/admin/goodsstore/goodsstore_list.html")})
 public class GoodsStoreAction
   extends WWAction
 {
   private IGoodsStoreManager goodsStoreManager;
   private IDepotManager depotManager;
   private int goodsid;
   private String html;
   private Integer stype;
   private String keyword;
   private String name;
   private String sn;
   private Integer stores;
   private Map goodsStoreMap;
   private List goodsStoreList;
   private String optype;
   private Integer depot_id;

   public String listGoodsStore()
   {
     this.goodsStoreList = this.depotManager.list();
     return "goodsstore_list";
   }

   public String listGoodsStoreJson()
   {
     Map storeMap = new HashMap();
     storeMap.put("stype", this.stype);
     storeMap.put("keyword", this.keyword);
     storeMap.put("name", this.name);
     storeMap.put("sn", this.sn);
     storeMap.put("store", this.stores);
     this.depot_id = Integer.valueOf(this.depot_id == null ? 0 : this.depot_id.intValue());
     storeMap.put("depotid", this.depot_id);

     Page page = this.goodsStoreManager.listGoodsStore(storeMap, getPage(), getPageSize(), null, getSort(), getOrder());
     showGridJson(page);
     return "json_message";
   }

   public String listStoreJson()
   {
     List list = this.goodsStoreManager.getStoreList();
     String s = JSONArray.fromObject(list).toString();
     this.json = s.replace("name", "text");
     return "json_message";
   }





   public String getStoreDialogHtml()
   {
     this.html = this.goodsStoreManager.getStoreHtml(Integer.valueOf(this.goodsid));
     return "dialog_html";
   }



   public String getStockDialogHtml()
   {
     this.html = this.goodsStoreManager.getStockHtml(Integer.valueOf(this.goodsid));
     return "dialog_html";
   }





   public String getShipDialogHtml()
   {
     this.html = this.goodsStoreManager.getShipHtml(Integer.valueOf(this.goodsid));
     return "dialog_html";
   }




   public String saveStore()
   {
     try
     {
       this.goodsStoreManager.saveStore(this.goodsid);
       showSuccessJson("保存商品库存成功");
     } catch (RuntimeException e) {
       e.printStackTrace();
       this.logger.error("保存商品库存出错", e);
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }




   public String saveStock()
   {
     try
     {
       this.goodsStoreManager.saveStock(this.goodsid);
       showSuccessJson("保存进货成功");
     } catch (RuntimeException e) {
       e.printStackTrace();
       this.logger.error("保存进货出错", e);
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }



   public String getWarnDialogHtml()
   {
     this.html = this.goodsStoreManager.getWarnHtml(Integer.valueOf(this.goodsid));
     return "dialog_html";
   }




   public String saveWarn()
   {
     try
     {
       this.goodsStoreManager.saveWarn(this.goodsid);
       showSuccessJson("保存报警成功");
     } catch (RuntimeException e) {
       this.logger.error("保存报警出错", e);
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }




   public String saveShip()
   {
     try
     {
       this.goodsStoreManager.saveShip(this.goodsid);
       showSuccessJson("保存出货成功");
     } catch (RuntimeException e) {
       this.logger.error("保存出货出错", e);
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }

   public String saveCmpl() {
     try {
       this.goodsStoreManager.saveCmpl(this.goodsid);
       showSuccessJson("更新状态成功");
     } catch (RuntimeException e) {
       this.logger.error("保更新状态出错", e);
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }

   public IGoodsStoreManager getGoodsStoreManager() {
     return this.goodsStoreManager;
   }

   public void setGoodsStoreManager(IGoodsStoreManager goodsStoreManager) {
     this.goodsStoreManager = goodsStoreManager;
   }

   public int getGoodsid() {
     return this.goodsid;
   }

   public void setGoodsid(int goodsid) {
     this.goodsid = goodsid;
   }

   public String getHtml() {
     return this.html;
   }

   public void setHtml(String html) {
     this.html = html;
   }

   public Integer getStype()
   {
     return this.stype;
   }

   public void setStype(Integer stype) {
     this.stype = stype;
   }

   public String getKeyword() {
     return this.keyword;
   }

   public void setKeyword(String keyword) {
     this.keyword = keyword;
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }

   public String getSn() {
     return this.sn;
   }

   public void setSn(String sn) {
     this.sn = sn;
   }

   public Integer getStores() {
     return this.stores;
   }

   public void setStores(Integer stores) {
     this.stores = stores;
   }

   public Map getGoodsStoreMap() {
     return this.goodsStoreMap;
   }

   public void setGoodsStoreMap(Map goodsStoreMap) {
     this.goodsStoreMap = goodsStoreMap;
   }

   public IDepotManager getDepotManager() {
     return this.depotManager;
   }

   public void setDepotManager(IDepotManager depotManager) {
     this.depotManager = depotManager;
   }

   public List getGoodsStoreList() {
     return this.goodsStoreList;
   }

   public void setGoodsStoreList(List goodsStoreList) {
     this.goodsStoreList = goodsStoreList;
   }

   public String getOptype()
   {
     return this.optype;
   }

   public void setOptype(String optype) {
     this.optype = optype;
   }

   public Integer getDepot_id() {
     return this.depot_id;
   }

   public void setDepot_id(Integer depot_id) {
     this.depot_id = depot_id;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\GoodsStoreAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */