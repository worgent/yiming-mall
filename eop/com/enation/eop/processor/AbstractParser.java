 package com.enation.eop.processor;

 import com.enation.eop.processor.core.Request;
 import com.enation.eop.processor.core.RequestWrapper;
 import com.enation.eop.processor.core.Response;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;








 public abstract class AbstractParser
   extends RequestWrapper
 {
   protected String uri;
   protected HttpServletResponse httpResponse;
   protected HttpServletRequest httpRequest;

   public AbstractParser(Request request)
   {
     super(request);
   }











   public Response execute(String uri, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
   {
     this.uri = uri;
     this.httpRequest = httpRequest;
     this.httpResponse = httpResponse;
     return parse();
   }






   protected Response parse()
   {
     Response response = super.execute(this.uri, this.httpResponse, this.httpRequest);
     return parse(response);
   }

   protected abstract Response parse(Response paramResponse);
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\AbstractParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */