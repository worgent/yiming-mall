 package com.enation.eop.sdk.utils;

 import com.enation.eop.processor.Widget;

 public class WidgetUtil
 {
   public static String toHtml(Widget widget, String content)
   {
     StringBuffer htmlBuffer = new StringBuffer();


     htmlBuffer.append("<div ");

     htmlBuffer.append("class=\"widget\"");
     htmlBuffer.append(" ");


     htmlBuffer.append("eop_type=\"widget\"");
     htmlBuffer.append(" ");







     htmlBuffer.append("appId=\"");
     htmlBuffer.append(widget.getApp().getId());
     htmlBuffer.append("\"");
     htmlBuffer.append(" ");

     htmlBuffer.append("widgetType=\"");
     htmlBuffer.append(widget.getWidgetType());
     htmlBuffer.append("\"");
     htmlBuffer.append(" ");

     htmlBuffer.append(">");

     htmlBuffer.append(content);

     htmlBuffer.append("</div>");

     return htmlBuffer.toString();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\\utils\WidgetUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */