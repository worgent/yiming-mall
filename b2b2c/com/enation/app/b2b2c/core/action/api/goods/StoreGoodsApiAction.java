 package com.enation.app.b2b2c.core.action.api.goods;

 import com.enation.app.b2b2c.core.model.goods.StoreGoods;
 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.IStoreTemplateManager;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.util.JsonMessageUtil;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.stereotype.Component;












 @ParentPackage("eop_default")
 @Namespace("/api/b2b2c")
 @Action("goods")
 @Component
 public class StoreGoodsApiAction
   extends WWAction
 {
   private IGoodsManager goodsManager;
   private IOrderManager orderManager;
   private IStoreGoodsManager storeGoodsManager;
   private IStoreTemplateManager storeTemplateManager;
   private IStoreMemberManager storeMemberManager;
   private StoreGoods storeGoods;
   private Integer[] goods_id;
   private Integer productid;
   private Integer store;
   private Integer storeid;

   public String add()
   {
     try
     {
       StoreMember storeMember = this.storeMemberManager.getStoreMember();

       this.storeGoods.setStore_id(storeMember.getStore_id());

       if (this.storeGoods.getGoods_transfee_charge().intValue() == 0) {
         Integer tempid = this.storeTemplateManager.getDefTempid(storeMember.getStore_id());
         if (tempid == null) {
           showErrorJson("店铺未设置默认配送模板，不能设置【买家承担运费】。");
           return "json_message";
         }
       }

       this.goodsManager.add(this.storeGoods);
       showSuccessJson("商品添加成功");
     } catch (Exception e) {
       showErrorJson("商品添加失败:" + e.getMessage());
       this.logger.error(e);
     }
     return "json_message";
   }





   public String edit()
   {
     try
     {
       this.goodsManager.edit(this.storeGoods);
       showSuccessJson("商品修改成功");
     } catch (Exception e) {
       showErrorJson("商品修改失败:" + e.getMessage());
       this.logger.error(e);
     }
     return "json_message";
   }




   public String deleteGoods()
   {
     try
     {
       if (this.goods_id != null) {
         this.goodsManager.delete(this.goods_id);
         showSuccessJson("商品添加至回收站成功");
       } else {
         showErrorJson("请选择商品");
       }
     } catch (Exception e) {
       showErrorJson("商品添加至回收站失败");
       this.logger.error(e);
     }
     return "json_message";
   }




   public String cleanGoods()
   {
     try
     {
       if (this.goods_id != null) {
         this.goodsManager.clean(this.goods_id);
         showSuccessJson("清除商品成功");
       } else {
         showErrorJson("请选择商品");
       }
     } catch (Exception e) {
       showErrorJson("清除商品失败");
       this.logger.error(e);
     }
     return "json_message";
   }





   public String checkProInOrder()
   {
     boolean isinorder = this.orderManager.checkProInOrder(this.productid.intValue());
     if (isinorder) {
       showErrorJson("此货品已经有顾客购买，如果删除此订单将不能配货发货，请谨慎操作!\n点击确定删除此货品，点击取消保留此货品。");
     } else {
       showSuccessJson("删除吧");
     }
     return "json_message";
   }





   public String revertGoods()
   {
     try
     {
       if (this.goods_id != null) {
         this.goodsManager.revert(this.goods_id);
         showSuccessJson("还原成功");
       } else {
         showErrorJson("请选择商品");
       }
     } catch (RuntimeException e) {
       showErrorJson("还原失败");
       this.logger.error("商品还原失败", e);
     }
     return "json_message";
   }







   public String saveGoodsStore()
   {
     try
     {
       this.storeGoodsManager.saveGoodsStore(this.storeid, this.goods_id[0], this.store);
       showSuccessJson("保存库存成功");
     } catch (Exception e) {
       showErrorJson("保存库存失败");
       this.logger.error("保存库存失败:" + e);
     }
     return "json_message";
   }







   public String search()
   {
     try
     {
       HttpServletRequest request = getRequest();
       String keyword = request.getParameter("keyword");
       String store_catid = request.getParameter("store_catid");

       StoreMember storeMember = this.storeMemberManager.getStoreMember();
       if (storeMember == null) {
         showErrorJson("尚未登陆，不能使用此API");
         return "json_message";
       }


       Map params = new HashMap();
       params.put("keyword", keyword);
       params.put("store_catid", store_catid);
       List<Map> goodsList = this.storeGoodsManager.storeGoodsList(storeMember.getStore_id().intValue(), params);
       this.json = JsonMessageUtil.getListJson(goodsList);
     }
     catch (Exception e) {
       showErrorJson("api调用失败" + e.getMessage());
       this.logger.error("商品搜索出错", e);
     }


     return "json_message";
   }


   public StoreGoods getStoreGoods()
   {
     return this.storeGoods;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager)
   {
     this.storeMemberManager = storeMemberManager;
   }

   public void setStoreGoods(StoreGoods storeGoods) {
     this.storeGoods = storeGoods;
   }

   public IGoodsManager getGoodsManager() { return this.goodsManager; }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }

   public Integer[] getGoods_id() { return this.goods_id; }

   public void setGoods_id(Integer[] goods_id) {
     this.goods_id = goods_id;
   }

   public IOrderManager getOrderManager() { return this.orderManager; }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public Integer getProductid() { return this.productid; }

   public void setProductid(Integer productid) {
     this.productid = productid;
   }

   public IStoreGoodsManager getStoreGoodsManager() { return this.storeGoodsManager; }

   public void setStoreGoodsManager(IStoreGoodsManager storeGoodsManager) {
     this.storeGoodsManager = storeGoodsManager;
   }

   public Integer getStore() { return this.store; }

   public void setStore(Integer store) {
     this.store = store;
   }

   public Integer getStoreid() { return this.storeid; }

   public void setStoreid(Integer storeid) {
     this.storeid = storeid;
   }

   public IStoreTemplateManager getStoreTemplateManager() { return this.storeTemplateManager; }

   public void setStoreTemplateManager(IStoreTemplateManager storeTemplateManager) {
     this.storeTemplateManager = storeTemplateManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\goods\StoreGoodsApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */