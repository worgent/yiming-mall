 package com.enation.app.shop.core.service.impl;

 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.model.mapper.CatMapper;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.util.StringUtil;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.log4j.Logger;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;

 public class GoodsCatManager extends BaseSupport<Cat> implements IGoodsCatManager
 {
   public boolean checkname(String name, Integer catid)
   {
     if (name != null) name = name.trim();
     String sql = "select count(0) from goods_cat where name=? and cat_id!=?";
     if (catid == null) {
       catid = Integer.valueOf(0);
     }

     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { name, catid });
     if (count > 0) return true;
     return false;
   }

   public int delete(int catId) {
     String sql = "select count(0) from goods_cat where parent_id = ?";
     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(catId) });
     if (count > 0) {
       return 1;
     }

     sql = "select count(0) from goods where cat_id = ?";
     count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(catId) });
     if (count > 0) {
       return 2;
     }
     sql = "delete from  goods_cat   where cat_id=?";
     this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(catId) });

     return 0;
   }



   public Cat getById(int catId)
   {
     String sql = "select * from goods_cat  where cat_id=?";
     Cat cat = (Cat)this.baseDaoSupport.queryForObject(sql, Cat.class, new Object[] { Integer.valueOf(catId) });
     if (cat != null) {
       String image = cat.getImage();
       if (image != null) {
         image = UploadUtil.replacePath(image);
         cat.setImage(image);
       }
     }
     return cat;
   }


   public List<Cat> listChildren(Integer catId)
   {
     String sql = "select c.*,'' type_name from goods_cat c where parent_id=?";
     return this.baseDaoSupport.queryForList(sql, new CatMapper(), new Object[] { catId });
   }



   public List<Cat> listAllChildren(Integer catId)
   {
     String tableName = getTableName("goods_cat");
     String sql = "select c.*,t.name as type_name  from  " + tableName + " c  left join " + getTableName("goods_type") + " t on c.type_id = t.type_id  " + " order by parent_id,cat_order";




     List<Cat> allCatList = this.daoSupport.queryForList(sql, new CatMapper(), new Object[0]);
     List<Cat> topCatList = new ArrayList();
     if (catId.intValue() != 0) {
       Cat cat = getById(catId.intValue());
       topCatList.add(cat);
     }
     for (Cat cat : allCatList) {
       if (cat.getParent_id().compareTo(catId) == 0) {
         if (this.logger.isDebugEnabled()) {
           this.logger.debug("发现子[" + cat.getName() + "-" + cat.getCat_id() + "]" + cat.getImage());
         }
         List<Cat> children = getChildren(allCatList, cat.getCat_id());

         int i = this.baseDaoSupport.queryForInt("select count(0) from es_goods_cat where parent_id=" + cat.getCat_id(), new Object[0]);
         if (i != 0) {
           cat.setState("closed");
         }
         cat.setChildren(children);
         topCatList.add(cat);
       }
     }
     return topCatList;
   }

   private List<Cat> getChildren(List<Cat> catList, Integer parentid)
   {
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("查找[" + parentid + "]的子");
     }
     List<Cat> children = new ArrayList();
     for (Cat cat : catList) {
       if (cat.getParent_id().compareTo(parentid) == 0) {
         if (this.logger.isDebugEnabled()) {
           this.logger.debug(cat.getName() + "-" + cat.getCat_id() + "是子");
         }
         cat.setChildren(getChildren(catList, cat.getCat_id()));
         children.add(cat);
       }
     }
     return children;
   }


   @Transactional(propagation=Propagation.REQUIRED)
   public void saveAdd(Cat cat)
   {
     this.baseDaoSupport.insert("goods_cat", cat);
     int cat_id = this.baseDaoSupport.getLastId("goods_cat");
     String sql = "";


     if ((cat.getParent_id() != null) && (cat.getParent_id().intValue() != 0)) {
       sql = "select * from goods_cat  where cat_id=?";
       Cat parent = (Cat)this.baseDaoSupport.queryForObject(sql, Cat.class, new Object[] { cat.getParent_id() });

       cat.setCat_path(parent.getCat_path() + cat_id + "|");
     } else {
       cat.setCat_path(cat.getParent_id() + "|" + cat_id + "|");

       cat.setParent_id(Integer.valueOf(0));
     }

     sql = "update goods_cat set  cat_path=?,parent_id=?  where  cat_id=?";
     this.baseDaoSupport.execute(sql, new Object[] { cat.getCat_path(), cat.getParent_id(), Integer.valueOf(cat_id) });
   }



   @Transactional(propagation=Propagation.REQUIRED)
   public void update(Cat cat)
   {
     checkIsOwner(cat.getCat_id());

     if ((cat.getParent_id() != null) && (cat.getParent_id().intValue() != 0))
     {
       String sql = "select * from goods_cat where cat_id=?";
       Cat parent = (Cat)this.baseDaoSupport.queryForObject(sql, Cat.class, new Object[] { cat.getParent_id() });
       cat.setCat_path(parent.getCat_path() + cat.getCat_id() + "|");
     }
     else
     {
       cat.setCat_path(cat.getParent_id() + "|" + cat.getCat_id() + "|");
     }

     HashMap map = new HashMap();
     map.put("name", cat.getName());
     map.put("parent_id", cat.getParent_id());
     map.put("cat_order", Integer.valueOf(cat.getCat_order()));
     map.put("type_id", cat.getType_id());
     map.put("cat_path", cat.getCat_path());
     map.put("list_show", cat.getList_show());
     map.put("image", StringUtil.isEmpty(cat.getImage()) ? null : cat.getImage());
     this.baseDaoSupport.update("goods_cat", map, "cat_id=" + cat.getCat_id());


     List<Map> childList = this.baseDaoSupport.queryForList("select * from es_goods_cat where parent_id=?", new Object[] { cat.getCat_id() });
     if ((childList != null) && (childList.size() > 0)) {
       for (Map maps : childList) {
         Integer cat_id = (Integer)maps.get("cat_id");
         Map childmap = new HashMap();
         childmap.put("cat_path", cat.getCat_path() + cat_id + "|");
         this.baseDaoSupport.update("goods_cat", childmap, " cat_id=" + cat_id);
       }
     }
   }







   protected void checkIsOwner(Integer catId) {}






   public void saveSort(int[] cat_ids, int[] cat_sorts)
   {
     String sql = "";
     if (cat_ids != null) {
       for (int i = 0; i < cat_ids.length; i++) {
         sql = "update  goods_cat  set cat_order=? where cat_id=?";
         this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(cat_sorts[i]), Integer.valueOf(cat_ids[i]) });
       }
     }
   }

   public List getNavpath(int catId)
   {
     return null;
   }


   public List<Cat> getParents(int catid)
   {
     Cat cat = getById(catid);
     String path = cat.getCat_path();
     path = path.substring(0, path.length() - 1);
     path = path.replace("|", ",");
     List lists = new ArrayList();
     getParent(Integer.valueOf(catid), lists);

     List list = new ArrayList();
     for (int i = lists.size() - 1; i >= 0; i--) {
       Cat c = (Cat)lists.get(i);
       list.add(c);
     }
     return list;
   }

   private List getParent(Integer catid, List ls) {
     if (catid != null) {
       String sql = "select cat_id,name,parent_id,type_id from goods_cat where cat_id=" + catid;
       List<Cat> list = this.baseDaoSupport.queryForList(sql, Cat.class, new Object[0]);
       if (!list.isEmpty()) {
         for (Cat cat : list) {
           ls.add(cat);
           getParent(cat.getParent_id(), ls);
         }
       }
     }
     return ls;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\GoodsCatManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */