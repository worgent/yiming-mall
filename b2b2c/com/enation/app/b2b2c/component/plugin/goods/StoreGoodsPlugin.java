 package com.enation.app.b2b2c.component.plugin.goods;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.shop.component.gallery.model.GoodsGallery;
 import com.enation.app.shop.component.gallery.service.IGoodsGalleryManager;
 import com.enation.app.shop.core.plugin.goods.IGoodsAfterAddEvent;
 import com.enation.app.shop.core.plugin.goods.IGoodsAfterEditEvent;
 import com.enation.app.shop.core.plugin.goods.IGoodsBeforeAddEvent;
 import com.enation.app.shop.core.plugin.goods.IGoodsBeforeEditEvent;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IProductManager;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;











 @Component
 public class StoreGoodsPlugin
   extends AutoRegisterPlugin
   implements IGoodsAfterAddEvent, IGoodsAfterEditEvent, IGoodsBeforeAddEvent, IGoodsBeforeEditEvent
 {
   private IGoodsGalleryManager goodsGalleryManager;
   private IStoreMemberManager storeMemberManager;
   private IProductManager productManager;
   private IGoodsManager goodsManager;
   private IDaoSupport daoSupport;
   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
   {
     Integer goods_id = Integer.valueOf(goods.get("goods_id").toString());
     String[] imgFs = request.getParameterValues("goods_fs");
     String[] status = request.getParameterValues("status");
     String[] id = request.getParameterValues("img_id");
     if ((status != null) && (imgFs != null)) {
       for (int i = 0; i < status.length; i++) {
         if (status[i].equals("3")) {
           GoodsGallery gallery = new GoodsGallery();
           if (i == 0) {
             gallery.setIsdefault(1);
             editGoodsOriginal(imgFs[i], goods_id);
           }
           gallery.setBig(imgFs[i]);
           gallery.setSmall(imgFs[i]);
           gallery.setThumbnail(imgFs[i]);
           gallery.setOriginal(imgFs[i]);
           gallery.setTiny(imgFs[i]);
           gallery.setGoods_id(goods_id.intValue());
           gallery.setImg_id(Integer.valueOf(id[i]).intValue());
           edit(gallery);
         } else if (status[i].equals("1")) {
           GoodsGallery gallery = new GoodsGallery();
           if (i == 0) {
             gallery.setIsdefault(1);
             editGoodsOriginal(imgFs[i], goods_id);
           }
           gallery.setBig(imgFs[i]);
           gallery.setSmall(imgFs[i]);
           gallery.setThumbnail(imgFs[i]);
           gallery.setOriginal(imgFs[i]);
           gallery.setTiny(imgFs[i]);
           gallery.setGoods_id(Integer.valueOf(goods.get("goods_id").toString()).intValue());
           this.goodsGalleryManager.add(gallery);
         }
       }
     }
   }



   private void edit(GoodsGallery gallery)
   {
     this.daoSupport.update("es_goods_gallery", gallery, "img_id=" + gallery.getImg_id());
   }




   private void editGoodsOriginal(String original, Integer goods_id)
   {
     this.daoSupport.execute("update es_goods set original=? where goods_id=?", new Object[] { original, goods_id });
   }





   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
     throws RuntimeException
   {
     String[] imgFs = request.getParameterValues("goods_fs");
     String[] status = request.getParameterValues("status");
     Integer goods_id = Integer.valueOf(goods.get("goods_id").toString());
     if ((status != null) && (imgFs != null)) {
       for (int i = 0; i < status.length; i++) {
         if (status[i].equals("1")) {
           GoodsGallery gallery = new GoodsGallery();
           if (i == 0) {
             gallery.setIsdefault(1);
             editGoodsOriginal(imgFs[i], goods_id);
           }
           gallery.setBig(imgFs[i]);
           gallery.setSmall(imgFs[i]);
           gallery.setThumbnail(imgFs[i]);
           gallery.setOriginal(imgFs[i]);
           gallery.setTiny(imgFs[i]);
           gallery.setGoods_id(goods_id.intValue());
           this.goodsGalleryManager.add(gallery);
         }
       }
     }
   }




   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request)
   {
     Map map = this.goodsManager.get(Integer.valueOf(goods.get("goods_id").toString()));
     if (goods.get("market_enable").equals("1")) {
       if (map.get("market_enable").toString().equals("0")) {
         StoreMember member = this.storeMemberManager.getStoreMember();
         String sql = "update es_store set goods_num=goods_num+1 where store_id=?";
         this.daoSupport.execute(sql, new Object[] { member.getStore_id() });
       }
     } else if ((goods.get("market_enable").equals("0")) &&
       (map.get("market_enable").toString().equals("1"))) {
       StoreMember member = this.storeMemberManager.getStoreMember();
       String sql = "update es_store set goods_num=goods_num-1 where store_id=?";
       this.daoSupport.execute(sql, new Object[] { member.getStore_id() });
     }


     String[] status = request.getParameterValues("status");
     String[] imgFs = request.getParameterValues("goods_fs");
     if ((status != null) && (imgFs != null) && (
       (status[0].equals("3")) || (status[0].equals("1")))) {
       goods.put("thumbnail", imgFs[0]);
     }
   }





   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request)
   {
     if (goods.get("market_enable").equals("1")) {
       StoreMember member = this.storeMemberManager.getStoreMember();
       String sql = "update es_store set goods_num=goods_num+1 where store_id=?";
       this.daoSupport.execute(sql, new Object[] { member.getStore_id() });
     }

     String[] status = request.getParameterValues("status");
     String[] imgFs = request.getParameterValues("goods_fs");
     if ((status != null) && (imgFs != null) &&
       (status[0].equals("1"))) {
       goods.put("thumbnail", imgFs[0]);
     }
   }

   public IGoodsGalleryManager getGoodsGalleryManager()
   {
     return this.goodsGalleryManager;
   }

   public void setGoodsGalleryManager(IGoodsGalleryManager goodsGalleryManager) { this.goodsGalleryManager = goodsGalleryManager; }

   public IDaoSupport getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) { this.daoSupport = daoSupport; }

   public IGoodsManager getGoodsManager() {
     return this.goodsManager;
   }

   public void setGoodsManager(IGoodsManager goodsManager) { this.goodsManager = goodsManager; }

   public IProductManager getProductManager() {
     return this.productManager;
   }

   public void setProductManager(IProductManager productManager) { this.productManager = productManager; }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) { this.storeMemberManager = storeMemberManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\component\plugin\goods\StoreGoodsPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */