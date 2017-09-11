 package com.enation.framework.taglib;

 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateMethodModel;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import net.sf.json.JSONObject;
 import org.apache.log4j.Logger;












 public abstract class BaseFreeMarkerTag
   implements TemplateMethodModel
 {
   protected final Logger logger = Logger.getLogger(getClass());
   private int pageSize = 10;

   public Object exec(List jsonParam) throws TemplateModelException
   {
     if ((jsonParam != null) && (!jsonParam.isEmpty()))
     {
       String param = (String)jsonParam.get(0);
       if (param != null) {
         if (!param.startsWith("{")) {
           param = "{" + param + "}";
         }
         JSONObject jsonObject = JSONObject.fromObject(param);
         Integer pageSizeNum = (Integer)jsonObject.get("pageSize");
         if (pageSizeNum != null) {
           this.pageSize = pageSizeNum.intValue();
         }
         return exec(jsonObject);
       }
       return exec(new HashMap());
     }

     return exec(new HashMap());
   }

   protected <T> T getBean(String beanid)
   {
     return (T)SpringContextHolder.getBean(beanid);
   }

   protected abstract Object exec(Map paramMap) throws TemplateModelException;

   protected int getPageSize()
   {
     return this.pageSize;
   }


   protected int getPage()
   {
     int page = 1;
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     page = StringUtil.toInt(request.getParameter("page"), Integer.valueOf(1)).intValue();
     page = page < 1 ? 1 : page;
     return page;
   }

   protected HttpServletRequest getRequest() {
     return ThreadContextHolder.getHttpRequest();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\taglib\BaseFreeMarkerTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */