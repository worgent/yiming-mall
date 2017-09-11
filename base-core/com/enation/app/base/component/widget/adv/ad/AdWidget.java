 package com.enation.app.base.component.widget.adv.ad;

 import com.enation.app.base.component.widget.abstractadv.AbstractAdvWidget;
 import com.enation.app.base.core.model.AdColumn;
 import com.enation.app.base.core.model.Adv;
 import java.util.List;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;








 @Component("ad")
 @Scope("prototype")
 public class AdWidget
   extends AbstractAdvWidget
 {
   protected void execute(AdColumn adColumn, List<Adv> advList)
   {
     String adwidth = adColumn.getWidth();
     String adheight = adColumn.getHeight();
     int atype = adColumn.getAtype().intValue();
     int a = (int)(advList.size() * Math.random());
     putData("adv", advList.get(a));
     putData("adwidth", adwidth);
     putData("adheight", adheight);
     putData("atype", Integer.valueOf(atype));
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\adv\ad\AdWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */