 package com.enation.app.b2b2c.core.action.backend.goods;

 import com.enation.app.b2b2c.core.service.goods.IB2b2cGoodsTagManager;
 import com.enation.app.shop.core.model.Tag;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.action.WWAction;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.stereotype.Component;











 @Component
 @ParentPackage("eop_default")
 @Namespace("/b2b2c/admin")
 @Results({
         @org.apache.struts2.convention.annotation.Result(name="add", type="freemarker", location="/b2b2c/admin/tags/add.html"),
         @org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/b2b2c/admin/tags/edit.html"),
         @org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/b2b2c/admin/tags/tag_list.html")})
 @Action("b2b2cTag")
 public class B2b2cTagAction
   extends WWAction
 {
   private IB2b2cGoodsTagManager b2b2cGoodsTagManager;
     private Tag tag;
     private Integer[] tag_id;
     private Integer tagId;

   public String list() { return "list"; }

   public String listJson() {
     this.webpage = this.b2b2cGoodsTagManager.list(getPage(), getPageSize());
     showGridJson(this.webpage);
     return "json_message";
   }



     public String add()
     {
         return "add";
     }

     public String edit() {
         this.tag = this.b2b2cGoodsTagManager.getById(this.tagId);
         return "edit";
     }

     public String saveAdd()
     {
         try
         {
             this.b2b2cGoodsTagManager.add(this.tag);
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


         this.b2b2cGoodsTagManager.update(this.tag);
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
             this.b2b2cGoodsTagManager.delete(this.tag_id);
             showSuccessJson("标签删除成功");
         } catch (Exception e) {
             showErrorJson("标签删除失败");
             this.logger.error("标签删除失败", e);
         }
         return "json_message";
     }

   public IB2b2cGoodsTagManager getB2b2cGoodsTagManager() { return this.b2b2cGoodsTagManager; }

   public void setB2b2cGoodsTagManager(IB2b2cGoodsTagManager b2b2cGoodsTagManager) {
     this.b2b2cGoodsTagManager = b2b2cGoodsTagManager;
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


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\backend\goods\B2b2cTagAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */