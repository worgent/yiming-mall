 package com.enation.app.cms.component.widget;

 import com.enation.app.cms.core.model.DataCat;
 import com.enation.app.cms.core.service.IDataCatManager;
 import com.enation.app.cms.core.service.IDataManager;
 import com.enation.eop.sdk.widget.AbstractWidget;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;











 @Component("staticDataList")
 @Scope("prototype")
 public class StaticDataListWidget
   extends AbstractWidget
 {
   private IDataManager dataManager;
   private IDataCatManager dataCatManager;

   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     Integer catid = Integer.valueOf((String)params.get("catid"));
     String countStr = (String)params.get("count");
     String term = (String)params.get("term");

     String showchilds = (String)params.get("showchild");
     boolean showchild = showchilds == null ? false : showchilds.trim().toUpperCase().equals("YES");






     String orders = (String)params.get("orders");

     Integer count = Integer.valueOf(StringUtil.isEmpty(countStr) ? 10 : Integer.valueOf(countStr).intValue());
     Page page = this.dataManager.listAll(catid, term, orders, showchild, 1, count.intValue());
     List articleList = (List)page.getResult();

     putData("dataList", articleList);


     DataCat cat = this.dataCatManager.get(Integer.valueOf(catid.intValue()));

     putData("cat", cat);




     String customName = (String)params.get("custom_name");
     if (customName != null) {
       putData(customName, articleList);
     }
   }












   public IDataManager getDataManager()
   {
     return this.dataManager;
   }

   public void setDataManager(IDataManager dataManager) {
     this.dataManager = dataManager;
   }

   public IDataCatManager getDataCatManager() {
     return this.dataCatManager;
   }

   public void setDataCatManager(IDataCatManager dataCatManager) {
     this.dataCatManager = dataCatManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\widget\StaticDataListWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */