package io.apache.org.template;

import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.util.StringUtil;
import java.io.File;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;

public class TplUtil
{
  private static int isload = 0;
  private static String[] licenses = null;
  private static String publicKey = "30819f300d06092a864886f70d010101050003818d003081890281810088f1f1b67fe7d6f27af68fa5b2644321589753df45d6a7c328e5e3088fda97dbe9acb5bc65b68a60d42a242609332eeaaafbab4ad57912c481d612e0714c9cb0c87c3786fc38b3d001ee6a47375948823dd5c450c5fc09245765d29396e728fb4537c60d10d6261025625620d675321aed79aacd03c89c0efb22e9455c3ddcc90203010001";
  
  private static boolean verifySign(String hexPublicKey, String signedStr, String str)
  {
    try
    {
      X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(HexUtils.hexStrToBytes(hexPublicKey));
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
      byte[] signed = HexUtils.hexStrToBytes(signedStr);
      
      Signature signature = Signature.getInstance("MD5withRSA");
      signature.initVerify(publicKey);
      signature.update(str.getBytes());
      return signature.verify(signed);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public static void load()
  {
    if (isload == 1) {
      return;
    }
    isload = 1;
    try
    {
      File licenseDir = new File(StringUtil.getRootPath() + "/" + EncryptionUtil.authCode("CVpSUgxAUw==", "DECODE"));
      if (!licenseDir.exists()) {
        return;
      }
      Collection<File> licenseFileList = FileUtils.listFiles(licenseDir, new String[] { EncryptionUtil.authCode("CVpS", "DECODE") }, false);
      if ((licenseFileList == null) || (licenseFileList.size() <= 0)) {
        return;
      }
      licenses = new String[licenseFileList.size()];
      int i = 0;
      for (File licenseFile : licenseFileList)
      {
        licenses[i] = FileUtils.readFileToString(licenseFile, "UTF-8");
        i++;
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  public static void load(String path)
  {
    try
    {
      File licenseDir = new File(path);
      Collection<File> licenseFileList = FileUtils.listFiles(licenseDir, new String[] { "lic" }, false);
      if ((licenseFileList == null) || (licenseFileList.size() <= 0)) {
        return;
      }
      licenses = new String[licenseFileList.size()];
      int i = 0;
      for (File licenseFile : licenseFileList)
      {
        licenses[i] = FileUtils.readFileToString(licenseFile, "UTF-8");
        i++;
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  public static boolean verify(String domain)
  {
    if( true )
      return true;
    try
    {
      if ((licenses == null) || (licenses.length == 0)) {
        return false;
      }
      String[] arrayOfString;
      int j = (arrayOfString = licenses).length;
      for (int i = 0; i < j; i++)
      {
        String license = arrayOfString[i];
        try
        {
          if (verifySign(publicKey, license, domain)) {
            return true;
          }
        }
        catch (Exception localException) {}
      }
    }
    catch (Exception localException1) {}
    return false;
  }
  
  private static int TPP = 0;
  
  public static String process()
  {
    HttpServletRequest req = ThreadContextHolder.getHttpRequest();
    if (req != null)
    {
      String domain = req.getServerName();
      if ((EncryptionUtil.authCode("VAEGGVIdBhxX", "DECODE").equals(domain)) || (EncryptionUtil.authCode("CVxSVg5bWUES", "DECODE").equals(domain))) {
        return "";
      }
      load();
      if (TPP != 2) {
        if (!verify(domain)) {
          TPP = 1;
        } else {
          TPP = 2;
        }
      }
      if (TPP == 1)
      {
        String str = ",x64,x6f,x63,x75,x6d,x65,x6e,x74,x2e,x77,x72,x69,x74,x65,x28,x27,u672c,u7ad9,u70b9,u57fa,u4e8e,u3010,u6613,u65cf,u667a,u6c47,u7f51,u7edc,u5546,u5e97,u7cfb,u7edf,x56,x34,x2e,x30,u3011,x28,u7b80,u79f0,x4a,x61,x76,x61,x73,x68,x6f,x70,x29,u5f00,u53d1,uff0c,u4f46,u672c,u7ad9,u70b9,u672a,u5f97,u5230,u5b98,u65b9,u6388,u6743,uff0c,u4e3a,u975e,u6cd5,u7ad9,u70b9,u3002,x3c,x62,x72,x3e,x4a,x61,x76,x61,x73,x68,x6f,x70,u7684,u5b98,u65b9,u7f51,u7ad9,u4e3a,uff1a,x3c,x61,x20,x68,x72,x65,x66,x3d,x22,x68,x74,x74,x70,x3a,x2f,x2f,x77,x77,x77,x2e,x6a,x61,x76,x61,x6d,x61,x6c,x6c,x2e,x63,x6f,x6d,x2e,x63,x6e,x22,x20,x74,x61,x72,x67,x65,x74,x3d,x22,x5f,x62,x6c,x61,x6e,x6b,x22,x20,x3e,x77,x77,x77,x2e,x6a,x61,x76,x61,x6d,x61,x6c,x6c,x2e,x63,x6f,x6d,x2e,x63,x6e,x3c,x2f,x61,x3e,x3c,x62,x72,x3e,u3010,u6613,u65cf,u667a,u6c47,u7f51,u7edc,u5546,u5e97,u7cfb,u7edf,u3011,u8457,u4f5c,u6743,u5df2,u5728,u4e2d,u534e,u4eba,u6c11,u5171,u548c,u56fd,u56fd,u5bb6,u7248,u6743,u5c40,u6ce8,u518c,u3002,x3c,x62,x72,x3e,u672a,u7ecf,u6613,u65cf,u667a,u6c47,uff08,u5317,u4eac,uff09,u79d1,u6280,u6709,u9650,u516c,u53f8,u4e66,u9762,u6388,u6743,uff0c,x3c,x62,x72,x3e,u4efb,u4f55,u7ec4,u7ec7,u6216,u4e2a,u4eba,u4e0d,u5f97,u4f7f,u7528,uff0c,x3c,x62,x72,x3e,u8fdd,u8005,u672c,u516c,u53f8,u5c06,u4f9d,u6cd5,u8ffd,u7a76,u8d23,u4efb,u3002,x3c,x62,x72,x3e,x27,x29";
        str = str.replaceAll(",", "\\\\");
        return "<script>eval(\"" + str + "\");</script>";
      }
    }
    return "";
  }
}
