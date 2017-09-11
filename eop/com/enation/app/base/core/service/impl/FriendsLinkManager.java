 package com.enation.app.base.core.service.impl;

 import com.enation.app.base.core.model.FriendsLink;
 import com.enation.app.base.core.model.FriendsLinkMapper;
 import com.enation.app.base.core.service.IFriendsLinkManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;







 public class FriendsLinkManager
   extends BaseSupport<FriendsLink>
   implements IFriendsLinkManager
 {
   public void add(FriendsLink friendsLink)
   {
     this.baseDaoSupport.insert("friends_link", friendsLink);
   }


   public void delete(String id)
   {
     this.baseDaoSupport.execute("delete from friends_link where link_id in (" + id + ")", new Object[0]);
   }



   public List listLink()
   {
     return this.baseDaoSupport.queryForList("select * from friends_link order by sort", new FriendsLinkMapper(), new Object[0]);
   }

   public void update(FriendsLink friendsLink)
   {
     this.baseDaoSupport.update("friends_link", friendsLink, "link_id = " + friendsLink.getLink_id());
   }


   public FriendsLink get(int link_id)
   {
     FriendsLink friendsLink = (FriendsLink)this.baseDaoSupport.queryForObject("select * from friends_link where link_id = ?", FriendsLink.class, new Object[] { Integer.valueOf(link_id) });
     String logo = friendsLink.getLogo();
     if (logo != null) {
       logo = UploadUtil.replacePath(logo);
       friendsLink.setLogo(logo);
     }
     return friendsLink;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\impl\FriendsLinkManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */