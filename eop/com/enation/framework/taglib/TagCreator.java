 package com.enation.framework.taglib;

 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateMethodModel;
 import freemarker.template.TemplateModelException;
 import java.util.List;











 public class TagCreator
   implements TemplateMethodModel
 {
   public Object exec(List args)
     throws TemplateModelException
   {
     String beanid = (String)args.get(0);
     if (StringUtil.isEmpty(beanid)) {
       throw new TemplateModelException("标签beanid参数不能为空");
     }
     return SpringContextHolder.getBean(beanid);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\taglib\TagCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */