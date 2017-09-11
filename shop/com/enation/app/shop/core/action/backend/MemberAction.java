 package com.enation.app.shop.core.action.backend;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.base.core.model.MemberLv;
 import com.enation.app.base.core.service.IRegionsManager;
 import com.enation.app.shop.core.model.AdvanceLogs;
 import com.enation.app.shop.core.model.PointHistory;
 import com.enation.app.shop.core.plugin.member.MemberPluginBundle;
 import com.enation.app.shop.core.service.IAdvanceLogsManager;
 import com.enation.app.shop.core.service.IMemberCommentManager;
 import com.enation.app.shop.core.service.IMemberLvManager;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.app.shop.core.service.IPointHistoryManager;
 import com.enation.app.shop.core.service.OrderStatus;
 import com.enation.eop.resource.IUserManager;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.eop.resource.model.EopUser;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;


































 @Component
 @Scope("prototype")
 @ParentPackage("shop_default")
 @Namespace("/shop/admin")
 @Action("member")
 @Results({@org.apache.struts2.convention.annotation.Result(name="add_lv", type="freemarker", location="/shop/admin/member/lv_add.html"), @org.apache.struts2.convention.annotation.Result(name="edit_lv", type="freemarker", location="/shop/admin/member/lv_edit.html"), @org.apache.struts2.convention.annotation.Result(name="list_lv", type="freemarker", location="/shop/admin/member/lv_list.html"), @org.apache.struts2.convention.annotation.Result(name="add_member", type="freemarker", location="/shop/admin/member/member_add.html"), @org.apache.struts2.convention.annotation.Result(name="edit_member", type="freemarker", location="/shop/admin/member/member_edit.html"), @org.apache.struts2.convention.annotation.Result(name="list_member", type="freemarker", location="/shop/admin/member/member_list.html"), @org.apache.struts2.convention.annotation.Result(name="detail", type="freemarker", location="/shop/admin/member/member_detail.html"), @org.apache.struts2.convention.annotation.Result(name="base", location="/shop/admin/member/member_base.jsp"), @org.apache.struts2.convention.annotation.Result(name="edit", location="/shop/admin/>member/member_edit.jsp"), @org.apache.struts2.convention.annotation.Result(name="orderLog", location="/shop/admin/member/member_orderLog.jsp"), @org.apache.struts2.convention.annotation.Result(name="editPoint", location="/shop/admin/member/member_editPoint.jsp"), @org.apache.struts2.convention.annotation.Result(name="pointLog", location="/shop/admin/member/member_pointLog.jsp"), @org.apache.struts2.convention.annotation.Result(name="advance", location="/shop/admin/member/member_advance.jsp"), @org.apache.struts2.convention.annotation.Result(name="comments", location="/shop/admin/member/member_comments.jsp"), @org.apache.struts2.convention.annotation.Result(name="remark", location="/shop/admin/member/member_remark.jsp"), @org.apache.struts2.convention.annotation.Result(name="syslogin", location="/shop/admin/member/syslogin.jsp")})
 public class MemberAction
   extends WWAction
 {
   private IMemberManager memberManager;
   private IMemberLvManager memberLvManager;
   private IRegionsManager regionsManager;
   private IPointHistoryManager pointHistoryManager;
   private IAdvanceLogsManager advanceLogsManager;
   private IMemberCommentManager memberCommentManager;
   private MemberPluginBundle memberPluginBundle;
   private IUserManager userManager;
   private Member member;
   private MemberLv lv;
   private String birthday;
   private Integer[] lv_id;
   private Integer memberId;
   private Integer[] member_id;
   private List lvlist;
   private List provinceList;
   private List cityList;
   private List regionList;
   private List listPointHistory;
   private List listAdvanceLogs;
   private List listComments;
   private int point;
   private int pointtype;
   private Double modify_advance;
   private String modify_memo;
   private String object_type;
   private String name;
   private String uname;
   private Integer mobile;
   private String email;
   private Integer sex = Integer.valueOf(2);

   private Integer lvId;

   private List catDiscountList;

   private int[] cat_ids;
   private int[] cat_discounts;
   private Map memberMap;
   private String start_time;
   private String end_time;
   private Integer stype;
   private String keyword;
   private Integer province_id;
   private Integer city_id;
   private Integer region_id;
   protected Map<Integer, String> pluginTabs;
   protected Map<Integer, String> pluginHtmls;
   private Map statusMap;
   private String status_Json;
   private String message;

   public String add_lv()
   {
     return "add_lv";
   }

   public String edit_lv() {
     this.lv = this.memberLvManager.get(this.lvId);
     return "edit_lv";
   }

   public String list_lv() {
     return "list_lv";
   }

   public String list_lvJson() {
     this.webpage = this.memberLvManager.list(getSort(), getPage(), getPageSize());
     showGridJson(this.webpage);
     return "json_message";
   }

   public String saveAddLv() {
     this.memberLvManager.add(this.lv);
     showSuccessJson("会员等级添加成功");
     return "json_message";
   }

   public String saveEditLv()
   {
     try {
       this.memberLvManager.edit(this.lv);
       showSuccessJson("会员等级修改成功");
     } catch (Exception e) {
       showErrorJson("非法参数");
     }
     return "json_message";
   }

   public String deletelv() {
     try {
       this.memberLvManager.delete(this.lv_id);
       showSuccessJson("删除成功");
     } catch (RuntimeException e) {
       showErrorJson("删除失败");
     }
     return "json_message";
   }

   public String add_member() {
     if (this.lvlist == null) {
       this.lvlist = this.memberLvManager.list();
     }
     this.provinceList = this.regionsManager.listProvince();
     return "add_member";
   }

   public String edit_member() {
     this.member = this.memberManager.get(this.memberId);
     if (this.lvlist == null) {
       this.lvlist = this.memberLvManager.list();
     }
     return "edit_member";
   }

   public String memberlist() {
     this.lvlist = this.memberLvManager.list();
     return "list_member";
   }

   public String memberlistJson()
   {
     this.memberMap = new HashMap();
     this.memberMap.put("stype", this.stype);
     this.memberMap.put("keyword", this.keyword);
     this.memberMap.put("uname", this.uname);
     this.memberMap.put("mobile", this.mobile);
     this.memberMap.put("lvId", this.lvId);
     this.memberMap.put("email", this.email);
     this.memberMap.put("sex", this.sex);
     this.memberMap.put("start_time", this.start_time);
     this.memberMap.put("end_time", this.end_time);
     this.memberMap.put("province_id", this.province_id);
     this.memberMap.put("city_id", this.city_id);
     this.memberMap.put("region_id", this.region_id);
     this.webpage = this.memberManager.searchMember(this.memberMap, Integer.valueOf(getPage()), Integer.valueOf(getPageSize()), getSort(), getOrder());
     showGridJson(this.webpage);

     return "json_message";
   }

   public String saveEditMember() {
     if (!StringUtil.isEmpty(this.birthday)) {
       this.member.setBirthday(Long.valueOf(DateUtil.getDatelineLong(this.birthday)));
     }
     try {
       Member oldMember = this.memberManager.get(this.member.getMember_id());

       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       String province = request.getParameter("province");
       String city = request.getParameter("city");
       String region = request.getParameter("region");

       String province_id = request.getParameter("province_id");
       String city_id = request.getParameter("city_id");
       String region_id = request.getParameter("region_id");

       if (!StringUtil.isEmpty(province)) {
         oldMember.setProvince(province);
       }
       if (!StringUtil.isEmpty(province)) {
         oldMember.setProvince(city);
       }
       if (!StringUtil.isEmpty(province)) {
         oldMember.setProvince(region);
       }

       if (!StringUtil.isEmpty(province_id)) {
         oldMember.setProvince_id(Integer.valueOf(StringUtil.toInt(province_id, true)));
       }

       if (!StringUtil.isEmpty(city_id)) {
         oldMember.setCity_id(Integer.valueOf(StringUtil.toInt(city_id, true)));
       }

       if (!StringUtil.isEmpty(province_id)) {
         oldMember.setRegion_id(Integer.valueOf(StringUtil.toInt(region_id, true)));
       }
       if (!this.member.getPassword().equals(oldMember.getPassword())) {
         oldMember.setPassword(StringUtil.md5(this.member.getPassword()));
       }
       oldMember.setName(this.member.getName());
       oldMember.setSex(this.member.getSex());
       oldMember.setBirthday(this.member.getBirthday());
       oldMember.setEmail(this.member.getEmail());
       oldMember.setTel(this.member.getTel());
       oldMember.setMobile(this.member.getMobile());
       oldMember.setLv_id(this.member.getLv_id());
       oldMember.setZip(this.member.getZip());
       oldMember.setAddress(this.member.getAddress());
       oldMember.setQq(this.member.getQq());
       oldMember.setMsn(this.member.getMsn());
       oldMember.setPw_answer(this.member.getPw_answer());
       oldMember.setPw_question(this.member.getPw_question());
       this.memberManager.edit(oldMember);
       showSuccessJson("修改成功");
     } catch (Exception e) {
       showErrorJson("修改失败");
       e.printStackTrace();
     }
     return "json_message";
   }

   public String delete()
   {
     try {
       this.memberManager.delete(this.member_id);
       showSuccessJson("删除成功");
     } catch (RuntimeException e) {
       showErrorJson("删除失败" + e.getMessage());
     }
     return "json_message";
   }



   public String detail()
   {
     this.member = this.memberManager.get(this.memberId);
     this.pluginTabs = this.memberPluginBundle.getTabList(this.member);
     this.pluginHtmls = this.memberPluginBundle.getDetailHtml(this.member);

     return "detail";
   }


   public String editPoint()
   {
     this.member = this.memberManager.get(this.memberId);
     return "editPoint";
   }

   public String editSavePoint() {
     this.member = this.memberManager.get(this.memberId);
     this.member.setPoint(Integer.valueOf(this.member.getPoint().intValue() + this.point));
     PointHistory pointHistory = new PointHistory();
     pointHistory.setMember_id(this.memberId.intValue());
     pointHistory.setOperator("管理员");
     pointHistory.setPoint(this.point);
     pointHistory.setReason("管理员手工修改");
     pointHistory.setTime(Long.valueOf(DateUtil.getDatelineLong()));
     try {
       this.memberManager.edit(this.member);
       this.pointHistoryManager.addPointHistory(pointHistory);
       showSuccessJson("会员积分修改成功");
     } catch (Exception e) {
       showErrorJson("修改失败");
       e.printStackTrace();
     }
     return "json_message";
   }

   public String pointLog() {
     this.listPointHistory = this.pointHistoryManager.listPointHistory(this.memberId.intValue(), this.pointtype);
     this.member = this.memberManager.get(this.memberId);
     return "pointLog";
   }

   public String advance() {
     this.member = this.memberManager.get(this.memberId);
     this.listAdvanceLogs = this.advanceLogsManager.listAdvanceLogsByMemberId(this.memberId.intValue());

     return "advance";
   }

   public String comments() {
     Page page = this.memberCommentManager.getMemberComments(1, 100, StringUtil.toInt(this.object_type), this.memberId.intValue());
     if (page != null) {
       this.listComments = ((List)page.getResult());
     }
     return "comments";
   }







   public String editSaveAdvance()
   {
     this.member = this.memberManager.get(this.memberId);
     this.member.setAdvance(Double.valueOf(this.member.getAdvance().doubleValue() + this.modify_advance.doubleValue()));
     try {
       this.memberManager.edit(this.member);
     } catch (Exception e) {
       this.json = "{'result':1, 'message':'操作失败'}";
       e.printStackTrace();
     }

     AdvanceLogs advanceLogs = new AdvanceLogs();
     advanceLogs.setMember_id(this.memberId.intValue());
     advanceLogs.setDisabled("false");
     advanceLogs.setMtime(Long.valueOf(DateUtil.getDatelineLong()));
     advanceLogs.setImport_money(this.modify_advance);
     advanceLogs.setMember_advance(this.member.getAdvance());
     advanceLogs.setShop_advance(this.member.getAdvance());
     advanceLogs.setMoney(this.modify_advance);
     advanceLogs.setMessage(this.modify_memo);
     advanceLogs.setMemo("admin代充值");
     try {
       this.advanceLogsManager.add(advanceLogs);
     } catch (Exception e) {
       this.json = "{'result':1, 'message':'操作失败'}";
       e.printStackTrace();
     }
     this.json = "{'result':0,'message':'操作成功'}";

     return "json_message";
   }






















   public String saveMember()
   {
     int result = this.memberManager.checkname(this.member.getUname());
     if (result == 1) {
       showErrorJson("用户名已存在");
       return "json_message";
     }
     if (this.member != null)
     {

       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       String province = request.getParameter("province");
       String city = request.getParameter("city");
       String region = request.getParameter("region");

       String province_id = request.getParameter("province_id");
       String city_id = request.getParameter("city_id");
       String region_id = request.getParameter("region_id");


       this.member.setProvince(province);
       this.member.setCity(city);
       this.member.setRegion(region);

       if (!StringUtil.isEmpty(province_id)) {
         this.member.setProvince_id(Integer.valueOf(StringUtil.toInt(province_id, true)));
       }

       if (!StringUtil.isEmpty(city_id)) {
         this.member.setCity_id(Integer.valueOf(StringUtil.toInt(city_id, true)));
       }

       if (!StringUtil.isEmpty(province_id)) {
         this.member.setRegion_id(Integer.valueOf(StringUtil.toInt(region_id, true)));
       }

       this.member.setBirthday(Long.valueOf(DateUtil.getDatelineLong(this.birthday)));
       this.member.setPassword(this.member.getPassword());
       this.member.setRegtime(Long.valueOf(DateUtil.getDatelineLong()));
       this.memberManager.add(this.member);
       showSuccessJson("保存会员成功", this.member.getMember_id());
     }


     return "json_message";
   }

   public String sysLogin() {
     try {
       this.name = StringUtil.toUTF8(this.name);
       int r = this.memberManager.loginbysys(this.name);
       if (r == 1) {
         EopUser user = this.userManager.get(this.name);
         if (user != null) {
           WebSessionContext<EopUser> sessonContext = ThreadContextHolder.getSessionContext();

           sessonContext.setAttribute("eop_user_key", user);
         }
         return "syslogin";
       }
       this.msgs.add("登录失败");
       return "message";
     } catch (RuntimeException e) {
       this.msgs.add(e.getMessage()); }
     return "message";
   }





   private Map getStatusJson()
   {
     Map orderStatus = new HashMap();
     orderStatus.put("0", OrderStatus.getOrderStatusText(0));
     orderStatus.put("9", OrderStatus.getOrderStatusText(9));
     orderStatus.put("2", OrderStatus.getOrderStatusText(2));
     orderStatus.put("4", OrderStatus.getOrderStatusText(4));
     orderStatus.put("5", OrderStatus.getOrderStatusText(5));
     orderStatus.put("6", OrderStatus.getOrderStatusText(6));
     orderStatus.put("-2", OrderStatus.getOrderStatusText(-2));
     orderStatus.put("7", OrderStatus.getOrderStatusText(7));
     orderStatus.put("-1", OrderStatus.getOrderStatusText(-1));
     orderStatus.put("8", OrderStatus.getOrderStatusText(8));
     orderStatus.put("-7", OrderStatus.getOrderStatusText(-7));
     orderStatus.put("-4", OrderStatus.getOrderStatusText(-4));
     orderStatus.put("-3", OrderStatus.getOrderStatusText(-3));
     orderStatus.put("1", OrderStatus.getOrderStatusText(1));
     return orderStatus;
   }



   public String editRemark()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String modify_memo = request.getParameter("modify_memo");
     int memberid = StringUtil.toInt(request.getParameter("memberid"), true);
     Member member = this.memberManager.get(Integer.valueOf(memberid));
     member.setRemark(modify_memo);
     try {
       this.memberManager.edit(member);
       showSuccessJson("会员备注修改成功");
     } catch (Exception e) {
       this.logger.error("修改会员备注", e);
       showErrorJson("会员备注修改失败");
     }

     return "json_message";
   }


   public String editAdvance()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     double modify_advance = StringUtil.toDouble(request.getParameter("modify_advance"), true).doubleValue();
     String modify_memo = request.getParameter("modify_memo");
     int memberid = StringUtil.toInt(request.getParameter("memberid"), true);
     Member member = this.memberManager.get(Integer.valueOf(memberid));
     member.setAdvance(Double.valueOf(member.getAdvance().doubleValue() + modify_advance));
     AdvanceLogs advanceLogs = new AdvanceLogs();
     advanceLogs.setMember_id(memberid);
     advanceLogs.setDisabled("false");
     advanceLogs.setMtime(Long.valueOf(DateUtil.getDatelineLong()));
     advanceLogs.setImport_money(Double.valueOf(modify_advance));
     advanceLogs.setMember_advance(member.getAdvance());
     advanceLogs.setShop_advance(member.getAdvance());
     advanceLogs.setMoney(Double.valueOf(modify_advance));
     advanceLogs.setMessage(modify_memo);
     AdminUser user = (AdminUser)ThreadContextHolder.getSessionContext().getAttribute("admin_user_key");
     advanceLogs.setMemo(user.getUsername() + "代充值");
     try {
       this.memberManager.edit(member);
       this.advanceLogsManager.add(advanceLogs);
       showSuccessJson("会员预存款修改成功");
     } catch (Exception e) {
       this.logger.error("会员预存款修改失败", e);
       showErrorJson("修改失败");
     }
     return "json_message";
   }



   public MemberLv getLv()
   {
     return this.lv;
   }

   public void setLv(MemberLv lv) {
     this.lv = lv;
   }

   public Member getMember() {
     return this.member;
   }

   public void setMember(Member member) {
     this.member = member;
   }

   public IMemberManager getMemberManager() {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }

   public String getMessage() {
     return this.message;
   }

   public void setMessage(String message) {
     this.message = message;
   }

   public String getBirthday() {
     return this.birthday;
   }

   public void setBirthday(String birthday) {
     this.birthday = birthday;
   }

   public IMemberLvManager getMemberLvManager() {
     return this.memberLvManager;
   }

   public void setMemberLvManager(IMemberLvManager memberLvManager) {
     this.memberLvManager = memberLvManager;
   }

   public Integer[] getLv_id() {
     return this.lv_id;
   }

   public void setLv_id(Integer[] lv_id) {
     this.lv_id = lv_id;
   }

   public Integer getLvId() {
     return this.lvId;
   }

   public void setLvId(Integer lvId) {
     this.lvId = lvId;
   }

   public List getLvlist() {
     return this.lvlist;
   }

   public void setLvlist(List lvlist) {
     this.lvlist = lvlist;
   }

   public IRegionsManager getRegionsManager() {
     return this.regionsManager;
   }

   public void setRegionsManager(IRegionsManager regionsManager) {
     this.regionsManager = regionsManager;
   }

   public List getProvinceList() {
     return this.provinceList;
   }

   public void setProvinceList(List provinceList) {
     this.provinceList = provinceList;
   }

   public List getCityList() {
     return this.cityList;
   }

   public void setCityList(List cityList) {
     this.cityList = cityList;
   }

   public List getRegionList() {
     return this.regionList;
   }

   public void setRegionList(List regionList) {
     this.regionList = regionList;
   }


   public int getPoint()
   {
     return this.point;
   }

   public void setPoint(int point) {
     this.point = point;
   }

   public IPointHistoryManager getPointHistoryManager() {
     return this.pointHistoryManager;
   }

   public void setPointHistoryManager(IPointHistoryManager pointHistoryManager) {
     this.pointHistoryManager = pointHistoryManager;
   }

   public List getListPointHistory() {
     return this.listPointHistory;
   }

   public void setListPointHistory(List listPointHistory) {
     this.listPointHistory = listPointHistory;
   }

   public IAdvanceLogsManager getAdvanceLogsManager() {
     return this.advanceLogsManager;
   }

   public void setAdvanceLogsManager(IAdvanceLogsManager advanceLogsManager) {
     this.advanceLogsManager = advanceLogsManager;
   }

   public List getListAdvanceLogs() {
     return this.listAdvanceLogs;
   }

   public void setListAdvanceLogs(List listAdvanceLogs) {
     this.listAdvanceLogs = listAdvanceLogs;
   }

   public Double getModify_advance() {
     return this.modify_advance;
   }

   public void setModify_advance(Double modifyAdvance) {
     this.modify_advance = modifyAdvance;
   }

   public String getModify_memo() {
     return this.modify_memo;
   }

   public void setModify_memo(String modifyMemo) {
     this.modify_memo = modifyMemo;
   }

   public List getListComments() {
     return this.listComments;
   }

   public void setListComments(List listComments) {
     this.listComments = listComments;
   }

   public String getObject_type() {
     return this.object_type;
   }

   public void setObject_type(String objectType) {
     this.object_type = objectType;
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }

   public String getUname() {
     return this.uname;
   }

   public void setUname(String uname) {
     this.uname = uname;
   }

   public IUserManager getUserManager() {
     return this.userManager;
   }

   public void setUserManager(IUserManager userManager) {
     this.userManager = userManager;
   }

   public List getCatDiscountList() {
     return this.catDiscountList;
   }

   public void setCatDiscountList(List catDiscountList) {
     this.catDiscountList = catDiscountList;
   }

   public int[] getCat_ids() {
     return this.cat_ids;
   }

   public void setCat_ids(int[] cat_ids) {
     this.cat_ids = cat_ids;
   }

   public int[] getCat_discounts() {
     return this.cat_discounts;
   }

   public void setCat_discounts(int[] cat_discounts) {
     this.cat_discounts = cat_discounts;
   }

   public void setMemberCommentManager(IMemberCommentManager memberCommentManager) {
     this.memberCommentManager = memberCommentManager;
   }

   public int getPointtype() {
     return this.pointtype;
   }

   public void setPointtype(int pointtype) {
     this.pointtype = pointtype;
   }

   public Map<Integer, String> getPluginTabs() {
     return this.pluginTabs;
   }

   public void setPluginTabs(Map<Integer, String> pluginTabs) {
     this.pluginTabs = pluginTabs;
   }

   public Map<Integer, String> getPluginHtmls() {
     return this.pluginHtmls;
   }

   public void setPluginHtmls(Map<Integer, String> pluginHtmls) {
     this.pluginHtmls = pluginHtmls;
   }

   public MemberPluginBundle getMemberPluginBundle() {
     return this.memberPluginBundle;
   }

   public void setMemberPluginBundle(MemberPluginBundle memberPluginBundle) {
     this.memberPluginBundle = memberPluginBundle;
   }

   public Integer getMemberId() {
     return this.memberId;
   }

   public void setMemberId(Integer memberId) {
     this.memberId = memberId;
   }

   public Integer[] getMember_id() {
     return this.member_id;
   }

   public void setMember_id(Integer[] member_id) {
     this.member_id = member_id;
   }

   public Integer getMobile() {
     return this.mobile;
   }

   public void setMobile(Integer mobile) {
     this.mobile = mobile;
   }

   public String getEmail() {
     return this.email;
   }

   public void setEmail(String email) {
     this.email = email;
   }

   public Integer getSex()
   {
     return this.sex;
   }

   public void setSex(Integer sex) {
     this.sex = sex;
   }

   public Map getMemberMap() {
     return this.memberMap;
   }

   public void setMemberMap(Map memberMap) {
     this.memberMap = memberMap;
   }

   public IMemberCommentManager getMemberCommentManager() {
     return this.memberCommentManager;
   }

   public String getStart_time() {
     return this.start_time;
   }

   public void setStart_time(String start_time) {
     this.start_time = start_time;
   }

   public String getEnd_time() {
     return this.end_time;
   }

   public void setEnd_time(String end_time) {
     this.end_time = end_time;
   }


   public Integer getStype()
   {
     return this.stype;
   }

   public void setStype(Integer stype) {
     this.stype = stype;
   }

   public String getKeyword() {
     return this.keyword;
   }

   public void setKeyword(String keyword) {
     this.keyword = keyword;
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

   public Map getStatusMap() {
     return this.statusMap;
   }

   public void setStatusMap(Map statusMap) {
     this.statusMap = statusMap;
   }

   public String getStatus_Json() {
     return this.status_Json;
   }

   public void setStatus_Json(String status_Json) {
     this.status_Json = status_Json;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\MemberAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */