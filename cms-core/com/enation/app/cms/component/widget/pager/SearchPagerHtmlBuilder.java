 package com.enation.app.cms.component.widget.pager;

 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.RequestUtil;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import javax.servlet.http.HttpServletRequest;



 public class SearchPagerHtmlBuilder
 {
   protected String url;
   private HttpServletRequest request;
   private long pageNum;
   private long totalCount;
   private int pageSize;
   private long pageCount;
   private int showCount = 10;

   public SearchPagerHtmlBuilder(long _pageNum, long _totalCount, int _pageSize) {
     this.pageNum = _pageNum;
     this.totalCount = _totalCount;
     this.pageSize = _pageSize;
     this.request = ThreadContextHolder.getHttpRequest();
   }

   public String buildPageHtml() {
     init();
     StringBuffer pageStr = new StringBuffer("");
     pageStr.append("<table align=\"right\" class=\"pager\"><tbody><tr>");
     pageStr.append(getHeadString());
     pageStr.append(getBodyString());
     pageStr.append(getFooterString());
     pageStr.append("</tr></tbody></table>");
     return pageStr.toString();
   }










   private void init()
   {
     this.pageSize = (this.pageSize < 1 ? 1 : this.pageSize);

     this.pageCount = (this.totalCount / this.pageSize);
     this.pageCount = (this.totalCount % this.pageSize > 0L ? this.pageCount + 1L : this.pageCount);

     this.pageNum = (this.pageNum > this.pageCount ? this.pageCount : this.pageNum);
     this.pageNum = (this.pageNum < 1L ? 1L : this.pageNum);


     this.url = RequestUtil.getRequestUrl(this.request);
     String regEx = "(&||\\?)page=.*";
     Pattern p = Pattern.compile(regEx);
     Matcher m = p.matcher(this.url);
     this.url = m.replaceAll("");
     this.url += "?";
     this.url = (this.request.getContextPath() + this.url);
   }








   protected String getHeadString()
   {
     StringBuffer headString = new StringBuffer("");
     headString.append("<td>");



     if (this.pageNum > 1L)
     {
       headString.append("<a title=\"上一页\"");
       headString.append(" onmouseout=\"this.className = 'prev'\" ");
       headString.append("  onmouseover=\"this.className = 'onprev'\" ");
       headString.append(" class=\"prev\" ");
       headString.append(" href=\"");
       headString.append(getUrlStr(this.pageNum - 1L));
       headString.append("\" >上一页");
       headString.append("</a>\n");
     }
     else {
       headString.append("<span title=\"已经是第一页\" ");
       headString.append(" class=\"prev\"> 已经是第一页</span>");
     }
     headString.append("</td>");
     return headString.toString();
   }







   protected String getFooterString()
   {
     StringBuffer footerStr = new StringBuffer("");
     footerStr.append("<td style=\"padding-right: 20px;\">");
     if (this.pageNum < this.pageCount)
     {

       footerStr.append("<a title=\"下一页\" onmouseout=\"this.className = 'next'\" onmouseover=\"this.className = 'onnext'\" class=\"next\" ");
       footerStr.append(" href=\"");
       footerStr.append(getUrlStr(this.pageNum + 1L));
       footerStr.append("\"");
       footerStr.append("下一页</a>");
     }
     else {
       footerStr.append("<span title=\"已经是最后一页\" class=\"next\">已经是最后一页</span>");
     }
     footerStr.append("</td>\n");
     return footerStr.toString();
   }








   protected String getBodyString()
   {
     StringBuffer pageStr = new StringBuffer();

     long start = this.pageNum - this.showCount / 2;
     start = start <= 1L ? 1L : start;

     long end = start + this.showCount;
     end = end > this.pageCount ? this.pageCount : end;
     pageStr.append("<td>");

     for (long i = start; i <= end; i += 1L)
     {

       if (i != this.pageNum) {
         pageStr.append("<a");
         pageStr.append(" href=\"");

         pageStr.append(getUrlStr(i));
         pageStr.append("\">");

         pageStr.append(i);
         pageStr.append("</a>\n");
       } else {
         pageStr.append(" <strong class=\"pagecurrent\">");
         pageStr.append(i);
         pageStr.append("</strong> ");
       }
     }

     pageStr.append("</td>");
     return pageStr.toString();
   }








   protected String getUrlStr(long page)
   {
     return this.url + "page=" + page;
   }

   public static void main(String[] args)
   {
     String url = "/articleList-1-2.html";
     String pattern = "/(.*)-(\\d+)-(\\d+).html";
     Pattern p = Pattern.compile(pattern, 6);
     Matcher m = p.matcher(url);
     if (m.find()) {}
   }














   private String findUrl(String url)
   {
     String pattern = "(.*)(p(\\d))(.*).html";
     Pattern p = Pattern.compile(pattern, 34);
     Matcher m = p.matcher(url);
     if (m.find()) {
       String s = m.replaceAll("$1");
       return s + "-";
     }
     return null;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\widget\pager\SearchPagerHtmlBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */