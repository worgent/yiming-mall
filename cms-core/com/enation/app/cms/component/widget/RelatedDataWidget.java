 package com.enation.app.cms.component.widget;

 import com.enation.app.cms.core.service.IDataManager;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;





 @Component("relatedData")
 @Scope("prototype")
 public class RelatedDataWidget
   extends RequestParamWidget
 {
   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     String fieldname = (String)params.get("fieldname");
     String catidStr = (String)params.get("catid");
     Integer[] ids = parseId();
     Integer articleid = ids[0];
     Integer catid = ids[1];

     int relcatid = Integer.valueOf(catidStr).intValue();

     List dataList = this.dataManager.listRelated(catid, Integer.valueOf(relcatid), articleid, fieldname);
     putData("dataList", dataList);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\widget\RelatedDataWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */