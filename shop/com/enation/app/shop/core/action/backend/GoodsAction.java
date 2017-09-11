 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.core.model.Goods;
 import com.enation.app.shop.core.model.Tag;
 import com.enation.app.shop.core.model.support.GoodsEditDTO;
 import com.enation.app.shop.core.plugin.goods.GoodsPluginBundle;
 import com.enation.app.shop.core.service.IBrandManager;
 import com.enation.app.shop.core.service.ICartManager;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.ITagManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.JsonMessageUtil;
 import com.enation.framework.util.StringUtil;
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
 @Action("goods")
 @Results({@org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/shop/admin/goods/goods_list.html"), @org.apache.struts2.convention.annotation.Result(name="cat_tree", type="freemarker", location="/shop/admin/cat/select.html"), @org.apache.struts2.convention.annotation.Result(name="trash_list", type="freemarker", location="/shop/admin/goods/goods_trash.html"), @org.apache.struts2.convention.annotation.Result(name="input", type="freemarker", location="/shop/admin/goods/goods_input.html"), @org.apache.struts2.convention.annotation.Result(name="select_cat", type="freemarker", location="/shop/admin/goods/select_cat.html")})
 public class GoodsAction
   extends WWAction
 {
   protected String name;
   protected String sn;
   protected String order;
   private Integer catid;
   protected Integer[] goods_id;
   protected List brandList;
   protected Integer brand_id;
   protected Integer is_market;
   protected Goods goods;
   protected Map goodsView;
   protected Integer goodsId;
   protected List catList;
   protected IGoodsCatManager goodsCatManager;
   protected IBrandManager brandManager;
   protected IGoodsManager goodsManager;
   private ICartManager cartManager;
   private IOrderManager orderManager;
   protected Boolean is_edit;
   protected String actionName;
   protected Integer market_enable;
   private Integer[] tagids;
   private GoodsPluginBundle goodsPluginBundle;
   private ITagManager tagManager;
   protected Map<Integer, String> pluginTabs;
   protected Map<Integer, String> pluginHtmls;
   private List<Tag> tagList;
   private String is_other;
   private Integer stype;
   private String keyword;
   private Map goodsMap;
   private String optype = "no";



   public String selectCat()
   {
     return "select_cat";
   }












   public String searchGoods()
   {
     Map goodsMap = new HashMap();
     if (this.stype != null) {
       if (this.stype.intValue() == 0) {
         goodsMap.put("stype", this.stype);
         goodsMap.put("keyword", this.keyword);
       } else if (this.stype.intValue() == 1) {
         goodsMap.put("stype", this.stype);
         goodsMap.put("name", this.name);
         goodsMap.put("sn", this.sn);
         goodsMap.put("catid", this.catid);
       }
     }
     this.webpage = this.goodsManager.searchGoods(goodsMap, getPage(), getPageSize(), null, getSort(), getOrder());
     String s = JSONArray.fromObject(this.webpage.getResult()).toString();
     this.json = s;
     return "json_message";
   }









   public String list()
   {
     this.goodsMap = new HashMap();
     if (this.name != null) {
       String encoding = EopSetting.ENCODING;
       if (!StringUtil.isEmpty(encoding)) {
         this.name = StringUtil.to(this.name, encoding);
       }
     }

     this.goodsMap.put("brand_id", this.brand_id);
     this.goodsMap.put("is_market", this.is_market);
     this.goodsMap.put("catid", this.catid);
     this.goodsMap.put("name", this.name);
     this.goodsMap.put("sn", this.sn);
     this.goodsMap.put("tagids", this.tagids);

     this.brandList = this.brandManager.list();
     this.tagList = this.tagManager.list();

     this.webpage = this.goodsManager.searchGoods(this.goodsMap, getPage(), getPageSize(), this.is_other, "goods_id", "desc");
     return "list";
   }











   public String listJson()
   {
     if (this.name != null) {
       String encoding = EopSetting.ENCODING;
       if (!StringUtil.isEmpty(encoding)) {
         this.name = StringUtil.to(this.name, encoding);
       }
     }

     this.goodsMap = new HashMap();
     if (this.stype != null) {
       if (this.stype.intValue() == 0) {
         this.goodsMap.put("stype", this.stype);
         this.goodsMap.put("keyword", this.keyword);
       } else if (this.stype.intValue() == 1) {
         this.goodsMap.put("stype", this.stype);
         this.goodsMap.put("name", this.name);
         this.goodsMap.put("sn", this.sn);
         this.goodsMap.put("catid", this.catid);
       }
     }

     this.webpage = this.goodsManager.searchGoods(this.goodsMap, getPage(), getPageSize(), null, getSort(), getOrder());
     showGridJson(this.webpage);

     return "json_message";
   }





   public String getCatTree()
   {
     this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
     return "cat_tree";
   }




   public String trash_list()
   {
     return "trash_list";
   }






   public String trash_listJson()
   {
     this.webpage = this.goodsManager.pageTrash(this.name, this.sn, this.order, getPage(), getPageSize());

     showGridJson(this.webpage);
     return "json_message";
   }







   public String delete()
   {
     if (EopSetting.IS_DEMO_SITE) {
       for (Integer gid : this.goods_id) {
         if (gid.intValue() <= 261) {
           showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
           return "json_message";
         }
       }
     }
     try {
       if (this.goods_id != null)
         for (Integer goodsid : this.goods_id) {
           if (this.cartManager.checkGoodsInCart(goodsid)) {
             showErrorJson("删除失败，此商品已加入购物车");
             return "json_message";
           }
           if (this.orderManager.checkGoodsInOrder(goodsid.intValue())) {
             showErrorJson("删除失败，此商品已经下单");
             return "json_message";
           }
         }
       this.goodsManager.delete(this.goods_id);
       showSuccessJson("删除成功");
     } catch (RuntimeException e) {
       showErrorJson("删除失败");
       this.logger.error("商品删除失败", e);
     }
     return "json_message";
   }





   public String revert()
   {
     try
     {
       this.goodsManager.revert(this.goods_id);
       showSuccessJson("还原成功");
     } catch (RuntimeException e) {
       showErrorJson("还原失败");
       this.logger.error("商品还原失败", e);
     }
     return "json_message";
   }





   public String clean()
   {
     try
     {
       this.goodsManager.clean(this.goods_id);
       showSuccessJson("清除成功");
     } catch (RuntimeException e) {
       showErrorJson("清除失败");
       this.logger.error("商品清除失败", e);
     }
     return "json_message";
   }











   public String add()
   {
     this.actionName = "goods!saveAdd.do";
     this.is_edit = Boolean.valueOf(false);

     this.pluginTabs = this.goodsPluginBundle.getTabList();
     this.pluginHtmls = this.goodsPluginBundle.onFillAddInputData();

     return "input";
   }











   public String edit()
   {
     this.actionName = "goods!saveEdit.do";
     this.is_edit = Boolean.valueOf(true);

     this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
     GoodsEditDTO editDTO = this.goodsManager.getGoodsEditData(this.goodsId);
     this.goodsView = editDTO.getGoods();

     this.pluginTabs = this.goodsPluginBundle.getTabList();
     this.pluginHtmls = editDTO.getHtmlMap();

     return "input";
   }





   public String saveAdd()
   {
     try
     {
       this.goodsManager.add(this.goods);
       JsonMessageUtil.getNumberJson("goodsid", this.goods.getGoods_id());
       showSuccessJson("商品添加成功");
     }
     catch (RuntimeException e) {
       this.logger.error("添加商品出错", e);
       showErrorJson("添加商品出错" + e.getMessage());
     }

     return "json_message";
   }




   public String saveEdit()
   {
     try
     {
       this.goodsManager.edit(this.goods);
       showSuccessJson("商品修改成功");
     }
     catch (RuntimeException e) {
       this.logger.error("修改商品出错", e);
       showErrorJson("修改商品出错" + e.getMessage());
     }
     return "json_message";
   }





   public String updateMarketEnable()
   {
     try
     {
       this.goodsManager.updateField("market_enable", Integer.valueOf(1), this.goodsId);
       showSuccessJson("更新上架状态成功");
     } catch (RuntimeException e) {
       showErrorJson("更新上架状态失败");
       this.logger.error("商品更新上架失败", e);
     }
     return "json_message";
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) { this.name = name; }

   public String getSn() {
     return this.sn;
   }

   public void setSn(String sn) { this.sn = sn; }

   public String getOrder() {
     return this.order;
   }

   public void setOrder(String order) { this.order = order; }

   public Integer getCatid() {
     return this.catid;
   }

   public void setCatid(Integer catid) { this.catid = catid; }

   public Integer[] getGoods_id() {
     return this.goods_id;
   }

   public void setGoods_id(Integer[] goods_id) { this.goods_id = goods_id; }

   public List getBrandList() {
     return this.brandList;
   }

   public void setBrandList(List brandList) { this.brandList = brandList; }

   public Integer getBrand_id() {
     return this.brand_id;
   }

   public void setBrand_id(Integer brand_id) { this.brand_id = brand_id; }

   public Integer getIs_market() {
     return this.is_market;
   }

   public void setIs_market(Integer is_market) { this.is_market = is_market; }

   public Goods getGoods() {
     return this.goods;
   }

   public void setGoods(Goods goods) { this.goods = goods; }

   public Map getGoodsView() {
     return this.goodsView;
   }

   public void setGoodsView(Map goodsView) { this.goodsView = goodsView; }

   public Integer getGoodsId() {
     return this.goodsId;
   }

   public void setGoodsId(Integer goodsId) { this.goodsId = goodsId; }

   public List getCatList() {
     return this.catList;
   }

   public void setCatList(List catList) { this.catList = catList; }

   public IGoodsCatManager getGoodsCatManager() {
     return this.goodsCatManager;
   }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) { this.goodsCatManager = goodsCatManager; }

   public IBrandManager getBrandManager() {
     return this.brandManager;
   }

   public void setBrandManager(IBrandManager brandManager) { this.brandManager = brandManager; }

   public IGoodsManager getGoodsManager() {
     return this.goodsManager;
   }

   public void setGoodsManager(IGoodsManager goodsManager) { this.goodsManager = goodsManager; }

   public ICartManager getCartManager() {
     return this.cartManager;
   }

   public void setCartManager(ICartManager cartManager) { this.cartManager = cartManager; }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) { this.orderManager = orderManager; }

   public Boolean getIs_edit() {
     return this.is_edit;
   }

   public void setIs_edit(Boolean is_edit) { this.is_edit = is_edit; }

   public String getActionName() {
     return this.actionName;
   }

   public void setActionName(String actionName) { this.actionName = actionName; }

   public Integer getMarket_enable() {
     return this.market_enable;
   }

   public void setMarket_enable(Integer market_enable) { this.market_enable = market_enable; }

   public Integer[] getTagids() {
     return this.tagids;
   }

   public void setTagids(Integer[] tagids) { this.tagids = tagids; }

   public GoodsPluginBundle getGoodsPluginBundle() {
     return this.goodsPluginBundle;
   }

   public void setGoodsPluginBundle(GoodsPluginBundle goodsPluginBundle) { this.goodsPluginBundle = goodsPluginBundle; }

   public ITagManager getTagManager() {
     return this.tagManager;
   }

   public void setTagManager(ITagManager tagManager) { this.tagManager = tagManager; }

   public Map<Integer, String> getPluginTabs() {
     return this.pluginTabs;
   }

   public void setPluginTabs(Map<Integer, String> pluginTabs) { this.pluginTabs = pluginTabs; }

   public Map<Integer, String> getPluginHtmls() {
     return this.pluginHtmls;
   }

   public void setPluginHtmls(Map<Integer, String> pluginHtmls) { this.pluginHtmls = pluginHtmls; }

   public List<Tag> getTagList() {
     return this.tagList;
   }

   public void setTagList(List<Tag> tagList) { this.tagList = tagList; }

   public String getIs_other() {
     return this.is_other;
   }

   public void setIs_other(String is_other) { this.is_other = is_other; }

   public Integer getStype() {
     return this.stype;
   }

   public void setStype(Integer stype) { this.stype = stype; }

   public String getKeyword() {
     return this.keyword;
   }

   public void setKeyword(String keyword) { this.keyword = keyword; }

   public Map getGoodsMap() {
     return this.goodsMap;
   }

   public void setGoodsMap(Map goodsMap) { this.goodsMap = goodsMap; }

   public String getOptype() {
     return this.optype;
   }

   public void setOptype(String optype) { this.optype = optype; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\GoodsAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */