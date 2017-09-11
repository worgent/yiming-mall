 package com.enation.app.cms.component.widget;

 import com.enation.app.cms.core.model.DataCat;
 import com.enation.app.cms.core.service.IDataCatManager;
 import com.enation.app.cms.core.service.IDataManager;
 import com.enation.framework.database.Page;
 import com.enation.framework.pager.StaticPagerHtmlBuilder;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;


















 @Component("dynamicDataList")
 @Scope("prototype")
 public class DynamicDataListWidget
   extends RequestParamWidget
 {
   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     String pageSize = (String)params.get("pagesize");
     String pageNoStr = (String)params.get("pageno");
     String term = (String)params.get("term");
     pageSize = StringUtil.isEmpty(pageSize) ? "20" : pageSize;
     Integer[] ids = parseId();
     Integer catid = ids[1];
     Integer pageNo = StringUtil.isEmpty(pageNoStr) ? ids[0] : Integer.valueOf(pageNoStr);

     String showchilds = (String)params.get("showchild");
     boolean showchild = showchilds == null ? false : showchilds.trim().toUpperCase().equals("YES");

     String orders = (String)params.get("orders");

     Page webpage = this.dataManager.listAll(catid, term, orders, showchild, pageNo.intValue(), Integer.valueOf(pageSize).intValue());


     StaticPagerHtmlBuilder pagerHtmlBuilder = new StaticPagerHtmlBuilder(pageNo.intValue(), webpage.getTotalCount(), Integer.valueOf(pageSize).intValue());
     String page_html = pagerHtmlBuilder.buildPageHtml();

     long totalPageCount = webpage.getTotalPageCount();
     long totalCount = webpage.getTotalCount();
     putData("dataList", webpage.getResult());
     putData("pager", page_html);
     putData("pagesize", pageSize);
     putData("pageno", pageNo);
     putData("totalcount", Long.valueOf(totalCount));
     putData("totalpagecount", Long.valueOf(totalPageCount));




     String customName = (String)params.get("custom_name");
     if (customName != null) {
       putData(customName, webpage.getResult());
     }

     List<DataCat> parents = this.dataCatManager.getParents(catid);
     DataCat cat = (DataCat)parents.get(parents.size() - 1);

     putData("catname", cat.getName());
     putData("cat", cat);


     StringBuffer navBar = new StringBuffer();
     navBar.append("<a href='index.html'>首页</a>");
     for (DataCat c : parents) {
       navBar.append("> <a href='" + c.getUrl() + "'>" + c.getName() + "</a>");
     }
     putData("navbar", navBar.toString());
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\widget\DynamicDataListWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */