 package com.enation.app.shop.core.service.impl;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.base.core.model.MemberLv;
 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.OrderLog;
 import com.enation.app.shop.core.model.PaymentLog;
 import com.enation.app.shop.core.model.PaymentLogType;
 import com.enation.app.shop.core.model.SellBackGoodsList;
 import com.enation.app.shop.core.model.SellBackList;
 import com.enation.app.shop.core.model.SellBackLog;
 import com.enation.app.shop.core.model.SellBackStatus;
 import com.enation.app.shop.core.service.IGoodsStoreManager;
 import com.enation.app.shop.core.service.IMemberLvManager;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.app.shop.core.service.IMemberPointManger;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IOrderMetaManager;
 import com.enation.app.shop.core.service.ISellBackManager;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.CurrencyUtil;
 import com.enation.framework.util.DateUtil;
 import java.io.PrintStream;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;


 public class SellBackManager
   extends BaseSupport
   implements ISellBackManager
 {
   private IOrderManager orderManager;
   private IMemberManager memberManager;
   private IOrderMetaManager orderMetaManager;
   private IMemberPointManger memberPointManger;
   private IMemberLvManager memberLvManager;
   private IGoodsStoreManager goodsStoreManager;
   private IAdminUserManager adminUserManager;

   public Page list(int page, int pageSize)
   {
     String sql = "select * from sellback_list order by id desc ";
     Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
     return webpage;
   }



   public Page search(String keyword, int page, int pageSize)
   {
     String sql = "select * from sellback_list";
     String where = "";
     if (keyword != "") {
       where = " where tradeno like '%" + keyword + "%' or ordersn like '%" + keyword + "%' order by id desc";
     }

     sql = sql + where;
     Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
     return webpage;
   }



   public SellBackList get(String tradeno)
   {
     String sql = "select * from sellback_list where tradeno=?";
     SellBackList result = (SellBackList)this.baseDaoSupport.queryForObject(sql, SellBackList.class, new Object[] { tradeno });

     return result;
   }

   public SellBackList get(Integer id) {
     String sql = "select * from sellback_list where id=?";
     SellBackList result = (SellBackList)this.baseDaoSupport.queryForObject(sql, SellBackList.class, new Object[] { id });
     return result;
   }





   @Transactional(propagation=Propagation.REQUIRED)
   public Integer save(SellBackList data)
   {
     Integer id = Integer.valueOf(0);
     if (data.getId() != null) {
       this.baseDaoSupport.update("sellback_list", data, "id=" + data.getId());
       id = data.getId();
     } else {
       this.baseDaoSupport.insert("sellback_list", data);
       id = Integer.valueOf(this.baseDaoSupport.getLastId("sellback_list"));
     }

     if (data.getTradestatus().intValue() == 1) {
       Integer orderid = this.orderManager.get(data.getOrdersn()).getOrder_id();
       this.baseDaoSupport.execute("update order set status=? where order_id=?", new Object[] { Integer.valueOf(-3), orderid });
       log(orderid, "订单申请退货，金额[" + data.getAlltotal_pay() + "]");
     }

     if (data.getTradestatus().intValue() == 2) {
       syncStore(data);
     }

     if (data.getTradestatus().intValue() == 4) {
       Integer orderid = this.orderManager.get(data.getOrdersn()).getOrder_id();
       this.baseDaoSupport.execute("update order set status=? where order_id=?", new Object[] { Integer.valueOf(5), orderid });
       log(orderid, "取消退货");
     }

     return id;
   }

   protected void updateMemberLv(Member member, int point) {
     MemberLv lv = this.memberLvManager.getByPoint(member.getPoint().intValue() + point);

     if ((lv != null) && (
       (member.getLv_id() == null) || (lv.getLv_id().intValue() < member.getLv_id().intValue())))
     {
       this.memberManager.updateLv(member.getMember_id().intValue(), lv.getLv_id().intValue());
     }
   }







   @Transactional(propagation=Propagation.REQUIRED)
   public void closePayable(int backid, String finance_remark, String logdetail)
   {
     SellBackList data = get(Integer.valueOf(backid));
     data.setTradestatus(Integer.valueOf(SellBackStatus.close_payable.getValue()));
     data.setFinance_remark(finance_remark);

     System.out.println(data.getMember_id());

     Member member = this.memberManager.get(data.getMember_id());
     Order order = this.orderManager.get(data.getOrdersn());
     Integer orderid = order.getOrder_id();



























































     addPayable(member, data.getTotal(), Double.valueOf(0.0D), Double.valueOf(0.0D), order);


     saveLog(data.getId(), SellBackStatus.close_payable, logdetail);


     this.baseDaoSupport.execute("update order set status=?,ship_status=?,pay_status=? where order_id=?", new Object[] { Integer.valueOf(-2), Integer.valueOf(4), Integer.valueOf(3), orderid });




     this.baseDaoSupport.execute("update sellback_list set tradestatus=3 where id=?", new Object[] { Integer.valueOf(backid) });


     log(orderid, "订单退货，金额[" + data.getAlltotal_pay() + "]");
   }










   private void kouChuJingYan(double alltotalpay, Member member, String sn)
   {
     double exp = CurrencyUtil.mul(alltotalpay, 0.1D).doubleValue();
     int value = (int)exp;

     updateMemberLv(member, value);

     member.setPoint(Integer.valueOf(member.getPoint().intValue() - value));

     this.baseDaoSupport.execute("update es_member m set m.point=m.point-? where member_id=?", new Object[] { Integer.valueOf(value), member.getMember_id() });



     Map log = new HashMap();
     log.put("user_id", member.getMember_id());
     log.put("user_money", Integer.valueOf(0));
     log.put("pay_points", Integer.valueOf(0));
     log.put("change_desc", "订单[ " + sn + "]退货扣除经验");
     log.put("change_type", Integer.valueOf(99));
     log.put("frozen_money", Integer.valueOf(0));
     log.put("rank_points", Integer.valueOf(0 - value));
     log.put("friend_points", Integer.valueOf(0));
     log.put("frozen_friend_points", Integer.valueOf(0));
     log.put("add_credit_account_money", Integer.valueOf(0));
     log.put("change_time", Integer.valueOf(DateUtil.getDateline()));
     this.baseDaoSupport.insert("account_log", log);
   }











   private void addPayable(Member member, Double money, Double credit, Double mp, Order order)
   {
     PaymentLog paymentLog = new PaymentLog();

     paymentLog.setMember_id(member.getMember_id());
     paymentLog.setPay_user(member.getUname());
     paymentLog.setMoney(money);
     paymentLog.setCredit(credit);
     paymentLog.setMarket_point(mp);
     paymentLog.setPay_date(DateUtil.getDateline());
     paymentLog.setOrder_sn(order.getSn());
     paymentLog.setSn("");
     paymentLog.setPay_method(order.getPayment_name());
     paymentLog.setOrder_id(order.getOrder_id().intValue());
     paymentLog.setType(PaymentLogType.payable.getValue());
     paymentLog.setStatus(Integer.valueOf(1));
     paymentLog.setCreate_time(Long.valueOf(System.currentTimeMillis()));

     AdminUser adminUser = this.adminUserManager.getCurrentUser();
     if (adminUser != null) {
       paymentLog.setAdmin_user(adminUser.getRealname() + "[" + adminUser.getUsername() + "]");
     }
     else if (member != null) {
       paymentLog.setAdmin_user(member.getName());
     }

     this.baseDaoSupport.insert("payment_logs", paymentLog);
   }













   private void returnCreditAndMp(int memberid, Double credit, Double mp, String desc)
   {
     if (credit == null) {
       credit = Double.valueOf(0.0D);
     }

     if (mp == null) {
       mp = Double.valueOf(0.0D);
     }

     if ((credit != null) && (credit.doubleValue() != 0.0D))
     {
       this.memberManager.addMoney(Integer.valueOf(memberid), credit);
     }

     if ((mp != null) && (mp.doubleValue() != 0.0D))
     {
       this.baseDaoSupport.execute("update member set mp=mp+?   where member_id=?", new Object[] { mp, Integer.valueOf(memberid) });
     }



     saveAccountLog(memberid, credit.doubleValue(), mp.doubleValue(), desc);
   }



   public void saveLog(Integer recid, SellBackStatus status, String logdetail)
   {
     SellBackLog sellBackLog = new SellBackLog();

     sellBackLog.setRecid(recid);
     if ("".equals(logdetail)) {
       logdetail = status.getName();
     }
     sellBackLog.setLogdetail(logdetail);
     sellBackLog.setLogtime(Long.valueOf(DateUtil.getDatelineLong()));
     sellBackLog.setOperator("test");
     sellBackLog.setOperator(this.adminUserManager.getCurrentUser().getUsername());

     this.baseDaoSupport.insert("sellback_log", sellBackLog);
   }


   private void saveAccountLog(int memberid, double credit, double mp, String desc)
   {
     Map log = new HashMap();
     log.put("user_id", Integer.valueOf(memberid));
     log.put("user_money", Double.valueOf(credit));
     log.put("pay_points", Double.valueOf(mp));
     log.put("change_desc", desc);
     log.put("change_type", Integer.valueOf(99));
     log.put("frozen_money", Integer.valueOf(0));
     log.put("rank_points", Integer.valueOf(0));
     log.put("friend_points", Integer.valueOf(0));
     log.put("frozen_friend_points", Integer.valueOf(0));
     log.put("add_credit_account_money", Integer.valueOf(0));
     log.put("change_time", Integer.valueOf(DateUtil.getDateline()));
     this.baseDaoSupport.insert("account_log", log);
   }



   private void log(Integer order_id, String message)
   {
     AdminUser adminUser = this.adminUserManager.getCurrentUser();
     OrderLog orderLog = new OrderLog();
     orderLog.setMessage(message);
     orderLog.setOp_id(adminUser.getUserid());
     orderLog.setOp_id(Integer.valueOf(1));
     orderLog.setOp_name("test");
     orderLog.setOp_name(adminUser.getUsername());
     orderLog.setOp_time(Long.valueOf(System.currentTimeMillis()));
     orderLog.setOrder_id(order_id);
     this.baseDaoSupport.insert("order_log", orderLog);
   }




   public Integer saveGoodsList(SellBackGoodsList data)
   {
     if (data.getId() == null) {
       this.baseDaoSupport.insert("sellback_goodslist", data);
     } else {
       this.baseDaoSupport.update("sellback_goodslist", data, "id=" + data.getId());
     }

     Integer id = Integer.valueOf(this.baseDaoSupport.getLastId("sellback_goodslist"));

     return id;
   }



   public SellBackGoodsList getSellBackGoods(Integer recid, Integer goodsid)
   {
     String sql = "select * from sellback_goodslist where recid=? and goods_id=?";
     SellBackGoodsList result = (SellBackGoodsList)this.baseDaoSupport.queryForObject(sql, SellBackGoodsList.class, new Object[] { recid, goodsid });

     return result;
   }



   public List getGoodsList(Integer recid, String sn)
   {
     String sql = "select i.*,g.return_num,g.goods_id as goodsId,storage_num,goods_remark,g.ship_num from " + getTableName("order_items") + " i left join (select return_num,goods_id,storage_num,goods_remark,ship_num,price  from  " + getTableName("sellback_goodslist") + " where recid=" + recid + ") g on g.goods_id=i.goods_id where i.order_id in (select order_id from " + getTableName("order") + " where sn='" + sn + "') order by item_id";










     List result = this.baseDaoSupport.queryForList(sql, new Object[0]);
     return result;
   }

   public List getGoodsList(Integer recid) {
     return this.baseDaoSupport.queryForList("select s.*,g.name,g.is_pack from sellback_goodslist s inner join goods g on g.goods_id=s.goods_id where recid=?", new Object[] { recid });
   }





   public void saveAccountLog(Map log)
   {
     this.baseDaoSupport.insert("account_log", log);
   }



   public Integer getRecid(String tradeno)
   {
     return Integer.valueOf(this.baseDaoSupport.queryForInt("select id from sellback_list where tradeno=?", new Object[] { tradeno }));
   }




   public void editGoodsNum(Map data)
   {
     Integer recid = (Integer)data.get("recid");
     Integer goods_id = (Integer)data.get("goods_id");
     this.baseDaoSupport.update("sellback_goodslist", data, "recid=" + recid + " and goods_id=" + goods_id);
   }





   public void editStorageNum(Integer recid, Integer goods_id, Integer num)
   {
     this.baseDaoSupport.execute("update sellback_goodslist set storage_num=" + num + " where recid=? and goods_id=?", new Object[] { recid, goods_id });
   }





   public void delGoods(Integer recid, Integer goodsid)
   {
     this.baseDaoSupport.execute("delete from sellback_goodslist where recid=? and goods_id=?", new Object[] { recid, goodsid });
   }





   public List sellBackLogList(Integer recid)
   {
     return this.baseDaoSupport.queryForList("select * from sellback_log where recid=? order by id desc", new Object[] { recid });
   }








   public List<Map> listPack(int goods_id)
   {
     String sql = "select pp.*, g.name  from es_package_product  pp inner join es_goods  g on g.goods_id = pp.rel_goods_id";
     sql = sql + " where pp.goods_id = " + goods_id;
     List<Map> list = this.daoSupport.queryForList(sql, new Object[0]);
     return list;
   }



   public Integer isAll(int recid)
   {
     Integer result = Integer.valueOf(0);
     Integer returnCount = Integer.valueOf(this.baseDaoSupport.queryForInt("select count(*) from sellback_goodslist where recid=?", new Object[] { Integer.valueOf(recid) }));

     Integer storageCount = Integer.valueOf(this.baseDaoSupport.queryForInt("select count(*) from sellback_goodslist a," + getTableName("sellback_goodslist") + " b where a.goods_id=b.goods_id and a.return_num=b.storage_num and a.recid=?", new Object[] { Integer.valueOf(recid) }));





     if (returnCount == storageCount) {
       result = Integer.valueOf(1);
     }
     return result;
   }


   public void syncStore(SellBackList sellback)
   {
     int depotid = sellback.getDepotid().intValue();
     List<Map> goodsList = getGoodsList(sellback.getId());

     for (Map goods : goodsList) {
       Integer goodsid = (Integer)goods.get("goods_id");
       Integer storage_num = (Integer)goods.get("storage_num");
       Integer productid = (Integer)goods.get("product_id");
       returned(goodsid.intValue(), storage_num.intValue(), depotid, true, productid);
     }
   }

   private void returned(int goods_id, int num, int depotid, boolean isInner, Integer productid) {
     this.goodsStoreManager.increaseStroe(goods_id, productid.intValue(), depotid, num);
   }

   public List getProduct(int goodsid) {
     String sql = "select product_id,goods_id from es_product p where goods_id=?";
     List list = this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(goodsid) });
     return list;
   }


   public int searchSn(String sn)
   {
     String sql = "select count(0) from es_sellback_list where ordersn=" + sn;
     int num = this.baseDaoSupport.queryForInt(sql, new Object[0]);
     return num;
   }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public IAdminUserManager getAdminUserManager() {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) {
     this.adminUserManager = adminUserManager;
   }

   public IMemberManager getMemberManager() {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }

   public IOrderMetaManager getOrderMetaManager() {
     return this.orderMetaManager;
   }

   public void setOrderMetaManager(IOrderMetaManager orderMetaManager) {
     this.orderMetaManager = orderMetaManager;
   }

   public IMemberPointManger getMemberPointManger() {
     return this.memberPointManger;
   }

   public void setMemberPointManger(IMemberPointManger memberPointManger) {
     this.memberPointManger = memberPointManger;
   }

   public IMemberLvManager getMemberLvManager() {
     return this.memberLvManager;
   }

   public void setMemberLvManager(IMemberLvManager memberLvManager) {
     this.memberLvManager = memberLvManager;
   }

   public IGoodsStoreManager getGoodsStoreManager() {
     return this.goodsStoreManager;
   }

   public void setGoodsStoreManager(IGoodsStoreManager goodsStoreManager) {
     this.goodsStoreManager = goodsStoreManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\SellBackManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */