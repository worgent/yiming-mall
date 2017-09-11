 package com.enation.eop.processor.core;

 import java.io.ByteArrayInputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.UnsupportedEncodingException;
 import java.util.ArrayList;
 import java.util.Enumeration;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.fileupload.FileItem;
 import org.apache.commons.fileupload.FileItemFactory;
 import org.apache.commons.fileupload.FileUploadException;
 import org.apache.commons.fileupload.disk.DiskFileItemFactory;
 import org.apache.commons.fileupload.servlet.ServletFileUpload;
 import org.apache.http.HttpEntity;
 import org.apache.http.NameValuePair;
 import org.apache.http.client.entity.UrlEncodedFormEntity;
 import org.apache.http.entity.mime.MultipartEntity;
 import org.apache.http.entity.mime.content.InputStreamBody;
 import org.apache.http.entity.mime.content.StringBody;
 import org.apache.http.message.BasicNameValuePair;

 public abstract class HttpEntityFactory
 {
   public static HttpEntity buildEntity(HttpServletRequest request, Map<String, String> otherParams)
   {
     boolean isMultipart = ServletFileUpload.isMultipartContent(request);
     HttpEntity entity = null;
     if (isMultipart) {
       entity = buildMultipartFormEntity(request, otherParams);
     } else {
       entity = buildFormEntity(request, otherParams);
     }
     return entity;
   }

   private static HttpEntity buildFormEntity(HttpServletRequest request, Map<String, String> otherParams)
   {
     try {
       Enumeration<String> paramNames = request.getParameterNames();
       if (paramNames == null) {
         return null;
       }
       List<NameValuePair> formparams = new ArrayList();

       while (paramNames.hasMoreElements()) {
         String name = (String)paramNames.nextElement();
         String value = request.getParameter(name);
         formparams.add(new BasicNameValuePair(name, value));
       }

       if (otherParams != null) {
         Iterator<String> iterator = otherParams.keySet().iterator();
         while (iterator.hasNext()) {
           String key = (String)iterator.next();
           String value = (String)otherParams.get(key);
           formparams.add(new BasicNameValuePair(key, value));
         }
       }

       return new UrlEncodedFormEntity(formparams, "UTF-8");
     }
     catch (UnsupportedEncodingException e)
     {
       e.printStackTrace();
     }
     return null;
   }

   public static HttpEntity buildEntity(Map<String, String> otherParams)
   {
     try {
       List<NameValuePair> formparams = new ArrayList();

       if (otherParams != null) {
         Iterator<String> iterator = otherParams.keySet().iterator();
         while (iterator.hasNext()) {
           String key = (String)iterator.next();
           String value = (String)otherParams.get(key);
           formparams.add(new BasicNameValuePair(key, value));
         }
       }
       return new UrlEncodedFormEntity(formparams, "UTF-8");
     }
     catch (UnsupportedEncodingException e)
     {
       e.printStackTrace();
     }
     return null;
   }

   private static HttpEntity buildMultipartFormEntity(HttpServletRequest request, Map<String, String> otherParams) {
     try {
       MultipartEntity entity = new MultipartEntity();

       FileItemFactory factory = new DiskFileItemFactory();
       ServletFileUpload upload = new ServletFileUpload(factory);
       List<FileItem> items = upload.parseRequest(request);
       for (FileItem item : items) {
         String fieldName = item.getFieldName();
         StringBody strBody; if (item.isFormField()) {
           strBody = new StringBody(item.getString());
         }
         else {
           String name = item.getName();
           InputStream dataIn = new ByteArrayInputStream(item.get());
           InputStreamBody isbody = new InputStreamBody(dataIn, name);
         }
       }

       if (otherParams != null) {
         Iterator<String> iterator = otherParams.keySet().iterator();
         StringBody strBody; while (iterator.hasNext()) {
           String key = (String)iterator.next();
           String value = (String)otherParams.get(key);
           strBody = new StringBody(value);
         }
       }


       return entity;
     } catch (FileUploadException e) {
       e.printStackTrace();
     } catch (IOException e) {
       e.printStackTrace();
     }
     return null;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\core\HttpEntityFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */