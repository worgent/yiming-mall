//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.enation.app.cms.core.service.impl;

import com.enation.app.base.core.model.MultiSite;
import com.enation.app.cms.core.model.DataCat;
import com.enation.app.cms.core.model.DataField;
import com.enation.app.cms.core.model.DataModel;
import com.enation.app.cms.core.plugin.ArticlePluginBundle;
import com.enation.app.cms.core.plugin.IFieldValueShowEvent;
import com.enation.app.cms.core.service.IDataCatManager;
import com.enation.app.cms.core.service.IDataFieldManager;
import com.enation.app.cms.core.service.IDataManager;
import com.enation.app.cms.core.service.IDataModelManager;
import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
import com.enation.eop.sdk.context.EopContext;
import com.enation.eop.sdk.database.BaseSupport;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.database.IntegerMapper;
import com.enation.framework.database.Page;
import com.enation.framework.plugin.IPlugin;
import com.enation.framework.util.DateUtil;
import com.enation.framework.util.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class DataManager extends BaseSupport implements IDataManager {
    private IDataModelManager dataModelManager;
    private IDataFieldManager dataFieldManager;
    private ArticlePluginBundle articlePluginBundle;
    private IDataCatManager dataCatManager;

    public DataManager() {
    }

    @Transactional(
            propagation = Propagation.REQUIRED
    )
    public void add(Integer modelid, Integer catid) {
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        DataModel dataModel = this.dataModelManager.get(modelid);
        List fieldList = this.dataFieldManager.list(modelid);
        HashMap article = new HashMap();
        Iterator sort = fieldList.iterator();

        while(sort.hasNext()) {
            DataField page_title = (DataField)sort.next();
            this.articlePluginBundle.onSave(article, page_title);
        }

        String sort1 = request.getParameter("sort");
        String page_title1 = request.getParameter("page_title");
        String page_keywords = request.getParameter("page_keywords");
        String page_description = request.getParameter("page_description");
        sort1 = StringUtil.isEmpty(sort1)?"0":sort1;
        Long now = Long.valueOf(DateUtil.getDatelineLong());
        article.put("cat_id", String.valueOf(catid.intValue()));
        article.put("sort", sort1);
        article.put("hit", Integer.valueOf(0));
        article.put("page_keywords", page_keywords);
        article.put("page_title", page_title1);
        article.put("page_description", page_description);
        article.put("add_time", now);
        article.put("lastmodified", now);
        if(EopContext.getContext().getCurrentSite().getMulti_site().intValue() == 1) {
            Integer article_id = EopContext.getContext().getCurrentChildSite().getCode();
            article.put("site_code", article_id);
        }

        this.baseDaoSupport.insert(dataModel.getEnglish_name(), article);
        String article_id1 = String.valueOf(this.baseDaoSupport.getLastId(dataModel.getEnglish_name()));
        article.put("id", article_id1);
        this.articlePluginBundle.onSave(article, dataModel, 1);
    }

    public void delete(Integer catid, Integer articleid) {
        DataModel dataModel = this.getModelByCatid(catid);
        this.articlePluginBundle.onDelete(catid, articleid);
        String sql = "delete from " + dataModel.getEnglish_name() + " where id=?";
        this.baseDaoSupport.execute(sql, new Object[]{articleid});
    }

    public void edit(Integer modelid, Integer catid, Integer articleid) {
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        DataModel dataModel = this.dataModelManager.get(modelid);
        if(EopContext.getContext().getCurrentSite().getMulti_site().intValue() == 1) {
            Integer fieldList = EopContext.getContext().getCurrentChildSite().getCode();
            String article = request.getParameter("site_code");
            if(!fieldList.toString().equals(article)) {
                throw new IllegalArgumentException("不是本站数据，不能修改");
            }
        }

        List fieldList1 = this.dataFieldManager.list(modelid);
        HashMap article1 = new HashMap();
        Iterator page_title = fieldList1.iterator();

        while(page_title.hasNext()) {
            DataField page_keywords = (DataField)page_title.next();
            this.articlePluginBundle.onSave(article1, page_keywords);
        }

        String page_title1 = request.getParameter("page_title");
        String page_keywords1 = request.getParameter("page_keywords");
        String page_description = request.getParameter("page_description");
        article1.put("page_keywords", page_keywords1);
        article1.put("page_title", page_title1);
        article1.put("page_description", page_description);
        String sort = request.getParameter("sort");
        sort = StringUtil.isEmpty(sort)?"0":sort;
        article1.put("cat_id", catid);
        article1.put("sort", sort);
        article1.put("lastmodified", Long.valueOf(DateUtil.getDatelineLong()));
        this.baseDaoSupport.update(dataModel.getEnglish_name(), article1, "id=" + articleid);
        this.articlePluginBundle.onSave(article1, dataModel, 2);
    }

    public Page list(Integer catid, int page, int pageSize) {
        DataModel model = this.getModelByCatid(catid);
        String sql = "select " + this.buildFieldStr(model.getModel_id()) + ",sort from " + model.getEnglish_name() + " where cat_id=? order by sort desc, add_time desc";
        Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[]{catid});
        return webpage;
    }

    public Page list(Integer catid, int page, int pageSize, Integer site_code) {
        DataModel model = this.getModelByCatid(catid);
        String sql = "select " + this.buildFieldStr(model.getModel_id()) + ",sort from " + model.getEnglish_name() + " where cat_id=? and site_code between " + site_code + " and " + StringUtil.getMaxLevelCode(site_code.intValue()) + " and (not siteidlist like \'%," + EopContext.getContext().getCurrentChildSite().getSiteid() + ",%\' or siteidlist is null) order by sort desc, add_time desc";
        Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[]{catid});
        return webpage;
    }

    public void importdata(Integer catid, Integer[] ids) {
        DataModel model = this.getModelByCatid(catid);
        String ids_str = StringUtil.arrayToString(ids, ",");
        int site_id = EopContext.getContext().getCurrentChildSite().getSiteid().intValue();
        String sql = "update " + model.getEnglish_name() + " set siteidlist = CASE WHEN siteidlist is null THEN \'," + site_id + ",\' ELSE CONCAT(siteidlist,\'" + site_id + ",\') END where id in (" + ids_str + ")";
        this.baseDaoSupport.execute(sql, new Object[0]);
    }

    public List list(Integer catid) {
        DataModel model = this.getModelByCatid(catid);
        String sql = "select " + this.buildFieldStr(model.getModel_id()) + ",sort from " + model.getEnglish_name() + " where cat_id=? order by sort desc, add_time desc";
        List webpage = this.baseDaoSupport.queryForList(sql, new Object[]{catid});
        return webpage;
    }

    @Transactional(
            propagation = Propagation.REQUIRED
    )
    public void updateSort(Integer[] ids, Integer[] sorts, Integer catid) {
        if(ids != null && sorts != null && sorts.length == ids.length) {
            DataModel model = this.getModelByCatid(catid);
            String sql = "update " + model.getEnglish_name() + " set sort=? where id=?";

            for(int i = 0; i < ids.length; ++i) {
                this.baseDaoSupport.execute(sql, new Object[]{sorts[i], ids[i]});
            }

        } else {
            throw new IllegalArgumentException("ids or sorts params error");
        }
    }

    public Page listAll(Integer catid, String term, int page, int pageSize) {
        DataModel model = this.getModelByCatid(catid);
        DataCat cat = this.dataCatManager.get(catid);
        StringBuffer sql = new StringBuffer("select * from ");
        sql.append(this.getTableName(model.getEnglish_name()));
        sql.append(" where cat_id in (select cat_id from ");
        sql.append(this.getTableName("data_cat"));
        sql.append(" where cat_path like \'");
        sql.append(cat.getCat_path());
        sql.append("%\'");
        sql.append(") ");
        if(!StringUtil.isEmpty(term)) {
            sql.append(term);
        }

        sql.append(" order by sort desc, add_time desc");
        final List fieldList = this.dataFieldManager.list(model.getModel_id());
        return this.daoSupport.queryForPage(sql.toString(), page, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                HashMap data = new HashMap();

                Object value;
                String name;
                for(Iterator cat = fieldList.iterator(); cat.hasNext(); data.put(name, value)) {
                    DataField field = (DataField)cat.next();
                    value = null;
                    name = field.getEnglish_name();
                    value = rs.getString(name);
                    IPlugin plugin = DataManager.this.articlePluginBundle.findPlugin(field.getShow_form());
                    if(plugin != null && plugin instanceof IFieldValueShowEvent) {
                        value = ((IFieldValueShowEvent)plugin).onShow(field, value);
                    }
                }

                data.put("id", Integer.valueOf(rs.getInt("id")));
                data.put("cat_id", Integer.valueOf(rs.getInt("cat_id")));
                data.put("sort", Integer.valueOf(rs.getInt("sort")));
                data.put("add_time", Long.valueOf(rs.getLong("add_time")));
                data.put("lastmodified", Long.valueOf(rs.getLong("lastmodified")));
                data.put("hit", Long.valueOf(rs.getLong("hit")));
                data.put("sys_lock", Integer.valueOf(rs.getInt("sys_lock")));
                DataCat cat1 = DataManager.this.dataCatManager.get(Integer.valueOf(rs.getInt("cat_id")));
                data.put("cat_name", cat1.getName());
                return data;
            }
        }, new Object[0]);
    }

    public List listRelated(Integer catid, Integer relcatid, Integer id, String fieldname) {
        Map article = this.get(id, catid, false);
        String ids = (String)article.get(fieldname);
        if(StringUtil.isEmpty(ids)) {
            return new ArrayList();
        } else {
            DataModel model = this.getModelByCatid(relcatid);
            StringBuffer sql = new StringBuffer("select * from ");
            sql.append(this.getTableName(model.getEnglish_name()));
            sql.append(" where id in (" + ids + ")");
            sql.append(" order by sort desc, add_time desc");
            final List fieldList = this.dataFieldManager.list(model.getModel_id());
            return this.daoSupport.queryForList(sql.toString(), new RowMapper() {
                public Object mapRow(ResultSet rs, int c) throws SQLException {
                    HashMap data = new HashMap();

                    Object value;
                    String name;
                    for(Iterator i$ = fieldList.iterator(); i$.hasNext(); data.put(name, value)) {
                        DataField field = (DataField)i$.next();
                        value = null;
                        name = field.getEnglish_name();
                        value = rs.getString(name);
                        IPlugin plugin = DataManager.this.articlePluginBundle.findPlugin(field.getShow_form());
                        if(plugin != null && plugin instanceof IFieldValueShowEvent) {
                            value = ((IFieldValueShowEvent)plugin).onShow(field, value);
                        }
                    }

                    data.put("id", Integer.valueOf(rs.getInt("id")));
                    data.put("cat_id", Integer.valueOf(rs.getInt("cat_id")));
                    data.put("sort", Integer.valueOf(rs.getInt("sort")));
                    data.put("add_time", Long.valueOf(rs.getLong("add_time")));
                    data.put("lastmodified", Long.valueOf(rs.getLong("lastmodified")));
                    data.put("hit", Long.valueOf(rs.getLong("hit")));
                    data.put("sys_lock", Integer.valueOf(rs.getInt("sys_lock")));
                    data.put("cat_name", "");
                    return data;
                }
            }, new Object[0]);
        }
    }

    public Map get(Integer articleid, Integer catid, boolean filter) {
        DataModel model = this.getModelByCatid(catid);
        String sql = "select * from " + model.getEnglish_name() + " where id=?";
        Map data = this.baseDaoSupport.queryForMap(sql, new Object[]{articleid});
        if(filter) {
            List fieldList = this.dataFieldManager.list(model.getModel_id());
            Iterator i$ = fieldList.iterator();

            while(i$.hasNext()) {
                DataField field = (DataField)i$.next();
                String name = field.getEnglish_name();
                Object value = data.get(name);
                IPlugin plugin = this.articlePluginBundle.findPlugin(field.getShow_form());
                if(plugin != null && plugin instanceof IFieldValueShowEvent) {
                    value = ((IFieldValueShowEvent)plugin).onShow(field, value);
                    data.put(name, value);
                }
            }
        }

        return data;
    }

    public void updateHit(Integer id, Integer catid) {
        DataModel model = this.getModelByCatid(catid);
        this.baseDaoSupport.execute("update " + model.getEnglish_name() + " set hit=hit+1 where id=?", new Object[]{id});
    }

    private DataModel getModelByCatid(Integer catid) {
        String sql = "select dm.* from " + this.getTableName("data_model") + " dm ," + this.getTableName("data_cat") + " c where dm.model_id=c.model_id and c.cat_id=?";
        List modelList = this.daoSupport.queryForList(sql, DataModel.class, new Object[]{catid});
        if(modelList != null && !modelList.isEmpty()) {
            DataModel model = (DataModel)modelList.get(0);
            return model;
        } else {
            throw new RuntimeException("此类别[" + catid + "]不存在模型");
        }
    }

    private String buildFieldStr(Integer modelid) {
        StringBuffer sql = new StringBuffer("id");
        List fieldList = this.dataFieldManager.listIsShow(modelid);
        Iterator i$ = fieldList.iterator();

        while(i$.hasNext()) {
            DataField field = (DataField)i$.next();
            if(field.getIs_show().intValue() == 1) {
                sql.append(",");
                sql.append(field.getEnglish_name());
            }
        }

        return sql.toString();
    }

    public List search(int modelid, String connector) {
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        final List fieldList = this.dataFieldManager.list(Integer.valueOf(modelid));
        DataModel model = this.dataModelManager.get(Integer.valueOf(modelid));
        StringBuffer sql = new StringBuffer();
        sql.append("select * from ");
        sql.append(model.getEnglish_name());
        int i = 0;
        StringBuffer term = new StringBuffer();
        Iterator i$ = fieldList.iterator();

        while(i$.hasNext()) {
            DataField field = (DataField)i$.next();
            String showform = field.getShow_form();
            if(!"image".equals(showform)) {
                String value = request.getParameter(field.getEnglish_name());
                if(!"utf-8".toLowerCase().equals(request.getCharacterEncoding()) && value != null) {
                    value = StringUtil.toUTF8(value);
                }

                String name = field.getEnglish_name();
                FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getCurrInstance();
                freeMarkerPaser.putData(name, value);
                if(!"radio".equals(showform) && !"select".equals(showform)) {
                    if("dateinput".equals(showform)) {
                        String paramname = field.getEnglish_name();
                        String start = request.getParameter(paramname + "_start");
                        String end = request.getParameter(paramname + "_end");
                        if(!StringUtil.isEmpty(start) || !StringUtil.isEmpty(end)) {
                            term.append("(");
                            if(!StringUtil.isEmpty(start)) {
                                term.append(name);
                                term.append(">=\'");
                                term.append(start);
                                term.append("\'");
                            }

                            if(!StringUtil.isEmpty(end)) {
                                if(!StringUtil.isEmpty(start)) {
                                    term.append(connector);
                                }

                                term.append(name);
                                term.append("<=\'");
                                term.append(end);
                                term.append("\'");
                            }

                            term.append(")");
                        }
                    } else {
                        if(StringUtil.isEmpty(value)) {
                            continue;
                        }

                        if(i != 0) {
                            term.append(connector);
                        }

                        term.append(name);
                        term.append(" like \'%");
                        term.append(value);
                        term.append("%\'");
                    }
                } else {
                    if(StringUtil.isEmpty(value)) {
                        continue;
                    }

                    if(i != 0) {
                        term.append(connector);
                    }

                    term.append(name);
                    term.append(" =\'");
                    term.append(value);
                    term.append("\'");
                }

                ++i;
            }
        }

        if(term.length() > 0) {
            sql.append(" where ");
            sql.append(term);
        }

        return this.baseDaoSupport.queryForList(sql.toString(), new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                HashMap data = new HashMap();

                Object value;
                String name;
                for(Iterator i$ = fieldList.iterator(); i$.hasNext(); data.put(name, value)) {
                    DataField field = (DataField)i$.next();
                    value = null;
                    name = field.getEnglish_name();
                    value = rs.getString(name);
                    IPlugin plugin = DataManager.this.articlePluginBundle.findPlugin(field.getShow_form());
                    if(plugin != null && plugin instanceof IFieldValueShowEvent) {
                        value = ((IFieldValueShowEvent)plugin).onShow(field, value);
                    }
                }

                data.put("id", Integer.valueOf(rs.getInt("id")));
                data.put("cat_id", Integer.valueOf(rs.getInt("cat_id")));
                data.put("add_time", Long.valueOf(rs.getLong("add_time")));
                data.put("hit", Long.valueOf(rs.getLong("hit")));
                return data;
            }
        }, new Object[0]);
    }

    public List search(int modelid, String connector, boolean showchild) {
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        final List fieldList = this.dataFieldManager.list(Integer.valueOf(modelid));
        DataModel model = this.dataModelManager.get(Integer.valueOf(modelid));
        StringBuffer sql = new StringBuffer();
        sql.append("select * from ");
        sql.append(model.getEnglish_name());
        int i = 0;
        StringBuffer term = new StringBuffer();
        Iterator site = fieldList.iterator();

        while(site.hasNext()) {
            DataField site_code = (DataField)site.next();
            String site_id = site_code.getShow_form();
            if(!"image".equals(site_id)) {
                String value = request.getParameter(site_code.getEnglish_name());
                if(!"utf-8".toLowerCase().equals(request.getCharacterEncoding()) && value != null) {
                    value = StringUtil.toUTF8(value);
                }

                String name = site_code.getEnglish_name();
                FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getCurrInstance();
                freeMarkerPaser.putData(name, value);
                if(!"radio".equals(site_id) && !"select".equals(site_id)) {
                    if("dateinput".equals(site_id)) {
                        String paramname = site_code.getEnglish_name();
                        String start = request.getParameter(paramname + "_start");
                        String end = request.getParameter(paramname + "_end");
                        if(!StringUtil.isEmpty(start) || !StringUtil.isEmpty(end)) {
                            term.append("(");
                            if(!StringUtil.isEmpty(start)) {
                                term.append(name);
                                term.append(">=\'");
                                term.append(start);
                                term.append("\'");
                            }

                            if(!StringUtil.isEmpty(end)) {
                                if(!StringUtil.isEmpty(start)) {
                                    term.append(connector);
                                }

                                term.append(name);
                                term.append("<=\'");
                                term.append(end);
                                term.append("\'");
                            }

                            term.append(")");
                        }
                    } else {
                        if(StringUtil.isEmpty(value)) {
                            continue;
                        }

                        if(i != 0) {
                            term.append(connector);
                        }

                        term.append(name);
                        term.append(" like \'%");
                        term.append(value);
                        term.append("%\'");
                    }
                } else {
                    if(StringUtil.isEmpty(value)) {
                        continue;
                    }

                    if(i != 0) {
                        term.append(connector);
                    }

                    term.append(name);
                    term.append(" =\'");
                    term.append(value);
                    term.append("\'");
                }

                ++i;
            }
        }

        if(EopContext.getContext().getCurrentSite().getMulti_site().intValue() == 1) {
            MultiSite var20 = EopContext.getContext().getCurrentChildSite();
            Integer var19 = var20.getCode();
            Integer var21 = var20.getSiteid();
            if(i != 0) {
                sql.append(" and ");
            }

            term.append(" ((site_code = " + var19 + ") ");
            term.append(" or (siteidlist like \'%," + var21 + ",%\')");
            if(showchild) {
                term.append(" or (site_code between " + var19 + " and " + StringUtil.getMaxLevelCode(var19.intValue()) + ")");
            }

            term.append(")");
        }

        if(term.length() > 0) {
            sql.append(" where ");
            sql.append(term);
        }

        return this.baseDaoSupport.queryForList(sql.toString(), new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                HashMap data = new HashMap();

                Object value;
                String name;
                for(Iterator i$ = fieldList.iterator(); i$.hasNext(); data.put(name, value)) {
                    DataField field = (DataField)i$.next();
                    value = null;
                    name = field.getEnglish_name();
                    value = rs.getString(name);
                    IPlugin plugin = DataManager.this.articlePluginBundle.findPlugin(field.getShow_form());
                    if(plugin != null && plugin instanceof IFieldValueShowEvent) {
                        value = ((IFieldValueShowEvent)plugin).onShow(field, value);
                    }
                }

                data.put("id", Integer.valueOf(rs.getInt("id")));
                data.put("cat_id", Integer.valueOf(rs.getInt("cat_id")));
                data.put("add_time", Long.valueOf(rs.getLong("add_time")));
                data.put("hit", Long.valueOf(rs.getLong("hit")));
                return data;
            }
        }, new Object[0]);
    }

    public Page search(int pageNo, int pageSize, int modelid, String connector, String catid) {
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        final List fieldList = this.dataFieldManager.list(Integer.valueOf(modelid));
        DataModel model = this.dataModelManager.get(Integer.valueOf(modelid));
        StringBuffer sql = new StringBuffer();
        sql.append("select * from ");
        sql.append(model.getEnglish_name());
        StringBuffer term = new StringBuffer();
        Iterator i$ = fieldList.iterator();

        while(i$.hasNext()) {
            DataField field = (DataField)i$.next();
            String showform = field.getShow_form();
            if(!"image".equals(showform)) {
                String value = request.getParameter(field.getEnglish_name());
                if(!request.getCharacterEncoding().toLowerCase().equals("utf-8") && value != null) {
                    value = StringUtil.toUTF8(value);
                }

                String name = field.getEnglish_name();
                FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getCurrInstance();
                freeMarkerPaser.putData(name, value);
                if(!"radio".equals(showform) && !"select".equals(showform)) {
                    if("dateinput".equals(showform)) {
                        String paramname = field.getEnglish_name();
                        String start = request.getParameter(paramname + "_start");
                        String end = request.getParameter(paramname + "_end");
                        if(!StringUtil.isEmpty(start) || !StringUtil.isEmpty(end)) {
                            if(term.length() > 0) {
                                term.append(connector);
                            }

                            term.append("(");
                            if(!StringUtil.isEmpty(start)) {
                                term.append(name);
                                term.append(">=\'");
                                term.append(start);
                                term.append("\'");
                            }

                            if(!StringUtil.isEmpty(end)) {
                                if(!StringUtil.isEmpty(start)) {
                                    term.append(connector);
                                }

                                term.append(name);
                                term.append("<=\'");
                                term.append(end);
                                term.append("\'");
                            }

                            term.append(")");
                        }
                    } else if(!StringUtil.isEmpty(value)) {
                        if(term.length() > 0) {
                            term.append(connector);
                        }

                        term.append(name);
                        term.append(" like \'%");
                        term.append(value);
                        term.append("%\'");
                    }
                } else if(!StringUtil.isEmpty(value)) {
                    if(term.length() > 0) {
                        term.append(connector);
                    }

                    term.append(name);
                    term.append(" =\'");
                    term.append(value);
                    term.append("\'");
                }
            }
        }

        if(catid != null) {
            if(catid.startsWith("lt")) {
                catid = catid.replaceAll("lt", "<");
            } else if(catid.startsWith("gt")) {
                catid = catid.replaceAll("gt", ">");
            } else {
                catid = "=" + catid;
            }

            if(term.length() > 0) {
                term.append(connector);
            }

            term.append(" cat_id" + catid);
        }

        if(term.length() > 0) {
            sql.append(" where ");
            sql.append(term);
        }

        sql.append(" order by sort desc");
        return this.baseDaoSupport.queryForPage(sql.toString(), pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                HashMap data = new HashMap();

                Object value;
                String name;
                for(Iterator i$ = fieldList.iterator(); i$.hasNext(); data.put(name, value)) {
                    DataField field = (DataField)i$.next();
                    value = null;
                    name = field.getEnglish_name();
                    value = rs.getString(name);
                    IPlugin plugin = DataManager.this.articlePluginBundle.findPlugin(field.getShow_form());
                    if(plugin != null && plugin instanceof IFieldValueShowEvent) {
                        value = ((IFieldValueShowEvent)plugin).onShow(field, value);
                    }
                }

                data.put("id", Integer.valueOf(rs.getInt("id")));
                data.put("cat_id", Integer.valueOf(rs.getInt("cat_id")));
                data.put("add_time", Long.valueOf(rs.getLong("add_time")));
                data.put("lastmodified", Long.valueOf(rs.getLong("lastmodified")));
                data.put("hit", Long.valueOf(rs.getLong("hit")));
                return data;
            }
        }, new Object[0]);
    }

    public Page search(int pageNo, int pageSize, int modelid, String connector, boolean showchild) {
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        final List fieldList = this.dataFieldManager.list(Integer.valueOf(modelid));
        DataModel model = this.dataModelManager.get(Integer.valueOf(modelid));
        StringBuffer sql = new StringBuffer();
        sql.append("select * from ");
        sql.append(model.getEnglish_name());
        int i = 0;
        StringBuffer term = new StringBuffer();
        Iterator site = fieldList.iterator();

        while(site.hasNext()) {
            DataField site_code = (DataField)site.next();
            String site_id = site_code.getShow_form();
            if(!"image".equals(site_id)) {
                String value = request.getParameter(site_code.getEnglish_name());
                if(value != null) {
                    value = StringUtil.toUTF8(value);
                }

                String name = site_code.getEnglish_name();
                if(!"radio".equals(site_id) && !"select".equals(site_id)) {
                    if("dateinput".equals(site_id)) {
                        String paramname = site_code.getEnglish_name();
                        String start = request.getParameter(paramname + "_start");
                        String end = request.getParameter(paramname + "_end");
                        if(!StringUtil.isEmpty(start) || !StringUtil.isEmpty(end)) {
                            term.append("(");
                            if(!StringUtil.isEmpty(start)) {
                                term.append(name);
                                term.append(">=\'");
                                term.append(start);
                                term.append("\'");
                            }

                            if(!StringUtil.isEmpty(end)) {
                                if(!StringUtil.isEmpty(start)) {
                                    term.append(connector);
                                }

                                term.append(name);
                                term.append("<=\'");
                                term.append(end);
                                term.append("\'");
                            }

                            term.append(")");
                        }
                    } else {
                        if(StringUtil.isEmpty(value)) {
                            continue;
                        }

                        if(i != 0) {
                            sql.append(connector);
                        }

                        term.append(name);
                        term.append(" like \'%");
                        term.append(value);
                        term.append("%\'");
                    }
                } else {
                    if(StringUtil.isEmpty(value)) {
                        continue;
                    }

                    if(i != 0) {
                        sql.append(connector);
                    }

                    term.append(name);
                    term.append(" =\'");
                    term.append(value);
                    term.append("\'");
                }

                ++i;
            }
        }

        if(EopContext.getContext().getCurrentSite().getMulti_site().intValue() == 1) {
            MultiSite var20 = EopContext.getContext().getCurrentChildSite();
            Integer var21 = var20.getCode();
            Integer var22 = var20.getSiteid();
            if(i != 0) {
                sql.append(" and ");
            }

            term.append(" ((site_code = " + var21 + ") ");
            term.append(" or (siteidlist like \'%," + var22 + ",%\')");
            if(showchild) {
                term.append(" or (site_code between " + var21 + " and " + StringUtil.getMaxLevelCode(var21.intValue()) + ")");
            }

            term.append(")");
        }

        if(term.length() > 0) {
            sql.append(" where ");
            sql.append(term);
        }

        return this.baseDaoSupport.queryForPage(sql.toString(), pageNo, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                HashMap data = new HashMap();

                Object value;
                String name;
                for(Iterator i$ = fieldList.iterator(); i$.hasNext(); data.put(name, value)) {
                    DataField field = (DataField)i$.next();
                    value = null;
                    name = field.getEnglish_name();
                    value = rs.getString(name);
                    IPlugin plugin = DataManager.this.articlePluginBundle.findPlugin(field.getShow_form());
                    if(plugin != null && plugin instanceof IFieldValueShowEvent) {
                        value = ((IFieldValueShowEvent)plugin).onShow(field, value);
                    }
                }

                data.put("id", Integer.valueOf(rs.getInt("id")));
                data.put("cat_id", Integer.valueOf(rs.getInt("cat_id")));
                data.put("add_time", Long.valueOf(rs.getLong("add_time")));
                data.put("hit", Long.valueOf(rs.getLong("hit")));
                return data;
            }
        }, new Object[0]);
    }

    public Map census() {
        List modelList = this.dataModelManager.list();
        int count = 0;

        String sql;
        for(Iterator catcount = modelList.iterator(); catcount.hasNext(); count += this.baseDaoSupport.queryForInt(sql, new Object[0])) {
            DataModel msgcount = (DataModel)catcount.next();
            String map = msgcount.getEnglish_name();
            sql = "select count(0)  from " + map;
        }

        sql = "select count(0) from data_cat";
        int catcount1 = this.baseDaoSupport.queryForInt(sql, new Object[0]);
        sql = "select count(0)  from " + this.getTableName("guestbook") + " g where parentid=0";
        int msgcount1 = this.daoSupport.queryForInt(sql, new Object[0]);
        HashMap map1 = new HashMap(3);
        map1.put("count", Integer.valueOf(count));
        map1.put("catcount", Integer.valueOf(catcount1));
        map1.put("msgcount", Integer.valueOf(msgcount1));
        return map1;
    }

    public Page listAll(Integer catid, String term, String orders, boolean showchild, int page, int pageSize) {
        DataModel model = this.getModelByCatid(catid);
        DataCat cat = this.dataCatManager.get(catid);
        StringBuffer sql = new StringBuffer("select * from ");
        sql.append(this.getTableName(model.getEnglish_name()));
        sql.append(" where cat_id in (select cat_id from ");
        sql.append(this.getTableName("data_cat"));
        sql.append(" where cat_path like \'");
        sql.append(cat.getCat_path());
        sql.append("%\'");
        sql.append(") ");
        if(!StringUtil.isEmpty(term)) {
            sql.append(term);
        }

        if(EopContext.getContext().getCurrentSite().getMulti_site().intValue() == 1) {
            MultiSite fieldList = EopContext.getContext().getCurrentChildSite();
            Integer datalist = fieldList.getCode();
            Integer site_id = fieldList.getSiteid();
            sql.append(" and ((site_code = " + datalist + ") ");
            sql.append(" or (siteidlist like \'%," + site_id + ",%\')");
            if(showchild) {
                sql.append(" or (site_code between " + datalist + " and " + StringUtil.getMaxLevelCode(datalist.intValue()) + ")");
            }

            sql.append(")");
        }

        if(!StringUtil.isEmpty(orders)) {
            sql.append(" order by " + orders);
        } else {
            sql.append(" order by sort desc, add_time desc");
        }

        final List fieldList1 = this.dataFieldManager.list(model.getModel_id());
        Page datalist1 = this.daoSupport.queryForPage(sql.toString(), page, pageSize, new RowMapper() {
            public Object mapRow(ResultSet rs, int c) throws SQLException {
                HashMap data = new HashMap();

                Object value;
                String name;
                for(Iterator i$ = fieldList1.iterator(); i$.hasNext(); data.put(name, value)) {
                    DataField field = (DataField)i$.next();
                    value = null;
                    name = field.getEnglish_name();
                    value = rs.getString(name);
                    IPlugin plugin = DataManager.this.articlePluginBundle.findPlugin(field.getShow_form());
                    if(plugin != null && plugin instanceof IFieldValueShowEvent) {
                        value = ((IFieldValueShowEvent)plugin).onShow(field, value);
                    }
                }

                data.put("id", Integer.valueOf(rs.getInt("id")));
                data.put("cat_id", Integer.valueOf(rs.getInt("cat_id")));
                data.put("sort", Integer.valueOf(rs.getInt("sort")));
                data.put("add_time", Long.valueOf(rs.getLong("add_time")));
                data.put("lastmodified", Long.valueOf(rs.getLong("lastmodified")));
                data.put("hit", Long.valueOf(rs.getLong("hit")));
                data.put("sys_lock", Integer.valueOf(rs.getInt("sys_lock")));
                data.put("site_code", Integer.valueOf(rs.getInt("site_code")));
                data.put("page_title", rs.getString("page_title"));
                data.put("cat_name", "");
                return data;
            }
        }, new Object[0]);
        this.logger.debug("查询sql[" + sql + "]完成");
        return datalist1;
    }

    public int getNextId(Integer currentId, Integer catid) {
        DataModel model = this.getModelByCatid(catid);
        String sql = "select min(id) from " + model.getEnglish_name() + " where cat_id=? and id>?";
        List list = this.baseDaoSupport.queryForList(sql, new IntegerMapper(), new Object[]{catid, currentId});
        return list != null && !list.isEmpty()?((Integer)list.get(0)).intValue():0;
    }

    public int getPrevId(Integer currentId, Integer catid) {
        DataModel model = this.getModelByCatid(catid);
        String sql = "select max(id) from " + model.getEnglish_name() + " where cat_id=? and id<?";
        List list = this.baseDaoSupport.queryForList(sql, new IntegerMapper(), new Object[]{catid, currentId});
        return list != null && !list.isEmpty()?((Integer)list.get(0)).intValue():0;
    }

    public IDataModelManager getDataModelManager() {
        return this.dataModelManager;
    }

    public void setDataModelManager(IDataModelManager dataModelManager) {
        this.dataModelManager = dataModelManager;
    }

    public IDataFieldManager getDataFieldManager() {
        return this.dataFieldManager;
    }

    public void setDataFieldManager(IDataFieldManager dataFieldManager) {
        this.dataFieldManager = dataFieldManager;
    }

    public ArticlePluginBundle getArticlePluginBundle() {
        return this.articlePluginBundle;
    }

    public void setArticlePluginBundle(ArticlePluginBundle articlePluginBundle) {
        this.articlePluginBundle = articlePluginBundle;
    }

    public IDataCatManager getDataCatManager() {
        return this.dataCatManager;
    }

    public void setDataCatManager(IDataCatManager dataCatManager) {
        this.dataCatManager = dataCatManager;
    }
}
