 package com.enation.app.shop.core.tag.member;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.service.IGnotifyManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;










 @Component
 @Scope("prototype")
 public class MemberGnotifyListTag
   extends BaseFreeMarkerTag
 {
   private IGnotifyManager gnotifyManager;

   public Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();

     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
     if (member == null) {
       throw new TemplateModelException("未登陆不能使用此标签[MemberPointTag]");
     }

     Map result = new HashMap();

     String page = request.getParameter("page");
     page = page == null ? "1" : page;
     int pageSize = 20;
     Page gnotifyPage = this.gnotifyManager.pageGnotify(Integer.valueOf(page).intValue(), pageSize);

     Long totalCount = Long.valueOf(gnotifyPage.getTotalCount());
     Long pageCount = Long.valueOf(gnotifyPage.getTotalPageCount());
     List gnotifyList = (List)gnotifyPage.getResult();
     gnotifyList = gnotifyList == null ? new ArrayList() : gnotifyList;
     result.put("totalCount", totalCount);
     result.put("pageSize", Integer.valueOf(pageSize));
     result.put("page", page);

     result.put("gnotifyList", gnotifyList);
     result.put("pageCount", pageCount);
     return result;
   }

   public IGnotifyManager getGnotifyManager() {
     return this.gnotifyManager;
   }

   public void setGnotifyManager(IGnotifyManager gnotifyManager) {
     this.gnotifyManager = gnotifyManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\member\MemberGnotifyListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */