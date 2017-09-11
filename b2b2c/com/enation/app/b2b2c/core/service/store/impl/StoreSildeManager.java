 package com.enation.app.b2b2c.core.service.store.impl;

 import com.enation.app.b2b2c.core.model.store.StoreSilde;
 import com.enation.app.b2b2c.core.service.store.IStoreSildeManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;



 @Component
 public class StoreSildeManager
   extends BaseSupport
   implements IStoreSildeManager
 {
   public List<StoreSilde> list(Integer store_id)
   {
     String sql = "select * from es_store_silde where store_id=?";
     List<StoreSilde> list = this.daoSupport.queryForList(sql, StoreSilde.class, new Object[] { store_id });


     return list;
   }



   private void editImg(List<StoreSilde> list)
   {
     for (StoreSilde storeSilde : list) {
       storeSilde.setSildeImg(UploadUtil.replacePath(storeSilde.getImg()));
     }
   }




   @Transactional(propagation=Propagation.REQUIRED)
   public void edit(Integer[] silde_id, String[] fsImg, String[] silde_url)
   {
     for (int i = 0; i < silde_id.length; i++) {
       StoreSilde storeSilde = new StoreSilde();
       storeSilde.setImg(fsImg[i]);
       storeSilde.setSilde_id(silde_id[i]);
       storeSilde.setSilde_url(silde_url[i]);
       editSilde(storeSilde);
     }
   }



   private void editSilde(StoreSilde storeSilde)
   {
     this.daoSupport.update("es_store_silde", storeSilde, "silde_id=" + storeSilde.getSilde_id());
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\store\impl\StoreSildeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */