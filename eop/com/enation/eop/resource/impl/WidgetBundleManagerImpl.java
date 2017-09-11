 package com.enation.eop.resource.impl;

 import com.enation.eop.resource.model.WidgetBundle;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;

 public class WidgetBundleManagerImpl extends BaseSupport<WidgetBundle> implements com.enation.eop.resource.IWidgetBundleManager
 {
   public WidgetBundle getWidgetBundle(String widgetType)
   {
     String sql = "select * from widgetbundle where widgettype=?";
     return (WidgetBundle)this.baseDaoSupport.queryForObject(sql, WidgetBundle.class, new Object[] { widgetType });
   }

   public List<WidgetBundle> getWidgetbundleList() {
     String sql = "select * from widgetbundle";
     return this.baseDaoSupport.queryForList(sql, WidgetBundle.class, new Object[0]);
   }

   public void add(WidgetBundle widgetBundle) {
     this.baseDaoSupport.insert("widgetbundle", widgetBundle);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\impl\WidgetBundleManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */