 package com.enation.app.b2b2c.core.tag.store;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberCommentManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;







 @Component
 public class StoreConsultListTag
   extends BaseFreeMarkerTag
 {
   private IStoreMemberCommentManager storeMemberCommentManager;
   private IStoreMemberManager storeMemberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     StoreMember member = this.storeMemberManager.getStoreMember();
     String page = request.getParameter("page");
     page = (page == null) || (page.equals("")) ? "1" : page;
     int pageSize = 10;
     Map map = new HashMap();
     String stype = request.getParameter("stype");
     String grade = request.getParameter("grade");
     if (request.getParameter("stype") == null) {
       stype = "0";
     }
     if (request.getParameter("grade") == null) {
       grade = "-1";
     }
     map.put("type", params.get("type").toString());
     map.put("status", request.getParameter("status"));
     map.put("grade", grade);
     map.put("replyStatus", request.getParameter("replyStatus"));
     map.put("keyword", request.getParameter("keyword"));
     map.put("mname", request.getParameter("mname"));
     map.put("gname", request.getParameter("gname"));
     map.put("content", request.getParameter("content"));
     map.put("stype", stype);
     Page cmmentList = this.storeMemberCommentManager.getAllComments(Integer.parseInt(page), pageSize, map, member.getStore_id());

     Long totalCount = Long.valueOf(cmmentList.getTotalCount());

     map.put("page", page);
     map.put("pageSize", Integer.valueOf(pageSize));
     map.put("totalCount", totalCount);
     map.put("cmmentList", cmmentList);

     return map;
   }

   public IStoreMemberCommentManager getStoreMemberCommentManager() { return this.storeMemberCommentManager; }

   public void setStoreMemberCommentManager(IStoreMemberCommentManager storeMemberCommentManager)
   {
     this.storeMemberCommentManager = storeMemberCommentManager;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\store\StoreConsultListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */