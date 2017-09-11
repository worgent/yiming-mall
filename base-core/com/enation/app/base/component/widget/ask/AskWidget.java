 package com.enation.app.base.component.widget.ask;

 import com.enation.app.base.core.model.Ask;
 import com.enation.app.base.core.service.IAskManager;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.widget.AbstractWidget;
 import com.enation.framework.database.Page;
 import com.enation.framework.pager.StaticPagerHtmlBuilder;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.Date;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;














 public class AskWidget
   extends AbstractWidget
 {
   private IAskManager askManager;

   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     HttpServletRequest request = EopContext.getHttpRequest();
     String action = request.getParameter("action");
     if (action.equals("list")) {
       list();
     }
     if (action.equals("toAsk")) {
       toAsk();
     }

     if (action.equals("ask")) {
       ask();
     }

     if (action.equals("view")) {
       view();
     }
   }

   private void list() {
     HttpServletRequest request = EopContext.getHttpRequest();
     String keyword = request.getParameter("keyword");
     String startTime = request.getParameter("startTime");
     String endTime = request.getParameter("endTime");
     String page = request.getParameter("page");
     Date start = StringUtil.isEmpty(startTime) ? null : DateUtil.toDate(startTime, "yyyy-MM-dd");
     Date end = StringUtil.isEmpty(endTime) ? null : DateUtil.toDate(endTime, "yyyy-MM-dd");
     Page askPage = this.askManager.listMyAsk(keyword, start, end, Integer.valueOf(page).intValue(), 20);
     putData("askList", askPage.getResult());

     StaticPagerHtmlBuilder pagerHtmlBuilder = new StaticPagerHtmlBuilder(Integer.valueOf(page).intValue(), askPage.getTotalCount(), 20);
     String page_html = pagerHtmlBuilder.buildPageHtml();
     putData("pager", page_html);

     setPageName("list");
   }


   private void toAsk()
   {
     setPageName("ask");
   }

   private void ask()
   {
     HttpServletRequest request = EopContext.getHttpRequest();
     String title = request.getParameter("title");
     String content = request.getParameter("content");
     Ask ask = new Ask();
     ask.setTitle(title);
     ask.setContent(content);

     this.askManager.add(ask);
     showSuccess("问题提交成功", "我的问题", "member_ask.html?action=list");
   }

   private void view() {
     HttpServletRequest request = EopContext.getHttpRequest();
     String askid = request.getParameter("askid");
     Ask ask = this.askManager.get(Integer.valueOf(askid));
     putData("ask", ask);
     setPageName("view");
   }

   public IAskManager getAskManager() {
     return this.askManager;
   }

   public void setAskManager(IAskManager askManager)
   {
     this.askManager = askManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\ask\AskWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */