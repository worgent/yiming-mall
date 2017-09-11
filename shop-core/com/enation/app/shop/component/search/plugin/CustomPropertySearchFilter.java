 package com.enation.app.shop.component.search.plugin;

 import com.enation.app.shop.core.model.Attribute;
 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.plugin.search.IGoodsSearchFilter;
 import com.enation.app.shop.core.plugin.search.IMultiSelector;
 import com.enation.app.shop.core.plugin.search.SearchSelector;
 import com.enation.app.shop.core.service.IGoodsTypeManager;
 import com.enation.app.shop.core.utils.UrlUtils;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.StringUtil;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.LinkedHashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.stereotype.Component;







 @Component
 public class CustomPropertySearchFilter
   extends AutoRegisterPlugin
   implements IGoodsSearchFilter, IMultiSelector
 {
   private IGoodsTypeManager goodsTypeManager;

   public List<SearchSelector> createSelectorList(Cat cat, String url, String urlFragment)
   {
     return null;
   }


   public Map<String, List<SearchSelector>> createMultiSelector(Cat cat, String url, String urlFragment)
   {
     if (cat == null) { return null;
     }


     if (!StringUtil.isEmpty(urlFragment))
     {
       url = UrlUtils.addUrl(url, "prop", urlFragment);
       url = "/search-" + UrlUtils.getParamStr(url);
     }

     List<Attribute> attrList = this.goodsTypeManager.getAttrListByTypeId(cat.getType_id().intValue());

     attrList = attrList == null ? new ArrayList() : attrList;

     Map<String, List<SearchSelector>> map = new LinkedHashMap();
     String[] s_ar = StringUtil.isEmpty(urlFragment) ? new String[0] : StringUtils.split(urlFragment, ",");
     int i = 0;

     for (Attribute attr : attrList)
     {
       String attrName = attr.getName();

       if ((attr.getType() == 3) || (attr.getType() == 6)) {
         List<SearchSelector> selectorList = new ArrayList();
         String[] optionAr = attr.getOptionAr();
         int j = 0;

         boolean haveSelected = false;
         SearchSelector allSelector = new SearchSelector();
         allSelector.setName("全部");
         allSelector.setSelected(false);
         allSelector.setUrl(UrlUtils.getExParamUrl(UrlUtils.getPropExSelf(i, url), "page"));
         selectorList.add(allSelector);


         for (String option : optionAr)
         {

           SearchSelector selector = new SearchSelector();
           selector.setName(option);
           selector.setSelected(isSelected(s_ar, i, j));
           if (selector.getIsSelected()) { haveSelected = true;
           }

           String propurl = UrlUtils.appendParamValue(url, getFilterId(), i + "_" + j);


           selector.setUrl(propurl);
           selectorList.add(selector);

           j++;

           if ((j == optionAr.length) && (!haveSelected)) {
             allSelector.setSelected(true);
           }
         }


         map.put(attrName, selectorList);
       }

       i++;
     }
     return map;
   }








   private boolean isSelected(String[] s_ar, int attrIndex, int optionIndex)
   {
     for (int i = 0; i < s_ar.length; i++) {
       String[] value = s_ar[i].split("\\_");
       int attr_index = Integer.valueOf(value[0]).intValue();
       int option_index = Integer.valueOf(value[1]).intValue();

       if ((attrIndex == attr_index) && (option_index == optionIndex)) {
         return true;
       }
     }

     return false;
   }



   public void filter(StringBuffer sql, Cat cat, String urlFragment)
   {
     if (cat == null) return;
     if (StringUtil.isEmpty(urlFragment)) { return;
     }


     if ((urlFragment != null) && (!urlFragment.equals(""))) {
       List<Attribute> prop_list = this.goodsTypeManager.getAttrListByTypeId(cat.getType_id().intValue());
       prop_list = prop_list == null ? new ArrayList() : prop_list;
       String[] s_ar = StringUtils.split(urlFragment, ",");
       Map<String, String> mutil = new HashMap();

       for (int i = 0; i < s_ar.length; i++) {
         String[] value = s_ar[i].split("\\_");
         int index = Integer.valueOf(value[0]).intValue();
         Attribute attr = (Attribute)prop_list.get(index);
         int type = attr.getType();
         if ((type != 2) && (type != 5))
         {
           if (type == 6)
           {
             String key = "g.p" + (index + 1);
             String term = key + " like '%#" + value[1] + "%'";
             String temp = (String)mutil.get(key);

             if (temp != null) {
               term = temp + " or " + term;
             }

             mutil.put(key, term);
           }
           else {
             sql.append(" and g.p" + (index + 1));

             if (type == 1) {
               sql.append(" like'%");
               sql.append(value[1]);
               sql.append("%'");
             }
             if ((type == 3) || (type == 4)) {
               sql.append("='");
               sql.append(value[1]);
               sql.append("'");
             }
           }
         }
       }


       Iterator<String> keySet = mutil.keySet().iterator();
       while (keySet.hasNext()) {
         String key = (String)keySet.next();
         String term = (String)mutil.get(key);
         sql.append(" and (" + term + ")");
       }
     }
   }





   public String getFilterId()
   {
     return "prop";
   }


   public String getAuthor()
   {
     return "kingapex";
   }


   public String getId()
   {
     return "goodsPropertySearchFilter";
   }


   public String getName()
   {
     return "商品属性过滤器";
   }


   public String getType()
   {
     return "searchFilter";
   }


   public String getVersion()
   {
     return "1.0";
   }



   public void perform(Object... params) {}


   public void register() {}


   public IGoodsTypeManager getGoodsTypeManager()
   {
     return this.goodsTypeManager;
   }

   public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager)
   {
     this.goodsTypeManager = goodsTypeManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\search\plugin\CustomPropertySearchFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */