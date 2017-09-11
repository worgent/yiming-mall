package com.enation.app.shop.core.tag;

import com.enation.app.b2b2c.core.service.goods.IStoreGoodsManager;
import com.enation.app.b2b2c.core.service.goods.IStoreGoodsTagManager;
import com.enation.app.b2b2c.core.service.store.IStoreManager;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.database.Page;
import com.enation.framework.taglib.BaseFreeMarkerTag;
import freemarker.template.TemplateModelException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;





@Component
public class BaozhenArtistStarTag
        extends BaseFreeMarkerTag
{
    private IStoreManager storeManager;
    private IStoreGoodsManager storeGoodsManager;
    private IStoreGoodsTagManager storeGoodsTagManager;

    protected Object exec(Map params)
            throws TemplateModelException
    {
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();

        List stores = this.storeManager.store_recommend_list();
        JSONArray json = new JSONArray();
        for(Object o : stores){
            JSONObject jsonStore = new JSONObject();
            JSONObject jsonObject = JSONObject.fromObject(o);
            jsonStore.put("artist_id", jsonObject.get("member_id"));
            jsonStore.put("artist_name", jsonObject.get("name"));
            jsonStore.put("artist_graduate", "其他学校");
            jsonStore.put("artist_collect_count", jsonObject.get("store_collect"));
            jsonStore.put("artist_concern_count", "1252");
            jsonStore.put("art_head_imgurl", toUrl(jsonObject.getString("face")));
            jsonStore.put("artist_art_count", jsonObject.get("goods_num"));

            JSONArray jsonGoods = new JSONArray();
            Map map = new HashMap();
            map.put("mark", "recommend");
            map.put("storeid", jsonObject.get("store_id"));

            Map result = new HashMap();
            int page = 1;
            int pageSize = 2;
            Page webpage = this.storeGoodsTagManager.getGoodsList(map, page, pageSize);
            List goods = (List) webpage.getResult();
            for(Object o2 : goods){
                JSONObject jsonObject2 = JSONObject.fromObject(o2);
                JSONObject jsonGood = new JSONObject();
                jsonGood.put("goods_id", jsonObject2.get("goods_id"));
                jsonGood.put("goods_img", toUrl(jsonObject2.getString("thumbnail")));
                jsonGood.put("goods_name", jsonObject2.get("name"));
                jsonGood.put("goods_price", jsonObject2.get("price"));
                jsonGood.put("goods_width", "1");
                jsonGood.put("goods_height", "1");
                jsonGood.put("goods_length", "1");
                jsonGood.put("goods_view_count", jsonObject2.get("view_count"));
                jsonGood.put("goods_like_count", "10");
                jsonGood.put("is_artist_special", "0");
                jsonGood.put("goods_has_length", "false");

                jsonGoods.add(jsonGood);
            }

            jsonStore.put("goods", jsonGoods);

            json.add(jsonStore);
            json.add(jsonStore);
            json.add(jsonStore);
        }
        return json.toString();
    }
    protected String toUrl(String path)
    {
        String urlBase = EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath();

        return path.replaceAll("fs:", urlBase);
    }
    public IStoreManager getStoreManager() { return this.storeManager; }

    public void setStoreManager(IStoreManager storeManager) {
        this.storeManager = storeManager;
    }
    public IStoreGoodsManager getStoreGoodsManager() {
        return this.storeGoodsManager;
    }

    public void setStoreGoodsManager(IStoreGoodsManager storeGoodsManager) {
        this.storeGoodsManager = storeGoodsManager;
    }


    public IStoreGoodsTagManager getStoreGoodsTagManager() { return this.storeGoodsTagManager; }

    public void setStoreGoodsTagManager(IStoreGoodsTagManager storeGoodsTagManager) {
        this.storeGoodsTagManager = storeGoodsTagManager;
    }
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\store\StoreSearchTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */