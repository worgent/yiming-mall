 package com.enation.framework.pager.impl;

 import com.enation.framework.pager.AbstractPageHtmlBuilder;

 public class AjaxPagerHtmlBuilder extends AbstractPageHtmlBuilder
 {
   public AjaxPagerHtmlBuilder(long _pageNum, long _totalCount, int _pageSize) {
     super(_pageNum, _totalCount, _pageSize);
   }


   protected String getUrlStr(long page)
   {
     StringBuffer linkHtml = new StringBuffer();
     linkHtml.append("href='javascript:;'");
     linkHtml.append("page='");
     linkHtml.append(page);
     linkHtml.append("'>");
     return linkHtml.toString();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\pager\impl\AjaxPagerHtmlBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */