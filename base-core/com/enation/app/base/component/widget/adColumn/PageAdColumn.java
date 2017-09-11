 package com.enation.app.base.component.widget.adColumn;

 import com.enation.app.base.core.model.AdColumn;
 import com.enation.app.base.core.model.Adv;
 import com.enation.app.base.core.service.IAdColumnManager;
 import com.enation.app.base.core.service.IAdvManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.widget.AbstractWidget;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;







 @Component
 @Scope("prototype")
 public class PageAdColumn
   extends AbstractWidget
 {
   private IAdvManager advManager;
   private IAdColumnManager adColumnManager;

   protected void config(Map<String, String> params)
   {
     setPageName("PageAdColumn_config");
     List<AdColumn> adColumnList = this.adColumnManager.listAllAdvPos();
     adColumnList = adColumnList == null ? new ArrayList() : adColumnList;
     putData("adColumnList", adColumnList);
   }

   protected void display(Map<String, String> params)
   {
     setPageName("PageAdColumn");
     String acid = (String)params.get("acid");
     AdColumn adc = this.adColumnManager.getADcolumnDetail(Long.valueOf(acid));
     String adwidth = adc.getWidth();
     String adheight = adc.getHeight();
     int atype = adc.getAtype().intValue();
     List<Adv> advList = this.advManager.listAdv(Long.valueOf(acid));
     advList = advList == null ? new ArrayList() : advList;
     if (advList.size() != 0) {
       int a = (int)(advList.size() * Math.random());
       putData("adv", advList.get(a));
     } else {
       Adv adv = new Adv();
       adv.setAcid(Integer.valueOf(0));
       adv.setAid(Integer.valueOf(0));

       adv.setAtturl(EopSetting.IMG_SERVER_DOMAIN + "/images/zhaozu.png");
       putData("adv", adv);
     }

     putData("wid", params.get("id"));
     putData("adwidth", adwidth);
     putData("adheight", adheight);
     putData("atype", Integer.valueOf(atype));
   }

   public void setAdvManager(IAdvManager advManager) {
     this.advManager = advManager;
   }

   public void setAdColumnManager(IAdColumnManager adColumnManager) {
     this.adColumnManager = adColumnManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\adColumn\PageAdColumn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */