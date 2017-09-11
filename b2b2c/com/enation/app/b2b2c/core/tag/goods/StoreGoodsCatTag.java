 package com.enation.app.b2b2c.core.tag.goods;

 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;





 @Component
 public class StoreGoodsCatTag
   extends BaseFreeMarkerTag
 {
   private IGoodsCatManager goodsCatManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Map result = new HashMap();
     int catid = StringUtil.toInt(params.get("catid").toString(), true);

     List<Cat> parentList = this.goodsCatManager.getParents(catid);

     Cat currentCat = (Cat)parentList.get(parentList.size() - 1);
     result.put("typeid", currentCat.getType_id());
     result.put("catid", Integer.valueOf(catid));
     result.put("parentList", parentList);
     result.put("cat", this.goodsCatManager.getById(catid));
     return result;
   }

   public IGoodsCatManager getGoodsCatManager() {
     return this.goodsCatManager;
   }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
     this.goodsCatManager = goodsCatManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\goods\StoreGoodsCatTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */