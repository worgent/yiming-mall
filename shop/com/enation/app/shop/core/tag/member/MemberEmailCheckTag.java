 package com.enation.app.shop.core.tag.member;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.EncryptionUtil1;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;





 @Component
 @Scope("prototype")
 public class MemberEmailCheckTag
   extends BaseFreeMarkerTag
 {
   private IMemberManager memberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Map result = new HashMap();
     try {
       String s = ThreadContextHolder.getHttpRequest().getParameter("s");
       String str = EncryptionUtil1.authcode(s, "DECODE", "", 0);
       String[] array = StringUtils.split(str, ",");
       if (array.length != 2) throw new RuntimeException("验证字串不正确");
       int memberid = Integer.valueOf(array[0]).intValue();
       long regtime = Long.valueOf(array[1]).longValue();

       Member member = this.memberManager.get(Integer.valueOf(memberid));
       if (member.getRegtime().longValue() != regtime) {
         result.put("result", Integer.valueOf(0));
         result.put("message", "验证字串不正确");
         return result;
       }
       if (member.getIs_cheked().intValue() == 0) {
         this.memberManager.checkEmailSuccess(member);
         result.put("result", Integer.valueOf(1));
         result.put("message", member.getUname() + "您好，您的邮箱验证成功!");
       } else {
         result.put("result", Integer.valueOf(0));
         result.put("message", member.getUname() + "您好，验证失败，您已经验证过该邮箱!");
       }
     } catch (RuntimeException e) {
       result.put("result", Integer.valueOf(0));
       result.put("message", "验证地址不正确");
     }
     return result;
   }

   public IMemberManager getMemberManager() {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\member\MemberEmailCheckTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */