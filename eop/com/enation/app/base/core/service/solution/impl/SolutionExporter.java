 package com.enation.app.base.core.service.solution.impl;

 import com.enation.app.base.core.plugin.data.DataExportPluginBundle;
 import com.enation.app.base.core.service.solution.ISetupCreator;
 import com.enation.app.base.core.service.solution.ISolutionExporter;
 import com.enation.eop.resource.ISiteManager;
 import com.enation.eop.resource.model.EopApp;
 import com.enation.eop.sdk.IApp;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.util.FileUtil;
 import java.io.File;
 import java.util.List;
 import org.apache.log4j.Logger;
 import org.apache.tools.ant.Project;
 import org.apache.tools.ant.taskdefs.Delete;
 import org.apache.tools.ant.taskdefs.Zip;
 import org.apache.tools.ant.types.FileSet;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;







 public class SolutionExporter
   implements ISolutionExporter
 {
   protected final Logger logger = Logger.getLogger(getClass());
   private SqlExportService sqlExportService;
   private ProfileCreator profileCreator;
   private ISetupCreator setupCreator;
   private ISiteManager siteManager;
   private DataExportPluginBundle dataExportPluginBundle;

   @Transactional(propagation=Propagation.REQUIRED)
   public void export(String name, boolean exportData, boolean exportTheme, boolean exportProfile, boolean exportAttr)
   {
     String datapath = EopSetting.IMG_SERVER_PATH + "/" + EopContext.getContext().getContextPath() + "/backup/";


     String temppath = datapath + System.currentTimeMillis();
     File tempdir = new File(temppath);
     tempdir.mkdirs();


     File target = new File(datapath + name + ".zip");
     if (target.exists()) {
       target.delete();
     }





     try
     {
       if (exportData) {
         StringBuffer sqlContent = new StringBuffer();
         StringBuffer xmlFile = new StringBuffer();
         xmlFile.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
         xmlFile.append("<dbsolution>\n");



         List<EopApp> appList = this.siteManager.getSiteApps();
         for (EopApp eopApp : appList) {
           String appid = eopApp.getAppid();
           IApp app = (IApp)SpringContextHolder.getBean(appid);





           xmlFile.append(app.dumpXml());
         }


         String pluginData = this.dataExportPluginBundle.exportData();
         xmlFile.append(pluginData);

         xmlFile.append("</dbsolution>");

         FileUtil.write(temppath + "/example_data.xml", xmlFile.toString());
       }











       if (exportTheme) {
         if ("1".equals(EopSetting.RESOURCEMODE)) {
           FileUtil.copyFolder(EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/themes", temppath + "/themes");
           FileUtil.copyFolder(EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/themes", temppath + "/themes");
         }

         if ("2".equals(EopSetting.RESOURCEMODE)) {
           FileUtil.copyFolder(EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/themes", temppath + "/themes");
         }
       }






       if (exportProfile)
       {
         this.profileCreator.createProfile(temppath + "/profile.xml");
       }







       AuthFileCreator.create(temppath);






       if (exportAttr)
       {
         FileUtil.copyFolder(EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/attachment", temppath + "/attachment");
       }

       Project prj = new Project();
       Zip zip = new Zip();
       zip.setEncoding("UTF-8");
       zip.setProject(prj);
       zip.setDestFile(target);
       FileSet fileSet = new FileSet();
       fileSet.setProject(prj);
       fileSet.setDir(tempdir);
       zip.addFileset(fileSet);
       zip.execute();
       Delete delete = new Delete();
       delete.setDir(tempdir);
       delete.execute();
     } catch (Exception e) {
       e.printStackTrace();
       this.logger.error("导出解决方案出错", e.fillInStackTrace());
       throw new RuntimeException("导出解决方案出错[" + e.getMessage() + "]", e);
     }
   }

   public SqlExportService getSqlExportService() {
     return this.sqlExportService;
   }

   public void setSqlExportService(SqlExportService sqlExportService) {
     this.sqlExportService = sqlExportService;
   }

   public ProfileCreator getProfileCreator() {
     return this.profileCreator;
   }

   public void setProfileCreator(ProfileCreator profileCreator) {
     this.profileCreator = profileCreator;
   }

   public ISetupCreator getSetupCreator() {
     return this.setupCreator;
   }

   public void setSetupCreator(ISetupCreator setupCreator) {
     this.setupCreator = setupCreator;
   }

   public ISiteManager getSiteManager() {
     return this.siteManager;
   }

   public void setSiteManager(ISiteManager siteManager) {
     this.siteManager = siteManager;
   }

   public DataExportPluginBundle getDataExportPluginBundle() {
     return this.dataExportPluginBundle;
   }

   public void setDataExportPluginBundle(DataExportPluginBundle dataExportPluginBundle)
   {
     this.dataExportPluginBundle = dataExportPluginBundle;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\solution\impl\SolutionExporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */