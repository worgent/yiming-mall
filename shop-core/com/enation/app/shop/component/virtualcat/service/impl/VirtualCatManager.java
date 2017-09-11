 package com.enation.app.shop.component.virtualcat.service.impl;

 import com.enation.app.shop.component.virtualcat.model.VirtualCat;
 import com.enation.app.shop.component.virtualcat.service.IVirtualCatManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;



 @Component
 public class VirtualCatManager
   extends BaseSupport<VirtualCat>
   implements IVirtualCatManager
 {
   @Transactional(propagation=Propagation.REQUIRED)
   public void add(VirtualCat cat)
   {
     this.baseDaoSupport.insert("virtual_cat", cat);
     if ((cat.getCid() == null) || (cat.getCid().longValue() == 0L)) {
       int vid = this.baseDaoSupport.getLastId("virtual_cat");
       cat.setVirtual_id(vid);
       cat.setCid(new Long(vid));
       edit(cat);
     }
   }

   public void edit(VirtualCat cat)
   {
     this.baseDaoSupport.update("virtual_cat", cat, "virtual_id=" + cat.getVirtual_id());
   }


   public VirtualCat get(int id)
   {
     return (VirtualCat)this.baseDaoSupport.queryForObject("select * from virtual_cat where virtual_id = ?", VirtualCat.class, new Object[] { Integer.valueOf(id) });
   }

   public void delete(int id)
   {
     this.baseDaoSupport.execute("delete from virtual_cat where virtual_id = ?", new Object[] { Integer.valueOf(id) });
   }

   public List<VirtualCat> list()
   {
     return this.baseDaoSupport.queryForList("select * from virtual_cat order by sort_order", VirtualCat.class, new Object[0]);
   }

   private List<VirtualCat> list(long cid) {
     List<VirtualCat> result = this.baseDaoSupport.queryForList("select * from virtual_cat where parent_cid = ? order by sort_order", VirtualCat.class, new Object[] { Long.valueOf(cid) });
     for (VirtualCat cat : result) {
       List<VirtualCat> children = list(cat.getCid().longValue());
       if ((children != null) && (children.size() > 0))
         cat.setChildren(children);
     }
     return result;
   }


   public List<VirtualCat> getTree()
   {
     return list(0L);
   }

   public List<VirtualCat> listChildren(long cid)
   {
     List<VirtualCat> result = this.baseDaoSupport.queryForList("select * from virtual_cat where parent_cid = ? order by sort_order", VirtualCat.class, new Object[] { Long.valueOf(cid) });
     return result;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\virtualcat\service\impl\VirtualCatManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */