 package com.enation.eop.resource.impl;

 import com.enation.eop.resource.IThemeUriManager;
 import com.enation.eop.resource.model.ThemeUri;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import java.util.Map;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;

 public class ThemeUriManagerImpl extends BaseSupport<ThemeUri> implements IThemeUriManager
 {
   public void clean()
   {
     this.baseDaoSupport.execute("truncate table themeuri", new Object[0]);
   }

   public ThemeUri get(Integer id) {
     return (ThemeUri)this.baseDaoSupport.queryForObject("select * from themeuri where id=?", ThemeUri.class, new Object[] { id });
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void edit(List<ThemeUri> uriList) {
     for (ThemeUri uri : uriList) {
       this.baseDaoSupport.update("themeuri", uri, "id=" + uri.getId());
     }
   }

   public List<ThemeUri> list(Map map) {
     String sql = "select * from themeuri";
     if (map != null) {
       String keyword = (String)map.get("keyword");
       if (!StringUtil.isEmpty(keyword)) {
         sql = sql + " where uri like '%" + keyword + "%'";
         sql = sql + " or path like '%" + keyword + "%'";
         sql = sql + " or pagename like '%" + keyword + "%'";
       }
     }
     return this.baseDaoSupport.queryForList(sql, ThemeUri.class, new Object[0]);
   }

   public ThemeUri getPath(String uri) {
     List<ThemeUri> list = list(null);

     for (ThemeUri themeUri : list) {
       if (themeUri.getUri().equals(uri)) {
         return themeUri;
       }
     }
     return null;
   }

   public void add(ThemeUri uri) {
     this.baseDaoSupport.insert("themeuri", uri);
   }

   public void delete(int id) {
     this.baseDaoSupport.execute("delete from themeuri where id=? ", new Object[] { Integer.valueOf(id) });
   }

   public void edit(ThemeUri themeUri) {
     this.baseDaoSupport.update("themeuri", themeUri, "id=" + themeUri.getId());
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\impl\ThemeUriManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */