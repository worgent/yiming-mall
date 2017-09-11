 package com.enation.app.b2b2c.core.action.api.goods;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.model.store.StoreCat;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsCatManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.util.JsonMessageUtil;
 import java.util.List;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.stereotype.Component;













 @Component
 @ParentPackage("eop_default")
 @Namespace("/api/b2b2c")
 @Action("goodsCat")
 @Results({@org.apache.struts2.convention.annotation.Result(name="cat_edit", type="freemarker", location="/themes/default/b2b2c/goods/goods_cat_edit.html")})
 public class GoodsCatApiAction
   extends WWAction
 {
   private IStoreMemberManager storeMemberManager;
   private IStoreGoodsCatManager storeGoodsCatManager;
   private Integer cat_id;
   private String store_cat_name;
   private Integer store_cat_pid;
   private Integer store_sort;
   private Integer disable;
   private String catids;
   private String catnames;
   private String cat_name;
   private IGoodsCatManager goodsCatManager;

   public String getStoreGoodsChildJson()
   {
     try
     {
       List list = this.goodsCatManager.listChildren(this.cat_id);
       this.json = JsonMessageUtil.getListJson(list);
     }
     catch (Exception e) {
       showErrorJson("加载出错");
     }
     return "json_message";
   }










   public String addGoodsCat()
   {
     StoreCat storeCat = new StoreCat();
     StoreMember storeMember = this.storeMemberManager.getStoreMember();
     try {
       storeCat.setStore_cat_name(this.store_cat_name);
       storeCat.setStore_cat_pid(this.store_cat_pid);
       storeCat.setSort(this.store_sort);
       storeCat.setDisable(this.disable);
       storeCat.setStore_id(storeMember.getStore_id());

       this.storeGoodsCatManager.addStoreCat(storeCat);
       showSuccessJson("保存成功");
     } catch (Exception e) {
       showErrorJson("保存失败");
     }
     return "json_message";
   }




   public String edit()
   {
     return "cat_edit";
   }











   public String editGoodsCat()
   {
     StoreCat storeCat = new StoreCat();
     StoreMember storeMember = this.storeMemberManager.getStoreMember();

     try
     {
       int pid = this.storeGoodsCatManager.is_children(this.cat_id).intValue();

       if ((pid == 0) && (this.store_cat_pid.intValue() != pid)) {
         showErrorJson("顶级分类不可修改上级分类");
         return "json_message";
       }

       storeCat.setStore_cat_name(this.store_cat_name);
       storeCat.setStore_cat_pid(this.store_cat_pid);
       storeCat.setSort(this.store_sort);
       storeCat.setDisable(this.disable);
       storeCat.setStore_cat_id(this.cat_id);

       this.storeGoodsCatManager.editStoreCat(storeCat);
       showSuccessJson("保存成功");
     }
     catch (Exception e) {
       showErrorJson("保存失败");
     }

     return "json_message";
   }






   public String delete()
   {
     try
     {
       this.storeGoodsCatManager.deleteStoreCat(this.cat_id);
       showSuccessJson("删除成功");
     } catch (RuntimeException e) {
       String str_message = e.getMessage().replaceAll("\\*", "【" + this.cat_name + "】");
       showErrorJson(str_message);
     } catch (Exception e) {
       showErrorJson("删除失败");
     }

     return "json_message";
   }






   public String delAll()
   {
     String catname = null;
     try {
       String[] str_catid = this.catids.split(",");
       String[] str_catname = this.catnames.split(",");
       for (int i = 0; i < str_catid.length; i++) {
         String catid = str_catid[i];
         catname = str_catname[i];
         this.storeGoodsCatManager.deleteStoreCat(Integer.valueOf(Integer.parseInt(catid)));
       }
       showSuccessJson("删除成功");
     } catch (RuntimeException e) {
       String str_message = e.getMessage().replaceAll("\\*", "【" + catname + "】");
       showErrorJson(str_message);
     } catch (Exception e) {
       showErrorJson("删除失败");
     }
     return "json_message";
   }

   public IStoreGoodsCatManager getStoreGoodsCatManager()
   {
     return this.storeGoodsCatManager;
   }

   public void setStoreGoodsCatManager(IStoreGoodsCatManager storeGoodsCatManager) {
     this.storeGoodsCatManager = storeGoodsCatManager;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }

   public Integer getCat_id() { return this.cat_id; }

   public void setCat_id(Integer cat_id) {
     this.cat_id = cat_id;
   }

   public String getStore_cat_name() {
     return this.store_cat_name;
   }

   public void setStore_cat_name(String store_cat_name) {
     this.store_cat_name = store_cat_name;
   }

   public Integer getStore_cat_pid() {
     return this.store_cat_pid;
   }

   public void setStore_cat_pid(Integer store_cat_pid) {
     this.store_cat_pid = store_cat_pid;
   }

   public Integer getDisable() {
     return this.disable;
   }

   public void setDisable(Integer disable) {
     this.disable = disable;
   }

   public Integer getStore_sort() {
     return this.store_sort;
   }

   public void setStore_sort(Integer store_sort) {
     this.store_sort = store_sort;
   }

   public IGoodsCatManager getGoodsCatManager() { return this.goodsCatManager; }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
     this.goodsCatManager = goodsCatManager;
   }

   public String getCatids() { return this.catids; }

   public void setCatids(String catids) {
     this.catids = catids;
   }

   public String getCatnames() {
     return this.catnames;
   }

   public void setCatnames(String catnames) { this.catnames = catnames; }

   public String getCat_name() {
     return this.cat_name;
   }

   public void setCat_name(String cat_name) { this.cat_name = cat_name; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\goods\GoodsCatApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */