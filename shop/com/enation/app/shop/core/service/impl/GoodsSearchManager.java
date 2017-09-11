//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.enation.app.shop.core.service.impl;

import com.enation.app.base.core.model.Member;
import com.enation.app.base.core.model.MemberLv;
import com.enation.app.shop.core.model.Attribute;
import com.enation.app.shop.core.model.GoodsLvPrice;
import com.enation.app.shop.core.model.support.GoodsView;
import com.enation.app.shop.core.service.IGoodsSearchManager;
import com.enation.app.shop.core.service.IGoodsTypeManager;
import com.enation.app.shop.core.service.IMemberLvManager;
import com.enation.app.shop.core.service.IMemberPriceManager;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.eop.sdk.user.IUserService;
import com.enation.eop.sdk.user.UserServiceFactory;
import com.enation.eop.sdk.utils.UploadUtil;
import com.enation.framework.database.Page;
import com.enation.framework.database.StringMapper;
import com.enation.framework.util.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;

public class GoodsSearchManager extends BaseSupport implements IGoodsSearchManager {
    private IMemberPriceManager memberPriceManager;
    private IMemberLvManager memberLvManager;
    private IGoodsTypeManager goodsTypeManager;

    public GoodsSearchManager() {
    }

    public Page search(int page, int pageSize, Map<String, String> params) {
        String cat_path = (String)params.get("cat_path");
        String order = (String)params.get("order");
        String brandStr = (String)params.get("brandStr");
        String propStr = (String)params.get("propStr");
        String keyword = (String)params.get("keyword");
        String minPrice = (String)params.get("minPrice");
        String maxPrice = (String)params.get("maxPrice");
        String tagids = (String)params.get("tagids");
        String attrStr = (String)params.get("attrStr");
        int typeid = Integer.valueOf((String)params.get("typeid")).intValue();
        List list = this.listByCatId(typeid, cat_path, page, pageSize, order, brandStr, propStr, keyword, minPrice, maxPrice, tagids, attrStr);
        long count = this.countByCatId(typeid, cat_path, brandStr, propStr, keyword, minPrice, maxPrice, tagids, attrStr);
        Page webPage = new Page(0L, count, pageSize, list);
        return webPage;
    }

    public List[] getPropListByCat(final int type_id, String cat_path, String brand_str, String propStr, String attrStr) {
        List temp_prop_list = this.goodsTypeManager.getAttrListByTypeId(type_id);
        final Object var13 = temp_prop_list == null?new ArrayList():temp_prop_list;
        String[] temp_brand_list;
        if(propStr != null && !propStr.equals("")) {
            temp_brand_list = propStr.split(",");

            for(int propList = 0; propList < temp_brand_list.length; ++propList) {
                String[] brandList = temp_brand_list[propList].split("\\_");
                int sql = Integer.valueOf(brandList[0]).intValue();
                Attribute mapper = (Attribute)((List)var13).get(sql);
                mapper.getOptionMap()[Integer.valueOf(brandList[1]).intValue()].put("selected", Integer.valueOf(1));
                if(mapper.getType() == 3) {
                    mapper.setHidden(1);
                } else {
                    mapper.setHidden(0);
                }
            }
        }

        temp_brand_list = null;
        final List var14 = this.goodsTypeManager.getBrandListByTypeId(type_id);
        String var16 = "select g.* from " + this.getTableName("goods") + " g where g.disabled=0 and g.market_enable=1 and g.cat_id in(";
        var16 = var16 + "select c.cat_id from " + this.getTableName("goods_cat") + " c where c.cat_path like \'" + cat_path + "%\')";
        var16 = var16 + this.buildTermForByCat(type_id, brand_str, propStr, attrStr);
        RowMapper var15 = new RowMapper() {
            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
                GoodsView goods = new GoodsView();
                int i;
                if(rs.getInt("type_id") == type_id) {
                    for(i = 0; i < 20 && i < ((List)var13).size(); ++i) {
                        String brand = rs.getString("p" + (i + 1));
                        Attribute obj_num = (Attribute)((List)var13).get(i);
                        if(obj_num.getType() == 3 && brand != null && !brand.toString().equals("")) {
                            int[] num = obj_num.getNums();
                            int pos = Integer.valueOf(brand).intValue();
                            ++num[pos];
                        }
                    }
                }

                for(i = 0; i < var14.size(); ++i) {
                    Map var9 = (Map)var14.get(i);
                    if(rs.getInt("brand_id") == ((Integer)var9.get("brand_id")).intValue()) {
                        Object var10 = var9.get("num");
                        if(var10 == null) {
                            var10 = Integer.valueOf(0);
                        }

                        int var11 = Integer.valueOf(var10.toString()).intValue();
                        ++var11;
                        var9.put("num", Integer.valueOf(var11));
                    }
                }

                return goods;
            }
        };
        this.daoSupport.queryForList(var16, var15, new Object[0]);
        List[] props = new List[]{(List)var13, var14};
        return props;
    }

    private List getSpecListByCatId(String cat_path) {
        String sql = "select s.* from " + this.getTableName("product") + " s," + this.getTableName("goods") + " g  where s.goods_id=g.goods_id  ";
        if(!StringUtil.isEmpty(cat_path)) {
            sql = sql + " and g.cat_id in(";
            sql = sql + "select c.cat_id from " + this.getTableName("goods_cat") + " c where c.cat_path like \'" + cat_path + "%\')";
        }

        List specList = this.daoSupport.queryForList(sql, new Object[0]);
        return specList;
    }

    private List listByCatId(int typeid, String cat_path, int page, int pageSize, String order, String brand_str, String propStr, String keyword, String minPrice, String maxPrice, String tagids, String attrStr) {
        final List goods_spec_list = this.getSpecListByCatId(cat_path);
        if("1".equals(order)) {
            order = "last_modify desc";
        } else if("1".equals(order)) {
            order = "last_modify asc";
        } else if("2".equals(order)) {
            order = "last_modify asc";
        } else if("3".equals(order)) {
            order = "price desc";
        } else if("4".equals(order)) {
            order = "price asc";
        } else if("5".equals(order)) {
            order = "view_count desc";
        } else if("6".equals(order)) {
            order = "buy_count asc";
        } else if(order == null || order.equals("") || order.equals("0")) {
            order = "sord desc";
        }

        String sql = "select g.* from " + this.getTableName("goods") + " g where g.goods_type = \'normal\' and g.disabled=0 and market_enable=1 ";
        if(cat_path != null) {
            sql = sql + " and  g.cat_id in(";
            sql = sql + "select c.cat_id from " + this.getTableName("goods_cat") + " c where c.cat_path like \'" + cat_path + "%\')  ";
        }

        sql = sql + this.buildTermForByCat(typeid, brand_str, propStr, keyword, minPrice, maxPrice, tagids, attrStr);
        sql = sql + " order by " + order;
        IUserService userService = UserServiceFactory.getUserService();
        final Member member = userService.getCurrentMember();
        Object memPriceList = new ArrayList();
        double discount = 1.0D;
        if(member != null && member.getLv_id() != null) {
            memPriceList = this.memberPriceManager.listPriceByLvid(member.getLv_id().intValue());
            MemberLv priceList = this.memberLvManager.get(member.getLv_id());
            discount = (double)priceList.getDiscount().intValue() / 100.0D;
            this.applyMemPrice(goods_spec_list, (List)memPriceList, discount);
        }

        final Object finalMemPriceList = memPriceList;
        final double finalDiscount = discount;
        RowMapper mapper = new RowMapper() {
            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
                GoodsView goods = new GoodsView();
                goods.setName(rs.getString("name"));
                goods.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
                goods.setMktprice(Double.valueOf(rs.getDouble("mktprice")));
                goods.setPrice(Double.valueOf(rs.getDouble("price")));
                goods.setCreate_time(Long.valueOf(rs.getLong("create_time")));
                goods.setLast_modify(Long.valueOf(rs.getLong("last_modify")));
                goods.setType_id(Integer.valueOf(rs.getInt("type_id")));
                goods.setStore(Integer.valueOf(rs.getInt("store")));
                List specList = GoodsSearchManager.this.getSpecList(goods.getGoods_id().intValue(), goods_spec_list);
                goods.setSpecList(specList);
                goods.setHasSpec(rs.getInt("have_spec"));
                goods.setSn(rs.getString("sn"));
                goods.setIntro(rs.getString("intro"));
                String thumbnail = rs.getString("thumbnail");
                if(thumbnail != null) {
                    thumbnail = UploadUtil.replacePath(thumbnail);
                    goods.setThumbnail(thumbnail);
                }

                goods.setCat_id(Integer.valueOf(rs.getInt("cat_id")));
                if(goods.getHasSpec() == 0 && specList != null && !specList.isEmpty()) {
                    goods.setProductid((Integer)((Map)specList.get(0)).get("product_id"));
                }

                if(member != null && goods.getProductid() != null) {
                    goods.setPrice(Double.valueOf(goods.getPrice().doubleValue() * finalDiscount));
                    Iterator propMap = ((List) finalMemPriceList).iterator();

                    while(propMap.hasNext()) {
                        GoodsLvPrice i = (GoodsLvPrice)propMap.next();
                        if(goods.getProductid().intValue() == i.getProductid()) {
                            goods.setPrice(i.getPrice());
                        }
                    }
                }

                HashMap var9 = new HashMap();

                for(int var10 = 0; var10 < 20; ++var10) {
                    String value = rs.getString("p" + (var10 + 1));
                    var9.put("p" + (var10 + 1), value);
                }

                goods.setPropMap(var9);
                return goods;
            }
        };
        List goodslist = this.daoSupport.queryForList(sql, page, pageSize, mapper);
        return goodslist;
    }

    private void applyMemPrice(List<Map> proList, List<GoodsLvPrice> memPriceList, double discount) {
        Iterator i$ = proList.iterator();

        while(i$.hasNext()) {
            Map pro = (Map)i$.next();
            double price = Double.valueOf(pro.get("price").toString()).doubleValue() * discount;
            Iterator i$1 = memPriceList.iterator();

            while(i$1.hasNext()) {
                GoodsLvPrice lvPrice = (GoodsLvPrice)i$1.next();
                if(((Integer)pro.get("product_id")).intValue() == lvPrice.getProductid()) {
                    price = lvPrice.getPrice().doubleValue();
                }
            }

            pro.put("price", Double.valueOf(price));
        }

    }

    private long countByCatId(int typeid, String cat_path, String brand_str, String propStr, String keyword, String minPrice, String maxPrice, String tagids, String attrStr) {
        String sql = "select count(0) from " + this.getTableName("goods") + " g where g.disabled=0 and market_enable=1 ";
        if(cat_path != null) {
            sql = sql + " and g.cat_id in(";
            sql = sql + "select c.cat_id from " + this.getTableName("goods_cat") + " c where c.cat_path like \'" + cat_path + "%\')";
        }

        sql = sql + this.buildTermForByCat(typeid, brand_str, propStr, keyword, minPrice, maxPrice, tagids, attrStr);
        long count = this.daoSupport.queryForLong(sql, new Object[0]);
        return count;
    }

    private List getSpecList(int goods_id, List specList) {
        ArrayList list = new ArrayList();

        for(int i = 0; i < specList.size(); ++i) {
            Map spec = (Map)specList.get(i);
            Integer temp_id = (Integer)spec.get("goods_id");
            if(temp_id.intValue() == goods_id) {
                list.add(spec);
            }
        }

        return list;
    }

    private String buildTerm(int typeid, String brand_str, String propStr, String keyword, String minPrice, String maxPrice, String attrStr) {
        StringBuffer sql = new StringBuffer();
        sql.append(this.buildTermForByCat(typeid, brand_str, propStr, attrStr));
        if(keyword != null) {
            sql.append(" and g.name like \'%");
            sql.append(keyword);
            sql.append("%\'");
        }

        if(minPrice != null) {
            sql.append(" and  g.price>=");
            sql.append(minPrice);
        }

        if(maxPrice != null) {
            sql.append(" and g.price<=");
            sql.append(maxPrice);
        }

        return sql.toString();
    }

    private String buildTermForByCat(int typeid, String brand_str, String propStr, String keyword, String minPrice, String maxPrice, String tagids, String attrStr) {
        StringBuffer sql = new StringBuffer(this.buildTerm(typeid, brand_str, propStr, keyword, minPrice, maxPrice, attrStr));
        if(tagids != null) {
            String filter = this.goodsIdInTags(tagids);
            filter = filter.equals("")?"-1":filter;
            sql.append(" and goods_id in(" + filter + ")");
        }

        return sql.toString();
    }

    private String goodsIdInTags(String tags) {
        String sql = "select rel_id from tag_rel where tag_id in (" + tags + ")";
        List goodsIdList = this.baseDaoSupport.queryForList(sql, new StringMapper(), new Object[0]);
        return StringUtil.listToString(goodsIdList, ",");
    }

    private String buildTermForByCat(int typeid, String brand_str, String propStr, String attrStr) {
        StringBuffer sql = new StringBuffer();
        if(brand_str != null && !brand_str.equals("")) {
            brand_str = "-1," + brand_str.replaceAll("\\_", ",");
            sql.append(" and g.brand_id in(");
            sql.append(brand_str);
            sql.append(")");
        }

        String[] s_ar;
        int i;
        if(!StringUtil.isEmpty(attrStr)) {
            String[] prop_list = attrStr.split(",");
            s_ar = prop_list;
            i = prop_list.length;

            for(int value = 0; value < i; ++value) {
                String index = s_ar[value];
                String[] attr = index.split("\\_");
                if(attr.length == 2) {
                    sql.append(" and " + attr[0] + "=" + attr[1]);
                }
            }
        }

        if(propStr != null && !propStr.equals("")) {
            List var14 = this.goodsTypeManager.getAttrListByTypeId(typeid);
            Object var13 = var14 == null?new ArrayList():var14;
            s_ar = propStr.split(",");

            for(i = 0; i < s_ar.length; ++i) {
                String[] var15 = s_ar[i].split("\\_");
                int var17 = Integer.valueOf(var15[0]).intValue();
                Attribute var16 = (Attribute)((List)var13).get(var17);
                int type = var16.getType();
                if(type != 2 && type != 5) {
                    sql.append(" and g.p" + (var17 + 1));
                    if(type == 1) {
                        sql.append(" like\'%");
                        sql.append(var15[1]);
                        sql.append("%\'");
                    }

                    if(type == 3 || type == 4) {
                        sql.append("=\'");
                        sql.append(var15[1]);
                        sql.append("\'");
                    }
                }
            }
        }

        return sql.toString();
    }

    public IGoodsTypeManager getGoodsTypeManager() {
        return this.goodsTypeManager;
    }

    public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
        this.goodsTypeManager = goodsTypeManager;
    }

    public IMemberPriceManager getMemberPriceManager() {
        return this.memberPriceManager;
    }

    public void setMemberPriceManager(IMemberPriceManager memberPriceManager) {
        this.memberPriceManager = memberPriceManager;
    }

    public IMemberLvManager getMemberLvManager() {
        return this.memberLvManager;
    }

    public void setMemberLvManager(IMemberLvManager memberLvManager) {
        this.memberLvManager = memberLvManager;
    }
}
