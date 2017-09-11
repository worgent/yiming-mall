 package com.enation.app.base.core.service.impl;

 import com.enation.app.base.core.model.AdColumn;
 import com.enation.app.base.core.service.IAdColumnManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.StringUtil;
 import java.util.List;








 public class AdColumnManager
   extends BaseSupport<AdColumn>
   implements IAdColumnManager
 {
   public void addAdvc(AdColumn adColumn)
   {
     this.baseDaoSupport.insert("adcolumn", adColumn);
   }

   public void delAdcs(Integer[] ids)
   {
     if ((ids == null) || (ids.equals("")))
       return;
     String id_str = StringUtil.arrayToString(ids, ",");
     String sql = "delete from adcolumn where acid in (" + id_str + ")";

     this.baseDaoSupport.execute(sql, new Object[0]);
   }

   public AdColumn getADcolumnDetail(Long acid)
   {
     AdColumn adColumn = (AdColumn)this.baseDaoSupport.queryForObject("select * from adcolumn where acid = ?", AdColumn.class, new Object[] { acid });
     return adColumn;
   }

   public List listAllAdvPos()
   {
     List<AdColumn> list = this.baseDaoSupport.queryForList("select * from adcolumn", AdColumn.class, new Object[0]);
     return list;
   }

   public Page pageAdvPos(int page, int pageSize)
   {
     String sql = "select * from adcolumn order by acid desc";
     Page rpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
     return rpage;
   }

   public void updateAdvc(AdColumn adColumn)
   {
     this.baseDaoSupport.update("adcolumn", adColumn, "acid = " + adColumn.getAcid());
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\impl\AdColumnManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */