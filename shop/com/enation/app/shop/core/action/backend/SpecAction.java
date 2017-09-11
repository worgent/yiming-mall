 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.component.spec.service.ISpecManager;
 import com.enation.app.shop.component.spec.service.ISpecValueManager;
 import com.enation.app.shop.core.model.SpecValue;
 import com.enation.app.shop.core.model.Specification;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.action.WWAction;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;













 @Component
 @Scope("prototype")
 @ParentPackage("shop_default")
 @Namespace("/shop/admin")
 @Action("spec")
 @Results({@org.apache.struts2.convention.annotation.Result(name="input", type="freemarker", location="/shop/admin/spec/spec_input.html"), @org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/shop/admin/spec/spec_edit.html"), @org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/shop/admin/spec/spec_list.html")})
 public class SpecAction
   extends WWAction
 {
   private ISpecManager specManager;
   private ISpecValueManager specValueManager;
   private Integer specId;
   private Map specView;
   private List specList;
   private List valueList;
   private Specification spec;
   private String[] valueArray;
   private String[] imageArray;
   private Integer[] valueIdArray;
   private Integer[] spec_id;
   private int valueid;

   public String checkUsed()
   {
     if (this.specManager.checkUsed(this.spec_id)) {
       this.json = "{result:1}";
     } else {
       this.json = "{result:0}";
     }

     return "json_message";
   }







   public String checkValueUsed()
   {
     boolean isused = this.specManager.checkUsed(Integer.valueOf(this.valueid));

     if (isused) {
       this.json = "{result:1}";
     } else {
       this.json = "{result:0}";
     }

     return "json_message";
   }



   public String list()
   {
     return "list";
   }



   public String listJson()
   {
     this.specList = this.specManager.list();
     showGridJson(this.specList);
     return "json_message";
   }

   public String add() {
     return "input";
   }

   public String saveAdd()
   {
     fillSpecValueList();
     try
     {
       this.specManager.add(this.spec, this.valueList);
       showSuccessJson("规格添加成功");
     } catch (Exception e) {
       showErrorJson("规格添加失败");
       this.logger.error("规格添加失败", e);
     }
     return "json_message";
   }

   public String edit() {
     this.specView = this.specManager.get(this.specId);
     this.valueList = this.specValueManager.list(this.specId);
     return "edit";
   }

   public String saveEdit() {
     fillSpecValueList();
     try {
       this.specManager.edit(this.spec, this.valueList);
       showSuccessJson("规格修改成功");
     } catch (Exception e) {
       showErrorJson("规格修改失败");
       this.logger.error("规格修改失败", e);
     }
     return "json_message";
   }

   private List<SpecValue> fillSpecValueList() {
     this.valueList = new ArrayList();

     if (this.valueArray != null) {
       for (int i = 0; i < this.valueArray.length; i++) {
         String value = this.valueArray[i];

         SpecValue specValue = new SpecValue();
         specValue.setSpec_value_id(this.valueIdArray[i]);
         specValue.setSpec_value(value);
         if (this.imageArray != null) {
           String image = this.imageArray[i];
           if ((image == null) || (image.equals(""))) { image = "/shop/admin/spec/image/spec_def.gif";
           } else
             image = UploadUtil.replacePath(image);
           specValue.setSpec_image(image);
         } else {
           specValue.setSpec_image("/shop/admin/spec/image/spec_def.gif");
         }
         this.valueList.add(specValue);
       }
     }
     return this.valueList;
   }

   public String delete()
   {
     this.specManager.delete(this.spec_id);
     showSuccessJson("规格删除成功");
     return "json_message";
   }


   public ISpecManager getSpecManager()
   {
     return this.specManager;
   }

   public void setSpecManager(ISpecManager specManager) {
     this.specManager = specManager;
   }

   public ISpecValueManager getSpecValueManager() {
     return this.specValueManager;
   }

   public void setSpecValueManager(ISpecValueManager specValueManager) {
     this.specValueManager = specValueManager;
   }

   public List getSpecList() {
     return this.specList;
   }

   public void setSpecList(List specList) {
     this.specList = specList;
   }

   public Specification getSpec() {
     return this.spec;
   }

   public void setSpec(Specification spec) {
     this.spec = spec;
   }

   public String[] getValueArray() {
     return this.valueArray;
   }

   public void setValueArray(String[] valueArray) {
     this.valueArray = valueArray;
   }

   public String[] getImageArray() {
     return this.imageArray;
   }

   public void setImageArray(String[] imageArray) {
     this.imageArray = imageArray;
   }


   public Map getSpecView()
   {
     return this.specView;
   }

   public void setSpecView(Map specView) {
     this.specView = specView;
   }

   public List getValueList() {
     return this.valueList;
   }

   public void setValueList(List valueList) {
     this.valueList = valueList;
   }


   public Integer getSpecId()
   {
     return this.specId;
   }


   public void setSpecId(Integer specId)
   {
     this.specId = specId;
   }


   public Integer[] getSpec_id()
   {
     return this.spec_id;
   }


   public void setSpec_id(Integer[] spec_id)
   {
     this.spec_id = spec_id;
   }


   public Integer[] getValueIdArray()
   {
     return this.valueIdArray;
   }

   public void setValueIdArray(Integer[] valueIdArray) {
     this.valueIdArray = valueIdArray;
   }


   public int getValueid()
   {
     return this.valueid;
   }


   public void setValueid(int valueid)
   {
     this.valueid = valueid;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\SpecAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */