 package com.enation.app.base.core.action;

 import com.enation.app.base.core.service.IAccessRecorder;
 import com.enation.eop.resource.model.Link;
 import com.enation.eop.sdk.context.EopContext;
 import java.io.File;
 import java.util.Calendar;
 import java.util.List;
 import java.util.Map;

 public class AccessAction extends com.enation.framework.action.WWAction
 {
   private IAccessRecorder accessRecorder;
   private int startday;
   private int endday;
   private String runmodel;
   private Map accessData;
   private String daytime;
   private String ip;
   private List accessList;
   private List<Link> linkList;

   public String list()
   {
     Calendar cal = Calendar.getInstance();
     cal.setTime(new java.util.Date());
     int year = cal.get(1);
     int month = cal.get(2) + 1;

     String starttime = null;
     String endtime = null;

     if (this.startday != 0) {
       starttime = year + "-" + month + "-" + this.startday;
     }
     if (this.endday != 0) {
       endtime = year + "-" + month + "-" + this.endday;
     }
     this.webpage = this.accessRecorder.list(starttime, endtime, getPage(), 50);


     this.accessData = this.accessRecorder.census();
     this.runmodel = com.enation.eop.sdk.context.EopSetting.RUNMODE;
     return "list";
   }












   public String detaillist()
   {
     this.accessList = this.accessRecorder.detaillist(this.ip, this.daytime);
     this.runmodel = com.enation.eop.sdk.context.EopSetting.RUNMODE;
     return "detaillist";
   }



   public String history()
   {
     this.linkList = new java.util.ArrayList();
     String target = com.enation.eop.sdk.context.EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/access";

     File file = new File(target);
     if (file.exists()) {
       String[] reportList = file.list();
       for (String name : reportList) {
         Link link = new Link();
         link.setLink(com.enation.eop.sdk.context.EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath() + "/access/" + name);


         link.setText(name);
         this.linkList.add(link);
       }
     }
     return "history";
   }

   public IAccessRecorder getAccessRecorder() {
     return this.accessRecorder;
   }

   public void setAccessRecorder(IAccessRecorder accessRecorder) {
     this.accessRecorder = accessRecorder;
   }

   public int getStartday() {
     return this.startday;
   }

   public void setStartday(int startday) {
     this.startday = startday;
   }

   public int getEndday() {
     return this.endday;
   }

   public void setEndday(int endday) {
     this.endday = endday;
   }

   public List<Link> getLinkList() {
     return this.linkList;
   }

   public void setLinkList(List<Link> linkList) {
     this.linkList = linkList;
   }

   public String getDaytime() {
     return this.daytime;
   }

   public void setDaytime(String daytime) {
     this.daytime = daytime;
   }

   public String getIp() {
     return this.ip;
   }

   public void setIp(String ip) {
     this.ip = ip;
   }

   public List getAccessList() {
     return this.accessList;
   }

   public void setAccessList(List accessList) {
     this.accessList = accessList;
   }

   public String getRunmodel() {
     return this.runmodel;
   }

   public void setRunmodel(String runmodel) {
     this.runmodel = runmodel;
   }

   public Map getAccessData() {
     return this.accessData;
   }

   public void setAccessData(Map accessData) {
     this.accessData = accessData;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\AccessAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */