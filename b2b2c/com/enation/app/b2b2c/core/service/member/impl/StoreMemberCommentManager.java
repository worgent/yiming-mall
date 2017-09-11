 package com.enation.app.b2b2c.core.service.member.impl;

 import com.enation.app.b2b2c.core.model.member.StoreMemberComment;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberCommentManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import java.util.HashMap;
 import java.util.Map;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;



 @Component
 public class StoreMemberCommentManager
   extends BaseSupport
   implements IStoreMemberCommentManager
 {
   public Page getAllComments(int page, int pageSize, Map map, Integer store_id)
   {
     String sql = createTemlSql(map, store_id);
     return this.daoSupport.queryForPage(sql, page, pageSize, new Object[] { store_id });
   }




   public Map get(Integer comment_id)
   {
     return this.daoSupport.queryForMap("SELECT * FROM es_member_comment WHERE comment_id=?", new Object[] { comment_id });
   }




   public void edit(Map map, Integer comment_id)
   {
     this.daoSupport.update("es_member_comment", map, "comment_id=" + comment_id);
   }

   private String createTemlSql(Map map, Integer store_id) {
     StringBuffer sql = new StringBuffer();
     sql.append("SELECT m.uname as uname,g.name as goods_name,c.* FROM es_member_comment c LEFT JOIN es_goods g ON c.goods_id=g.goods_id LEFT JOIN es_member m ON c.member_id=m.member_id WHERE g.store_id=? and c.type=" + map.get("type"));

     if (map.get("stype") != null) {
       if (map.get("stype").equals("0")) {
         if (map.get("keyword") != null) {
           sql.append(" and (m.uname like '%" + map.get("keyword") + "%'");
           sql.append(" or c.content like '%" + map.get("keyword") + "%'");
           sql.append(" or g.name like '%" + map.get("keyword") + "%')");
         }
       } else {
         if (map.get("mname") != null) {
           sql.append(" and m.uname like '%" + map.get("mname") + "%'");
         }
         if (map.get("gname") != null) {
           sql.append(" and g.name like '%" + map.get("gname") + "%'");
         }
         if (map.get("content") != null) {
           sql.append(" and c.content like '%" + map.get("content") + "%'");
         }
       }
       if (map.get("status") != null) {
         sql.append(" and c.status=" + map.get("status"));
       }
       if ((map.get("replyStatus") != null) && (!map.get("replyStatus").equals("0"))) {
         sql.append(" and c.replystatus =" + map.get("replyStatus"));
       }
     }
     if ((map.get("grade") != null) &&
       (Integer.parseInt(map.get("grade").toString()) != -1)) {
       sql.append(" and c.grade=" + map.get("grade"));
     }
     sql.append(" ORDER BY c.comment_id DESC");

     return sql.toString();
   }





   @Transactional(propagation=Propagation.REQUIRED)
   public void add(StoreMemberComment memberComment)
   {
     this.daoSupport.insert("es_member_comment", memberComment);

     updateStoreComment(memberComment);
   }




   private void updateStoreComment(StoreMemberComment memberComment)
   {
     String sql = "select (SELECT COUNT(comment_id)from es_member_comment WHERE grade=3)as grade,sum(store_desccredit) as store_desccredit ,SUM(store_servicecredit)as store_servicecredit,SUM(store_deliverycredit) as store_deliverycredit ,COUNT(comment_id) as comment_num from es_member_comment WHERE store_id=?";
     Map map = this.daoSupport.queryForMap(sql, new Object[] { Integer.valueOf(memberComment.getStore_id()) });
     Integer grade = Integer.valueOf(Integer.parseInt(map.get("grade").toString()));
     Double store_desccredit = Double.valueOf(Double.parseDouble(map.get("store_desccredit").toString()));
     Double store_servicecredit = Double.valueOf(Double.parseDouble(map.get("store_servicecredit").toString()));
     Double store_deliverycredit = Double.valueOf(Double.parseDouble(map.get("store_deliverycredit").toString()));
     int comment_num = Integer.parseInt(map.get("comment_num").toString());

     Map storeInfo = new HashMap();

     storeInfo.put("praise_rate", Integer.valueOf(grade.intValue() / comment_num));
     storeInfo.put("store_desccredit", Double.valueOf(store_desccredit.doubleValue() / comment_num));
     storeInfo.put("store_servicecredit", Double.valueOf(store_servicecredit.doubleValue() / comment_num));
     storeInfo.put("store_deliverycredit", Double.valueOf(store_deliverycredit.doubleValue() / comment_num));
     this.daoSupport.update("es_store", storeInfo, "store_id=" + memberComment.getStore_id());
   }




   public Integer getCommentCount(Integer type, Integer store_id)
   {
     String sql = "SELECT count(0) from es_member_comment c WHERE c.type=? AND c.replystatus=0 and store_id=?";
     return Integer.valueOf(this.daoSupport.queryForInt(sql, new Object[] { type, store_id }));
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\member\impl\StoreMemberCommentManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */