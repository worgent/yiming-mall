 package com.enation.app.base.core.action;

 import com.enation.app.base.core.model.AdColumn;
 import com.enation.app.base.core.model.Adv;
 import com.enation.app.base.core.service.IAdColumnManager;
 import com.enation.app.base.core.service.IAdvManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.util.FileUtil;
 import com.enation.framework.util.StringUtil;
 import java.io.File;
 import java.util.Date;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;





 public class AdvAction
   extends WWAction
 {
   private IAdColumnManager adColumnManager;
   private IAdvManager advManager;
   private List<AdColumn> adColumnList;
   private Adv adv;
   private Long acid;
   private String advname;
   private Long advid;
   private Integer[] aid;
   private File pic;
   private String picFileName;
   private Date mstartdate;
   private Date menddate;
   private String order;
   private File url;
   private String imgPath;

   public String list()
   {
     return "list";
   }

   public String listJson() {
     this.adColumnList = this.adColumnManager.listAllAdvPos();
     this.webpage = this.advManager.search(this.acid, this.advname, getPage(), getPageSize(), this.order);
     showGridJson(this.webpage);
     return "json_message";
   }

   public String detail() {
     this.adv = this.advManager.getAdvDetail(this.advid);
     return "detail";
   }

   public String click() {
     if (this.advid.longValue() == 0L) {
       getRequest().setAttribute("gourl", "/eop/shop/adv/zhaozu.jsp");
     } else {
       this.adv = this.advManager.getAdvDetail(this.advid);


       if (getRequest().getSession().getAttribute("AD" + this.advid.toString()) == null)
       {
         getRequest().getSession().setAttribute("AD" + this.advid.toString(), "clicked");

         this.adv.setClickcount(Integer.valueOf(this.adv.getClickcount().intValue() + 1));
         this.advManager.updateAdv(this.adv);
       }

       getRequest().setAttribute("gourl", this.adv.getUrl());
     }
     return "go";
   }

   public String delete()
   {
     if (EopSetting.IS_DEMO_SITE) {
       for (Integer id : this.aid) {
         if (id.intValue() <= 21) {
           showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
           return "json_message";
         }
       }
     }
     try
     {
       this.advManager.delAdvs(this.aid);
       showSuccessJson("删除成功");
     } catch (RuntimeException e) {
       showErrorJson("删除失败");
     }

     return "json_message";
   }

   public String add() {
     this.adColumnList = this.adColumnManager.listAllAdvPos();
     return "add";
   }

   public String addSave() {
     if (this.pic != null)
     {
       if (FileUtil.isAllowUp(this.picFileName)) {
         String path = UploadUtil.upload(this.pic, this.picFileName, "adv");
         this.adv.setAtturl(path);
       } else {
         showErrorJson("不允许上传的文件格式，请上传gif,jpg,bmp,swf格式文件。");
         return "json_message";
       }
     }
     this.adv.setBegintime(Long.valueOf(this.mstartdate.getTime() / 1000L));
     this.adv.setEndtime(Long.valueOf(this.menddate.getTime() / 1000L));
     this.adv.setDisabled("false");
     try {
       this.advManager.addAdv(this.adv);
       showSuccessJson("新增广告成功");
     } catch (RuntimeException e) {
       showErrorJson("新增广告失败");
     }
     return "json_message";
   }

   public String edit() {
     this.adColumnList = this.adColumnManager.listAllAdvPos();
     this.adv = this.advManager.getAdvDetail(this.advid);
     if ((this.adv.getAtturl() != null) && (!StringUtil.isEmpty(this.adv.getAtturl()))) {
       this.imgPath = UploadUtil.replacePath(this.adv.getAtturl());
     }

     return "edit";
   }

   public String editSave() {
     if (this.pic != null) {
       if (FileUtil.isAllowUp(this.picFileName)) {
         String path = UploadUtil.upload(this.pic, this.picFileName, "adv");
         this.adv.setAtturl(path);
       } else {
         showErrorJson("不允许上传的文件格式，请上传gif,jpg,bmp,swf格式文件。");
         return "json_message";
       }
     }
     this.adv.setBegintime(Long.valueOf(this.mstartdate.getTime() / 1000L));
     this.adv.setEndtime(Long.valueOf(this.menddate.getTime() / 1000L));
     this.advManager.updateAdv(this.adv);
     showSuccessJson("修改广告成功");
     return "json_message";
   }

   public String stop() {
     this.adv = this.advManager.getAdvDetail(this.advid);
     this.adv.setIsclose(Integer.valueOf(1));
     try {
       this.advManager.updateAdv(this.adv);
       showSuccessJson("操作成功");
     } catch (RuntimeException e) {
       showErrorJson("操作失败");
     }
     return "json_message";
   }

   public String start() {
     this.adv = this.advManager.getAdvDetail(this.advid);
     this.adv.setIsclose(Integer.valueOf(0));
     try {
       this.advManager.updateAdv(this.adv);
       showSuccessJson("操作成功");
     } catch (RuntimeException e) {
       showErrorJson("操作失败");
     }
     return "json_message";
   }

   public IAdColumnManager getAdColumnManager() {
     return this.adColumnManager;
   }

   public void setAdColumnManager(IAdColumnManager adColumnManager) {
     this.adColumnManager = adColumnManager;
   }

   public IAdvManager getAdvManager() {
     return this.advManager;
   }

   public void setAdvManager(IAdvManager advManager) {
     this.advManager = advManager;
   }

   public Adv getAdv() {
     return this.adv;
   }

   public void setAdv(Adv adv) {
     this.adv = adv;
   }

   public Long getAcid() {
     return this.acid;
   }

   public void setAcid(Long acid) {
     this.acid = acid;
   }

   public Long getAdvid() {
     return this.advid;
   }

   public void setAdvid(Long advid) {
     this.advid = advid;
   }


   public Integer[] getAid()
   {
     return this.aid;
   }

   public void setAid(Integer[] aid) {
     this.aid = aid;
   }

   public List<AdColumn> getAdColumnList() {
     return this.adColumnList;
   }

   public void setAdColumnList(List<AdColumn> adColumnList) {
     this.adColumnList = adColumnList;
   }

   public File getPic() {
     return this.pic;
   }

   public void setPic(File pic) {
     this.pic = pic;
   }

   public String getPicFileName() {
     return this.picFileName;
   }

   public void setPicFileName(String picFileName) {
     this.picFileName = picFileName;
   }

   public Date getMstartdate() {
     return this.mstartdate;
   }

   public void setMstartdate(Date mstartdate) {
     this.mstartdate = mstartdate;
   }

   public Date getMenddate() {
     return this.menddate;
   }

   public void setMenddate(Date menddate) {
     this.menddate = menddate;
   }

   public String getOrder() {
     return this.order;
   }

   public void setOrder(String order) {
     this.order = order;
   }

   public String getAdvname() {
     return this.advname;
   }

   public void setAdvname(String advname) {
     this.advname = advname;
   }

   public File getUrl() {
     return this.url;
   }

   public void setUrl(File url) {
     this.url = url;
   }

   public String getImgPath() {
     return this.imgPath;
   }

   public void setImgPath(String imgPath) {
     this.imgPath = imgPath;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\AdvAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */