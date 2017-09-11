 package com.enation.app.shop.core.tag.member;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.base.core.model.MemberLv;
 import com.enation.app.shop.core.service.IMemberLvManager;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.app.shop.core.service.IMemberPointManger;
 import com.enation.app.shop.core.service.IPointHistoryManager;
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
 public class MemberPointTag
   extends BaseFreeMarkerTag
 {
   private IPointHistoryManager pointHistoryManager;
   private IMemberLvManager memberLvManager;
   private IMemberManager memberManager;
   private IMemberPointManger memberPointManger;
   private String action;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Map data = new HashMap();
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String action = request.getParameter("action");

     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
     if (member == null) {
       throw new TemplateModelException("未登陆不能使用此标签[MemberPointTag]");
     }

     member = this.memberManager.get(member.getMember_id());
     if ((action == null) || (action.equals("")))
     {

       data.put("freezepoint", Integer.valueOf(this.memberPointManger.getFreezePointByMemberId(member.getMember_id().intValue())));
       data.put("freezemp", Integer.valueOf(this.memberPointManger.getFreezeMpByMemberId(member.getMember_id().intValue())));
       data.put("member", member);


       MemberLv memberLv = this.memberLvManager.get(member.getLv_id());
       data.put("memberLv", memberLv);


       MemberLv nextLv = this.memberLvManager.getNextLv(member.getPoint().intValue());
       if (nextLv != null) {
         data.put("nextLv", nextLv);
       } else {
         data.put("nextLv", memberLv);
       }
     }
     else if (action.equals("list"))
     {

       String page = request.getParameter("page");
       page = (page == null) || (page.equals("")) ? "1" : page;
       int pageSize = 20;
       Page pointHistoryPage = this.pointHistoryManager.pagePointHistory(Integer.valueOf(page).intValue(), pageSize);

       List pointHistoryList = (List)pointHistoryPage.getResult();
       pointHistoryList = pointHistoryList == null ? new ArrayList() : pointHistoryList;


       data.put("totalCount", Long.valueOf(pointHistoryPage.getTotalCount()));
       data.put("pageSize", Integer.valueOf(pageSize));
       data.put("pageNo", page);
       data.put("pointHistoryList", pointHistoryList);


     }
     else if (action.equals("freeze"))
     {

       String page = request.getParameter("page");
       page = (page == null) || (page.equals("")) ? "1" : page;
       int pageSize = 20;
       Page pointHistoryPage = this.pointHistoryManager.pagePointFreeze(Integer.valueOf(page).intValue(), pageSize);

       List pointFreezeList = (List)pointHistoryPage.getResult();
       pointFreezeList = pointFreezeList == null ? new ArrayList() : pointFreezeList;


       data.put("totalCount", Long.valueOf(pointHistoryPage.getTotalCount()));
       data.put("pageSize", Integer.valueOf(pageSize));
       data.put("pageNo", page);
       data.put("pointFreezeList", pointFreezeList);
       data.put("point", member.getPoint());
     }

     return data;
   }


   public IPointHistoryManager getPointHistoryManager()
   {
     return this.pointHistoryManager;
   }

   public void setPointHistoryManager(IPointHistoryManager pointHistoryManager) {
     this.pointHistoryManager = pointHistoryManager;
   }

   public IMemberLvManager getMemberLvManager() {
     return this.memberLvManager;
   }

   public void setMemberLvManager(IMemberLvManager memberLvManager) {
     this.memberLvManager = memberLvManager;
   }

   public IMemberManager getMemberManager() {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }

   public IMemberPointManger getMemberPointManger() {
     return this.memberPointManger;
   }

   public void setMemberPointManger(IMemberPointManger memberPointManger) {
     this.memberPointManger = memberPointManger;
   }

   public String getAction() {
     return this.action;
   }

   public void setAction(String action) {
     this.action = action;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\member\MemberPointTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */