 package com.enation.app.b2b2c.component.plugin;

 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
 import com.enation.app.shop.core.plugin.search.SearchSelector;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import org.springframework.stereotype.Component;







 @Component
 public class StoreSearchFilter
   extends AutoRegisterPlugin
   implements IGoodsSearchFilter
 {
   public List<SearchSelector> createSelectorList(Cat cat, String url, String urlFragment)
   {
     return null;
   }

   public void filter(StringBuffer sql, Cat cat, String urlFragment)
   {
     String store_id = urlFragment == null ? "" : urlFragment;
     if (!StringUtil.isEmpty(store_id)) {
       sql.append(" and store_id=" + store_id);
     }
   }

   public String getFilterId()
   {
     return "store";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\component\plugin\StoreSearchFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */