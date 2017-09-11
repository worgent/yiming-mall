 package com.enation.eop.sdk.webapp.taglib.html.support;

 import com.enation.framework.pager.AbstractPageHtmlBuilder;








 public class GridAjaxPageHtmlBuilder
   extends AbstractPageHtmlBuilder
 {
   private String gridid;

   public GridAjaxPageHtmlBuilder(long pageNum, long totalCount, int pageSize, String _gridid)
   {
     super(pageNum, totalCount, pageSize);
     this.gridid = _gridid;
   }





   public String buildPageHtml()
   {
     return super.buildPageHtml() + "<script>$(function(){$(\".gridbody[gridid='" + this.gridid + "']>.page\").gridAjaxPager('" + this.url + "');});</script>";
   }






   protected String getUrlStr(long page)
   {
     StringBuffer linkHtml = new StringBuffer();
     linkHtml.append("href='javascript:;'");
     linkHtml.append(" pageNo='");
     linkHtml.append(page);
     linkHtml.append("'>");
     return linkHtml.toString();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\webapp\taglib\html\support\GridAjaxPageHtmlBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */