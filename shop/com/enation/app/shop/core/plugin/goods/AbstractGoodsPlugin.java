package com.enation.app.shop.core.plugin.goods;

import com.enation.framework.plugin.AutoRegisterPlugin;

public abstract class AbstractGoodsPlugin
  extends AutoRegisterPlugin
  implements IGetGoodsAddHtmlEvent, IGoodsBeforeAddEvent, IGetGoodsEditHtmlEvent, IGoodsAfterAddEvent, IGoodsAfterEditEvent, IGoodsBeforeEditEvent
{}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\goods\AbstractGoodsPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */