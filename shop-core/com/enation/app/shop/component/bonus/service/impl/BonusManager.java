 package com.enation.app.shop.component.bonus.service.impl;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.component.bonus.model.BonusType;
 import com.enation.app.shop.component.bonus.model.MemberBonus;
 import com.enation.app.shop.component.bonus.service.IBonusManager;
 import com.enation.app.shop.component.bonus.service.IBonusTypeManager;
 import com.enation.app.shop.core.model.Goods;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.ExcelUtil;
 import com.enation.framework.util.StringUtil;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Random;
 import java.util.Set;
 import org.apache.log4j.Logger;
 import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;








 @Component
 public class BonusManager
   extends BaseSupport
   implements IBonusManager
 {
   private IBonusTypeManager bonusTypeManager;
   private SimpleJdbcTemplate simpleJdbcTemplate;
   private IMemberManager memberManager;

   private void updateNum(int typeid, int count)
   {
     this.baseDaoSupport.execute("update bonus_type set create_num=? where type_id=?", new Object[] { Integer.valueOf(count), Integer.valueOf(typeid) });
   }

   private void increaseNum(int typeid, int count) { this.baseDaoSupport.execute("update bonus_type set create_num=create_num+? where type_id=?", new Object[] { Integer.valueOf(count), Integer.valueOf(typeid) }); }


   @Transactional(propagation=Propagation.REQUIRED)
   public int sendForMemberLv(int typeid, int lvid, int onlyEmailChecked)
   {
     String sql = "select * from member where lv_id=? ";
     if (onlyEmailChecked == 1) {
       sql = sql + " and is_checked=1";
     }
     List<Member> mebmerList = this.baseDaoSupport.queryForList(sql, Member.class, new Object[] { Integer.valueOf(lvid) });

     int count = 0;
     BonusType bonusType = this.bonusTypeManager.get(typeid);

     count = send(mebmerList, typeid, bonusType.getType_name(), bonusType.getSend_type());
     increaseNum(typeid, count);
     return count;
   }


   private int send(List<Member> mebmerList, int typeid, String type_name, int bonus_type)
   {
     int count = 0;

     for (Member member : mebmerList) {
       Map memberBonus = new HashMap();
       memberBonus.put("bonus_type_id", Integer.valueOf(typeid));
       memberBonus.put("member_id", member.getMember_id());
       memberBonus.put("type_name", type_name);
       memberBonus.put("bonus_type", Integer.valueOf(bonus_type));
       memberBonus.put("member_name", member.getUname() + "[" + member.getName() + "]");
       memberBonus.put("emailed", Integer.valueOf(0));
       memberBonus.put("create_time", Long.valueOf(DateUtil.getDatelineLong()));
       this.baseDaoSupport.insert("member_bonus", memberBonus);
       count++;
     }
     return count;
   }






   @Transactional(propagation=Propagation.REQUIRED)
   public int sendForMember(int typeid, Integer[] memberids)
   {
     if ((memberids == null) || (memberids.length == 0)) { return 0;
     }
     String sql = "select * from member where member_id in(" + StringUtil.arrayToString(memberids, ",") + ") ";
     List<Member> mebmerList = this.baseDaoSupport.queryForList(sql, Member.class, new Object[0]);
     int count = 0;
     BonusType bonusType = this.bonusTypeManager.get(typeid);
     count = send(mebmerList, typeid, bonusType.getType_name(), bonusType.getSend_type());
     increaseNum(typeid, count);
     return count;
   }






   @Transactional(propagation=Propagation.REQUIRED)
   public int sendForGoods(int typeid, Integer[] goodsids)
   {
     if ((goodsids == null) || (goodsids.length == 0)) return 0;
     this.baseDaoSupport.execute("delete from bonus_goods where bonus_type_id=?", new Object[] { Integer.valueOf(typeid) });
     String sql = "select * from goods where goods_id in(" + StringUtil.arrayToString(goodsids, ",") + ")";
     List<Goods> goodsList = this.baseDaoSupport.queryForList(sql, Goods.class, new Object[0]);
     for (Goods goods : goodsList) {
       this.baseDaoSupport.execute("insert into bonus_goods(bonus_type_id,goods_id)values(?,?)", new Object[] { Integer.valueOf(typeid), goods.getGoods_id() });
     }
     int count = goodsList.size();
     updateNum(typeid, count);
     return count;
   }


   @Transactional(propagation=Propagation.REQUIRED)
   public int sendForOffLine(int typeid, int count)
   {
     Set<String> cardNoSet = new HashSet();
     int successCount = 0;
     int i = 0;
     try {
       BonusType bronusType = this.bonusTypeManager.get(typeid);
       String prefix = bronusType.getRecognition();

       int step = 10;
       if (count > 1000) {
         step = 100;
       }

       Set<MemberBonus> bronusSet = new HashSet();

       while (cardNoSet.size() < count) {
         String sn = createSn(prefix);
         int c = this.baseDaoSupport.queryForInt("select count(0) from member_bonus where bonus_sn=?", new Object[] { sn });
         if ((!cardNoSet.contains(sn)) && (c <= 0))
         {

           cardNoSet.add(sn);

           MemberBonus bronus = new MemberBonus();
           bronus.setBonus_type_id(typeid);
           bronus.setBonus_sn(sn);
           bronusSet.add(bronus);

           if (bronusSet.size() >= step) {
             batchCreate(bronusSet, bronusType);
             int size = bronusSet.size();
             successCount += size;
             bronusSet.clear();
           }
           i++;
         }
       }
       if (bronusSet.size() >= 0) {
         batchCreate(bronusSet, bronusType);
         int size = bronusSet.size();
         successCount += size;
         bronusSet.clear();
       }
     }
     catch (Throwable e) {
       this.logger.error("生成个优惠卷第[" + i + "]出错，已生成[" + successCount + "]个", e);
     }

     increaseNum(typeid, successCount);

     return successCount;
   }





   private void batchCreate(Set<MemberBonus> bronusSet, BonusType bronusType)
   {
     List list = new ArrayList();
     Iterator<MemberBonus> itor = bronusSet.iterator();

     while (itor.hasNext()) {
       MemberBonus bronus = (MemberBonus)itor.next();
       Object[] params = new Object[5];
       params[0] = Integer.valueOf(bronus.getBonus_type_id());
       params[1] = bronus.getBonus_sn();
       params[2] = bronusType.getType_name();
       params[3] = Integer.valueOf(bronusType.getSend_type());
       params[4] = Long.valueOf(DateUtil.getDatelineLong());
       list.add(params);
     }
     this.simpleJdbcTemplate.batchUpdate("insert into es_member_bonus(bonus_type_id,bonus_sn,type_name,bonus_type,create_time,emailed)values(?,?,?,?,?,0)", list);
   }


   private String createSn(String prefix)
   {
     StringBuffer sb = new StringBuffer();
     sb.append(prefix);
     sb.append(DateUtil.toString(new Date(), "yyMM"));
     sb.append(createRandom());

     return sb.toString();
   }

   private String createRandom() {
     Random random = new Random();
     StringBuffer pwd = new StringBuffer();
     for (int i = 0; i < 6; i++) {
       pwd.append(random.nextInt(9));
     }

     return pwd.toString();
   }

   public static void main(String[] args) {
     long i = 1380211200L;
   }



   public Page list(int page, int pageSize, int typeid)
   {
     String sql = "select * from es_member_bonus where bonus_type_id=? order by bonus_id asc";
     Page webPage = this.baseDaoSupport.queryForPage(sql, page, pageSize, MemberBonus.class, new Object[] { Integer.valueOf(typeid) });
     return webPage;
   }

   public List<Map> getMemberBonusList(int memberid, Double goodsprice)
   {
     String sql = "select mb.bonus_id,bt.* from es_bonus_type bt,es_member_bonus mb where  bt.type_id=mb.bonus_type_id and mb.member_id=?  and bt.min_goods_amount<=?";


     int now = DateUtil.getDateline();
     sql = sql + " and bt.use_start_date<=" + now;
     sql = sql + " and bt.use_end_date>=" + now;
     sql = sql + " order by bonus_id asc";

     return this.daoSupport.queryForList(sql, new Object[] { Integer.valueOf(memberid), goodsprice });
   }


   public void delete(int bronusid)
   {
     this.baseDaoSupport.execute("delete from member_bonus where bonus_id=?", new Object[] { Integer.valueOf(bronusid) });
   }

   public List<Map> getGoodsList(int typeid)
   {
     String sql = "select g.goods_id,g.name from " + getTableName("goods") + " g ," + getTableName("bonus_goods") + " bg where bg.goods_id =g.goods_id and bg.bonus_type_id=?";
     return this.daoSupport.queryForList(sql, new Object[] { Integer.valueOf(typeid) });
   }



   public String exportToExcel(int typeid)
   {
     try
     {
       BonusType bonusType = this.bonusTypeManager.get(typeid);
       String sql = "select * from member_bonus where bonus_type_id=? order by bonus_id asc";
       List<MemberBonus> list = this.baseDaoSupport.queryForList(sql, MemberBonus.class, new Object[] { Integer.valueOf(typeid) });

       ExcelUtil excelUtil = new ExcelUtil();

       InputStream in = new FileInputStream(new File(EopSetting.EOP_PATH + "/excel/bonus.xls"));

       excelUtil.openModal(in);
       int i = 1;
       for (MemberBonus memberBonus : list) {
         excelUtil.writeStringToCell(i, 0, memberBonus.getBonus_sn());
         excelUtil.writeStringToCell(i, 1, bonusType.getType_money().toString());
         excelUtil.writeStringToCell(i, 2, bonusType.getType_name());
         long time = bonusType.getUse_end_date();
         excelUtil.writeStringToCell(i, 3, DateUtil.toString(new Date(time * 1000L), "yyyy年MM月dd"));
         i++;
       }
       String filePath = EopSetting.IMG_SERVER_PATH + "/bouns_excel/" + typeid + ".xls";
       excelUtil.writeToFile(filePath);
       return filePath;
     } catch (Exception e) {
       e.printStackTrace(); }
     return null;
   }


   public Page pageList(int page, int pageSize, int memberid)
   {
     Page pages = this.daoSupport.queryForPage("select bo.*,botype.type_id,botype.min_amount,botype.type_money,botype.use_end_date from es_member_bonus bo ,es_bonus_type botype where  bo.bonus_type_id =botype.type_id and member_id=? ", page, pageSize, new Object[] { Integer.valueOf(memberid) });
     return pages;
   }




   public MemberBonus getBonus(int bounusid)
   {
     String sql = "select mb.*,bt.type_money bonus_money,bt.min_goods_amount,bt.use_start_date,bt.use_end_date  from " + getTableName("member_bonus") + " mb , " + getTableName("bonus_type") + " bt where  bt.type_id=mb.bonus_type_id and mb.bonus_id=? ";



     return (MemberBonus)this.daoSupport.queryForObject(sql, MemberBonus.class, new Object[] { Integer.valueOf(bounusid) });
   }

   public MemberBonus getBonus(String sn)
   {
     String sql = "select mb.*,bt.type_money bonus_money,bt.min_goods_amount,bt.use_start_date,bt.use_end_date  from " + getTableName("member_bonus") + " mb , " + getTableName("bonus_type") + " bt where  bt.type_id=mb.bonus_type_id and mb.bonus_sn=? ";


     return (MemberBonus)this.daoSupport.queryForObject(sql, MemberBonus.class, new Object[] { sn });
   }


   @Transactional(propagation=Propagation.REQUIRED)
   public void use(int bonusid, int memberid, int orderid, String ordersn, int bonus_type_id)
   {
     Member member = this.memberManager.get(Integer.valueOf(memberid));

     String sql = "update member_bonus set order_id=?,order_sn=?,member_id=?,used_time=?,member_name=? where bonus_id=?";
     this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(orderid), ordersn, Integer.valueOf(memberid), Integer.valueOf(DateUtil.getDateline()), member.getUname() + "-" + member.getName(), Integer.valueOf(bonusid) });

     this.baseDaoSupport.execute("update es_bonus_type set use_num=use_num+1 where type_id=?", new Object[] { Integer.valueOf(bonus_type_id) });
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void returned(int orderid)
   {
     String sql = "update member_bonus set order_sn=null,used_time=null,order_id=null,member_name=null  where order_id=?";
     this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(orderid) });
   }


   public IBonusTypeManager getBonusTypeManager()
   {
     return this.bonusTypeManager;
   }

   public void setBonusTypeManager(IBonusTypeManager bonusTypeManager) {
     this.bonusTypeManager = bonusTypeManager;
   }

   public SimpleJdbcTemplate getSimpleJdbcTemplate() {
     return this.simpleJdbcTemplate;
   }

   public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
     this.simpleJdbcTemplate = simpleJdbcTemplate;
   }

   public IMemberManager getMemberManager() { return this.memberManager; }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\bonus\service\impl\BonusManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */