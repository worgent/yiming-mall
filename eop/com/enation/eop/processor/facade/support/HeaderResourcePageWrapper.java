 package com.enation.eop.processor.facade.support;

 import com.enation.eop.processor.IPageParser;
 import com.enation.eop.processor.PageWrapper;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.resource.impl.ResourceBuilder;
 import com.enation.framework.util.StringUtil;
 import io.apache.org.template.TplUtil;
 import java.io.IOException;
 import javax.servlet.http.HttpServletRequest;
 import org.mozilla.javascript.EvaluatorException;



 public class HeaderResourcePageWrapper
   extends PageWrapper
 {
   public HeaderResourcePageWrapper(IPageParser paser)
   {
     super(paser);
   }

   public static String THE_SSO_SCRIPT = "";

   public String parse(String url) {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();

     String content = super.parse(url);
     String pageid = (String)request.getAttribute("pageid");
     String tplFileName = (String)request.getAttribute("tplFileName");

     if ((StringUtil.isEmpty(pageid)) || (StringUtil.isEmpty(tplFileName))) {
       return content;
     }





     try
     {
       ResourceBuilder.reCreate(pageid, tplFileName);
     } catch (EvaluatorException e) {
       e.printStackTrace();
     } catch (IOException e) {
       e.printStackTrace();
     }







     StringBuffer headerhtml = new StringBuffer();
     EopContext ectx = EopContext.getContext();
     EopSite site = ectx.getCurrentSite();











     String resdomain = ectx.getResDomain();

     String scriptpath = "";
     String csspath = "";

     if (!EopSetting.DEVELOPMENT_MODEL) {
       if ("2".equals(EopSetting.RESOURCEMODE))
       {







         resdomain = resdomain + "/themes/" + site.getThemepath();
         scriptpath = resdomain + "/js/headerresource?type=javascript&id=" + pageid;
         csspath = resdomain + "/css/headerresource?type=css&id=" + pageid;
         headerhtml.append("<script src=\"" + scriptpath + "\" type=\"text/javascript\"></script>");
         headerhtml.append("<link href=\"" + csspath + "\" rel=\"stylesheet\" type=\"text/css\" />");


       }
       else
       {


         if (ResourceBuilder.haveCommonScript()) {
           String commonjs = resdomain + "/js/" + site.getThemepath() + "_common.js";
           headerhtml.append("<script src=\"" + commonjs + "\" type=\"text/javascript\"></script>");
         }


         if (ResourceBuilder.haveCommonCss()) {
           String commoncss = resdomain + "/css/" + site.getThemepath() + "_common.css";
           headerhtml.append("<link href=\"" + commoncss + "\" rel=\"stylesheet\" type=\"text/css\" />");
         }


         if (ResourceBuilder.haveScript()) {
           scriptpath = resdomain + "/js/" + site.getThemepath() + "_" + pageid.replaceAll("/", "_") + ".js";
           headerhtml.append("<script src=\"" + scriptpath + "\" type=\"text/javascript\"></script>");
         }


         if (ResourceBuilder.haveCss()) {
           csspath = resdomain + "/css/" + site.getThemepath() + "_" + tplFileName.replaceAll("/", "_") + ".css";
           headerhtml.append("<link href=\"" + csspath + "\" rel=\"stylesheet\" type=\"text/css\" />");
         }
       }
     }

     headerhtml.append(ResourceBuilder.getNotMergeResource());

     content = content.replaceAll("#headerscript#", headerhtml.toString());

     if ("y".equals(request.getParameter("cpr"))) {
       content = content + THE_SSO_SCRIPT;
     }

     content = content + TplUtil.process();

     return content;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\facade\support\HeaderResourcePageWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */