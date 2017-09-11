 package com.enation.app.base.core.action;

 import com.enation.app.base.core.model.Smtp;
 import com.enation.app.base.core.service.ISmtpManager;
 import com.enation.framework.action.WWAction;
 import java.util.List;
 import org.apache.log4j.Logger;







 public class SmtpAction
   extends WWAction
 {
   private ISmtpManager smtpManager;
   private Smtp smtp;
   private Integer[] id;
   private int smtpId;
   private int isedit;
   private List<Smtp> smtpList;

   public String add()
   {
     this.isedit = 0;
     return "add";
   }

   public String edit() {
     this.isedit = 1;
     this.smtp = this.smtpManager.get(this.smtpId);
     return "edit";
   }

   public String saveAdd() {
     try { this.smtpManager.add(this.smtp);
       showSuccessJson("smtp添加成功");
     } catch (RuntimeException e) {
       showErrorJson("smtp添加失败");
       this.logger.error("smtp添加失败", e);
     }
     return "json_message";
   }

   public String saveEdit()
   {
     try {
       this.smtpManager.edit(this.smtp);
       showSuccessJson("smtp修改成功");
     } catch (RuntimeException e) {
       showErrorJson("smtp修改失败");
       this.logger.error("smtp修改失败", e);
     }
     return "json_message";
   }

   public String list()
   {
     this.smtpList = this.smtpManager.list();
     return "list";
   }



   public String listJson()
   {
     this.smtpList = this.smtpManager.list();
     showGridJson(this.smtpList);
     return "json_message";
   }

   public String delete() {
     try {
       this.smtpManager.delete(this.id);
       showSuccessJson("smtp删除成功");
     } catch (RuntimeException e) {
       showErrorJson("smtp删除失败");
       this.logger.error("smtp删除失败", e);
     }
     return "json_message";
   }

   public ISmtpManager getSmtpManager() {
     return this.smtpManager;
   }

   public void setSmtpManager(ISmtpManager smtpManager) {
     this.smtpManager = smtpManager;
   }

   public Smtp getSmtp() {
     return this.smtp;
   }

   public void setSmtp(Smtp smtp) {
     this.smtp = smtp;
   }

   public Integer[] getId() { return this.id; }

   public void setId(Integer[] id)
   {
     this.id = id;
   }

   public int getSmtpId()
   {
     return this.smtpId;
   }

   public void setSmtpId(int smtpId) {
     this.smtpId = smtpId;
   }

   public int getIsedit() {
     return this.isedit;
   }

   public void setIsedit(int isedit) {
     this.isedit = isedit;
   }

   public List<Smtp> getSmtpList() {
     return this.smtpList;
   }

   public void setSmtpList(List<Smtp> smtpList) {
     this.smtpList = smtpList;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\SmtpAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */