 package com.enation.app.base.component.widget.counter;

 import com.enation.app.base.core.service.IAccessRecorder;
 import com.enation.eop.sdk.widget.AbstractWidget;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;

 @Component("access")
 @Scope("prototype")
 public class AccessWidget extends AbstractWidget
 {
   private IAccessRecorder accessRecorder;

   public IAccessRecorder getAccessRecorder()
   {
     return this.accessRecorder;
   }

   public void setAccessRecorder(IAccessRecorder accessRecorder) {
     this.accessRecorder = accessRecorder;
   }

   protected void display(Map<String, String> params)
   {
     Map map = this.accessRecorder.census();

     putData("access", map);
   }

   protected void config(Map<String, String> params) {}
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\counter\AccessWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */