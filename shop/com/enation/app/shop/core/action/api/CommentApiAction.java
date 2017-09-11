 package com.enation.app.shop.core.action.api;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.model.MemberComment;
 import com.enation.app.shop.core.model.MemberOrderItem;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IMemberCommentManager;
 import com.enation.app.shop.core.service.IMemberOrderItemManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
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

















 @ParentPackage("eop_default")
 @Namespace("/api/shop")
 @Action("commentApi")
 public class CommentApiAction
   extends WWAction
 {
   private File file;
   private String fileFileName;
   private IGoodsManager goodsManager;
   private IMemberCommentManager memberCommentManager;
   private IMemberOrderItemManager memberOrderItemManager;
   private String content;
   private int grade;
   private int comment_id;
   private int commenttype;
   private int goods_id;

   public String add()
   {
     MemberComment memberComment = new MemberComment();

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


     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     int type = this.commenttype;

     if ((type != 1) && (type != 2)) {
       showErrorJson("系统参数错误！");
       return "json_message";
     }
     memberComment.setType(type);


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

     Member member = UserServiceFactory.getUserService().getCurrentMember();
     if (type == 1) {
       if (member == null) {
         showErrorJson("只有登录且成功购买过此商品的用户才能发表评论！");
         return "json_message";
       }
       int buyCount = this.memberOrderItemManager.count(member.getMember_id().intValue(), this.goods_id);
       int commentCount = this.memberOrderItemManager.count(member.getMember_id().intValue(), this.goods_id, 1);
       if ((buyCount > 0) || (








         (this.grade < 0) || (this.grade > 5))) {
         memberComment.setGrade(5);
       } else {
         memberComment.setGrade(this.grade);
       }
     } else {
       memberComment.setImg(null);
       memberComment.setGrade(0);
     }
     memberComment.setMember_id(Integer.valueOf(member == null ? 0 : member.getMember_id().intValue()));
     memberComment.setDateline(System.currentTimeMillis() / 1000L);
     memberComment.setIp(request.getRemoteHost());
     try
     {
       this.memberCommentManager.add(memberComment);

       if (type == 1) {
         MemberOrderItem memberOrderItem = this.memberOrderItemManager.get(member.getMember_id().intValue(), this.goods_id, 0);
         if (memberOrderItem != null) {
           memberOrderItem.setComment_time(Long.valueOf(System.currentTimeMillis()));
           memberOrderItem.setCommented(Integer.valueOf(1));
           this.memberOrderItemManager.update(memberOrderItem);
         }
       }

       showSuccessJson("发表成功");
     }
     catch (RuntimeException e) {
       this.logger.error("发表评论出错", e);
       showErrorJson("发表评论出错" + e.getMessage());
     }

     return "json_message";
   }

    public String delComment()
   {
     MemberComment memberComment = this.memberCommentManager.get(this.comment_id);
     if (memberComment == null) {
       showErrorJson("此评论或咨询不存在！");
       return "json_message";
     }
     try {

       this.memberCommentManager.deletealone(this.comment_id);
       showSuccessJson("删除成功");
     } catch (Exception e) {
       showErrorJson("删除失败");
       this.logger.error("删除失败:" + e);
     }
     return "json_message";
   }


   public String update()
   {
     try
     {
       MemberComment memberComment = new MemberComment();
       memberComment = this.memberCommentManager.get(this.comment_id);
       memberComment.setComment_id(this.comment_id);
       memberComment.setGrade(this.grade);
       memberComment.setContent(this.content);
       this.memberCommentManager.update(memberComment);
       showSuccessJson("更新成功");
     } catch (RuntimeException e) {
       this.logger.error("修改评论出错", e);
       showErrorJson("更新失败");
     }
     return "json_message";
   }

   public static void main(String[] args) { File file = new File("/Users/kingapex/Downloads/shopping_flow.js"); }


   public File getFile()
   {
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

   public IGoodsManager getGoodsManager() {
     return this.goodsManager;
   }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }

   public IMemberCommentManager getMemberCommentManager() {
     return this.memberCommentManager;
   }

   public void setMemberCommentManager(IMemberCommentManager memberCommentManager) {
     this.memberCommentManager = memberCommentManager;
   }

   public IMemberOrderItemManager getMemberOrderItemManager() {
     return this.memberOrderItemManager;
   }

   public void setMemberOrderItemManager(IMemberOrderItemManager memberOrderItemManager)
   {
     this.memberOrderItemManager = memberOrderItemManager;
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

   public int getComment_id() {
     return this.comment_id;
   }

   public void setComment_id(int comment_id) {
     this.comment_id = comment_id;
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
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\api\CommentApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */