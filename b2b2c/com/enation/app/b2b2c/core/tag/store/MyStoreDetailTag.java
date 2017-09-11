 package com.enation.app.b2b2c.core.tag.store;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.model.store.Store;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.b2b2c.core.service.store.IStoreManager;
 import com.enation.eop.processor.core.UrlNotFoundException;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.io.IOException;
 import java.util.Map;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.stereotype.Component;






 @Component
 public class MyStoreDetailTag
   extends BaseFreeMarkerTag
 {
   private IStoreManager storeManager;
   private IStoreMemberManager storeMemberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Store store = new Store();
     if (params.get("type") != null) {
       store = this.storeManager.getStore(Integer.valueOf(Integer.parseInt(params.get("store_id").toString())));
     }
     else {
       StoreMember member = this.storeMemberManager.getStoreMember();
       if (member == null) {
         HttpServletResponse response = ThreadContextHolder.getHttpResponse();
         try {
           response.sendRedirect("/store/login.html");
         } catch (IOException e) {
           throw new UrlNotFoundException();
         }
       } else if (member.getStore_id() == null) {
         HttpServletResponse response = ThreadContextHolder.getHttpResponse();
         try {
           response.sendRedirect("/index.html");
         } catch (IOException e) {
           throw new UrlNotFoundException();
         }
       }
       store = this.storeManager.getStore(member.getStore_id());
     }
     return store;
   }

   public IStoreManager getStoreManager() { return this.storeManager; }

   public void setStoreManager(IStoreManager storeManager) {
     this.storeManager = storeManager;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\store\MyStoreDetailTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */