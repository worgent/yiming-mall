 package com.enation.app.b2b2c.core.tag.goods;

 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import org.springframework.stereotype.Component;




 @Component
 public class GoodsStoreTag
   extends BaseFreeMarkerTag
 {
   private IStoreGoodsManager storeGoodsManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Map result = new HashMap();
     result.put("store", this.storeGoodsManager.getGoodsStore((Integer)params.get("goods_id")));
     return result;
   }

   public IStoreGoodsManager getStoreGoodsManager() { return this.storeGoodsManager; }

   public void setStoreGoodsManager(IStoreGoodsManager storeGoodsManager) {
     this.storeGoodsManager = storeGoodsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\goods\GoodsStoreTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */