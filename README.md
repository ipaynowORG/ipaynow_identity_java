# 短信SDK使用说明 #

## 版本变更记录 ##

- 1.0.0 : 初稿


## 目录 ##

[1. 概述](#1)

&nbsp;&nbsp;&nbsp;&nbsp;[1.1 简介](#1.1)

&nbsp;&nbsp;&nbsp;&nbsp;[1.2 如何获取](#1.2)

[2. API](#2)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[身份验证](#2.1)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[身份验证-订单查询](#2.2)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[卡信息认证](#2.3)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[卡信息认证-订单查询](#2.4)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[手机号认证](#2.5)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[手机号认证-订单查询](#2.6)

[3. 配置文件](#3)

[4. DEMO](#4)

<h2 id='1'> 1. 概述 </h2>

<h4 id='1.1'> 1.1 简介 </h4>

- 鉴权SDK。

<h4 id='1.2'> 1.2 如何获取 </h4>

[获取源码](https://github.com/ipaynowORG/ipaynow_identity_java)

[demo源码](https://github.com/ipaynowORG/ipaynow_identity_java)

Maven坐标如下

	<dependency>
	       <groupId>com.github.ipaynow</groupId>
           <artifactId>ipaynow_identity_sdk</artifactId>
           <version>1.0.0</version>
	</dependency>





<h2 id='2'> 2. API </h2>

业务客户端使用SDK的相关类: cn.ipaynow.identity.sdk.IdentitySdk;

<h4 id='2.1'> 2.1 身份验证</h4>

        /**
         * 身份验证
         * @param cardName  姓名
         * @param idcard    身份证
         * @param mhtOrderNo    商户订单号(可空,为空时自动生成)
         * @return
         */
        public Map IdentityAuth(String cardName,String idcard,String mhtOrderNo)

- 返回map中的值的含义

<table>
        <tr>
            <th>名称</th>
            <th>说明</th>
        </tr>
<tr>
            <td>appId</td>
            <td>同输入</td>
         </tr>
<tr>
            <td>funcode</td>
            <td>同输入</td>
         </tr>
<tr>
            <td>responseTime</td>
            <td>yyyyMMddHHmmss</td>
         </tr>
<tr>
            <td>responseCode</td>
            <td>0000 成功
                0001 参数信息有误
                0002 获取商户信息失败
                0003 获取商户费率失败
                0004 获取商户可用条数失败
                0005 商户资金不足
                0006 验证异常
                0007 商户订单号重复
            </td>
         </tr>
<tr>
            <td>responseMsg</td>
            <td>应答信息</td>
         </tr>
<tr>
            <td>status</td>
            <td>00匹配,01不匹配,02未知,03调用错误</td>
         </tr>
<tr>
            <td>transStatus</td>
            <td>00成功  01失败</td>
         </tr>
<tr>
            <td>mhtOrderNo</td>
            <td>同输入或SDK自动生成的订单号</td>
         </tr>
    </table>



<h4 id='2.2'> 2.2 身份验证-订单查询</h4>

          /**
           * 身份验证-订单查询
           * @param mhtOrderNo    商户订单号
           * @return
           */
            public Map IdentityAuthQuery(String mhtOrderNo)


<h4 id='2.3'> 2.3 卡信息认证</h4>

        /**
         *  卡信息认证
         * @param idCardName   姓名
         * @param idCard    身份证
         * @param bankCardNum   银行账户
         * @param mhtOrderNo    商户订单号(可空,为空时自动生成)
         * @return
         */
        public Map CardAuth(String idCardName,String idCard,String bankCardNum,String mhtOrderNo)


<h4 id='2.4'> 2.4 卡信息认证-订单查询</h4>

        /**
         * 卡信息认证- 订单查询
         * @param mhtOrderNo
         * @return
         */
        public Map CardAuthQuery(String mhtOrderNo)



<h4 id='2.5'> 2.5 手机号认证</h4>

        /**
         * 手机号认证
         * @param idCardName    认证姓名
         * @param idCard    身份证号码
         * @param mobile    手机号
         * @param mhtOrderNo    商户订单号
         * @return
         */
        public Map MobileNoAuth(String idCardName,String idCard,String mobile,String mhtOrderNo)

<h4 id='2.6'> 2.6 手机号认证-订单查询</h4>

        /**
         * 手机号认证 - 订单查询
         * @param mhtOrderNo
         * @return
         */
        public Map MobileNoAuthQuery(String mhtOrderNo)

<h2 id='3'> 3. 配置文件 </h2>

classpath下创建名为ipaynow-identity-sdk.properties的配置文件,内容如下

```
#appId(应用ID)和md5(appKey),登录商户后台 : https://mch.ipaynow.cn ->商户中心->应用信息可以新增应用或查看appKey
appId=xxxxxxxxx
md5=xxxxxxxxxx
#需要在运营后台-鉴权服务管理 中为商户进行配置
des=xxxxxxxx
```


<h2 id='4'> 4. DEMO </h2>

```
    private static SmsSdk smsSdk = new SmsSdk();
    public static void main(String [] args){
        //发送行业短信
        System.out.println(smsSdk.send_hy("13401234567","内容"null,"https://www.xxx.com"));

        //查询发送结果
        System.out.println(smsSdk.query("1234567890987654321","13401234567"));

}
```