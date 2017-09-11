 package com.enation.app.base.component.widget.guestbook;

 import com.enation.app.base.core.model.GuestBook;
 import com.enation.app.base.core.service.IGuestBookManager;
 import com.enation.eop.sdk.widget.AbstractWidget;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.database.Page;
 import com.enation.framework.pager.FacadePagerHtmlBuilder;
 import com.enation.framework.util.StringUtil;
 import com.enation.framework.util.ip.IPSeeker;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;











 @Component("guestbook")
 @Scope("prototype")
 public class GuestBookWidget
   extends AbstractWidget
 {
   protected IGuestBookManager guestBookManager;

   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String action = request.getParameter("action");
     if ((action == null) || (action.equals("list")))
     {
       String pageSize = (String)params.get("pagesize");
       if (StringUtil.isEmpty(pageSize)) {
         pageSize = request.getParameter("pagesize");
         if (StringUtil.isEmpty(pageSize)) {
           pageSize = "10";
         }
       }
       putData("pagesize", pageSize);


       String adminname = (String)params.get("adminname");
       if (StringUtil.isEmpty(adminname))
         adminname = "管理员";
       putData("adminname", adminname);


       String message = (String)params.get("message");
       if (StringUtil.isEmpty(message))
         message = "留言成功，稍候我们会对您的留言回复。";
       putData("message", message);
       list(Integer.valueOf(pageSize).intValue());
     }
     if ("add".equals(action)) {
       add();
     }
   }



   protected void list(int pageSize)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String pageNo = request.getParameter("page");
     if (StringUtil.isEmpty(pageNo)) {
       pageNo = "1";
     }
     Page page = this.guestBookManager.list(null, Integer.valueOf(pageNo).intValue(), pageSize);
     putData("subjectList", page.getResult());

     FacadePagerHtmlBuilder pagerHtmlBuilder = new FacadePagerHtmlBuilder(Integer.valueOf(pageNo).intValue(), page.getTotalCount(), pageSize);
     String page_html = pagerHtmlBuilder.buildPageHtml();
     putData("pager", page_html);
   }



   protected void add()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String username = htmlEncode(request.getParameter("username"));
     String title = htmlEncode(request.getParameter("title"));
     String content = htmlEncode(request.getParameter("content"));
     String email = request.getParameter("email");
     String qq = htmlEncode(request.getParameter("qq"));
     String tel = htmlEncode(request.getParameter("tel"));
     String sex = request.getParameter("sex");
     String validate = request.getParameter("vcode");
     String ip = request.getRemoteAddr();
     String area = new IPSeeker().getCountry(ip);
     setPageFolder("/commons");
     setPageName("json");
     if (validcode(validate) == 0) {
       putData("json", "{result:0,message:'验证码输入错误'}");
     } else {
       GuestBook guestbook = new GuestBook();
       guestbook.setIp(ip);
       guestbook.setArea(area);
       guestbook.setTitle(title);
       guestbook.setContent(content);
       guestbook.setQq(qq);
       guestbook.setEmail(email);
       guestbook.setSex(Integer.valueOf(sex));
       guestbook.setTel(tel);
       guestbook.setUsername(username);
       this.guestBookManager.add(guestbook);
       putData("json", "{result:1}");
     }
   }






   private int validcode(String validcode)
   {
     if (validcode == null) {
       return 0;
     }
     String code = (String)ThreadContextHolder.getSessionContext().getAttribute("valid_codeguestbook");
     if (code == null) {
       return 0;
     }
     if (!code.equalsIgnoreCase(validcode)) {
       return 0;
     }


     return 1;
   }

   protected String htmlEncode(String input) {
     if (input == null)
       return "";
     return input.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
   }

   public IGuestBookManager getGuestBookManager() {
     return this.guestBookManager;
   }

   public void setGuestBookManager(IGuestBookManager guestBookManager) {
     this.guestBookManager = guestBookManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\guestbook\GuestBookWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */