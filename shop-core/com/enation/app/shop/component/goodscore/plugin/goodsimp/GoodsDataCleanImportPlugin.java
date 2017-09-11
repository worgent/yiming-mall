 package com.enation.app.shop.component.goodscore.plugin.goodsimp;

 import com.enation.app.shop.core.plugin.goodsimp.IBeforeGoodsImportEvent;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.FileUtil;
 import com.enation.framework.util.StringUtil;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;
 import org.w3c.dom.Document;












 @Component
 public class GoodsDataCleanImportPlugin
   extends AutoRegisterPlugin
   implements IBeforeGoodsImportEvent
 {
   private IDaoSupport baseDaoSupport;

   public void register() {}

   public void onBeforeImport(Document configDoc)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();

     String cleanall = request.getParameter("cleanall");
     String cleancat = request.getParameter("cleancat");
     String imptype = request.getParameter("imptype");

     if (!StringUtil.isEmpty(cleanall))
     {

       String imgfolder = EopSetting.IMG_SERVER_PATH + "/attachment/goods";
       FileUtil.delete(imgfolder);

       String ckeditor = EopSetting.IMG_SERVER_PATH + "/attachment/ckeditor";
       FileUtil.delete(imgfolder);

       this.baseDaoSupport.execute("truncate table goods", new Object[0]);
       this.baseDaoSupport.execute("truncate table product", new Object[0]);
       this.baseDaoSupport.execute("truncate table goods_spec", new Object[0]);
       this.baseDaoSupport.execute("truncate table cart", new Object[0]);
       this.baseDaoSupport.execute("truncate table order", new Object[0]);
       this.baseDaoSupport.execute("truncate table order_items", new Object[0]);
     }

     if (("2".equals(imptype)) && (!StringUtil.isEmpty(cleancat))) {
       int catid = Integer.valueOf(request.getParameter("catid")).intValue();
       this.baseDaoSupport.execute("delete from product where goods_id in (select goods_id from goods where cat_id=?)", new Object[] { Integer.valueOf(catid) });
       this.baseDaoSupport.execute("delete from goods where cat_id=?", new Object[] { Integer.valueOf(catid) });
     }
   }




   public String getAuthor()
   {
     return "kingapex";
   }


   public String getId()
   {
     return "goodsImageDeletePlugin";
   }


   public String getName()
   {
     return "商品导入前删除商品图片插件 ";
   }


   public String getType()
   {
     return "goodsimp";
   }


   public String getVersion()
   {
     return "1.0";
   }



   public void perform(Object... params) {}



   public IDaoSupport getBaseDaoSupport()
   {
     return this.baseDaoSupport;
   }

   public void setBaseDaoSupport(IDaoSupport baseDaoSupport)
   {
     this.baseDaoSupport = baseDaoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\goodscore\plugin\goodsimp\GoodsDataCleanImportPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */