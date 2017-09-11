 package com.enation.app.shop.core.tag.nav;

 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.app.shop.core.utils.UrlUtils;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;












 @Component
 @Scope("prototype")
 public class SearchNavTag
   extends BaseFreeMarkerTag
 {
   private IGoodsCatManager goodsCatManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String url = request.getServletPath();
     Cat cat = null;
     Map result = new HashMap();
     boolean isSearch = false;
     String catidstr = UrlUtils.getParamStringValue(url, "cat");
     if ((!StringUtil.isEmpty(catidstr)) && (!"0".equals(catidstr))) {
       Integer catid = Integer.valueOf(catidstr);
       cat = this.goodsCatManager.getById(catid.intValue());
     }
     if (cat == null) {
       isSearch = true;
     }
     result.put("isSearch", Boolean.valueOf(isSearch));
     result.put("cat", cat);
     return result;
   }

   public IGoodsCatManager getGoodsCatManager() { return this.goodsCatManager; }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
     this.goodsCatManager = goodsCatManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\nav\SearchNavTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */