 package com.enation.app.base.core.service.solution.impl;

 import com.enation.app.base.core.service.solution.IInstaller;
 import com.enation.app.base.core.service.solution.InstallUtil;
 import com.enation.eop.resource.ISiteManager;
 import com.enation.eop.resource.IThemeManager;
 import com.enation.eop.resource.model.Theme;
 import org.apache.log4j.Logger;
 import org.w3c.dom.Element;
 import org.w3c.dom.Node;
 import org.w3c.dom.NodeList;











 public class ThemeInstaller
   implements IInstaller
 {
   private IThemeManager themeManager;
   private ISiteManager siteManager;
   private String productId;

   public void install(String productId, Node fragment)
   {
     this.productId = productId;













     NodeList themeList = fragment.getChildNodes();
     install(themeList); }

   protected final Logger logger = Logger.getLogger(getClass());

   private void install(Element themeNode) { String isdefault = themeNode.getAttribute("default");
     Theme theme = new Theme();
     theme.setProductId(this.productId);
     theme.setPath(themeNode.getAttribute("id"));
     theme.setThemename(themeNode.getAttribute("name"));
     theme.setThumb("preview.png");
     theme.setSiteid(Integer.valueOf(0));
     InstallUtil.putMessaage("安装主题" + theme.getThemename() + "...");
     String commonAttr = themeNode.getAttribute("isCommonTheme");
     commonAttr = commonAttr == null ? "" : commonAttr;
     Boolean isCommonTheme = Boolean.valueOf(commonAttr.toUpperCase().equals("TRUE"));
     Integer themeid = this.themeManager.add(theme, isCommonTheme.booleanValue());
     if (this.logger.isDebugEnabled()) {
       this.logger.debug("install " + theme.getThemename() + ",default :" + isdefault);
     }
     if ("yes".equals(isdefault)) {
       if (this.logger.isDebugEnabled())
         this.logger.debug("change theme[" + themeid + "] ");
       this.siteManager.changeTheme(themeid);
     }


     InstallUtil.putMessaage("完成!");
   }

   private void install(NodeList nodeList) {
     for (int i = 0; i < nodeList.getLength(); i++) {
       Node node = nodeList.item(i);
       if (node.getNodeType() == 1) {
         install((Element)node);
       }
     }
   }


   public IThemeManager getThemeManager()
   {
     return this.themeManager;
   }

   public void setThemeManager(IThemeManager themeManager) {
     this.themeManager = themeManager;
   }

   public ISiteManager getSiteManager() {
     return this.siteManager;
   }

   public void setSiteManager(ISiteManager siteManager) {
     this.siteManager = siteManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\solution\impl\ThemeInstaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */