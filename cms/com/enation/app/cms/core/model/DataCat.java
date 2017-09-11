 package com.enation.app.cms.core.model;

 import com.enation.framework.database.NotDbField;
 import com.enation.framework.database.PrimaryKeyField;
 import java.io.Serializable;
 import java.util.List;











 public class DataCat
   implements Serializable
 {
   private static final long serialVersionUID = 3764718745312895356L;
   protected Integer cat_id;
   protected String name;
   protected Integer parent_id;
   protected String cat_path;
   protected Integer cat_order;
   protected Integer model_id;
   protected Integer if_audit;
   private String url;
   private String detail_url;
   private Integer tositemap;
   private String descript;
   protected List<DataCat> children;
   private boolean hasChildren;

   public String getDescript()
   {
     return this.descript;
   }

   public void setDescript(String descript) { this.descript = descript; }

   public Integer getIf_audit() {
     return this.if_audit;
   }

   public void setIf_audit(Integer if_audit) { this.if_audit = if_audit; }

   @PrimaryKeyField
   public Integer getCat_id()
   {
     return this.cat_id;
   }

   public void setCat_id(Integer cat_id) { this.cat_id = cat_id; }

   public Integer getCat_order()
   {
     return this.cat_order;
   }

   public void setCat_order(Integer cat_order) { this.cat_order = cat_order; }

   public String getCat_path() {
     return this.cat_path;
   }

   public void setCat_path(String cat_path) { this.cat_path = cat_path; }

   public Integer getModel_id() {
     return this.model_id;
   }

   public void setModel_id(Integer model_id) { this.model_id = model_id; }

   public String getName() {
     return this.name;
   }

   public void setName(String name) { this.name = name; }

   public Integer getParent_id() {
     return this.parent_id;
   }

   public void setParent_id(Integer parent_id) { this.parent_id = parent_id; }

   @NotDbField
   public List<DataCat> getChildren()
   {
     return this.children;
   }

   public void setChildren(List<DataCat> children) { this.children = children; }

   @NotDbField
   public boolean getHasChildren()
   {
     this.hasChildren = ((this.children != null) && (!this.children.isEmpty()));
     return this.hasChildren;
   }

   public void setHasChildren(boolean hasChildren)
   {
     this.hasChildren = hasChildren;
   }

   public String getUrl() { return this.url; }

   public void setUrl(String url) {
     this.url = url;
   }

   public String getDetail_url() { return this.detail_url; }

   public void setDetail_url(String detailUrl) {
     this.detail_url = detailUrl;
   }

   public Integer getTositemap() { return this.tositemap; }

   public void setTositemap(Integer tositemap) {
     this.tositemap = tositemap;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\model\DataCat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */