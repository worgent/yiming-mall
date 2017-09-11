 package com.enation.app.base.core.action;

 import com.enation.framework.action.WWAction;
 import com.enation.framework.resource.ResourceStateManager;



 public class ResourceStateAction
   extends WWAction
 {
   private boolean haveNewDisplaoy;

   public String execute()
   {
     this.haveNewDisplaoy = ResourceStateManager.getHaveNewDisploy();
     return "input";
   }

   public String save()
   {
     ResourceStateManager.setDisplayState(1);
     showSuccessJson("更新成功");
     return "json_message";
   }

   public boolean isHaveNewDisplaoy()
   {
     return this.haveNewDisplaoy;
   }

   public void setHaveNewDisplaoy(boolean haveNewDisplaoy)
   {
     this.haveNewDisplaoy = haveNewDisplaoy;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\ResourceStateAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */