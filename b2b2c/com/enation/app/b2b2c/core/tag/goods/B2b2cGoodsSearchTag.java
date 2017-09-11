 package com.enation.app.b2b2c.core.tag.goods;

 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsManager;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;






 @Component
 public class B2b2cGoodsSearchTag
   extends BaseFreeMarkerTag
 {
   private IStoreGoodsManager storeGoodsManager;
   private IGoodsCatManager goodsCatManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String namekeyword = request.getParameter("namekeyword");
     String cat_id = request.getParameter("cat_id");
     String pageNo = request.getParameter("page");
     String search_type = request.getParameter("search_type") == null ? "0" : request.getParameter("search_type");
     int pageSize = 8;
     pageNo = (pageNo == null) || (pageNo.equals("")) ? "1" : pageNo;

     Map map = new HashMap();
     map.put("namekeyword", namekeyword);
     map.put("cat_id", cat_id);
     map.put("search_type", search_type);
     Page page = this.storeGoodsManager.b2b2cGoodsList(Integer.valueOf(Integer.parseInt(pageNo)), Integer.valueOf(pageSize), map);

     Map result = new HashMap();
     result.put("goodsList", page);
     result.put("totalCount", Long.valueOf(page.getTotalCount()));
     result.put("pageSize", Integer.valueOf(pageSize));
     result.put("pageTotalCount", Long.valueOf(page.getTotalPageCount()));
     if (!StringUtil.isEmpty(cat_id)) {
       result.put("cat", this.goodsCatManager.getById(Integer.parseInt(cat_id)));
     }
     if (!StringUtil.isEmpty(namekeyword)) {
       result.put("namekeyword", namekeyword);
     }
     result.put("search_type", search_type);
     return result;
   }

   public IStoreGoodsManager getStoreGoodsManager() { return this.storeGoodsManager; }

   public void setStoreGoodsManager(IStoreGoodsManager storeGoodsManager) {
     this.storeGoodsManager = storeGoodsManager;
   }

   public IGoodsCatManager getGoodsCatManager() { return this.goodsCatManager; }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
     this.goodsCatManager = goodsCatManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\goods\B2b2cGoodsSearchTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */