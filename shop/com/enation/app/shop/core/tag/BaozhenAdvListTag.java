 package com.enation.app.shop.core.tag;

 import com.enation.app.base.core.model.AdColumn;
 import com.enation.app.base.core.model.Adv;
 import com.enation.app.base.core.service.IAdColumnManager;
 import com.enation.app.base.core.service.IAdvManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;

 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;


 @Component
 @Scope("prototype")
 public class BaozhenAdvListTag
   extends BaseFreeMarkerTag
 {
   private IAdvManager advManager;
   private IAdColumnManager adColumnManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     String acid = (String)params.get("acid");
     acid = acid == null ? "0" : acid;
     Map<String, Object> data = new HashMap();
     try {
       AdColumn adDetails = this.adColumnManager.getADcolumnDetail(Long.valueOf(acid));
       List<Adv> advList = null;

       if (adDetails != null) {
         advList = this.advManager.listAdv(Long.valueOf(acid));
       }

       advList = advList == null ? new ArrayList() : advList;

       data.put("adDetails", adDetails);
       data.put("advList", advList);
     }
     catch (RuntimeException e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.error(e.getStackTrace());
       }
     }
     return data;
   }

   public IAdvManager getAdvManager() { return this.advManager; }

   public void setAdvManager(IAdvManager advManager) {
     this.advManager = advManager;
   }

   public IAdColumnManager getAdColumnManager() { return this.adColumnManager; }

   public void setAdColumnManager(IAdColumnManager adColumnManager) {
     this.adColumnManager = adColumnManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\tag\AdvListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */