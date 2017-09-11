 package com.enation.app.shop.core.service.impl;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.base.core.service.auth.IPermissionManager;
 import com.enation.app.base.core.service.auth.IRoleManager;
 import com.enation.app.base.core.service.auth.impl.PermissionConfig;
 import com.enation.app.shop.core.model.DepotUser;
 import com.enation.app.shop.core.model.DlyType;
 import com.enation.app.shop.core.model.Goods;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.OrderItem;
 import com.enation.app.shop.core.model.OrderLog;
 import com.enation.app.shop.core.model.PayCfg;
 import com.enation.app.shop.core.model.Promotion;
 import com.enation.app.shop.core.model.support.CartItem;
 import com.enation.app.shop.core.model.support.OrderPrice;
 import com.enation.app.shop.core.plugin.order.OrderPluginBundle;
 import com.enation.app.shop.core.service.ICartManager;
 import com.enation.app.shop.core.service.IDepotManager;
 import com.enation.app.shop.core.service.IDlyTypeManager;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IOrderAllocationManager;
 import com.enation.app.shop.core.service.IOrderFlowManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IPaymentManager;
 import com.enation.app.shop.core.service.IPromotionManager;
 import com.enation.eop.processor.httpcache.HttpCacheManager;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.database.DoubleMapper;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.database.StringMapper;
 import com.enation.framework.util.CurrencyUtil;
 import com.enation.framework.util.ExcelUtil;
 import com.enation.framework.util.FileUtil;
 import com.enation.framework.util.StringUtil;
 import java.io.File;
 import java.io.InputStream;
 import java.io.PrintStream;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.jdbc.core.RowMapper;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;







 public class OrderManager
   extends BaseSupport
   implements IOrderManager
 {
   private ICartManager cartManager;
   private IDlyTypeManager dlyTypeManager;
   private IPaymentManager paymentManager;
   private IPromotionManager promotionManager;
   private OrderPluginBundle orderPluginBundle;
   private IPermissionManager permissionManager;
   private IAdminUserManager adminUserManager;
   private IRoleManager roleManager;
   private IGoodsManager goodsManager;
   private IOrderAllocationManager orderAllocationManager;
   private IDepotManager depotManager;

   public IOrderAllocationManager getOrderAllocationManager()
   {
     return this.orderAllocationManager;
   }

   public void setOrderAllocationManager(IOrderAllocationManager orderAllocationManager) {
     this.orderAllocationManager = orderAllocationManager;
   }

   public IGoodsManager getGoodsManager() { return this.goodsManager; }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }

   public IDepotManager getDepotManager() { return this.depotManager; }


   public void setDepotManager(IDepotManager depotManager) { this.depotManager = depotManager; }

   @Transactional(propagation=Propagation.REQUIRED)
   public void savePrice(double price, int orderid) {
     Order order = get(Integer.valueOf(orderid));
     double amount = order.getOrder_amount().doubleValue();

     double discount = CurrencyUtil.sub(amount, price);
     this.baseDaoSupport.execute("update order set order_amount=?,need_pay_money=? where order_id=?", new Object[] { Double.valueOf(price), Double.valueOf(price), Integer.valueOf(orderid) });



     String sql = "update es_payment_logs set money=? where order_id=?";
     this.daoSupport.execute(sql, new Object[] { Double.valueOf(price), Integer.valueOf(orderid) });

     this.baseDaoSupport.execute("update order set discount=discount+? where order_id=?", new Object[] { Double.valueOf(discount), Integer.valueOf(orderid) });
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public double saveShipmoney(double shipmoney, int orderid)
   {
     Order order = get(Integer.valueOf(orderid));
     double currshipamount = order.getShipping_amount().doubleValue();

     double shortship = CurrencyUtil.sub(shipmoney, currshipamount);
     double discount = CurrencyUtil.sub(currshipamount, shipmoney);

     this.baseDaoSupport.execute("update order set order_amount=order_amount+?,need_pay_money=need_pay_money+?,shipping_amount=?,discount=discount+? where order_id=?", new Object[] { Double.valueOf(shortship), Double.valueOf(shortship), Double.valueOf(shipmoney), Double.valueOf(discount), Integer.valueOf(orderid) });



     this.daoSupport.execute("update es_payment_logs set money=money+? where order_id=?", new Object[] { Double.valueOf(shortship), Integer.valueOf(orderid) });
     return get(Integer.valueOf(orderid)).getOrder_amount().doubleValue();
   }








   public void log(Integer order_id, String message, Integer op_id, String op_name)
   {
     OrderLog orderLog = new OrderLog();
     orderLog.setMessage(message);
     orderLog.setOp_id(op_id);
     orderLog.setOp_name(op_name);
     orderLog.setOp_time(Long.valueOf(com.enation.framework.util.DateUtil.getDatelineLong()));
     orderLog.setOrder_id(order_id);
     this.baseDaoSupport.insert("order_log", orderLog);
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public Order add(Order order, String sessionid) {
     String opname = "游客";

     if (order == null) {
       throw new RuntimeException("error: order is null");
     }

     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();

     if (member != null) {
       order.setMember_id(member.getMember_id());
       opname = member.getUname();
     }



     List<CartItem> cartItemList = this.cartManager.listGoods(sessionid);
     OrderPrice orderPrice = this.cartManager.countPrice(cartItemList, order.getShipping_id(), "" + order.getRegionid());

     order.setGoods_amount(orderPrice.getGoodsPrice());
     order.setWeight(orderPrice.getWeight());

     order.setDiscount(orderPrice.getDiscountPrice());
     order.setOrder_amount(orderPrice.getOrderPrice());
     order.setProtect_price(orderPrice.getProtectPrice());
     order.setShipping_amount(orderPrice.getShippingPrice());
     order.setGainedpoint(orderPrice.getPoint().intValue());

     order.setNeed_pay_money(orderPrice.getNeedPayMoney());

     DlyType dlyType = new DlyType();
     if ((dlyType != null) && (order.getShipping_id().intValue() != 0)) {
       dlyType = this.dlyTypeManager.getDlyTypeById(order.getShipping_id());
     } else {
       dlyType.setName("卖家承担运费！");
     }





     order.setShipping_type(dlyType.getName());


     PayCfg payCfg = this.paymentManager.get(order.getPayment_id());
     order.setPaymoney(this.paymentManager.countPayPrice(order.getOrder_id()));
     order.setPayment_name(payCfg.getName());

     order.setPayment_type(payCfg.getType());


     order.setCreate_time(Long.valueOf(com.enation.framework.util.DateUtil.getDatelineLong()));

     order.setSn(createSn());
     order.setStatus(Integer.valueOf(9));
     order.setDisabled(Integer.valueOf(0));
     order.setPay_status(Integer.valueOf(0));
     order.setShip_status(Integer.valueOf(2));
     order.setOrderStatus("订单已生效");


     Integer depotId = Integer.valueOf(this.baseDaoSupport.queryForInt("select id from depot where choose=1", new Object[0]));
     order.setDepotid(depotId);

     List<CartItem> itemList = this.cartManager.listGoods(sessionid);

     this.orderPluginBundle.onBeforeCreate(order, itemList, sessionid);
     this.baseDaoSupport.insert("order", order);



     if (itemList.isEmpty()) {
       throw new RuntimeException("创建订单失败，购物车为空");
     }
     Integer orderId = Integer.valueOf(this.baseDaoSupport.getLastId("order"));

     order.setOrder_id(orderId);
     saveGoodsItem(itemList, order);





     if (member != null) {
       this.promotionManager.applyOrderPmt(orderId, orderPrice.getOrderPrice(), member.getLv_id());

       List<Promotion> pmtList = this.promotionManager.list(orderPrice.getOrderPrice(), member.getLv_id());

       for (Promotion pmt : pmtList) {
         String sql = "insert into order_pmt(pmt_id,order_id,pmt_describe)values(?,?,?)";
         this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(pmt.getPmt_id()), orderId, pmt.getPmt_describe() });
       }
     }




     OrderLog log = new OrderLog();
     log.setMessage("订单创建");
     log.setOp_name(opname);
     log.setOrder_id(orderId);
     addLog(log);
     order.setOrder_id(orderId);
     order.setOrderprice(orderPrice);
     try {
       this.orderPluginBundle.onAfterCreate(order, itemList, sessionid);
     } catch (Exception e) {
       System.out.println(e);
     }




     IOrderFlowManager flowManager = (IOrderFlowManager)SpringContextHolder.getBean("orderFlowManager");
     flowManager.confirmOrder(orderId);
     this.cartManager.clean(sessionid);
     HttpCacheManager.sessionChange();

     return order;
   }





   private void addLog(OrderLog log)
   {
     log.setOp_time(Long.valueOf(com.enation.framework.util.DateUtil.getDatelineLong()));
     this.baseDaoSupport.insert("order_log", log);
   }






   private void saveGoodsItem(List<CartItem> itemList, Order order)
   {
     Integer order_id = order.getOrder_id();
     for (int i = 0; i < itemList.size(); i++)
     {
       OrderItem orderItem = new OrderItem();

       CartItem cartItem = (CartItem)itemList.get(i);
       orderItem.setPrice(cartItem.getCoupPrice());
       orderItem.setName(cartItem.getName());
       orderItem.setNum(Integer.valueOf(cartItem.getNum()));

       orderItem.setGoods_id(cartItem.getGoods_id());
       orderItem.setShip_num(Integer.valueOf(0));
       orderItem.setProduct_id(cartItem.getProduct_id());
       orderItem.setOrder_id(order_id);
       orderItem.setGainedpoint(cartItem.getPoint().intValue());
       orderItem.setAddon(cartItem.getAddon());


       orderItem.setSn(cartItem.getSn());
       orderItem.setImage(cartItem.getImage_default());
       orderItem.setCat_id(cartItem.getCatid());

       orderItem.setUnit(cartItem.getUnit());

       this.baseDaoSupport.insert("order_items", orderItem);
       int itemid = this.baseDaoSupport.getLastId("order_items");
       orderItem.setItem_id(Integer.valueOf(itemid));
       this.orderPluginBundle.onItemSave(order, orderItem);
     }
   }








   @Transactional(propagation=Propagation.REQUIRED)
   private void saveGiftItem(List<CartItem> itemList, Integer orderid)
   {
     Member member = UserServiceFactory.getUserService().getCurrentMember();
     if (member == null) {
       throw new IllegalStateException("会员尚未登录,不能兑换赠品!");
     }

     int point = 0;
     for (CartItem item : itemList) {
       point += item.getSubtotal().intValue();
       this.baseDaoSupport.execute("insert into order_gift(order_id,gift_id,gift_name,point,num,shipnum,getmethod)values(?,?,?,?,?,?,?)", new Object[] { orderid, item.getProduct_id(), item.getName(), item.getPoint(), Integer.valueOf(item.getNum()), Integer.valueOf(0), "exchange" });
     }




     if (member.getPoint().intValue() < point) {
       throw new IllegalStateException("会员积分不足,不能兑换赠品!");
     }
     member.setPoint(Integer.valueOf(member.getPoint().intValue() - point));
     this.baseDaoSupport.execute("update member set point=? where member_id=? ", new Object[] { member.getPoint(), member.getMember_id() });
   }




   public Page listbyshipid(int pageNo, int pageSize, int status, int shipping_id, String sort, String order)
   {
     order = " ORDER BY " + sort + " " + order;
     String sql = "select * from order where disabled=0 and status=" + status + " and shipping_id= " + shipping_id;

     sql = sql + " order by " + order;
     Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, Order.class, new Object[0]);

     return page;
   }

   public Page listConfirmPay(int pageNo, int pageSize, String sort, String order) {
     order = " order_id";
     String sql = "select * from order where disabled=0 and ((status = 5 and payment_type = 'cod') or status= 1  )";


     sql = sql + " order by " + order;

     Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, Order.class, new Object[0]);
     return page;
   }

   public Order get(Integer orderId) {
     String sql = "select * from order where order_id=?";
     Order order = (Order)this.baseDaoSupport.queryForObject(sql, Order.class, new Object[] { orderId });

     return order;
   }

   public Order get(String ordersn) { String sql = "select * from es_order where sn='" + ordersn + "'";
     Order order = (Order)this.baseDaoSupport.queryForObject(sql, Order.class, new Object[0]);
     return order;
   }



   public List<OrderItem> listGoodsItems(Integer orderId)
   {
     String sql = "select * from " + getTableName("order_items");
     sql = sql + " where order_id = ?";
     List<OrderItem> itemList = this.daoSupport.queryForList(sql, OrderItem.class, new Object[] { orderId });
     this.orderPluginBundle.onFilter(orderId, itemList);
     return itemList;
   }

   public List listGiftItems(Integer orderId)
   {
     String sql = "select * from order_gift where order_id=?";
     return this.baseDaoSupport.queryForList(sql, new Object[] { orderId });
   }




   public List listLogs(Integer orderId)
   {
     String sql = "select * from order_log where order_id=?";
     return this.baseDaoSupport.queryForList(sql, new Object[] { orderId });
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void clean(Integer[] orderId) {
     String ids = StringUtil.arrayToString(orderId, ",");
     String sql = "delete from order where order_id in (" + ids + ")";
     this.baseDaoSupport.execute(sql, new Object[0]);

     sql = "delete from order_items where order_id in (" + ids + ")";
     this.baseDaoSupport.execute(sql, new Object[0]);

     sql = "delete from order_log where order_id in (" + ids + ")";
     this.baseDaoSupport.execute(sql, new Object[0]);

     sql = "delete from payment_logs where order_id in (" + ids + ")";
     this.baseDaoSupport.execute(sql, new Object[0]);

     sql = "delete from " + getTableName("delivery_item") + " where delivery_id in (select delivery_id from " + getTableName("delivery") + " where order_id in (" + ids + "))";



     this.daoSupport.execute(sql, new Object[0]);

     sql = "delete from delivery where order_id in (" + ids + ")";
     this.baseDaoSupport.execute(sql, new Object[0]);

     this.orderAllocationManager.clean(orderId);







     this.orderPluginBundle.onDelete(orderId);
   }


   private boolean exec(Integer[] orderId, int disabled)
   {
     if (cheack(orderId)) {
       String ids = StringUtil.arrayToString(orderId, ",");
       String sql = "update order set disabled = ? where order_id in (" + ids + ")";

       this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(disabled) });
       return true;
     }
     return false;
   }

   private boolean cheack(Integer[] orderId) {
     boolean i = true;
     for (int j = 0; j < orderId.length; j++) {
       if (this.baseDaoSupport.queryForInt("select status from es_order where order_id=?", new Object[] { orderId[j] }) != 8) {
         i = false;
       }
     }
     return i;
   }

   public boolean delete(Integer[] orderId) { return exec(orderId, 1); }


   public void revert(Integer[] orderId)
   {
     exec(orderId, 0);
   }

   public String createSn()
   {
     String orderNum = "00000" + (getOrderNumByMonth().intValue() + 1);
     Date now = new Date();

     String sn = com.enation.framework.util.DateUtil.toString(now, "yyyyMMdd" + orderNum.substring(orderNum.length() - 6, orderNum.length()));

     return sn;
   }




   private Integer getOrderNumByMonth()
   {
     String[] dateString = com.enation.framework.util.DateUtil.getCurrentMonth();
     String sql = "select count(sn) from es_order where create_time>? and create_time<?";
     return Integer.valueOf(this.daoSupport.queryForInt(sql, new Object[] { Long.valueOf(com.enation.framework.util.DateUtil.getDatelineLong(dateString[0])), Long.valueOf(com.enation.framework.util.DateUtil.getDatelineLong(dateString[1])) }));
   }

   public ICartManager getCartManager() {
     return this.cartManager;
   }

   public void setCartManager(ICartManager cartManager) {
     this.cartManager = cartManager;
   }

   public IDlyTypeManager getDlyTypeManager() {
     return this.dlyTypeManager;
   }

   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
     this.dlyTypeManager = dlyTypeManager;
   }

   public IPaymentManager getPaymentManager() {
     return this.paymentManager;
   }

   public void setPaymentManager(IPaymentManager paymentManager) {
     this.paymentManager = paymentManager;
   }

   public List listOrderByMemberId(int member_id) {
     String sql = "select * from order where member_id = ? order by create_time desc";
     List list = this.baseDaoSupport.queryForList(sql, Order.class, new Object[] { Integer.valueOf(member_id) });

     return list;
   }

   public Map mapOrderByMemberId(int memberId) {
     Integer buyTimes = Integer.valueOf(this.baseDaoSupport.queryForInt("select count(0) from order where member_id = ?", new Object[] { Integer.valueOf(memberId) }));

     Double buyAmount = (Double)this.baseDaoSupport.queryForObject("select sum(paymoney) from order where member_id = ?", new DoubleMapper(), new Object[] { Integer.valueOf(memberId) });


     Map map = new HashMap();
     map.put("buyTimes", buyTimes);
     map.put("buyAmount", buyAmount);
     return map;
   }

   public IPromotionManager getPromotionManager() {
     return this.promotionManager;
   }

   public void setPromotionManager(IPromotionManager promotionManager) {
     this.promotionManager = promotionManager;
   }

   public void edit(Order order) {
     this.baseDaoSupport.update("order", order, "order_id = " + order.getOrder_id());
   }


   public List<Map> listAdjItem(Integer orderid)
   {
     String sql = "select * from order_items where order_id=? and addon!=''";
     return this.baseDaoSupport.queryForList(sql, new Object[] { orderid });
   }






   public Map censusState()
   {
     Map<String, Integer> stateMap = new HashMap(7);
     String[] states = { "cancel_ship", "cancel_pay", "pay", "ship", "complete", "allocation_yes" };
     for (String s : states) {
       stateMap.put(s, Integer.valueOf(0));
     }


     String sql = "select count(0) num,status  from " + getTableName("order") + " where disabled = 0 group by status";


     List<Map<String, Integer>> list = this.daoSupport.queryForList(sql, new RowMapper()
     {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException
       {
         Map<String, Integer> map = new HashMap();
         map.put("status", Integer.valueOf(rs.getInt("status")));
         map.put("num", Integer.valueOf(rs.getInt("num")));
         return map; } }, new Object[0]);




     for (Map<String, Integer> state : list) {
       stateMap.put(getStateString((Integer)state.get("status")), state.get("num"));
     }

     sql = "select count(0) num  from " + getTableName("order") + " where disabled = 0  and status=0 ";
     int count = this.daoSupport.queryForInt(sql, new Object[0]);
     stateMap.put("wait", Integer.valueOf(0));

     sql = "select count(0) num  from " + getTableName("order") + " where disabled = 0  ";
     sql = sql + " and ( ( payment_type!='cod' and  status=0) ";
     sql = sql + " or ( payment_id=8 and status!=0  and  pay_status!=2)";
     sql = sql + " or ( payment_type='cod' and  (status=5 or status=6 )  ) )";
     count = this.daoSupport.queryForInt(sql, new Object[0]);
     stateMap.put("not_pay", Integer.valueOf(count));

     sql = "select count(0) from es_order where disabled=0  and ( ( payment_type!='cod' and payment_id!=8  and  status=2)  or ( payment_type='cod' and  status=0))";
     count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
     stateMap.put("allocation_yes", Integer.valueOf(count));

     return stateMap;
   }






   private String getStateString(Integer state)
   {
     String str = null;
     switch (state.intValue()) {
     case -2:
       str = "cancel_ship";
       break;
     case -1:
       str = "cancel_pay";
       break;
     case 1:
       str = "pay";
       break;
     case 2:
       str = "ship";
       break;
     case 4:
       str = "allocation_yes";
       break;
     case 7:
       str = "complete";
       break;
     case 0: case 3: case 5: case 6: default:
       str = null;
     }

     return str;
   }

   public OrderPluginBundle getOrderPluginBundle()
   {
     return this.orderPluginBundle;
   }

   public void setOrderPluginBundle(OrderPluginBundle orderPluginBundle) {
     this.orderPluginBundle = orderPluginBundle;
   }

   public String export(Date start, Date end)
   {
     String sql = "select * from order where disabled=0 ";
     if (start != null) {
       sql = sql + " and create_time>" + start.getTime();
     }

     if (end != null) {
       sql = sql + "  and create_timecreate_time<" + end.getTime();
     }

     List<Order> orderList = this.baseDaoSupport.queryForList(sql, Order.class, new Object[0]);



     ExcelUtil excelUtil = new ExcelUtil();


     InputStream in = FileUtil.getResourceAsStream("com/enation/app/shop/core/service/impl/order.xls");

     excelUtil.openModal(in);
     int i = 1;
     for (Order order : orderList)
     {
       excelUtil.writeStringToCell(i, 0, order.getSn());
       excelUtil.writeStringToCell(i, 1, com.enation.eop.sdk.utils.DateUtil.toString(new Date(order.getCreate_time().longValue()), "yyyy-MM-dd HH:mm:ss"));
       excelUtil.writeStringToCell(i, 2, order.getOrderStatus());
       excelUtil.writeStringToCell(i, 3, "" + order.getOrder_amount());
       excelUtil.writeStringToCell(i, 4, order.getShip_name());
       excelUtil.writeStringToCell(i, 5, order.getPayStatus());
       excelUtil.writeStringToCell(i, 6, order.getShipStatus());
       excelUtil.writeStringToCell(i, 7, order.getShipping_type());
       excelUtil.writeStringToCell(i, 8, order.getPayment_name());
       i++;
     }


     String filename = "";
     if ("2".equals(EopSetting.RUNMODE)) {
       EopSite site = EopContext.getContext().getCurrentSite();
       filename = "/user/" + site.getUserid() + "/" + site.getId() + "/order";
     } else {
       filename = "/order";
     }
     File file = new File(EopSetting.IMG_SERVER_PATH + filename);
     if (!file.exists()) { file.mkdirs();
     }
     filename = filename + "/order" + com.enation.framework.util.DateUtil.getDatelineLong() + ".xls";
     excelUtil.writeToFile(EopSetting.IMG_SERVER_PATH + filename);

     return EopSetting.IMG_SERVER_DOMAIN + filename;
   }



   public OrderItem getItem(int itemid)
   {
     String sql = "select items.*,p.store as store from " + getTableName("order_items") + " items ";

     sql = sql + " left join " + getTableName("product") + " p on p.product_id = items.product_id ";

     sql = sql + " where items.item_id = ?";

     OrderItem item = (OrderItem)this.daoSupport.queryForObject(sql, OrderItem.class, new Object[] { Integer.valueOf(itemid) });


     return item;
   }

   public IAdminUserManager getAdminUserManager()
   {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) {
     this.adminUserManager = adminUserManager;
   }

   public IPermissionManager getPermissionManager() {
     return this.permissionManager;
   }

   public void setPermissionManager(IPermissionManager permissionManager) {
     this.permissionManager = permissionManager;
   }

   public IRoleManager getRoleManager() {
     return this.roleManager;
   }

   public void setRoleManager(IRoleManager roleManager) {
     this.roleManager = roleManager;
   }





   public int getMemberOrderNum(int member_id, int payStatus)
   {
     return this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM order WHERE member_id=? AND pay_status=?", new Object[] { Integer.valueOf(member_id), Integer.valueOf(payStatus) });
   }







   public List<Map> getItemsByOrderid(Integer order_id)
   {
     String sql = "select * from order_items where order_id=?";
     return this.baseDaoSupport.queryForList(sql, new Object[] { order_id });
   }

   public void refuseReturn(String orderSn)
   {
     this.baseDaoSupport.execute("update order set state = -5 where sn = ?", new Object[] { orderSn });
   }





   public void updateOrderPrice(double price, int orderid)
   {
     this.baseDaoSupport.execute("update order set order_amount = order_amount-?,goods_amount = goods_amount- ? where order_id = ?", new Object[] { Double.valueOf(price), Double.valueOf(price), Integer.valueOf(orderid) });
   }







   public String queryLogiNameById(Integer logi_id)
   {
     return (String)this.baseDaoSupport.queryForObject("select name from logi_company where id=?", new StringMapper(), new Object[] { logi_id });
   }






   public Page searchForGuest(int pageNo, int pageSize, String ship_name, String ship_tel)
   {
     String sql = "select * from order where ship_name=? AND (ship_mobile=? OR ship_tel=?) and member_id is null ORDER BY order_id DESC";
     Page page = this.baseDaoSupport.queryForPage(sql.toString(), pageNo, pageSize, Order.class, new Object[] { ship_name, ship_tel, ship_tel });
     return page;
   }

   public Page listByStatus(int pageNo, int pageSize, int status, int memberid) {
     String filedname = "status";
     if (status == 0)
     {
       filedname = " status!=8 AND pay_status";
     }

     String sql = "select * from order where " + filedname + "=? AND member_id=? ORDER BY order_id DESC";

     Page page = this.baseDaoSupport.queryForPage(sql.toString(), pageNo, pageSize, Order.class, new Object[] { Integer.valueOf(status), Integer.valueOf(memberid) });

     return page;
   }

   public List<Order> listByStatus(int status, int memberid)
   {
     String filedname = "status";
     if (status == 0)
     {
       filedname = " status!=8 AND pay_status";
     }

     String sql = "select * from order where " + filedname + "=? AND member_id=? ORDER BY order_id DESC";


     return this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(status), Integer.valueOf(memberid) });
   }

   public int getMemberOrderNum(int member_id)
   {
     return this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM order WHERE member_id=?", new Object[] { Integer.valueOf(member_id) });
   }



   public Page search(int pageNO, int pageSize, int disabled, String sn, String logi_no, String uname, String ship_name, int status, Integer paystatus)
   {
     StringBuffer sql = new StringBuffer("select * from " + getTableName("order") + " where disabled=?  ");

     if (status != -100) {
       if (status == -99)
       {


         sql.append(" and ((payment_type='cod' and status=0 )  or (payment_type!='cod' and status=1 )) ");
       }
       else {
         sql.append(" and status = " + status + " ");
       }
     }
     if ((paystatus != null) && (paystatus.intValue() != -100)) {
       sql.append(" and pay_status = " + paystatus + " ");
     }

     if (!StringUtil.isEmpty(sn)) {
       sql.append(" and sn = '" + sn + "' ");
     }
     if (!StringUtil.isEmpty(uname)) {
       sql.append(" and member_id  in ( SELECT  member_id FROM " + getTableName("member") + " where uname = '" + uname + "' )  ");
     }

     if (!StringUtil.isEmpty(ship_name)) {
       sql.append(" and  ship_name = '" + ship_name + "' ");
     }
     if (!StringUtil.isEmpty(logi_no)) {
       sql.append(" and order_id in (SELECT order_id FROM " + getTableName("delivery") + " where logi_no = '" + logi_no + "') ");
     }

     sql.append(" order by create_time desc ");
     Page page = this.daoSupport.queryForPage(sql.toString(), pageNO, pageSize, Order.class, new Object[] { Integer.valueOf(disabled) });

     return page;
   }


   public Page search(int pageNO, int pageSize, int disabled, String sn, String logi_no, String uname, String ship_name, int status)
   {
     return search(pageNO, pageSize, disabled, sn, logi_no, uname, ship_name, status, null);
   }



   public Order getNext(String next, Integer orderId, Integer status, int disabled, String sn, String logi_no, String uname, String ship_name)
   {
     StringBuffer sql = new StringBuffer("select * from " + getTableName("order") + " where  1=1  ");


     StringBuffer depotsql = new StringBuffer("  ");
     AdminUser adminUser = this.adminUserManager.getCurrentUser();
     if (adminUser.getFounder() != 1)
     {
       boolean isShiper = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_ship"));


       boolean haveAllo = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("allocation"));

       boolean haveOrder = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("order"));

       if ((isShiper) && (!haveAllo) && (!haveOrder)) {
         DepotUser depotUser = (DepotUser)adminUser;
         int depotid = depotUser.getDepotid().intValue();
         depotsql.append(" and depotid=" + depotid + "  ");
       }
     }

     StringBuilder sbsql = new StringBuilder("  ");
     if ((status != null) && (status.intValue() != -100)) {
       sbsql.append(" and status = " + status + " ");
     }



     if ((!StringUtil.isEmpty(uname)) && (!uname.equals("undefined"))) {
       sbsql.append(" and member_id  in ( SELECT  member_id FROM " + getTableName("member") + " where uname = '" + uname + "' )  ");
     }

     if (!StringUtil.isEmpty(ship_name)) {
       sbsql.append("  and  ship_name = '" + ship_name.trim() + "'  ");
     }
     if ((!StringUtil.isEmpty(logi_no)) && (!logi_no.equals("undefined"))) {
       sbsql.append("  and order_id in (SELECT order_id FROM " + getTableName("delivery") + " where logi_no = '" + logi_no + "')  ");
     }
     if (next.equals("previous")) {
       sql.append("  and order_id IN (SELECT CASE WHEN SIGN(order_id - " + orderId + ") < 0 THEN MAX(order_id)  END AS order_id FROM " + getTableName("order") + " WHERE order_id <> " + orderId + depotsql.toString() + " and disabled=? " + sbsql.toString() + " GROUP BY SIGN(order_id - " + orderId + ") ORDER BY SIGN(order_id - " + orderId + "))   ");




     }
     else if (next.equals("next")) {
       sql.append("  and  order_id in (SELECT CASE WHEN SIGN(order_id - " + orderId + ") > 0 THEN MIN(order_id) END AS order_id FROM " + getTableName("order") + " WHERE order_id <> " + orderId + depotsql.toString() + " and disabled=? " + sbsql.toString() + " GROUP BY SIGN(order_id - " + orderId + ") ORDER BY SIGN(order_id - " + orderId + "))   ");

     }
     else
     {

       return null;
     }
     sql.append(" order by create_time desc ");

     Order order = (Order)this.daoSupport.queryForObject(sql.toString(), Order.class, new Object[] { Integer.valueOf(disabled) });

     return order;
   }






   private double getOrderTotal(String sessionid)
   {
     List goodsItemList = this.cartManager.listGoods(sessionid);
     double orderTotal = 0.0D;
     if ((goodsItemList != null) && (goodsItemList.size() > 0)) {
       for (int i = 0; i < goodsItemList.size(); i++) {
         CartItem cartItem = (CartItem)goodsItemList.get(i);
         orderTotal += cartItem.getCoupPrice().doubleValue() * cartItem.getNum();
       }
     }
     return orderTotal;
   }





   private OrderItem getOrderItem(Integer itemid)
   {
     return (OrderItem)this.baseDaoSupport.queryForObject("select * from order_items where item_id = ?", OrderItem.class, new Object[] { itemid });
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public boolean delItem(Integer itemid, Integer itemnum) {
     OrderItem item = getOrderItem(itemid);
     Order order = get(item.getOrder_id());
     boolean flag = false;
     int paymentid = order.getPayment_id().intValue();
     int status = order.getStatus().intValue();
     if (((paymentid == 1) || (paymentid == 3) || (paymentid == 4) || (paymentid == 5)) && ((status == 0) || (status == 1) || (status == 2) || (status == 3) || (status == 4))) {
       flag = true;
     }
     if ((paymentid == 2) && ((status == 0) || (status == 9) || (status == 3) || (status == 4))) {
       flag = true;
     }
     if (flag) {
       try {
         if (itemnum.intValue() <= item.getNum().intValue()) {
           Goods goods = this.goodsManager.getGoods(item.getGoods_id());
           double order_amount = order.getOrder_amount().doubleValue();
           double itemprice = item.getPrice().doubleValue() * itemnum.intValue();
           double leftprice = CurrencyUtil.sub(order_amount, itemprice);
           int difpoint = (int)Math.floor(leftprice);
           Double[] dlyprice = this.dlyTypeManager.countPrice(order.getShipping_id(), Double.valueOf(order.getWeight().doubleValue() - goods.getWeight().doubleValue() * itemnum.intValue()), Double.valueOf(leftprice), order.getShip_regionid().toString());
           double sumdlyprice = dlyprice[0].doubleValue();
           this.baseDaoSupport.execute("update order set goods_amount = goods_amount- ?,shipping_amount = ?,order_amount =  ?,weight =  weight - ?,gainedpoint =  ? where order_id = ?", new Object[] { Double.valueOf(itemprice), Double.valueOf(sumdlyprice), Double.valueOf(leftprice), Double.valueOf(goods.getWeight().doubleValue() * itemnum.intValue()), Integer.valueOf(difpoint), order.getOrder_id() });

           this.baseDaoSupport.execute("update freeze_point set mp =?,point =?  where orderid = ? and type = ?", new Object[] { Integer.valueOf(difpoint), Integer.valueOf(difpoint), order.getOrder_id(), "buygoods" });
           if (itemnum.intValue() == item.getNum().intValue()) {
             this.baseDaoSupport.execute("delete from order_items where item_id = ?", new Object[] { itemid });
           } else {
             this.baseDaoSupport.execute("update order_items set num = num - ? where item_id = ?", new Object[] { Integer.valueOf(itemnum.intValue()), itemid });
           }
         }
         else {
           return false;
         }
       }
       catch (Exception e) {
         e.printStackTrace();
         return false;
       }
     }

     return flag;
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public boolean saveAddrDetail(String addr, int orderid)
   {
     if ((addr == null) || (StringUtil.isEmpty(addr))) {
       return false;
     }
     this.baseDaoSupport.execute("update order set ship_addr=?  where order_id=?", new Object[] { addr, Integer.valueOf(orderid) });
     return true;
   }


   public boolean saveShipInfo(String remark, String ship_day, String ship_name, String ship_tel, String ship_mobile, String ship_zip, int orderid)
   {
     Order order = get(Integer.valueOf(orderid));
     try {
       if ((ship_day != null) && (!StringUtil.isEmpty(ship_day))) {
         this.baseDaoSupport.execute("update order set ship_day=?  where order_id=?", new Object[] { ship_day, Integer.valueOf(orderid) });
         if ((remark != null) && (!StringUtil.isEmpty(remark)) && (!remark.equals("undefined"))) {
           StringBuilder sb = new StringBuilder("");
           sb.append("【配送时间：");
           sb.append(remark.trim());
           sb.append("】");
           this.baseDaoSupport.execute("update order set remark= concat(remark,'" + sb.toString() + "')   where order_id=?", new Object[] { Integer.valueOf(orderid) });
         }
         return true;
       }
       if ((ship_name != null) && (!StringUtil.isEmpty(ship_name))) {
         this.baseDaoSupport.execute("update order set ship_name=?  where order_id=?", new Object[] { ship_name, Integer.valueOf(orderid) });
         return true;
       }
       if ((ship_tel != null) && (!StringUtil.isEmpty(ship_tel))) {
         this.baseDaoSupport.execute("update order set ship_tel=?  where order_id=?", new Object[] { ship_tel, Integer.valueOf(orderid) });
         return true;
       }
       if ((ship_mobile != null) && (!StringUtil.isEmpty(ship_mobile))) {
         this.baseDaoSupport.execute("update order set ship_mobile=?  where order_id=?", new Object[] { ship_mobile, Integer.valueOf(orderid) });
         return true;
       }
       if ((ship_zip != null) && (!StringUtil.isEmpty(ship_zip))) {
         this.baseDaoSupport.execute("update order set ship_zip=?  where order_id=?", new Object[] { ship_zip, Integer.valueOf(orderid) });
         return true;
       }
       return false;
     } catch (Exception e) {
       e.printStackTrace(); }
     return false;
   }




   public void updatePayMethod(int orderid, int payid, String paytype, String payname)
   {
     this.baseDaoSupport.execute("update order set payment_id=?,payment_type=?,payment_name=? where order_id=?", new Object[] { Integer.valueOf(payid), paytype, payname, Integer.valueOf(orderid) });
   }






   public boolean checkProInOrder(int productid)
   {
     String sql = "select count(0) from order_items where product_id=?";
     return this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(productid) }) > 0;
   }





   public boolean checkGoodsInOrder(int goodsid)
   {
     String sql = "select count(0) from order_items where goods_id=?";
     return this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(goodsid) }) > 0;
   }

   public List listByOrderIds(Integer[] orderids, String order)
   {
     try {
       StringBuffer sql = new StringBuffer("select * from es_order where disabled=0 ");

       if ((orderids != null) && (orderids.length > 0)) {
         sql.append(" and order_id in (" + StringUtil.arrayToString(orderids, ",") + ")");
       }
       if (StringUtil.isEmpty(order)) {
         order = "create_time desc";
       }
       sql.append(" order by  " + order);
       return this.daoSupport.queryForList(sql.toString(), Order.class, new Object[0]);
     } catch (Exception e) {
       e.printStackTrace(); }
     return null;
   }

   public Page list(int pageNO, int pageSize, int disabled, String order)
   {
     StringBuffer sql = new StringBuffer("select * from order where disabled=? ");

     AdminUser adminUser = this.adminUserManager.getCurrentUser();
     if (adminUser.getFounder() != 1) {
       boolean isShiper = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_ship"));
       boolean haveAllo = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("allocation"));

       boolean haveOrder = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("order"));

       if ((isShiper) && (!haveAllo) && (!haveOrder)) {
         DepotUser depotUser = (DepotUser)adminUser;
         int depotid = depotUser.getDepotid().intValue();
         sql.append(" and depotid=" + depotid);
       }
     }

     order = StringUtil.isEmpty(order) ? "order_id desc" : order;
     sql.append(" order by " + order);
     return this.baseDaoSupport.queryForPage(sql.toString(), pageNO, pageSize, Order.class, new Object[] { Integer.valueOf(disabled) });
   }

   public Page list(int pageNo, int pageSize, int status, int depotid, String order)
   {
     order = StringUtil.isEmpty(order) ? "order_id desc" : order;
     String sql = "select * from order where disabled=0 and status=" + status;

     if (depotid > 0) {
       sql = sql + " and depotid=" + depotid;
     }
     sql = sql + " order by " + order;
     Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, Order.class, new Object[0]);
     return page;
   }


   public Page listOrder(Map map, int page, int pageSize, String other, String order)
   {
     String sql = createTempSql(map, other, order);
     Page webPage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
     return webPage;
   }


   private String createTempSql(Map map, String other, String order)
   {
     Integer stype = (Integer)map.get("stype");
     String keyword = (String)map.get("keyword");
     String orderstate = (String)map.get("order_state");
     String start_time = (String)map.get("start_time");
     String end_time = (String)map.get("end_time");
     Integer status = (Integer)map.get("status");
     String sn = (String)map.get("sn");
     String ship_name = (String)map.get("ship_name");
     Integer paystatus = (Integer)map.get("paystatus");
     Integer shipstatus = (Integer)map.get("shipstatus");
     Integer shipping_type = (Integer)map.get("shipping_type");
     Integer payment_id = (Integer)map.get("payment_id");
     Integer depotid = (Integer)map.get("depotid");
     String complete = (String)map.get("complete");

     StringBuffer sql = new StringBuffer();
     sql.append("select * from order where disabled=0 ");

     if ((stype != null) && (keyword != null) &&
       (stype.intValue() == 0)) {
       sql.append(" and (sn like '%" + keyword + "%'");
       sql.append(" or ship_name like '%" + keyword + "%')");
     }


     if (status != null) {
       sql.append("and status=" + status);
     }

     if ((sn != null) && (!StringUtil.isEmpty(sn))) {
       sql.append(" and sn like '%" + sn + "%'");
     }

     if ((ship_name != null) && (!StringUtil.isEmpty(ship_name))) {
       sql.append(" and ship_name like '" + ship_name + "'");
     }

     if (paystatus != null) {
       sql.append(" and pay_status=" + paystatus);
     }

     if (shipstatus != null) {
       sql.append(" and ship_status=" + shipstatus);
     }

     if (shipping_type != null) {
       sql.append(" and shipping_id=" + shipping_type);
     }

     if (payment_id != null) {
       sql.append(" and payment_id=" + payment_id);
     }

     if ((depotid != null) && (depotid.intValue() > 0)) {
       sql.append(" and depotid=" + depotid);
     }

     if ((start_time != null) && (!StringUtil.isEmpty(start_time))) {
       long stime = com.enation.framework.util.DateUtil.getDateline(start_time + " 00:00:00");
       sql.append(" and create_time>" + stime);
     }
     if ((end_time != null) && (!StringUtil.isEmpty(end_time))) {
       long etime = com.enation.framework.util.DateUtil.getDateline(end_time + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
       sql.append(" and create_time<" + etime * 1000L);
     }
     if (!StringUtil.isEmpty(orderstate)) {
       if (orderstate.equals("wait_ship")) {
         sql.append(" and ( ( payment_type!='cod' and payment_id!=8  and  status=2) ");
         sql.append(" or ( payment_type='cod' and  status=0)) ");
       } else if (orderstate.equals("wait_pay")) {
         sql.append(" and ( ( payment_type!='cod' and  status=0) ");
         sql.append(" or ( payment_id=8 and status!=0  and  pay_status!=2)");
         sql.append(" or ( payment_type='cod' and  (status=5 or status=6 )  ) )");
       }
       else if (orderstate.equals("wait_rog")) {
         sql.append(" and status=5");
       } else {
         sql.append(" and status=" + orderstate);
       }
     }


     if (!StringUtil.isEmpty(complete)) {
       sql.append(" and status=7");
     }

     sql.append(" ORDER BY " + other + " " + order);


     return sql.toString();
   }

   public void saveDepot(int orderid, int depotid) { this.orderPluginBundle.onOrderChangeDepot(get(Integer.valueOf(orderid)), depotid, listGoodsItems(Integer.valueOf(orderid)));
     this.daoSupport.execute("update es_order set depotid=?  where order_id=?", new Object[] { Integer.valueOf(depotid), Integer.valueOf(orderid) });
   }

   public void savePayType(int orderid, int paytypeid) { PayCfg cfg = this.paymentManager.get(Integer.valueOf(paytypeid));
     String typename = cfg.getName();
     String paytype = cfg.getType();
     this.daoSupport.execute("update es_order set payment_id=?,payment_name=?,payment_type=? where order_id=?", new Object[] { Integer.valueOf(paytypeid), typename, paytype, Integer.valueOf(orderid) });
   }

   public void saveShipType(int orderid, int shiptypeid) { String typename = this.dlyTypeManager.getDlyTypeById(Integer.valueOf(shiptypeid)).getName();
     this.daoSupport.execute("update es_order set shipping_id=?,shipping_type=? where order_id=?", new Object[] { Integer.valueOf(shiptypeid), typename, Integer.valueOf(orderid) });
   }

   public void add(Order order)
   {
     this.baseDaoSupport.insert("es_order", order);
   }

   public void saveAddr(int orderId, int ship_provinceid, int ship_cityid, int ship_regionid, String Attr) {
     this.daoSupport.execute("update es_order set ship_provinceid=?,ship_cityid=?,ship_regionid=?,shipping_area=? where order_id=?", new Object[] { Integer.valueOf(ship_provinceid), Integer.valueOf(ship_cityid), Integer.valueOf(ship_regionid), Attr, Integer.valueOf(orderId) });
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\OrderManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */