package cn.ipaynow.identity.sdk;

import cn.ipaynow.util.EncryDecryUtils;
import cn.ipaynow.util.FormDateReportConvertor;
import cn.ipaynow.util.PropertiesLoader;
import cn.ipaynow.util.RandomUtil;
import cn.ipaynow.util.httpkit.HttpsTookit;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ipaynow1130 on 2017/11/13.
 */
public class IdentitySdk {


    private final String URL_IDENTITY =  "https://dby.ipaynow.cn/identify";
    private final String URL_IDENTITY_PROD =  "https://s.ipaynow.cn/auth";


    private HttpsTookit httpsTookit;

    private boolean isDev;
    public IdentitySdk(boolean isDev){
        this.isDev = isDev;
        try {
            httpsTookit = new HttpsTookit(null,null);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
    }
    public IdentitySdk(){
        this(false);
    }

    private Map query(Map<String,String> requestMap,String funcode){

        try{

            //1. 参数map
//            Map<String,String> requestMap = new HashMap<String, String>();
//            requestMap.put("mhtOrderNo", "Num12011525");

            //2. map 2 kv 并排序
            String toRSAStr = FormDateReportConvertor.postFormLinkReport(requestMap);
            //3. message=base64(appId=xxx)| base64(3DES(报文原文))|base64(MD5(报文原文+&+ md5Key))
            String message1 = "appId="+ PropertiesLoader.getAppId()+"";
            message1 = EncryDecryUtils.base64Encrypt(message1);//base64(appId=xxx)
            String message2 = toRSAStr;
            message2 = EncryDecryUtils.encryptFromDESBase64(PropertiesLoader.get3Des(),message2);// base64(3DES(报文原文)
            String message3 = EncryDecryUtils.base64Encrypt(EncryDecryUtils.md5(toRSAStr.toString().trim() +"&"+ PropertiesLoader.getMd5()));//base64(MD5(报文原文+&+ md5Key))
            String message = message1+"|"+message2+"|"+message3+"";
            //4. urlencoder
            message = URLEncoder.encode(message,"UTF-8");
            //5. post funcode=xxx&message=xxx
            String res = httpsTookit.doPost(isDev?URL_IDENTITY:URL_IDENTITY_PROD,"funcode="+funcode+"&message="+message,null,null,"UTF-8");

//            System.out.println("SMS返回报文"+res);

            //6.基本验证
            if(res.split("\\|").length==2){
                String return2 = res.split("\\|")[1];
                //错误原因
                System.out.println(EncryDecryUtils.base64Decrypt(return2));
                return null;
            }
            //7. 返回解析
            String return1 = res.split("\\|")[0];
            String return2 = res.split("\\|")[1];
            String return3 = res.split("\\|")[2];

            String originalMsg  =  EncryDecryUtils.decryptFromBase64DES(PropertiesLoader.get3Des(), return2);
//            System.out.println("返回的报文原文是:"+originalMsg);

            //8. 返回验签
            String originalSign  =  EncryDecryUtils.base64Decrypt(return3);
//           System.out.println("返回的报文原始签名是:"+originalSign);
            String mySign = EncryDecryUtils.md5(originalMsg.trim()+"&"+PropertiesLoader.getMd5());//.trim() 很重要

            //9. 结果返回
            if(originalSign.equals(mySign)){
                return form2Map(originalMsg);
            }else{
                System.out.println("验证签名不正确");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }




    private  Map form2Map(String s) {
        Map result = new HashMap();
        for(String tmp : s.split("&")){
            result.put(tmp.split("=")[0],tmp.split("=")[1]);
        }
        return result;
    }









    /**
     * 身份验证
     * @param cardName  姓名
     * @param idcard    身份证
     * @param mhtOrderNo    商户订单号(可空,为空时自动生成)
     * @return
     */
    public Map IdentityAuth(String cardName,String idcard,String mhtOrderNo){
        Map<String,String> requestMap = new HashMap<String, String>();
        requestMap.put("cardName",cardName);
        requestMap.put("idcard",idcard);
        if(StringUtils.isEmpty(mhtOrderNo)){
            requestMap.put("mhtOrderNo", RandomUtil.getRandomStr(20));
        }else{
            requestMap.put("mhtOrderNo",mhtOrderNo);
        }
        return query(requestMap,"ID01");
    }
    /**
     * 身份验证-订单查询
     * @param mhtOrderNo    商户订单号
     * @return
     */
    public Map IdentityAuthQuery(String mhtOrderNo){
        Map<String,String> requestMap = new HashMap<String, String>();
        requestMap.put("mhtOrderNo", mhtOrderNo);
        return query(requestMap,"ID01_Query");
    }









    /**
     *  卡信息认证
     * @param idCardName   姓名
     * @param idCard    身份证
     * @param bankCardNum   银行账户
     * @param mhtOrderNo    商户订单号(可空,为空时自动生成)
     * @return
     */
    public Map CardAuth(String idCardName,String idCard,String bankCardNum,String mhtOrderNo){
        Map<String,String> requestMap = new HashMap<String, String>();
        requestMap.put("idCardName",idCardName);
        requestMap.put("idCard",idCard);
        requestMap.put("bankCardNum",bankCardNum);
        if(StringUtils.isEmpty(mhtOrderNo)){
            requestMap.put("mhtOrderNo", RandomUtil.getRandomStr(20));
        }else{
            requestMap.put("mhtOrderNo",mhtOrderNo);
        }
        return query(requestMap,"ID02");
    }

    /**
     * 卡信息认证- 订单查询
     * @param mhtOrderNo
     * @return
     */
    public Map CardAuthQuery(String mhtOrderNo){
        Map<String,String> requestMap = new HashMap<String, String>();
        requestMap.put("mhtOrderNo",mhtOrderNo);
        return query(requestMap,"ID02_Query");
    }





















    /**
     * 手机号认证
     * @param idCardName    认证姓名
     * @param idCard    身份证号码
     * @param mobile    手机号
     * @param mhtOrderNo    商户订单号
     * @return
     */
    public Map MobileNoAuth(String idCardName,String idCard,String mobile,String mhtOrderNo){
        Map<String,String> requestMap = new HashMap<String, String>();
        requestMap.put("idCardName",idCardName);
        requestMap.put("idCard",idCard);
        requestMap.put("mobile",mobile);
        if(StringUtils.isEmpty(mhtOrderNo)){
            requestMap.put("mhtOrderNo", RandomUtil.getRandomStr(20));
        }else{
            requestMap.put("mhtOrderNo",mhtOrderNo);
        }
        return query(requestMap,"ID03");
    }


    /**
     * 手机号认证 - 订单查询
     * @param mhtOrderNo
     * @return
     */
    public Map MobileNoAuthQuery(String mhtOrderNo){
        Map<String,String> requestMap = new HashMap<String, String>();
        requestMap.put("mhtOrderNo",mhtOrderNo);
        return query(requestMap,"ID03_Query");
    }
}
