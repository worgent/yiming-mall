package com.enation.framework.database;

import com.enation.framework.database.impl.IRowMapperColumnFilter;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public abstract interface IDaoSupport<T>
{
  public abstract void execute(String paramString, Object... paramVarArgs);
  
  public abstract int queryForInt(String paramString, Object... paramVarArgs);
  
  public abstract long queryForLong(String paramString, Object... paramVarArgs);
  
  public abstract String queryForString(String paramString);
  
  public abstract T queryForObject(String paramString, Class paramClass, Object... paramVarArgs);
  
  public abstract T queryForObject(String paramString, ParameterizedRowMapper paramParameterizedRowMapper, Object... paramVarArgs);
  
  public abstract Map queryForMap(String paramString, Object... paramVarArgs);
  
  public abstract List<Map> queryForList(String paramString, Object... paramVarArgs);
  
  public abstract List<T> queryForList(String paramString, RowMapper paramRowMapper, Object... paramVarArgs);
  
  public abstract List<T> queryForList(String paramString, IRowMapperColumnFilter paramIRowMapperColumnFilter, Object... paramVarArgs);
  
  public abstract List<T> queryForList(String paramString, Class paramClass, Object... paramVarArgs);
  
  public abstract List<Map> queryForListPage(String paramString, int paramInt1, int paramInt2, Object... paramVarArgs);
  
  public abstract List<T> queryForList(String paramString, int paramInt1, int paramInt2, RowMapper paramRowMapper);
  
  public abstract List<T> queryForList(String paramString, int paramInt1, int paramInt2, IRowMapperColumnFilter paramIRowMapperColumnFilter);
  
  public abstract Page queryForPage(String paramString, int paramInt1, int paramInt2, Object... paramVarArgs);
  
  public abstract Page queryForPage(String paramString, int paramInt1, int paramInt2, RowMapper paramRowMapper, Object... paramVarArgs);
  
  public abstract Page queryForPage(String paramString, int paramInt1, int paramInt2, IRowMapperColumnFilter paramIRowMapperColumnFilter, Object... paramVarArgs);
  
  public abstract Page queryForPage(String paramString, int paramInt1, int paramInt2, Class<T> paramClass, Object... paramVarArgs);
  
  public abstract void update(String paramString, Map paramMap1, Map paramMap2);
  
  public abstract void update(String paramString1, Map paramMap, String paramString2);
  
  public abstract void update(String paramString, T paramT, Map paramMap);
  
  public abstract void update(String paramString1, T paramT, String paramString2);
  
  public abstract void insert(String paramString, Map paramMap);
  
  public abstract void insert(String paramString, Object paramObject);
  
  public abstract int getLastId(String paramString);
  
  public abstract String buildPageSql(String paramString, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\database\IDaoSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */