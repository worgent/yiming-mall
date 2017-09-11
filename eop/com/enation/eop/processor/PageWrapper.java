 package com.enation.eop.processor;


 public class PageWrapper
   implements IPageParser
 {
   protected IPageParser pageParser;


   public PageWrapper(IPageParser parser)
   {
     this.pageParser = parser;
   }

   public String parse(String url) {
     return this.pageParser.parse(url);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\PageWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */