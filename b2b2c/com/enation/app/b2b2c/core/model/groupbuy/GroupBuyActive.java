 package com.enation.app.b2b2c.core.model.groupbuy;

 import com.enation.framework.database.NotDbField;
 import com.enation.framework.database.PrimaryKeyField;
 import com.enation.framework.util.DateUtil;





 public class GroupBuyActive
 {
   private int act_id;
   private String act_name;
   private long start_time;
   private long end_time;
   private long join_end_time;
   private int act_status;
   private long add_time;

   @PrimaryKeyField
   public int getAct_id()
   {
     return this.act_id;
   }

   public void setAct_id(int act_id) { this.act_id = act_id; }

   public String getAct_name() {
     return this.act_name;
   }

   public void setAct_name(String act_name) { this.act_name = act_name; }


   public long getStart_time()
   {
     return this.start_time;
   }





   @NotDbField
   public String getStart_time_str()
   {
     return DateUtil.toString(Long.valueOf(this.start_time), "yyyy-MM-dd");
   }

   public void setStart_time(long start_time) {
     this.start_time = start_time;
   }

   public long getEnd_time() {
     return this.end_time;
   }




   @NotDbField
   public String getEnd_time_str()
   {
     return DateUtil.toString(Long.valueOf(this.end_time), "yyyy-MM-dd");
   }

   public void setEnd_time(long end_time)
   {
     this.end_time = end_time;
   }

   public long getJoin_end_time() {
     return this.join_end_time;
   }




   @NotDbField
   public String getJoin_end_time_str()
   {
     return DateUtil.toString(Long.valueOf(this.join_end_time), "yyyy-MM-dd");
   }

   public void setJoin_end_time(long join_end_time)
   {
     this.join_end_time = join_end_time;
   }

   public int getAct_status() { return this.act_status; }

   public void setAct_status(int act_status) {
     this.act_status = act_status;
   }

   public long getAdd_time() {
     return this.add_time;
   }



   @NotDbField
   public String getAdd_time_str()
   {
     return DateUtil.toString(Long.valueOf(this.add_time), "yyyy-MM-dd");
   }

   public void setAdd_time(long add_time) {
     this.add_time = add_time;
   }

   @NotDbField
   public String getAct_status_text()
   {
     return this.act_status == 0 ? "未审核" : "已审核";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\groupbuy\GroupBuyActive.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */