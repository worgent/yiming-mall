 package com.enation.framework.pager.impl;

 import com.enation.framework.pager.AbstractPageHtmlBuilder;

 public class WidgetPagerHtmlBuilder extends AbstractPageHtmlBuilder {
   public WidgetPagerHtmlBuilder(long _pageNum, long _totalCount, int _pageSize) {
     super(_pageNum, _totalCount, _pageSize);
   }


   protected String getUrlStr(long page)
   {
     return null;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\pager\impl\WidgetPagerHtmlBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */