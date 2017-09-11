 package com.enation.app.b2b2c.core.action.backend;

 import com.enation.app.b2b2c.core.model.groupbuy.GroupBuyCat;
 import com.enation.app.b2b2c.core.service.groupbuy.IGroupBuyCatManager;
 import com.enation.framework.action.WWAction;
 import java.util.List;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;












 @ParentPackage("eop_default")
 @Namespace("/b2b2c/admin")
 @Results({@org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/b2b2c/admin/groupbuy/cat_list.html"), @org.apache.struts2.convention.annotation.Result(name="add", type="freemarker", location="/b2b2c/admin/groupbuy/cat_add.html"), @org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/b2b2c/admin/groupbuy/cat_edit.html")})
 @Action("groupBuyCat")
 public class GroupBuyCatAction
   extends WWAction
 {
   private IGroupBuyCatManager groupBuyCatManager;
   private String cat_name;
   private String cat_path;
   private int cat_order;
   private Integer[] catid;
   private GroupBuyCat groupBuyCat;
   private List catList;

   public String list()
   {
     return "list";
   }

   public String list_json()
   {
     this.webpage = this.groupBuyCatManager.list(getPage(), getPageSize());
     showGridJson(this.webpage);
     return "json_message";
   }


   public String add()
   {
     return "add";
   }

   public String saveAdd()
   {
     try
     {
       GroupBuyCat groupBuyCat = new GroupBuyCat();
       groupBuyCat.setCat_name(this.cat_name);
       groupBuyCat.setCat_order(this.cat_order);
       this.groupBuyCatManager.add(groupBuyCat);
       showSuccessJson("添加成功");
     } catch (Exception e) {
       showErrorJson("添加失败" + e.getMessage());
       this.logger.error("添加失败", e);
     }

     return "json_message";
   }

   public String edit() {
     this.groupBuyCat = this.groupBuyCatManager.get(this.catid[0].intValue());
     return "edit";
   }

   public String saveEdit()
   {
     try
     {
       GroupBuyCat groupBuyCat = new GroupBuyCat();
       groupBuyCat.setCatid(this.catid[0].intValue());
       groupBuyCat.setCat_name(this.cat_name);
       groupBuyCat.setCat_order(this.cat_order);
       this.groupBuyCatManager.update(groupBuyCat);
       showSuccessJson("修改成功");
     } catch (Exception e) {
       showErrorJson("修改失败" + e.getMessage());
       this.logger.error("修改失败", e);
     }

     return "json_message";
   }

   public String batchDelete() {
     try {
       this.groupBuyCatManager.delete(this.catid);
       showSuccessJson("删除改成功");
     } catch (Exception e) {
       showErrorJson("删除失败" + e.getMessage());
       this.logger.error("删除失败", e);
     }
     return "json_message";
   }

   public IGroupBuyCatManager getGroupBuyCatManager()
   {
     return this.groupBuyCatManager;
   }

   public void setGroupBuyCatManager(IGroupBuyCatManager groupBuyCatManager)
   {
     this.groupBuyCatManager = groupBuyCatManager;
   }

   public List getCatList()
   {
     return this.catList;
   }

   public void setCatList(List catList)
   {
     this.catList = catList;
   }

   public String getCat_name()
   {
     return this.cat_name;
   }

   public void setCat_name(String cat_name)
   {
     this.cat_name = cat_name;
   }

   public String getCat_path()
   {
     return this.cat_path;
   }

   public void setCat_path(String cat_path)
   {
     this.cat_path = cat_path;
   }

   public int getCat_order()
   {
     return this.cat_order;
   }

   public void setCat_order(int cat_order)
   {
     this.cat_order = cat_order;
   }

   public GroupBuyCat getGroupBuyCat()
   {
     return this.groupBuyCat;
   }

   public void setGroupBuyCat(GroupBuyCat groupBuyCat)
   {
     this.groupBuyCat = groupBuyCat;
   }

   public Integer[] getCatid()
   {
     return this.catid;
   }

   public void setCatid(Integer[] catid)
   {
     this.catid = catid;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\backend\GroupBuyCatAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */