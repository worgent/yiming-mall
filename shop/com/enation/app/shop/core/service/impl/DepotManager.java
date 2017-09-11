 package com.enation.app.shop.core.service.impl;

 import com.enation.app.shop.core.model.Depot;
 import com.enation.app.shop.core.service.IDepotManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.IntegerMapper;
 import java.util.ArrayList;
import java.util.List;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;








 public class DepotManager
   extends BaseSupport<Depot>
   implements IDepotManager
 {
   public void add(Depot room)
   {
     if (room.getChoose().intValue() == 1) {
       this.daoSupport.execute("update es_depot set choose=0 where choose=1", new Object[0]);
     }
     this.baseDaoSupport.insert("depot", room);
   }

   public void update(Depot room)
   {
     if (room.getChoose().intValue() == 1) {
       this.daoSupport.execute("update es_depot set choose=0 where choose=1", new Object[0]);
     }
     this.baseDaoSupport.update("depot", room, "id=" + room.getId());
   }

   public Depot get(int roomid)
   {
     return (Depot)this.baseDaoSupport.queryForObject("select * from depot where id=?", Depot.class, new Object[] { Integer.valueOf(roomid) });
   }



   public List<Depot> list()
   {
     return this.baseDaoSupport.queryForList("select * from depot", Depot.class, new Object[0]);
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public String delete(int roomid)
   {
     String message = depot_validate(roomid);
     if (message.equals("")) {
       this.baseDaoSupport.execute("delete from goods_depot where depotid = ?", new Object[] { Integer.valueOf(roomid) });
       this.baseDaoSupport.execute("delete from product_store where depotid = ?", new Object[] { Integer.valueOf(roomid) });
       this.baseDaoSupport.execute("delete from depot_user where depotid = ?", new Object[] { Integer.valueOf(roomid) });
       this.baseDaoSupport.execute("delete from depot where id = ?", new Object[] { Integer.valueOf(roomid) });
       message = "删除成功";
     }
     return message;
   }

   private String depot_validate(int roomid) {
     int is_choose = this.baseDaoSupport.queryForInt("select choose from depot where id=?", new Object[] { Integer.valueOf(roomid) });
     if (is_choose == 1) {
       return "此仓库为默认仓库，不能删除";
     }
     List numList = this.baseDaoSupport.queryForList("select sum(store) from product_store where depotid=? and store>0  and goodsid in (select goods_id from es_goods g where g.disabled=0 )", new IntegerMapper(), new Object[] { Integer.valueOf(roomid) });

     Integer has_goods = Integer.valueOf(0);
     if ((numList != null) && (numList.isEmpty())) {
       has_goods = (Integer)numList.get(0);
       if (has_goods == null) { has_goods = Integer.valueOf(0);
       }
     }
     if (has_goods.intValue() != 0) {
       return "此仓库仍有商品。不能删除";
     }
     return "";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\DepotManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */