 package com.enation.app.shop.component.spec.tag;

 import com.enation.app.shop.core.model.Product;
 import com.enation.app.shop.core.model.Specification;
 import com.enation.app.shop.core.service.IProductManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;














 @Component
 @Scope("prototype")
 public class GoodsSpecTag
   extends BaseFreeMarkerTag
 {
   private IProductManager productManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = getRequest();
     Map goods = (Map)request.getAttribute("goods");
     if (goods == null) throw new TemplateModelException("调用商规格标签前，必须先调用商品基本信息标签");
     Integer goods_id = (Integer)goods.get("goods_id");
     List<Product> productList = this.productManager.list(goods_id);
     Map data = new HashMap();
     if (("" + goods.get("have_spec")).equals("0")) {
       data.put("productid", ((Product)productList.get(0)).getProduct_id());
       data.put("productList", productList);
     } else {
       List<Specification> specList = this.productManager.listSpecs(goods_id);
       data.put("specList", specList);
       data.put("productList", productList);
     }
     data.put("have_spec", goods.get("have_spec"));
     return data;
   }

   public IProductManager getProductManager() { return this.productManager; }

   public void setProductManager(IProductManager productManager) {
     this.productManager = productManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\spec\tag\GoodsSpecTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */