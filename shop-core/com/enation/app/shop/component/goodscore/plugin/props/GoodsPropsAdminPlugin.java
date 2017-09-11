 package com.enation.app.shop.component.goodscore.plugin.props;

 import com.enation.app.shop.core.model.Attribute;
 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.model.GoodsType;
 import com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin;
 import com.enation.app.shop.core.plugin.goods.IGoodsTabShowEvent;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.app.shop.core.service.IGoodsTypeManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.util.StringUtil;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;


















 @Component
 public class GoodsPropsAdminPlugin
   extends AbstractGoodsPlugin
   implements IGoodsTabShowEvent
 {
   private IDaoSupport<GoodsType> baseDaoSupport;
   private IGoodsCatManager goodsCatManager;
   private IGoodsTypeManager goodsTypeManager;

   public String getAddHtml(HttpServletRequest request)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();

     int catid = StringUtil.toInt(request.getParameter("catid"), true);
     Cat cat = this.goodsCatManager.getById(catid);
     int typeid = cat.getType_id().intValue();
     GoodsType goodsType = this.goodsTypeManager.get(Integer.valueOf(typeid));

     List attrList = this.goodsTypeManager.getAttrListByTypeId(typeid);

     if (goodsType.getJoin_brand() == 1) {
       List brandList = this.goodsTypeManager.listByTypeId(Integer.valueOf(typeid));
       freeMarkerPaser.putData("brandList", brandList);
     }

     freeMarkerPaser.setPageName("props_input");
     freeMarkerPaser.putData("attrList", attrList);

     return freeMarkerPaser.proessPageContent();
   }






   public String getEditHtml(Map goods, HttpServletRequest request)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();

     if (goods.get("type_id") == null) {
       return "类型id为空";
     }
     Integer typeid = null;
     try {
       typeid = Integer.valueOf("" + goods.get("type_id"));
     } catch (NumberFormatException e) {
       return "类型不为数字";
     }

     GoodsType goodsType = this.goodsTypeManager.getById(typeid.intValue());
     if (goodsType.getJoin_brand() == 1) {
       List brandList = this.goodsTypeManager.listByTypeId(typeid);
       freeMarkerPaser.putData("brandList", brandList);
     }

     if (goodsType.getHave_prop() == 1)
     {
       Map propMap = new HashMap();

       for (int i = 0; i < 20; i++)
       {
         String value = goods.get("p" + (i + 1)) == null ? "" : goods.get("p" + (i + 1)).toString();
         propMap.put("p" + i, value);
       }

       goods.put("propMap", propMap);
       List propList = proessProps(goods, typeid);
       freeMarkerPaser.putData("attrList", propList);
     }

     freeMarkerPaser.setPageName("props_input");

     return freeMarkerPaser.proessPageContent();
   }













   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {}













   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request)
   {
     String[] propvalues = request.getParameterValues("propvalues");
     try
     {
       Integer goods_id = Integer.valueOf("" + goods.get("goods_id"));
       saveProps(goods_id.intValue(), propvalues);
     }
     catch (NumberFormatException e) {
       throw new RuntimeException("商品id格式错误");
     }
   }








   public void onAfterGoodsEdit(Map goods, HttpServletRequest request) {}







   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
   {
     String[] propvalues = request.getParameterValues("propvalues");
     try
     {
       Integer goods_id = Integer.valueOf("" + goods.get("goods_id"));
       saveProps(goods_id.intValue(), propvalues);
     }
     catch (NumberFormatException e) {
       throw new RuntimeException("商品id格式错误");
     }
   }




















   private void saveProps(int goodsid, String[] propvalues)
   {
     if ((propvalues != null) && (propvalues.length > 0)) {
       HashMap fields = new HashMap();
       int length = propvalues.length;
       length = length > 20 ? 20 : length;



       for (int i = 0; i < length; i++) {
         String value = propvalues[i];
         fields.put("p" + (i + 1), value);
       }


       this.baseDaoSupport.update("goods", fields, "goods_id=" + goodsid);
     }
   }









   private List proessProps(Map goodsView, Integer typeid)
   {
     List propList = this.goodsTypeManager.getAttrListByTypeId(typeid.intValue());
     if (propList == null) return propList;
     Map<String, String> propMap = (Map)goodsView.get("propMap");
     for (int i = 0; i < propList.size(); i++) {
       Attribute attribute = (Attribute)propList.get(i);
       String value = (String)propMap.get("p" + i);
       attribute.setValue(value);
     }
     return propList;
   }




   public String getTabName()
   {
     return "属性";
   }

   public int getOrder()
   {
     return 7;
   }

   public IGoodsCatManager getGoodsCatManager() {
     return this.goodsCatManager;
   }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
     this.goodsCatManager = goodsCatManager;
   }

   public IGoodsTypeManager getGoodsTypeManager() {
     return this.goodsTypeManager;
   }

   public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
     this.goodsTypeManager = goodsTypeManager;
   }

   public IDaoSupport<GoodsType> getBaseDaoSupport() {
     return this.baseDaoSupport;
   }

   public void setBaseDaoSupport(IDaoSupport<GoodsType> baseDaoSupport) {
     this.baseDaoSupport = baseDaoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\goodscore\plugin\props\GoodsPropsAdminPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */