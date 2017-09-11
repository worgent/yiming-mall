 package com.enation.app.base.component.widget;

 import com.enation.eop.sdk.widget.AbstractWidget;
 import com.enation.framework.database.IDBRouter;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import java.util.Map;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;







 @Component("sql_list")
 @Scope("prototype")
 public class SqlListWidget
   extends AbstractWidget
 {
   private IDBRouter baseDBRouter;

   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     String sql = (String)params.get("sql");
     sql = filterSql(sql);
     List list = this.daoSupport.queryForList(sql, new Object[0]);
     putData("dataList", list);
   }

   private String filterSql(String sql)
   {
     String pattern = "#(.*?)#";

     Pattern p = Pattern.compile(pattern, 34);
     Matcher m = p.matcher(sql);
     while (m.find()) {
       String tb = m.group(0);

       String newTb = tb.replaceAll("#", "");
       newTb = this.baseDBRouter.getTableName(newTb);
       sql = sql.replaceAll(tb, newTb);
     }
     return sql;
   }

   public static void main(String[] args) {
     String sql = "select * from  #goods# g ,#goods_cat# where cat_id=1";
     String pattern = "#(.*?)#";

     Pattern p = Pattern.compile(pattern, 34);
     Matcher m = p.matcher(sql);
     while (m.find()) {
       String tb = m.group(0);
       String newTb = tb.replaceAll("#", "");
       sql = sql.replaceAll(tb, newTb);
     }
   }

   public IDBRouter getBaseDBRouter() {
     return this.baseDBRouter;
   }

   public void setBaseDBRouter(IDBRouter baseDBRouter) {
     this.baseDBRouter = baseDBRouter;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\SqlListWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */