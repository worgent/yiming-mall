package com.enation.eop.processor.core;

public abstract interface HttpHeaderConstants
{
  public static final String CONTEXT_TYPE_XML = "text/xml";
  public static final String CONTEXT_TYPE_JSON = "text/json";
  public static final String CONTEXT_TYPE_HTML = "text/html";
  public static final String CONTEXT_TYPE_JAVASCRIPT = "application/x-javascript";
  public static final String CONTEXT_TYPE_FLASH = "application/x-shockwave-flash";
  public static final String CONTEXT_TYPE_CSS = "text/css";
  public static final String CONTEXT_TYPE_JPG = "image/jpeg";
  public static final String CONTEXT_TYPE_GIF = "image/gif";
  public static final String CONTEXT_TYPE_PNG = "image/png";
  public static final int status_200 = 200;
  public static final int status_304 = 304;
  public static final int status_404 = 404;
  public static final int status_500 = 500;
  public static final int status_redirect = -1;
  public static final int status_do_original = -2;
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\core\HttpHeaderConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */