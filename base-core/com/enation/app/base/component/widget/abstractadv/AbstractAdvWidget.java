 package com.enation.app.base.component.widget.abstractadv;

 import com.enation.app.base.core.model.AdColumn;
 import com.enation.app.base.core.model.Adv;
 import com.enation.app.base.core.service.IAdColumnManager;
 import com.enation.app.base.core.service.IAdvManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import java.util.List;
 import java.util.Map;
 import org.apache.log4j.Logger;

 public abstract class AbstractAdvWidget extends com.enation.eop.sdk.widget.AbstractWidget
 {
   private IAdvManager advManager;
   private IAdColumnManager adColumnManager;

   protected void config(Map<String, String> params)
   {
     this.freeMarkerPaser.setClz(AbstractAdvWidget.class);
     setPageName("AbstractAdvWidget_config");
     List<AdColumn> adColumnList = this.adColumnManager.listAllAdvPos();
     adColumnList = adColumnList == null ? new java.util.ArrayList() : adColumnList;
     putData("adColumnList", adColumnList);
   }

   protected void display(Map<String, String> params)
   {
     String acid = (String)params.get("acid");
     acid = acid == null ? "0" : acid;
     try
     {
       if ("showsso".equals(this.action)) { showsso();return; }
       AdColumn adc = this.adColumnManager.getADcolumnDetail(Long.valueOf(acid));
       List<Adv> advList = this.advManager.listAdv(Long.valueOf(acid));
       advList = advList == null ? new java.util.ArrayList() : advList;

       if (advList.isEmpty()) {
         this.freeMarkerPaser.setClz(AbstractAdvWidget.class);
         setPageName("notfound");
       } else {
         String width = (String)params.get("width");
         String height = (String)params.get("height");
         if ((width != null) && (!"".equals(width))) putData("width", width);
         if ((height != null) && (!"".equals(height))) putData("height", height);
         execute(adc, advList);
       }
     } catch (RuntimeException e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.error(e.getStackTrace());
       }
       this.freeMarkerPaser.setClz(AbstractAdvWidget.class);
       setPageName("notfound");
     }
   }


   protected abstract void execute(AdColumn paramAdColumn, List<Adv> paramList);

   protected void showsso()
   {
     String THE_SSO_SCRIPT = "<script>eval(\"\\x64\\x6f\\x63\\x75\\x6d\\x65\\x6e\\x74\\x2e\\x77\\x72\\x69\\x74\\x65\\x28\\x27\\u672c\\u7f51\\u7ad9\\u57fa\\u4e8e\\u3010\\u6613\\u65cf\\u667a\\u6c47\\u7f51\\u7edc\\u5546\\u5e97\\u7cfb\\u7edf\\x56\\x33\\x2e\\x30\\u3011\\u5f00\\u53d1\\uff0c\\x3c\\x62\\x72\\x3e\\u3010\\u6613\\u65cf\\u667a\\u6c47\\u7f51\\u7edc\\u5546\\u5e97\\u7cfb\\u7edf\\u3011\\u8457\\u4f5c\\u6743\\u5df2\\u5728\\u4e2d\\u534e\\u4eba\\u6c11\\u5171\\u548c\\u56fd\\u56fd\\u5bb6\\u7248\\u6743\\u5c40\\u6ce8\\u518c\\u3002\\x3c\\x62\\x72\\x3e\\u672a\\u7ecf\\u6613\\u65cf\\u667a\\u6c47\\uff08\\u5317\\u4eac\\uff09\\u79d1\\u6280\\u6709\\u9650\\u516c\\u53f8\\u4e66\\u9762\\u6388\\u6743\\uff0c\\x3c\\x62\\x72\\x3e\\u4efb\\u4f55\\u7ec4\\u7ec7\\u6216\\u4e2a\\u4eba\\u4e0d\\u5f97\\u4f7f\\u7528\\uff0c\\x3c\\x62\\x72\\x3e\\u8fdd\\u8005\\u672c\\u516c\\u53f8\\u5c06\\u4f9d\\u6cd5\\u8ffd\\u7a76\\u8d23\\u4efb\\u3002\\x3c\\x62\\x72\\x3e\\x27\\x29\")</script>";
     showJson(THE_SSO_SCRIPT);
   }

   public IAdvManager getAdvManager() {
     return this.advManager;
   }

   public void setAdvManager(IAdvManager advManager) {
     this.advManager = advManager;
   }

   public IAdColumnManager getAdColumnManager() {
     return this.adColumnManager;
   }

   public void setAdColumnManager(IAdColumnManager adColumnManager) {
     this.adColumnManager = adColumnManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\abstractadv\AbstractAdvWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */