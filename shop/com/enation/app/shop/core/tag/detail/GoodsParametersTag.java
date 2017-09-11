 package com.enation.app.shop.core.tag.detail;

 import com.enation.app.shop.core.model.support.ParamGroup;
 import com.enation.app.shop.core.service.GoodsTypeUtil;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;




















 @Component
 @Scope("prototype")
 public class GoodsParametersTag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = getRequest();
     Map goods = (Map)request.getAttribute("goods");
     String goodParams = (String)goods.get("params");
     Map result = new HashMap();
     if ((goodParams != null) && (!goodParams.equals(""))) {
       ParamGroup[] paramList = GoodsTypeUtil.converFormString(goodParams);

       result.put("paramList", paramList);


       if ((paramList != null) && (paramList.length > 0)) {
         result.put("hasParam", Boolean.valueOf(true));
       } else
         result.put("hasParam", Boolean.valueOf(false));
     } else {
       result.put("hasParam", Boolean.valueOf(false));
     }

     return result;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\detail\GoodsParametersTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */