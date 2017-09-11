 package com.enation.app.shop.core.model.mapper;

 import com.enation.app.shop.core.model.support.GoodsView;
 import com.enation.eop.sdk.utils.UploadUtil;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.HashMap;
 import java.util.Map;
 import org.springframework.jdbc.core.RowMapper;










 public class GoodsListMapper
   implements RowMapper
 {
   public Object mapRow(ResultSet rs, int arg1)
     throws SQLException
   {
     GoodsView goods = new GoodsView();
     goods.setName(rs.getString("name"));
     goods.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
     goods.setMktprice(Double.valueOf(rs.getDouble("mktprice")));
     goods.setPrice(Double.valueOf(rs.getDouble("price")));
     goods.setCreate_time(Long.valueOf(rs.getLong("create_time")));
     goods.setLast_modify(Long.valueOf(rs.getLong("last_modify")));
     goods.setType_id(Integer.valueOf(rs.getInt("type_id")));
     goods.setPoint(Integer.valueOf(rs.getInt("point")));
     goods.setStore(Integer.valueOf(rs.getInt("store")));
     goods.setCat_id(Integer.valueOf(rs.getInt("cat_id")));

     goods.setSn(rs.getString("sn"));
     goods.setIntro(rs.getString("intro"));
     goods.setStore(Integer.valueOf(rs.getInt("store")));
     String temp = rs.getString("thumbnail");
     goods.setOriginal(UploadUtil.replacePath(rs.getString("original")));
     goods.setThumbnail(UploadUtil.replacePath(temp));
     goods.setSmall(UploadUtil.replacePath(rs.getString("small")));
     goods.setBig(UploadUtil.replacePath(rs.getString("big")));


     Map propMap = new HashMap();

     for (int i = 0; i < 20; i++) {
       String value = rs.getString("p" + (i + 1));
       propMap.put("p" + (i + 1), value);
     }
     goods.setPropMap(propMap);

     goods.setBuy_count(Integer.valueOf(rs.getInt("buy_count")));

     return goods;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\mapper\GoodsListMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */