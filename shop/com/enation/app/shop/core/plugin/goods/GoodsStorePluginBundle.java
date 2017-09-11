 package com.enation.app.shop.core.plugin.goods;

 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.plugin.AutoRegisterPluginsBundle;
 import com.enation.framework.plugin.IPlugin;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.logging.Log;




 public class GoodsStorePluginBundle
   extends AutoRegisterPluginsBundle
 {
   private void logerOut(String outString)
   {
     if (AutoRegisterPluginsBundle.loger.isDebugEnabled()) {
       AutoRegisterPluginsBundle.loger.debug(outString);
     }
   }






   public String getStoreHtml(Map goods)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.putData("goods", goods);
     String html = null;
     List<IPlugin> plugins = getPlugins();

     if (plugins != null) {
       for (IPlugin plugin : plugins) {
         if ((plugin instanceof AbstractGoodsStorePlugin)) {
           AbstractGoodsStorePlugin event = (AbstractGoodsStorePlugin)plugin;
           freeMarkerPaser.setClz(event.getClass());

           if (event.canBeExecute(goods)) {
             html = event.getStoreHtml(goods);
             logerOut("处理商品[" + goods.get("name") + "]获取商品存维护页面html事件:执行插件[" + plugin.getClass() + "]");
           } else {
             logerOut("处理商品[" + goods.get("name") + "]获取商品存维护页面html事件:插件[" + plugin.getClass() + "]不被执行");
           }
         }
       }
     }
     return html;
   }






   public String getStockHtml(Map goods)
   {
     List<IPlugin> plugins = getPlugins();

     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.putData("goods", goods);
     String html = null;
     if (plugins != null) {
       for (IPlugin plugin : plugins) {
         if ((plugin instanceof AbstractGoodsStorePlugin)) {
           AbstractGoodsStorePlugin event = (AbstractGoodsStorePlugin)plugin;
           freeMarkerPaser.setClz(event.getClass());

           if (event.canBeExecute(goods)) {
             html = event.getStockHtml(goods);
             logerOut("处理商品[" + goods.get("name") + "]获取商品进货页面html事件:执行插件[" + plugin.getClass() + "]");
           } else {
             logerOut("处理商品[" + goods.get("name") + "]获取商品进货页面html事件:插件[" + plugin.getClass() + "]不被执行");
           }
         }
       }
     }
     return html;
   }






   public String getShipHtml(Map goods)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.putData("goods", goods);
     String html = null;
     List<IPlugin> plugins = getPlugins();

     if (plugins != null) {
       for (IPlugin plugin : plugins) {
         if ((plugin instanceof AbstractGoodsStorePlugin)) {
           AbstractGoodsStorePlugin event = (AbstractGoodsStorePlugin)plugin;
           freeMarkerPaser.setClz(event.getClass());
           if (event.canBeExecute(goods)) {
             html = event.getShipHtml(goods);
             logerOut("处理商品[" + goods.get("name") + "]获取商品出货页面html事件:执行插件[" + plugin.getClass() + "]");
           } else {
             logerOut("处理商品[" + goods.get("name") + "]获取商品出货页面html事件:插件[" + plugin.getClass() + "]不被执行");
           }
         }
       }
     }
     return html;
   }






   public void onStoreSave(Map goods)
   {
     List<IPlugin> plugins = getPlugins();
     for (IPlugin plugin : plugins) {
       if ((plugin instanceof AbstractGoodsStorePlugin)) {
         AbstractGoodsStorePlugin event = (AbstractGoodsStorePlugin)plugin;
         if (event.canBeExecute(goods)) {
           event.onStoreSave(goods);
           logerOut("处理商品[" + goods.get("name") + "]库存保存事件:执行插件[" + plugin.getClass() + "]");
         } else {
           logerOut("处理商品[" + goods.get("name") + "]库存保存事件:插件[" + plugin.getClass() + "]不被执行");
         }
       }
     }
   }






   public String getWarnHtml(Map goods)
   {
     List<IPlugin> plugins = getPlugins();

     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.putData("goods", goods);
     String html = null;
     if (plugins != null) {
       for (IPlugin plugin : plugins) {
         if ((plugin instanceof AbstractGoodsStorePlugin)) {
           AbstractGoodsStorePlugin event = (AbstractGoodsStorePlugin)plugin;
           freeMarkerPaser.setClz(event.getClass());

           if (event.canBeExecute(goods)) {
             html = event.getWarnNumHtml(goods);
             logerOut("处理商品[" + goods.get("name") + "]获取商品报警设置页面html事件:执行插件[" + plugin.getClass() + "]");
           } else {
             logerOut("处理商品[" + goods.get("name") + "]获取商品报警设置页面html事件:插件[" + plugin.getClass() + "]不被执行");
           }
         }
       }
     }
     return html;
   }






   public void onWarnSave(Map goods)
   {
     List<IPlugin> plugins = getPlugins();

     for (IPlugin plugin : plugins) {
       if ((plugin instanceof AbstractGoodsStorePlugin)) {
         AbstractGoodsStorePlugin event = (AbstractGoodsStorePlugin)plugin;
         if (event.canBeExecute(goods)) {
           event.onWarnSave(goods);
           logerOut("处理商品[" + goods.get("name") + "]报警保存事件:执行插件[" + plugin.getClass() + "]");
         } else {
           logerOut("处理商品[" + goods.get("name") + "]库存保存事件:插件[" + plugin.getClass() + "]不被执行");
         }
       }
     }
   }






   public void onStockSave(Map goods)
   {
     List<IPlugin> plugins = getPlugins();

     for (IPlugin plugin : plugins) {
       if ((plugin instanceof AbstractGoodsStorePlugin)) {
         AbstractGoodsStorePlugin event = (AbstractGoodsStorePlugin)plugin;
         if (event.canBeExecute(goods)) {
           event.onStockSave(goods);
           logerOut("处理商品[" + goods.get("name") + "]进货事件:执行插件[" + plugin.getClass() + "]");
         } else {
           logerOut("处理商品[" + goods.get("name") + "]进货事件:插件[" + plugin.getClass() + "]不被执行");
         }
       }
     }
   }







   public void onShipSave(Map goods)
   {
     List<IPlugin> plugins = getPlugins();

     for (IPlugin plugin : plugins) {
       if ((plugin instanceof AbstractGoodsStorePlugin)) {
         AbstractGoodsStorePlugin event = (AbstractGoodsStorePlugin)plugin;
         if (event.canBeExecute(goods)) {
           event.onShipSave(goods);
           logerOut("处理商品[" + goods.get("name") + "]出货事件:执行插件[" + plugin.getClass() + "]");
         } else {
           logerOut("处理商品[" + goods.get("name") + "]出货事件:插件[" + plugin.getClass() + "]不被执行");
         }
       }
     }
   }

   public String getName()
   {
     return "商品库存插件桩";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\goods\GoodsStorePluginBundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */