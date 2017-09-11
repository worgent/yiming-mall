 package com.enation.app.base.component.widget.counter;

 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.widget.AbstractWidget;
 import com.enation.framework.util.FileUtil;
 import com.enation.framework.util.StringUtil;
 import java.io.File;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;










 @Component("counter")
 @Scope("prototype")
 public class CounterWidget
   extends AbstractWidget
 {
   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     String counterPath = EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/counter.txt";
     File file = new File(counterPath);
     try {
       if (!file.exists()) file.createNewFile();
     } catch (Exception e) {
       e.printStackTrace();
     }
     String count = FileUtil.read(counterPath, "UTF-8");
     if (StringUtil.isEmpty(count))
       count = "0";
     count = count.replaceAll("\n", "");
     FileUtil.write(counterPath, "" + (Integer.valueOf(count).intValue() + 1));
     putData("count", Integer.valueOf(Integer.valueOf(count).intValue() + 1));
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\counter\CounterWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */