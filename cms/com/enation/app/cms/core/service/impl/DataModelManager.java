 package com.enation.app.cms.core.service.impl;

 import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
 import com.enation.app.cms.core.model.DataModel;
 import com.enation.app.cms.core.service.IDataModelManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;





 public class DataModelManager
   extends BaseSupport<DataModel>
   implements IDataModelManager
 {
   @Transactional(propagation=Propagation.REQUIRED)
   public void add(DataModel dataModel)
   {
     dataModel.setIf_audit(Integer.valueOf(0));
     dataModel.setAdd_time(Long.valueOf(System.currentTimeMillis()));
     this.baseDaoSupport.insert("data_model", dataModel);

     StringBuffer createSQL = new StringBuffer();
     createSQL.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
     createSQL.append("<dbsolution>\n");
     createSQL.append("<action>\n");
     createSQL.append("<command>create</command>\n");
     createSQL.append("<table>" + dataModel.getEnglish_name() + "</table>\n");
     createSQL.append("<field><name>id</name><type>int</type><size>8</size><option>11</option></field>\n");
     createSQL.append("<field><name>sort</name><type>int</type><size>8</size><option>00</option></field>\n");
     createSQL.append("<field><name>add_time</name><type>int</type><size>11</size><option>00</option></field>\n");
     createSQL.append("<field><name>lastmodified</name><type>int</type><size>11</size><option>00</option></field>\n");
     createSQL.append("<field><name>hit</name><type>long</type><size>20</size><option>00</option></field>\n");
     createSQL.append("<field><name>able_time</name><type>long</type><size>20</size><option>00</option></field>\n");
     createSQL.append("<field><name>state</name><type>int</type><size>8</size><option>00</option></field>\n");
     createSQL.append("<field><name>user_id</name><type>long</type><size>20</size><option>00</option></field>\n");
     createSQL.append("<field><name>cat_id</name><type>int</type><size>8</size><option>00</option></field>\n");
     createSQL.append("<field><name>is_commend</name><type>int</type><size>4</size><option>00</option></field>\n");
     createSQL.append("<field><name>sys_lock</name><type>int</type><size>4</size><option>00</option><default>0</default></field>\n");
     createSQL.append("<field><name>page_title</name><type>varchar</type><size>255</size><option>00</option></field>\n");
     createSQL.append("<field><name>page_keywords</name><type>varchar</type><size>255</size><option>00</option></field>\n");
     createSQL.append("<field><name>page_description</name><type>memo</type><size>21845</size><option>00</option></field>\n");
     createSQL.append("<field><name>site_code</name><type>int</type><size>11</size><option>00</option><default>100000</default></field>\n");
     createSQL.append("<field><name>siteidlist</name><type>varchar</type><size>255</size><option>00</option></field>\n");
     createSQL.append("</action>\n");
     createSQL.append("</dbsolution>");
     DBSolutionFactory.dbImport(createSQL.toString(), "es_");
   }









   private String createTableName(String tbname)
   {
     tbname = getTableName(tbname);
     return tbname;
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void delete(Integer modelid) {
     DataModel dataModel = get(modelid);


     this.daoSupport.execute("drop table " + createTableName(dataModel.getEnglish_name()), new Object[0]);


     this.baseDaoSupport.execute("delete from data_field where model_id=?", new Object[] { modelid });


     this.baseDaoSupport.execute("delete from data_model where model_id=?", new Object[] { modelid });
   }


   @Transactional(propagation=Propagation.REQUIRED)
   public void edit(DataModel dataModel)
   {
     DataModel oldmodel = get(dataModel.getModel_id());
     this.baseDaoSupport.update("data_model", dataModel, "model_id=" + dataModel.getModel_id());
     String tbname = createTableName(dataModel.getEnglish_name());
     if (!oldmodel.getEnglish_name().equals(tbname)) {
       StringBuffer sql = new StringBuffer("ALTER TABLE ");
       sql.append(getTableName(oldmodel.getEnglish_name()));
       sql.append(" RENAME TO ");
       sql.append(tbname);
       this.daoSupport.execute(sql.toString(), new Object[0]);
     }
   }

   public DataModel get(Integer modelid) {
     String sql = "select * from data_model where model_id=?";
     return (DataModel)this.baseDaoSupport.queryForObject(sql, DataModel.class, new Object[] { modelid });
   }

   public List<DataModel> list() {
     return this.baseDaoSupport.queryForList("select * from data_model order by add_time asc", DataModel.class, new Object[0]);
   }

   public int checkIfModelInUse(Integer modelid) {
     DataModel dataModel = get(modelid);
     return this.daoSupport.queryForInt("select count(0) from " + createTableName(dataModel.getEnglish_name()), new Object[0]);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\service\impl\DataModelManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */