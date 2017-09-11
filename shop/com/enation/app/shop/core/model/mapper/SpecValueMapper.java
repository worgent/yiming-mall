 package com.enation.app.shop.core.model.mapper;

 import com.enation.app.shop.core.model.SpecValue;
 import com.enation.eop.sdk.utils.UploadUtil;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import org.springframework.jdbc.core.RowMapper;







 public class SpecValueMapper
   implements RowMapper
 {
   public Object mapRow(ResultSet rs, int arg1)
     throws SQLException
   {
     SpecValue specValue = new SpecValue();
     specValue.setSpec_id(Integer.valueOf(rs.getInt("spec_id")));
     String spec_img = rs.getString("spec_image");
     if (spec_img != null) {
       spec_img = UploadUtil.replacePath(spec_img);
     }
     specValue.setSpec_image(spec_img);
     specValue.setSpec_order(Integer.valueOf(rs.getInt("spec_order")));
     specValue.setSpec_type(Integer.valueOf(rs.getInt("spec_type")));
     specValue.setSpec_value(rs.getString("spec_value"));
     specValue.setSpec_value_id(Integer.valueOf(rs.getInt("spec_value_id")));
     return specValue;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\mapper\SpecValueMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */