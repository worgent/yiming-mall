 package com.enation.app.b2b2c.core.service.impl;

 import com.enation.app.b2b2c.core.model.MemberCollect;
 import com.enation.app.b2b2c.core.service.IStoreCollectManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.DateUtil;
 import org.springframework.stereotype.Component;








 @Component
 public class StoreCollectManager
   extends BaseSupport
   implements IStoreCollectManager
 {
   public void addCollect(MemberCollect collect)
   {
     Integer num = Integer.valueOf(this.baseDaoSupport.queryForInt("select count(0) from es_member_collect where member_id=? and store_id=?", new Object[] { collect.getMember_id(), collect.getStore_id() }));
     if (num.intValue() != 0) {
       throw new RuntimeException("店铺已收藏！");
     }
     collect.setCreate_time(Long.valueOf(DateUtil.getDatelineLong()));
     this.baseDaoSupport.insert("es_member_collect", collect);
   }






   public void delCollect(Integer collect_id)
   {
     this.baseDaoSupport.execute("delete from es_member_collect where id=?", new Object[] { collect_id });
   }





   public Page getList(Integer memberid, int page, int pageSize)
   {
     String sql = "select s.*,m.id celloct_id from es_store s LEFT JOIN es_member_collect m ON s.store_id=m.store_id where s.store_id in (select store_id from es_member_collect where member_id=?)";
     Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[] { memberid });
     return webpage;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\impl\StoreCollectManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */