package com.enation.app.shop.core.tag;


import com.enation.app.shop.core.model.Cat;
import com.enation.app.shop.core.service.IGoodsCatManager;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.taglib.BaseFreeMarkerTag;
import com.enation.framework.util.StringUtil;
import freemarker.template.TemplateModelException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Scope("prototype")
public class BaozhenGoodsCatTag
        extends BaseFreeMarkerTag {
    private IGoodsCatManager goodsCatManager;

    protected Object exec(Map params)
            throws TemplateModelException {
        Integer parentid = (Integer) params.get("parentid");
        if (parentid == null) {
            parentid = Integer.valueOf(0);
        }
        List<Cat> cat_tree = this.goodsCatManager.listAllChildren(parentid);
        String catimage = (String) params.get("catimage");
        boolean showimage = (catimage != null) && (catimage.equals("on"));

        String imgPath = "";
        if (!cat_tree.isEmpty()) {
            for (Cat cat : cat_tree) {
                if ((cat.getImage() != null) && (!StringUtil.isEmpty(cat.getImage()))) {
                    imgPath = UploadUtil.replacePath(cat.getImage());
                    cat.setImage(imgPath);
                }
            }
        }


        Map<String, Object> data = new HashMap();
        data.put("showimg", Boolean.valueOf(showimage));
        data.put("cat_tree", cat_tree);
        JSONArray json = new JSONArray();
        JSONObject jsonOne = new JSONObject();
        JSONArray jsonLevel1 = new JSONArray();
        JSONObject jsonLevel1Ele = new JSONObject();
        JSONArray jsonLevel2 = new JSONArray();
        JSONObject jsonLevel2Ele = new JSONObject();

        jsonLevel1Ele.put("cat_id", "72");
        jsonLevel1Ele.put("parent_id", "0");
        jsonLevel1Ele.put("cat_name", "国画");
        jsonLevel1Ele.put("nav_combine_show", "0");
        jsonLevel1Ele.put("related_category_id", "");
        jsonLevel1.add(jsonLevel1Ele);

        jsonLevel2Ele.put("cat_id", "54");
        jsonLevel2Ele.put("parent_id", "72");
        jsonLevel2Ele.put("cat_name", "纸本水墨");
        jsonLevel2Ele.put("nav_combine_show", "0");
        jsonLevel2Ele.put("related_category_id", "");
        jsonLevel2.add(jsonLevel2Ele);
        jsonLevel2.add(jsonLevel2Ele);
        jsonLevel2.add(jsonLevel2Ele);
        jsonLevel2.add(jsonLevel2Ele);

        jsonOne.put("level1", jsonLevel1);
        jsonOne.put("level2", jsonLevel2);
        json.add(jsonOne);
        json.add(jsonOne);
        data.put("data", json.toString());

        return data;
    }

    public IGoodsCatManager getGoodsCatManager() {
        return this.goodsCatManager;
    }

    public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
        this.goodsCatManager = goodsCatManager;
    }
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\GoodsCatTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */