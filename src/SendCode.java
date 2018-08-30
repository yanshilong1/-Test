import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


/**
 * 发送验证码
 * @author liuxuanlin
 *
 */
public class SendCode {
    //发送验证码的请求路径URL
    private static final String SERVER_URL="https://api.netease.im/sms/sendcode.action";
    //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
    private static final String APP_KEY="a5597bdf07ea6bafba95677826565c22";
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
    private static final String APP_SECRET="ab41a9f0cdd7";
    //随机数
    private static final String NONCE="123456";
    //短信模板ID
    private static final String TEMPLATEID="9294020";
    //手机号
//    private static final String MOBILE="接收者，当然这里注释了。";
    //验证码长度，范围4～10，默认为4
    private static final String CODELEN="6";

    public static void sendMsg(String phone) throws IOException {
      //  CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpClient httpClients=HttpClients.createDefault();
        HttpPost post = new HttpPost(SERVER_URL);

//      CheckSum的计算
        String curTime=String.valueOf((new Date().getTime()/1000L));
        String checkSum= CheckSumBuilder.getCheckSum(APP_SECRET,NONCE,curTime);

        //设置请求的header
        post.addHeader("AppKey",APP_KEY);
        post.addHeader("Nonce",NONCE);
        post.addHeader("CurTime",curTime);
        post.addHeader("CheckSum",checkSum);
        post.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

        //设置请求参数
        List<NameValuePair> nameValuePairs =new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("mobile",phone));
        nameValuePairs.add(new BasicNameValuePair("templateid", TEMPLATEID));
        nameValuePairs.add(new BasicNameValuePair("codeLen", CODELEN));
        System.out.println(nameValuePairs);
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));

        //执行请求
        HttpResponse response=httpClients.execute(post);
        String responseEntity= EntityUtils.toString(response.getEntity(),"utf-8");


        //获取发送状态码
        String code= JSON.parseObject(responseEntity).getString("code");
        String sms= JSON.parseObject(responseEntity).getString("obj");
        System.out.println(sms);

        if (code.equals("200")){
            System.out.println("发送成功！");
            return;
        }
        System.out.println("发送失败！");
    }
}