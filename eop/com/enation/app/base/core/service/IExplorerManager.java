package com.enation.app.base.core.service;

import com.enation.app.base.core.model.FileNode;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

public abstract interface IExplorerManager
{
  public abstract List<FileNode> list(FileFilter paramFileFilter);
  
  public abstract List<FileNode> listex(FileFilter paramFileFilter);
  
  public abstract FileNode get(String paramString);
  
  public abstract void add(FileNode paramFileNode)
    throws IOException;
  
  public abstract void edit(FileNode paramFileNode);
  
  public abstract void rename(String paramString1, String paramString2);
  
  public abstract void move(String paramString1, String paramString2, String paramString3);
  
  public abstract void delete(String paramString);
  
  public abstract void upload(File paramFile, String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\IExplorerManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */