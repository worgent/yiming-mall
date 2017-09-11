package com.enation.app.shop.core.tag;

import com.enation.app.b2b2c.core.model.store.Store;
import com.enation.app.base.core.model.Member;
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
public class BaozhenGoodsNewUploadTag
        extends BaseFreeMarkerTag
{
    private IGoodsCatManager goodsCatManager;
    private IGoodsManager goodsManager;

    protected Object exec(Map params)
            throws TemplateModelException
    {
        String catid = "";
        String tagid = "3";
        String goodsnum = "18";
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
            jsonOne.put("goods_number", "1");
            jsonOne.put("goods_id", goods.get("goods_id"));
            jsonOne.put("goods_img", goods.get("thumbnail"));
            jsonOne.put("goods_name",
                    goods.get("name"));
            jsonOne.put("goods_price", goods.get("price"));
            jsonOne.put("goods_width", "1");
            jsonOne.put("goods_height", "1");
            jsonOne.put("goods_length", "1");
            jsonOne.put("goods_view_count", goods.get("view_count"));
            jsonOne.put("goods_like_count", "1");
            jsonOne.put("artist_id", goods.get("m_member_id"));
            jsonOne.put("artist_name", goods.get("m_name"));
            jsonOne.put("is_artist_special", "0");
            jsonOne.put("is_resale", "1");
            jsonOne.put("market_price", goods.get("mktprice"));
            jsonOne.put("goods_has_length", "false");
            jsonOne.put("goods_img_original", goods.get("thumbnail"));

            json.add(jsonOne);
            json.add(jsonOne);
            json.add(jsonOne);
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