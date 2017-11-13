package cn.ipaynow.ipaynow_identity_demo;

import cn.ipaynow.identity.sdk.IdentitySdk;
import cn.ipaynow.util.FormDateReportConvertor;

import java.util.Map;

/**
 * Created by ipaynow1130 on 2017/11/8.
 */
public class Demo {


    private static IdentitySdk identitySdk = new IdentitySdk();

    public static void main(String [] args){

        Map map = identitySdk.IdentityAuthQuery("Num12011525");
        System.out.println(FormDateReportConvertor.postFormLinkReport(map));

        Map map1 = identitySdk.IdentityAuth("张江南","110101198204031532",null);
        System.out.println(FormDateReportConvertor.postFormLinkReport(map1));
    }

}
