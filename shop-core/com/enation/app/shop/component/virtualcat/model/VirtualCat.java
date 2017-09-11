 package com.enation.app.shop.component.virtualcat.model;

 import com.enation.framework.database.NotDbField;
 import com.enation.framework.database.PrimaryKeyField;
 import java.util.List;

































 public class VirtualCat
 {
   private int virtual_id;
   private Long cid;
   private long created;
   private long modified;
   private String name;
   private Long parent_cid;
   private String pic_url;
   private Long sort_order;
   private String type;
   private List<VirtualCat> children;

   @PrimaryKeyField
   public int getVirtual_id()
   {
     return this.virtual_id;
   }

   public void setVirtual_id(int virtual_id) {
     this.virtual_id = virtual_id;
   }

   public Long getCid() {
     return this.cid;
   }

   public void setCid(Long cid) {
     this.cid = cid;
   }

   public long getCreated() {
     return this.created;
   }

   public void setCreated(long created) {
     this.created = created;
   }

   public long getModified() {
     return this.modified;
   }

   public void setModified(long modified) {
     this.modified = modified;
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }

   public Long getParent_cid() {
     return this.parent_cid;
   }

   public void setParent_cid(Long parent_cid) {
     this.parent_cid = parent_cid;
   }

   public String getPic_url() {
     return this.pic_url;
   }

   public void setPic_url(String pic_url) {
     this.pic_url = pic_url;
   }

   public Long getSort_order() {
     return this.sort_order;
   }

   public void setSort_order(Long sort_order) {
     this.sort_order = sort_order;
   }

   public String getType() {
     return this.type;
   }

   public void setType(String type) {
     this.type = type;
   }

   @NotDbField
   public List<VirtualCat> getChildren() { return this.children; }

   public void setChildren(List<VirtualCat> children)
   {
     this.children = children;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\virtualcat\model\VirtualCat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */