package com.enation.app.shop.core.tag;

import com.enation.app.b2b2c.core.model.store.Store;
import com.enation.app.base.core.model.Member;
import com.enation.app.shop.core.model.Cat;
import com.enation.app.shop.core.model.Goods;
import com.enation.app.shop.core.service.IGoodsCatManager;
import com.enation.app.shop.core.service.IGoodsManager;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.taglib.BaseFreeMarkerTag;
import freemarker.template.TemplateModelException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class BaozhenGoodsWeekRecommendTag
        extends BaseFreeMarkerTag
{
    private IGoodsCatManager goodsCatManager;
    private IGoodsManager goodsManager;

    protected Object exec(Map params)
            throws TemplateModelException
    {
        String catid = "";
        String tagid = "11";
        String goodsnum = "100";
        String uri;
        if ((catid == null) || (catid.equals(""))) {
            uri = ThreadContextHolder.getHttpRequest().getServletPath();
        }


        List goodsList = this.goodsManager.listGoods(catid, tagid, goodsnum);
        JSONArray json = new JSONArray();

        for(Object o : goodsList) {
//             Map goods = (Map) o;
            JSONObject goods = JSONObject.fromObject(o);
//            Member member = (Member) o;
            JSONObject jsonOne = new JSONObject();
            jsonOne.put("goods_id", goods.get("goods_id"));
            jsonOne.put("cat_id", goods.get("cat_id"));
            jsonOne.put("goods_img", goods.get("thumbnail"));
            jsonOne.put("goods_name",
                    goods.get("name"));
            jsonOne.put("original_goods_width", null);
            jsonOne.put("original_goods_height", null);
            jsonOne.put("goods_number", "1");
            jsonOne.put("not_sale", "0");
            jsonOne.put("line_sale", "0");
            jsonOne.put("is_bargain", "0");

            jsonOne.put("goods_price", goods.get("price"));
            jsonOne.put("goods_width", "1");
            jsonOne.put("goods_height", "1");
            jsonOne.put("goods_length", "1");
            jsonOne.put("goods_view_count", goods.get("view_count"));
            jsonOne.put("goods_like_count", "1");
            jsonOne.put("artist_id", goods.get("m_member_id"));
            jsonOne.put("artist_name", goods.get("m_name"));
            jsonOne.put("is_artist_special", "0");
            jsonOne.put("goods_has_length", "false");
            jsonOne.put("goods_img_original", goods.get("thumbnail"));

            //获得父类
            List<Cat> cats = goodsCatManager.getParents(goods.getInt("cat_id"));
            Cat Root = cats.size() == 0 ? goodsCatManager.getById(goods.getInt("cat_id")) : cats.get(0);
            for( Object o2 : json){
                JSONObject jsonCat = (JSONObject)o2;
                if( jsonCat.getInt("cat_id") == Root.getCat_id() ){
                    jsonCat.getJSONArray("goods").add(jsonOne);
                    jsonCat.getJSONArray("goods").add(jsonOne);
                    jsonOne.clear();
                    break;
                }
            }
            if( jsonOne.isEmpty() == false ){
                JSONObject jsonCat = new JSONObject();
                jsonCat.put("cat_id", Root.getCat_id());
                jsonCat.put("cat_name", Root.getName());
                JSONArray jsonArray = new JSONArray();
                jsonArray.add(jsonOne);
                jsonCat.put("goods", jsonArray);
                json.add(jsonCat);
            }
//            JSONObject jsonCat = json.getJSONObject("")
//            json.add(jsonOne);
//            json.add(jsonOne);
//            json.add(jsonOne);
        }
        return json.toString();
    }

    public IGoodsCatManager getGoodsCatManager()
    {
        return this.goodsCatManager;
    }

    public void setGoodsCatManager(IGoodsCatManager goodsCatManager) { this.goodsCatManager = goodsCatManager; }

    public IGoodsManager getGoodsManager() {
        return this.goodsManager;
    }

    public void setGoodsManager(IGoodsManager goodsManager) { this.goodsManager = goodsManager; }
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\GoodsListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */