 package com.enation.app.b2b2c.core.action.backend;

 import com.enation.app.b2b2c.core.service.groupbuy.IGroupBuyManager;
 import com.enation.framework.action.WWAction;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;










 @ParentPackage("eop_default")
 @Namespace("/b2b2c/admin")
 @Results({@org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/b2b2c/admin/groupbuy/groupbuy_list.html")})
 @Action("groupBuy")
 public class GroupBuyAction
   extends WWAction
 {
   private int actid;
   private int gbid;
   private Integer status;
   private IGroupBuyManager groupBuyManager;

   public String list()
   {
     return "list";
   }






   public String listJson()
   {
     try
     {
       this.webpage = this.groupBuyManager.listByActId(getPage(), getPageSize(), this.actid, this.status);
       showGridJson(this.webpage);
     }
     catch (Exception e) {
       this.logger.error("查询出错", e);
       showErrorJson("查询出错");
     }

     return "json_message";
   }


   public String auth()
   {
     try
     {
       this.groupBuyManager.auth(this.gbid, this.status.intValue());
       showSuccessJson("操作成功");
     } catch (Exception e) {
       this.logger.error("审核操作失败", e);
       showErrorJson("审核操作失败" + e.getMessage());
     }



     return "json_message";
   }

   public IGroupBuyManager getGroupBuyManager()
   {
     return this.groupBuyManager;
   }

   public void setGroupBuyManager(IGroupBuyManager groupBuyManager) {
     this.groupBuyManager = groupBuyManager;
   }


   public int getActid()
   {
     return this.actid;
   }


   public void setActid(int actid)
   {
     this.actid = actid;
   }


   public Integer getStatus()
   {
     return this.status;
   }


   public void setStatus(Integer status)
   {
     this.status = status;
   }


   public int getGbid()
   {
     return this.gbid;
   }


   public void setGbid(int gbid)
   {
     this.gbid = gbid;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\backend\GroupBuyAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */