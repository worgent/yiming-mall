 package com.enation.app.base.component.widget.adv.fadeplay;

 import com.enation.app.base.component.widget.abstractadv.AbstractAdvWidget;
 import com.enation.app.base.core.model.AdColumn;
 import com.enation.app.base.core.model.Adv;
 import java.util.List;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;







 @Component("fadeplay")
 @Scope("prototype")
 public class FadePlayWidget
   extends AbstractAdvWidget
 {
   protected void execute(AdColumn adColumn, List<Adv> advList)
   {
     putData("advlist", advList);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\adv\fadeplay\FadePlayWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */