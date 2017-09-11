 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.core.model.Logi;
 import com.enation.app.shop.core.service.ILogiManager;
 import com.enation.framework.action.WWAction;
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
 @Action("logi")
 @Results({@org.apache.struts2.convention.annotation.Result(name="add_logi", type="freemarker", location="/shop/admin/setting/logi_add.html"), @org.apache.struts2.convention.annotation.Result(name="edit_logi", type="freemarker", location="/shop/admin/setting/logi_edit.html"), @org.apache.struts2.convention.annotation.Result(name="list_logi", type="freemarker", location="/shop/admin/setting/logi_list.html")})
 public class LogiAction
   extends WWAction
 {
   private ILogiManager logiManager;
   private String name;
   private Integer cid;
   private Integer[] id;
   private String order;
   private Logi logi;
   private String code;

   public String add_logi()
   {
     return "add_logi";
   }

   public String edit_logi() {
     this.logi = this.logiManager.getLogiById(this.cid);
     return "edit_logi";
   }

   public String list_logi() {
     this.webpage = this.logiManager.pageLogi(this.order, Integer.valueOf(getPage()), Integer.valueOf(getPageSize()));
     return "list_logi";
   }



   public String list_logiJson()
   {
     this.webpage = this.logiManager.pageLogi(this.order, Integer.valueOf(getPage()), Integer.valueOf(getPageSize()));
     showGridJson(this.webpage);
     return "json_message";
   }

   public String delete() {
     try {
       this.logiManager.delete(this.id);
       showSuccessJson("删除成功");
     } catch (RuntimeException e) {
       showErrorJson("快递公司删除失败");
       this.logger.error("物流公司删除失败", e);
     }
     return "json_message";
   }

   public String saveAdd() {
     try {
       Logi logi = new Logi();
       logi.setCode(this.code);
       logi.setName(this.name);
       this.logiManager.saveAdd(logi);
       showSuccessJson("添加成功");
     }
     catch (Exception e) {
       e.printStackTrace();
       showErrorJson("物流公司添加失败");
       this.logger.error("物流公司添加失败", e);
     }
     return "json_message";
   }

   public String saveEdit() {
     try {
       Logi logi = new Logi();
       logi.setId(this.cid);
       logi.setCode(this.code);
       logi.setName(this.name);
       this.logiManager.saveEdit(logi);
       showSuccessJson("修改成功");
     }
     catch (Exception e) {
       e.printStackTrace();
       showErrorJson("物流公司修改失败");
       this.logger.error("物流公司修改失败", e);
     }
     return "json_message";
   }

   public String getName() { return this.name; }

   public void setName(String name)
   {
     this.name = name;
   }

   public ILogiManager getLogiManager() {
     return this.logiManager;
   }

   public void setLogiManager(ILogiManager logiManager) {
     this.logiManager = logiManager;
   }

   public Integer getCid() {
     return this.cid;
   }

   public void setCid(Integer cid) { this.cid = cid; }


   public Integer[] getId()
   {
     return this.id;
   }

   public void setId(Integer[] id) {
     this.id = id;
   }

   public String getOrder() {
     return this.order;
   }

   public void setOrder(String order) {
     this.order = order;
   }

   public Logi getLogi() {
     return this.logi;
   }

   public void setLogi(Logi logi) {
     this.logi = logi;
   }

   public String getCode() {
     return this.code;
   }

   public void setCode(String code) {
     this.code = code;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\LogiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */