 package com.enation.eop.processor.facade.support.widget;

 import com.enation.eop.processor.widget.IWidgetParamParser;
 import com.enation.framework.cache.AbstractCacheProxy;
 import com.enation.framework.cache.ICache;
 import java.util.Map;

 public class XmlWidgetParamParserCacheProxy extends AbstractCacheProxy implements IWidgetParamParser
 {
   private static String cacheName = "widget_key";
   private IWidgetParamParser xmlWidgetParamParserImpl;

   public XmlWidgetParamParserCacheProxy(IWidgetParamParser _xmlWidgetParamParserImpl) {
     super(cacheName);
     this.xmlWidgetParamParserImpl = _xmlWidgetParamParserImpl;
   }


   public Map<String, Map<String, Map<String, String>>> parse()
   {
     Object obj = this.cache.get("obj");

     if (obj == null) {
       obj = this.xmlWidgetParamParserImpl.parse();
       this.cache.put("obj", obj);
     }


     return (Map)obj;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\facade\support\widget\XmlWidgetParamParserCacheProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */