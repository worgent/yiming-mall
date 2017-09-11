 package com.enation.app.base.core.service.solution.impl;

 import com.enation.app.base.core.service.solution.IInstaller;
 import com.enation.eop.resource.IWidgetBundleManager;
 import com.enation.eop.resource.model.WidgetBundle;
 import org.w3c.dom.Element;
 import org.w3c.dom.Node;
 import org.w3c.dom.NodeList;






 public class WidgetInstaller
   implements IInstaller
 {
   private IWidgetBundleManager widgetBundleManager;

   public void install(String productId, Node fragment)
   {
     NodeList widgetList = fragment.getChildNodes();
     install(widgetList);
   }


   private void install(Element ele)
   {
     WidgetBundle widgetBundle = new WidgetBundle();
     widgetBundle.setWidgettype(ele.getAttribute("type"));
     widgetBundle.setWidgetname(ele.getAttribute("name"));
     this.widgetBundleManager.add(widgetBundle);
   }


   private void install(NodeList nodeList)
   {
     int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
       Node node = nodeList.item(i);
       if (node.getNodeType() == 1) {
         install((Element)node);
       }
     }
   }

   public IWidgetBundleManager getWidgetBundleManager()
   {
     return this.widgetBundleManager;
   }

   public void setWidgetBundleManager(IWidgetBundleManager widgetBundleManager)
   {
     this.widgetBundleManager = widgetBundleManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\solution\impl\WidgetInstaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */