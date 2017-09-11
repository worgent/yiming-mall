 package com.enation.app.shop.component.point.plugin;

 import com.enation.app.base.core.plugin.setting.IOnSettingInputShow;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import org.springframework.stereotype.Component;








 @Component
 public class PointSettingPlugin
   extends AutoRegisterPlugin
   implements IOnSettingInputShow
 {
   public String getSettingGroupName()
   {
     return "point";
   }


   public String onShow()
   {
     return "setting";
   }




   public String getTabName()
   {
     return "积分设置";
   }



   public int getOrder()
   {
     return 3;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\point\plugin\PointSettingPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */