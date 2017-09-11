 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.core.service.IMemberLvManager;
 import com.enation.framework.action.WWAction;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import net.sf.json.JSONArray;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;



 @Component
 @Scope("prototype")
 @ParentPackage("shop_default")
 @Namespace("/shop/admin")
 @Action("demoJson")
 @Results({@org.apache.struts2.convention.annotation.Result(name="demolist", type="freemarker", location="/shop/admin/demo/demo_list.html")})
 public class DemoJsonAction
   extends WWAction
 {
   private IMemberLvManager memberLvManager;

   public String demolist()
     throws IOException
   {
     return "demolist";
   }

   public String demo() {
     List list = new ArrayList();
     for (int i = 0; i <= 5; i++) {
       Map map = new HashMap();
       map.put("name", Integer.valueOf(i));
       map.put("sex", i + "qq");
       list.add(map);
     }
     this.json = ("{\"total\":100,\"rows\":" + JSONArray.fromObject(list).toString() + "}");

     return "json_message";
   }

   public IMemberLvManager getMemberLvManager() {
     return this.memberLvManager;
   }

   public void setMemberLvManager(IMemberLvManager memberLvManager) {
     this.memberLvManager = memberLvManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\DemoJsonAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */