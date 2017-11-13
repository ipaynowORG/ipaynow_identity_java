# 鉴权SDK使用说明 #

## 版本变更记录 ##

- 1.0.0 : 初稿


## 目录 ##

[1. 概述](#1)

&nbsp;&nbsp;&nbsp;&nbsp;[1.1 简介](#1.1)

&nbsp;&nbsp;&nbsp;&nbsp;[1.2 如何获取](#1.2)

[2. API](#2)

&nbsp;&nbsp;&nbsp;&nbsp;[2.1 短信发送](#2.1)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[行业短信发送](#2.1.1)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[营销短信发送](#2.1.2)

&nbsp;&nbsp;&nbsp;&nbsp;[2.2 接受通知(状态报告)](#2.2)

&nbsp;&nbsp;&nbsp;&nbsp;[2.3 查询短信发送结果(状态报告)](#2.3)


[3. 配置文件](#3)

[4. DEMO](#4)

<h2 id='1'> 1. 概述 </h2>

<h4 id='1.1'> 1.1 简介 </h4>

- 短信SDK。

<h4 id='1.2'> 1.2 如何获取 </h4>

[获取源码](https://github.com/ipaynowORG/ipaynow_sms_java)

[demo源码](https://github.com/ipaynowORG/ipaynow_sms_java)

Maven坐标如下

	<dependency>
	       <groupId>com.github.ipaynow</groupId>
           <artifactId>ipaynow_sms_sdk</artifactId>
           <version>1.0.0</version>
	</dependency>





<h2 id='2'> 2. API </h2>

业务客户端使用SDK的相关类: cn.ipaynow.sms.sdk.SmsSdk

<h4 id='2.1'> 2.1 短信发送 </h4>

<h5 id='2.1.1'></h4>

- 行业短信发送

            /**
             * 发送行业短信(需要在运营后台-短信服务管理 中进行配置)
             * @param mobile    发送手机号
             * @param content   发送内容
             * @param mhtOrderNo    商户订单号,可为空(自动生成)。商户订单号和状态报告通知中的相关字段对应
             * @param notifyUrl 后台通知地址
             * @return  现在支付订单号,和状态报告通知中的相关字段对应。查询短信发送结果(状态报告)使用该字段。
             */
            public  String send_hy(String mobile,String content,String mhtOrderNo,String notifyUrl)

<h5 id='2.1.2'></h4>

- 营销短信发送

            /**
             * 发送营销短信(需要在运营后台-短信服务管理 中进行配置)
             * @param mobile    发送手机号
             * @param content   发送内容
             * @param mhtOrderNo    商户订单号,可为空(自动生成)。商户订单号和状态报告通知中的相关字段对应
             * @param notifyUrl 后台通知地址
             * @return  现在支付订单号,和状态报告通知中的相关字段对应。查询短信发送结果(状态报告)使用该字段。
             */
            public  String send_yx(String mobile,String content,String mhtOrderNo,String notifyUrl)


<h4 id='2.2'>2.2 接受通知(状态报告)</h4>

由现在支付方发起,通知方式采用httppost方式通知,接受demo如下

        //获取通知数据需要从body中流式读取
        BufferedReader reader = req.getReader();
        StringBuilder reportBuilder = new StringBuilder();
        String tempStr = "";
        while((tempStr = reader.readLine()) != null){
               reportBuilder.append(tempStr);
        }
        //报文数据字符串
        String reportContent = reportBuilder.toString();


字段含义如下:

<table>
        <tr>
            <th>字段名称</th>
            <th>字段Key</th>
            <th>备注</th>
        </tr>
        <tr>
            <td>功能码</td>
            <td>funcode</td>
            <td>同下行时候的输入,S01或YX_01</td>
        </tr>
        <tr>
            <td>商户应用ID</td>
            <td>appId</td>
            <td>同输入</td>
         </tr>
<tr>
            <td>手机号</td>
            <td>phone</td>
            <td>下行手机号</td>
         </tr>
<tr>
            <td>商户订单号</td>
            <td>mhtOrderNo</td>
            <td>同输入</td>
         </tr>
<tr>
            <td>订单生成时间</td>
            <td>mhtOrderStartTime</td>
            <td></td>
         </tr>
<tr>
            <td>现在支付订单号</td>
            <td>nowPayOrderNo</td>
            <td></td>
         </tr>
<tr>
            <td>字符编码</td>
            <td>mhtCharset</td>
            <td>定值UTF-8</td>
         </tr>
<tr>
            <td>签名类型</td>
            <td>signType</td>
            <td>定值MD5</td>
         </tr>
<tr>
            <td>签名值</td>
            <td>signature</td>
            <td></td>
         </tr>
<tr>
            <td>推送状态</td>
            <td>tradeStatus</td>
            <td>A001:成功。A002:失败。 A00H:处理中</td>
         </tr>
    </table>

<h4 id='2.3'> 2.3 查询短信发送结果(状态报告) </h4>

- 查询短信发送结果(状态报告)

            /**
             * 查询短信发送结果(状态报告)
             * @param nowPayOrderNo 现在支付订单号(send_yx和send_hy方法的返回值)
             * @param mobile 手机号
             * @return 发送成功返回true , 失败false
             */
            public  boolean query(String nowPayOrderNo,String mobile)

<h2 id='3'> 3. 配置文件 </h2>

classpath下创建名为ipaynow-sms-sdk.properties的配置文件,内容如下

```
#appId(应用ID)和md5(appKey),登录商户后台 : https://mch.ipaynow.cn ->商户中心->应用信息可以新增应用或查看appKey
appId=xxxxxxxxx
md5=xxxxxxxxxx
#需要在运营后台-短信服务管理 中为商户进行配置
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