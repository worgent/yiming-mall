 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.core.model.Tag;
 import com.enation.app.shop.core.service.ITagManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.action.WWAction;
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
 @Action("tag")
 @Results({
         @org.apache.struts2.convention.annotation.Result(name="add", type="freemarker", location="/shop/admin/tag/add.html"),
         @org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/shop/admin/tag/edit.html"),
         @org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/shop/admin/tag/tag_list.html")})
 public class TagAction
   extends WWAction
 {
   private ITagManager tagManager;
   private Tag tag;
   private Integer[] tag_id;
   private Integer tagId;

   public String checkJoinGoods()
   {
     if (this.tagManager.checkJoinGoods(this.tag_id)) {
       this.json = "{result:1}";
     } else {
       this.json = "{result:0}";
     }
     return "json_message";
   }

   public String checkname() {
     if (this.tagManager.checkname(this.tag.getTag_name(), this.tag.getTag_id())) {
       this.json = "{result:1}";
     } else {
       this.json = "{result:0}";
     }
     return "json_message";
   }

   public String add()
   {
     return "add";
   }

   public String edit() {
     this.tag = this.tagManager.getById(this.tagId);
     return "edit";
   }

   public String saveAdd()
   {
     try
     {
       this.tagManager.add(this.tag);
       showSuccessJson("添加标签成功");
     } catch (Exception e) {
       showErrorJson("添加标签失败");
       this.logger.error("添加标签失败", e);
     }
     return "json_message";
   }



   public String saveEdit()
   {
     if ((EopSetting.IS_DEMO_SITE) &&
       (this.tag.getTag_id().intValue() <= 3)) {
       showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }


     this.tagManager.update(this.tag);
     showSuccessJson("商品修改成功");
     return "json_message";
   }

   public String delete() {
     if (EopSetting.IS_DEMO_SITE) {
       for (Integer tid : this.tag_id) {
         if (tid.intValue() <= 3) {
           showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
           return "json_message";
         }
       }
     }
     try
     {
       this.tagManager.delete(this.tag_id);
       showSuccessJson("标签删除成功");
     } catch (Exception e) {
       showErrorJson("标签删除失败");
       this.logger.error("标签删除失败", e);
     }
     return "json_message";
   }

   public String list() {
     return "list";
   }


   public String listJson()
   {
     this.webpage = this.tagManager.list(getPage(), getPageSize());
     showGridJson(this.webpage);
     return "json_message";
   }

   public ITagManager getTagManager() {
     return this.tagManager;
   }

   public void setTagManager(ITagManager tagManager) {
     this.tagManager = tagManager;
   }

   public Tag getTag() {
     return this.tag;
   }

   public void setTag(Tag tag) {
     this.tag = tag;
   }

   public Integer[] getTag_id() {
     return this.tag_id;
   }

   public void setTag_id(Integer[] tag_id) {
     this.tag_id = tag_id;
   }

   public Integer getTagId() {
     return this.tagId;
   }

   public void setTagId(Integer tagId) {
     this.tagId = tagId;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\TagAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */