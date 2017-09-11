 package com.enation.app.shop.component.shopsetting;

 import com.enation.app.base.core.plugin.setting.IOnSettingInputShow;
 import com.enation.framework.plugin.AutoRegisterPlugin;











 public class ShopSettingPlugin
   extends AutoRegisterPlugin
   implements IOnSettingInputShow
 {
   public String onShow()
   {
     return "ShopSetting";
   }


   public String getSettingGroupName()
   {
     return "shop";
   }




   public String getTabName()
   {
     return "网店参数";
   }


   public int getOrder()
   {
     return 4;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\shopsetting\ShopSettingPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */