 package com.enation.app.shop.core.tag.detail;

 import com.enation.app.shop.core.model.Attribute;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IGoodsTypeManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;







 @Component
 @Scope("prototype")
 public class GoodsAttributeListTag
   extends BaseFreeMarkerTag
 {
   private IGoodsTypeManager goodsTypeManager;
   private IGoodsManager goodsManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer goodsid = (Integer)params.get("goodsid");
     Map goodsmap = this.goodsManager.get(goodsid);
     Integer typeid = (Integer)goodsmap.get("type_id");

     List<Attribute> list = this.goodsTypeManager.getAttrListByTypeId(typeid.intValue());
     List attrList = new ArrayList();

     int i = 1;
     for (Attribute attribute : list) {
       Map attrmap = new HashMap();
       if (attribute.getType() == 3) {
         String[] s = attribute.getOptionAr();
         String p = (String)goodsmap.get("p" + i);
         Integer num = Integer.valueOf(0);
         if (!StringUtil.isEmpty(p)) {
           num = Integer.valueOf(Integer.parseInt(p));
         }
         attrmap.put("attrName", attribute.getName());
         attrmap.put("attrValue", s[num.intValue()]);
       } else {
         attrmap.put("attrName", attribute.getName());
         attrmap.put("attrValue", goodsmap.get("p" + i));
       }
       attrList.add(attrmap);
       i++;
     }
     return attrList;
   }

   public IGoodsTypeManager getGoodsTypeManager() {
     return this.goodsTypeManager;
   }

   public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) { this.goodsTypeManager = goodsTypeManager; }

   public IGoodsManager getGoodsManager()
   {
     return this.goodsManager;
   }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\detail\GoodsAttributeListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */