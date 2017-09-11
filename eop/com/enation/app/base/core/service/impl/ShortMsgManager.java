 package com.enation.app.base.core.service.impl;

 import com.enation.app.base.core.model.ShortMsg;
 import com.enation.app.base.core.plugin.shortmsg.ShortMsgPluginBundle;
 import com.enation.app.base.core.service.IShortMsgManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import java.util.List;









 public class ShortMsgManager
   extends BaseSupport<ShortMsg>
   implements IShortMsgManager
 {
   private ShortMsgPluginBundle shortMsgPluginBundle;

   public List<ShortMsg> listNotReadMessage()
   {
     return this.shortMsgPluginBundle.getMessageList();
   }

   public ShortMsgPluginBundle getShortMsgPluginBundle() {
     return this.shortMsgPluginBundle;
   }

   public void setShortMsgPluginBundle(ShortMsgPluginBundle shortMsgPluginBundle) {
     this.shortMsgPluginBundle = shortMsgPluginBundle;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\impl\ShortMsgManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */