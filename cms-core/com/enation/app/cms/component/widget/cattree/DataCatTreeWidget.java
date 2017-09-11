 package com.enation.app.cms.component.widget.cattree;

 import com.enation.app.cms.component.widget.RequestParamWidget;
 import com.enation.app.cms.core.model.DataCat;
 import com.enation.app.cms.core.service.IDataCatManager;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;









 @Component("dataCatTree")
 @Scope("prototype")
 public class DataCatTreeWidget
   extends RequestParamWidget
 {
   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     boolean useUrlcatid = true;
     Integer catid = Integer.valueOf(0);
     Integer level = Integer.valueOf(0);

     String catidstr = (String)params.get("catid");
     String levelstr = (String)params.get("level");
     if (!StringUtil.isEmpty(catidstr)) {
       catid = Integer.valueOf(catidstr);
       useUrlcatid = false;
     }
     if (!StringUtil.isEmpty(levelstr)) {
       level = Integer.valueOf(levelstr);
     }
     Integer[] ids = parseId();
     Integer urlcatid = ids == null ? catid : ids[1];

     int passid = (useUrlcatid ? urlcatid : catid).intValue();
     DataCat dataCat = this.dataCatManager.get(Integer.valueOf(passid));
     String[] path = dataCat.getCat_path().split("\\|");
     int levelid = Integer.valueOf(path[level.intValue()]).intValue();
     List catList;
//     List catList;
     if (level.intValue() == 0) {
       catList = this.dataCatManager.listAllChildren(Integer.valueOf(passid));
     } else {
       catList = this.dataCatManager.listAllChildren(Integer.valueOf(levelid));
     }
     String url = (String)params.get("url");
     url = StringUtil.isEmpty(url) ? "data" : url;
     putData("catpath", dataCat.getCat_path());
     putData("catid", catid);
     putData("levelid", Integer.valueOf(levelid));
     putData("urlcatid", urlcatid);
     putData("url", url);
     putData("cat_tree", catList);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\widget\cattree\DataCatTreeWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */