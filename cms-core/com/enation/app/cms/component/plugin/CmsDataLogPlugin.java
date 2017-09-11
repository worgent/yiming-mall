 package com.enation.app.cms.component.plugin;

 import com.enation.app.base.core.model.DataLog;
 import com.enation.app.cms.core.model.DataModel;
 import com.enation.app.cms.core.plugin.IDataSaveEvent;
 import com.enation.eop.resource.IDataLogManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Set;
 import org.springframework.stereotype.Component;








 @Component
 public class CmsDataLogPlugin
   extends AutoRegisterPlugin
   implements IDataSaveEvent
 {
   private IDataLogManager dataLogManager;

   public void onSave(Map<String, Object> data, DataModel dataModel, int dataSaveType)
   {
     Iterator<String> iter = data.keySet().iterator();
     StringBuffer content = new StringBuffer();
     StringBuffer pics = new StringBuffer();
     while (iter.hasNext()) {
       String key = (String)iter.next();
       Object v = data.get(key);
       if ((v instanceof String))
       {

         String value = (String)v;

         if (value != null) {
           if (value.startsWith(EopSetting.FILE_STORE_PREFIX)) {
             if (pics.length() != 0) {
               pics.append(",");
             }
             pics.append(value + "|" + value);
           } else {
             content.append(key + ":" + value + "<br>");
           }
         }
       }
     }
     DataLog datalog = new DataLog();
     datalog.setContent(content.toString());
     datalog.setPics(pics.toString());
     datalog.setLogtype("文章");
     if (dataSaveType == 1) {
       datalog.setOptype("添加");
     } else if (dataSaveType == 2) {
       datalog.setOptype("修改");
     } else
       datalog.setOptype("未定义");
     this.dataLogManager.add(datalog);
   }

   public IDataLogManager getDataLogManager() {
     return this.dataLogManager;
   }

   public void setDataLogManager(IDataLogManager dataLogManager) {
     this.dataLogManager = dataLogManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\plugin\CmsDataLogPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */