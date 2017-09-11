 package com.enation.app.shop.component.spec.plugin.goods;

 import com.enation.app.shop.component.spec.service.ISpecManager;
 import com.enation.app.shop.core.model.GoodsLvPrice;
 import com.enation.app.shop.core.model.Product;
 import com.enation.app.shop.core.model.SpecValue;
 import com.enation.app.shop.core.model.Specification;
 import com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin;
 import com.enation.app.shop.core.plugin.goods.IGoodsDeleteEvent;
 import com.enation.app.shop.core.plugin.goods.IGoodsTabShowEvent;
 import com.enation.app.shop.core.service.IMemberLvManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IProductManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.plugin.IAjaxExecuteEnable;
 import com.enation.framework.util.StringUtil;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;













 @Component
 public class GoodsSpecPlugin
   extends AbstractGoodsPlugin
   implements IGoodsDeleteEvent, IGoodsTabShowEvent, IAjaxExecuteEnable
 {
   private IProductManager productManager;
   private IMemberLvManager memberLvManager;
   private IOrderManager orderManager;
   private ISpecManager specManager;

   public void addTabs() {}

   private void processGoods(Map goods, HttpServletRequest request)
   {
     HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
     String haveSpec = httpRequest.getParameter("haveSpec");
     goods.put("have_spec", haveSpec);

     if ("0".equals(haveSpec)) {
       goods.put("cost", httpRequest.getParameter("cost"));
       goods.put("price", httpRequest.getParameter("price"));
       goods.put("weight", httpRequest.getParameter("weight"));
       if (!"yes".equals(httpRequest.getParameter("isedit"))) {
         goods.put("store", Integer.valueOf(0));
       }
     } else if ("1".equals(haveSpec)) {
       if (!"yes".equals(httpRequest.getParameter("isedit")))
       {
         goods.put("cost", Integer.valueOf(0));
         goods.put("price", Integer.valueOf(0));
         goods.put("weight", Integer.valueOf(0));
         goods.put("store", Integer.valueOf(0));
       }

       String specs_img = httpRequest.getParameter("spec_imgs");
       specs_img = specs_img == null ? "{}" : specs_img;
       goods.put("specs", specs_img);

       String[] prices = httpRequest.getParameterValues("prices");
       String[] costs = httpRequest.getParameterValues("costs");
       String[] stores = httpRequest.getParameterValues("stores");
       String[] weights = httpRequest.getParameterValues("weights");

       if ((prices != null) && (prices.length > 0)) {
         goods.put("price", prices[0]);
       }
       if ((costs != null) && (costs.length > 0)) {
         goods.put("cost", costs[0]);
       }
       if ((stores != null) && (stores.length > 0)) {
         goods.put("store", stores[0]);
       }
       if ((weights != null) && (weights.length > 0)) {
         goods.put("weight", weights[0]);
       }
     }
   }

   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {
     processGoods(goods, request);
   }

   public void onAfterGoodsEdit(Map goods, HttpServletRequest request) {
     processSpec(goods, request);
   }

   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request) {
     processGoods(goods, request);
   }





   private int getSnsSize(String[] sns)
   {
     int i = 0;
     for (String sn : sns) {
       if (!StringUtil.isEmpty(sn)) {
         i++;
       }
     }
     return i;
   }










   private void processSpec(Map goods, HttpServletRequest request)
   {
     if (goods.get("goods_id") == null)
       throw new RuntimeException("商品id不能为空");
     Integer goodsId = Integer.valueOf(goods.get("goods_id").toString());
     HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();

     String haveSpec = httpRequest.getParameter("haveSpec");

     if ("1".equals(haveSpec))
     {





       String[] specidsAr = httpRequest.getParameterValues("specids");
       String[] specvidsAr = httpRequest.getParameterValues("specvids");

       String[] productids = httpRequest.getParameterValues("productids");
       String[] sns = httpRequest.getParameterValues("sns");
       String[] prices = httpRequest.getParameterValues("prices");
       String[] costs = httpRequest.getParameterValues("costs");

       String[] weights = httpRequest.getParameterValues("weights");

       List<Product> productList = new ArrayList();

       int i = 0;
       int snIndex = getSnsSize(sns);
       for (String sn : sns) {
         Integer productId = StringUtil.isEmpty(productids[i]) ? null : Integer.valueOf(productids[i]);
         if ((sn == null) || (sn.equals(""))) {
           sn = goods.get("sn") + "-" + (snIndex + 1);
           snIndex++;
         }




         List<SpecValue> valueList = new ArrayList();
         int j = 0;
         String[] specids = specidsAr[i].split(",");
         String[] specvids = specvidsAr[i].split(",");


         for (String specid : specids) {
           SpecValue specvalue = new SpecValue();
           specvalue.setSpec_value_id(Integer.valueOf(specvids[j].trim()));
           specvalue.setSpec_id(Integer.valueOf(specid.trim()));
           valueList.add(specvalue);
           j++;
         }


         Product product = new Product();
         product.setGoods_id(goodsId);
         product.setSpecList(valueList);
         product.setName((String)goods.get("name"));
         product.setSn(sn);
         product.setProduct_id(productId);

         String[] specvalues = httpRequest.getParameterValues("specvalue_" + i);
         product.setSpecs(StringUtil.arrayToString(specvalues, "、"));

         if ((null == prices[i]) || ("".equals(prices[i]))) {
           product.setPrice(Double.valueOf(0.0D));
         } else {
           product.setPrice(Double.valueOf(prices[i]));
         }
         if (!"yes".equals(httpRequest.getParameter("isedit"))) {
           product.setStore(Integer.valueOf(0));
         }


         if ((null == costs[i]) || ("".equals(costs[i]))) {
           product.setCost(Double.valueOf(0.0D));
         } else {
           product.setCost(Double.valueOf(costs[i]));
         }

         if ((null == weights[i]) || ("".equals(weights[i]))) {
           product.setWeight(Double.valueOf(0.0D));
         } else {
           product.setWeight(Double.valueOf(weights[i]));
         }


         String[] lvPriceStr = httpRequest.getParameterValues("lvPrice_" + i);

         String[] lvidStr = httpRequest.getParameterValues("lvid_" + i);


         if ((lvidStr != null) && (lvidStr.length > 0)) {
           List<GoodsLvPrice> goodsLvPrices = createGoodsLvPrices(lvPriceStr, lvidStr, goodsId.intValue());
           product.setGoodsLvPrices(goodsLvPrices);
         }

         productList.add(product);
         i++;
       }
       this.productManager.add(productList);
     } else {
       Product product = this.productManager.getByGoodsId(goodsId);
       if (product == null) {
         product = new Product();
       }

       product.setGoods_id(goodsId);
       product.setCost(Double.valueOf("" + goods.get("cost")));
       product.setPrice(Double.valueOf("" + goods.get("price")));
       product.setSn((String)goods.get("sn"));
       product.setWeight(Double.valueOf("" + goods.get("weight")));
       product.setName((String)goods.get("name"));


       String[] lvPriceStr = httpRequest.getParameterValues("lvPrice");
       String[] lvidStr = httpRequest.getParameterValues("lvid");


       if ((lvidStr != null) && (lvidStr.length > 0)) {
         List<GoodsLvPrice> goodsLvPrices = createGoodsLvPrices(lvPriceStr, lvidStr, goodsId.intValue());
         product.setGoodsLvPrices(goodsLvPrices);
       }

       List<Product> productList = new ArrayList();
       productList.add(product);
       this.productManager.add(productList);
     }
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

   public void onAfterGoodsAdd(Map goods, HttpServletRequest request) {
     processSpec(goods, request);
   }




   public String getEditHtml(Map goods, HttpServletRequest request)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     Integer goods_id = Integer.valueOf(goods.get("goods_id").toString());
     List<String> specNameList = this.productManager.listSpecName(goods_id.intValue());
     List<Product> productList = this.productManager.list(goods_id);

     List<Specification> specList = this.specManager.listSpecAndValue();
     freeMarkerPaser.putData("specList", specList);

     List lvList = this.memberLvManager.list();
     freeMarkerPaser.putData("lvList", lvList);

     freeMarkerPaser.putData("productList", productList);
     freeMarkerPaser.putData("specNameList", specNameList);
     freeMarkerPaser.setPageName("spec2");

     return freeMarkerPaser.proessPageContent();
   }




   public String getAddHtml(HttpServletRequest request)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();

     List<Specification> specList = this.specManager.listSpecAndValue();
     freeMarkerPaser.putData("specList", specList);

     freeMarkerPaser.setPageName("spec2");
     return freeMarkerPaser.proessPageContent();
   }

   public void onGoodsDelete(Integer[] goodsid) {
     this.productManager.delete(goodsid);
   }

   public String getAuthor() {
     return "kingapex";
   }

   public String getId() {
     return "goodsspec";
   }

   public String getName() {
     return "通用商品规格插件";
   }

   public String getType() {
     return "";
   }

   public String getVersion() {
     return "1.0";
   }


   public void perform(Object... params) {}

   public IProductManager getProductManager()
   {
     return this.productManager;
   }

   public void setProductManager(IProductManager productManager) {
     this.productManager = productManager;
   }

   public IMemberLvManager getMemberLvManager() {
     return this.memberLvManager;
   }

   public void setMemberLvManager(IMemberLvManager memberLvManager) {
     this.memberLvManager = memberLvManager;
   }

   public String getTabName()
   {
     return "规格";
   }

   public int getOrder()
   {
     return 4;
   }

   public String execute()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String action = request.getParameter("action");
     if ("check-pro-in-order".equals(action)) {
       int productid = StringUtil.toInt(request.getParameter("productid"), true);
       boolean isinorder = this.orderManager.checkProInOrder(productid);
       if (isinorder) {
         return "{result:1}";
       }
       return "{result:0}";
     }
     if ("check-goods-in-order".equals(action)) {
       int goodsid = StringUtil.toInt(request.getParameter("goodsid"), true);
       boolean isinorder = this.orderManager.checkGoodsInOrder(goodsid);
       if (isinorder) {
         return "{result:1}";
       }
       return "{result:0}";
     }

     return "";
   }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public ISpecManager getSpecManager() {
     return this.specManager;
   }

   public void setSpecManager(ISpecManager specManager) {
     this.specManager = specManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\spec\plugin\goods\GoodsSpecPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */