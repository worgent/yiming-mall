 package com.enation.app.shop.core.action.backend;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.model.MemberComment;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IMemberCommentManager;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.app.shop.core.service.IMemberOrderItemManager;
 import com.enation.app.shop.core.service.IMemberPointManger;
 import com.enation.eop.processor.httpcache.HttpCacheManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.Map;
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
 @Action("comments")
 @Results({@org.apache.struts2.convention.annotation.Result(name="bglist", type="freemarker", location="/shop/admin/comments/list.html"), @org.apache.struts2.convention.annotation.Result(name="gmlist", type="freemarker", location="/shop/admin/comments/gm_list.html"), @org.apache.struts2.convention.annotation.Result(name="detail", type="freemarker", location="/shop/admin/comments/detail.html")})
 public class CommentsAction
   extends WWAction
 {
   private IMemberCommentManager memberCommentManager;
   private IMemberPointManger memberPointManger;
   private IMemberOrderItemManager memberOrderItemManager;
   private IMemberManager memberManager;
   private IGoodsManager goodsManager;
   private int type;
   private int status;
   private int commentId;
   private MemberComment memberComment;
   private Member member;
   private String reply;
   private Integer[] comment_id;

   public String list()
   {
     if (this.type == 2) {
       return "gmlist";
     }
     return "bglist";
   }






   public String listJson()
   {
     setPageSize(10);
     this.webpage = this.memberCommentManager.getAllComments(getPage(), this.pageSize, this.type);
     showGridJson(this.webpage);
     return "json_message";
   }







   public String msgList()
   {
     this.webpage = this.memberCommentManager.getCommentsByStatus(getPage(), getPageSize(), this.type, this.status);
     return "bglist";
   }





   public String detail()
   {
     IUserService userService = UserServiceFactory.getUserService();
     int managerid = userService.getCurrentManagerId().intValue();
     this.memberComment = this.memberCommentManager.get(this.commentId);
     if ((this.memberComment.getMember_id() != null) && (this.memberComment.getMember_id().intValue() != 0)) {
       this.member = this.memberManager.get(this.memberComment.getMember_id());
     }
     if ((this.memberComment != null) && (!StringUtil.isEmpty(this.memberComment.getImg()))) {
       this.memberComment.setImgPath(UploadUtil.replacePath(this.memberComment.getImg()));
     }
     return "detail";
   }







   public String add()
   {
     if (StringUtil.isEmpty(this.reply)) {
       showErrorJson("回复不能为空！");
       return "json_message";
     }
     MemberComment dbMemberComment = this.memberCommentManager.get(this.commentId);
     if (dbMemberComment == null) {
       showErrorJson("此评论或咨询不存在！");
       return "json_message";
     }
     dbMemberComment.setReply(this.reply);
     dbMemberComment.setReplystatus(1);
     dbMemberComment.setReplytime(DateUtil.getDatelineLong());
     this.memberCommentManager.update(dbMemberComment);
     showSuccessJson("回复成功");
     return "json_message";
   }





   public String delete()
   {
     try
     {
       this.memberCommentManager.delete(this.comment_id);
       showSuccessJson("操作成功");
     } catch (Exception e) {
       e.printStackTrace();
       showErrorJson("操作失败");
     }
     return "json_message";
   }




   public String hide()
   {
     try
     {
       this.memberComment = this.memberCommentManager.get(this.commentId);
       this.memberComment.setStatus(2);
       this.memberCommentManager.update(this.memberComment);
       showSuccessJson("操作成功");
     } catch (RuntimeException e) {
       e.printStackTrace();
       showErrorJson("操作失败");
     }
     return "json_message";
   }








   public String show()
   {
     try
     {
       this.memberComment = this.memberCommentManager.get(this.commentId);
       boolean isFirst = false;


       if ((this.memberCommentManager.getGoodsCommentsCount(this.memberComment.getGoods_id()) == 0) && (this.memberComment.getType() != 2))
       {
         isFirst = true;
       }

       this.memberComment.setStatus(1);
       this.memberCommentManager.update(this.memberComment);


       Map goods = this.goodsManager.get(Integer.valueOf(this.memberComment.getGoods_id()));
       if (goods != null) {
         String url = "";
         if (goods.get("cat_id") != null) {
           switch (StringUtil.toInt(goods.get("cat_id").toString())) {
           case 1:
           case 2:
             url = "yxgoods";
             break;
           case 3:
           case 4:
           case 12:
           case 18:
             url = "jkgoods";
             break;
           case 6:
             url = "jpgoods";
             break;
           case 5:
           case 7:
           case 8:
           case 9:
             url = "goods";
             break;
           case 19:
             url = "gngoods";
           }

         }
         HttpCacheManager.updateUrlModified("/" + url + "-" + this.memberComment.getGoods_id() + ".html");
       }






       String reson = "文字评论";
       String type = "comment";
       if (!StringUtil.isEmpty(this.memberComment.getImg())) {
         type = "comment_img";
         reson = "上传图片评论";
       }

       if ((this.memberPointManger.checkIsOpen(type)) &&
         (this.memberComment.getMember_id() != null) && (this.memberComment.getMember_id().intValue() != 0) && (this.memberComment.getType() != 2)) {
         int point = this.memberPointManger.getItemPoint(type + "_num");
         int mp = this.memberPointManger.getItemPoint(type + "_num_mp");
         this.memberPointManger.add(this.memberComment.getMember_id().intValue(), point, reson, null, mp);
       }




       if ((isFirst) &&
         (this.memberPointManger.checkIsOpen("first_comment")))
       {
         int point = this.memberPointManger.getItemPoint("first_comment_num");


         int mp = this.memberPointManger.getItemPoint("first_comment_num_mp");


         this.memberPointManger.add(this.memberComment.getMember_id().intValue(), point, "首次评论", null, mp);
       }



       showSuccessJson("操作成功");
     } catch (RuntimeException e) {
       e.printStackTrace();
       showErrorJson("操作失败");
     }
     return "json_message";
   }






   public String deletealone()
   {
     this.memberComment = this.memberCommentManager.get(this.commentId);
     if (this.memberComment != null) {
       this.memberCommentManager.deletealone(this.commentId);
     }
     showSuccessJson("删除成功");
     return "json_message";
   }

   public IMemberPointManger getMemberPointManger() {
     return this.memberPointManger;
   }

   public void setMemberPointManger(IMemberPointManger memberPointManger) {
     this.memberPointManger = memberPointManger;
   }

   public void setMemberOrderItemManager(IMemberOrderItemManager memberOrderItemManager)
   {
     this.memberOrderItemManager = memberOrderItemManager;
   }

   public void setMemberCommentManager(IMemberCommentManager memberCommentManager)
   {
     this.memberCommentManager = memberCommentManager;
   }

   public int getType() {
     return this.type;
   }

   public void setType(int type) {
     this.type = type;
   }

   public int getStatus() { return this.status; }

   public void setStatus(int status)
   {
     this.status = status;
   }

   public MemberComment getMemberComment()
   {
     return this.memberComment;
   }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }

   public Member getMember() {
     return this.member;
   }

   public void setMember(Member member) {
     this.member = member;
   }

   public String getReply() {
     return this.reply;
   }

   public void setReply(String reply) {
     this.reply = reply;
   }


   public int getCommentId()
   {
     return this.commentId;
   }

   public void setCommentId(int commentId) {
     this.commentId = commentId;
   }

   public Integer[] getComment_id() {
     return this.comment_id;
   }

   public void setComment_id(Integer[] comment_id) {
     this.comment_id = comment_id;
   }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\CommentsAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */