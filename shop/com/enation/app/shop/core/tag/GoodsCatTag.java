 package com.enation.app.shop.core.tag;

 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;












 @Component
 @Scope("prototype")
 public class GoodsCatTag
   extends BaseFreeMarkerTag
 {
   private IGoodsCatManager goodsCatManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer parentid = (Integer)params.get("parentid");
     if (parentid == null) {
       parentid = Integer.valueOf(0);
     }
     List<Cat> cat_tree = this.goodsCatManager.listAllChildren(parentid);
     String catimage = (String)params.get("catimage");
     boolean showimage = (catimage != null) && (catimage.equals("on"));

     String imgPath = "";
     if (!cat_tree.isEmpty()) {
       for (Cat cat : cat_tree)
       {
         if ((cat.getImage() != null) && (!StringUtil.isEmpty(cat.getImage()))) {
           imgPath = UploadUtil.replacePath(cat.getImage());
           cat.setImage(imgPath);
         }
       }
     }


     Map<String, Object> data = new HashMap();
     data.put("showimg", Boolean.valueOf(showimage));
     data.put("cat_tree", cat_tree);
     return data;
   }

   public IGoodsCatManager getGoodsCatManager() {
     return this.goodsCatManager;
   }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) { this.goodsCatManager = goodsCatManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\GoodsCatTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */