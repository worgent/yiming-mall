 package com.enation.app.base.core.action;

 import com.enation.app.base.core.model.GuestBook;
 import com.enation.app.base.core.service.IGuestBookManager;

 public class GuestBookAction extends com.enation.framework.action.WWAction
 {
   private IGuestBookManager guestBookManager;
   private String keyword;
   private int parentid;
   private int id;
   private String content;
   private GuestBook book;
   private Integer[] idArray;

   public String list()
   {
     if (!com.enation.framework.util.StringUtil.isEmpty(this.keyword)) this.keyword = com.enation.framework.util.StringUtil.toUTF8(this.keyword);
     this.webpage = this.guestBookManager.list(this.keyword, getPage(), getPageSize());
     return "list";
   }

   public String reply() {
     GuestBook guestbook = new GuestBook();
     guestbook.setContent(this.content);
     guestbook.setParentid(Integer.valueOf(this.parentid));
     this.guestBookManager.reply(guestbook);
     this.msgs.add("回复成功");
     this.urls.put("查看此条留言", "guestBook!detail.do?id=" + this.parentid);
     return "message";
   }

   public String detail()
   {
     this.book = this.guestBookManager.get(this.id);
     return "detail";
   }

   public String edit() {
     this.guestBookManager.edit(this.id, this.content);
     this.json = "{result:1}";
     return "json_message";
   }

   public String delete() {
     try {
       this.guestBookManager.delete(this.idArray);
       this.json = "{result:0,message:'删除成功'}";
     } catch (RuntimeException e) {
       this.json = ("{result:1,message:'" + e.getMessage() + "'}");
     }
     return "json_message";
   }

   public IGuestBookManager getGuestBookManager() {
     return this.guestBookManager;
   }

   public void setGuestBookManager(IGuestBookManager guestBookManager) { this.guestBookManager = guestBookManager; }

   public String getKeyword()
   {
     return this.keyword;
   }

   public void setKeyword(String keyword) {
     this.keyword = keyword;
   }

   public int getParentid() {
     return this.parentid;
   }

   public void setParentid(int parentid) {
     this.parentid = parentid;
   }

   public int getId() {
     return this.id;
   }

   public void setId(int id) {
     this.id = id;
   }

   public String getContent() {
     return this.content;
   }

   public void setContent(String content) {
     this.content = content;
   }

   public GuestBook getBook() {
     return this.book;
   }

   public void setBook(GuestBook book) {
     this.book = book;
   }

   public Integer[] getIdArray() {
     return this.idArray;
   }

   public void setIdArray(Integer[] idArray) {
     this.idArray = idArray;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\GuestBookAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */