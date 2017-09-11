 package com.enation.app.base.core.action;

 import com.enation.eop.resource.IThemeUriManager;
 import com.enation.eop.resource.model.ThemeUri;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.action.WWAction;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;

 public class ThemeUriAction extends WWAction
 {
   private IThemeUriManager themeUriManager;
   private List uriList;
   private ThemeUri themeUri;
   private int id;
   private int[] ids;
   private String[] uri;
   private String[] path;
   private String[] pagename;
   private int[] point;
   private int[] httpcache;
   private String keyword;

   public String list()
   {
     return "list";
   }

   public String listJson() {
     Map map = new HashMap();
     map.put("keyword", this.keyword);
     this.uriList = this.themeUriManager.list(map);
     showGridJson(this.uriList);
     return "json_message";
   }



   public String add() { return "add"; }

   public String edit() {
     this.themeUri = this.themeUriManager.get(Integer.valueOf(this.id));
     return "edit";
   }

   public String saveAdd() {
     try {
       this.themeUriManager.add(this.themeUri);
       showSuccessJson("添加成功");
     } catch (RuntimeException e) {
       showErrorJson("失败:" + e.getMessage());
     }
     return "json_message";
   }


   public String saveEdit()
   {
     if ((EopSetting.IS_DEMO_SITE) &&
       (this.id <= 6)) {
       showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }

     try
     {
       this.themeUriManager.edit(this.themeUri);
       showSuccessJson("修改成功");
     } catch (RuntimeException e) {
       showErrorJson("修改失败:" + e.getMessage());
     }
     return "json_message";
   }

   public String batchEdit()
   {
     try {
       List<ThemeUri> uriList = new java.util.ArrayList();
       if (this.uri != null) {
         int i = 0; for (int len = this.uri.length; i < len; i++) {
           ThemeUri themeUri = new ThemeUri();
           themeUri.setUri(this.uri[i]);
           themeUri.setId(Integer.valueOf(this.ids[i]));
           themeUri.setPath(this.path[i]);
           themeUri.setPagename(this.pagename[i]);
           themeUri.setPoint(this.point[i]);
           themeUri.setHttpcache(this.httpcache[i]);
           uriList.add(themeUri);
         }
       }
       this.themeUriManager.edit(uriList);
       showSuccessJson("保存修改成功");
     } catch (RuntimeException e) {
       showErrorJson("失败:" + e.getMessage());
     }
     return "json_message";
   }


   public String delete()
   {
     if ((EopSetting.IS_DEMO_SITE) &&
       (this.id <= 6)) {
       showErrorJson("抱歉，当前为演示站点，以不能删除这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }

     try
     {
       this.themeUriManager.delete(this.id);
       showSuccessJson("删除成功");
     } catch (RuntimeException e) {
       showErrorJson("删除失败:" + e.getMessage());
     }
     return "json_message";
   }

   public IThemeUriManager getThemeUriManager() {
     return this.themeUriManager;
   }

   public void setThemeUriManager(IThemeUriManager themeUriManager) {
     this.themeUriManager = themeUriManager;
   }

   public List getUriList() {
     return this.uriList;
   }

   public void setUriList(List uriList) {
     this.uriList = uriList;
   }

   public ThemeUri getThemeUri() {
     return this.themeUri;
   }

   public void setThemeUri(ThemeUri themeUri) {
     this.themeUri = themeUri;
   }

   public String[] getUri() {
     return this.uri;
   }

   public void setUri(String[] uri) {
     this.uri = uri;
   }

   public String[] getPath() {
     return this.path;
   }

   public void setPath(String[] path) {
     this.path = path;
   }

   public String[] getPagename() {
     return this.pagename;
   }

   public void setPagename(String[] pagename) {
     this.pagename = pagename;
   }

   public int[] getPoint() {
     return this.point;
   }

   public void setPoint(int[] point) {
     this.point = point;
   }

   public int getId() {
     return this.id;
   }

   public void setId(int id) {
     this.id = id;
   }

   public int[] getIds() {
     return this.ids;
   }

   public void setIds(int[] ids) {
     this.ids = ids;
   }

   public int[] getHttpcache()
   {
     return this.httpcache;
   }

   public void setHttpcache(int[] httpcache)
   {
     this.httpcache = httpcache;
   }

   public String getKeyword() { return this.keyword; }

   public void setKeyword(String keyword) {
     this.keyword = keyword;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\ThemeUriAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */