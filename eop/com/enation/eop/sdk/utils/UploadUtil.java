 package com.enation.eop.sdk.utils;

 import com.enation.eop.processor.MultipartRequestWrapper;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.image.IThumbnailCreator;
 import com.enation.framework.image.ThumbnailCreatorFactory;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.FileUtil;
 import com.enation.framework.util.StringUtil;
 import java.io.File;
 import java.io.IOException;
 import java.util.Date;
 import java.util.Hashtable;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import javax.servlet.http.HttpServletRequest;
 import javazoom.upload.MultipartFormDataRequest;
 import javazoom.upload.UploadException;
 import javazoom.upload.UploadFile;







 public class UploadUtil
 {
   public static String uploadUseRequest(String name, String subFolder, String allowext)
   {
     if (name == null) throw new IllegalArgumentException("file or filename object is null");
     if (subFolder == null) throw new IllegalArgumentException("subFolder is null");
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     if (!MultipartFormDataRequest.isMultipartFormData(request)) throw new RuntimeException("request data is not MultipartFormData");
     try
     {
       String encoding = EopSetting.ENCODING;
       if (StringUtil.isEmpty(encoding)) {
         encoding = "UTF-8";
       }

       MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request, null, 1048576000, MultipartFormDataRequest.COSPARSER, encoding);
       request = new MultipartRequestWrapper(request, mrequest);
       ThreadContextHolder.setHttpRequest(request);

       Hashtable files = mrequest.getFiles();
       UploadFile file = (UploadFile)files.get(name);
       if (file.getInpuStream() == null) { return null;
       }
       String fileFileName = file.getFileName();



       String fileName = null;
       String filePath = "";

       String ext = FileUtil.getFileExt(fileFileName);

       if (!allowext.equals(ext)) {
         throw new IllegalArgumentException("不被允许的上传文件类型");
       }

       fileName = DateUtil.toString(new Date(), "yyyyMMddHHmmss") + StringUtil.getRandStr(4) + "." + ext;

       filePath = EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/attachment/";
       if (subFolder != null) {
         filePath = filePath + subFolder + "/";
       }

       String path = EopSetting.FILE_STORE_PREFIX + "/attachment/" + (subFolder == null ? "" : subFolder) + "/" + fileName;

       filePath = filePath + fileName;
       FileUtil.createFile(file.getInpuStream(), filePath);

       return path;

     }
     catch (UploadException e)
     {
       e.printStackTrace();
     }
     catch (IOException e) {
       e.printStackTrace();
     }






     return null;
   }






   public static String uploadUseRequest(String name, String subFolder)
   {
     if (name == null) throw new IllegalArgumentException("file or filename object is null");
     if (subFolder == null) throw new IllegalArgumentException("subFolder is null");
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     if (!MultipartFormDataRequest.isMultipartFormData(request)) throw new RuntimeException("request data is not MultipartFormData");
     try
     {
       String encoding = EopSetting.ENCODING;
       if (StringUtil.isEmpty(encoding)) {
         encoding = "UTF-8";
       }

       MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request, null, 1048576000, MultipartFormDataRequest.COSPARSER, encoding);
       request = new MultipartRequestWrapper(request, mrequest);
       ThreadContextHolder.setHttpRequest(request);

       Hashtable files = mrequest.getFiles();
       UploadFile file = (UploadFile)files.get(name);
       if (file.getInpuStream() == null) { return null;
       }
       String fileFileName = file.getFileName();

       String fileName = null;
       String filePath = "";

       String ext = FileUtil.getFileExt(fileFileName);
       fileName = DateUtil.toString(new Date(), "yyyyMMddHHmmss") + StringUtil.getRandStr(4) + "." + ext;

       filePath = EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/attachment/";
       if (subFolder != null) {
         filePath = filePath + subFolder + "/";
       }

       String path = EopSetting.FILE_STORE_PREFIX + "/attachment/" + (subFolder == null ? "" : subFolder) + "/" + fileName;

       filePath = filePath + fileName;
       FileUtil.createFile(file.getInpuStream(), filePath);

       return path;
     }
     catch (UploadException e)
     {
       e.printStackTrace();
     } catch (IOException e) {
       e.printStackTrace();
     }






     return null;
   }












   public static String upload(File file, String fileFileName, String subFolder)
   {
     if ((file == null) || (fileFileName == null)) throw new IllegalArgumentException("file or filename object is null");
     if (subFolder == null) { throw new IllegalArgumentException("subFolder is null");
     }
     if (!FileUtil.isAllowUp(fileFileName)) {
       throw new IllegalArgumentException("不被允许的上传文件类型");
     }

     String fileName = null;
     String filePath = "";

     String ext = FileUtil.getFileExt(fileFileName);
     fileName = DateUtil.toString(new Date(), "yyyyMMddHHmmss") + StringUtil.getRandStr(4) + "." + ext;

     filePath = EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/attachment/";
     if (subFolder != null) {
       filePath = filePath + subFolder + "/";
     }

     String path = EopSetting.FILE_STORE_PREFIX + "/attachment/" + (subFolder == null ? "" : subFolder) + "/" + fileName;

     filePath = filePath + fileName;
     FileUtil.createFile(file, filePath);
     return path;
   }


   public static String replacePath(String path)
   {
     if (StringUtil.isEmpty(path)) { return path;
     }

     return path.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath());
   }












   public static String[] upload(File file, String fileFileName, String subFolder, int width, int height)
   {
     if ((file == null) || (fileFileName == null)) throw new IllegalArgumentException("file or filename object is null");
     if (subFolder == null) throw new IllegalArgumentException("subFolder is null");
     String fileName = null;
     String filePath = "";
     String[] path = new String[2];
     if (!FileUtil.isAllowUp(fileFileName)) {
       throw new IllegalArgumentException("不被允许的上传文件类型");
     }
     String ext = FileUtil.getFileExt(fileFileName);
     fileName = DateUtil.toString(new Date(), "yyyyMMddHHmmss") + StringUtil.getRandStr(4) + "." + ext;

     filePath = EopSetting.IMG_SERVER_PATH + getContextFolder() + "/attachment/";
     if (subFolder != null) {
       filePath = filePath + subFolder + "/";
     }

     path[0] = (EopSetting.IMG_SERVER_DOMAIN + getContextFolder() + "/attachment/" + (subFolder == null ? "" : subFolder) + "/" + fileName);
     filePath = filePath + fileName;
     FileUtil.createFile(file, filePath);
     String thumbName = getThumbPath(filePath, "_thumbnail");

     IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator(filePath, thumbName);
     thumbnailCreator.resize(width, height);
     path[1] = getThumbPath(path[0], "_thumbnail");
     return path;
   }







   public static void deleteFile(String filePath)
   {
     filePath = filePath.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
     FileUtil.delete(filePath);
   }






   public static void deleteFileAndThumb(String filePath)
   {
     filePath = filePath.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
     FileUtil.delete(filePath);
     FileUtil.delete(getThumbPath(filePath, "_thumbnail"));
   }


   private static String getContextFolder()
   {
     return EopContext.getContext().getContextPath();
   }






   public static String getThumbPath(String filePath, String shortName)
   {
     String pattern = "(.*)([\\.])(.*)";
     String thumbPath = "$1" + shortName + "$2$3";

     Pattern p = Pattern.compile(pattern, 34);
     Matcher m = p.matcher(filePath);
     if (m.find()) {
       String s = m.replaceAll(thumbPath);

       return s;
     }
     return null;
   }

   public static void main(String[] args) {}
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\\utils\UploadUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */