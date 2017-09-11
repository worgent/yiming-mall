 package com.enation.app.shop.core.service.impl;

 import com.enation.app.shop.core.model.DlyCenter;
 import com.enation.app.shop.core.service.IDlyCenterManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.util.StringUtil;
 import java.util.List;





 public class DlyCenterManager
   extends BaseSupport<DlyCenter>
   implements IDlyCenterManager
 {
   public void add(DlyCenter dlyCenter)
   {
     if (dlyCenter.getChoose().equals("true")) {
       this.daoSupport.execute("update es_dly_center set choose='false' where choose='true'", new Object[0]);
     }
     this.baseDaoSupport.insert("dly_center", dlyCenter);
   }

   public void delete(Integer[] id)
   {
     if ((id == null) || (id.length == 0)) return;
     String ids = StringUtil.arrayToString(id, ",");
     this.baseDaoSupport.execute("update dly_center set disabled = 'true' where dly_center_id in (" + ids + ")", new Object[0]);
   }


   public void edit(DlyCenter dlyCenter)
   {
     if (dlyCenter.getChoose().equals("true")) {
       this.daoSupport.execute("update es_dly_center set choose='false' where choose='true'", new Object[0]);
     }
     this.baseDaoSupport.update("dly_center", dlyCenter, "dly_center_id = " + dlyCenter.getDly_center_id());
   }


   public List<DlyCenter> list()
   {
     return this.baseDaoSupport.queryForList("select * from dly_center where disabled = 'false' order by dly_center_id desc", DlyCenter.class, new Object[0]);
   }

   public DlyCenter get(Integer dlyCenterId)
   {
     return (DlyCenter)this.baseDaoSupport.queryForObject("select * from dly_center where dly_center_id = ?", DlyCenter.class, new Object[] { dlyCenterId });
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\DlyCenterManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */