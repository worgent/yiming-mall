 package com.enation.app.b2b2c.core.action.api.goods;

 import com.enation.app.b2b2c.core.model.goods.StoreTag;
 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsTagManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.shop.core.service.ITagManager;
 import com.enation.framework.action.WWAction;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.stereotype.Component;







 @Component
 @ParentPackage("eop_default")
 @Namespace("/api/b2b2c")
 @Action("tag")
 public class StoreGoodsTagsAction
   extends WWAction
 {
   private IStoreGoodsTagManager storeGoodsTagManager;
   private IStoreMemberManager storeMemberManager;
   private ITagManager tagManager;
   private StoreTag tag;
   private Integer tagId;
   private Integer[] tag_id;
   private Integer[] reg_id;
   private Integer[] regId;
   private Integer[] ordernum;

   public String add()
   {
     try
     {
       StoreMember member = this.storeMemberManager.getStoreMember();
       this.tag.setRel_count(Integer.valueOf(0));
       this.tag.setStore_id(member.getStore_id());
       this.storeGoodsTagManager.add(this.tag);
       showSuccessJson("添加成功");
     } catch (Exception e) {
       this.logger.error("商品标签添加失败" + e);
       showErrorJson("添加失败");
     }
     return "json_message";
   }




   public String edit()
   {
     try
     {
       this.tagManager.update(this.tag);
       showSuccessJson("修改成功");
     } catch (Exception e) {
       this.logger.error("商品标签修改失败:" + e);
       showErrorJson("修改失败");
     }
     return "json_message";
   }




   public String delete()
   {
     try
     {
       this.tagManager.delete(this.tag_id);
       showSuccessJson("删除成功");
     } catch (Exception e) {
       this.logger.error("标签删除失败:" + e);
       showErrorJson("删除失败");
     }
     return "json_message";
   }





   public String deleteRel()
   {
     try
     {
       this.storeGoodsTagManager.deleteRel(this.tagId, this.reg_id);
       showSuccessJson("删除成功");
     } catch (Exception e) {
       showErrorJson("删除失败");
       this.logger.error("标签引用删除失败:" + e);
     }
     return "json_message";
   }





   public String saveRel()
   {
     try
     {
       this.storeGoodsTagManager.addRels(this.tagId, this.reg_id);
       showSuccessJson("保存成功");
     } catch (Exception e) {
       showErrorJson("保存失败");
       this.logger.error("标签引用保存失败:" + e);
     }
     return "json_message";
   }






   public String saveSort()
   {
     try
     {
       this.storeGoodsTagManager.saveSort(this.tagId, this.regId, this.ordernum);
       showSuccessJson("保存成功");
     } catch (Exception e) {
       showErrorJson("保存失败");
       this.logger.error("商品标签保存失败:" + e);
     }
     return "json_message";
   }

   public IStoreGoodsTagManager getStoreGoodsTagManager() { return this.storeGoodsTagManager; }

   public void setStoreGoodsTagManager(IStoreGoodsTagManager storeGoodsTagManager) {
     this.storeGoodsTagManager = storeGoodsTagManager;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }

   public StoreTag getTag() { return this.tag; }

   public void setTag(StoreTag tag) {
     this.tag = tag;
   }

   public Integer getTagId() { return this.tagId; }

   public void setTagId(Integer tagId) {
     this.tagId = tagId;
   }

   public Integer[] getTag_id() { return this.tag_id; }

   public void setTag_id(Integer[] tag_id) {
     this.tag_id = tag_id;
   }

   public Integer[] getReg_id() { return this.reg_id; }

   public void setReg_id(Integer[] reg_id) {
     this.reg_id = reg_id;
   }

   public ITagManager getTagManager() { return this.tagManager; }

   public void setTagManager(ITagManager tagManager) {
     this.tagManager = tagManager;
   }

   public Integer[] getOrdernum() { return this.ordernum; }

   public void setOrdernum(Integer[] ordernum) {
     this.ordernum = ordernum;
   }

   public Integer[] getRegId() { return this.regId; }

   public void setRegId(Integer[] regId) {
     this.regId = regId;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\goods\StoreGoodsTagsAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */