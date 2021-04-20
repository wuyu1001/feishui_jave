package xj.util;

import sun.misc.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;
public class RSAUtils {
//	 public static String Character = "utf-8"; //这里统一字符集

    public static String MyreplaceBlank(String str) {// 回车换行替换函数
        String dest = "";
        if (str != null) {
            // Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Pattern p = Pattern.compile("\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest.trim();
    }

    public static String aes_encrypt(String data, String key) {// aes加密
        try {
            key = key + "!@2b3c4d5e6f7890"; // 补码
            key = key.substring(0, 16);
            String iv = key;
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes("gbk"); // 2016-08-21
            //加了aescharacter的代码
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext); // 加密后的组
            String aesstr, newaes;
            aesstr = new sun.misc.BASE64Encoder().encode(encrypted);
            newaes = MyreplaceBlank(aesstr); // 去回车换行符
            return newaes;
        } catch (Exception e) {
            // 记系统日志
            // e.printStackTrace();
            return null;
        }
    }

    public static  String aes_desEncrypt(String data, String key) {//aes解密
        try {
            String sss = new String(data.getBytes("gbk")); //统一字符集
            key = key + "!@2b3c4d5e6f7890"; //补码
            key = key.substring(0,16);
            String iv = key;
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sss) ;  // new BASE64Decoder().decodeBuffer(sss);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1); // 解密出来的数组
            String newaes = new String(original,"gbk");
            newaes = MyreplaceBlank(newaes); // 去回车换行符
            return newaes;
        } catch (Exception e) {// 记系统日志
            // e.printStackTrace();
            return null;
        }
    }
}