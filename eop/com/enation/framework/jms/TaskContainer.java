 package com.enation.framework.jms;

 import java.util.Collection;
 import java.util.HashMap;
 import java.util.Map;




 public class TaskContainer
 {
   private static Map<String, ITaskView> taskMap = new HashMap();

   public static void pushTask(ITaskView taskView)
   {
     taskMap.put(taskView.getTaskId(), taskView);
   }

   public static ITaskView getTask(String taskid)
   {
     return (ITaskView)taskMap.get(taskid);
   }

   public static void removeTask(String taskid)
   {
     taskMap.remove(taskid);
   }


   public static Collection<ITaskView> listTask()
   {
     return taskMap.values();
   }

   public static void clear()
   {
     taskMap.clear();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\jms\TaskContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */