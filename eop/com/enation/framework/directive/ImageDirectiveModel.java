 package com.enation.framework.directive;

 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.resource.Resource;
 import com.enation.framework.util.StringUtil;
 import freemarker.core.Environment;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateDirectiveModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.io.Writer;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Set;






 public class ImageDirectiveModel
   extends AbstractResourceDirectiveModel
   implements TemplateDirectiveModel
 {
   public void execute(Environment env, Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3)
     throws TemplateException, IOException
   {
     Resource resource = createResource(params);
     resource.setType("image");




     String src = params.get("src").toString();
     String postfix = getValue(params, "postfix");
     String imageurl = getImageUrl(src, postfix);
     StringBuffer html = new StringBuffer();

     html.append("<img");
     html.append(" src=\"" + imageurl + "\"");


     Set keySet = params.keySet();
     Iterator<String> itor = keySet.iterator();

     while (itor.hasNext()) {
       String name = (String)itor.next();
       if ((!"src".equals(name)) &&
         (!"postfix".equals(name))) {
         String value = getValue(params, name);
         if (!StringUtil.isEmpty(value)) {
           html.append(" " + name + "=\"" + value + "\"");
         }
       }
     }



     html.append(" />");
     env.getOut().write(html.toString());
   }

   private static String getImageUrl(String pic, String postfix)
   {
     if (StringUtil.isEmpty(pic)) {
       pic = EopSetting.DEFAULT_IMG_URL;
     }





     if (pic.startsWith("fs:")) {
       pic = UploadUtil.replacePath(pic);
     } else if (!pic.toUpperCase().startsWith("HTTP"))
     {
       EopContext ectx = EopContext.getContext();


       if (!pic.startsWith("/")) {
         pic = "/" + pic;
       }








       if (("2".equals(EopSetting.RESOURCEMODE)) || (EopSetting.DEVELOPMENT_MODEL))
       {
         EopSite site = ectx.getCurrentSite();

         pic = ectx.getResDomain() + "/themes/" + site.getThemepath() + pic;
       }
       else
       {
         pic = ectx.getResDomain() + pic;
       }
     }


     if (!StringUtil.isEmpty(postfix)) {
       return UploadUtil.getThumbPath(pic, postfix);
     }
     return pic;
   }

   public static void main(String[] args) {}
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\directive\ImageDirectiveModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */