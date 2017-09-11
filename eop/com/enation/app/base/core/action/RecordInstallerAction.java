 package com.enation.app.base.core.action;

 import com.enation.app.base.core.service.solution.Installer;
 import com.enation.app.base.core.service.solution.InstallerManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.util.ip.IPSeeker;
 import java.util.Date;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;








 @ParentPackage("eop_default")
 @Namespace("/api/base")
 public class RecordInstallerAction
   extends WWAction
 {
   private InstallerManager installerManager;
   private String version;
   private String authcode;
   private String remark;
   private String domain;

   public String execute()
   {
     Installer installer = new Installer();
     installer.setInstalltime(Long.valueOf(new Date().getTime()));
     installer.setIp(getRequest().getRemoteAddr());
     installer.setRemark(getRequest().getServerName());
     installer.setVersion(this.version);
     installer.setRemark(this.remark);
     installer.setDomain(this.domain);
     installer.setArea(new IPSeeker().getCountry(installer.getIp()));
     this.installerManager.add(installer);

     this.json = "{result:1}";

     return "json_message";
   }



















   public String list()
   {
     this.webpage = this.installerManager.list(getPage(), getPageSize());
     return "list";
   }

   public InstallerManager getInstallerManager() {
     return this.installerManager;
   }

   public void setInstallerManager(InstallerManager installerManager) { this.installerManager = installerManager; }

   public String getVersion() {
     return this.version;
   }

   public void setVersion(String version) { this.version = version; }

   public String getAuthcode()
   {
     return this.authcode;
   }

   public void setAuthcode(String authcode) {
     this.authcode = authcode;
   }

   public String getRemark() {
     return this.remark;
   }

   public void setRemark(String remark) {
     this.remark = remark;
   }

   public String getDomain() {
     return this.domain;
   }

   public void setDomain(String domain) {
     this.domain = domain;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\RecordInstallerAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */