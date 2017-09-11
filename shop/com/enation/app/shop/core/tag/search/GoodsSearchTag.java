 package com.enation.app.shop.core.tag.search;

 import com.enation.app.shop.core.service.IGoodsSearchManager2;
 import com.enation.app.shop.core.utils.UrlUtils;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;












 @Component
 @Scope("prototype")
 public class GoodsSearchTag
   extends BaseFreeMarkerTag
 {
   private IGoodsSearchManager2 goodsSearchManager2;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String uri = request.getServletPath();


     Integer pageSize = (Integer)params.get("pageSize");
     if (pageSize == null) { pageSize = Integer.valueOf(getPageSize());
     }
     String page_str = UrlUtils.getParamStringValue(uri, "page");

     int page = getPage();

     if ((page_str != null) && (!page_str.equals(""))) {
       page = Integer.valueOf(page_str).intValue();
     }

     Page webpage = this.goodsSearchManager2.search(page, pageSize.intValue(), uri);
     webpage.setCurrentPageNo(page);

     return webpage;
   }

   public IGoodsSearchManager2 getGoodsSearchManager2() { return this.goodsSearchManager2; }

   public void setGoodsSearchManager2(IGoodsSearchManager2 goodsSearchManager2) {
     this.goodsSearchManager2 = goodsSearchManager2;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\search\GoodsSearchTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */