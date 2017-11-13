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

//        Map map = identitySdk.IdentityAuthQuery("Num12011525");
//        System.out.println(FormDateReportConvertor.postFormLinkReport(map));
//
//        Map map1 = identitySdk.IdentityAuth("张江南","110101198204031532",null);
//        System.out.println(FormDateReportConvertor.postFormLinkReport(map1));

//        Map map2 = identitySdk.CardAuth("大王","230128199207080925","6226200105111414","13264172356",null);
//        System.out.println(FormDateReportConvertor.postFormLinkReport(map2));

//        Map map3 = identitySdk.CardAuthQuery("X3w89qe0BuwbgkmsWf3d");
//        System.out.println(FormDateReportConvertor.postFormLinkReport(map3));

//        Map map4 = identitySdk.MobileNoAuth("大王","230128199207080569","13264177123",null);
//        System.out.println(FormDateReportConvertor.postFormLinkReport(map4));

        Map map5 = identitySdk.MobileNoAuthQuery("Num11111606");
        System.out.println(FormDateReportConvertor.postFormLinkReport(map5));

    }

}
