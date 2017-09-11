 package com.enation.app.base.component.widget.adv.flash;

 import com.enation.app.base.component.widget.abstractadv.AbstractAdvWidget;
 import com.enation.app.base.core.model.AdColumn;
 import com.enation.app.base.core.model.Adv;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;




 @Component("flashAdv")
 @Scope("prototype")
 public class FlashAdvWidget
   extends AbstractAdvWidget
 {
   protected void execute(AdColumn adColumn, List<Adv> advList)
   {
     StringBuffer imgs = new StringBuffer();
     StringBuffer urls = new StringBuffer();
     StringBuffer titles = new StringBuffer();
     for (Adv adv : advList) {
       if (imgs.length() != 0) imgs.append("|");
       imgs.append(adv.getAtturl());
       if (urls.length() != 0) urls.append("|");
       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       urls.append(request.getContextPath() + "/core/adv!click." + EopSetting.EXTENSION + "?advid=" + adv.getAid());
       if (titles.length() != 0) titles.append("|");
       titles.append(adv.getAname());
     }
     putData("imgs", imgs.toString());
     putData("urls", urls.toString());
     putData("titles", titles.toString());
     putData("width", adColumn.getWidth().replaceAll("px", ""));
     putData("height", adColumn.getHeight().replaceAll("px", ""));
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\adv\flash\FlashAdvWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */