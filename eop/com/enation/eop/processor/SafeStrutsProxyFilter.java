 package com.enation.eop.processor;

 import java.io.IOException;
 import javax.servlet.FilterChain;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

 public class SafeStrutsProxyFilter extends StrutsPrepareAndExecuteFilter
 {
   public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
   {
     req = new SafeHttpRequestWrapper((HttpServletRequest)req);
     super.doFilter(req, res, chain);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\SafeStrutsProxyFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */