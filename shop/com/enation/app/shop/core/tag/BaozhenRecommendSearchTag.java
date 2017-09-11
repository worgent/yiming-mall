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

//搜索条下推荐设置
@Component
@Scope("prototype")
public class BaozhenRecommendSearchTag
        extends BaseFreeMarkerTag {
    private IGoodsCatManager goodsCatManager;

    protected Object exec(Map params)
            throws TemplateModelException {

        Map<String, Object> data = new HashMap();

        JSONArray json = new JSONArray();
        JSONObject jsonOne = new JSONObject();
        jsonOne.put("name", "测试热词1");
        jsonOne.put("href", "/page/index.html");
        json.add(jsonOne);
        json.add(jsonOne);
        json.add(jsonOne);

        data.put("data", json);

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