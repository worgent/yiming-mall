 package com.enation.app.base.core.service.impl;

 import com.enation.app.base.core.plugin.job.JobExecutePluginsBundle;
 import com.enation.app.base.core.service.IJobExecuter;




 public class JobExecuter
   implements IJobExecuter
 {
   private JobExecutePluginsBundle jobExecutePluginsBundle;

   public void everyHour()
   {
     this.jobExecutePluginsBundle.everyHourExcecute();
   }

   public void everyDay()
   {
     try {
       this.jobExecutePluginsBundle.everyDayExcecute();
     }
     catch (Exception e) {
       e.printStackTrace();
     }
   }

   public void everyMonth()
   {
     this.jobExecutePluginsBundle.everyMonthExcecute();
   }

   public JobExecutePluginsBundle getJobExecutePluginsBundle() {
     return this.jobExecutePluginsBundle;
   }

   public void setJobExecutePluginsBundle(JobExecutePluginsBundle jobExecutePluginsBundle)
   {
     this.jobExecutePluginsBundle = jobExecutePluginsBundle;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\impl\JobExecuter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */