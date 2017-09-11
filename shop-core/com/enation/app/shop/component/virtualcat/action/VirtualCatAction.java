 package com.enation.app.shop.component.virtualcat.action;

 import com.enation.app.shop.component.virtualcat.model.VirtualCat;
 import com.enation.app.shop.component.virtualcat.service.IVirtualCatManager;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.util.FileUtil;
 import java.io.File;
 import java.util.List;
 import java.util.Map;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;










 @ParentPackage("shop_default")
 @Namespace("/shop/admin")
 @Results({@org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/com/enation/app/shop/component/virtualcat/action/list.html"), @org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/com/enation/app/shop/component/virtualcat/action/edit.html"), @org.apache.struts2.convention.annotation.Result(name="add", type="freemarker", location="/com/enation/app/shop/component/virtualcat/action/add.html")})
 public class VirtualCatAction
   extends WWAction
 {
   private IVirtualCatManager virtualCatManager;
   private VirtualCat myCat;
   private int vid;
   protected List catList;
   private File image;
   private String imageFileName;

   public String list()
   {
     this.catList = this.virtualCatManager.getTree();
     return "list";
   }

   public String add() {
     this.catList = this.virtualCatManager.getTree();
     return "add";
   }

   public String saveAdd() {
     if (this.image != null) {
       if (FileUtil.isAllowUp(this.imageFileName)) {
         this.myCat.setPic_url(UploadUtil.upload(this.image, this.imageFileName, "virtualcat"));
       }
       else {
         this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
         return "message";
       }
     }
     this.virtualCatManager.add(this.myCat);
     this.msgs.add("添加成功");
     this.urls.put("分类列表", "virtual-cat!list.do");
     return "message";
   }

   public String edit() {
     this.myCat = this.virtualCatManager.get(this.vid);
     this.catList = this.virtualCatManager.getTree();
     return "edit";
   }

   public String saveEdit() {
     if (this.image != null) {
       if (FileUtil.isAllowUp(this.imageFileName)) {
         this.myCat.setPic_url(UploadUtil.upload(this.image, this.imageFileName, "virtualcat"));
       }
       else {
         this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
         return "message";
       }
     }
     this.virtualCatManager.edit(this.myCat);
     this.msgs.add("修改成功");
     this.urls.put("分类列表", "virtual-cat!list.do");
     return "message";
   }

   public String delete() {
     this.virtualCatManager.delete(this.vid);
     this.json = "{\"result\":\"0\"}";
     return "json_message";
   }

   public IVirtualCatManager getVirtualCatManager() {
     return this.virtualCatManager;
   }

   public void setVirtualCatManager(IVirtualCatManager virtualCatManager) {
     this.virtualCatManager = virtualCatManager;
   }

   public VirtualCat getMyCat() {
     return this.myCat;
   }

   public void setMyCat(VirtualCat myCat) {
     this.myCat = myCat;
   }

   public int getVid() {
     return this.vid;
   }

   public void setVid(int vid) {
     this.vid = vid;
   }

   public List getCatList() {
     return this.catList;
   }

   public void setCatList(List catList) {
     this.catList = catList;
   }

   public File getImage() {
     return this.image;
   }

   public void setImage(File image) {
     this.image = image;
   }

   public String getImageFileName() {
     return this.imageFileName;
   }

   public void setImageFileName(String imageFileName) {
     this.imageFileName = imageFileName;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\virtualcat\action\VirtualCatAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */