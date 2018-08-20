package common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;
import wang.smalleyes.sm.common.log.LogUtils;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: SmalleyesWong
 * Date: 2018/6/5
 * Time: 10:14
 * Description:
 */
public class ReportPage {
    private static final LogUtils LOG = LogUtils.newInstance(ReportPage.class);

    @Test
    public void testGetReportPage() {
        String partnerId = "whptp";
        String verifyKey = "68e63be483d745b9b1adba207c180deb";
//        String partnerId = "bqs2";
//        String verifyKey = "8bcc5c89c0da409692abc7c92bf925ee";
//        String partnerId = "bqs2";
//        String verifyKey = "a12717900b3d4a06a8d2c3df6c9075b3";
        String certNo = "440507199302100010";
        String mobile = "18515161211";
        String name = "连泽锋";
        String timeStamp = System.currentTimeMillis() + "";
        String token = getToken(partnerId, verifyKey, certNo, timeStamp);
        String type = "mno";
        getReportPage(partnerId, verifyKey, certNo, mobile, name, timeStamp, token, type);
    }

    public void getReportPage(String partnerId, String verifyKey, String certNo, String mobile, String name, String timeStamp, String token, String type) {
        String url = "https://credit.baiqishi.com/clweb/api/" + type +
//                String url = "http://192.168.1.182:8089/clweb/api/"+ type +
                "/getreportpage" +
                "?partnerId=" + partnerId +
                "&verifyKey=" + verifyKey +
                "&certNo=" + certNo +
                "&mobile=" + mobile +
                "&name=" + name +
                "&timeStamp=" + timeStamp +
                "&token=" + token;

        System.err.println(url);
    }

    public String getToken(String partnerId, String verifyKey, String certNo, String timeStamp) {
        String url = "https://credit.baiqishi.com/clweb/api/common/gettoken";
//        String url = "http://192.168.1.182:8089/clweb/api/common/gettoken";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("partnerId", partnerId);
        jsonObject.put("verifyKey", verifyKey);

        jsonObject.put("certNo", certNo);
        jsonObject.put("timeStamp", timeStamp);
        String requestBody = jsonObject.toJSONString();

        try {
            Connection.Response response = Jsoup.connect(url).method(Connection.Method.POST).requestBody(requestBody).ignoreContentType(true).execute();
            if (response != null) {
                String body = response.body();
                JSONObject data = JSON.parseObject(body);
                return data.getString("data");
            }
        } catch (IOException e) {
            LOG.error("", e);
        }
        return "";
    }
}
