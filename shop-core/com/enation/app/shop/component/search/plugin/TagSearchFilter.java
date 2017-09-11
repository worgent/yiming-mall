 package com.enation.app.shop.component.search.plugin;

 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
 import com.enation.app.shop.core.plugin.search.IPutWidgetParamsEvent;
 import com.enation.app.shop.core.plugin.search.SearchSelector;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;









 @Component
 public class TagSearchFilter
   extends AutoRegisterPlugin
   implements IGoodsSearchFilter, IPutWidgetParamsEvent
 {
   public List<SearchSelector> createSelectorList(Cat cat, String url, String urlFragment)
   {
     return null;
   }

   public void filter(StringBuffer sql, Cat cat, String urlFragment)
   {
     if (!StringUtil.isEmpty(urlFragment)) {
       sql.append(" and goods_id in (select rel_id from es_tag_rel where tag_id in(" + urlFragment + ") )");
     }
   }

   public void putParams(Map<String, Object> params, String urlFragment, String url)
   {
     if (!StringUtil.isEmpty(urlFragment)) {
       params.put("tag", urlFragment);
     }
   }


   public String getFilterId()
   {
     return "tag";
   }


   public String getAuthor()
   {
     return "kingapex";
   }


   public String getId()
   {
     return "tagSearchFilter";
   }


   public String getName()
   {
     return "商品标签搜索过虑器";
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

   public void register() {}
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\search\plugin\TagSearchFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */