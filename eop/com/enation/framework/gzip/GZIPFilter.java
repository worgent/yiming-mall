 package com.enation.framework.gzip;

 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletRequest;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;

 public class GZIPFilter implements javax.servlet.Filter
 {
   public void doFilter(ServletRequest req, javax.servlet.ServletResponse res, FilterChain chain) throws java.io.IOException, javax.servlet.ServletException
   {
     if ((req instanceof HttpServletRequest)) {
       HttpServletRequest request = (HttpServletRequest)req;
       HttpServletResponse response = (HttpServletResponse)res;
       String ae = request.getHeader("accept-encoding");
       if ((ae != null) && (ae.indexOf("gzip") != -1)) {
         GZIPResponseWrapper gZIPResponseWrapper = new GZIPResponseWrapper(response);
         chain.doFilter(req, gZIPResponseWrapper);
         gZIPResponseWrapper.finishResponse();
         return;
       }
       chain.doFilter(req, res);
     }
   }

   public void init(FilterConfig filterConfig) {}

   public void destroy() {}
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\gzip\GZIPFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */