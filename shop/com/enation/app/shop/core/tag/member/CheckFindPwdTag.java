 package com.enation.app.shop.core.tag.member;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.EncryptionUtil1;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.stereotype.Component;





 @Component
 public class CheckFindPwdTag
   extends BaseFreeMarkerTag
 {
   private IMemberManager memberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Map result = new HashMap();
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String s = request.getParameter("s");
     if (s == null) {
       result.put("type", Integer.valueOf(1));
       result.put("message", "非法链接地址,请重新找回");
       return result;
     }
     String str = EncryptionUtil1.authcode(s, "DECODE", "", 0);
     String[] array = StringUtils.split(str, ",");
     if (array.length != 2) {
       result.put("type", Integer.valueOf(1));
       result.put("message", "验证字串不正确,请重新找回");
       return result;
     }
     int memberid = Integer.valueOf(array[0]).intValue();
     long regtime = Long.valueOf(array[1]).longValue();

     Member member = this.memberManager.get(Integer.valueOf(memberid));
     if ((member == null) || (member.getRegtime().longValue() != regtime)) {
       result.put("type", Integer.valueOf(1));
       result.put("message", "验证字串不正确,请重新找回");
       return result;
     }
     if ((member.getFind_code() == null) || ("".equals(member.getFind_code())) || (member.getFind_code().length() != 10)) {
       result.put("type", Integer.valueOf(1));
       result.put("message", "地址已经过期,请重新找回");
       return result;
     }
     int time = Integer.parseInt(member.getFind_code()) + 3600;
     if (DateUtil.getDateline() > time) {
       result.put("type", Integer.valueOf(1));
       result.put("message", "地址已经过期,请重新找回");
       return result;
     }

     result.put("type", Integer.valueOf(0));
     result.put("s", s);
     result.put("email", member.getEmail());
     return result;
   }

   public IMemberManager getMemberManager() { return this.memberManager; }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\member\CheckFindPwdTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */