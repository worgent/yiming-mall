 package com.enation.app.shop.component.product.plugin.goods;

 import com.enation.app.shop.core.model.GoodsLvPrice;
 import com.enation.app.shop.core.model.Product;
 import com.enation.app.shop.core.plugin.goods.IGoodsAfterAddEvent;
 import com.enation.app.shop.core.plugin.goods.IGoodsAfterEditEvent;
 import com.enation.app.shop.core.plugin.goods.IGoodsDeleteEvent;
 import com.enation.app.shop.core.service.IProductManager;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.StringUtil;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;








 @Component
 public class GeneralProductPlugin
   extends AutoRegisterPlugin
   implements IGoodsAfterAddEvent, IGoodsAfterEditEvent, IGoodsDeleteEvent
 {
   private IProductManager productManager;

   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
     throws RuntimeException
   {
     if (goods.get("goods_id") == null)
       throw new RuntimeException("商品id不能为空");
     Integer goodsId = Integer.valueOf(goods.get("goods_id").toString());
     Integer brandid = null;
     if (goods.get("brand_id") != null) {
       brandid = Integer.valueOf(goods.get("brand_id").toString());
     }
     String sn = (String)goods.get("sn");

     Product product = new Product();
     product.setGoods_id(goodsId);
     product.setCost(Double.valueOf("" + goods.get("cost")));
     product.setPrice(Double.valueOf("" + goods.get("price")));
     product.setSn(sn);
     product.setStore(Integer.valueOf("" + goods.get("store")));
     product.setWeight(Double.valueOf("" + goods.get("weight")));
     product.setName((String)goods.get("name"));

     List<Product> productList = new ArrayList();


     String[] lvPriceStr = request.getParameterValues("lvPrice");
     String[] lvidStr = request.getParameterValues("lvid");


     if ((lvidStr != null) && (lvidStr.length > 0)) {
       List<GoodsLvPrice> goodsLvPrices = createGoodsLvPrices(lvPriceStr, lvidStr, goodsId.intValue());
       product.setGoodsLvPrices(goodsLvPrices);
     }

     productList.add(product);
     this.productManager.add(productList);
   }

   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
   {
     if (goods.get("goods_id") == null)
       throw new RuntimeException("商品id不能为空");
     Integer goodsId = Integer.valueOf(goods.get("goods_id").toString());
     Product product = this.productManager.getByGoodsId(goodsId);
     product.setGoods_id(goodsId);
     product.setCost(Double.valueOf("" + goods.get("cost")));
     product.setPrice(Double.valueOf("" + goods.get("price")));
     product.setSn((String)goods.get("sn"));


     product.setName((String)goods.get("name"));

     List<Product> productList = new ArrayList();


     String[] lvPriceStr = request.getParameterValues("lvPrice");
     String[] lvidStr = request.getParameterValues("lvid");


     if ((lvidStr != null) && (lvidStr.length > 0)) {
       List<GoodsLvPrice> goodsLvPrices = createGoodsLvPrices(lvPriceStr, lvidStr, goodsId.intValue());
       product.setGoodsLvPrices(goodsLvPrices);
     }

     productList.add(product);
     this.productManager.add(productList);
   }









   private List<GoodsLvPrice> createGoodsLvPrices(String[] lvPriceStr, String[] lvidStr, int goodsid)
   {
     List<GoodsLvPrice> goodsLvPrices = new ArrayList();
     for (int i = 0; i < lvidStr.length; i++) {
       int lvid = StringUtil.toInt(lvidStr[i]);
       Double lvPrice = StringUtil.toDouble(lvPriceStr[i]);

       if (lvPrice.doubleValue() != 0.0D) {
         GoodsLvPrice goodsLvPrice = new GoodsLvPrice();
         goodsLvPrice.setGoodsid(goodsid);
         goodsLvPrice.setPrice(lvPrice);
         goodsLvPrice.setLvid(lvid);
         goodsLvPrices.add(goodsLvPrice);
       }
     }
     return goodsLvPrices;
   }

   public IProductManager getProductManager() {
     return this.productManager;
   }

   public void setProductManager(IProductManager productManager) {
     this.productManager = productManager;
   }

   public void onGoodsDelete(Integer[] goodsid)
   {
     this.productManager.delete(goodsid);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\product\plugin\goods\GeneralProductPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */