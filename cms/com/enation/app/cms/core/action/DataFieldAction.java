 package com.enation.app.cms.core.action;

 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.plugin.ArticlePluginBundle;
 import com.enation.app.cms.core.service.IDataFieldManager;
 import com.enation.framework.action.WWAction;
 import java.util.List;
 import org.apache.log4j.Logger;



 public class DataFieldAction
   extends WWAction
 {
   private IDataFieldManager dataFieldManager;
   private ArticlePluginBundle articlePluginBundle;
   private DataField dataField;
   private List fieldPluginList;
   private Integer fieldid;
   private Integer modelid;
   private boolean isEdit;
   private Integer[] ids;
   private Integer[] sorts;

   public String add()
   {
     this.isEdit = false;
     this.fieldPluginList = this.articlePluginBundle.getFieldPlugins();
     return "add";
   }

   public String edit() {
     this.isEdit = true;
     this.dataField = this.dataFieldManager.get(this.fieldid);
     this.fieldPluginList = this.articlePluginBundle.getFieldPlugins();
     return "edit";
   }

   public String saveAdd() {
     try {
       this.fieldid = this.dataFieldManager.add(this.dataField);
       showSuccessJson("字段添加成功");
     } catch (RuntimeException e) {
       showErrorJson("字段添加出错" + e.getMessage());
     }
     return "json_message";
   }

   public String saveEdit() {
     try {
       this.dataFieldManager.edit(this.dataField);
       showSuccessJson("字段修改成功");
     } catch (RuntimeException e) {
       showErrorJson("字段修改出错");
     }
     return "json_message";
   }

   public String delete() {
     try {
       this.dataFieldManager.delete(this.fieldid);
       showSuccessJson("字段删除成功");
     } catch (RuntimeException e) {
       this.logger.error(e.getMessage(), e);
       showErrorJson("字段删除出错");
     }
     return "json_message";
   }

   public String saveSort() {
     try {
       this.dataFieldManager.saveSort(this.ids, this.sorts);
       this.json = "{result:1,message:'排序更新成功'}";
     } catch (RuntimeException e) {
       this.logger.error(e.getMessage(), e);
       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
     }

     return "json_message";
   }

   public IDataFieldManager getDataFieldManager() { return this.dataFieldManager; }

   public void setDataFieldManager(IDataFieldManager dataFieldManager)
   {
     this.dataFieldManager = dataFieldManager;
   }

   public DataField getDataField() {
     return this.dataField;
   }

   public void setDataField(DataField dataField) {
     this.dataField = dataField;
   }

   public Integer getFieldid() {
     return this.fieldid;
   }

   public void setFieldid(Integer fieldid) {
     this.fieldid = fieldid;
   }

   public Integer getModelid() {
     return this.modelid;
   }

   public void setModelid(Integer modelid) {
     this.modelid = modelid;
   }

   public ArticlePluginBundle getArticlePluginBundle() {
     return this.articlePluginBundle;
   }

   public void setArticlePluginBundle(ArticlePluginBundle articlePluginBundle) {
     this.articlePluginBundle = articlePluginBundle;
   }

   public List getFieldPluginList() {
     return this.fieldPluginList;
   }

   public void setFieldPluginList(List fieldPluginList) {
     this.fieldPluginList = fieldPluginList;
   }

   public void setIsEdit(boolean isEdit) {
     this.isEdit = isEdit;
   }

   public boolean getIsEdit() {
     return this.isEdit;
   }

   public Integer[] getIds() {
     return this.ids;
   }

   public void setIds(Integer[] ids) {
     this.ids = ids;
   }

   public Integer[] getSorts() {
     return this.sorts;
   }

   public void setSorts(Integer[] sorts) {
     this.sorts = sorts;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\action\DataFieldAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */