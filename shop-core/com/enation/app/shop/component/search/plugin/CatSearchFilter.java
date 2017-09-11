 package com.enation.app.shop.component.search.plugin;

 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
 import com.enation.app.shop.core.plugin.search.SearchSelector;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.app.shop.core.utils.UrlUtils;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.database.IDBRouter;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.StringUtil;
 import java.util.ArrayList;
 import java.util.List;
 import org.springframework.stereotype.Component;








 @Component
 public class CatSearchFilter
   extends AutoRegisterPlugin
   implements IGoodsSearchFilter
 {
   private IDBRouter baseDBRouter;
   private IGoodsCatManager goodsCatManager;

   public List<SearchSelector> createSelectorList(Cat cat, String url, String urlFragment)
   {
     if (cat == null) return null;
     List<SearchSelector> selectorList = new ArrayList();


     List<Cat> catList = null;

     if (cat != null) {
       catList = this.goodsCatManager.listChildren(cat.getCat_id());
     } else {
       catList = this.goodsCatManager.listChildren(Integer.valueOf(0));
     }





     SearchSelector allselector = new SearchSelector();
     allselector.setName(cat.getName());
     allselector.setUrl(UrlUtils.addUrl(url, "cat", cat.getCat_id().toString()));

     allselector.setSelected(true);



     selectorList.add(allselector);

     for (Cat child : catList) {
       SearchSelector selector = new SearchSelector();
       selector.setName(child.getName());
       selector.setUrl(UrlUtils.addUrl(url, "cat", child.getCat_id().toString()));
       allselector.setValue(urlFragment);
       if (child.getCat_id().toString().equals(urlFragment)) {
         selector.setSelected(true);
       } else {
         selector.setSelected(false);
       }
       selectorList.add(selector);
     }

     return selectorList;
   }

   public void filter(StringBuffer sql, Cat cat, String urlFragment) {
     FreeMarkerPaser.getInstance().putData("cat", cat);
     if (!StringUtil.isEmpty(urlFragment)) {
       String cat_path = cat.getCat_path();
       if (cat_path != null) {
         sql.append(" and  g.cat_id in(");
         sql.append("select c.cat_id from " + this.baseDBRouter.getTableName("goods_cat"));
         sql.append(" c where c.cat_path like '" + cat_path + "%')");
       }
     }
   }

   public IDBRouter getBaseDBRouter() {
     return this.baseDBRouter;
   }

   public void setBaseDBRouter(IDBRouter baseDBRouter) {
     this.baseDBRouter = baseDBRouter;
   }

   public String getFilterId() {
     return "cat";
   }

   public String getAuthor()
   {
     return "kingapex";
   }

   public String getId() {
     return "catSearchFilter";
   }

   public String getName() {
     return "商品分类筛选器";
   }

   public String getType() {
     return "searchFilter";
   }

   public String getVersion() {
     return "1.0";
   }


   public void perform(Object... params) {}


   public void register() {}


   public IGoodsCatManager getGoodsCatManager()
   {
     return this.goodsCatManager;
   }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
     this.goodsCatManager = goodsCatManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\search\plugin\CatSearchFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */