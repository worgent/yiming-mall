 package com.enation.app.shop.component.spec.service;

 import com.enation.app.shop.core.model.SpecValue;
 import com.enation.app.shop.core.model.mapper.SpecValueMapper;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;






 @Component
 public class SpecValueManager
   extends BaseSupport<SpecValue>
   implements ISpecValueManager
 {
   public void add(SpecValue value)
   {
     this.baseDaoSupport.insert("spec_values", value);
   }



   public void update(SpecValue value)
   {
     this.baseDaoSupport.update("spec_values", value, "spec_value_id=" + value.getSpec_value_id());
   }

   public List<SpecValue> list(Integer specId)
   {
     String sql = "select * from spec_values where spec_id =?";
     List valueList = this.baseDaoSupport.queryForList(sql, new SpecValueMapper(), new Object[] { specId });
     return valueList;
   }


   public Map get(Integer valueId)
   {
     String sql = "select sv.*,s.spec_type from " + getTableName("spec_values") + " sv," + getTableName("specification") + " s  where sv.spec_id=s.spec_id and sv.spec_value_id =?";
     Map temp = this.daoSupport.queryForMap(sql, new Object[] { valueId });
     String spec_image = (String)temp.get("spec_image");
     if (spec_image != null) {
       spec_image = UploadUtil.replacePath(spec_image);
     }
     temp.put("spec_image", spec_image);
     return temp;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\spec\service\SpecValueManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */