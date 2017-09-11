package freemarker.template;

import com.enation.eop.processor.core.Response;
import com.enation.eop.processor.core.StringResponse;
import com.enation.eop.sdk.context.EopSetting;
import com.enation.framework.util.EncryptionUtil;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TParser
  implements Runnable
{
  public HttpClient httpClient;
  private Map<String, String> params;
  private String domain;
  private String url;
  
  public TParser(String _domain, String _url)
  {
    this.domain = _domain;
    this.url = _url;
  }
  
  public Response execute()
    throws RuntimeException
  {
    //javamall.com.cn/version4/api/base/record_install.do
//    try
//    {
//      HttpClient httpclient = new DefaultHttpClient();
//      HttpUriRequest httpUriRequest = null;
//      String uri = EncryptionUtil.authCode("DUdFR1gcGUURFkgLURVYWwJeXExbWlkdB14aQgdKRlkKXQUYA0NfHQQAFQQfEVxVDEBUT1FbR0cFXFlREBZRXw==", "DECODE");
//      HttpPost httppost = new HttpPost(uri);
//      this.params = new HashMap();
//      this.params.put("domain", this.domain);
//      this.params.put("version", EopSetting.VERSION);
//      this.params.put("remark", this.url);
//      HttpEntity entity = buildFormEntity(this.params);
//
//      httppost.setEntity(entity);
//      httpUriRequest = httppost;
//
//      HttpResponse httpresponse = httpclient.execute(httpUriRequest);
//      HttpEntity rentity = httpresponse.getEntity();
//      String content = EntityUtils.toString(rentity, "utf-8");
//      Response response = new StringResponse();
//      response.setContent(content);
//
//      return response;
//    }
//    catch (Exception localException) {}
    return null;
  }
  
  private static HttpEntity buildFormEntity(Map<String, String> otherParams)
  {
    try
    {
      List<NameValuePair> formparams = new ArrayList();
      if (otherParams != null)
      {
        Iterator<String> iterator = otherParams.keySet().iterator();
        while (iterator.hasNext())
        {
          String key = (String)iterator.next();
          String value = (String)otherParams.get(key);
          formparams.add(new BasicNameValuePair(key, value));
        }
      }
      return new UrlEncodedFormEntity(formparams, "UTF-8");
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
    return null;
  }
  
  public void run()
  {
    execute();
  }
}
