 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.core.model.PrintTmpl;
 import com.enation.app.shop.core.service.ILogiManager;
 import com.enation.app.shop.core.service.IPrintTmplManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.action.WWAction;
 import java.util.List;
 import org.apache.log4j.Logger;









 public class PrintTmplAction
   extends WWAction
 {
   private IPrintTmplManager printTmplManager;
   private List list;
   private List trash;
   private List listCanUse;
   private Integer[] prt_tmpl_id;
   private Integer prt_tmplId;
   private PrintTmpl printTmpl;
   private String picfile;
   private List logiList;
   private ILogiManager logiManager;

   public String list()
   {
     this.list = this.printTmplManager.list();
     return "list";
   }

   public String listJson() {
     this.list = this.printTmplManager.list();
     showGridJson(this.list);
     return "json_message";
   }

   public String trash() {
     this.trash = this.printTmplManager.trash();
     return "trash";
   }

   public String listCanUse() {
     this.listCanUse = this.printTmplManager.listCanUse();
     return "listCanUse";
   }

   public String add() {
     this.logiList = this.logiManager.list();
     return "add";
   }

   public String saveAdd() {
     try {
       if (this.printTmplManager.check(this.printTmpl.getPrt_tmpl_title())) {
         showErrorJson("已经存在此快递单模板");
       } else {
         this.printTmplManager.add(this.printTmpl);
         showSuccessJson("模板添加成功");
       }
     } catch (Exception e) {
       this.logger.error("模板添加失败", e);
       showErrorJson("模板添加失败");
     }
     return "json_message";
   }

   public String edit() {
     this.logiList = this.logiManager.list();
     this.printTmpl = this.printTmplManager.get(this.prt_tmplId.intValue());
     return "edit";
   }

   public String saveEdit() {
     try {
       this.printTmplManager.edit(this.printTmpl);
       showSuccessJson("模板修改成功");
     } catch (Exception e) {
       showErrorJson("模板修改失败");
       this.logger.error("模板修改失败", e);
     }
     return "json_message";
   }

   public String delete() {
     if (EopSetting.IS_DEMO_SITE) {
       showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }
     try {
       this.printTmplManager.delete(this.prt_tmpl_id);
       showSuccessJson("删除成功");
     } catch (Exception e) {
       showErrorJson("删除失败");
       this.logger.error("模板删除失败", e);
     }
     return "json_message";
   }

   public String revert() {
     try {
       this.printTmplManager.revert(this.prt_tmpl_id);
       this.json = "{'result':0,'message':'还原成功'}";
     } catch (Exception e) {
       this.json = "{'result':1;'message':'还原失败'}";
       this.logger.error("模板还原失败", e);
     }
     return "json_message";
   }

   public String clean()
   {
     if (EopSetting.IS_DEMO_SITE) {
       showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }
     try
     {
       this.printTmplManager.clean(this.prt_tmpl_id);
       showSuccessJson("清除成功");
     } catch (Exception e) {
       showErrorJson("清除失败");
       this.logger.error("模板清除失败", e);
     }
     return "json_message";
   }

   public IPrintTmplManager getPrintTmplManager() {
     return this.printTmplManager;
   }

   public void setPrintTmplManager(IPrintTmplManager printTmplManager) {
     this.printTmplManager = printTmplManager;
   }

   public List getList() {
     return this.list;
   }

   public void setList(List list) {
     this.list = list;
   }

   public List getTrash() {
     return this.trash;
   }

   public void setTrash(List trash) {
     this.trash = trash;
   }

   public List getListCanUse() {
     return this.listCanUse;
   }

   public void setListCanUse(List listCanUse) {
     this.listCanUse = listCanUse;
   }

   public Integer[] getPrt_tmpl_id() {
     return this.prt_tmpl_id;
   }

   public void setPrt_tmpl_id(Integer[] prt_tmpl_id) {
     this.prt_tmpl_id = prt_tmpl_id;
   }

   public Integer getPrt_tmplId() {
     return this.prt_tmplId;
   }

   public void setPrt_tmplId(Integer prt_tmplId) {
     this.prt_tmplId = prt_tmplId;
   }

   public PrintTmpl getPrintTmpl() {
     return this.printTmpl;
   }

   public void setPrintTmpl(PrintTmpl printTmpl) {
     this.printTmpl = printTmpl;
   }

   public String getPicfile()
   {
     return this.picfile;
   }

   public void setPicfile(String picfile) {
     this.picfile = picfile;
   }

   public List getLogiList() {
     return this.logiList;
   }

   public void setLogiList(List logiList) {
     this.logiList = logiList;
   }

   public ILogiManager getLogiManager() {
     return this.logiManager;
   }

   public void setLogiManager(ILogiManager logiManager) {
     this.logiManager = logiManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\PrintTmplAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */