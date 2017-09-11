 package com.enation.app.b2b2c.core.action.api.member;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.model.member.StoreMemberComment;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberCommentManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.shop.core.model.MemberOrderItem;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IMemberOrderItemManager;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.StringUtil;
 import java.io.File;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.stereotype.Component;

















 @Component
 @ParentPackage("eop_default")
 @Namespace("/api/b2b2c")
 @Action("storeCommentApi")
 public class StoreCommentApiAction
   extends WWAction
 {
   private IGoodsManager goodsManager;
   private IMemberOrderItemManager memberOrderItemManager;
   private IStoreMemberCommentManager storeMemberCommentManager;
   private IStoreMemberManager storeMemberManager;
   private File file;
   private String fileFileName;
   private int commenttype;
   private int goods_id;
   private String content;
   private int grade;
   private int store_desccredit;
   private int store_servicecredit;
   private int store_deliverycredit;
   private Integer status;
   private String reply;
   private Integer comment_id;
   private Integer store_id;

   public String add()
   {
     try
     {
       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       StoreMemberComment memberComment = new StoreMemberComment();

       String subFolder = "comment";
       if (this.file != null)
       {

         String allowTYpe = "gif,jpg,bmp,png";
         if ((!this.fileFileName.trim().equals("")) && (this.fileFileName.length() > 0)) {
           String ex = this.fileFileName.substring(this.fileFileName.lastIndexOf(".") + 1, this.fileFileName.length());
           if (allowTYpe.toString().indexOf(ex.toLowerCase()) < 0) {
             showErrorJson("对不起,只能上传gif,jpg,bmp,png格式的图片！");
             return "json_message";
           }
         }



         if (this.file.length() > 204800L) {
           showErrorJson("'对不起,图片不能大于200K！");
           return "json_message";
         }

         String imgPath = UploadUtil.upload(this.file, this.fileFileName, subFolder);
         memberComment.setImg(imgPath);
       }


       if ((this.commenttype != 1) && (this.commenttype != 2)) {
         showErrorJson("系统参数错误！");
         return "json_message";
       }
       memberComment.setType(this.commenttype);


       if (this.goodsManager.get(Integer.valueOf(this.goods_id)) == null) {
         showErrorJson("此商品不存在！");
         return "json_message";
       }
       memberComment.setGoods_id(this.goods_id);

       if (StringUtil.isEmpty(this.content)) {
         showErrorJson("评论或咨询内容不能为空！");
         return "json_message"; }
       if (this.content.length() > 1000) {
         showErrorJson("请输入1000以内的内容！");
         return "json_message";
       }
       this.content = StringUtil.htmlDecode(this.content);
       memberComment.setContent(this.content);

       StoreMember member = this.storeMemberManager.getStoreMember();
       if (member == null) {
         showErrorJson("只有登录且成功购买过此商品的用户才能发表评论和咨询！");
         return "json_message";
       }
       memberComment.setStore_id(this.store_id.intValue());
       if (this.commenttype == 1) {
         int buyCount = this.memberOrderItemManager.count(member.getMember_id().intValue(), this.goods_id);
         int commentCount = this.memberOrderItemManager.count(member.getMember_id().intValue(), this.goods_id, 1);
         if ((this.grade < 1) || (this.grade > 3)) {
           showErrorJson("请选择对商品的评价！");
           return "json_message";
         }
         memberComment.setGrade(this.grade);
       }

       memberComment.setMember_id(Integer.valueOf(member == null ? 0 : member.getMember_id().intValue()));
       memberComment.setDateline(System.currentTimeMillis() / 1000L);
       memberComment.setIp(request.getRemoteHost());

       memberComment.setStore_deliverycredit(this.store_deliverycredit);
       memberComment.setStore_desccredit(this.store_desccredit);
       memberComment.setStore_servicecredit(this.store_servicecredit);
       this.storeMemberCommentManager.add(memberComment);

       if (this.commenttype == 1) {
         MemberOrderItem memberOrderItem = this.memberOrderItemManager.get(member.getMember_id().intValue(), this.goods_id, 0);
         if (memberOrderItem != null) {
           memberOrderItem.setComment_time(Long.valueOf(System.currentTimeMillis()));
           memberOrderItem.setCommented(Integer.valueOf(1));
           this.memberOrderItemManager.update(memberOrderItem);
         }
       }
       showSuccessJson("发表成功");
     } catch (Exception e) {
       e.printStackTrace();
       this.logger.error("发表评论出错", e);
       showErrorJson("发表评论出错：" + e.getMessage());
     }

     return "json_message";
   }









   public String edit()
   {
     if (StringUtil.isEmpty(this.reply)) {
       showErrorJson("回复不能为空！");
       return "json_message";
     }
     Map memberComment = this.storeMemberCommentManager.get(this.comment_id);
     if (memberComment == null) {
       showErrorJson("此评论或咨询不存在！");
       return "json_message";
     }
     try {
       StoreMember member = this.storeMemberManager.getStoreMember();

       memberComment.put("reply", this.reply);
       memberComment.put("replystatus", Integer.valueOf(1));
       memberComment.put("replytime", Long.valueOf(DateUtil.getDatelineLong()));
       memberComment.put("status", this.status);
       this.storeMemberCommentManager.edit(memberComment, this.comment_id);
       showSuccessJson("回复成功");
     } catch (Exception e) {
       showErrorJson("回复失败");
       this.logger.error("回复失败:" + e);
     }
     return "json_message";
   }


   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }


   public void setStoreMemberManager(IStoreMemberManager storeMemberManager)
   {
     this.storeMemberManager = storeMemberManager;
   }

   public IGoodsManager getGoodsManager()
   {
     return this.goodsManager;
   }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }

   public IMemberOrderItemManager getMemberOrderItemManager() {
     return this.memberOrderItemManager;
   }

   public void setMemberOrderItemManager(IMemberOrderItemManager memberOrderItemManager)
   {
     this.memberOrderItemManager = memberOrderItemManager;
   }

   public IStoreMemberCommentManager getStoreMemberCommentManager() {
     return this.storeMemberCommentManager;
   }

   public void setStoreMemberCommentManager(IStoreMemberCommentManager storeMemberCommentManager)
   {
     this.storeMemberCommentManager = storeMemberCommentManager;
   }

   public File getFile() {
     return this.file;
   }

   public void setFile(File file) {
     this.file = file;
   }

   public String getFileFileName() {
     return this.fileFileName;
   }

   public void setFileFileName(String fileFileName) {
     this.fileFileName = fileFileName;
   }

   public int getCommenttype() {
     return this.commenttype;
   }

   public void setCommenttype(int commenttype) {
     this.commenttype = commenttype;
   }

   public int getGoods_id() {
     return this.goods_id;
   }

   public void setGoods_id(int goods_id) {
     this.goods_id = goods_id;
   }

   public String getContent() {
     return this.content;
   }

   public void setContent(String content) {
     this.content = content;
   }

   public int getGrade() {
     return this.grade;
   }

   public void setGrade(int grade) {
     this.grade = grade;
   }

   public int getStore_desccredit() {
     return this.store_desccredit;
   }

   public void setStore_desccredit(int store_desccredit) {
     this.store_desccredit = store_desccredit;
   }

   public int getStore_servicecredit() {
     return this.store_servicecredit;
   }

   public void setStore_servicecredit(int store_servicecredit) {
     this.store_servicecredit = store_servicecredit;
   }

   public int getStore_deliverycredit() {
     return this.store_deliverycredit;
   }

   public void setStore_deliverycredit(int store_deliverycredit) {
     this.store_deliverycredit = store_deliverycredit;
   }

   public Integer getStatus() { return this.status; }

   public void setStatus(Integer status) {
     this.status = status;
   }

   public String getReply() { return this.reply; }

   public void setReply(String reply) {
     this.reply = reply;
   }

   public Integer getComment_id() { return this.comment_id; }

   public void setComment_id(Integer comment_id) {
     this.comment_id = comment_id;
   }

   public Integer getStore_id() { return this.store_id; }

   public void setStore_id(Integer store_id) {
     this.store_id = store_id;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\member\StoreCommentApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */