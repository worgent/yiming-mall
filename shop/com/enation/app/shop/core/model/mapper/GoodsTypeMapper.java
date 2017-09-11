 package com.enation.app.shop.core.model.mapper;

 import com.enation.app.shop.core.model.GoodsType;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import org.springframework.jdbc.core.RowMapper;

 public class GoodsTypeMapper
   implements RowMapper
 {
   public Object mapRow(ResultSet rs, int arg1)
     throws SQLException
   {
     GoodsType goodsType = new GoodsType();
     goodsType.setType_id(Integer.valueOf(rs.getInt("type_id")));
     goodsType.setName(rs.getString("name"));
     goodsType.setHave_parm(rs.getInt("have_parm"));
     goodsType.setHave_prop(rs.getInt("have_prop"));
     goodsType.setIs_physical(rs.getInt("is_physical"));
     goodsType.setJoin_brand(rs.getInt("join_brand"));
     goodsType.setProps(rs.getString("props"));
     goodsType.setParams(rs.getString("params"));

     return goodsType;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\mapper\GoodsTypeMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */