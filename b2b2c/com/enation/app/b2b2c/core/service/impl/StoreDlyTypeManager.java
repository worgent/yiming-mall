 package com.enation.app.b2b2c.core.service.impl;

 import com.enation.app.b2b2c.core.model.StoreDlyType;
 import com.enation.app.b2b2c.core.service.IStoreDlyTypeManager;
 import com.enation.app.shop.core.model.support.DlyTypeConfig;
 import com.enation.app.shop.core.model.support.TypeArea;
 import com.enation.app.shop.core.model.support.TypeAreaConfig;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import java.util.Map;
 import net.sf.json.JSONObject;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;









 @Component
 public class StoreDlyTypeManager
   extends BaseSupport
   implements IStoreDlyTypeManager
 {
   public List getDlyTypeById(String typeid)
   {
     String sql = "select * from es_dly_type where type_id in (" + typeid + ")";
     List list = this.baseDaoSupport.queryForList(sql, new Object[0]);
     return list;
   }

   public List getDlyTypeList(Integer template_id)
   {
     String sql = "select * from es_dly_type where template_id=? order by type_id";
     List list = this.baseDaoSupport.queryForList(sql, new Object[] { template_id });
     return list;
   }



   @Transactional(propagation=Propagation.REQUIRED)
   public void add(StoreDlyType storeDlyType, DlyTypeConfig config, TypeAreaConfig[] configArray)
   {
     storeDlyType = fillType(storeDlyType, config);

     this.baseDaoSupport.insert("es_dly_type", storeDlyType);
     Integer typeId = Integer.valueOf(this.baseDaoSupport.getLastId("dly_type"));
     addTypeArea(typeId, configArray);
   }

   private StoreDlyType fillType(StoreDlyType storeDlyType, DlyTypeConfig config)
   {
     Double firstprice = config.getFirstprice();
     Double continueprice = config.getContinueprice();
     Integer firstunit = config.getFirstunit();
     Integer continueunit = config.getContinueunit();


     String expressions = null;

     if (config.getUseexp().intValue() == 0) {
       expressions = createExpression(firstprice, continueprice, firstunit, continueunit);
     }
     else {
       expressions = config.getExpression();
     }

     storeDlyType.setExpressions(expressions);
     storeDlyType.setConfig(JSONObject.fromObject(config).toString());
     return storeDlyType;
   }

   private void addTypeArea(Integer typeId, TypeAreaConfig[] configArray) {
     for (TypeAreaConfig areaConfig : configArray) {
       if (areaConfig != null)
       {
         TypeArea typeArea = new TypeArea();
         typeArea.setArea_id_group(areaConfig.getAreaId());
         typeArea.setArea_name_group(areaConfig.getAreaName());
         typeArea.setType_id(typeId);
         typeArea.setHas_cod(areaConfig.getHave_cod());

         typeArea.setConfig(JSONObject.fromObject(areaConfig).toString());
         String expressions = "";
         if (areaConfig.getUseexp().intValue() == 1) {
           expressions = areaConfig.getExpression();
         }
         else {
           expressions = createExpression(areaConfig);
         }
         typeArea.setExpressions(expressions);
         this.baseDaoSupport.insert("dly_type_area", typeArea);
       }
     }
   }







   private String createExpression(TypeAreaConfig areaConfig)
   {
     return createExpression(areaConfig.getFirstprice(), areaConfig.getContinueprice(), areaConfig.getFirstunit(), areaConfig.getContinueunit());
   }












   private String createExpression(Double firstprice, Double continueprice, Integer firstunit, Integer continueunit)
   {
     return firstprice + "+tint(w-" + firstunit + ")/" + continueunit + "*" + continueprice;
   }


   public Integer getLastId()
   {
     Integer type_id = Integer.valueOf(this.baseDaoSupport.getLastId("es_dly_type"));
     return type_id;
   }

   public List getDlyTypeAreaList(Integer type_id)
   {
     String sql = "select * from es_dly_type_area where type_id=?";
     List list = this.baseDaoSupport.queryForList(sql, new Object[] { type_id });
     return list;
   }


   public Integer getDefaultDlyId(Integer store_id)
   {
     String sql = "select type_id from es_dly_type where store_id=? and defaulte=1";
     Integer id = Integer.valueOf(this.baseDaoSupport.queryForInt(sql, new Object[] { store_id }));
     return id;
   }

   public void del_dlyType(Integer tempid)
   {
     String sql = "select * from es_dly_type where template_id=?";
     List<Map> list = this.baseDaoSupport.queryForList(sql, new Object[] { tempid });
     if (!list.isEmpty()) {
       StringBuffer dlyids = new StringBuffer();
       for (Map map : list) {
         Integer type_id = (Integer)map.get("type_id");
         dlyids.append(type_id + ",");
       }
       String ids = dlyids.toString().substring(0, dlyids.toString().length() - 1);

       String areadelsql = "delete from es_dly_type_area where type_id in (" + ids + ")";
       String dlydelsql = "delete from es_dly_type where template_id=?";
       this.baseDaoSupport.execute(areadelsql, new Object[0]);
       this.baseDaoSupport.execute(dlydelsql, new Object[] { tempid });
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\impl\StoreDlyTypeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */