 package com.enation.app.base.core.service.impl;

 import com.enation.app.base.core.model.Help;
 import com.enation.app.base.core.service.IHelpManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;


 public class HelpManager
   extends BaseSupport<Help>
   implements IHelpManager
 {
   public Help get(String helpid)
   {
     String sql = "select * from es_help_1_1 where helpid=?";
     return (Help)this.daoSupport.queryForObject(sql, Help.class, new Object[] { helpid });
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\impl\HelpManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */