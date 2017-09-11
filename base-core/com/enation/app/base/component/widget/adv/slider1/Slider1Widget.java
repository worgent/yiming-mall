 package com.enation.app.base.component.widget.adv.slider1;

 import com.enation.app.base.component.widget.abstractadv.AbstractAdvWidget;
 import com.enation.app.base.core.model.AdColumn;
 import com.enation.app.base.core.model.Adv;
 import java.util.List;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;

 @Component("slider1")
 @Scope("prototype")
 public class Slider1Widget
   extends AbstractAdvWidget
 {
   protected void execute(AdColumn adColumn, List<Adv> advList)
   {
     putData("adColumn", adColumn);
     putData("advList", advList);
     String width = adColumn.getWidth();
     width = width.endsWith("px") ? width.substring(0, width.length() - 2) : width;
     String height = adColumn.getHeight();
     height = height.endsWith("px") ? height.substring(0, height.length() - 2) : height;
     putData("width", width);
     putData("height", height);
     putData("count", Integer.valueOf(advList.size()));
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\adv\slider1\Slider1Widget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */