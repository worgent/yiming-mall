 package com.enation.framework.util;

 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Set;
 import javax.servlet.http.HttpServletRequest;











 public abstract class RequestUtil
 {
   public static Map<String, String> paramToMap(HttpServletRequest request)
   {
     Map<String, String> params = new HashMap();

     Map rMap = request.getParameterMap();
     Iterator rIter = rMap.keySet().iterator();

     while (rIter.hasNext()) {
       Object key = rIter.next();
       String value = request.getParameter(key.toString());
       if ((key != null) && (value != null)) {
         params.put(key.toString(), value.toString());
       }
     }
     return params;
   }


   public static String getRequestUrl(HttpServletRequest request)
   {
     String redirect = (String)request.getAttribute("redirect");
     if (redirect != null)
       return redirect;
     String pathInfo = request.getPathInfo();
     String queryString = request.getQueryString();

     String uri = request.getServletPath();
     String ctx = request.getContextPath();
     ctx = ctx.equals("/") ? "" : ctx;

     if (uri == null) {
       uri = request.getRequestURI();
       uri = uri.substring(request.getContextPath().length());
     }

     return ctx + uri + (pathInfo == null ? "" : pathInfo) + (queryString == null ? "" : new StringBuilder().append("?").append(queryString).toString());
   }





   public static String getWholeUrl(HttpServletRequest request)
   {
     String servername = request.getServerName();
     String path = request.getServletPath();
     int port = request.getServerPort();

     String portstr = "";
     if (port != 80) {
       portstr = ":" + port;
     }
     String contextPath = request.getContextPath();
     if (contextPath.equals("/")) {
       contextPath = "";
     }


     String url = "http://" + servername + portstr + contextPath + "/" + path;

     return url;
   }







   public static String getDomain()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String port = "" + request.getServerPort();
     if (port.equals("80")) {
       port = "";
     } else {
       port = ":" + port;
     }

     String contextPath = request.getContextPath();
     if (contextPath.equals("/")) {
       contextPath = "";
     }

     String domain = "http://" + request.getServerName() + port;
     domain = domain + contextPath;
     return domain;
   }






   public static Integer getIntegerValue(HttpServletRequest request, String name)
   {
     String value = request.getParameter(name);
     if (StringUtil.isEmpty(value)) {
       return null;
     }
     return Integer.valueOf(value);
   }



   public static Double getDoubleValue(HttpServletRequest request, String name)
   {
     String value = request.getParameter(name);
     if (StringUtil.isEmpty(value)) {
       return null;
     }
     return Double.valueOf(value);
   }











   public static int getIntValue(HttpServletRequest request, String name)
   {
     String value = request.getParameter(name);
     if (StringUtil.isEmpty(value)) {
       return 0;
     }
     return Integer.valueOf(value).intValue();
   }

   public static String getRequestMethod(HttpServletRequest request)
   {
     String method = request.getParameter("_method");
     method = method == null ? "get" : method;
     method = method.toUpperCase();
     return method;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\\util\RequestUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */