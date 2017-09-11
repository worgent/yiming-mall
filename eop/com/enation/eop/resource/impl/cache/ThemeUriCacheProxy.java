 package com.enation.eop.resource.impl.cache;

 import com.enation.eop.resource.IThemeUriManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.resource.model.ThemeUri;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.framework.cache.AbstractCacheProxy;
 import com.enation.framework.cache.ICache;
 import java.util.List;
 import java.util.Map;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;









 public class ThemeUriCacheProxy
   extends AbstractCacheProxy<List<ThemeUri>>
   implements IThemeUriManager
 {
   private IThemeUriManager themeUriManager;
   public static final String LIST_KEY_PREFIX = "theme_uri_list_";

   public void clean()
   {
     this.themeUriManager.clean();
   }

   public void add(ThemeUri uri) {
     this.themeUriManager.add(uri);
     cleanCache();
   }

   public void edit(List<ThemeUri> uriList) {
     this.themeUriManager.edit(uriList);
     cleanCache();
   }

   public void edit(ThemeUri themeUri) {
     this.themeUriManager.edit(themeUri);
     cleanCache();
   }

   public ThemeUriCacheProxy(IThemeUriManager themeUriManager) {
     super("themeUriCache");
     this.themeUriManager = themeUriManager;
   }

   private void cleanCache() {
     EopSite site = EopContext.getContext().getCurrentSite();
     Integer userid = site.getUserid();
     Integer siteid = site.getId();
     this.cache.remove("theme_uri_list_" + userid + "_" + siteid);
   }

   public List<ThemeUri> list(Map map) {
     EopSite site = EopContext.getContext().getCurrentSite();
     Integer userid = site.getUserid();
     Integer siteid = site.getId();
     List<ThemeUri> uriList = (List)this.cache.get("theme_uri_list_" + userid + "_" + siteid);

     if ((uriList == null) || (map != null)) {
       uriList = this.themeUriManager.list(map);

       this.cache.put("theme_uri_list_" + userid + "_" + siteid, uriList);
     }






     return uriList;
   }




   public ThemeUri getPath(String uri)
   {
     if (uri.equals("/")) {
       uri = "/index.html";
     }




     List<ThemeUri> uriList = list(null);
     for (ThemeUri themeUri : uriList) {
       Matcher m = themeUri.getPattern().matcher(uri);





       if (m.find())
       {






         return themeUri;
       }
     }



     return null;
   }

   public void delete(int id)
   {
     this.themeUriManager.delete(id);
     cleanCache();
   }

   public ThemeUri get(Integer id) {
     return this.themeUriManager.get(id);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\impl\cache\ThemeUriCacheProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */