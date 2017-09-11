package com.enation.app.cms.component.widget;

import com.enation.eop.sdk.widget.AbstractWidget;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("staticContent")
@Scope("prototype")
public class StaticContentWidget
  extends AbstractWidget
{
  protected void config(Map<String, String> params) {}
  
  protected void display(Map<String, String> params) {}
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\widget\StaticContentWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */