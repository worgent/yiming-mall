 package com.enation.app.shop.component.goodscore.plugin.params;

 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.model.GoodsParam;
 import com.enation.app.shop.core.model.GoodsType;
 import com.enation.app.shop.core.model.support.ParamGroup;
 import com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin;
 import com.enation.app.shop.core.plugin.goods.IGoodsTabShowEvent;
 import com.enation.app.shop.core.service.GoodsTypeUtil;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.app.shop.core.service.IGoodsTypeManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.util.StringUtil;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;


















 @Component
 public class GoodsParamsAdminPlugin
   extends AbstractGoodsPlugin
   implements IGoodsTabShowEvent
 {
   private IDaoSupport<GoodsType> baseDaoSupport;
   private IGoodsCatManager goodsCatManager;
   private IGoodsTypeManager goodsTypeManager;

   public String getAddHtml(HttpServletRequest request)
   {
     int catid = StringUtil.toInt(request.getParameter("catid"), true);
     Cat cat = this.goodsCatManager.getById(catid);
     int typeid = cat.getType_id().intValue();
     ParamGroup[] paramAr = this.goodsTypeManager.getParamArByTypeId(typeid);



     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();

     freeMarkerPaser.setPageName("params_input");
     freeMarkerPaser.putData("paramAr", paramAr);
     return freeMarkerPaser.proessPageContent();
   }








   public String getEditHtml(Map goods, HttpServletRequest request)
   {
     ParamGroup[] type_paramAr = this.goodsTypeManager.getParamArByTypeId(((Integer)goods.get("type_id")).intValue());

     String params = goods.get("params") == null ? "" : goods.get("params").toString();
     ParamGroup[] paramAr = GoodsTypeUtil.converFormString(params);
     Map<String, GoodsParam> temp = new HashMap();
     if ((paramAr != null) && (paramAr.length > 0)) {
       for (int i = 0; i < paramAr.length; i++) {
         ParamGroup paramGroup = paramAr[i];
         List<GoodsParam> list = paramGroup.getParamList();
         if (list != null) {
           Iterator<GoodsParam> it = list.iterator();
           while (it.hasNext()) {
             GoodsParam goodsParam = (GoodsParam)it.next();
             temp.put(paramGroup.getName() + "=" + goodsParam.getName(), goodsParam);
           }
         }
       }
     }
     if ((type_paramAr != null) && (type_paramAr.length > 0)) {
       for (int i = 0; i < type_paramAr.length; i++) {
         ParamGroup paramGroup = type_paramAr[i];
         List<GoodsParam> list = paramGroup.getParamList();
         if ((list != null) && (list.size() > 0)) {
           for (int j = 0; j < list.size(); j++) {
             GoodsParam goodsParam = (GoodsParam)list.get(j);
             if (temp.containsKey(paramGroup.getName() + "=" + goodsParam.getName())) {
               goodsParam = (GoodsParam)temp.get(paramGroup.getName() + "=" + goodsParam.getName());
               list.set(j, goodsParam);
             }
           }
         }
       }
     }



     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setPageName("params_input");
     freeMarkerPaser.putData("paramAr", type_paramAr);
     freeMarkerPaser.putData("is_edit", Boolean.valueOf(true));
     return freeMarkerPaser.proessPageContent();
   }









   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request)
   {
     String[] paramnums = request.getParameterValues("paramnums");
     String[] groupnames = request.getParameterValues("groupnames");
     String[] paramnames = request.getParameterValues("paramnames");
     String[] paramvalues = request.getParameterValues("paramvalues");

     String params = this.goodsTypeManager.getParamString(paramnums, groupnames, paramnames, paramvalues);

     goods.put("params", params);
   }






   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request)
   {
     String[] paramnums = request.getParameterValues("paramnums");
     String[] groupnames = request.getParameterValues("groupnames");
     String[] paramnames = request.getParameterValues("paramnames");
     String[] paramvalues = request.getParameterValues("paramvalues");


     String params = this.goodsTypeManager.getParamString(paramnums, groupnames, paramnames, paramvalues);

     goods.put("params", params);
   }






   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
     throws RuntimeException
   {}






   public void onAfterGoodsEdit(Map goods, HttpServletRequest request) {}






   public String getTabName()
   {
     return "参数";
   }


   public int getOrder()
   {
     return 9;
   }


   public IDaoSupport<GoodsType> getBaseDaoSupport()
   {
     return this.baseDaoSupport;
   }



   public void setBaseDaoSupport(IDaoSupport<GoodsType> baseDaoSupport)
   {
     this.baseDaoSupport = baseDaoSupport;
   }



   public IGoodsCatManager getGoodsCatManager()
   {
     return this.goodsCatManager;
   }



   public void setGoodsCatManager(IGoodsCatManager goodsCatManager)
   {
     this.goodsCatManager = goodsCatManager;
   }



   public IGoodsTypeManager getGoodsTypeManager()
   {
     return this.goodsTypeManager;
   }



   public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager)
   {
     this.goodsTypeManager = goodsTypeManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\goodscore\plugin\params\GoodsParamsAdminPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */