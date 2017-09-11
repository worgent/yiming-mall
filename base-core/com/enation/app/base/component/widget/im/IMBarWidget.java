 package com.enation.app.base.component.widget.im;

 import com.enation.app.base.core.service.ISettingService;
 import com.enation.eop.resource.ISiteManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.widget.AbstractWidget;
 import com.enation.framework.util.StringUtil;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;











 @Component("imbar")
 @Scope("prototype")
 public class IMBarWidget
   extends AbstractWidget
 {
   private ISettingService settingService;
   private ISiteManager siteManager;

   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     EopSite site = EopContext.getContext().getCurrentSite();


     String state = site.getState() == null ? "0" : String.valueOf(site.getState());




     String title = "在线客服";


     Integer qq = site.getQq();
     String qqlist = site.getQqlist();
     qq = Integer.valueOf(qq == null ? 0 : qq.intValue());


     Integer msn = site.getMsn();
     String msnlist = site.getMsnlist();
     msn = Integer.valueOf(msn == null ? 0 : msn.intValue());


     Integer ww = site.getWw();
     String wwlist = site.getWwlist();
     ww = Integer.valueOf(ww == null ? 0 : ww.intValue());


     Integer wt = site.getWt();
     wt = Integer.valueOf(wt == null ? 0 : wt.intValue());


     Integer tel = site.getTel();
     tel = Integer.valueOf(tel == null ? 0 : tel.intValue());


     qqlist = StringUtil.isEmpty(qqlist) ? "" : qqlist;





     msnlist = StringUtil.isEmpty(msnlist) ? "" : msnlist;



     wwlist = StringUtil.isEmpty(wwlist) ? "" : wwlist;


     String worktime = site.getWorktime();
     String tellist = site.getTellist();

     putData("state", state);
     putData("imtitle", title);
     putData("qq", qq);
     putData("msn", msn);
     putData("ww", ww);
     putData("wt", wt);
     putData("tel", tel);

     putData("qqlist", createList(qqlist));
     putData("msnlist", createList(msnlist));
     putData("wwlist", createList(wwlist));
     putData("worktime", worktime);
     putData("imtel", tellist);

     setPageName("imbar");
   }






   private Map<String, String> createMap(String im)
   {
     im = StringUtil.isEmpty(im) ? "" : im;
     String[] imar = StringUtils.split(im, ":");

     Map<String, String> immap = new HashMap();
     if (imar.length != 2) { return null;
     }
     immap.put("num", imar[0]);
     immap.put("text", imar[1]);
     return immap;
   }





   private List createList(String im)
   {
     List imlist = new ArrayList();
     im = StringUtil.isEmpty(im) ? "" : im;
     String[] imar = StringUtils.split(im, ",");
     for (String s : imar) {
       Map<String, String> immap = createMap(s);
       if (immap != null)
         imlist.add(immap);
     }
     return imlist;
   }

   public ISettingService getSettingService() {
     return this.settingService;
   }

   public void setSettingService(ISettingService settingService) {
     this.settingService = settingService;
   }

   public ISiteManager getSiteManager()
   {
     return this.siteManager;
   }

   public void setSiteManager(ISiteManager siteManager)
   {
     this.siteManager = siteManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\im\IMBarWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */