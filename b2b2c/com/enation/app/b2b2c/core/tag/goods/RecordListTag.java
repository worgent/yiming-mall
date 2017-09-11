 package com.enation.app.b2b2c.core.tag.goods;

 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsManager;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;












 @Component
 public class RecordListTag
   extends BaseFreeMarkerTag
 {
   private IStoreGoodsManager storeGoodsManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     int pageNo = getPage();
     int pageSize = getPageSize();
     Integer goods_id = (Integer)params.get("goods_id");
     List list = this.storeGoodsManager.transactionList(Integer.valueOf(pageNo), Integer.valueOf(pageSize), goods_id);

     Page page = new Page(0L, this.storeGoodsManager.transactionCount(goods_id), pageSize, list);
     page.setCurrentPageNo(pageNo);
     return page;
   }

   public IStoreGoodsManager getStoreGoodsManager() { return this.storeGoodsManager; }

   public void setStoreGoodsManager(IStoreGoodsManager storeGoodsManager) {
     this.storeGoodsManager = storeGoodsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\goods\RecordListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */