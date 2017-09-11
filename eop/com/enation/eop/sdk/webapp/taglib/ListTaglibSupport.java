 package com.enation.eop.sdk.webapp.taglib;

 import java.util.Iterator;
 import java.util.List;
 import javax.servlet.ServletRequest;
 import javax.servlet.jsp.JspException;
 import javax.servlet.jsp.PageContext;

 public abstract class ListTaglibSupport extends BaseTaglibSupport
 {
   private boolean isFirst = true;

   protected String item;

   protected IListTaglibParam param;

   protected IListTaglibProvider tagProvider;

   private Iterator listIterator;

   private boolean hasNext = false;

   private int index;

   protected String postStart()
   {
     return "";
   }

   protected String postEnd()
   {
     return "";
   }

   protected String postStartOnce() {
     return "";
   }

   protected String postEndOnce() {
     return "";
   }

   public int doStartTag() throws JspException
   {
     print(postStartOnce());

     init();

     List list = this.tagProvider.getData(this.param, this.pageContext);
     this.listIterator = list.iterator();

     if (this.listIterator.hasNext()) {
       this.hasNext = true;
       setSope();
     }

     if (this.hasNext) {
       print(postStart());
       return 1;
     }
     print(postEndOnce());
     return 0;
   }

   public void init()
   {
     this.index = 0;
     this.hasNext = false;


     loadProvider();
   }

   protected abstract void loadProvider();

   protected void setSope()
   {
     Object row = this.listIterator.next();
     this.pageContext.setAttribute("index", Integer.valueOf(this.index));
     this.pageContext.setAttribute(this.item, row);
     this.pageContext.getRequest().setAttribute(this.item, row);
     this.index += 1;
   }

   public int doAfterBody()
   {
     print(postEnd());

     if (this.listIterator.hasNext()) {
       print(postStart());
       setSope();
       return 2;
     }
     this.pageContext.removeAttribute(this.item);
     print(postEndOnce());
     return 0;
   }

   public String getItem()
   {
     return this.item;
   }

   public void setItem(String item) {
     this.item = item;
   }

   public int getIndex() {
     return this.index;
   }

   public void setIndex(int index) {
     this.index = index;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\webapp\taglib\ListTaglibSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */