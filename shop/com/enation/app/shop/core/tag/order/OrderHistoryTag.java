 package com.enation.app.shop.core.tag.order;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.plugin.goods.GoodsDataFilterBundle;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;






 @Component
 @Scope("prototype")
 public class OrderHistoryTag
   extends BaseFreeMarkerTag
 {
   private IDaoSupport daoSupport;
   private GoodsDataFilterBundle goodsDataFilterBundle;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer num = (Integer)params.get("num");
     if (num == null)
       num = Integer.valueOf(10);
     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
     if (member == null) {
       throw new TemplateModelException("未登陆不能使用此标签[OrderHistoryTag]");
     }

     String sql = "select * from es_goods g where goods_id in (select goods_id from es_order_items i,es_order o where i.order_id=o.order_id and o.member_id=?)";


     Page webPage = this.daoSupport.queryForPage(sql, 1, num.intValue(), new Object[] { member.getMember_id() });

     this.goodsDataFilterBundle.filterGoodsData((List)webPage.getResult());
     return webPage.getResult();
   }

   public IDaoSupport getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }

   public GoodsDataFilterBundle getGoodsDataFilterBundle() {
     return this.goodsDataFilterBundle;
   }

   public void setGoodsDataFilterBundle(GoodsDataFilterBundle goodsDataFilterBundle) {
     this.goodsDataFilterBundle = goodsDataFilterBundle;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\order\OrderHistoryTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */