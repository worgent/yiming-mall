 package com.enation.app.shop.core.service.impl;

 import com.enation.app.shop.core.model.Logi;
 import com.enation.app.shop.core.service.ILogiManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.StringUtil;
 import java.util.List;




 public class LogiManager
   extends BaseSupport<Logi>
   implements ILogiManager
 {
   public void delete(Integer[] logi_id)
   {
     String id = StringUtil.implode(",", logi_id);
     if ((id == null) || (id.equals(""))) return;
     String sql = "delete from logi_company where id in (" + id + ")";
     this.baseDaoSupport.execute(sql, new Object[0]);
   }

   public Logi getLogiById(Integer id)
   {
     String sql = "select * from logi_company where id=?";
     Logi a = (Logi)this.baseDaoSupport.queryForObject(sql, Logi.class, new Object[] { id });
     return a;
   }

   public Page pageLogi(String order, Integer page, Integer pageSize)
   {
     order = order == null ? " id desc" : order;
     String sql = "select * from logi_company";
     sql = sql + " order by  " + order;
     Page webpage = this.baseDaoSupport.queryForPage(sql, page.intValue(), pageSize.intValue(), new Object[0]);
     return webpage;
   }

   public void saveAdd(Logi logi)
   {
     this.baseDaoSupport.insert("logi_company", logi);
   }

   public void saveEdit(Logi logi)
   {
     this.baseDaoSupport.update("logi_company", logi, "id=" + logi.getId());
   }

   public List list() {
     String sql = "select * from logi_company";
     return this.baseDaoSupport.queryForList(sql, new Object[0]);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\LogiManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */