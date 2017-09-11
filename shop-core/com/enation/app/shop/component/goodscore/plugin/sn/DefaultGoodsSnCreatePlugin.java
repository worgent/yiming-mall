 package com.enation.app.shop.component.goodscore.plugin.sn;

 import com.enation.app.shop.core.plugin.goods.AbstractGoodsSnCreator;
 import com.enation.app.shop.core.service.SnDuplicateException;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.Date;
 import java.util.Map;
 import org.springframework.stereotype.Component;















 @Component
 public class DefaultGoodsSnCreatePlugin
   extends AbstractGoodsSnCreator
 {
   private IDaoSupport baseDaoSupport;

   public IDaoSupport getBaseDaoSupport()
   {
     return this.baseDaoSupport;
   }

   public void setBaseDaoSupport(IDaoSupport baseDaoSupport)
   {
     this.baseDaoSupport = baseDaoSupport;
   }






   public void register() {}





   public String createSn(Map goods)
   {
     if ((goods.get("sn") != null) && (!goods.get("sn").equals("")))
     {
       if (goods.get("goods_id") == null)
       {
         if (checkSn(goods.get("sn").toString()) == 1) {
           throw new SnDuplicateException(goods.get("sn").toString());
         }

       }
       else {
         try
         {
           Integer goods_id = Integer.valueOf("" + goods.get("goods_id"));

           if (checkSn(goods.get("sn").toString(), goods_id.intValue()) == 1) {
             throw new SnDuplicateException(goods.get("sn").toString());
           }
         }
         catch (NumberFormatException e)
         {
           throw new RuntimeException("商品id格式错误");
         }
       }


       return goods.get("sn").toString();
     }



     String sn = "G" + DateUtil.toString(new Date(System.currentTimeMillis()), "yyyyMMddhhmmss") + StringUtil.getRandStr(4);
     return sn;
   }







   private int checkSn(String sn)
   {
     String sql = "select count(0) num from goods where sn='" + sn + "'";
     int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
     count = count > 0 ? 1 : 0;
     return count;
   }







   private int checkSn(String sn, int goods_id)
   {
     String sql = "select count(0) num from goods where sn='" + sn + "' and goods_id!=" + goods_id;

     int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
     count = count > 0 ? 1 : 0;
     return count;
   }

   public String getAuthor() {
     return "kingapex";
   }

   public String getId() {
     return "goods.sn_creator";
   }

   public String getName()
   {
     return "默认商品货号生成插件";
   }

   public String getType() {
     return "";
   }

   public String getVersion() {
     return "1.0";
   }

   public void perform(Object... params) {}
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\goodscore\plugin\sn\DefaultGoodsSnCreatePlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */