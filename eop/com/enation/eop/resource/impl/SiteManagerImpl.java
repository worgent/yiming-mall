 package com.enation.eop.resource.impl;

 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
 import com.enation.app.base.core.service.solution.ISetupLoader;
 import com.enation.eop.resource.IAppManager;
 import com.enation.eop.resource.IDomainManager;
 import com.enation.eop.resource.ISiteManager;
 import com.enation.eop.resource.IThemeManager;
 import com.enation.eop.resource.model.Dns;
 import com.enation.eop.resource.model.EopApp;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.resource.model.EopSiteDomain;
 import com.enation.eop.resource.model.EopUser;
 import com.enation.eop.resource.model.Theme;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.ISqlFileExecutor;
 import com.enation.framework.database.Page;
 import com.enation.framework.database.StringMapper;
 import com.enation.framework.resource.ResourceStateManager;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.FileUtil;
 import com.enation.framework.util.StringUtil;
 import java.io.File;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import javax.xml.parsers.DocumentBuilder;
 import javax.xml.parsers.DocumentBuilderFactory;
 import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;
 import org.w3c.dom.Document;
 import org.w3c.dom.Element;
 import org.w3c.dom.Node;
 import org.w3c.dom.NodeList;









 public class SiteManagerImpl
   implements ISiteManager
 {
   private IDaoSupport<EopSite> daoSupport;
   private IDomainManager domainManager;
   private IThemeManager themeManager;
   private ISqlFileExecutor sqlFileExecutor;
   private IAdminUserManager adminUserManager;
   private IAppManager appManager;
   private ISetupLoader setupLoader;

   @Transactional(propagation=Propagation.REQUIRED)
   public int addDomain(EopSiteDomain eopSiteDomain)
   {
     if (checkInDomain(eopSiteDomain.getDomain()).booleanValue()) {
       throw new IllegalArgumentException("域名[" + eopSiteDomain.getDomain() + "]已存在！");
     }
     this.daoSupport.insert("eop_sitedomain", eopSiteDomain);
     return this.daoSupport.getLastId("eop_sitedomain");
   }

   public Boolean checkInDomain(String domain) {
     String sql = "select count(0) from eop_sitedomain where domain = ?";
     int count = this.daoSupport.queryForInt(sql, new Object[] { domain });
     return Boolean.valueOf(count != 0);
   }

   public void deleteDomain(String domain) {
     String sql = "delete from eop_sitedomain where domain=?";
     this.daoSupport.execute(sql, new Object[] { domain });
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public Integer add(EopUser user, EopSite site, String domain) {
     int userid = user.getId().intValue();
     site.setUserid(Integer.valueOf(userid));

     if (site.getIcofile() == null)
       site.setIcofile(EopSetting.IMG_SERVER_DOMAIN + "/images/favicon.ico");
     if (site.getLogofile() == null) {
       site.setLogofile(EopSetting.IMG_SERVER_DOMAIN + "/images/logo.gif");
     }
     site.setPoint(1000);


     site.setCreatetime(Long.valueOf(DateUtil.getDateline()));
     site.setLastlogin(Long.valueOf(0L));
     site.setLastgetpoint(DateUtil.getDateline());


     this.daoSupport.insert("eop_site", site);
     Integer siteid = Integer.valueOf(this.daoSupport.getLastId("eop_site"));

     EopSiteDomain eopSiteDomain = new EopSiteDomain();
     eopSiteDomain.setDomain(domain);
     eopSiteDomain.setSiteid(siteid);
     eopSiteDomain.setUserid(Integer.valueOf(userid));


     addDomain(eopSiteDomain);

     return siteid;
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public Integer add(EopSite site, String domain)
   {
     WebSessionContext<EopUser> sessonContext = ThreadContextHolder.getSessionContext();
     EopUser user = (EopUser)sessonContext.getAttribute("eop_user_key");
     return add(user, site, domain);
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void deleteDomain(Integer domainid) {
     EopSiteDomain domain = this.domainManager.get(domainid);
     UserUtil.validUser(domain.getUserid());
     int domainCount = this.domainManager.getDomainCount(domain.getSiteid());
     if (domainCount <= 1) {
       throw new RuntimeException("此站点只有一个域名不可删除，删除后将不可访问");
     }
     String sql = "delete from eop_sitedomain where id=?";
     this.daoSupport.execute(sql, new Object[] { domainid });
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void delete(Integer id) {
     EopSite site = get(id);

     Integer userid = site.getUserid();
     Integer siteid = site.getId();


     String sql = "show tables like '%_" + userid + "_" + siteid + "'";
     List tableList = this.daoSupport.queryForList(sql, new StringMapper(), new Object[0]);
     for (Object tbName : tableList) {
       sql = "drop table if exists " + tbName;
       this.daoSupport.execute(sql, new Object[0]);
     }


     sql = "delete from eop_sitedomain where siteid = ?";
     this.daoSupport.execute(sql, new Object[] { id });
     sql = "delete from  eop_site  where id = ?";
     this.daoSupport.execute(sql, new Object[] { id });


     String userTplFile = EopSetting.EOP_PATH + "/user/" + userid + "/" + siteid;
     String userStyleFile = EopSetting.IMG_SERVER_PATH + "/user/" + userid + "/" + siteid;
     FileUtil.delete(userTplFile);
     FileUtil.delete(userStyleFile);
   }



   public List getDnsList()
   {
     String sql = "select a.domain as domain,a.siteid as siteid, b.* from eop_sitedomain a left join eop_site b on b.id = a.siteid";
     return this.daoSupport.queryForList(sql, new DnsMapper(), new Object[0]);
   }




   private static class ValueConvert
   {
     public static String replacePrefix(String in, EopSite site)
     {
       String replaceString = null;
       if ("2".equals(EopSetting.RUNMODE)) {
         replaceString = EopSetting.IMG_SERVER_DOMAIN + "/user/" + site.getUserid() + "/" + site.getId();
       } else {
         replaceString = EopSetting.IMG_SERVER_DOMAIN;
       }

       if (in != null)
         return in.replaceAll(EopSetting.FILE_STORE_PREFIX, replaceString);
       return in;
     }
   }

   private static class DnsMapper implements ParameterizedRowMapper<Dns> {
     public Dns mapRow(ResultSet rs, int rowNum) throws SQLException {
       Dns dns = new Dns();

       dns.setDomain(rs.getString("domain"));
       EopSite site = new EopSite();
       site.setId(Integer.valueOf(rs.getInt("siteid")));
       site.setAdminthemeid(Integer.valueOf(rs.getInt("adminthemeid")));
       site.setThemeid(Integer.valueOf(rs.getInt("themeid")));
       site.setSitename(rs.getString("sitename"));
       site.setUserid(Integer.valueOf(rs.getInt("userid")));
       site.setKeywords(rs.getString("keywords"));
       site.setDescript(rs.getString("descript"));
       site.setThemepath(rs.getString("themepath"));
       site.setPoint(rs.getInt("point"));

       site.setCreatetime(Long.valueOf(rs.getLong("createtime")));
       site.setLastgetpoint(rs.getLong("lastgetpoint"));
       site.setLastlogin(Long.valueOf(rs.getLong("lastlogin")));
       site.setLogincount(rs.getInt("logincount"));

       site.setCopyright(rs.getString("copyright"));
       site.setTitle(rs.getString("title"));
       site.setUsername(rs.getString("username"));
       site.setUsertel(rs.getString("usertel"));
       site.setUsermobile(rs.getString("usermobile"));
       site.setUsertel1(rs.getString("usertel1"));
       site.setUseremail(rs.getString("useremail"));
       site.setIcp(rs.getString("icp"));
       site.setAddress(rs.getString("address"));
       site.setZipcode(rs.getString("zipcode"));
       site.setSiteon(Integer.valueOf(rs.getInt("siteon")));
       site.setState(Integer.valueOf(rs.getInt("state")));
       site.setQq(Integer.valueOf(rs.getInt("qq")));
       site.setMsn(Integer.valueOf(rs.getInt("msn")));
       site.setWw(Integer.valueOf(rs.getInt("ww")));
       site.setTel(Integer.valueOf(rs.getInt("tel")));
       site.setWt(Integer.valueOf(rs.getInt("wt")));
       site.setQqlist(rs.getString("qqlist"));
       site.setMsnlist(rs.getString("msnlist"));
       site.setWwlist(rs.getString("wwlist"));
       site.setTellist(rs.getString("tellist"));
       site.setWorktime(rs.getString("worktime"));
       site.setLinkman(rs.getString("linkman"));
       site.setLinktel(rs.getString("linktel"));
       site.setEmail(rs.getString("email"));
       site.setClosereson(rs.getString("closereson"));


       site.setRelid(rs.getString("relid"));
       site.setImptype(rs.getInt("imptype"));
       site.setSitestate(rs.getInt("sitestate"));
       site.setIsimported(rs.getInt("isimported"));

       site.setIcofile(SiteManagerImpl.ValueConvert.replacePrefix(rs.getString("icofile"), site));
       site.setLogofile(SiteManagerImpl.ValueConvert.replacePrefix(rs.getString("logofile"), site));
       site.setBklogofile(SiteManagerImpl.ValueConvert.replacePrefix(rs.getString("bklogofile"), site));
       site.setBkloginpicfile(SiteManagerImpl.ValueConvert.replacePrefix(rs.getString("bkloginpicfile"), site));

       site.setMulti_site(Integer.valueOf(rs.getInt("multi_site")));
       site.setProductid(rs.getString("productid"));
       site.setMobilesite(Integer.valueOf(rs.getInt("mobilesite")));

       dns.setSite(site);

       return dns;
     }
   }

   public EopSite get(Integer id) {
     String sql = "select * from eop_site where  id = ?";
     EopSite site = (EopSite)this.daoSupport.queryForObject(sql, EopSite.class, new Object[] { id });
     String icofile = site.getIcofile();
     if (icofile != null)
       icofile = UploadUtil.replacePath(icofile);
     site.setIcofile(icofile);

     site.setLogofile(ValueConvert.replacePrefix(site.getLogofile(), site));
     site.setBklogofile(ValueConvert.replacePrefix(site.getBklogofile(), site));
     site.setBkloginpicfile(ValueConvert.replacePrefix(site.getBkloginpicfile(), site));

     return site;
   }

   public EopSite get(String domain) {
     String sql = "select a.* from eop_site a join eop_sitedomain b on b.siteid = a.id and b.domain= ?";
     List<EopSite> sitelist = this.daoSupport.queryForList(sql, EopSite.class, new Object[] { domain });
     if ((sitelist == null) || (sitelist.isEmpty()))
       return null;
     EopSite site = (EopSite)sitelist.get(0);

     site.setIcofile(ValueConvert.replacePrefix(site.getIcofile(), site));
     site.setLogofile(ValueConvert.replacePrefix(site.getLogofile(), site));
     site.setBklogofile(ValueConvert.replacePrefix(site.getBklogofile(), site));
     site.setBkloginpicfile(ValueConvert.replacePrefix(site.getBkloginpicfile(), site));

     return site;
   }












   public Page list(String keyword, int sitestate, int pageNo, int pageSize)
   {
     String sql = "select s.id,s.sitename,s.point,s.createtime,s.sitestate,s.logincount,s.lastlogin,u.username from eop_site s,eop_user u where s.userid=u.id";



     String sitesql = sitestate > 0 ? " and s.sitestate=" + (sitestate - 1) : "";

     if (!StringUtil.isEmpty(keyword)) {
       sql = sql + " and (s.sitename like '%" + keyword + "%' ";
       sql = sql + " or u.username like '%" + keyword + "%'";
       sql = sql + " or s.id in(select siteid from eop_sitedomain where domain like '%" + keyword + "%'))";
     }
     sql = sql + sitesql + " order by s.createtime desc";

     return this.daoSupport.queryForPage(sql, pageNo, pageSize, new Object[0]);
   }

   public Page list(int pageNo, int pageSize, String order, String search) {
     Integer userid = EopContext.getContext().getCurrentSite().getUserid();
     List<EopSiteDomain> listdomain = this.domainManager.listUserDomain();
     if (search == null) {
       search = "";
     } else
       search = " and sitename like '%" + search + "%'";
     if (order == null) {
       order = "";
     } else {
       order = " order by " + order.replace(":", " ");
     }
     Page page = this.daoSupport.queryForPage("select * from eop_site where deleteflag = 0 and userid = " + userid + search + order, pageNo, pageSize, new Object[0]);

     List<Map> listsite = (List)page.getResult();

     for (Map site : listsite) {
       List<EopSiteDomain> domainList = new ArrayList();
       String logofile = site.get("logofile").toString();
       if (logofile != null)
         logofile = logofile.replaceAll(EopSetting.FILE_STORE_PREFIX, EopSetting.IMG_SERVER_DOMAIN + "/user/" + site.get("userid").toString() + "/" + site.get("id").toString());
       site.put("logofile", logofile);
       for (EopSiteDomain siteDomain : listdomain) {
         if (site.get("id").toString().equals(siteDomain.getSiteid().toString())) {
           domainList.add(siteDomain);
         }
       }
       site.put("eopSiteDomainList", domainList);
     }

     return page;
   }

   public void edit(EopSite eopSite) {
     eopSite.setPoint(get(eopSite.getId()).getPoint());
     this.daoSupport.update("eop_site", eopSite, "id = " + eopSite.getId());
   }

   public void setSiteProduct(Integer userid, Integer siteid, String productid) {
     String sql = "update eop_site set productid=? where userid=? and id=?";
     this.daoSupport.execute(sql, new Object[] { productid, userid, siteid });
   }

   public void changeAdminTheme(Integer themeid) {
     EopSite site = EopContext.getContext().getCurrentSite();
     String sql = "update eop_site set adminthemeid=? where userid=? and id=?";

     this.daoSupport.execute(sql, new Object[] { themeid, site.getUserid(), site.getId() });
     EopContext.getContext().getCurrentSite().setAdminthemeid(themeid);
   }

   public void changeTheme(Integer themeid) {
     EopSite site = EopContext.getContext().getCurrentSite();
     Theme theme = this.themeManager.getTheme(themeid);
     String sql = "update eop_site set themeid=?,themepath=? where userid=? and id=?";
     this.daoSupport.execute(sql, new Object[] { themeid, theme.getPath(), site.getUserid(), site.getId() });
     site.setThemeid(themeid);
     site.setThemepath(theme.getPath());
     ResourceStateManager.setDisplayState(1);
   }

   public List listDomain(Integer userid, Integer siteid) {
     String sql = "select * from eop_sitedomain where userid=? and siteid=?";
     return this.daoSupport.queryForList(sql, EopSiteDomain.class, new Object[] { userid, siteid });
   }

   public void updatePoint(Integer id, int point, int sitestate) {
     this.daoSupport.execute("update eop_site set point=?,sitestate=? where id=?", new Object[] { Integer.valueOf(point), Integer.valueOf(sitestate), id });
   }



   public int getPoint(Integer id, int point)
   {
     EopSite site = EopContext.getContext().getCurrentSite();
     long lastgetpoint = site.getLastgetpoint();
     long now = (int)(System.currentTimeMillis() / 1000L);
     int onemonth = 2592000;
     if (now - lastgetpoint > onemonth) {
       this.daoSupport.execute("update eop_site set point=point+? where id=?", new Object[] { Integer.valueOf(point), id });
       site.setPoint(site.getPoint() + point);
       site.setLastgetpoint(DateUtil.getDateline());
       this.daoSupport.execute("update eop_site set lastgetpoint=? where id=?", new Object[] { Integer.valueOf(DateUtil.getDateline()), id });

       return 1;
     }
     return 0;
   }





   public void initData()
   {
     EopSite site = EopContext.getContext().getCurrentSite();
     String productId = site.getProductid();
     String dataPath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/init.xml";
     if (!DBSolutionFactory.dbImport(dataPath, ""))
       throw new RuntimeException("初始化数据出错...");
   }

   public ISqlFileExecutor getSqlFileExecutor() {
     return this.sqlFileExecutor;
   }

   public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
     this.sqlFileExecutor = sqlFileExecutor;
   }



   public List<Map> list()
   {
     String sql = "select * from eop_site ";
     return this.daoSupport.queryForList(sql, new Object[0]);
   }



   public List<Map> list(Integer userid)
   {
     String sql = "select * from eop_site where userid=?";
     List<Map> list = this.daoSupport.queryForList(sql, new Object[] { userid });
     for (Map site : list) {
       List<EopSiteDomain> domainList = this.domainManager.listSiteDomain(Integer.valueOf(Integer.parseInt(site.get("id").toString())));
       site.put("eopSiteDomainList", domainList);
     }
     return list;
   }



   public List<EopApp> getSiteApps()
   {
     if (EopSetting.RUNMODE.equals("2")) {
       List<EopApp> appList = listSaasApp();
       return appList;
     }
     List<EopApp> appList = this.appManager.list();
     return appList;
   }

   private List<EopApp> listSaasApp()
   {
     List<EopApp> appList = new ArrayList();

     String xmlFile = EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/profile.xml";
     if (new File(xmlFile).exists()) {
       try {
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         DocumentBuilder builder = factory.newDocumentBuilder();
         Document document = builder.parse("file:///"+xmlFile);
         NodeList nodeList = document.getElementsByTagName("apps");
         if (nodeList != null)
         {
           Node appsNode = nodeList.item(0);
           NodeList appNodeList = appsNode.getChildNodes();


           int i = 0; for (int len = appNodeList.getLength(); i < len; i++) {
             Node node = appNodeList.item(i);
             if (node.getNodeType() == 1) {
               Element appNode = (Element)node;
               EopApp eopApp = new EopApp();
               eopApp.setAppid(appNode.getAttribute("id"));
               eopApp.setVersion(appNode.getAttribute("version"));
               appList.add(eopApp);
             }
           }
         }
         return appList;
       } catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException("加载本站点根目录的profile.xml失败，不能导出。");
       }
     }
     throw new RuntimeException("本站点根目录下未含有profile.xml，不能导出。");
   }

   public IAdminUserManager getAdminUserManager()
   {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) {
     this.adminUserManager = adminUserManager;
   }

   public void editDomain(String domainOld, String domainNew) {
     this.daoSupport.execute("update eop_sitedomain set domain = ? where domain = ?", new Object[] { domainNew, domainOld });
   }

   public IDaoSupport<EopSite> getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport<EopSite> daoSupport) {
     this.daoSupport = daoSupport;
   }

   public IDomainManager getDomainManager() {
     return this.domainManager;
   }

   public void setDomainManager(IDomainManager domainManager) {
     this.domainManager = domainManager;
   }

   public IThemeManager getThemeManager() {
     return this.themeManager;
   }

   public void setThemeManager(IThemeManager themeManager) {
     this.themeManager = themeManager;
   }

   public IAppManager getAppManager() {
     return this.appManager;
   }

   public void setAppManager(IAppManager appManager) {
     this.appManager = appManager;
   }

   public ISetupLoader getSetupLoader() {
     return this.setupLoader;
   }

   public void setSetupLoader(ISetupLoader setupLoader) {
     this.setupLoader = setupLoader;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\impl\SiteManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */