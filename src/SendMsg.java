import java.io.IOException;

/**
 * @Author: yanshilong
 * @Date: 18-8-30 下午3:45
 * @Version 1.0
 */
public class SendMsg {
    public static void main(String[] args) throws IOException {
        String mobileNumber = "17695569629";
        SendCode.sendMsg(mobileNumber);
        //MobileMessageSend.sendMsg(mobileNumer);
    }
}
