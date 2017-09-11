 package com.enation.app.shop.core.utils;

 import com.enation.app.shop.core.model.support.Adjunct;
 import com.enation.app.shop.core.model.support.AdjunctGroup;
 import com.enation.app.shop.core.model.support.SpecJson;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import net.sf.json.JSONArray;
 import net.sf.json.JSONObject;





 public abstract class GoodsUtils
 {
   public static List getSpecList(String specString)
   {
     if ((specString == null) || (specString.equals("[]")) || (specString.equals(""))) {
       return new ArrayList();
     }
     JSONArray j1 = JSONArray.fromObject(specString);
     List<SpecJson> list = (List)JSONArray.toCollection(j1, SpecJson.class);
     return list;
   }







   public static AdjunctGroup converAdjFormString(String adjString)
   {
     if (adjString == null) {
       return null;
     }
     Map classMap = new HashMap();

     classMap.put("adjList", Adjunct.class);
     JSONObject j1 = JSONObject.fromObject(adjString);
     AdjunctGroup adjunct = (AdjunctGroup)JSONObject.toBean(j1, AdjunctGroup.class, classMap);

     return adjunct;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\\utils\GoodsUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */