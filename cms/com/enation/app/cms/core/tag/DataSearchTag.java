 package com.enation.app.cms.core.tag;

 import com.enation.app.cms.core.service.IDataManager;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.stereotype.Component;









 @Component
 public class DataSearchTag
   extends BaseFreeMarkerTag
 {
   private IDataManager dataManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     String connector = (String)params.get("connector");

     if (StringUtil.isEmpty(connector)) {
       connector = " and ";
     }


     Integer modelid = (Integer)params.get("modelid");
     if (modelid == null) {
       throw new TemplateModelException("modelid 参数不能为空");
     }

     Integer pageNo = (Integer)params.get("pageNo");
     if (pageNo == null) {
       pageNo = Integer.valueOf(1);
     }
     Integer pageSize = (Integer)params.get("pageSize");
     if (pageSize == null) {
       pageSize = Integer.valueOf(10);
     }

     Integer catid = (Integer)params.get("catid");
     String cat_id = null;
     if (catid != null) {
       cat_id = catid.toString();
     }

     Page dataPage = this.dataManager.search(pageNo.intValue(), pageSize.intValue(), modelid.intValue(), connector, cat_id);

     return dataPage;
   }

   public IDataManager getDataManager() {
     return this.dataManager;
   }

   public void setDataManager(IDataManager dataManager) {
     this.dataManager = dataManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\tag\DataSearchTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */