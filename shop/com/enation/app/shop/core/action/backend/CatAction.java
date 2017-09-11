 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.app.shop.core.service.IGoodsTypeManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.database.PermssionRuntimeException;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.database.DBRuntimeException;
 import com.enation.framework.util.FileUtil;
 import com.enation.framework.util.JsonMessageUtil;
 import com.enation.framework.util.StringUtil;
 import java.io.File;
 import java.util.List;
 import net.sf.json.JSONArray;












 public class CatAction
   extends WWAction
 {
   private IGoodsCatManager goodsCatManager;
   private IGoodsTypeManager goodsTypeManager;
   protected List catList;
   private List typeList;
   private Cat cat;
   private File image;
   private String imageFileName;
   protected int cat_id;
   private int[] cat_ids;
   private int[] cat_sorts;
   private String imgPath;
   private Cat add_cat;
   private Integer cattype;

   public String checkname()
   {
     if (this.goodsCatManager.checkname(this.cat.getName(), this.cat.getCat_id())) {
       this.json = "{result:1}";
     } else {
       this.json = "{result:0}";
     }
     return "json_message";
   }

   public String list()
   {
     return "cat_list";
   }

   public String listJson() {
     this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
     String s = JSONArray.fromObject(this.catList).toString();
     this.json = s.replace("name", "text").replace("cat_id", "id");
     return "json_message";
   }

   public String addlistJson() {
     List addlist = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
     String s = JSONArray.fromObject(addlist).toString();
     this.json = s.replace("name", "text").replace("cat_id", "id");
     return "json_message";
   }

   public String typelistjson() {
     this.typeList = this.goodsTypeManager.listAll();
     String s = JSONArray.fromObject(this.typeList).toString();
     this.json = s.replace("name", "text").replace("type_id", "id");
     return "json_message";
   }

   public String add()
   {
     return "cat_add";
   }

   public String addChildren() {
     this.cat = this.goodsCatManager.getById(this.cat_id);
     return "children_add";
   }

   public String edit()
   {
     try {
       this.typeList = this.goodsTypeManager.listAll();
       this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
       this.cat = this.goodsCatManager.getById(this.cat_id);
       if ((this.cat.getImage() != null) && (!StringUtil.isEmpty(this.cat.getImage()))) {
         this.imgPath = UploadUtil.replacePath(this.cat.getImage());
       }
       return "cat_edit";
     } catch (DBRuntimeException ex) {
       showErrorJson("您查询的商品不存在"); }
     return "json_message";
   }






   public String saveAdd()
   {
     if (EopSetting.IS_DEMO_SITE) {
       showErrorJson("抱歉，当前为演示站点，以不能添加这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }

     if (this.image != null) {
       if (FileUtil.isAllowUp(this.imageFileName)) {
         this.cat.setImage(UploadUtil.upload(this.image, this.imageFileName, "goodscat"));
       }
       else
       {
         showErrorJson("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
         return "json_message";
       }
     }
     if (this.cattype.intValue() == 1) {
       this.cat.setParent_id(Integer.valueOf(0));
     }
     this.cat.setGoods_count(Integer.valueOf(0));
     this.goodsCatManager.saveAdd(this.cat);
     showSuccessJson("商品分类添加成功");
     return "json_message";
   }




   public String saveEdit()
   {
     if (this.image != null) {
       if (FileUtil.isAllowUp(this.imageFileName)) {
         this.cat.setImage(UploadUtil.upload(this.image, this.imageFileName, "goodscat"));
       }
       else
       {
         showErrorJson("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
         return "json_message";
       }
     }
     try {
       if (this.cat.getParent_id().intValue() == 0) {
         this.goodsCatManager.update(this.cat);
         showSuccessJson("商品分类修改成功");
         return "json_message";
       }
       Cat targetCat = this.goodsCatManager.getById(this.cat.getParent_id().intValue());
       if ((this.cat.getParent_id().intValue() == this.cat.getCat_id().intValue()) || (targetCat.getParent_id().intValue() == this.cat.getCat_id().intValue()))
       {

         showErrorJson("保存失败：上级分类不能选择当前分类或其子分类");
         return "json_message";
       }
       this.goodsCatManager.update(this.cat);
       showSuccessJson("商品分类修改成功");
       return "json_message";
     }
     catch (PermssionRuntimeException ex) {
       showErrorJson("非法操作"); }
     return "json_message";
   }




   public String delete()
   {
     if ((EopSetting.IS_DEMO_SITE) && (this.cat_id < 93)) {
       showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }
     try
     {
       int r = this.goodsCatManager.delete(this.cat_id);

       if (r == 0) {
         showSuccessJson("删除成功");
       } else if (r == 1) {
         showErrorJson("此类别下存在子类别不能删除!");
       } else if (r == 2) {
         showErrorJson("此类别下存在商品不能删除!");
       }
     } catch (PermssionRuntimeException ex) {
       showErrorJson("非法操作!");
       return "json_message";
     }
     return "json_message";
   }



   public String getChildJson()
   {
     try
     {
       this.catList = this.goodsCatManager.listChildren(Integer.valueOf(this.cat_id));
       this.json = JsonMessageUtil.getListJson(this.catList);
     } catch (RuntimeException e) {
       showErrorJson(e.getMessage());
     }

     return "json_message";
   }

   public String saveSort() {
     this.goodsCatManager.saveSort(this.cat_ids, this.cat_sorts);
     showSuccessJson("保存成功");
     return "json_message";
   }

   public List getCatList() {
     return this.catList;
   }

   public void setCatList(List catList) {
     this.catList = catList;
   }

   public Cat getCat() {
     return this.cat;
   }

   public void setCat(Cat cat) {
     this.cat = cat;
   }

   public int getCat_id() {
     return this.cat_id;
   }

   public void setCat_id(int cat_id) {
     this.cat_id = cat_id;
   }

   public int[] getCat_ids() {
     return this.cat_ids;
   }

   public void setCat_ids(int[] cat_ids) {
     this.cat_ids = cat_ids;
   }

   public int[] getCat_sorts() {
     return this.cat_sorts;
   }

   public void setCat_sorts(int[] cat_sorts) {
     this.cat_sorts = cat_sorts;
   }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
     this.goodsCatManager = goodsCatManager;
   }

   public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
     this.goodsTypeManager = goodsTypeManager;
   }

   public List getTypeList() {
     return this.typeList;
   }

   public void setTypeList(List typeList) {
     this.typeList = typeList;
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

   public String getImgPath() {
     return this.imgPath;
   }

   public void setImgPath(String imgPath) {
     this.imgPath = imgPath;
   }

   public IGoodsCatManager getGoodsCatManager() {
     return this.goodsCatManager;
   }

   public IGoodsTypeManager getGoodsTypeManager() {
     return this.goodsTypeManager;
   }

   public Cat getAdd_cat() {
     return this.add_cat;
   }

   public void setAdd_cat(Cat add_cat) {
     this.add_cat = add_cat;
   }

   public Integer getCattype() {
     return this.cattype;
   }

   public void setCattype(Integer cattype) {
     this.cattype = cattype;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\CatAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */