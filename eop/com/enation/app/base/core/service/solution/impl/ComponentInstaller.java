 package com.enation.app.base.core.service.solution.impl;

 import com.enation.app.base.core.service.solution.IInstaller;
 import com.enation.framework.component.IComponentManager;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;
 import org.w3c.dom.Element;
 import org.w3c.dom.Node;
 import org.w3c.dom.NodeList;







 public class ComponentInstaller
   implements IInstaller
 {
   private IComponentManager componentManager;

   @Transactional(propagation=Propagation.REQUIRED)
   public void install(String productId, Node fragment)
   {
     NodeList componentList = fragment.getChildNodes();
     int length = componentList.getLength();

     for (int i = 0; i < length; i++) {
       Node node = componentList.item(i);

       if (node.getNodeType() == 1) {
         Element componentEl = (Element)node;
         String componentid = componentEl.getAttribute("id");
         this.componentManager.install(componentid);
         this.componentManager.start(componentid);
       }
     }
   }

   public IComponentManager getComponentManager() {
     return this.componentManager;
   }

   public void setComponentManager(IComponentManager componentManager) {
     this.componentManager = componentManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\solution\impl\ComponentInstaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */