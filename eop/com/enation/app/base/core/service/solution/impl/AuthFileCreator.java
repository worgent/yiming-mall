 package com.enation.app.base.core.service.solution.impl;

 import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
 import com.enation.framework.util.FileUtil;










 public class AuthFileCreator
 {
   public static void create(String temppath)
   {
     String[] tables = { "auth_action", "role", "role_auth", "user_role" };
     String data = DBSolutionFactory.dbExport(tables, true, "es_");
     StringBuffer xmlFile = new StringBuffer();
     xmlFile.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
     xmlFile.append("<dbsolution>\n");
     xmlFile.append(data);
     xmlFile.append("</dbsolution>");

     FileUtil.write(temppath + "/auth.xml", xmlFile.toString());
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\solution\impl\AuthFileCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */