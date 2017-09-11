 package com.enation.app.b2b2c.core.service.goods.impl;

 import com.enation.app.b2b2c.core.service.goods.IB2b2cGoodsTagManager;
 import com.enation.app.shop.core.model.Tag;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.StringUtil;
 import org.springframework.stereotype.Component;


 @Component
 public class B2b2cGoodsTagManager
   extends BaseSupport
   implements IB2b2cGoodsTagManager
 {
   public Page list(int pageNo, int pageSize)
   {
     return this.daoSupport.queryForPage("select * from es_tags where ISNULL(store_id) order by tag_id", pageNo, pageSize, new Object[0]);
   }

     @Override
     public Tag getById(Integer tagid) {
         String sql = "select * from tags where tag_id=?";
         Tag tag = (Tag)this.baseDaoSupport.queryForObject(sql, Tag.class, new Object[] { tagid });
         return tag;
     }

     @Override
     public void add(Tag tag) {
         tag.setRel_count(Integer.valueOf(0));
         this.baseDaoSupport.insert("tags", tag);
     }

     @Override
     public void update(Tag tag) {
         this.baseDaoSupport.update("tags", tag, "tag_id=" + tag.getTag_id());

     }

     @Override
     public void delete(Integer[] tagids) {
         String ids = StringUtil.implode(",", tagids);
         if ((ids == null) || (ids.equals(""))) { return;
         }
         this.baseDaoSupport.execute("delete from tags where tag_id in(" + ids + ")", new Object[0]);
         this.daoSupport.execute("delete from " + getTableName("tag_rel") + " where tag_id in(" + ids + ")", new Object[0]);

     }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\goods\impl\B2b2cGoodsTagManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */