 package com.enation.app.shop.core.tag.order;

 import com.enation.app.base.core.model.Member;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.IntegerMapper;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;

















 @Component
 @Scope("prototype")
 public class OrderNumberTag
   extends BaseFreeMarkerTag
 {
   private IDaoSupport daoSupport;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
     if (member == null) {
       throw new TemplateModelException("未登陆不能使用此标签[OrderNumberTag]");
     }
     String sql = "select count(0) num,status from es_order where member_id=? group by status";
     List<Map> list = this.daoSupport.queryForList(sql, new Object[] { member.getMember_id() });
     Map data = new HashMap();

     for (Map map : list) {
       int status = ((Integer)map.get("status")).intValue();
       long num = ((Long)map.get("num")).longValue();
       data.put("" + status, Long.valueOf(num));
     }


     sql = "select count(0) from es_order where status!=8 AND pay_status=0 and member_id=?";
     List<Integer> noPayList = this.daoSupport.queryForList(sql, new IntegerMapper(), new Object[] { member.getMember_id() });
     if (noPayList.isEmpty()) {
       data.put("0", Integer.valueOf(0));
     } else {
       data.put("0", noPayList.get(0));
     }
     return data;
   }

   public IDaoSupport getDaoSupport() { return this.daoSupport; }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\order\OrderNumberTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */