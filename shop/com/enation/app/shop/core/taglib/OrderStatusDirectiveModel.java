 package com.enation.app.shop.core.taglib;

 import com.enation.app.shop.core.service.OrderStatus;
 import com.enation.framework.util.StringUtil;
 import freemarker.core.Environment;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateDirectiveModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.io.Writer;
 import java.util.Map;


 public class OrderStatusDirectiveModel
   implements TemplateDirectiveModel
 {
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     int status = StringUtil.toInt(params.get("status").toString(), true);
     String type = params.get("type").toString();
     if ("order".equals(type)) {
       String text = OrderStatus.getOrderStatusText(status);
       env.getOut().write(text);
     }
     if ("pay".equals(type)) {
       String text = OrderStatus.getPayStatusText(status);
       env.getOut().write(text);
     }
     if ("ship".equals(type)) {
       String text = OrderStatus.getShipStatusText(status);
       env.getOut().write(text);
     }
   }

   public static void main(String[] args) {}
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\taglib\OrderStatusDirectiveModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */