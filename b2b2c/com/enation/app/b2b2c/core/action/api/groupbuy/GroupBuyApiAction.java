 package com.enation.app.b2b2c.core.action.api.groupbuy;

 import com.enation.app.b2b2c.core.model.groupbuy.GroupBuy;
 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.groupbuy.IGroupBuyManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.action.WWAction;
 import java.io.File;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;











 @ParentPackage("eop_default")
 @Namespace("/api/b2b2c")
 @Action("groupBuy")
 public class GroupBuyApiAction
   extends WWAction
 {
   private IGroupBuyManager groupBuyManager;
   private IStoreMemberManager storeMemberManager;
   private int gb_id;
   private int act_id;
   private int area_id;
   private String gb_name;
   private String gb_title;
   private String goods_name;
   private int goods_id;
   private double price;
   private String img_url;
   private int goods_num;
   private int cat_id;
   private int visual_num;
   private int limit_num;
   private String remark;
   private File image;
   private String imageFileName;

   public String add()
   {
     try
     {
       GroupBuy groupBuy = putData();
       int result = this.groupBuyManager.add(groupBuy);
       showSuccessJson("团购添加成功", Integer.valueOf(result));
     } catch (Exception e) {
       showErrorJson("团购添加失败" + e.getMessage());
       this.logger.error("团购添加失败", e);
     }


     return "json_message";
   }






   public String update()
   {
     StoreMember storeMember = this.storeMemberManager.getStoreMember();
     if (storeMember == null) {
       throw new RuntimeException("尚未登陆，不能使用此API");
     }
     try
     {
       GroupBuy groupBuy = putData();
       groupBuy.setGb_id(this.gb_id);
       this.groupBuyManager.update(groupBuy);
       showSuccessJson("团购更新成功");
     } catch (Exception e) {
       showErrorJson("团购更新失败" + e.getMessage());
       this.logger.error("团购更新失败", e);
     }


     return "json_message";
   }





   public String delete()
   {
     try
     {
       this.groupBuyManager.delete(this.gb_id);
       showSuccessJson("删除成功");
     }
     catch (Exception e) {
       showErrorJson("删除失败" + e.getMessage());
       this.logger.error("删除失败", e);
     }

     return "json_message";
   }

   private GroupBuy putData()
   {
     StoreMember storeMember = this.storeMemberManager.getStoreMember();
     if (storeMember == null) {
       throw new RuntimeException("尚未登陆，不能使用此API");
     }

     GroupBuy groupBuy = new GroupBuy();
     if (this.image != null)
     {

       String allowTYpe = "gif,jpg,bmp,png";
       if ((!this.imageFileName.trim().equals("")) && (this.imageFileName.length() > 0)) {
         String ex = this.imageFileName.substring(this.imageFileName.lastIndexOf(".") + 1, this.imageFileName.length());
         if (allowTYpe.toString().indexOf(ex.toLowerCase()) < 0) {
           throw new RuntimeException("对不起,只能上传gif,jpg,bmp,png格式的图片！");
         }
       }



       if (this.image.length() > 2048000L) {
         throw new RuntimeException("图片不能大于2MB！");
       }


       String imgPath = UploadUtil.upload(this.image, this.imageFileName, "groupbuy");
       groupBuy.setImg_url(imgPath);
     }

     groupBuy.setStore_id(storeMember.getStore_id().intValue());
     groupBuy.setAct_id(this.act_id);
     groupBuy.setArea_id(this.area_id);
     groupBuy.setCat_id(this.cat_id);
     groupBuy.setGb_name(this.gb_name);
     groupBuy.setGb_title(this.gb_title);
     groupBuy.setGoods_id(this.goods_id);
     groupBuy.setGoods_name(this.goods_name);
     groupBuy.setGoods_num(this.goods_num);
     groupBuy.setLimit_num(this.limit_num);
     groupBuy.setPrice(this.price);
     groupBuy.setRemark(this.remark);
     groupBuy.setVisual_num(this.visual_num);
     return groupBuy;
   }

   public IGroupBuyManager getGroupBuyManager() {
     return this.groupBuyManager;
   }

   public void setGroupBuyManager(IGroupBuyManager groupBuyManager) {
     this.groupBuyManager = groupBuyManager;
   }

   public int getGb_id() {
     return this.gb_id;
   }

   public void setGb_id(int gb_id) {
     this.gb_id = gb_id;
   }

   public int getAct_id() {
     return this.act_id;
   }

   public void setAct_id(int act_id) {
     this.act_id = act_id;
   }

   public int getArea_id() {
     return this.area_id;
   }

   public void setArea_id(int area_id) {
     this.area_id = area_id;
   }

   public String getGb_name() {
     return this.gb_name;
   }

   public void setGb_name(String gb_name) {
     this.gb_name = gb_name;
   }

   public String getGb_title() {
     return this.gb_title;
   }

   public void setGb_title(String gb_title) {
     this.gb_title = gb_title;
   }

   public String getGoods_name() {
     return this.goods_name;
   }

   public void setGoods_name(String goods_name) {
     this.goods_name = goods_name;
   }

   public int getGoods_id() {
     return this.goods_id;
   }

   public void setGoods_id(int goods_id) {
     this.goods_id = goods_id;
   }

   public double getPrice() {
     return this.price;
   }

   public void setPrice(double price) {
     this.price = price;
   }

   public String getImg_url() {
     return this.img_url;
   }

   public void setImg_url(String img_url) {
     this.img_url = img_url;
   }

   public int getGoods_num() {
     return this.goods_num;
   }

   public void setGoods_num(int goods_num) {
     this.goods_num = goods_num;
   }

   public int getCat_id() {
     return this.cat_id;
   }

   public void setCat_id(int cat_id) {
     this.cat_id = cat_id;
   }

   public int getVisual_num() {
     return this.visual_num;
   }

   public void setVisual_num(int visual_num) {
     this.visual_num = visual_num;
   }

   public int getLimit_num() {
     return this.limit_num;
   }

   public void setLimit_num(int limit_num) {
     this.limit_num = limit_num;
   }

   public String getRemark() {
     return this.remark;
   }

   public void setRemark(String remark) {
     this.remark = remark;
   }

   public File getImage()
   {
     return this.image;
   }

   public void setImage(File image) {
     this.image = image;
   }

   public String getImageFileName() {
     return this.imageFileName;
   }

   public void setImageFileName(String imageFileName) {
     this.imageFileName = imageFileName;
   }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\groupbuy\GroupBuyApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */