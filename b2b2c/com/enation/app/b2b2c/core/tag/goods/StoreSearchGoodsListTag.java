 package com.enation.app.b2b2c.core.tag.goods;

 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsManager;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import org.springframework.stereotype.Component;



 @Component
 public class StoreSearchGoodsListTag
   extends BaseFreeMarkerTag
 {
   private IStoreGoodsManager storeGoodsManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer num = (Integer)params.get("num");
     if ((num == null) || (num.intValue() == 0)) {
       num = Integer.valueOf(getPageSize());
     }
     Page webpage = this.storeGoodsManager.store_searchGoodsList(Integer.valueOf(getPage()), num, params);
     Long totalCount = Long.valueOf(webpage.getTotalCount());
     Map result = new HashMap();

     result.put("page", Integer.valueOf(getPage()));
     result.put("pageSize", num);
     result.put("totalCount", totalCount);
     result.put("storegoods", webpage);
     return result;
   }

   public IStoreGoodsManager getStoreGoodsManager() {
     return this.storeGoodsManager;
   }

   public void setStoreGoodsManager(IStoreGoodsManager storeGoodsManager) {
     this.storeGoodsManager = storeGoodsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\goods\StoreSearchGoodsListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */