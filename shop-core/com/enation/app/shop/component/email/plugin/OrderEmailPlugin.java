 package com.enation.app.shop.component.email.plugin;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.support.CartItem;
 import com.enation.app.shop.core.plugin.order.IAfterOrderCreateEvent;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.jms.EmailModel;
 import com.enation.framework.jms.EmailProducer;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.RequestUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;











 @Component
 public class OrderEmailPlugin
   extends AutoRegisterPlugin
   implements IAfterOrderCreateEvent
 {
   private EmailProducer mailMessageProducer;

   public void onAfterOrderCreate(Order order, List<CartItem> itemList, String sessionid)
   {
     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
     if (member != null) {
       String email = member.getEmail();
       if (StringUtil.isEmpty(email)) {
         return;
       }

       EopSite site = EopContext.getContext().getCurrentSite();

       HttpServletRequest request = ThreadContextHolder.getHttpRequest();

       String domain = RequestUtil.getDomain();

       EmailModel emailModel = new EmailModel();
       emailModel.getData().put("username", member.getUname());
       emailModel.getData().put("sn", order.getSn());
       emailModel.getData().put("createtime", DateUtil.toString(order.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
       emailModel.getData().put("sitename", site.getSitename());
       emailModel.getData().put("logo", site.getLogofile());
       emailModel.getData().put("domain", domain);
       emailModel.getData().put("orderid", order.getOrder_id());

       emailModel.setTitle("订单提交成功--" + site.getSitename());
       emailModel.setEmail(member.getEmail());
       emailModel.setTemplate("order_create_email_template.html");
       emailModel.setEmail_type("新订单成功提醒");
       this.mailMessageProducer.send(emailModel);
     }
   }

   public EmailProducer getMailMessageProducer()
   {
     return this.mailMessageProducer;
   }

   public void setMailMessageProducer(EmailProducer mailMessageProducer) {
     this.mailMessageProducer = mailMessageProducer;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\email\plugin\OrderEmailPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */