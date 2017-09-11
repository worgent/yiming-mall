 package com.enation.app.shop.core.service.impl;

 import com.enation.app.shop.core.model.MemberComment;
 import com.enation.app.shop.core.service.IMemberCommentManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.StringUtil;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;







 public class MemberCommentManager
   extends BaseSupport<MemberComment>
   implements IMemberCommentManager
 {
   public void add(MemberComment memberComment)
   {
     this.baseDaoSupport.insert("member_comment", memberComment);
   }



   public Page getGoodsComments(int goods_id, int page, int pageSize, int type)
   {
     return this.daoSupport.queryForPage("SELECT m.lv_id,m.sex,m.uname,m.face,l.name as levelname,c.* FROM " + getTableName("member_comment") + " c LEFT JOIN " + getTableName("member") + " m ON c.member_id=m.member_id LEFT JOIN " + getTableName("member_lv") + " l ON m.lv_id=l.lv_id " + "WHERE c.goods_id=? AND c.type=? AND c.status=1 ORDER BY c.comment_id DESC", page, pageSize, new Object[] { Integer.valueOf(goods_id), Integer.valueOf(type) });
   }

   public int getGoodsGrade(int goods_id)
   {
     int sumGrade = this.baseDaoSupport.queryForInt("SELECT SUM(grade) FROM member_comment WHERE status=1 AND goods_id=? AND type=1", new Object[] { Integer.valueOf(goods_id) });
     int total = this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM member_comment WHERE status=1 AND goods_id=? AND type=1", new Object[] { Integer.valueOf(goods_id) });
     if ((sumGrade != 0) && (total != 0)) {
       return sumGrade / total;
     }
     return 0;
   }

   public Page getAllComments(int page, int pageSize, int type)
   {
     String sql = "SELECT m.uname muname,g.name gname,c.* FROM " + getTableName("member_comment") + " c LEFT JOIN " + getTableName("goods") + " g ON c.goods_id=g.goods_id LEFT JOIN " + getTableName("member") + " m ON c.member_id=m.member_id " + "WHERE c.type=? ORDER BY c.comment_id DESC";


     return this.daoSupport.queryForPage(sql, page, pageSize, new Object[] { Integer.valueOf(type) });
   }

   public MemberComment get(int comment_id)
   {
     return (MemberComment)this.baseDaoSupport.queryForObject("SELECT * FROM member_comment WHERE comment_id=?", MemberComment.class, new Object[] { Integer.valueOf(comment_id) });
   }




   public void update(MemberComment memberComment)
   {
     this.baseDaoSupport.update("member_comment", memberComment, "comment_id=" + memberComment.getComment_id());
   }

   public Map statistics(int goodsid)
   {
     String sql = "select grade,count(grade) num from es_member_comment c where c.goods_id =? GROUP BY c.grade ";
     List<Map> gradeList = this.daoSupport.queryForList(sql, new Object[] { Integer.valueOf(goodsid) });
     Map gradeMap = new HashMap();
     for (Map grade : gradeList) {
       Object gradeValue = grade.get("grade");
       long num = Long.parseLong(grade.get("num").toString().trim());
       gradeMap.put("grade_" + gradeValue, Long.valueOf(num));
     }
     return gradeMap;
   }

   public int getGoodsCommentsCount(int goods_id)
   {
     return this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM member_comment WHERE goods_id=? AND status=1 AND type=1", new Object[] { Integer.valueOf(goods_id) });
   }

   public void delete(Integer[] comment_id)
   {
     if ((comment_id == null) || (comment_id.equals("")))
       return;
     String id_str = StringUtil.arrayToString(comment_id, ",");
     String sql = "DELETE FROM member_comment where comment_id in (" + id_str + ")";
     this.baseDaoSupport.execute(sql, new Object[0]);
   }



   public Page getMemberComments(int page, int pageSize, int type, int member_id)
   {
     return this.daoSupport.queryForPage("SELECT g.name,g.cat_id, c.* FROM " + getTableName("member_comment") + " c LEFT JOIN " + getTableName("goods") + " g ON c.goods_id=g.goods_id " + "WHERE c.type=? AND c.member_id=? ORDER BY c.comment_id DESC", page, pageSize, new Object[] { Integer.valueOf(type), Integer.valueOf(member_id) });
   }


   public int getMemberCommentTotal(int member_id, int type)
   {
     return this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM member_comment WHERE member_id=? AND type=?", new Object[] { Integer.valueOf(member_id), Integer.valueOf(type) });
   }

   public Page getCommentsByStatus(int page, int pageSize, int type, int status)
   {
     return this.daoSupport.queryForPage("SELECT m.uname,g.name,c.* FROM " + getTableName("member_comment") + " c LEFT JOIN " + getTableName("goods") + " g ON c.goods_id=g.goods_id LEFT JOIN " + getTableName("member") + " m ON c.member_id=m.member_id " + "WHERE c.type=? and c.status = ? ORDER BY c.comment_id DESC", page, pageSize, new Object[] { Integer.valueOf(type), Integer.valueOf(status) });
   }






   public void deletealone(int comment_id)
   {
     this.baseDaoSupport.execute("DELETE FROM member_comment WHERE comment_id=?", new Object[] { Integer.valueOf(comment_id) });
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\MemberCommentManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */