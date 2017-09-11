 package com.enation.app.cms.core.action;

 import com.enation.app.cms.core.model.DataCat;
 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.service.IDataCatManager;
 import com.enation.app.cms.core.service.IDataFieldManager;
 import com.enation.app.cms.core.service.IDataManager;
 import com.enation.framework.action.WWAction;
 import java.util.List;




 public class DataSelectorAction
   extends WWAction
 {
   private IDataManager dataManager;
   private IDataCatManager dataCatManager;
   private List<DataCat> catList;
   private List<DataField> fieldList;
   private IDataFieldManager dataFieldManager;
   private int catid;

   public String showDialog()
   {
     this.catList = this.dataCatManager.listAllChildren(Integer.valueOf(0));
     return "dialog";
   }

   public String list() {
     this.fieldList = this.dataFieldManager.listByCatId(Integer.valueOf(this.catid));
     this.webpage = this.dataManager.list(Integer.valueOf(this.catid), getPage(), 15);
     return "list";
   }

   public IDataManager getDataManager() {
     return this.dataManager;
   }

   public void setDataManager(IDataManager dataManager) {
     this.dataManager = dataManager;
   }

   public IDataCatManager getDataCatManager() {
     return this.dataCatManager;
   }

   public void setDataCatManager(IDataCatManager dataCatManager) {
     this.dataCatManager = dataCatManager;
   }

   public List<DataCat> getCatList() {
     return this.catList;
   }

   public void setCatList(List<DataCat> catList) {
     this.catList = catList;
   }

   public int getCatid() {
     return this.catid;
   }

   public void setCatid(int catid) {
     this.catid = catid;
   }

   public IDataFieldManager getDataFieldManager() {
     return this.dataFieldManager;
   }

   public void setDataFieldManager(IDataFieldManager dataFieldManager) {
     this.dataFieldManager = dataFieldManager;
   }

   public List<DataField> getFieldList() {
     return this.fieldList;
   }

   public void setFieldList(List<DataField> fieldList) {
     this.fieldList = fieldList;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\action\DataSelectorAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */