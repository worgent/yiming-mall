 package com.enation.app.shop.core.service.impl;

 import com.enation.app.base.core.model.MemberAddress;
 import com.enation.app.base.core.service.IRegionsManager;
 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.shop.core.model.Delivery;
 import com.enation.app.shop.core.model.DeliveryItem;
 import com.enation.app.shop.core.model.DlyCenter;
 import com.enation.app.shop.core.model.Logi;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.OrderItem;
 import com.enation.app.shop.core.service.IDepotManager;
 import com.enation.app.shop.core.service.IDlyCenterManager;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IGoodsStoreManager;
 import com.enation.app.shop.core.service.IOrderFlowManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IOrderPrintManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.util.FileUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.List;
 import java.util.Map;
 import net.sf.json.JSONArray;

 public class OrderPrintManager implements IOrderPrintManager
 {
   private IDaoSupport daoSupport;
   private IOrderManager orderManager;
   private IDepotManager depotManager;
   private IAdminUserManager adminUserManager;
   private IDlyCenterManager dlyCenterManager;
   private IRegionsManager regionsManager;
   private MemberAddressManager memberAddressManager;
   private IOrderFlowManager orderFlowManager;
   private IGoodsStoreManager goodsStoreManager;
   private IGoodsManager goodsManager;

   public String getShipScript(Integer[] orderid)
   {
     if ((orderid == null) || (orderid.length == 0)) { return "";
     }
     String sql = "select * from es_order where order_id in(" + StringUtil.arrayToString(orderid, ",") + ")";
     List<Order> orderList = this.daoSupport.queryForList(sql, Order.class, new Object[0]);

     StringBuffer str = new StringBuffer();
     for (Order order : orderList) {
       str.append(getShipTemplate(order));
     }
     return str.toString();
   }



   public String getExpressScript(Integer[] orderid)
   {
     String sql = "select * from es_order where order_id in(" + StringUtil.arrayToString(orderid, ",") + ")";
     List<Order> orderList = this.daoSupport.queryForList(sql, Order.class, new Object[0]);

     StringBuffer str = new StringBuffer();
     String code = "";
     int size = 0;
     for (Order order : orderList) {
       if (disdlycenter())
         return "请选择默认发货点";
       if ((!code.equals(order.getShipping_type())) && (size != 0))
         return "快递单选择配送方式不同";
       if (getDlyType(order.getShipping_type()).equals("null"))
         return "请添加配送方式";
       if (!FileUtil.exist(EopSetting.EOP_PATH + "/shop/admin/printtpl/express/" + getcode(order.getShipping_type()) + ".html")) {
         return "没有此快递单模板请添加";
       }
       str.append(getExpressTemplate(order, getcode(order.getShipping_type())));
       code = order.getShipping_type();

       size++;
     }
     return str.toString();
   }



   private boolean disdlycenter()
   {
     String sql = "select count(name) from es_dly_center where choose='true' and disabled='false'";
     return this.daoSupport.queryForInt(sql, new Object[0]) <= 0;
   }

   private String getcode(String logiName) {
     List<Map> list = this.daoSupport.queryForList("select code from es_logi_company where name=?", new Object[] { logiName });
     if (list.size() != 0) {
       return ((Map)list.get(0)).get("code").toString();
     }
     return "null";
   }

   private String getDlyType(String logiName) {
     List<Map> list = this.daoSupport.queryForList("select name from es_dly_type where name=?", new Object[] { logiName });
     if (list.size() != 0) {
       return ((Map)list.get(0)).get("name").toString();
     }
     return "null";
   }

   private String getExpressTemplate(Order order, String code) {
     List<Map> itemList = listItem(order.getOrder_id().intValue());
     int itemCount = 0;
     for (Map item : itemList) {
       int num = ((Integer)item.get("num")).intValue();
       itemCount += num;
     }
     DlyCenter center = getdlycenter();
     Calendar cal = Calendar.getInstance();
     FreeMarkerPaser freeMarkerPaser = new FreeMarkerPaser();

     freeMarkerPaser.setClz(getClass());
     freeMarkerPaser.setPageName(code);
     freeMarkerPaser.setPageFolder("/shop/admin/printtpl/express");

     freeMarkerPaser.putData("order", order);
     freeMarkerPaser.putData("cod_order", order.getShip_no());
     freeMarkerPaser.putData("dlycenter", center);

     freeMarkerPaser.putData("shop_name", "javashop");

     freeMarkerPaser.putData("o_ship_province", getregions(order.getShip_provinceid()));
     freeMarkerPaser.putData("o_ship_city", getregions(order.getShip_cityid()));
     freeMarkerPaser.putData("o_ship_region", getregions(order.getShip_regionid()));
     freeMarkerPaser.putData("d_dly_province", getregions(center.getProvince_id()));
     freeMarkerPaser.putData("d_dly_city", getregions(center.getCity_id()));
     freeMarkerPaser.putData("d_dly_region", getregions(center.getRegion_id()));
     freeMarkerPaser.putData("year", Integer.valueOf(cal.get(1)));
     freeMarkerPaser.putData("month", Integer.valueOf(cal.get(2) + 1));
     freeMarkerPaser.putData("day", Integer.valueOf(cal.get(5)));
     freeMarkerPaser.putData("itemCount", Integer.valueOf(itemCount));
     freeMarkerPaser.putData("ship_time", "ship_time");
     freeMarkerPaser.putData("text", "自定义内容");
     String script = freeMarkerPaser.proessPageContent();
     return script;
   }

   private DlyCenter getdlycenter() {
     String sql = "select * from es_dly_center where choose='true' and disabled='false'";
     return (DlyCenter)this.daoSupport.queryForObject(sql, DlyCenter.class, new Object[0]);
   }

   private String getShipTemplate(Order order) {
     List<Map> itemList = listItem(order.getOrder_id().intValue());








     int itemCount = 0;
     for (Map item : itemList) {
       int num = ((Integer)item.get("num")).intValue();
       itemCount += num;

       if (item.get("addon") != null) {
         String addon = item.get("addon").toString();
         if (!StringUtil.isEmpty(addon)) {
           item.put("specList", (List)JSONArray.toCollection(JSONArray.fromObject(addon), Map.class));
         }
       }
     }
     String depotname = "";

     DlyCenter dlycenter = getdlycenter();

     depotname = dlycenter != null ? dlycenter.getName() : "";



     AdminUser adminUser = this.adminUserManager.getCurrentUser();
     String adminname = adminUser.getRealname();
     if (StringUtil.isEmpty(adminname)) {
       adminname = adminUser.getUsername();
     }

     FreeMarkerPaser freeMarkerPaser = new FreeMarkerPaser();
     freeMarkerPaser.setClz(getClass());
     freeMarkerPaser.setPageName("user");
     freeMarkerPaser.setPageFolder("/shop/admin/printtpl/ship");
     freeMarkerPaser.putData("order", order);

     freeMarkerPaser.putData("itemCount", Integer.valueOf(itemCount));
     freeMarkerPaser.putData("depotname", depotname);
     freeMarkerPaser.putData("adminname", adminname);

     String userHtml = freeMarkerPaser.proessPageContent();
     String itemHtml = createItemHtml(freeMarkerPaser, itemList);


     freeMarkerPaser.setPageName("footer");
     String footerHtml = freeMarkerPaser.proessPageContent();


     userHtml = userHtml.replaceAll("(\r\n|\r|\n|\n\r)", "");
     itemHtml = itemHtml.replaceAll("(\r\n|\r|\n|\n\r)", "");
     footerHtml = footerHtml.replaceAll("(\r\n|\r|\n|\n\r)", "");

     freeMarkerPaser.setPageName("script");
     freeMarkerPaser.putData("userHtml", userHtml);
     freeMarkerPaser.putData("itemHtml", itemHtml);
     freeMarkerPaser.putData("footerHtml", footerHtml);
     String script = freeMarkerPaser.proessPageContent();

     return script;
   }

   private String createItemHtml(FreeMarkerPaser freeMarkerPaser, List itemList)
   {
     StringBuffer itemHtml = new StringBuffer();

     int totalCount = itemList.size();
     int pageSize = 15;
     int firstPageSize = 10;



     int firstMax = totalCount > firstPageSize ? firstPageSize : totalCount;

     List firstList = itemList.subList(0, firstMax);


     freeMarkerPaser.setPageName("item");
     freeMarkerPaser.putData("itemList", firstList);
     freeMarkerPaser.putData("start", Integer.valueOf(0));
     String firstHtml = freeMarkerPaser.proessPageContent();
     firstHtml = "LODOP.ADD_PRINT_TABLE(\"60px\",\"-1\",\"200mm\",\"100%\",'" + firstHtml + "');";
     firstHtml = firstHtml + "LODOP.SET_PRINT_STYLEA(0,\"LinkedItem\",1);";
     itemHtml.append(firstHtml);


     int expessCount = totalCount - firstList.size();


     int pageCount = expessCount / pageSize;
     pageCount = expessCount / pageSize > 0 ? pageCount + 1 : pageCount;

     for (int pageNo = 1; pageNo <= pageCount; pageNo++) {
       itemHtml.append("LODOP.NEWPAGEA();");
       int start = firstMax + (pageNo - 1) * pageSize;
       int end = start + pageSize;
       if (pageNo == pageCount) {
         end = totalCount;
       }

       List subList = itemList.subList(start, end);
       freeMarkerPaser.putData("start", Integer.valueOf(start));
       freeMarkerPaser.putData("itemList", subList);
       String subHtml = freeMarkerPaser.proessPageContent();
       subHtml = "LODOP.ADD_PRINT_TABLE(\"0\",\"-0\",\"200mm\",\"100%\",'" + subHtml + "');";
       itemHtml.append(subHtml);
     }

     return itemHtml.toString();
   }

   public void saveShopNos(Integer[] orderids, String[] shipNos)
   {
     int i = 0;
     for (Integer orderid : orderids) {
       String shipno = shipNos[i];
       this.daoSupport.execute("update es_order set ship_no=? where order_id=?", new Object[] { shipno, orderid });
       i++;
     }
   }


   public String ship(Integer[] orderids)
   {
     StringBuffer sql = new StringBuffer("select * from es_order where disabled=0 ");

     if ((orderids != null) && (orderids.length > 0)) {
       sql.append(" and order_id in (" + StringUtil.arrayToString(orderids, ",") + ")");
     }
     List<Order> orderList = this.daoSupport.queryForList(sql.toString(), Order.class, new Object[0]);
     String is_ship = "";
     for (Order order : orderList) {
       is_ship = is_stock(order);
       if (is_ship.equals("true"))
       {
         if (ship(order))
           return "发货失败:配送方式没有选择物流公司";
       }
     }
     return is_ship;
   }

   private String is_stock(Order order) {
     List<OrderItem> items = this.daoSupport.queryForList("select * from es_order_items where order_id=?", OrderItem.class, new Object[] { order.getOrder_id() });

     String is_ship = "true";
     for (OrderItem item : items) {
       int goods_num = this.goodsStoreManager.getbStoreByProId(item.getProduct_id(), order.getDepotid()).intValue();
       if (goods_num < item.getNum().intValue())
       {
         is_ship = "商品[" + item.getName() + "]在仓库[" + this.depotManager.get(order.getDepotid().intValue()).getName() + "]中库存不足，库存量为[" + goods_num + "]发货量为[" + item.getNum() + "],请进行调拨或更换发货仓库后再发货。";
       }
     }
     return is_ship;
   }




   private boolean ship(Order order)
   {
     Delivery delivery = new Delivery();
     delivery.setOrder_id(order.getOrder_id());
     delivery.setMoney(order.getShipping_amount());
     delivery.setIs_protect(0);
     delivery.setProtect_price(Double.valueOf(0.0D));
     delivery.setMember_id(order.getMember_id());

     Logi logi = null;
     if ((order.getShipping_id() != null) && (order.getShipping_id().intValue() != 0)) {
       int logiid = 0;
       logiid = this.daoSupport.queryForInt("select corp_id from es_dly_type where type_id=?", new Object[] { order.getShipping_id() });
       if (logiid == 0)
         return true;
       logi = (Logi)this.daoSupport.queryForObject("select * from es_logi_company  where id=?", Logi.class, new Object[] { Integer.valueOf(logiid) });
     } else {
       logi = new Logi();
       logi.setId(Integer.valueOf(0));
       logi.setCode("maijia");
       logi.setName("卖家承担运费");
     }


     delivery.setLogi_id(logi.getId());
     delivery.setLogi_name(logi.getName());
     delivery.setShip_type(logi.getName());

     Integer addressid = order.getAddress_id();
     if ((addressid != null) && (addressid.intValue() != 0)) {
       MemberAddress address = this.memberAddressManager.getAddress(order.getAddress_id().intValue());
       delivery.setProvince(address.getProvince());
       delivery.setCity(address.getCity());
       delivery.setRegion(address.getRegion());
     } else {
       delivery.setProvince("");
       delivery.setCity("");
       delivery.setRegion("");
     }

     delivery.setShip_name(order.getShip_name());
     delivery.setShip_addr(order.getShip_addr());
     delivery.setShip_email(order.getShip_email());
     delivery.setShip_mobile(order.getShip_mobile());
     delivery.setShip_zip(order.getShip_zip());




     delivery.setLogi_no(order.getShip_no());


     List<DeliveryItem> itemList = new ArrayList();
     List<OrderItem> orderItemList = this.orderManager.listGoodsItems(order.getOrder_id());
     for (OrderItem orderItem : orderItemList) {
       DeliveryItem item1 = new DeliveryItem();
       item1.setGoods_id(orderItem.getGoods_id());
       item1.setName(orderItem.getName());
       item1.setNum(orderItem.getNum());
       item1.setProduct_id(orderItem.getProduct_id());
       item1.setOrder_itemid(orderItem.getItem_id().intValue());
       item1.setDepotId(order.getDepotid());
       itemList.add(item1);
     }
     this.orderFlowManager.shipping(delivery, itemList);
     return false;
   }




   public static void main(String[] args)
   {
     int total = 11;
     int count = total % 3;
   }


   private List listItem(int orderid)
   {
     String sql = "select i.num,i.price,i.addon,g.sn,g.name,g.type_id,g.p11 p11,g.p8 p8 from es_order_items i inner join es_goods g on i.goods_id = g.goods_id where i.order_id=?";
     return this.daoSupport.queryForList(sql, new Object[] { Integer.valueOf(orderid) });
   }

   private String getregions(Integer regionsId) { String sql = "select local_name from es_regions where region_id = " + regionsId;
     return this.daoSupport.queryForString(sql);
   }

   public IDaoSupport getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public IDepotManager getDepotManager() {
     return this.depotManager;
   }

   public void setDepotManager(IDepotManager depotManager) {
     this.depotManager = depotManager;
   }

   public IAdminUserManager getAdminUserManager() {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) {
     this.adminUserManager = adminUserManager;
   }

   public IDlyCenterManager getDlyCenterManager() {
     return this.dlyCenterManager;
   }

   public void setDlyCenterManager(IDlyCenterManager dlyCenterManager) {
     this.dlyCenterManager = dlyCenterManager;
   }

   public IRegionsManager getRegionsManager()
   {
     return this.regionsManager;
   }

   public void setRegionsManager(IRegionsManager regionsManager)
   {
     this.regionsManager = regionsManager;
   }



   public MemberAddressManager getMemberAddressManager()
   {
     return this.memberAddressManager;
   }

   public void setMemberAddressManager(MemberAddressManager memberAddressManager)
   {
     this.memberAddressManager = memberAddressManager;
   }

   public IOrderFlowManager getOrderFlowManager()
   {
     return this.orderFlowManager;
   }

   public void setOrderFlowManager(IOrderFlowManager orderFlowManager)
   {
     this.orderFlowManager = orderFlowManager;
   }

   public IGoodsStoreManager getGoodsStoreManager()
   {
     return this.goodsStoreManager;
   }

   public void setGoodsStoreManager(IGoodsStoreManager goodsStoreManager)
   {
     this.goodsStoreManager = goodsStoreManager;
   }

   public IGoodsManager getGoodsManager()
   {
     return this.goodsManager;
   }

   public void setGoodsManager(IGoodsManager goodsManager)
   {
     this.goodsManager = goodsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\OrderPrintManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */