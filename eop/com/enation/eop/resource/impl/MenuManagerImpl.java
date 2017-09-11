 package com.enation.eop.resource.impl;

 import com.enation.app.base.core.model.AuthAction;
 import com.enation.app.base.core.service.auth.IAuthActionManager;
 import com.enation.app.base.core.service.auth.IPermissionManager;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.eop.resource.model.Menu;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.IntegerMapper;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;

 public class MenuManagerImpl extends BaseSupport implements com.enation.eop.resource.IMenuManager
 {
   private IPermissionManager permissionManager;
   private String showall;
   private IAuthActionManager authActionManager;

   @Transactional(propagation=Propagation.REQUIRED)
   public void clean()
   {
     this.baseDaoSupport.execute("truncate table menu", new Object[0]);
   }


   public List<Menu> getMenuList() { return this.baseDaoSupport.queryForList("select * from menu where deleteflag = '0' order by sorder asc", Menu.class, new Object[0]); }

   @Transactional(propagation=Propagation.REQUIRED)
   public Integer add(Menu menu) {
     if (menu.getTitle() == null)
       throw new IllegalArgumentException("title argument is null");
     if (menu.getPid() == null)
       throw new IllegalArgumentException("pid argument is null");
     if (menu.getUrl() == null)
       throw new IllegalArgumentException("url argument is null");
     if (menu.getSorder() == null)
       throw new IllegalArgumentException("sorder argument is null");
     menu.setDeleteflag(Integer.valueOf(0));
     this.baseDaoSupport.insert("menu", menu);
     return Integer.valueOf(this.baseDaoSupport.getLastId("menu"));
   }

   public List<Menu> getMenuTree(Integer menuid) {
     if (menuid == null)
       throw new IllegalArgumentException("menuid argument is null");
     List<Menu> menuList = getMenuList();
     List<Menu> topMenuList = new ArrayList();
     for (Menu menu : menuList) {
       if (menu.getPid().compareTo(menuid) == 0) {
         List<Menu> children = getChildren(menuList, menu.getId());
         menu.setChildren(children);
         topMenuList.add(menu);
       }
     }
     return topMenuList;
   }









   private List<Menu> getChildren(List<Menu> menuList, Integer parentid)
   {
     List<Menu> children = new ArrayList();
     for (Menu menu : menuList) {
       if (menu.getPid().compareTo(parentid) == 0) {
         menu.setChildren(getChildren(menuList, menu.getId()));
         children.add(menu);
       }
     }
     return children;
   }

   public Menu get(Integer id) {
     if (id == null)
       throw new IllegalArgumentException("ids argument is null");
     String sql = "select * from menu where id=?";
     return (Menu)this.baseDaoSupport.queryForObject(sql, Menu.class, new Object[] { id });
   }

   public Menu get(String title) {
     String sql = "select * from menu where title=?";
     List<Menu> menuList = this.baseDaoSupport.queryForList(sql, Menu.class, new Object[] { title });

     if (menuList.isEmpty())
       return null;
     return (Menu)menuList.get(0);
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void edit(Menu menu) { if (menu.getId() == null)
       throw new IllegalArgumentException("id argument is null");
     if (menu.getTitle() == null)
       throw new IllegalArgumentException("title argument is null");
     if (menu.getPid() == null)
       throw new IllegalArgumentException("pid argument is null");
     if (menu.getUrl() == null)
       throw new IllegalArgumentException("url argument is null");
     if (menu.getSorder() == null)
       throw new IllegalArgumentException("sorder argument is null");
     menu.setDeleteflag(Integer.valueOf(0));
     this.baseDaoSupport.update("menu", menu, "id=" + menu.getId());
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void updateSort(Integer[] ids, Integer[] sorts) {
     if (ids == null)
       throw new IllegalArgumentException("ids argument is null");
     if (sorts == null)
       throw new IllegalArgumentException("sorts argument is null");
     if (sorts.length != ids.length)
       throw new IllegalArgumentException("ids's length and sorts's length not same");
     for (int i = 0; i < ids.length; i++) {
       String sql = "update menu set sorder=? where id=?";
       this.baseDaoSupport.execute(sql, new Object[] { sorts[i], ids[i] });
     }
   }

   public void delete(Integer id) throws RuntimeException {
     if (id == null)
       throw new IllegalArgumentException("ids argument is null");
     String sql = "select count(0) from menu where pid=?";
     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { id });
     if (count > 0)
       throw new RuntimeException("菜单" + id + "存在子类别,不能直接删除，请先删除其子类别。");
     sql = "delete from menu where id=?";
     this.baseDaoSupport.execute(sql, new Object[] { id });
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void delete(String title)
   {
     String sql = "delete from menu where title=?";
     this.baseDaoSupport.execute(sql, new Object[] { title });
   }



   public void move(int menuid, int targetid, String type)
   {
     Menu menu = get(Integer.valueOf(menuid));
     Menu target = get(Integer.valueOf(targetid));

     int parentid = menu.getPid().intValue();
     int targetpid = target.getPid().intValue();


     if ("inner".equals(type))
     {
       this.baseDaoSupport.execute("update menu set pid=? where id=?", new Object[] { Integer.valueOf(targetid), menu.getId() });
       List<Integer> sorderList = this.baseDaoSupport.queryForList("select max(sorder) sorder from menu where pid=?", new IntegerMapper(), new Object[] { Integer.valueOf(targetid) });
       int sorder = 1;
       if (!sorderList.isEmpty()) {
         sorder = ((Integer)sorderList.get(0)).intValue() + 1;
       }

       this.baseDaoSupport.execute("update menu set sorder=? where id=?", new Object[] { Integer.valueOf(sorder), Integer.valueOf(menuid) });
     }



     if (("prev".equals(type)) || ("next".equals(type)))
     {
       if (parentid != targetpid) {
         this.baseDaoSupport.execute("update menu set pid=? where id=?", new Object[] { Integer.valueOf(targetpid), menu.getId() });
       }

       if ("prev".equals(type))
       {

         String sql = "update menu set sorder=sorder-1 where pid=? and sorder<=? and id!=?";
         this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(targetpid), target.getSorder(), target.getId() });


         sql = "update menu set sorder=? where id=?";
         this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(target.getSorder().intValue() - 1), menu.getId() });
       }


       if ("next".equals(type))
       {

         String sql = "update menu set sorder=sorder+1 where pid=? and sorder>=? and id!=?";
         this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(targetpid), target.getSorder(), target.getId() });


         sql = "update menu set sorder=? where id=?";
         this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(target.getSorder().intValue() + 1), menu.getId() });
       }
     }
   }






   public IPermissionManager getPermissionManager()
   {
     return this.permissionManager;
   }

   public void setPermissionManager(IPermissionManager permissionManager) {
     this.permissionManager = permissionManager;
   }

   public String getShowall() {
     return this.showall;
   }

   public void setShowall(String showall) {
     this.showall = showall;
   }

   public IAuthActionManager getAuthActionManager() {
     return this.authActionManager;
   }

   public void setAuthActionManager(IAuthActionManager authActionManager) {
     this.authActionManager = authActionManager;
   }

   public List<Menu> newMenutree(Integer menuid, AdminUser user)
   {
     if (menuid == null) {
       throw new IllegalArgumentException("menuid argument is null");
     }
     List<Menu> menuList = getMenuList();

     menuList = menuListByUser(menuList, user);

     List<Menu> topMenuList = new ArrayList();
     for (Menu menu : menuList) {
       if (menu.getPid().compareTo(menuid) == 0) {
         List<Menu> children = getChildren(menuList, menu.getId());
         menu.setChildren(children);
         topMenuList.add(menu);
       }
     }
     return topMenuList;
   }

   private List<Menu> menuListByUser(List<Menu> menuList, AdminUser user) {
     List<Menu> topMenuList = new ArrayList();
    Menu menu;
     for (Iterator i$ = menuList.iterator(); i$.hasNext();) { menu = (Menu)i$.next();
       List<AuthAction> authList = this.permissionManager.getUesrAct(user.getUserid().intValue(), "menu");
       for (AuthAction authAction : authList) {
         String[] arth = authAction.getObjvalue().split(",");
         for (int i = 0; i < arth.length; i++) {
           if ((Integer.parseInt(arth[i]) == menu.getId().intValue()) && (choosemenu(topMenuList, menu)))
             topMenuList.add(menu);
         }
       }
     }

     return topMenuList;
   }

   private boolean choosemenu(List<Menu> newmenu, Menu menu) { boolean choose = true;
     for (Menu cmenu : newmenu) {
       int menuId = menu.getId().intValue();
       int cmenuId = cmenu.getId().intValue();
       if (menuId == cmenuId) {
         choose = false;
       }
     }
     return choose;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\impl\MenuManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */