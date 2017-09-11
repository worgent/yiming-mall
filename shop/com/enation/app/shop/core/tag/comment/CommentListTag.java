 package com.enation.app.shop.core.tag.comment;

 import com.enation.app.shop.core.service.IMemberCommentManager;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;




























 @Component
 @Scope("prototype")
 public class CommentListTag
   extends BaseFreeMarkerTag
 {
   private IMemberCommentManager memberCommentManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer goods_id = (Integer)params.get("goods_id");
     Integer type = (Integer)params.get("type");

     if (type == null) {
       throw new TemplateModelException("必须输入 type参数 ");
     }

     int pageNo = getPage();
     int pageSize = getPageSize();

     Page page = this.memberCommentManager.getGoodsComments(goods_id.intValue(), pageNo, pageSize, type.intValue());
     return page;
   }

   public IMemberCommentManager getMemberCommentManager() { return this.memberCommentManager; }

   public void setMemberCommentManager(IMemberCommentManager memberCommentManager) {
     this.memberCommentManager = memberCommentManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\comment\CommentListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */