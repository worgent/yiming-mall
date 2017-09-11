 package com.enation.app.shop.core.tag.comment;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.service.IMemberCommentManager;
 import com.enation.app.shop.core.service.IMemberOrderItemManager;
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
 public class MemberWaitCommontListTag
   extends BaseFreeMarkerTag
 {
   private IMemberCommentManager memberCommentManager;
   private IMemberOrderItemManager memberOrderItemManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String page = request.getParameter("page");
     page = (page == null) || (page.equals("")) ? "1" : page;
     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
     if (member == null) {
       throw new TemplateModelException("未登陆不能使用此标签[MemberWaitCommontListTag]");
     }
     int pageSize = 5;

     Map result = new HashMap();

     Page goodsPage = this.memberOrderItemManager.getGoodsList(member.getMember_id().intValue(), 0, Integer.valueOf(page).intValue(), pageSize);

     List waitcommentsList = (List)goodsPage.getResult();
     waitcommentsList = waitcommentsList == null ? new ArrayList() : waitcommentsList;

     result.put("goodsPage", goodsPage);
     result.put("totalCount", Long.valueOf(goodsPage.getTotalCount()));
     result.put("pageSize", Integer.valueOf(pageSize));
     result.put("page", page);
     result.put("waitcommentsList", waitcommentsList);
     return result;
   }

   public IMemberCommentManager getMemberCommentManager() {
     return this.memberCommentManager;
   }

   public void setMemberCommentManager(IMemberCommentManager memberCommentManager) {
     this.memberCommentManager = memberCommentManager;
   }

   public IMemberOrderItemManager getMemberOrderItemManager() {
     return this.memberOrderItemManager;
   }

   public void setMemberOrderItemManager(IMemberOrderItemManager memberOrderItemManager)
   {
     this.memberOrderItemManager = memberOrderItemManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\comment\MemberWaitCommontListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */