package xj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xj.util.CwParamesAPI;
import xj.util.DateUtil;
import xj.util.MD5Util;
import xj.util.RSAUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class controller1 {


    @Value("${feishui_url}")
    String feishui_url;

    @RequestMapping(value = "/upload_stuinfo_2_feishui", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String uploadstuinfo2feishui(HttpServletRequest request) throws Exception {
        JSONObject jsonParam = this.getJSONParam(request);
        String arrinfo = jsonParam.getString("arrinfo");
        String url = feishui_url + "/api/creditSchool/HnfsRecJc";
        Map<String, String> result = HnfsRecjc(url, arrinfo, "01");
        return JSONObject.toJSONString(result);
    }

    public static Map<String, String> HnfsRecjc(String url, String arrinfo, String tlb) throws Exception {
        char ch = (char) 4;
        String source = "001";//平台来源
        String dwbm = CwParamesAPI.czqh + ch + CwParamesAPI.dwbm + ch + CwParamesAPI.jylx + ch;//单位信息
        String Userinfo = "2018001" + ch + "张三" + ch + "admin00001" + ch + "0000000" + ch + "10.106.128.19" + ch;//用户信息
        String Apptoken = CwParamesAPI.Apptoken;
        DateUtil dd = new DateUtil();
        String Timeticks = dd.gettimestamp() + new Random().nextInt(1000);
        dwbm = RSAUtils.aes_encrypt(dwbm, CwParamesAPI.pub_keys);//单位信息
        Userinfo = RSAUtils.aes_encrypt(Userinfo, CwParamesAPI.keys);
        arrinfo = RSAUtils.aes_encrypt(arrinfo, CwParamesAPI.keys);
        Apptoken = RSAUtils.aes_encrypt(Apptoken, CwParamesAPI.pub_keys);
        Timeticks = RSAUtils.aes_encrypt(Timeticks, CwParamesAPI.keys);
        dwbm = URLEncoder.encode(dwbm, "utf-8");
        Userinfo = URLEncoder.encode(Userinfo, "utf-8");
        arrinfo = URLEncoder.encode(arrinfo, "utf-8");
        Apptoken = URLEncoder.encode(Apptoken, "utf-8");
        Timeticks = URLEncoder.encode(Timeticks, "utf-8");
        String checkcode = MD5Util.md5Encode(dwbm + ch + Userinfo + ch + arrinfo + ch + Apptoken + ch + Timeticks + ch + tlb + ch);

        Map<String, Object> data = new HashMap<>(8);
        data.put("source", source);
        data.put("dwbm", dwbm);
        HttpRequest httpRequest = new HttpRequest(url, "POST");
        httpRequest.contentType("application/json", "UTF-8");
        Map<String, String> aa = new HashMap<>();
        aa.put("source", source);
        aa.put("dwbm", dwbm);
        aa.put("Userinfo", Userinfo);
        aa.put("Arrinfo", arrinfo);
        aa.put("Apptoken", Apptoken);
        aa.put("Timetics", Timeticks);
        aa.put("Tlb", tlb);
        aa.put("Checkcode", checkcode);
        String jsonString = JSON.toJSONString(aa);
//        System.out.println(jsonString);
        httpRequest.send(JSONObject.toJSONString(aa));
        String body = httpRequest.body();
        JSONObject jsonObject = JSONObject.parseObject(body);
//        String str = URLDecoder.decode(jsonObject.getString("appmsg"),"UTF-8");
        String msg = URLDecoder.decode(jsonObject.getString("appmsg"), "utf-8");
        msg = RSAUtils.aes_desEncrypt(msg, CwParamesAPI.pub_keys);
        String[] str = msg.split(String.valueOf(ch));
        String s0 = RSAUtils.aes_desEncrypt(str[0], CwParamesAPI.pub_keys);
        String s3 = RSAUtils.aes_desEncrypt(str[3], CwParamesAPI.pub_keys);
//        System.out.println(s0);
//        System.out.println(s3);

        Map<String, String> result = new HashMap<>();
        result.put("code", s0);
        result.put("msg", s3);

        return result;
    }

    public JSONObject getJSONParam(HttpServletRequest request) {
        JSONObject jsonParam = null;
        try {
            // 获取输入流
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

            // 数据写入Stringbuilder
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            jsonParam = JSONObject.parseObject(sb.toString());
//            System.out.println(jsonParam.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonParam;
    }
}