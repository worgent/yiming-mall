 package com.enation.app.shop.core.model.mapper;

 import com.enation.app.shop.core.model.support.TypeArea;
 import com.enation.app.shop.core.model.support.TypeAreaConfig;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import net.sf.json.JSONObject;
 import org.springframework.jdbc.core.RowMapper;








 public class TypeAreaMapper
   implements RowMapper
 {
   public Object mapRow(ResultSet rs, int rowNum)
     throws SQLException
   {
     TypeArea typeArea = new TypeArea();
     typeArea.setArea_id_group(rs.getString("area_id_group"));
     typeArea.setArea_name_group(rs.getString("area_name_group"));
     typeArea.setConfig(rs.getString("config"));
     typeArea.setExpressions(rs.getString("expressions"));
     typeArea.setHas_cod(Integer.valueOf(rs.getInt("has_cod")));
     typeArea.setType_id(Integer.valueOf(rs.getInt("type_id")));
     JSONObject configJsonObject = JSONObject.fromObject(typeArea.getConfig());
     typeArea.setTypeAreaConfig((TypeAreaConfig)JSONObject.toBean(configJsonObject, TypeAreaConfig.class));
     return typeArea;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\mapper\TypeAreaMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */