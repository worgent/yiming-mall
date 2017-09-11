 package com.enation.app.shop.core.tag.comment;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.service.IMemberOrderItemManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;


















 @Component
 @Scope("prototype")
 public class MemberIsCommentTag
   extends BaseFreeMarkerTag
 {
   private IMemberOrderItemManager memberOrderItemManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Map result = new HashMap(5);
     Integer goods_id = (Integer)params.get("goods_id");
     result.put("goods_id", goods_id);
     Member member = UserServiceFactory.getUserService().getCurrentMember();
     if (member == null) {
       result.put("isLogin", Boolean.valueOf(false));
     } else {
       result.put("isLogin", Boolean.valueOf(true));
       int buyCount = this.memberOrderItemManager.count(member.getMember_id().intValue(), goods_id.intValue());
       int commentCount = this.memberOrderItemManager.count(member.getMember_id().intValue(), goods_id.intValue(), 1);
       result.put("isBuy", Boolean.valueOf(buyCount > 0));
       result.put("isCommented", Boolean.valueOf(commentCount >= buyCount));
     }
     return result;
   }

   public IMemberOrderItemManager getMemberOrderItemManager() {
     return this.memberOrderItemManager;
   }

   public void setMemberOrderItemManager(IMemberOrderItemManager memberOrderItemManager) {
     this.memberOrderItemManager = memberOrderItemManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\comment\MemberIsCommentTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */