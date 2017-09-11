 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.core.model.Tag;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IGoodsTagManager;
 import com.enation.app.shop.core.service.ITagManager;
 import com.enation.eop.processor.httpcache.HttpCacheManager;
 import com.enation.framework.action.WWAction;
 import java.util.List;
 import java.util.Map;
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
 @Action("goodsShow")
 @Results({@org.apache.struts2.convention.annotation.Result(name="add", location="/shop/admin/goodsshow/add.jsp"), @org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/shop/admin/goodsshow/list.html"), @org.apache.struts2.convention.annotation.Result(name="taglist", type="freemarker", location="/shop/admin/goodsshow/taglist.html"), @org.apache.struts2.convention.annotation.Result(name="search_list", type="freemarker", location="/shop/admin/goodsshow/search_list.html")})
 public class GoodsShowAction
   extends WWAction
 {
   protected String name;
   protected String sn;
   protected String order;
   private Integer catid;
   protected Integer[] goods_id;
   private Integer[] tagids;
   private Integer[] ordernum;
   protected Integer market_enable = new Integer(1);

   protected IGoodsManager goodsManager;

   protected ITagManager tagManager;

   private Tag tag;
   private int tagid;
   private int goodsid;
   private List<Tag> taglist;
   private IGoodsTagManager goodsTagManager;
   private Map goodsMap;
   private String optype = "no";



   public String taglist()
   {
     return "taglist";
   }





   public String taglistJson()
   {
     this.taglist = this.tagManager.list();
     showGridJson(this.taglist);
     return "json_message";
   }


   public String execute()
   {
     return "list";
   }






   public String listJson()
   {
     if ((this.catid == null) || (this.catid.intValue() == 0)) {
       this.webpage = this.goodsTagManager.getGoodsList(this.tagid, getPage(), getPageSize());
     } else {
       this.webpage = this.goodsTagManager.getGoodsList(this.tagid, this.catid.intValue(), getPage(), getPageSize());
     }
     showGridJson(this.webpage);
     return "json_message";
   }





   public String add()
   {
     this.tag = this.tagManager.getById(Integer.valueOf(this.tagid));
     return "add";
   }



   public String search()
   {
     return "search_list";
   }




   public String batchAdd()
   {
     try
     {
       if ((this.goods_id != null) && (this.goods_id.length > 0)) {
         for (Integer goodsId : this.goods_id) {
           this.goodsTagManager.addTag(this.tagid, goodsId.intValue());
         }
       }
       updateHttpCache();
       showSuccessJson("添加成功");
     } catch (RuntimeException e) {
       showErrorJson("添加失败");
     }
     return "json_message";
   }




   public String delete()
   {
     try
     {
       this.goodsTagManager.removeTag(this.tagid, this.goodsid);


       showSuccessJson("删除成功");
     } catch (RuntimeException e) {
       e.printStackTrace();
       showErrorJson("删除失败");
     }
     return "json_message";
   }




   public String saveOrdernum()
   {
     try
     {
       this.goodsTagManager.updateOrderNum(this.goods_id, this.tagids, this.ordernum);
       int tempCatId = this.catid == null ? 0 : this.catid.intValue();
       updateHttpCache();
       showSuccessJson("保存排序成功");
     } catch (RuntimeException e) {
       e.printStackTrace();
       showErrorJson("保存排序失败");
     }
     return "json_message";
   }

   private void updateHttpCache()
   {
     HttpCacheManager.updateUriModified("/");
     HttpCacheManager.updateUriModified("/index.html");
     HttpCacheManager.updateUriModified("/search-(.*).html");
   }

   public void setGoodsTagManager(IGoodsTagManager goodsTagManager) {
     this.goodsTagManager = goodsTagManager;
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

   public String getOrder() {
     return this.order;
   }

   public void setOrder(String order) {
     this.order = order;
   }

   public Integer getCatid() {
     return this.catid;
   }

   public void setCatid(Integer catid) {
     this.catid = catid;
   }

   public Integer[] getGoods_id() {
     return this.goods_id;
   }

   public void setGoods_id(Integer[] goods_id) { this.goods_id = goods_id; }

   public Integer[] getTagids() {
     return this.tagids;
   }

   public void setTagids(Integer[] tagids) {
     this.tagids = tagids;
   }

   public int getTagid() {
     return this.tagid;
   }

   public void setTagid(int tagid) {
     this.tagid = tagid;
   }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }

   public void setTagManager(ITagManager tagManager) {
     this.tagManager = tagManager;
   }

   public Tag getTag() {
     return this.tag;
   }

   public void setTag(Tag tag) {
     this.tag = tag;
   }

   public Integer[] getOrdernum() {
     return this.ordernum;
   }

   public void setOrdernum(Integer[] ordernum) {
     this.ordernum = ordernum;
   }

   public int getGoodsid() {
     return this.goodsid;
   }

   public void setGoodsid(int goodsid) {
     this.goodsid = goodsid;
   }

   public List<Tag> getTaglist() { return this.taglist; }

   public void setTaglist(List<Tag> taglist) {
     this.taglist = taglist;
   }

   public Map getGoodsMap() { return this.goodsMap; }

   public void setGoodsMap(Map goodsMap) {
     this.goodsMap = goodsMap;
   }

   public String getOptype() { return this.optype; }

   public void setOptype(String optype) {
     this.optype = optype;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\GoodsShowAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */