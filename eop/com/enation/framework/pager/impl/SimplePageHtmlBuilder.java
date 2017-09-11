 package com.enation.framework.pager.impl;

 import com.enation.framework.pager.AbstractPageHtmlBuilder;




 public class SimplePageHtmlBuilder
   extends AbstractPageHtmlBuilder
 {
   public SimplePageHtmlBuilder(long _pageNum, long _totalCount, int _pageSize)
   {
     super(_pageNum, _totalCount, _pageSize);
   }





   protected String getUrlStr(long page)
   {
     StringBuffer linkHtml = new StringBuffer();
     linkHtml.append("href='");
     linkHtml.append(this.url);
     linkHtml.append("page=");
     linkHtml.append(page);
     linkHtml.append("'>");
     return linkHtml.toString();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\pager\impl\SimplePageHtmlBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */