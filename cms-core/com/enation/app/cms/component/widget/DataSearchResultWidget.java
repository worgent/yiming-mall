 package com.enation.app.cms.component.widget;

 import com.enation.app.cms.component.widget.pager.SearchPagerHtmlBuilder;
 import com.enation.app.cms.core.service.IDataManager;
 import com.enation.eop.sdk.widget.AbstractWidget;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;










 @Component("dataSearchResult")
 @Scope("prototype")
 public class DataSearchResultWidget
   extends AbstractWidget
 {
   private IDataManager dataManager;
   private String catid;

   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     String pager = (String)params.get("pager");
     pager = StringUtil.isEmpty(pager) ? "on" : pager;
     Integer modelid = Integer.valueOf((String)params.get("modelid"));

     String pageSizeParam = (String)params.get("pagesize");
     int pageSize = StringUtil.isEmpty(pageSizeParam) ? 20 : Integer.valueOf(pageSizeParam).intValue();

     String connector = (String)params.get("connector");
     if (connector == null) {
       connector = " and ";
     }

     String outname = (String)params.get("outname");
     if (outname == null) {
       outname = "dataList";
     }
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String pageParam = request.getParameter("page");
     int pageNo = StringUtil.isEmpty(pageParam) ? 1 : Integer.valueOf(pageParam).intValue();

     String showchilds = (String)params.get("showchild");
     boolean showchild = showchilds == null ? false : showchilds.trim().toUpperCase().equals("YES");

     if ("on".equals(pager)) {
       Page dataPage = this.dataManager.search(pageNo, pageSize, modelid.intValue(), connector, this.catid);
       List dataList = (List)dataPage.getResult();
       putData(outname, dataList);

       SearchPagerHtmlBuilder pagerHtmlBuilder = new SearchPagerHtmlBuilder(pageNo, dataPage.getTotalCount(), pageSize);
       String pagerHtml = pagerHtmlBuilder.buildPageHtml();
       putData("pager", pagerHtml);
     } else {
       List dataList = this.dataManager.search(modelid.intValue(), connector);
       putData(outname, dataList);

       putData("pager", "<!--不分页-->");
     }
   }

   public IDataManager getDataManager() {
     return this.dataManager;
   }

   public void setDataManager(IDataManager dataManager) {
     this.dataManager = dataManager;
   }

   public String getCatid() {
     return this.catid;
   }

   public void setCatid(String catid) {
     this.catid = catid;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\widget\DataSearchResultWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */