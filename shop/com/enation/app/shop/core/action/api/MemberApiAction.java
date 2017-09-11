 package com.enation.app.shop.core.action.api;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.base.core.service.IRegionsManager;
 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.app.shop.core.service.IMemberPointManger;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.jms.EmailModel;
 import com.enation.framework.jms.EmailProducer;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.EncryptionUtil1;
 import com.enation.framework.util.FileUtil;
 import com.enation.framework.util.HttpUtil;
 import com.enation.framework.util.JsonMessageUtil;
 import com.enation.framework.util.RequestUtil;
 import com.enation.framework.util.StringUtil;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.ServletInputStream;
 import javax.servlet.http.HttpServletRequest;
 import net.sf.json.JSONObject;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.context.annotation.Scope;
 import org.springframework.mail.javamail.JavaMailSender;
 import org.springframework.stereotype.Component;










































 @Component
 @Scope("prototype")
 @ParentPackage("eop_default")
 @Namespace("/api/shop")
 @Action("member")
 public class MemberApiAction
   extends WWAction
 {
   private IMemberManager memberManager;
   private IAdminUserManager adminUserManager;
   private IRegionsManager regionsManager;
   private String username;
   private String password;
   private String validcode;
   private String remember;
   private Map memberMap;
   private String license;
   private String email;
   private String friendid;
   private File faceFile;
   private File face;
   private String faceFileName;
   private String faceFileFileName;
   private String photoServer;
   private String photoId;
   private String type;
   private String turename;
   private String oldpassword;
   private String newpassword;
   private String re_passwd;
   private Integer province_id;
   private Integer city_id;
   private Integer region_id;
   private String province;
   private String city;
   private String region;
   private String address;
   private String zip;
   private String mobile;
   private String tel;
   private String nickname;
   private String sex;
   private String mybirthday;
   private Integer lvid;
   private String keyword;
   private JavaMailSender mailSender;
   private EmailProducer mailMessageProducer;
   private IMemberPointManger memberPointManger;
   private File file;
   private String fileFileName;

   public String login()
   {
     if (validcode(this.validcode, "memberlogin") == 1) {
       int result = this.memberManager.login(this.username, this.password);
       if (result == 1)
       {
         if ((this.remember != null) && (this.remember.equals("1"))) {
           String cookieValue = EncryptionUtil1.authcode("{username:\"" + this.username + "\",password:\"" + StringUtil.md5(this.password) + "\"}", "ENCODE", "", 0);


           HttpUtil.addCookie(ThreadContextHolder.getHttpResponse(), "JavaShopUser", cookieValue, 20160);
         }
         showSuccessJson("登陆成功");
       } else {
         showErrorJson("账号密码错误");
       }
     } else {
       showErrorJson("验证码错误！");
     }
     return "json_message";
   }







   public String logout()
   {
     showSuccessJson("注销成功");
     this.memberManager.logout();
     return "json_message";
   }








   public String changePassword()
   {
     Member member = UserServiceFactory.getUserService().getCurrentMember();
     if (member == null) {
       showErrorJson("尚未登陆，无权使用此api");
       return "json_message";
     }
     String oldPassword = getOldpassword();
     oldPassword = oldPassword == null ? "" : StringUtil.md5(oldPassword);
     if (oldPassword.equals(member.getPassword())) {
       String password = getNewpassword();
       String passwd_re = getRe_passwd();

       if (passwd_re.equals(password)) {
         try {
           this.memberManager.updatePassword(password);
           showSuccessJson("修改密码成功");
         } catch (Exception e) {
           if (this.logger.isDebugEnabled()) {
             this.logger.error(e.getStackTrace());
           }
           showErrorJson("修改密码失败");
         }
       } else {
         showErrorJson("修改失败！两次输入的密码不一致");
       }
     } else {
       showErrorJson("修改失败！原始密码不符");
     }
     return "json_message";
   }








   public String password()
   {
     Member member = UserServiceFactory.getUserService().getCurrentMember();
     String old = this.oldpassword;
     String oldPassword = StringUtil.md5(old);
     if (oldPassword.equals(member.getPassword())) {
       showSuccessJson("正确");
     } else {
       showErrorJson("输入原始密码错误");
     }
     return "json_message";
   }










   public String search()
   {
     try
     {
       if (this.adminUserManager.getCurrentUser() == null) {
         showErrorJson("无权访问此api");
         return "json_message";
       }
       this.memberMap = new HashMap();
       this.memberMap.put("lvId", this.lvid);
       this.memberMap.put("keyword", this.keyword);
       this.memberMap.put("stype", Integer.valueOf(0));
       List memberList = this.memberManager.search(this.memberMap);
       this.json = JsonMessageUtil.getListJson(memberList);
     } catch (Throwable e) {
       this.logger.error("搜索会员出错", e);
       showErrorJson("搜索会员出错");
     }

     return "json_message";
   }



   public String checkname()
   {
     int result = this.memberManager.checkname(this.username);
     if (result == 0) {
       showSuccessJson("会员名称可以使用！");
     } else {
       showErrorJson("该会员名称已经存在！");
     }
     return "json_message";
   }



   public String checkemail()
   {
     int result = this.memberManager.checkemail(this.email);
     if (result == 0) {
       showSuccessJson("邮箱不存在，可以使用");
     } else {
       showErrorJson("该邮箱已经存在！");
     }
     return "json_message";
   }


   public String register()
   {
     if (validcode(this.validcode, "memberreg") == 0) {
       showErrorJson("验证码输入错误!");
       return "json_message";
     }
     if (!"agree".equals(this.license)) {
       showErrorJson("同意注册协议才可以注册!");
       return "json_message";
     }

     Member member = new Member();
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String registerip = request.getRemoteAddr();

     if (StringUtil.isEmpty(this.username)) {
       showErrorJson("用户名不能为空！");
       return "json_message";
     }
     if ((this.username.length() < 4) || (this.username.length() > 20)) {
       showErrorJson("用户名的长度为4-20个字符！");
       return "json_message";
     }
     if (this.username.contains("@")) {
       showErrorJson("用户名中不能包含@等特殊字符！");
       return "json_message";
     }
     if (StringUtil.isEmpty(this.email)) {
       showErrorJson("注册邮箱不能为空！");
       return "json_message";
     }
     if (!StringUtil.validEmail(this.email)) {
       showErrorJson("注册邮箱格式不正确！");
       return "json_message";
     }
     if (StringUtil.isEmpty(this.password)) {
       showErrorJson("密码不能为空！");
       return "json_message";
     }
     if (this.memberManager.checkname(this.username) > 0) {
       showErrorJson("此用户名已经存在，请您选择另外的用户名!");
       return "json_message";
     }
     if (this.memberManager.checkemail(this.email) > 0) {
       showErrorJson("此邮箱已经注册过，请您选择另外的邮箱!");
       return "json_message";
     }

     member.setMobile("");
     member.setUname(this.username);
     member.setPassword(this.password);
     member.setEmail(this.email);
     member.setRegisterip(registerip);
     if (!StringUtil.isEmpty(this.friendid)) {
       Member parentMember = this.memberManager.get(Integer.valueOf(Integer.parseInt(this.friendid)));
       if (parentMember != null) {
         member.setParentid(parentMember.getMember_id());
       }
     }
     else {
       String reg_Recomm = request.getParameter("reg_Recomm");
       if ((!StringUtil.isEmpty(reg_Recomm)) && (reg_Recomm.trim().equals(this.email.trim()))) {
         showErrorJson("推荐人的邮箱请不要填写自己的邮箱!");
         return "json_message";
       }
       if ((!StringUtil.isEmpty(reg_Recomm)) && (StringUtil.validEmail(reg_Recomm))) {
         Member parentMember = this.memberManager.getMemberByEmail(reg_Recomm);
         if (parentMember == null) {
           showErrorJson("您填写的推荐人不存在!");
           return "json_message";
         }
         member.setParentid(parentMember.getMember_id());
       }
     }


     int result = this.memberManager.register(member);
     if (result == 1) {
       this.memberManager.login(this.username, this.password);

       String mailurl = "http://mail." + org.apache.commons.lang3.StringUtils.split(member.getEmail(), "@")[1];






       this.json = JsonMessageUtil.getStringJson("mailurl", mailurl);
     } else {
       showErrorJson("用户名[" + member.getUname() + "]已存在!");
     }
     return "json_message";
   }


   public String reSendRegMail()
   {
     try
     {
       Member member = UserServiceFactory.getUserService().getCurrentMember();
       if (member == null) {
         showErrorJson("请您先登录再重新发送激活邮件!");
         return "json_message";
       }
       member = this.memberManager.get(member.getMember_id());
       if (member == null) {
         showErrorJson("用户不存在,请您先登录再重新发送激活邮件!");
         return "json_message";
       }
       if ((member.getLast_send_email() != null) && (System.currentTimeMillis() / 1000L - member.getLast_send_email().intValue() < 7200L)) {
         showErrorJson("对不起，两小时之内只能重新发送一次激活邮件!");
         return "json_message";
       }

       EopSite site = EopContext.getContext().getCurrentSite();
       String domain = RequestUtil.getDomain();
       String checkurl = domain + "/memberemailcheck.html?s=" + EncryptionUtil1.authcode(new StringBuilder().append(member.getMember_id()).append(",").append(member.getRegtime()).toString(), "ENCODE", "", 0);
       EmailModel emailModel = new EmailModel();
       emailModel.getData().put("username", member.getUname());
       emailModel.getData().put("checkurl", checkurl);
       emailModel.getData().put("sitename", site.getSitename());
       emailModel.getData().put("logo", site.getLogofile());
       emailModel.getData().put("domain", domain);
       if (this.memberPointManger.checkIsOpen("email_check")) {
         int point = this.memberPointManger.getItemPoint("email_check_num");
         int mp = this.memberPointManger.getItemPoint("email_check_num_mp");
         emailModel.getData().put("point", Integer.valueOf(point));
         emailModel.getData().put("mp", Integer.valueOf(mp));
       }
       emailModel.setTitle(member.getUname() + "您好，" + site.getSitename() + "会员注册成功!");
       emailModel.setEmail(member.getEmail());
       emailModel.setTemplate("reg_email_template.html");
       emailModel.setEmail_type("邮箱激活");
       this.mailMessageProducer.send(emailModel);
       member.setLast_send_email(Integer.valueOf(DateUtil.getDateline()));
       this.memberManager.edit(member);
       showSuccessJson("激活邮件发送成功，请登录您的邮箱 " + member.getEmail() + " 进行查收！");
     } catch (RuntimeException e) {
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }

   public String saveInfo()
   {
     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();

     member = this.memberManager.get(member.getMember_id());

     String encoding = EopSetting.ENCODING;
     if (StringUtil.isEmpty(encoding)) {
       encoding = "UTF-8";
     }

     String faceField = "faceFile";

     if (this.file != null)
     {

       String allowTYpe = "gif,jpg,bmp,png";
       if ((!this.fileFileName.trim().equals("")) && (this.fileFileName.length() > 0)) {
         String ex = this.fileFileName.substring(this.fileFileName.lastIndexOf(".") + 1, this.fileFileName.length());
         if (allowTYpe.toString().indexOf(ex.toLowerCase()) < 0) {
           showErrorJson("对不起,只能上传gif,jpg,bmp,png格式的图片！");
           return "json_message";
         }
       }



       if (this.file.length() > 204800L) {
         showErrorJson("'对不起,图片不能大于200K！");
         return "json_message";
       }

       String imgPath = UploadUtil.upload(this.file, this.fileFileName, faceField);
       member.setFace(imgPath);
     }

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();

     if (StringUtil.isEmpty(this.mybirthday)) {
       member.setBirthday(Long.valueOf(0L));
     } else {
       member.setBirthday(Long.valueOf(DateUtil.getDatelineLong(this.mybirthday)));
     }


     member.setProvince_id(this.province_id);
     member.setCity_id(this.city_id);
     member.setRegion_id(this.region_id);
     member.setProvince(this.province);
     member.setCity(this.city);
     member.setRegion(this.region);
     member.setAddress(this.address);
     member.setZip(this.zip);
     if (this.mobile != null) {
       member.setMobile(this.mobile);
     }
     member.setTel(this.tel);
     if (this.nickname != null) {
       member.setNickname(this.nickname);
     }
     member.setSex(Integer.valueOf(this.sex));



     String midentity = request.getParameter("member.midentity");
     if (!StringUtil.isEmpty(midentity)) {
       member.setMidentity(Integer.valueOf(StringUtil.toInt(midentity)));
     } else {
       member.setMidentity(Integer.valueOf(0));
     }




     try
     {
       boolean addPoint = false;
       if ((member.getInfo_full() == 0) && (!StringUtil.isEmpty(member.getName())) && (!StringUtil.isEmpty(member.getNickname())) && (!StringUtil.isEmpty(member.getProvince())) && (!StringUtil.isEmpty(member.getCity())) && (!StringUtil.isEmpty(member.getRegion())) && ((!StringUtil.isEmpty(member.getMobile())) || (!StringUtil.isEmpty(member.getTel()))))
       {


         addPoint = true;
       }

       if (addPoint) {
         member.setInfo_full(1);
         this.memberManager.edit(member);
         if (this.memberPointManger.checkIsOpen("finish_profile")) {
           int point = this.memberPointManger.getItemPoint("finish_profile_num");
           int mp = this.memberPointManger.getItemPoint("finish_profile_num_mp");
           this.memberPointManger.add(member.getMember_id().intValue(), point, "完善个人资料", null, mp);
         }
       } else {
         this.memberManager.edit(member);
       }
       showSuccessJson("编辑个人资料成功！");
       return "json_message";
     } catch (Exception e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.error(e.getStackTrace());
       }
       showErrorJson("编辑个人资料失！");
     }
     return "json_message";
   }



   protected String toUrl(String path)
   {
     String urlBase = EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath();

     return path.replaceAll("fs:", urlBase);
   }

   protected String makeFilename(String subFolder) {
     String ext = FileUtil.getFileExt(this.photoServer);
     String fileName = this.photoId + "_" + this.type + "." + ext;

     String filePath = EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/attachment/";

     if (subFolder != null) {
       filePath = filePath + subFolder + "/";
     }

     filePath = filePath + fileName;
     return filePath;
   }





   public String saveAvatar()
   {
     String targetFile = makeFilename("avatar");

     int potPos = targetFile.lastIndexOf('/') + 1;
     String folderPath = targetFile.substring(0, potPos);
     FileUtil.createFolder(folderPath);
     try
     {
       File file = new File(targetFile);

       if (!file.exists()) {
         file.createNewFile();
       }

       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       FileOutputStream dos = new FileOutputStream(file);
       int x = request.getInputStream().read();
       while (x > -1) {
         dos.write(x);
         x = request.getInputStream().read();
       }
       dos.flush();
       dos.close();
     } catch (Exception e) {
       e.printStackTrace();
     }

     if ("big".equals(this.type)) {
       Member member = UserServiceFactory.getUserService().getCurrentMember();

       member.setFace("fs:/attachment/avatar/" + this.photoId + "_big." + FileUtil.getFileExt(this.photoServer));

       this.memberManager.edit(member);
     }

     this.json = ("{\"data\":{\"urls\":[\"" + targetFile + "\"]},\"status\":1,\"statusText\":\"保存存成功\"}");


     return "json_message";
   }





   public String uploadAvatar()
   {
     JSONObject jsonObject = new JSONObject();
     try {
       if (this.faceFile != null) {
         String file = UploadUtil.upload(this.face, this.faceFileName, "avatar");
         Member member = UserServiceFactory.getUserService().getCurrentMember();

         jsonObject.put("result", Integer.valueOf(1));
         jsonObject.put("member_id", member.getMember_id());
         jsonObject.put("url", toUrl(file));
         jsonObject.put("message", "操作成功！");
       }
     } catch (Exception e) {
       jsonObject.put("result", Integer.valueOf(0));
       jsonObject.put("message", "操作失败！");
     }

     this.json = jsonObject.toString();

     return "json_message";
   }







   private int validcode(String validcode, String name)
   {
     if (validcode == null) {
       return 0;
     }

     String code = (String)ThreadContextHolder.getSessionContext().getAttribute("valid_code" + name);
     if (code == null) {
       return 0;
     }
     if (!code.equalsIgnoreCase(validcode)) {
       return 0;
     }

     return 1;
   }

   public IMemberManager getMemberManager() { return this.memberManager; }

   public void setMemberManager(IMemberManager memberManager)
   {
     this.memberManager = memberManager;
   }

   public String getUsername() {
     return this.username;
   }

   public void setUsername(String username) {
     this.username = username;
   }

   public String getPassword() {
     return this.password;
   }

   public void setPassword(String password) {
     this.password = password;
   }

   public File getFace() {
     return this.face;
   }

   public void setFace(File face) {
     this.face = face;
   }

   public String getFaceFileName() {
     return this.faceFileName;
   }

   public void setFaceFileName(String faceFileName) {
     this.faceFileName = faceFileName;
   }

   public String getPhotoServer() {
     return this.photoServer;
   }

   public void setPhotoServer(String photoServer) {
     this.photoServer = photoServer;
   }

   public String getPhotoId() {
     return this.photoId;
   }

   public void setPhotoId(String photoId) {
     this.photoId = photoId;
   }

   public String getType() {
     return this.type;
   }

   public void setType(String type) {
     this.type = type;
   }

   public String getOldpassword() {
     return this.oldpassword;
   }

   public void setOldpassword(String oldpassword) {
     this.oldpassword = oldpassword;
   }

   public String getNewpassword() {
     return this.newpassword;
   }

   public void setNewpassword(String newpassword) {
     this.newpassword = newpassword;
   }

   public String getRe_passwd() {
     return this.re_passwd;
   }

   public void setRe_passwd(String re_passwd) {
     this.re_passwd = re_passwd;
   }

   public Integer getLvid() {
     return this.lvid;
   }

   public void setLvid(Integer lvid) {
     this.lvid = lvid;
   }

   public String getKeyword() {
     return this.keyword;
   }

   public void setKeyword(String keyword) {
     this.keyword = keyword;
   }

   public IAdminUserManager getAdminUserManager() {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) {
     this.adminUserManager = adminUserManager;
   }

   public String getValidcode() {
     return this.validcode;
   }

   public void setValidcode(String validcode) {
     this.validcode = validcode;
   }

   public String getRemember() {
     return this.remember;
   }

   public void setRemember(String remember) {
     this.remember = remember;
   }

   public Map getMemberMap() {
     return this.memberMap;
   }

   public void setMemberMap(Map memberMap) {
     this.memberMap = memberMap;
   }

   public String getLicense() {
     return this.license;
   }

   public void setLicense(String license) {
     this.license = license;
   }

   public String getEmail() {
     return this.email;
   }

   public void setEmail(String email) {
     this.email = email;
   }

   public String getFriendid() {
     return this.friendid;
   }

   public void setFriendid(String friendid) {
     this.friendid = friendid;
   }

   public JavaMailSender getMailSender() {
     return this.mailSender;
   }

   public void setMailSender(JavaMailSender mailSender) {
     this.mailSender = mailSender;
   }

   public EmailProducer getMailMessageProducer() {
     return this.mailMessageProducer;
   }

   public void setMailMessageProducer(EmailProducer mailMessageProducer) {
     this.mailMessageProducer = mailMessageProducer;
   }

   public IMemberPointManger getMemberPointManger() {
     return this.memberPointManger;
   }

   public void setMemberPointManger(IMemberPointManger memberPointManger) {
     this.memberPointManger = memberPointManger;
   }

   public File getFaceFile() {
     return this.faceFile;
   }

   public void setFaceFile(File faceFile) {
     this.faceFile = faceFile;
   }

   public String getTurename() {
     return this.turename;
   }

   public void setTurename(String turename) {
     this.turename = turename;
   }

   public IRegionsManager getRegionsManager() {
     return this.regionsManager;
   }

   public void setRegionsManager(IRegionsManager regionsManager) {
     this.regionsManager = regionsManager;
   }

   public String getFaceFileFileName() {
     return this.faceFileFileName;
   }

   public void setFaceFileFileName(String faceFileFileName) {
     this.faceFileFileName = faceFileFileName;
   }

   public File getFile() {
     return this.file;
   }

   public void setFile(File file) {
     this.file = file;
   }

   public String getFileFileName() {
     return this.fileFileName;
   }

   public void setFileFileName(String fileFileName) {
     this.fileFileName = fileFileName;
   }

   public Integer getProvince_id() {
     return this.province_id;
   }

   public void setProvince_id(Integer province_id) {
     this.province_id = province_id;
   }

   public Integer getCity_id() {
     return this.city_id;
   }

   public void setCity_id(Integer city_id) {
     this.city_id = city_id;
   }

   public Integer getRegion_id() {
     return this.region_id;
   }

   public void setRegion_id(Integer region_id) {
     this.region_id = region_id;
   }

   public String getProvince() {
     return this.province;
   }

   public void setProvince(String province) {
     this.province = province;
   }

   public String getCity() {
     return this.city;
   }

   public void setCity(String city) {
     this.city = city;
   }

   public String getRegion() {
     return this.region;
   }

   public void setRegion(String region) {
     this.region = region;
   }

   public String getAddress() {
     return this.address;
   }

   public void setAddress(String address) {
     this.address = address;
   }

   public String getZip() {
     return this.zip;
   }

   public void setZip(String zip) {
     this.zip = zip;
   }

   public String getMobile() {
     return this.mobile;
   }

   public void setMobile(String mobile) {
     this.mobile = mobile;
   }

   public String getTel() {
     return this.tel;
   }

   public void setTel(String tel) {
     this.tel = tel;
   }

   public String getSex() {
     return this.sex;
   }

   public void setSex(String sex) {
     this.sex = sex;
   }

   public String getMybirthday() {
     return this.mybirthday;
   }

   public void setMybirthday(String mybirthday) {
     this.mybirthday = mybirthday;
   }

   public String getNickname() {
     return this.nickname;
   }

   public void setNickname(String nickname) {
     this.nickname = nickname;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\api\MemberApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */