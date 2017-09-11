 package com.enation.app.base.component.widget.im;

 import com.enation.app.base.core.plugin.setting.IOnSettingInputShow;
 import com.enation.framework.plugin.AutoRegisterPlugin;








 public class IMSettingPlugin
   extends AutoRegisterPlugin
   implements IOnSettingInputShow
 {
   public String getSettingGroupName()
   {
     return "im";
   }

   public String onShow()
   {
     return "setting";
   }


   public String getId()
   {
     return "imSettingPlugin";
   }


   public String getName()
   {
     return "imSettingPlugin";
   }





   public String getTabName()
   {
     return "在线客服";
   }


   public int getOrder()
   {
     return 0;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\im\IMSettingPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */