 package com.enation.framework.component;

 import java.util.List;

 public class PluginView
 {
   private String id;
   private String name;
   private List<String> bundleList;

   public PluginView() {
     this.bundleList = new java.util.ArrayList();
   }

   public void addBundle(String beanid) {
     this.bundleList.add(beanid);
   }

   public String getId()
   {
     return this.id;
   }

   public void setId(String id) { this.id = id; }

   public String getName() {
     return this.name;
   }

   public void setName(String name) { this.name = name; }

   public List<String> getBundleList() {
     return this.bundleList;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\component\PluginView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */