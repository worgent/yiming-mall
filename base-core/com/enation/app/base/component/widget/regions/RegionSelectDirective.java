 package com.enation.app.base.component.widget.regions;

 import com.enation.app.base.core.service.IRegionsManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.util.StringUtil;
 import freemarker.core.Environment;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateDirectiveModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.io.Writer;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;










 public class RegionSelectDirective
   implements TemplateDirectiveModel
 {
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Object provinceobj = params.get("province_id");
     Object cityObj = params.get("city_id");
     Object regionObj = params.get("region_id");
     Object ctxObj = params.get("ctx");

     IRegionsManager regionsManager = (IRegionsManager)SpringContextHolder.getBean("regionsManager");

     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());
     freeMarkerPaser.setPageName("RegionsSelectWidget");

     List provinceList = new ArrayList();
     provinceList = regionsManager.listProvince();
     freeMarkerPaser.putData("provinceList", provinceList);

     freeMarkerPaser.putData("province_id", provinceobj);
     freeMarkerPaser.putData("city_id", cityObj);
     freeMarkerPaser.putData("region_id", regionObj);
     freeMarkerPaser.putData("ctx", ctxObj);


     String html = freeMarkerPaser.proessPageContent();

     if ((provinceobj != null) && (cityObj != null) && (cityObj != null)) {
       String province_id = params.get("province_id").toString();
       String city_id = params.get("city_id").toString();
       String region_id = params.get("region_id").toString();
       freeMarkerPaser.putData("ctx", ctxObj);

       if ((!StringUtil.isEmpty(province_id)) && (!StringUtil.isEmpty(city_id)) && (!StringUtil.isEmpty(region_id))) {
         html = html + "<script>$(function(){ RegionsSelect.load(" + province_id + "," + city_id + "," + region_id + ");});</script>";
       }
     }

     env.getOut().write(html.toString());
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\regions\RegionSelectDirective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */