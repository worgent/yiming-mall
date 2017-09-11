 package com.enation.eop.sdk.utils;

 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.io.PrintWriter;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpServletResponseWrapper;







 public class WrapperResponse
   extends HttpServletResponseWrapper
 {
   private MyPrintWriter tmpWriter;
   private ByteArrayOutputStream output;

   public WrapperResponse(HttpServletResponse httpServletResponse)
   {
     super(httpServletResponse);
     this.output = new ByteArrayOutputStream();
     this.tmpWriter = new MyPrintWriter(this.output);
   }

   public void finalize() throws Throwable {
     super.finalize();
     this.output.close();
     this.tmpWriter.close();
   }

   public String getContent()
   {
     this.tmpWriter.flush();
     String s = "";

     s = this.tmpWriter.getByteArrayOutputStream().toString();






     return s;
   }

   public PrintWriter getWriter() throws IOException
   {
     return this.tmpWriter;
   }

   public void close() throws IOException {
     this.tmpWriter.close();
   }

   private static class MyPrintWriter extends PrintWriter {
     ByteArrayOutputStream myOutput;

     public MyPrintWriter(ByteArrayOutputStream output) {
       super(output);
       this.myOutput = output;
     }

     public ByteArrayOutputStream getByteArrayOutputStream() {
       return this.myOutput;
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\\utils\WrapperResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */