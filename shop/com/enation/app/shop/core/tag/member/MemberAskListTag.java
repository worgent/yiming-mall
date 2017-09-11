 package com.enation.app.shop.core.tag.member;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.service.IMemberCommentManager;
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
 public class MemberAskListTag
   extends BaseFreeMarkerTag
 {
   private IMemberCommentManager memberCommentManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String page = request.getParameter("page");

     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
     if (member == null) {
       throw new TemplateModelException("未登陆不能使用此标签[MemberAskListTag]");
     }

     page = (page == null) || (page.equals("")) ? "1" : page;
     int pageSize = 5;
     Page commentsPage = this.memberCommentManager.getMemberComments(Integer.valueOf(page).intValue(), pageSize, 2, member.getMember_id().intValue());
     Long totalCount = Long.valueOf(commentsPage.getTotalCount());

     Map result = new HashMap();
     List commentsList = (List)commentsPage.getResult();
     commentsList = commentsList == null ? new ArrayList() : commentsList;


     result.put("totalCount", totalCount);
     result.put("pageSize", Integer.valueOf(pageSize));
     result.put("page", page);
     result.put("commentsList", commentsList);
     return result;
   }

   public IMemberCommentManager getMemberCommentManager() { return this.memberCommentManager; }

   public void setMemberCommentManager(IMemberCommentManager memberCommentManager) {
     this.memberCommentManager = memberCommentManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\member\MemberAskListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */