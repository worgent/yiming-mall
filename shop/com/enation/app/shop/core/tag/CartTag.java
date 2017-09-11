 package com.enation.app.shop.core.tag;

 import com.enation.app.shop.core.service.ICartManager;
 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import freemarker.template.TemplateMethodModel;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;














 @Component
 @Scope("prototype")
 public class CartTag
   implements TemplateMethodModel
 {
   private ICartManager cartManager;

   public Object exec(List args)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     this.cartManager = ((ICartManager)SpringContextHolder.getBean("cartManager"));
     String sessionid = request.getSession().getId();
     List goodsList = this.cartManager.listGoods(sessionid);

     return goodsList;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\CartTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */