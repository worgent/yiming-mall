 package com.enation.app.shop.component.search.plugin;

 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
 import com.enation.app.shop.core.plugin.search.SearchSelector;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.util.List;
 import org.springframework.stereotype.Component;











 @Component
 public class StringPropertySearchFilter
   extends AutoRegisterPlugin
   implements IGoodsSearchFilter
 {
   public void register() {}

   public List<SearchSelector> createSelectorList(Cat cat, String url, String urlFragment)
   {
     return null;
   }



   public void filter(StringBuffer sql, Cat cat, String urlFragment) {}



   public String getFilterId()
   {
     return "sprop";
   }


   public String getAuthor()
   {
     return "kingapex";
   }


   public String getId()
   {
     return "stringPropertySearchFilter";
   }


   public String getName()
   {
     return "字串属性搜索过虑器";
   }


   public String getType()
   {
     return "searchFilter";
   }


   public String getVersion()
   {
     return "1.0";
   }

   public void perform(Object... params) {}
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\search\plugin\StringPropertySearchFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */