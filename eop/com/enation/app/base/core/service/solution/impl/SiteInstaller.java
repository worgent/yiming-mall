 package com.enation.app.base.core.service.solution.impl;

 import com.enation.eop.resource.ISiteManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import org.w3c.dom.Element;
 import org.w3c.dom.Node;
 import org.w3c.dom.NodeList;

 public class SiteInstaller implements com.enation.app.base.core.service.solution.IInstaller
 {
   private ISiteManager siteManager;

   private boolean setProperty(EopSite site, String name, String value)
   {
     try
     {
       org.apache.commons.beanutils.BeanUtils.setProperty(site, name, value);
       return true;
     } catch (Exception e) {}
     return false;
   }


   public void install(String productId, Node fragment)
   {
     EopSite site = EopContext.getContext().getCurrentSite();

     site.setMobilesite(Integer.valueOf(1));
     NodeList nodeList = fragment.getChildNodes();
     int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
       Node node = nodeList.item(i);
       if (node.getNodeType() == 1) {
         Element element = (Element)node;
         String name = element.getAttribute("name");
         String value = element.getAttribute("value");
         setProperty(site, name, value);
       }
     }
     this.siteManager.edit(site);
   }

   public ISiteManager getSiteManager() {
     return this.siteManager;
   }

   public void setSiteManager(ISiteManager siteManager) {
     this.siteManager = siteManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\solution\impl\SiteInstaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */