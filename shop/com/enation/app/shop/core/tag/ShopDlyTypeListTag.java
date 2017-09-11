 package com.enation.app.shop.core.tag;

 import com.enation.app.shop.core.model.DlyType;
 import com.enation.app.shop.core.service.ICartManager;
 import com.enation.app.shop.core.service.IDlyTypeManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;










 @Component
 @Scope("prototype")
 public class ShopDlyTypeListTag
   extends BaseFreeMarkerTag
 {
   private ICartManager cartManager;
   private IDlyTypeManager dlyTypeManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String sessionid = request.getSession().getId();
     Double orderPrice = this.cartManager.countGoodsTotal(sessionid);
     Double weight = this.cartManager.countGoodsWeight(sessionid);
     Integer regionid = (Integer)params.get("regionid");
     List<DlyType> dlyTypeList = this.dlyTypeManager.list(weight, orderPrice, regionid.toString());
     return dlyTypeList;
   }

   public ICartManager getCartManager() {
     return this.cartManager;
   }

   public void setCartManager(ICartManager cartManager) {
     this.cartManager = cartManager;
   }

   public IDlyTypeManager getDlyTypeManager() {
     return this.dlyTypeManager;
   }

   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
     this.dlyTypeManager = dlyTypeManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\ShopDlyTypeListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */