 package com.enation.app.b2b2c.core.service.member.impl;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.database.IDaoSupport;
 import org.springframework.stereotype.Component;

 @Component
 public class StoreMemberManager extends BaseSupport implements com.enation.app.b2b2c.core.service.member.IStoreMemberManager
 {
   public void edit(StoreMember member)
   {
     this.baseDaoSupport.update("member", member, "member_id=" + member.getMember_id());
     ThreadContextHolder.getSessionContext().setAttribute("curr_store_member", member);
   }




   public StoreMember getMember(Integer member_id)
   {
     String sql = "select * from es_member where member_id=?";
     return (StoreMember)this.daoSupport.queryForObject(sql, StoreMember.class, new Object[] { member_id });
   }




   public StoreMember getStoreMember()
   {
     StoreMember member = (StoreMember)ThreadContextHolder.getSessionContext().getAttribute("curr_store_member");
     return member;
   }




   public StoreMember getMember(String member_name)
   {
     String sql = "select * from es_member where uname=?";
     return (StoreMember)this.daoSupport.queryForObject(sql, StoreMember.class, new Object[] { member_name });
   }

     @Override
     public StoreMember getMemberByStoreId(int storeId) {
         String sql = "select * from es_member where store_id=?";
         return (StoreMember)this.daoSupport.queryForObject(sql, StoreMember.class, new Object[] { storeId });
     }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\member\impl\StoreMemberManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */