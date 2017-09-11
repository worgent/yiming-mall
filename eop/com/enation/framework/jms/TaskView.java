 package com.enation.framework.jms;


 public class TaskView
   implements ITaskView
 {
   private ITaskView task;


   public TaskView(ITaskView task)
   {
     this.task = task;
   }

   public String getTaskId() {
     return this.task.getTaskId();
   }

   public String getTaskName()
   {
     return this.task.getTaskName();
   }

   public int getState()
   {
     return this.task.getState();
   }




   public void setState(int state) {}



   public String getErrorMessage()
   {
     return this.task.getErrorMessage();
   }

   public void setErrorMessage(String errorMessage) {}
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\jms\TaskView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */