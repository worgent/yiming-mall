 package com.enation.app.shop.core.model.support;

 import com.enation.app.shop.core.model.Comments;
 import java.util.List;








 public class CommentDTO
 {
   private Comments comments;
   private List<Comments> list;

   public Comments getComments()
   {
     return this.comments;
   }

   public void setComments(Comments comments) {
     this.comments = comments;
   }

   public List<Comments> getList() {
     return this.list;
   }

   public void setList(List<Comments> list) {
     this.list = list;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\support\CommentDTO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */