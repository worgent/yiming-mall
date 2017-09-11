 package com.enation.app.shop.component.virtualcat.plugin;

 import com.enation.app.shop.component.virtualcat.model.VirtualCat;
 import com.enation.app.shop.component.virtualcat.service.IVirtualCatManager;
 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
 import com.enation.app.shop.core.plugin.search.SearchSelector;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import org.springframework.stereotype.Component;










 @Component
 public class VirtualCatSearchFilter
   extends AutoRegisterPlugin
   implements IGoodsSearchFilter
 {
   private IVirtualCatManager virtualCatManager;

   public List<SearchSelector> createSelectorList(Cat cat, String url, String urlFragment)
   {
     return null;
   }

   private String getCondition(VirtualCat vcat) {
     String condition = "";
     List<VirtualCat> children = this.virtualCatManager.listChildren(vcat.getCid().longValue());
     for (VirtualCat ccat : children) {
       condition = condition + " or seller_cids like '%," + ccat.getCid() + ",%'";
       condition = condition + getCondition(ccat);
     }
     return condition;
   }

   public void filter(StringBuffer sql, Cat cat, String urlFragment)
   {
     if ((!StringUtil.isEmpty(urlFragment)) && (!"0".equals(urlFragment))) {
       VirtualCat vcat = this.virtualCatManager.get(Integer.valueOf(urlFragment).intValue());
       String condition = "(seller_cids like '%," + vcat.getCid() + ",%'";
       condition = condition + getCondition(vcat);
       condition = condition + ")";
       sql.append(" and " + condition);
     }
   }


   public String getFilterId()
   {
     return "virtual";
   }

   public IVirtualCatManager getVirtualCatManager() {
     return this.virtualCatManager;
   }

   public void setVirtualCatManager(IVirtualCatManager virtualCatManager) {
     this.virtualCatManager = virtualCatManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\virtualcat\plugin\VirtualCatSearchFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */