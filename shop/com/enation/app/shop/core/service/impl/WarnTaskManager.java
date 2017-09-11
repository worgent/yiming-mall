 package com.enation.app.shop.core.service.impl;

 import com.enation.app.shop.core.model.Goods;
 import com.enation.app.shop.core.service.IWarnTaskManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;






 public class WarnTaskManager
   extends BaseSupport
   implements IWarnTaskManager
 {
   public List<Map> listColor(Integer goodsId)
   {
     String sql = "select  pc.productid,pc.color  from " + getTableName("product") + " p left join " + getTableName("product_color") + " pc  on p.product_id=pc.productid where p.goods_id=" + goodsId;
     return this.daoSupport.queryForList(sql, new Object[0]);
   }




   public void saveTask(Map map)
   {
     Goods goods = (Goods)map.get("goods");
     String depotval = map.get("depotval").toString();
     String sphereval = map.get("sphereval").toString();
     String cylinderval = map.get("cylinderval").toString();
     String productval = map.get("productval").toString();
     String[] deptArr = depotval.split(",");
     for (int i = 0; i < deptArr.length; i++) {
       Map tempMap = new HashMap();
       tempMap.put("goodsid", goods.getGoods_id());
       tempMap.put("catid", goods.getCat_id());
       tempMap.put("depotid", deptArr[i]);
       tempMap.put("sphere", sphereval);
       tempMap.put("cylinder", cylinderval);
       tempMap.put("flag", Integer.valueOf(1));
       tempMap.put("productids", productval);
       this.baseDaoSupport.insert("warn_task", tempMap);
     }
   }

   public Page listAll(Integer page, Integer pageSize) {
     String sql = "SELECT d.name as depotname,g.sn,g.name,gc.name as catname,w.* FROM " + getTableName("warn_task") + " w  " + " left join " + getTableName("goods") + " g on w.goodsid = g.goods_id " + " left join " + getTableName("goods_cat") + " gc on w.catid = gc.cat_id " + " left join " + getTableName("depot") + " d on d.id = w.depotid ";



     Page webpage = this.daoSupport.queryForPage(sql, page.intValue(), pageSize.intValue(), new Object[0]);

     List<Map> list = (List)webpage.getResult();
     for (Map map : list) {
       StringBuilder product_color = new StringBuilder("");
       if ((map.get("catid").toString().equals("3")) || (map.get("catid").toString().equals("4")) || (map.get("catid").toString().equals("12")) || (map.get("catid").toString().equals("18"))) {
         String[] productids = map.get("productids").toString().split(",");
         int flag = 0;
         for (String productid : productids) {
           if (flag != 0) {
             product_color.append(",");
           }
           product_color.append(this.baseDaoSupport.queryForString("select color from product_color where productid  =" + productid));
           flag++;
         }
         map.put("color", product_color.toString());
       }

       StringBuilder glasphere = new StringBuilder("");
       if ((map.get("catid").toString().equals("6")) && (!map.get("sphere").equals(""))) {
         String[] spheres = map.get("sphere").toString().split(",");
         String[] sylinderes = map.get("cylinder").toString().split("\\|");
         if ((sylinderes != null) && (sylinderes.length > 0)) {
           for (int i = 0; i < spheres.length; i++) {
             sylinderes[i] = sylinderes[i].substring(0, sylinderes[i].lastIndexOf(","));
             glasphere.append("[度数：" + spheres[i] + ",散光：" + sylinderes[i] + "]");
           }
         }
         map.put("glasses_sphere", glasphere.toString());
       }
     }
     return webpage;
   }


   public Page listdepot(Integer depotId, String name, String sn, int page, int pageSize)
   {
     String sql = "select g.*,b.name as brand_name ,t.name as type_name,c.name as cat_name,wt.id as task_id,wt.productids,wt.sphere,wt.cylinder from " + getTableName("goods") + " g left join " + getTableName("goods_cat") + " c on g.cat_id=c.cat_id left join " + getTableName("brand") + " b on g.brand_id = b.brand_id and b.disabled=0 left join " + getTableName("goods_type") + " t on g.type_id =t.type_id  left join " + getTableName("warn_task") + " wt  on g.goods_id=wt.goodsid " + " where wt.flag=1 and g.goods_type = 'normal' and g.disabled=0 and wt.depotid=" + depotId;







     if ((name != null) && (!name.equals(""))) {
       name = name.trim();
       String[] keys = name.split("\\s");

       for (String key : keys) {
         sql = sql + " and g.name like '%";
         sql = sql + key;
         sql = sql + "%'";
       }
     }

     if ((sn != null) && (!sn.equals(""))) {
       sql = sql + "   and g.sn = '" + sn + "'";
     }

     Page webpage = this.daoSupport.queryForPage(sql.toString(), page, pageSize, new Object[0]);
     List<Map> list = (List)webpage.getResult();
     for (Map map : list) {
       StringBuilder product_color = new StringBuilder("");
       if ((map.get("cat_id").toString().equals("3")) || (map.get("cat_id").toString().equals("4")) || (map.get("cat_id").toString().equals("12")) || (map.get("cat_id").toString().equals("18"))) {
         String[] productids = map.get("productids").toString().split(",");
         int flag = 0;
         for (String productid : productids) {
           if (flag != 0) {
             product_color.append(",");
           }
           product_color.append(this.baseDaoSupport.queryForString("select color from product_color where productid  =" + productid));
           flag++;
         }
         map.put("color", product_color.toString());
       }

       StringBuilder glasphere = new StringBuilder("");
       if ((map.get("cat_id").toString().equals("6")) && (!map.get("sphere").equals(""))) {
         String[] spheres = map.get("sphere").toString().split(",");
         String[] sylinderes = map.get("cylinder").toString().split("\\|");
         for (int i = 0; i < spheres.length; i++) {
           sylinderes[i] = sylinderes[i].substring(0, sylinderes[i].lastIndexOf(","));
           glasphere.append("[度数：" + spheres[i] + ",散光：" + sylinderes[i] + "]");
         }
         map.put("glasses_sphere", glasphere.toString());
       }
     }
     return webpage;
   }

   public Map listTask(Integer taskId)
   {
     String sql = "select wt.* from warn_task wt where wt.id=?";
     return this.baseDaoSupport.queryForMap(sql, new Object[] { taskId });
   }

   public Integer getProductId(Integer goodsid) {
     String sql = "select product_id from product where goods_id = ?";
     Integer productid = Integer.valueOf(this.baseDaoSupport.queryForInt(sql, new Object[] { goodsid }));
     return productid;
   }

   public void updateTask(Integer taskId) {
     this.baseDaoSupport.execute("update warn_task set flag=2 where id=? ", new Object[] { taskId });
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\WarnTaskManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */