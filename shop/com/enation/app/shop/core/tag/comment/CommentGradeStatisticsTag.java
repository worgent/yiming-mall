 package com.enation.app.shop.core.tag.comment;

 import com.enation.app.shop.core.service.IMemberCommentManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;















 @Component
 @Scope("prototype")
 public class CommentGradeStatisticsTag
   extends BaseFreeMarkerTag
 {
   private IMemberCommentManager memberCommentManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer goodsid = (Integer)params.get("goods_id");

     if (goodsid == null) {
       throw new TemplateModelException("必须传递goods_id参数");
     }
     Map map = this.memberCommentManager.statistics(goodsid.intValue());
     return this.memberCommentManager.statistics(goodsid.intValue());
   }



   public IMemberCommentManager getMemberCommentManager()
   {
     return this.memberCommentManager;
   }

   public void setMemberCommentManager(IMemberCommentManager memberCommentManager) {
     this.memberCommentManager = memberCommentManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\comment\CommentGradeStatisticsTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */