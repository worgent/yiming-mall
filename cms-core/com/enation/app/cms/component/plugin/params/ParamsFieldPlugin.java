 package com.enation.app.cms.component.plugin.params;

 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.plugin.AbstractFieldPlugin;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import net.sf.json.JSONArray;
 import org.springframework.stereotype.Component;










 @Component
 public class ParamsFieldPlugin
   extends AbstractFieldPlugin
 {
   public int getHaveSelectValue()
   {
     return 0;
   }

   public String getDataType() {
     return "text";
   }


   public String onDisplay(DataField field, Object value)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());
     if (value != null) {
       List<Param> paramlist = (List)JSONArray.toCollection(JSONArray.fromObject(value), Param.class);
       freeMarkerPaser.putData("paramlist", paramlist);
     }
     return freeMarkerPaser.proessPageContent();
   }

   public void onSave(Map article, DataField field)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String[] names = request.getParameterValues("param_name");
     String[] values = request.getParameterValues("param_value");
     List<Param> paramList = new ArrayList();

     if (names != null) {
       for (int i = 0; i < names.length; i++) {
         if (i != 0) {
           Param param = new Param();
           param.setName(names[i]);
           param.setValue(values[i]);
           paramList.add(param);
         }
       }
     }
     article.put("params", JSONArray.fromObject(paramList));
   }

   public Object onShow(DataField field, Object value)
   {
     if (value != null) {
       List<Param> paramlist = (List)JSONArray.toCollection(JSONArray.fromObject(value), Param.class);
       return paramlist;
     }
     return new ArrayList();
   }



   public String getId()
   {
     return "paramsField";
   }

   public String getName()
   {
     return "自定义参数";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\plugin\params\ParamsFieldPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */