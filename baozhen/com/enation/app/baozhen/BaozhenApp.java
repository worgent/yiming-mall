 package com.enation.app.baozhen;

 import com.enation.app.base.core.service.solution.impl.SqlExportService;
 import com.enation.app.shop.core.service.ICartManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.App;
 import com.enation.framework.cache.CacheFactory;
 import com.enation.framework.cache.ICache;
 import com.enation.framework.database.IDBRouter;
 import com.enation.framework.database.ISqlFileExecutor;
 import java.util.List;
 import org.apache.log4j.Logger;







 public class BaozhenApp
   extends App
 {
   protected final Logger logger = Logger.getLogger(getClass());

   private IDBRouter baseDBRouter;
   private ISqlFileExecutor sqlFileExecutor;
   private SqlExportService sqlExportService;
   private ICartManager cartManager;

   public BaozhenApp()
   {
     this.tables.add("goods");
     this.tables.add("product");


     this.tables.add("goods_cat");


     this.tables.add("brand");


     this.tables.add("goods_type");
     this.tables.add("type_brand");


     this.tables.add("freeoffer");
     this.tables.add("freeoffer_category");


     this.tables.add("tags");
     this.tables.add("tag_rel");


     this.tables.add("member");
     this.tables.add("member_lv");
     this.tables.add("goods_lv_price");


     this.tables.add("agent");
     this.tables.add("agent_transfer");


     this.tables.add("dly_type");
     this.tables.add("dly_area");
     this.tables.add("dly_type_area");


     this.tables.add("logi_company");


     this.tables.add("comments");


     this.tables.add("cart");
     this.tables.add("order");
     this.tables.add("order_items");
     this.tables.add("order_log");

     this.tables.add("delivery");
     this.tables.add("delivery_item");
     this.tables.add("payment_cfg");
     this.tables.add("payment_detail");
     this.tables.add("payment_logs");
     this.tables.add("refund_logs");
     this.tables.add("member_address");
     this.tables.add("message");
     this.tables.add("order_gift");

     this.tables.add("gnotify");
     this.tables.add("point_history");
     this.tables.add("coupons");
     this.tables.add("promotion");
     this.tables.add("member_coupon");
     this.tables.add("pmt_member_lv");
     this.tables.add("pmt_goods");
     this.tables.add("favorite");
     this.tables.add("advance_logs");
     this.tables.add("promotion_activity");
     this.tables.add("goods_complex");

//     this.tables.add("goods_articles");
//     this.tables.add("goods_field");
     this.tables.add("group_buy_count");
     this.tables.add("limitbuy");
     this.tables.add("limitbuy_goods");
     this.tables.add("article");
     this.tables.add("article_cat");
     this.tables.add("package_product");
     this.tables.add("dly_center");
     this.tables.add("print_tmpl");
     this.tables.add("order_pmt");
     this.tables.add("group_buy");

     this.tables.add("member_comment");
     this.tables.add("warn_num");
     this.tables.add("freeze_point");
     this.tables.add("member_lv_discount");

     this.tables.add("order_meta");
     this.tables.add("coupons");
     this.tables.add("member_coupon");
     this.tables.add("member_order_item");
     this.tables.add("store_log");
     this.tables.add("depot_user");
     this.tables.add("product_store");
     this.tables.add("depot");
     this.tables.add("goods_depot");
     this.tables.add("allocation_item");
     this.tables.add("returns_order");
   }

   public String getId()
   {
     return "shop";
   }

   public String getName() {
     return "shop应用";
   }

   public String getNameSpace() {
     return "/shop";
   }



   public void install()
   {
//     doInstall("file:com/enation/app/shop/shop.xml");
   }



   protected void cleanCache()
   {
     super.cleanCache();


     CacheFactory.getCache("goods_cat").remove("goods_cat_" + this.userid + "_" + this.siteid + "_0");


     CacheFactory.getCache("article_cat").remove("article_cat_" + this.userid + "_" + this.siteid);
   }



   public void saasInstall()
   {
//     this.baseDBRouter.doSaasInstall("file:com/enation/app/shop/shop.xml");
   }







   public void sessionDestroyed(String sessionid, EopSite site)
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("clean cart...");
     }


     if (site != null) {
       if (this.logger.isDebugEnabled()) {
         this.logger.debug("site get from session is userid[" + site.getUserid() + "]-siteid[" + site.getId() + "]");
       }
       this.cartManager.clean(sessionid, site.getUserid(), site.getId());
     }
     else if (this.logger.isDebugEnabled()) {
       this.logger.debug("site get from session is null");
     }
   }

   public IDBRouter getBaseDBRouter()
   {
     return this.baseDBRouter;
   }

   public void setBaseDBRouter(IDBRouter baseDBRouter) {
     this.baseDBRouter = baseDBRouter;
   }

   public ISqlFileExecutor getSqlFileExecutor() {
     return this.sqlFileExecutor;
   }

   public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
     this.sqlFileExecutor = sqlFileExecutor;
   }

   public SqlExportService getSqlExportService() {
     return this.sqlExportService;
   }

   public void setSqlExportService(SqlExportService sqlExportService) {
     this.sqlExportService = sqlExportService;
   }

   public ICartManager getCartManager() {
     return this.cartManager;
   }

   public void setCartManager(ICartManager cartManager) {
     this.cartManager = cartManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\ShopApp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */