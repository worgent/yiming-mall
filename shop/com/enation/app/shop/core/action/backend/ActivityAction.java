 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.core.model.PromotionActivity;
 import com.enation.app.shop.core.service.IPromotionActivityManager;
 import com.enation.framework.action.WWAction;
 import java.util.Date;
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
 @Action("activity")
 @Results({@org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/shop/admin/activities/activity_list.jsp"), @org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/shop/admin/activities/activity_edit.jsp"), @org.apache.struts2.convention.annotation.Result(name="add", type="freemarker", location="/shop/admin/activities/activity_add.jsp")})
 public class ActivityAction
   extends WWAction
 {
   private Date begin_time;
   private Date end_time;
   private Integer[] id;
   private PromotionActivity activity;
   private IPromotionActivityManager promotionActivityManager;
   private int activity_id;

   public String list()
   {
     this.webpage = this.promotionActivityManager.list(getPage(), getPageSize());
     return "list";
   }

   public String add() {
     return "add";
   }

   public String edit() {
     this.activity = this.promotionActivityManager.get(Integer.valueOf(this.activity_id));
     return "edit";
   }

   public String saveAdd() {
     this.activity.setBegin_time(Long.valueOf(this.begin_time.getTime()));
     this.activity.setEnd_time(Long.valueOf(this.end_time.getTime()));
     try {
       this.promotionActivityManager.add(this.activity);
       this.msgs.add("活动添加成功");
     } catch (Exception e) {
       this.msgs.add("活动添加失败");
       e.printStackTrace();
     }
     this.urls.put("促销活动列表", "activity!list.do");
     return "message";
   }

   public String saveEdit() {
     this.activity.setBegin_time(Long.valueOf(this.begin_time.getTime()));
     this.activity.setEnd_time(Long.valueOf(this.end_time.getTime()));
     try {
       this.promotionActivityManager.edit(this.activity);
       this.msgs.add("活动修改成功");
     } catch (Exception e) {
       this.msgs.add("活动修改失败");
       e.printStackTrace();
     }
     this.urls.put("促销活动列表", "activity!list.do");
     return "message";
   }

   public String delete() {
     try {
       this.promotionActivityManager.delete(this.id);
       this.json = "{'result':0,'message':'删除成功'}";
     } catch (Exception e) {
       this.json = "{'result':1;'message':'删除失败'}";
       e.printStackTrace();
     }
     return "json_message";
   }

   public PromotionActivity getActivity() {
     return this.activity;
   }

   public void setActivity(PromotionActivity activity) {
     this.activity = activity;
   }

   public IPromotionActivityManager getPromotionActivityManager() {
     return this.promotionActivityManager;
   }

   public void setPromotionActivityManager(IPromotionActivityManager promotionActivityManager)
   {
     this.promotionActivityManager = promotionActivityManager;
   }

   public Date getBegin_time() {
     return this.begin_time;
   }

   public void setBegin_time(Date begin_time) {
     this.begin_time = begin_time;
   }

   public Date getEnd_time() {
     return this.end_time;
   }

   public void setEnd_time(Date end_time) {
     this.end_time = end_time;
   }

   public Integer[] getId() {
     return this.id;
   }

   public void setId(Integer[] id) {
     this.id = id;
   }

   public int getActivity_id() {
     return this.activity_id;
   }

   public void setActivity_id(int activityId) {
     this.activity_id = activityId;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\ActivityAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */