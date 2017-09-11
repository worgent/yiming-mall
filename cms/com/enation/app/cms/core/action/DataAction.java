 package com.enation.app.cms.core.action;

 import com.enation.app.base.core.model.MultiSite;
 import com.enation.app.base.core.service.IMultiSiteManager;
 import com.enation.app.cms.core.model.DataCat;
 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.plugin.ArticlePluginBundle;
 import com.enation.app.cms.core.service.IDataCatManager;
 import com.enation.app.cms.core.service.IDataFieldManager;
 import com.enation.app.cms.core.service.IDataManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.framework.action.WWAction;
 import java.util.List;
 import java.util.Map;
 import org.apache.log4j.Logger;





 public class DataAction
   extends WWAction
 {
   private IDataFieldManager dataFieldManager;
   private IDataCatManager dataCatManager;
   private IDataManager dataManager;
   private ArticlePluginBundle articlePluginBundle;
   private IMultiSiteManager multiSiteManager;
   private Integer dataid;
   private Integer catid;
   private Integer modelid;
   private List<DataField> fieldList;
   private DataCat cat;
   private String searchField;
   private String searchText;
   private List<DataCat> catList;
   private Map article;
   private boolean isEdit;
   private Integer siteid;
   private Integer[] dataids;
   private Integer[] sorts;
   private EopSite site;

   public String updateSort()
   {
     try
     {
       this.dataManager.updateSort(this.dataids, this.sorts, this.catid);
       showSuccessJson("修改排序成功");
     } catch (Exception e) {
       this.logger.error(e);
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }







   public String implist()
   {
     Integer sitecode = Integer.valueOf(100000);
     if (this.siteid != null) {
       MultiSite site = this.multiSiteManager.get(this.siteid.intValue());
       sitecode = site.getCode();
     }
     this.webpage = this.dataManager.list(this.catid, getPage(), 5, sitecode);

     this.cat = this.dataCatManager.get(this.catid);
     this.fieldList = this.dataFieldManager.listIsShow(this.cat.getModel_id());
     return "implist";
   }

   public String importdata() {
     this.dataManager.importdata(this.catid, this.dataids);
     this.json = "{result:0}";
     return "json_message";
   }

   public String edit() {
     this.isEdit = true;
     this.article = this.dataManager.get(this.dataid, this.catid, false);
     if (owner(this.article.get("site_code"))) {
       this.cat = this.dataCatManager.get(this.catid);
       this.catList = this.dataCatManager.listAllChildren(Integer.valueOf(0));

       this.modelid = this.cat.getModel_id();
       this.fieldList = this.dataFieldManager.listByCatId(this.catid);
       for (DataField field : this.fieldList) {
         field.setInputHtml(this.articlePluginBundle.onDisplay(field, this.article.get(field.getEnglish_name())));
       }
       return "input";
     }
     showErrorJson("非本站内容，不能编辑！");
     return "json_message";
   }

   public String add() {
     this.isEdit = false;
     this.cat = this.dataCatManager.get(this.catid);
     this.catList = this.dataCatManager.listAllChildren(this.catid);

     this.modelid = this.cat.getModel_id();
     this.fieldList = this.dataFieldManager.listByCatId(this.catid);
     for (DataField field : this.fieldList) {
       field.setInputHtml(this.articlePluginBundle.onDisplay(field, null));
     }
     return "input";
   }

   public String saveAdd()
   {
     try {
       this.dataManager.add(this.modelid, this.catid);
       showSuccessJson("文章添加成功");
     } catch (Exception e) {
       showErrorJson("文章添加失败");
     }
     return "json_message";
   }

   public String saveEdit() {
     try {
       this.dataManager.edit(this.modelid, this.catid, this.dataid);
       showSuccessJson("文章修改成功");
     } catch (Exception e) {
       showErrorJson("文章修改失败");
     }
     return "json_message";
   }


   public EopSite getSite()
   {
     return this.site;
   }

   public void setSite(EopSite site) {
     this.site = site;
   }

   public String list() {
     this.site = EopContext.getContext().getCurrentSite();
     this.cat = this.dataCatManager.get(this.catid);
     this.fieldList = this.dataFieldManager.listIsShow(this.cat.getModel_id());
     return "list";
   }

   public String listJson() { String term = null;
     if (this.searchText != null)
       term = "and " + this.searchField + " like '%" + this.searchText + "%'";
     this.webpage = this.dataManager.listAll(this.catid, term, null, false, getPage(), getPageSize());
     showGridJson(this.webpage);
     return "json_message";
   }

   public String dlgList() { this.webpage = this.dataManager.listAll(this.catid, null, getPage(), 15);
     this.cat = this.dataCatManager.get(this.catid);
     this.fieldList = this.dataFieldManager.listIsShow(this.cat.getModel_id());
     return "dlglist";
   }

   private boolean owner(Object site_code) {
     if (EopContext.getContext().getCurrentSite().getMulti_site().intValue() == 0) {
       return true;
     }
     if (site_code == null)
       return true;
     if (site_code.toString().equals(EopContext.getContext().getCurrentChildSite().getCode().toString()))
     {

       return true;
     }
     return false;
   }


   public String delete()
   {
     this.article = this.dataManager.get(this.dataid, this.catid, false);
     if ((this.article.get("sys_lock") != null) && (this.article.get("sys_lock").toString().equals("1"))) {
       showErrorJson("此文章为系统文章，不能删除！");
     }
     else if (owner(this.article.get("site_code"))) {
       this.dataManager.delete(this.catid, this.dataid);
       showSuccessJson("文章删除成功");
     } else {
       showErrorJson("非本站内容，不能删除！");
     }

     return "json_message";
   }

   public Integer getCatid() {
     return this.catid;
   }

   public void setCatid(Integer catid) {
     this.catid = catid;
   }

   public IDataFieldManager getDataFieldManager() {
     return this.dataFieldManager;
   }

   public void setDataFieldManager(IDataFieldManager dataFieldManager) {
     this.dataFieldManager = dataFieldManager;
   }

   public List getFieldList() {
     return this.fieldList;
   }

   public Integer getModelid() {
     return this.modelid;
   }

   public void setModelid(Integer modelid) {
     this.modelid = modelid;
   }

   public IDataCatManager getDataCatManager() {
     return this.dataCatManager;
   }

   public void setDataCatManager(IDataCatManager dataCatManager) {
     this.dataCatManager = dataCatManager;
   }

   public DataCat getCat() {
     return this.cat;
   }

   public void setCat(DataCat cat) {
     this.cat = cat;
   }

   public IDataManager getDataManager() {
     return this.dataManager;
   }

   public void setDataManager(IDataManager dataManager) {
     this.dataManager = dataManager;
   }

   public ArticlePluginBundle getArticlePluginBundle() {
     return this.articlePluginBundle;
   }

   public void setArticlePluginBundle(ArticlePluginBundle articlePluginBundle) {
     this.articlePluginBundle = articlePluginBundle;
   }

   public void setFieldList(List<DataField> fieldList) {
     this.fieldList = fieldList;
   }

   public Map getArticle() {
     return this.article;
   }

   public void setArticle(Map article) {
     this.article = article;
   }

   public boolean getIsEdit() {
     return this.isEdit;
   }

   public void setIsEdit(boolean isEdit) {
     this.isEdit = isEdit;
   }

   public Integer getDataid() {
     return this.dataid;
   }

   public void setDataid(Integer dataid) {
     this.dataid = dataid;
   }

   public Integer[] getDataids() {
     return this.dataids;
   }

   public void setDataids(Integer[] dataids) {
     this.dataids = dataids;
   }

   public void setEdit(boolean isEdit) {
     this.isEdit = isEdit;
   }

   public Integer[] getSorts() {
     return this.sorts;
   }

   public void setSorts(Integer[] sorts) {
     this.sorts = sorts;
   }

   public List<DataCat> getCatList() {
     return this.catList;
   }

   public void setCatList(List<DataCat> catList) {
     this.catList = catList;
   }

   public Integer getSiteid() {
     return this.siteid;
   }

   public void setSiteid(Integer siteid) {
     this.siteid = siteid;
   }

   public IMultiSiteManager getMultiSiteManager() {
     return this.multiSiteManager;
   }

   public void setMultiSiteManager(IMultiSiteManager multiSiteManager) {
     this.multiSiteManager = multiSiteManager;
   }

   public String getSearchField() {
     return this.searchField;
   }

   public void setSearchField(String searchField) {
     this.searchField = searchField;
   }

   public String getSearchText() {
     return this.searchText;
   }

   public void setSearchText(String searchText) {
     this.searchText = searchText;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\action\DataAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */