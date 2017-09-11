 package com.enation.app.b2b2c.core.action.backend.goods;

 import com.enation.framework.action.WWAction;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.stereotype.Component;







 @Component
 @ParentPackage("eop_default")
 @Namespace("/b2b2c/admin")
 @Results({@org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/b2b2c/admin/tags/taglist.html")})
 @Action("b2b2cGoodsShow")
 public class B2b2cGoodsShowAction
   extends WWAction
 {
   public String list()
   {
     return "list";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\backend\goods\B2b2cGoodsShowAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */