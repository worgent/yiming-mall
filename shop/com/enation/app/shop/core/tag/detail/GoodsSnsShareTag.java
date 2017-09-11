 package com.enation.app.shop.core.tag.detail;

 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.RequestUtil;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;














 @Component
 @Scope("prototype")
 public class GoodsSnsShareTag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     Map goods = (Map)request.getAttribute("goods");
     if (goods == null) throw new RuntimeException("参数显示标签必须和商品详细显示标签同时存在");
     String default_img = (String)goods.get("small");
     if (StringUtil.isEmpty(default_img)) {
       default_img = EopSetting.IMG_SERVER_DOMAIN + "/images/no_picture.jpg";
     }
     else {
       default_img = UploadUtil.replacePath(default_img);
     }
     String title = (String)goods.get("name");
     String url = RequestUtil.getWholeUrl(request);
     title = title.replaceAll("\r", "").replaceAll("\n", "");
     Map data = new HashMap();
     data.put("title", title);
     data.put("url", url);
     data.put("img", default_img);
     return data;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\detail\GoodsSnsShareTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */