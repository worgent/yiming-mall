 package com.enation.app.cms.core.plugin;

 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.model.DataModel;
 import com.enation.framework.plugin.AutoRegisterPluginsBundle;
 import com.enation.framework.plugin.IPlugin;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;





 public class ArticlePluginBundle
   extends AutoRegisterPluginsBundle
 {
   public String getName()
   {
     return "文章插件桩";
   }






   public AbstractFieldPlugin getFieldPlugin(DataField field)
   {
     List<IPlugin> plugins = getPlugins();

     if (plugins != null) {
       for (IPlugin plugin : plugins) {
         if ((plugin instanceof AbstractFieldPlugin)) {
           AbstractFieldPlugin fieldPlugin = (AbstractFieldPlugin)plugin;
           if (fieldPlugin.getId().equals(field.getShow_form())) {
             return fieldPlugin;
           }
         }
       }
     }
     return null;
   }

   public void onSave(Map article, DataField field) {
     List<IPlugin> plugins = getPlugins();
     try
     {
       if (plugins != null) {
         for (IPlugin plugin : plugins) {
           if ((plugin instanceof AbstractFieldPlugin)) {
             AbstractFieldPlugin fieldPlugin = (AbstractFieldPlugin)plugin;
             if (fieldPlugin.getId().equals(field.getShow_form())) {
               fieldPlugin.onSave(article, field);
             }
           }
         }
       }
     } catch (Exception e) {
       e.printStackTrace();
     }
   }

   public void onSave(Map<String, Object> article, DataModel dataModel, int dataSaveType) {
     List<IPlugin> plugins = getPlugins();
     try
     {
       if (plugins != null) {
         for (IPlugin plugin : plugins) {
           if ((plugin instanceof IDataSaveEvent)) {
             IDataSaveEvent dataSaveEvent = (IDataSaveEvent)plugin;
             dataSaveEvent.onSave(article, dataModel, dataSaveType);
           }
         }
       }
     } catch (Exception e) {
       e.printStackTrace();
     }
   }






   public void onDelete(Integer catid, Integer articleid)
   {
     List<IPlugin> plugins = getPlugins();
     try
     {
       if (plugins != null) {
         for (IPlugin plugin : plugins) {
           if ((plugin instanceof IDataDeleteEvent)) {
             IDataDeleteEvent dataDeleteEvent = (IDataDeleteEvent)plugin;
             dataDeleteEvent.onDelete(catid, articleid);
           }
         }
       }
     } catch (Exception e) {
       e.printStackTrace();
     }
   }

   public String onDisplay(DataField field, Object value) {
     List<IPlugin> plugins = getPlugins();
     try
     {
       if (plugins != null) {
         for (IPlugin plugin : plugins) {
           if ((plugin instanceof AbstractFieldPlugin)) {
             AbstractFieldPlugin fieldPlugin = (AbstractFieldPlugin)plugin;
             if (fieldPlugin.getId().equals(field.getShow_form())) {
               return fieldPlugin.onDisplay(field, value);
             }
           }
         }
       }
       return "输入项" + field.getShow_form() + "未找到插件解析";
     } catch (Exception e) {
       e.printStackTrace(); }
     return "输入项" + field.getShow_form() + "发生错误";
   }

   public IPlugin findPlugin(String id)
   {
     List<IPlugin> plugins = getPlugins();

     if (plugins != null) {
       for (IPlugin plugin : plugins) {
         if ((plugin instanceof AbstractFieldPlugin)) {
           AbstractFieldPlugin p = (AbstractFieldPlugin)plugin;
           if (id.equals(p.getId())) {
             return plugin;
           }
         }
       }
     }
     return null;
   }

   public List getFieldPlugins() {
     List<IPlugin> plugins = getPlugins();

     List<IPlugin> pluginList = new ArrayList();
     if (plugins != null) {
       for (IPlugin plugin : plugins) {
         if ((plugin instanceof AbstractFieldPlugin)) {
           pluginList.add(plugin);
         }
       }
     }
     return pluginList;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\plugin\ArticlePluginBundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */