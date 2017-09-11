 package com.enation.app.b2b2c.core.tag.goods;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsTagManager;
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
 public class StoreTagsGoodsTag
   extends BaseFreeMarkerTag
 {
   private IStoreGoodsTagManager storeGoodsTagManager;
   private IStoreMemberManager storeMemberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String mark = (String)params.get("mark");


     Integer storeid = (Integer)params.get("storeid");
     if ((storeid == null) || (storeid.intValue() == 0)) {
       StoreMember storeMember = this.storeMemberManager.getStoreMember();
       storeid = storeMember.getStore_id();
     }
     Integer num = (Integer)params.get("num");
     if ((num == null) || (num.intValue() == 0)) {
       num = Integer.valueOf(getPageSize());
     }
     Map map = new HashMap();
     map.put("mark", mark);
     map.put("storeid", storeid);

     Map result = new HashMap();
     String page = request.getParameter("page");
     int pageSize = 10;
     page = (page == null) || (page.equals("")) ? "1" : page;
     Page webpage = new Page();

     webpage = this.storeGoodsTagManager.getGoodsList(map, getPage(), num.intValue());

     Long totalCount = Long.valueOf(webpage.getTotalCount());
     result.put("page", page);
     result.put("pageSize", Integer.valueOf(pageSize));
     result.put("totalCount", totalCount);
     result.put("goodsTag", webpage);
     result.put("list", webpage.getResult());
     return result;
   }

   public IStoreGoodsTagManager getStoreGoodsTagManager() { return this.storeGoodsTagManager; }

   public void setStoreGoodsTagManager(IStoreGoodsTagManager storeGoodsTagManager) {
     this.storeGoodsTagManager = storeGoodsTagManager;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\goods\StoreTagsGoodsTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */