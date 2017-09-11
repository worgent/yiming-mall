 package com.enation.app.cms.component.plugin.video;

 import com.enation.app.cms.component.plugin.ImageFieldPlugin;
 import com.enation.app.cms.core.model.DataField;
 import com.enation.app.cms.core.plugin.AbstractFieldPlugin;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.sun.xml.messaging.saaj.util.ByteOutputStream;
 import freemarker.template.Configuration;
 import freemarker.template.DefaultObjectWrapper;
 import freemarker.template.Template;
 import java.io.OutputStreamWriter;
 import java.io.Writer;
 import java.util.HashMap;
 import java.util.Locale;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import net.sf.json.JSONObject;
 import org.springframework.stereotype.Component;











 @Component
 public class VideoFieldPlugin
   extends AbstractFieldPlugin
 {
   private ImageFieldPlugin imageFieldPlugin;

   public int getHaveSelectValue()
   {
     return 0;
   }

   public String onDisplay(DataField field, Object value)
   {
     String fieldname = field.getEnglish_name();

     if (value != null) {
       value = UploadUtil.replacePath(value.toString());
     }

     String img = null;
     Map data = new HashMap();

     data.put("fieldname", fieldname);


     if (value != null) {
       JSONObject videoData = JSONObject.fromObject(value);

       String title = (String)videoData.get("title");
       String path = (String)videoData.get("path");
       img = (String)videoData.get("img");

       String width = (String)videoData.get("width");
       String height = (String)videoData.get("height");
       String autoplay = (String)videoData.get("autoplay");


       data.put("path", path);
       data.put("title", title);
       data.put("width", width);
       data.put("height", height);
       data.put("autoplay", autoplay);
     }


     field.setEnglish_name(fieldname + "_img");
     String imgHtml = this.imageFieldPlugin.onDisplay(field, img);
     data.put("imgHtml", imgHtml);
     try
     {
       data.put("ctx", ThreadContextHolder.getHttpRequest().getContextPath());
       data.put("ext", EopSetting.EXTENSION);
       Configuration cfg = new Configuration();
       cfg.setObjectWrapper(new DefaultObjectWrapper());
       cfg.setDefaultEncoding("UTF-8");
       cfg.setLocale(Locale.CHINA);
       cfg.setEncoding(Locale.CHINA, "UTF-8");


       cfg.setClassForTemplateLoading(getClass(), "");
       Template temp = cfg.getTemplate("VideoFieldPlugin.html");
       ByteOutputStream stream = new ByteOutputStream();

       Writer out = new OutputStreamWriter(stream);
       temp.process(data, out);

       out.flush();
       return stream.toString();

     }
     catch (Exception e)
     {
       return "CMS插件解析出错" + e.getMessage();
     }
   }




   public void onSave(Map article, DataField field)
   {
     String fieldname = field.getEnglish_name();
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String title = request.getParameter(fieldname + "_title");
     String path = request.getParameter(fieldname + "_path");
     String width = request.getParameter(fieldname + "_width");
     String height = request.getParameter(fieldname + "_height");
     String autoplay = request.getParameter(fieldname + "_autoplay");
     String img = request.getParameter(fieldname + "_img");


     Map video = new HashMap();
     video.put("title", title);
     video.put("path", path);
     video.put("width", width);
     video.put("height", height);
     video.put("autoplay", autoplay);
     video.put("img", img);



     String value = JSONObject.fromObject(video).toString();
     article.put(fieldname, value);
   }


   public Object onShow(DataField field, Object value)
   {
     String img = null;
     Map video = null;
     if (value != null) {
       video = new HashMap();
       value = UploadUtil.replacePath(value.toString());

       JSONObject videoData = JSONObject.fromObject(value);

       String title = (String)videoData.get("title");
       String path = (String)videoData.get("path");
       img = (String)videoData.get("img");

       String width = (String)videoData.get("width");
       String height = (String)videoData.get("height");
       String autoplay = (String)videoData.get("autoplay");

       video.put("path", path);
       video.put("title", title);
       video.put("img", img);
       video.put("width", width);
       video.put("height", height);
       video.put("autoplay", autoplay);
       String videoHtml = getVideoPlayerHtml(video, field.getEnglish_name());
       video.put("playerHtml", videoHtml);
     }


     return video;
   }

   private String getVideoPlayerHtml(Map video, String fieldname)
   {
     try
     {
       Map data = new HashMap();
       data.putAll(video);
       data.put("fieldname", fieldname);
       data.put("ctx", ThreadContextHolder.getHttpRequest().getContextPath());
       data.put("ext", EopSetting.EXTENSION);
       Configuration cfg = new Configuration();
       cfg.setObjectWrapper(new DefaultObjectWrapper());
       cfg.setDefaultEncoding("UTF-8");
       cfg.setLocale(Locale.CHINA);
       cfg.setEncoding(Locale.CHINA, "UTF-8");

       cfg.setClassForTemplateLoading(getClass(), "");
       Template temp = cfg.getTemplate("VideoPlayer.html");
       ByteOutputStream stream = new ByteOutputStream();

       Writer out = new OutputStreamWriter(stream);
       temp.process(data, out);

       out.flush();
       return stream.toString();

     }
     catch (Exception e)
     {
       return "CMS插件解析出错" + e.getMessage();
     }
   }



   public String getDataType()
   {
     return "text";
   }



   public String getId()
   {
     return "video";
   }


   public String getName()
   {
     return "视频";
   }

   public ImageFieldPlugin getImageFieldPlugin() {
     return this.imageFieldPlugin;
   }

   public void setImageFieldPlugin(ImageFieldPlugin imageFieldPlugin) {
     this.imageFieldPlugin = imageFieldPlugin;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms-core.jar!\com\enation\app\cms\component\plugin\video\VideoFieldPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */