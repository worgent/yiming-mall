 package com.enation.app.shop.component.gallery.plugin;

 import com.enation.app.base.core.plugin.setting.IOnSettingInputShow;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import org.springframework.stereotype.Component;








 @Component
 public class ImageSettingPlugin
   extends AutoRegisterPlugin
   implements IOnSettingInputShow
 {
   public String onShow()
   {
     return "setting";
   }

   public String getSettingGroupName()
   {
     return "photo";
   }


   public String getTabName()
   {
     return "图片设置";
   }


   public int getOrder()
   {
     return 1;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\gallery\plugin\ImageSettingPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */