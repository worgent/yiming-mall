package freemarker.template;

import java.io.File;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Collection;
import org.apache.commons.io.FileUtils;

public class TemplateUtil
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
      File licenseDir = new File(getRootPath() + "/" + EncryptionUtil.authCode("CVpSUgxAUw==", "DECODE"));
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
  
  public static String getRootPath()
  {
    String filePath = TemplateUtil.class.getResource("").toString();
    
    int index = filePath.indexOf("WEB-INF");
    if (index == -1) {
      index = filePath.indexOf("build");
    }
    if (index == -1) {
      index = filePath.indexOf("bin");
    }
    filePath = filePath.substring(0, index);
    if (filePath.startsWith("jar")) {
      filePath = filePath.substring(10);
    } else if (filePath.startsWith("file")) {
      filePath = filePath.substring(6);
    }
    if (System.getProperty("os.name").toLowerCase().indexOf("window") < 0) {
      filePath = "/" + filePath;
    }
    if (filePath.endsWith("/")) {
      filePath = filePath.substring(0, filePath.length() - 1);
    }
    return filePath;
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
}
