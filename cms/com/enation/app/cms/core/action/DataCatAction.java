 package com.enation.app.cms.core.action;

 import com.enation.app.cms.core.model.DataCat;
 import com.enation.app.cms.core.service.ArticleCatRuntimeException;
 import com.enation.app.cms.core.service.IDataCatManager;
 import com.enation.app.cms.core.service.IDataModelManager;
 import com.enation.framework.action.WWAction;
 import java.util.List;
 import net.sf.json.JSONArray;









 public class DataCatAction
   extends WWAction
 {
   private IDataCatManager dataCatManager;
   private IDataModelManager dataModelManager;
   private boolean isEdit;
   private List catList;
   private List modelList;
   private DataCat cat;
   private int cat_id;
   private int[] cat_ids;
   private int[] cat_sorts;

   public String list() { return "list"; }

   public String listJson() {
     this.catList = this.dataCatManager.listAllChildren(Integer.valueOf(0));
     this.json = JSONArray.fromObject(this.catList).toString();
     return "json_message";
   }



   public String add()
   {
     this.isEdit = false;
     this.catList = this.dataCatManager.listAllChildren(Integer.valueOf(0));
     this.modelList = this.dataModelManager.list();
     return "add";
   }


   public String edit()
   {
     this.isEdit = true;
     this.catList = this.dataCatManager.listAllChildren(Integer.valueOf(0));
     this.modelList = this.dataModelManager.list();
     this.cat = this.dataCatManager.get(Integer.valueOf(this.cat_id));
     return "edit";
   }

   public String saveAdd()
   {
     try
     {
       this.dataCatManager.add(this.cat);
       showSuccessJson("文章栏目添加成功");
     } catch (ArticleCatRuntimeException ex) {
       showErrorJson("同级文章栏目不能同名");
     }
     return "json_message";
   }


   public String saveEdit()
   {
     try
     {
       this.dataCatManager.edit(this.cat);
       showSuccessJson("文章栏目修改成功");
     } catch (ArticleCatRuntimeException ex) {
       showErrorJson("同级文章栏目不能同名");
     }
     return "json_message";
   }



   public String delete()
   {
     int r = this.dataCatManager.delete(Integer.valueOf(this.cat_id));
     if (r == 0) {
       showSuccessJson("删除成功");
     } else if (r == 1) {
       showErrorJson("此类别下存在子类别或者文章不能删除!");
     }

     return "json_message";
   }

   public String saveSort()
   {
     try {
       this.dataCatManager.saveSort(this.cat_ids, this.cat_sorts);
       showSuccessJson("保存成功");
     } catch (RuntimeException e) {
       showErrorJson("保存失败");
     }
     return "json_message";
   }





   public String showCatTree()
   {
     this.catList = this.dataCatManager.listAllChildren(Integer.valueOf(this.cat_id));
     return "cat_tree";
   }

   public IDataCatManager getDataCatManager()
   {
     return this.dataCatManager;
   }


   public void setDataCatManager(IDataCatManager dataCatManager)
   {
     this.dataCatManager = dataCatManager;
   }


   public IDataModelManager getDataModelManager()
   {
     return this.dataModelManager;
   }


   public void setDataModelManager(IDataModelManager dataModelManager)
   {
     this.dataModelManager = dataModelManager;
   }


   public List getCatList()
   {
     return this.catList;
   }


   public void setCatList(List catList)
   {
     this.catList = catList;
   }


   public DataCat getCat()
   {
     return this.cat;
   }


   public void setCat(DataCat cat)
   {
     this.cat = cat;
   }


   public int getCat_id()
   {
     return this.cat_id;
   }


   public void setCat_id(int catId)
   {
     this.cat_id = catId;
   }


   public int[] getCat_ids()
   {
     return this.cat_ids;
   }


   public void setCat_ids(int[] catIds)
   {
     this.cat_ids = catIds;
   }


   public int[] getCat_sorts()
   {
     return this.cat_sorts;
   }


   public void setCat_sorts(int[] catSorts)
   {
     this.cat_sorts = catSorts;
   }


   public List getModelList()
   {
     return this.modelList;
   }


   public void setModelList(List modelList)
   {
     this.modelList = modelList;
   }

   public void setIsEdit(boolean isEdit) {
     this.isEdit = isEdit;
   }

   public boolean getIsEdit() {
     return this.isEdit;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\action\DataCatAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */