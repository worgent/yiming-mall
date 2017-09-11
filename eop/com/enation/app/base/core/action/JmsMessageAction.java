 package com.enation.app.base.core.action;

 import com.enation.framework.action.WWAction;
 import com.enation.framework.jms.EopProducer;
 import com.enation.framework.jms.IEopJmsMessage;
 import com.enation.framework.jms.ITaskView;
 import com.enation.framework.jms.TaskContainer;
 import com.enation.framework.jms.TaskView;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.List;
 import net.sf.json.JSONArray;
 import org.apache.log4j.Logger;








 public class JmsMessageAction
   extends WWAction
 {
   private EopProducer eopProducer;
   private String taskid;

   public String list()
   {
     try
     {
       Collection<ITaskView> taskList = TaskContainer.listTask();
       List<ITaskView> tempList = new ArrayList();
       for (ITaskView task : taskList) {
         tempList.add(new TaskView(task));
       }
       String listStr = JSONArray.fromObject(tempList).toString();
       this.json = ("{\"result\":1,\"data\":" + listStr + "}");
     } catch (RuntimeException e) {
       e.printStackTrace();
       this.logger.error("读取jms消息出错", e);
       showErrorJson("读取Jms消息出错:[" + e.getMessage() + "]");
     }
     return "json_message";
   }

   public String remove() {
     try {
       TaskContainer.removeTask(this.taskid);
       showSuccessJson("移除任务成功");
     } catch (RuntimeException e) {
       this.logger.error("移除任务出错", e);
       showErrorJson("移除任务出错[" + e.getMessage() + "]");
     }
     return "json_message";
   }

   public String retry()
   {
     try {
       ITaskView taskView = TaskContainer.getTask(this.taskid);
       if ((taskView instanceof IEopJmsMessage)) {
         IEopJmsMessage message = (IEopJmsMessage)taskView;
         this.eopProducer.send(message);
       }
       showSuccessJson("任务[" + this.taskid + "]下达成功!");
     } catch (Exception e) {
       this.logger.error("下达任务失败", e);
       showErrorJson(e.getMessage());
     }

     return "json_message";
   }





   public String getTaskid()
   {
     return this.taskid;
   }

   public void setTaskid(String taskid) {
     this.taskid = taskid;
   }

   public EopProducer getEopProducer() {
     return this.eopProducer;
   }

   public void setEopProducer(EopProducer eopProducer) {
     this.eopProducer = eopProducer;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\JmsMessageAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */