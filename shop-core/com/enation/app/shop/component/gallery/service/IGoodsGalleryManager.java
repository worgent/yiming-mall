package com.enation.app.shop.component.gallery.service;

import com.enation.app.shop.component.gallery.model.GoodsGallery;
import java.io.File;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public abstract interface IGoodsGalleryManager
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void add(GoodsGallery paramGoodsGallery);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void delete(Integer[] paramArrayOfInteger);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void delete(int paramInt);
  
  public abstract List<GoodsGallery> list(int paramInt);
  
  public abstract String upload(File paramFile, String paramString);
  
  public abstract void createThumb(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract void delete(String paramString);
  
  public abstract void recreate(int paramInt1, int paramInt2);
  
  public abstract int getTotal();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\gallery\service\IGoodsGalleryManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */