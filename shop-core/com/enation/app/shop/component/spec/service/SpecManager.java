 package com.enation.app.shop.component.spec.service;

 import com.enation.app.shop.core.model.SpecValue;
 import com.enation.app.shop.core.model.Specification;
 import com.enation.app.shop.core.model.mapper.SpecValueMapper;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.util.StringUtil;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;









 @Component
 public class SpecManager
   extends BaseSupport<Specification>
   implements ISpecManager
 {
   private ISpecValueManager specValueManager;

   @Transactional(propagation=Propagation.REQUIRED)
   public void add(Specification spec, List<SpecValue> valueList)
   {
     this.baseDaoSupport.insert("specification", spec);
     Integer specId = Integer.valueOf(this.baseDaoSupport.getLastId("specification"));
     for (SpecValue value : valueList) {
       value.setSpec_id(specId);
       value.setSpec_type(spec.getSpec_type());
       String path = value.getSpec_image();
       if (path != null) {
         path = path.replaceAll(EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath(), EopSetting.FILE_STORE_PREFIX);
       }
       value.setSpec_image(path);
       this.specValueManager.add(value);
     }
   }






   public boolean checkUsed(Integer[] idArray)
   {
     if (idArray == null) { return false;
     }
     String idStr = StringUtil.arrayToString(idArray, ",");
     String sql = "select count(0)  from  goods_spec where spec_id in (-1," + idStr + ")";

     int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
     if (count > 0) {
       return true;
     }
     return false;
   }







   public boolean checkUsed(Integer valueid)
   {
     String sql = "select count(0) from goods_spec where spec_value_id=?";

     return this.baseDaoSupport.queryForInt(sql, new Object[] { valueid }) > 0;
   }


   @Transactional(propagation=Propagation.REQUIRED)
   public void delete(Integer[] idArray)
   {
     String idStr = StringUtil.arrayToString(idArray, ",");
     String sql = "delete from specification where spec_id in (-1," + idStr + ")";
     this.baseDaoSupport.execute(sql, new Object[0]);

     sql = "delete from spec_values where spec_id in (-1," + idStr + ")";
     this.baseDaoSupport.execute(sql, new Object[0]);

     sql = "delete from goods_spec where spec_id in (-1," + idStr + ")";
     this.baseDaoSupport.execute(sql, new Object[0]);
   }



   @Transactional(propagation=Propagation.REQUIRED)
   public void edit(Specification spec, List<SpecValue> valueList)
   {
     if ((valueList == null) || (valueList.isEmpty()))
     {
       String sql = "delete from spec_values where spec_id=?";
       this.baseDaoSupport.execute(sql, new Object[] { spec.getSpec_id() });
       this.baseDaoSupport.update("specification", spec, "spec_id=" + spec.getSpec_id());

     }
     else
     {
       String valuidstr = "";
       for (SpecValue value : valueList) {
         int valueid = value.getSpec_value_id().intValue();
         if (!StringUtil.isEmpty(valuidstr)) {
           valuidstr = valuidstr + ",";
         }
         valuidstr = valuidstr + valueid;
       }

       String sql = "delete from spec_values where  spec_id=? and spec_value_id not in(" + valuidstr + ")";
       this.baseDaoSupport.execute(sql, new Object[] { spec.getSpec_id() });
       this.baseDaoSupport.update("specification", spec, "spec_id=" + spec.getSpec_id());
       for (SpecValue value : valueList) {
         value.setSpec_id(spec.getSpec_id());
         value.setSpec_type(spec.getSpec_type());
         String path = value.getSpec_image();
         if (path != null) {
           path = path.replaceAll(EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath(), EopSetting.FILE_STORE_PREFIX);
         }
         value.setSpec_image(path);
         if (value.getSpec_value_id().intValue() == 0) {
           this.specValueManager.add(value);
         } else {
           this.specValueManager.update(value);
         }
       }
     }
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public List list()
   {
     String sql = "select * from specification order by spec_id desc";
     return this.baseDaoSupport.queryForList(sql, new Object[0]);
   }





   public List<Specification> listSpecAndValue()
   {
     String sql = "select * from specification";
     List<Specification> specList = this.baseDaoSupport.queryForList(sql, Specification.class, new Object[0]);

     sql = "select * from spec_values order by spec_id";
     List valueList = this.baseDaoSupport.queryForList(sql, new SpecValueMapper(), new Object[0]);
     for (Specification spec : specList) {
       List<SpecValue> newList = new ArrayList();
       for (Object value : valueList) {
            SpecValue one = (SpecValue)value;
         if (one.getSpec_id().intValue() == spec.getSpec_id().intValue()) {
           newList.add(one);
         }
       }
       spec.setValueList(newList);
     }
     return specList;
   }


   public Map get(Integer spec_id)
   {
     String sql = "select * from specification where spec_id=?";
     return this.baseDaoSupport.queryForMap(sql, new Object[] { spec_id });
   }





   public List getProSpecList(int productid)
   {
     String sql = "select s.spec_name name ,sv.spec_value value  from   " + getTableName("specification") + " s ," + getTableName("spec_values") + " sv ," + getTableName("goods_spec") + " gs where gs.product_id=? and gs.spec_id=s.spec_id and gs.spec_value_id = sv.spec_value_id";
     return this.daoSupport.queryForList(sql, new Object[] { Integer.valueOf(productid) });
   }

   public ISpecValueManager getSpecValueManager()
   {
     return this.specValueManager;
   }

   public void setSpecValueManager(ISpecValueManager specValueManager) {
     this.specValueManager = specValueManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\spec\service\SpecManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */