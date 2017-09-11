 package com.enation.app.shop.component.receipt;

 import java.io.Serializable;


 public class Receipt
   implements Serializable
 {
   private Integer id;
   private Integer order_id;
   private String title;
   private Long add_time;
   private String content;
   private int status;

   public Integer getId()
   {
     return this.id;
   }

   public void setId(Integer id) { this.id = id; }

   public Integer getOrder_id() {
     return this.order_id;
   }

   public void setOrder_id(Integer order_id) { this.order_id = order_id; }

   public String getTitle() {
     return this.title;
   }

   public void setTitle(String title) { this.title = title; }

   public Long getAdd_time() {
     return this.add_time;
   }

   public void setAdd_time(Long add_time) { this.add_time = add_time; }

   public String getContent() {
     return this.content;
   }

   public void setContent(String content) { this.content = content; }

   public int getStatus() {
     return this.status;
   }

   public void setStatus(int status) { this.status = status; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\receipt\Receipt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */