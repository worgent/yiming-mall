 package com.enation.app.b2b2c.core.tag.store;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.IStoreDlyTypeManager;
 import com.enation.app.b2b2c.core.service.IStoreRegionsManager;
 import com.enation.app.b2b2c.core.service.IStoreTemplateManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.shop.core.model.support.DlyTypeConfig;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import net.sf.json.JSONObject;
 import org.springframework.stereotype.Component;

















 @Component
 public class StoreTransportListTag
   extends BaseFreeMarkerTag
 {
   private IStoreDlyTypeManager storeDlyTypeManager;
   private IStoreTemplateManager storeTemplateManager;
   private IStoreRegionsManager storeRegionsManager;
   private IStoreMemberManager storeMemberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     StoreMember storeMember = this.storeMemberManager.getStoreMember();


     List<Map> templateList = this.storeTemplateManager.getTemplateList(storeMember.getStore_id());

     for (Map map : templateList) {
       Integer template_id = (Integer)map.get("id");
       List<Map> dlytypelist = this.storeDlyTypeManager.getDlyTypeList(template_id);
       map.put("dlylist", dlytypelist);


       for (Map dlymap : dlytypelist) {
         Integer type_id = (Integer)dlymap.get("type_id");
         List<Map> arealist = this.storeDlyTypeManager.getDlyTypeAreaList(type_id);
         dlymap.put("arealist", arealist);
         dlymap.put("area", "全国");
         DlyTypeConfig dlyConfig = convertTypeJson((String)dlymap.get("config"));
         dlymap.put("dlyConfig", dlyConfig);


         for (Map areamap : arealist) {
           areamap.put("area", areamap.get("area_name_group"));
           DlyTypeConfig areaConfig = convertTypeJson((String)areamap.get("config"));
           areamap.put("areaConfig", areaConfig);
         }
       }
     }

     return templateList;
   }

   private DlyTypeConfig convertTypeJson(String config)
   {
     JSONObject typeJsonObject = JSONObject.fromObject(config);
     DlyTypeConfig typeConfig = (DlyTypeConfig)JSONObject.toBean(typeJsonObject, DlyTypeConfig.class);


     return typeConfig;
   }



   public IStoreDlyTypeManager getStoreDlyTypeManager()
   {
     return this.storeDlyTypeManager;
   }

   public void setStoreDlyTypeManager(IStoreDlyTypeManager storeDlyTypeManager) { this.storeDlyTypeManager = storeDlyTypeManager; }

   public IStoreTemplateManager getStoreTemplateManager()
   {
     return this.storeTemplateManager;
   }

   public void setStoreTemplateManager(IStoreTemplateManager storeTemplateManager) {
     this.storeTemplateManager = storeTemplateManager;
   }

   public IStoreRegionsManager getStoreRegionsManager()
   {
     return this.storeRegionsManager;
   }

   public void setStoreRegionsManager(IStoreRegionsManager storeRegionsManager) {
     this.storeRegionsManager = storeRegionsManager;
   }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\store\StoreTransportListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */