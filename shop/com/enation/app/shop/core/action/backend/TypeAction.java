 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.core.model.Attribute;
 import com.enation.app.shop.core.model.GoodsType;
 import com.enation.app.shop.core.model.support.GoodsTypeDTO;
 import com.enation.app.shop.core.model.support.ParamGroup;
 import com.enation.app.shop.core.model.support.TypeSaveState;
 import com.enation.app.shop.core.service.IBrandManager;
 import com.enation.app.shop.core.service.IGoodsTypeManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.util.StringUtil;
 import java.io.UnsupportedEncodingException;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import net.sf.json.JSONArray;
 import net.sf.json.JSONObject;
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
 @Action("type")
 @Results({@org.apache.struts2.convention.annotation.Result(name="step1", type="freemarker", location="/shop/admin/type/type_add_step1.html"), @org.apache.struts2.convention.annotation.Result(name="list", type="freemarker", location="/shop/admin/type/type_list.html"), @org.apache.struts2.convention.annotation.Result(name="add_props", type="freemarker", location="/shop/admin/type/type_props.html"), @org.apache.struts2.convention.annotation.Result(name="add_parms", type="freemarker", location="/shop/admin/type/type_params.html"), @org.apache.struts2.convention.annotation.Result(name="join_brand", type="freemarker", location="/shop/admin/type/type_brand.html"), @org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/shop/admin/type/type_edit_step1.html"), @org.apache.struts2.convention.annotation.Result(name="props_input", type="freemarker", location="/com/enation/app/shop/plugin/standard/type/props_input.html"), @org.apache.struts2.convention.annotation.Result(name="params_input", type="freemarker", location="/com/enation/app/shop/plugin/standard/type/params_input.html"), @org.apache.struts2.convention.annotation.Result(name="brand_list", type="freemarker", location="/com/enation/app/shop/plugin/standard/type/brand_input.html"), @org.apache.struts2.convention.annotation.Result(name="field", type="freemarker", location="/shop/admin/type/type_field.html")})
 public class TypeAction
   extends WWAction
 {
   private IGoodsTypeManager goodsTypeManager;
   private IBrandManager brandManager;
   private List brandlist;
   private GoodsTypeDTO goodsType;
   private String[] propnames;
   private int[] proptypes;
   private String[] options;
   private String[] datatype;
   private int[] required;
   private String[] unit;
   private String paramnum;
   private String[] groupnames;
   private String[] paramnames;
   private Integer typeId;
   private int is_edit;
   private Integer[] type_id;
   private Integer[] chhoose_brands;
   private static String GOODSTYPE_SESSION_KEY = "goods_type_in_session";

   private static String GOODSTYPE_STATE_SESSION_KEY = "goods_type_state_in_session";
   private String order;
   private Integer otherType;
   private List attrList;
   private ParamGroup[] paramAr;

   public String getOrder()
   {
     return this.order;
   }

   public void setOrder(String order) {
     this.order = order;
   }

   public String checkname() {
     if ((EopSetting.IS_DEMO_SITE) &&
       (this.goodsType.getType_id() != null) && (this.goodsType.getType_id().intValue() <= 48)) {
       showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }


     if (this.goodsTypeManager.checkname(this.goodsType.getName(), this.goodsType.getType_id())) {
       showErrorJson("类型名称已存在");
     } else {
       this.goodsTypeManager.save(this.goodsType);
       showSuccessJson("保存成功");
     }
     return "json_message";
   }

   public String list()
   {
     return "list";
   }


   public String listJson()
   {
     this.webpage = this.goodsTypeManager.pageType(this.order, getPage(), getPageSize());

     showGridJson(this.webpage);
     return "json_message";
   }


   public String trash_list()
   {
     this.webpage = this.goodsTypeManager.pageTrashType(this.order, getPage(), getPageSize());

     return "trash_list";
   }






   public String step1()
   {
     return "step1";
   }



   public String step2()
   {
     TypeSaveState saveState = new TypeSaveState();
     this.session.put(GOODSTYPE_STATE_SESSION_KEY, saveState);

     GoodsType tempType = getTypeFromSession();
     if (tempType == null)
     {
       this.session.put(GOODSTYPE_SESSION_KEY, this.goodsType);

     }
     else if (this.is_edit == 1) {
       tempType.setHave_parm(this.goodsType.getHave_parm());
       tempType.setHave_prop(this.goodsType.getHave_prop());
       tempType.setJoin_brand(this.goodsType.getJoin_brand());
       tempType.setIs_physical(this.goodsType.getIs_physical());
       tempType.setHave_field(this.goodsType.getHave_field());
       tempType.setName(this.goodsType.getName());
     } else {
       this.session.put(GOODSTYPE_SESSION_KEY, this.goodsType);
     }


     String result = getResult();
     if (result == null) {
       renderText("参数不正确！");
     }
     showSuccessJson("添加成功");
     return "json_message";
   }





   public String edit()
   {
     this.goodsType = this.goodsTypeManager.get(this.typeId);
     return "edit";
   }

   public String editJson() { this.goodsType = this.goodsTypeManager.get(this.typeId);
     this.session.put(GOODSTYPE_SESSION_KEY, this.goodsType);
     this.json = this.goodsType.getProps();
     JSONArray jsonar = JSONArray.fromObject(this.json);
     Object[] objar = jsonar.toArray();
     int i = 0;
     for (Object object : objar) {
       JSONObject obj = (JSONObject)object;
       obj.put("id", Integer.valueOf(i));
       i++;
     }
     this.json = JSONArray.fromObject(objar).toString();
     this.is_edit = 1;
     return "json_message";
   }

   public String editOther() {
     this.goodsType = this.goodsTypeManager.get(this.typeId);
     if (this.otherType.intValue() == 2) {
       return "add_props";
     }
     if (this.otherType.intValue() == 3) {
       return "add_parms";
     }
     if (this.otherType.intValue() == 4) {
       this.brandlist = this.brandManager.list();
       return "join_brand";
     }
     return null;
   }




   public String saveParams()
   {
     String[] paramnums = new String[0];
     if ((this.paramnum != null) && (!StringUtil.isEmpty(this.paramnum))) {
       if (this.paramnum.indexOf(",-1") >= 0) {
         this.paramnum = this.paramnum.replaceAll(",-1", "");
       }
       paramnums = this.paramnum.split(",");
     }

     String params = this.goodsTypeManager.getParamString(paramnums, this.groupnames, this.paramnames, null);

     GoodsType prop = this.goodsTypeManager.getById(this.typeId.intValue());
     prop.setParams(params);
     this.goodsTypeManager.save(prop);
     showSuccessJson("保存成功");
     return "json_message";
   }












   public String saveProps()
     throws UnsupportedEncodingException
   {
     HttpServletRequest req = getRequest();

     req.setCharacterEncoding("UTF-8");

     String inserted = req.getParameter("inserted");
     String deleted = req.getParameter("deleted");
     String updated = req.getParameter("updated");

     if (inserted != null)
     {
       GoodsType inprop = this.goodsTypeManager.getById(this.typeId.intValue());

       JSONArray json = JSONArray.fromObject(inserted);
       List<Attribute> list = (List)JSONArray.toCollection(json, Attribute.class);
       String str = null;
       if ((inprop.getProps() != null) && (!StringUtil.isEmpty(inprop.getProps()))) {
         JSONArray propjson = JSONArray.fromObject(inprop.getProps());
         List<Attribute> proplist = (List)JSONArray.toCollection(propjson, Attribute.class);
         proplist.addAll(list);
         str = JSONArray.fromObject(proplist).toString();
       } else {
         str = JSONArray.fromObject(list).toString();
       }
       inprop.setProps(str);
       this.goodsTypeManager.save(inprop);
       showSuccessJson("添加成功");
     }

     if (deleted != null) {
       GoodsType dataprop = this.goodsTypeManager.getById(this.typeId.intValue());
       String datastr = dataprop.getProps();
       JSONArray datajson = JSONArray.fromObject(datastr);
       Object[] dataobj = datajson.toArray();
       int i = 0;
       for (Object daobj : dataobj) {
         JSONObject obj = (JSONObject)daobj;
         obj.put("id", Integer.valueOf(i));
         i++;
       }
       JSONArray detejson = JSONArray.fromObject(deleted);
       Object[] detobj = detejson.toArray();
       for (Object dobj : dataobj) {
         for (Object uobj : detobj) {
           JSONObject d_obj = (JSONObject)dobj;
           JSONObject u_obj = (JSONObject)uobj;
           if (d_obj.get("id").equals(u_obj.get("id"))) {
             datajson.remove(dobj);
           }
         }
       }
       Object[] dedatajson = datajson.toArray();
       for (Object object : dedatajson) {
         JSONObject updata_obj = (JSONObject)object;
         updata_obj.remove("id");
       }
       dataprop.setProps(JSONArray.fromObject(dedatajson).toString());
       this.goodsTypeManager.save(dataprop);
       showSuccessJson("删除成功");
     }

     if (updated != null) {
       GoodsType dataprop = this.goodsTypeManager.getById(this.typeId.intValue());
       String datastr = dataprop.getProps();
       JSONArray datajson = JSONArray.fromObject(datastr);
       Object[] dataobj = datajson.toArray();
       int i = 0;
       for (Object daobj : dataobj) {
         JSONObject obj = (JSONObject)daobj;
         obj.put("id", Integer.valueOf(i));
         i++;
       }
       JSONArray upjson = JSONArray.fromObject(updated);
       Object[] upobj = upjson.toArray();
       for (Object dobj : dataobj) {
         for (Object uobj : upobj) {
           JSONObject d_obj = (JSONObject)dobj;
           JSONObject u_obj = (JSONObject)uobj;
           if (d_obj.get("id").equals(u_obj.get("id"))) {
             datajson.remove(dobj);
             datajson.add(((Integer)u_obj.get("id")).intValue(), u_obj);
           }
         }
       }
       Object[] updatajson = datajson.toArray();
       for (Object object : updatajson) {
         JSONObject updata_obj = (JSONObject)object;
         updata_obj.remove("id");
       }
       dataprop.setProps(JSONArray.fromObject(updatajson).toString());
       this.goodsTypeManager.save(dataprop);
       showSuccessJson("修改成功");
     }
     return "json_message";
   }






   public String saveBrand()
   {
     GoodsType prop = this.goodsTypeManager.getById(this.typeId.intValue());
     prop.setBrand_ids(this.chhoose_brands);

     this.goodsTypeManager.save(prop);
     showSuccessJson("保存成功");
     return "json_message";
   }











   public String save()
   {
     GoodsTypeDTO tempType = getTypeFromSession();
     tempType.setDisabled(0);
     tempType.setBrandList(null);
     tempType.setPropList(null);
     tempType.setParamGroups(null);

     this.typeId = this.goodsTypeManager.save(tempType);
     this.session.remove(GOODSTYPE_SESSION_KEY);


     if (tempType.getHave_field() == 0) {
       this.msgs.add("商品类型保存成功");
       this.urls.put("商品类型列表", "type!list.do");
       return "message";
     }
     return "field";
   }





   private GoodsTypeDTO getTypeFromSession()
   {
     Object obj = this.session.get(GOODSTYPE_SESSION_KEY);

     if (obj == null)
     {
       return null;
     }

     GoodsTypeDTO tempType = (GoodsTypeDTO)obj;

     return tempType;
   }








   private TypeSaveState getSaveStateFromSession()
   {
     Object obj = this.session.get(GOODSTYPE_STATE_SESSION_KEY);
     if (obj == null) {
       renderText("参数不正确");
       return null;
     }
     TypeSaveState tempType = (TypeSaveState)obj;
     return tempType;
   }







   private String getResult()
   {
     GoodsType tempType = getTypeFromSession();
     this.goodsType = getTypeFromSession();
     TypeSaveState saveState = getSaveStateFromSession();
     String result = null;

     if ((tempType.getHave_prop() != 0) && (saveState.getDo_save_props() == 0)) {
       result = "add_props";
     } else if ((tempType.getHave_parm() != 0) && (saveState.getDo_save_params() == 0))
     {
       result = "add_parms";
     } else if ((tempType.getJoin_brand() != 0) && (saveState.getDo_save_brand() == 0))
     {
       this.brandlist = this.brandManager.list();
       result = "join_brand";
     }
     else {
       result = save();
     }

     return result;
   }




   public String delete()
   {
     try
     {
       if (EopSetting.IS_DEMO_SITE) {
         for (Integer tid : this.type_id) {
           if (tid.intValue() <= 48) {
             showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
             return "json_message";
           }
         }
       }

       int result = this.goodsTypeManager.delete(this.type_id);
       if (result == 1) {
         showSuccessJson("删除成功");
       } else
         showErrorJson("此类型存在与之关联的商品或类别，不能删除。");
     } catch (RuntimeException e) {
       e.printStackTrace();
       showErrorJson("删除失败");
     }
     return "json_message";
   }




   public String clean()
   {
     try
     {
       this.goodsTypeManager.clean(this.type_id);
       this.json = "{'result':0,'message':'清除成功'}";
     } catch (RuntimeException e) {
       this.json = "{'result':1,'message':'清除失败'}";
     }
     return "json_message";
   }




   public String revert()
   {
     try
     {
       this.goodsTypeManager.revert(this.type_id);
       this.json = "{'result':0,'message':'还原成功'}";
     } catch (RuntimeException e) {
       this.json = "{'result':1,'message':'还原失败'}";
     }
     return "json_message";
   }






   public String disPropsInput()
   {
     this.attrList = this.goodsTypeManager.getAttrListByTypeId(this.typeId.intValue());
     this.attrList = ((this.attrList == null) || (this.attrList.isEmpty()) ? null : this.attrList);
     return "props_input";
   }



   public String disParamsInput()
   {
     this.paramAr = this.goodsTypeManager.getParamArByTypeId(this.typeId.intValue());
     return "params_input";
   }


   public String listBrand()
   {
     this.brandlist = this.goodsTypeManager.listByTypeId(this.typeId);
     return "brand_list";
   }

   public List getAttrList() {
     return this.attrList;
   }

   public void setAttrList(List attrList) {
     this.attrList = attrList;
   }

   public ParamGroup[] getParamAr() {
     return this.paramAr;
   }

   public void setParamAr(ParamGroup[] paramAr) {
     this.paramAr = paramAr;
   }

   public GoodsTypeDTO getGoodsType() {
     return this.goodsType;
   }

   public void setGoodsType(GoodsTypeDTO goodsType) {
     this.goodsType = goodsType;
   }

   public String[] getPropnames() {
     return this.propnames;
   }

   public void setPropnames(String[] propnames) {
     this.propnames = propnames;
   }

   public int[] getProptypes() {
     return this.proptypes;
   }

   public void setProptypes(int[] proptypes) {
     this.proptypes = proptypes;
   }

   public String[] getOptions() {
     return this.options;
   }

   public void setOptions(String[] options) {
     this.options = options;
   }

   public IGoodsTypeManager getGoodsTypeManager() {
     return this.goodsTypeManager;
   }

   public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
     this.goodsTypeManager = goodsTypeManager;
   }

   public String[] getGroupnames() {
     return this.groupnames;
   }

   public void setGroupnames(String[] groupnames) {
     this.groupnames = groupnames;
   }

   public String[] getParamnames() {
     return this.paramnames;
   }

   public void setParamnames(String[] paramnames) {
     this.paramnames = paramnames;
   }

   public String getParamnum() {
     return this.paramnum;
   }

   public void setParamnum(String paramnum) {
     this.paramnum = paramnum;
   }

   public Integer[] getChhoose_brands() {
     return this.chhoose_brands;
   }

   public void setChhoose_brands(Integer[] chhoose_brands) {
     this.chhoose_brands = chhoose_brands;
   }


   public Integer getTypeId()
   {
     return this.typeId;
   }

   public void setTypeId(Integer typeId) {
     this.typeId = typeId;
   }

   public int getIs_edit() {
     return this.is_edit;
   }

   public void setIs_edit(int is_edit) {
     this.is_edit = is_edit;
   }

   public Integer[] getType_id() {
     return this.type_id;
   }

   public void setType_id(Integer[] type_id) {
     this.type_id = type_id;
   }

   public void setBrandManager(IBrandManager brandManager) {
     this.brandManager = brandManager;
   }

   public List getBrandlist() {
     return this.brandlist;
   }

   public void setBrandlist(List brandlist) {
     this.brandlist = brandlist;
   }

   public String[] getDatatype() {
     return this.datatype;
   }

   public void setDatatype(String[] datatype) {
     this.datatype = datatype;
   }

   public int[] getRequired() {
     return this.required;
   }

   public void setRequired(int[] required) {
     this.required = required;
   }

   public String[] getUnit() {
     return this.unit;
   }

   public void setUnit(String[] unit) {
     this.unit = unit;
   }

   public Integer getOtherType() {
     return this.otherType;
   }

   public void setOtherType(Integer otherType) {
     this.otherType = otherType;
   }

   public IBrandManager getBrandManager() {
     return this.brandManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\TypeAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */