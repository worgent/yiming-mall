 package com.enation.app.b2b2c.core.action.backend;

 import com.enation.app.b2b2c.core.model.groupbuy.GroupBuyActive;
 import com.enation.app.b2b2c.core.service.groupbuy.IGroupBuyActiveManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.util.DateUtil;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;









 @ParentPackage("eop_default")
 @Namespace("/b2b2c/admin")
 @Results({@org.apache.struts2.convention.annotation.Result(name="listJson", type="freemarker", location="/b2b2c/admin/groupbuy/act_list.html"), @org.apache.struts2.convention.annotation.Result(name="add", type="freemarker", location="/b2b2c/admin/groupbuy/act_add.html")})
 @Action("groupBuyAct")
 public class GroupBuyActAction
   extends WWAction
 {
   private IGroupBuyActiveManager groupBuyActiveManager;
   private Integer[] act_id;
   private String act_name;
   private String start_time;
   private String end_time;
   private String join_end_time;

   public String list()
   {
     return "listJson";
   }


   public String add()
   {
     return "add";
   }


   public String saveAdd()
   {
     try
     {
       GroupBuyActive groupBuyActive = new GroupBuyActive();
       groupBuyActive.setAct_name(this.act_name);
       groupBuyActive.setStart_time(DateUtil.getDatelineLong(this.start_time));
       groupBuyActive.setEnd_time(DateUtil.getDatelineLong(this.end_time));
       groupBuyActive.setJoin_end_time(DateUtil.getDatelineLong(this.join_end_time));
       this.groupBuyActiveManager.add(groupBuyActive);
       showSuccessJson("添加活动成功");
     } catch (Exception e) {
       showErrorJson("添加活动失败" + e.getMessage());
       this.logger.error("添加活动失败", e);
     }

     return "json_message";
   }


   public String listJson()
   {
     this.webpage = this.groupBuyActiveManager.list(getPage(), getPageSize());
     showGridJson(this.webpage);
     return "json_message";
   }




   public String delete()
   {
     try
     {
       this.groupBuyActiveManager.delete(this.act_id[0].intValue());
       showSuccessJson("删除成功");
     } catch (Exception e) {
       showErrorJson("删除失败");
     }


     return "json_message";
   }






   public String batchDelete()
   {
     try
     {
       this.groupBuyActiveManager.delete(this.act_id);
       showSuccessJson("删除成功");
     } catch (Exception e) {
       showErrorJson("删除失败");
     }


     return "json_message";
   }

   public IGroupBuyActiveManager getGroupBuyActiveManager()
   {
     return this.groupBuyActiveManager;
   }

   public void setGroupBuyActiveManager(IGroupBuyActiveManager groupBuyActiveManager)
   {
     this.groupBuyActiveManager = groupBuyActiveManager;
   }

   public String getAct_name()
   {
     return this.act_name;
   }

   public void setAct_name(String act_name)
   {
     this.act_name = act_name;
   }

   public String getStart_time()
   {
     return this.start_time;
   }

   public void setStart_time(String start_time)
   {
     this.start_time = start_time;
   }

   public String getEnd_time()
   {
     return this.end_time;
   }

   public void setEnd_time(String end_time)
   {
     this.end_time = end_time;
   }

   public String getJoin_end_time()
   {
     return this.join_end_time;
   }

   public void setJoin_end_time(String join_end_time)
   {
     this.join_end_time = join_end_time;
   }

   public Integer[] getAct_id()
   {
     return this.act_id;
   }

   public void setAct_id(Integer[] act_id)
   {
     this.act_id = act_id;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\backend\GroupBuyActAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */