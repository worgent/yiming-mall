 package com.enation.app.b2b2c.core.action.backend;

 import com.enation.app.b2b2c.core.model.groupbuy.GroupBuyArea;
 import com.enation.app.b2b2c.core.service.groupbuy.IGroupBuyAreaManager;
 import com.enation.framework.action.WWAction;
 import java.util.List;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
















 @ParentPackage("eop_default")
 @Namespace("/b2b2c/admin")
 @Results({@org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/b2b2c/admin/groupbuy/area_list.html"), @org.apache.struts2.convention.annotation.Result(name="add", type="freemarker", location="/b2b2c/admin/groupbuy/area_add.html"), @org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/b2b2c/admin/groupbuy/area_edit.html")})
 @Action("groupBuyArea")
 public class GroupBuyAreaAction
   extends WWAction
 {
   private IGroupBuyAreaManager groupBuyAreaManager;
   private String area_name;
   private String area_path;
   private int area_order;
   private Integer[] area_id;
   private GroupBuyArea groupBuyArea;
   private List catList;

   public String list()
   {
     return "list";
   }

   public String list_json()
   {
     this.webpage = this.groupBuyAreaManager.list(getPage(), getPageSize());
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
       GroupBuyArea groupBuyArea = new GroupBuyArea();
       groupBuyArea.setArea_name(this.area_name);
       groupBuyArea.setArea_order(this.area_order);
       this.groupBuyAreaManager.add(groupBuyArea);
       showSuccessJson("添加成功");
     } catch (Exception e) {
       showErrorJson("添加失败" + e.getMessage());
       this.logger.error("添加失败", e);
     }

     return "json_message";
   }

   public String edit() {
     this.groupBuyArea = this.groupBuyAreaManager.get(this.area_id[0].intValue());
     return "edit";
   }

   public String saveEdit()
   {
     try
     {
       GroupBuyArea groupBuyArea = new GroupBuyArea();
       groupBuyArea.setArea_id(this.area_id[0].intValue());
       groupBuyArea.setArea_name(this.area_name);
       groupBuyArea.setArea_order(this.area_order);
       this.groupBuyAreaManager.update(groupBuyArea);
       showSuccessJson("修改成功");
     } catch (Exception e) {
       showErrorJson("修改失败" + e.getMessage());
       this.logger.error("修改失败", e);
     }

     return "json_message";
   }

   public String batchDelete() {
     try {
       this.groupBuyAreaManager.delete(this.area_id);
       showSuccessJson("删除改成功");
     } catch (Exception e) {
       showErrorJson("删除失败" + e.getMessage());
       this.logger.error("删除失败", e);
     }
     return "json_message";
   }

   public IGroupBuyAreaManager getGroupBuyAreaManager()
   {
     return this.groupBuyAreaManager;
   }

   public void setGroupBuyAreaManager(IGroupBuyAreaManager groupBuyAreaManager)
   {
     this.groupBuyAreaManager = groupBuyAreaManager;
   }

   public String getArea_name()
   {
     return this.area_name;
   }

   public void setArea_name(String area_name)
   {
     this.area_name = area_name;
   }

   public String getArea_path()
   {
     return this.area_path;
   }

   public void setArea_path(String area_path)
   {
     this.area_path = area_path;
   }

   public int getArea_order()
   {
     return this.area_order;
   }

   public void setArea_order(int area_order)
   {
     this.area_order = area_order;
   }

   public Integer[] getArea_id()
   {
     return this.area_id;
   }

   public void setArea_id(Integer[] area_id)
   {
     this.area_id = area_id;
   }

   public GroupBuyArea getGroupBuyArea()
   {
     return this.groupBuyArea;
   }

   public void setGroupBuyArea(GroupBuyArea groupBuyArea)
   {
     this.groupBuyArea = groupBuyArea;
   }

   public List getCatList()
   {
     return this.catList;
   }

   public void setCatList(List catList)
   {
     this.catList = catList;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\backend\GroupBuyAreaAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */