 package com.enation.app.cms.core.service.impl;

 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.model.DataModel;
 import com.enation.app.cms.core.plugin.AbstractFieldPlugin;
 import com.enation.app.cms.core.plugin.ArticlePluginBundle;
 import com.enation.app.cms.core.service.IDataFieldManager;
 import com.enation.app.cms.core.service.IDataModelManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;



 public class DataFieldManager
   extends BaseSupport<DataField>
   implements IDataFieldManager
 {
   private IDataModelManager dataModelManager;
   private ArticlePluginBundle articlePluginBundle;

   @Transactional(propagation=Propagation.REQUIRED)
   public Integer add(DataField dataField)
   {
     dataField.setAdd_time(Long.valueOf(System.currentTimeMillis()));
     this.baseDaoSupport.insert("data_field", dataField);
     Integer fieldid = Integer.valueOf(this.baseDaoSupport.getLastId("data_field"));
     DataModel datamodel = this.dataModelManager.get(dataField.getModel_id());
     StringBuffer sql = new StringBuffer();
     sql.append("alter table ");
     sql.append(getModelTableName(datamodel.getEnglish_name()));
     sql.append(" add ");
     sql.append(getFieldTypeSql(dataField));

     this.daoSupport.execute(sql.toString(), new Object[0]);
     return fieldid;
   }

   private String getModelTableName(String tbname) {
     tbname = getTableName(tbname);
     return tbname;
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void delete(Integer fieldid)
   {
     DataField dataField = get(fieldid);
     DataModel dataModel = this.dataModelManager.get(dataField.getModel_id());


     String sql = "alter table " + getModelTableName(dataModel.getEnglish_name()) + " drop " + dataField.getEnglish_name();
     this.daoSupport.execute(sql, new Object[0]);


     sql = "delete from  data_field where field_id=?";
     this.baseDaoSupport.execute(sql, new Object[] { fieldid });
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void edit(DataField dataField) {
     DataField oldDataField = get(dataField.getField_id());
     this.baseDaoSupport.update("data_field", dataField, "field_id=" + dataField.getField_id());
     if (!oldDataField.getEnglish_name().equals(dataField.getEnglish_name())) {
       DataModel dataModel = this.dataModelManager.get(dataField.getModel_id());
       StringBuffer sql = new StringBuffer();
       sql.append("alter table ");
       sql.append(getModelTableName(dataModel.getEnglish_name()));
       sql.append(" change column ");
       sql.append(oldDataField.getEnglish_name());
       sql.append(" ");
       sql.append(getFieldTypeSql(dataField));
       this.daoSupport.execute(sql.toString(), new Object[0]);
     }
   }

   public List<DataField> list(Integer modelid) {
     return this.baseDaoSupport.queryForList("select * from data_field where model_id=? order by taxis", DataField.class, new Object[] { modelid });
   }

   public DataField get(Integer fieldid) {
     String sql = "select * from data_field where field_id=?";
     return (DataField)this.baseDaoSupport.queryForObject(sql, DataField.class, new Object[] { fieldid });
   }









   private String getFieldTypeSql(DataField field)
   {
     return field.getEnglish_name() + " " + this.articlePluginBundle.getFieldPlugin(field).getDataType();
   }

   public IDataModelManager getDataModelManager() {
     return this.dataModelManager;
   }

   public void setDataModelManager(IDataModelManager dataModelManager) {
     this.dataModelManager = dataModelManager;
   }

   public List<DataField> listByCatId(Integer catid) {
     String sql = "select df.* from " + getTableName("data_field") + " df," + getTableName("data_model") + " dm," + getTableName("data_cat") + " c where df.model_id=dm.model_id and dm.model_id= c.model_id and c.cat_id=?" + " order by df.taxis";





     return this.daoSupport.queryForList(sql, DataField.class, new Object[] { catid });
   }

   public List<DataField> listIsShow(Integer modelid) {
     return this.baseDaoSupport.queryForList("select * from data_field where model_id=? and is_show=1 order by taxis", DataField.class, new Object[] { modelid });
   }

   public void saveSort(Integer[] ids, Integer[] sorts)
   {
     for (int i = 0; i < ids.length; i++) {
       String sql = "update data_field set taxis=? where field_id=?";
       this.baseDaoSupport.execute(sql, new Object[] { sorts[i], ids[i] });
     }
   }

   public ArticlePluginBundle getArticlePluginBundle() {
     return this.articlePluginBundle;
   }

   public void setArticlePluginBundle(ArticlePluginBundle articlePluginBundle) {
     this.articlePluginBundle = articlePluginBundle;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\service\impl\DataFieldManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */