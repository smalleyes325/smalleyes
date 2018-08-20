/*
 * 文件名：ReportExt
 * 版权：Copyright by www.baiqishi.com
 * 创建人：SmalleyesWong
 * 创建时间：2018/6/19
 */

package common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;
import wang.smalleyes.sm.common.log.LogUtils;

import java.net.URLEncoder;

/**
 * 〈一句话功能简述〉
 * <p>〈功能详细描述〉</p>
 *
 * @author SmalleyesWong
 * @version 2018/6/19
 * @since
 */
public class ReportExt {
    private static final LogUtils LOG = LogUtils.newInstance(ReportExt.class);

    @Test public void reportExt() {
        String partnerId = "bqs2";
        String name = "王i相";
        String certNo = "513030199502018617";
        String mobile = "18583636357";
        JSONArray contacts = new JSONArray();
        JSONObject contact1 = new JSONObject();
        contact1.put("name","崔先生");
        contact1.put("mobile","18617191947");
        contact1.put("relation","3");
        JSONObject contact2 = new JSONObject();
        contact2.put("name","王先生");
        contact2.put("mobile","13079025379");
        contact2.put("relation","1");
        JSONObject contact3 = new JSONObject();
        contact3.put("name","王女士");
        contact3.put("mobile","13631525512");
        contact3.put("relation","8");


        contacts.add(contact1);
        contacts.add(contact2);
        contacts.add(contact3);

        reportExt(partnerId, name, certNo, mobile, contacts);
    }

    public void reportExt(String partnerId, String name, String certNo, String mobile, JSONArray contacts) {
//        String url = "https://credit.baiqishi.com/clweb/api/common/reportext";
        String url = "http://localhost:8089/clweb/api/common/reportext";
        try {
            JSONObject data = new JSONObject();
            data.put("partnerId", partnerId);
            data.put("name", name);
            data.put("certNo", certNo);
            data.put("mobile", mobile);
            data.put("contacts", contacts);
            String requestBody = URLEncoder.encode(data.toJSONString(), "utf-8");
            Connection.Response response =
                    Jsoup.connect(url).method(Connection.Method.POST).requestBody(requestBody).ignoreContentType(true)
                            .execute();
            if (response == null) {
                LOG.error("获取失败,response=null");
                return;
            }
            String body = response.body();
            if (StringUtils.contains(body, "CCOM1000")) {
                LOG.info("紧急联系人上报成功,body=" + body);
            } else {
                LOG.warn("未知返回,body=" + body);
            }
            return;
        } catch (Exception e) {
            LOG.error("", e);
        }
    }
}

