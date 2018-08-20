/*
 * 文件名：AnalysisPartner
 * 版权：Copyright by www.baiqishi.com
 * 创建人：SmalleyesWong
 * 创建时间：2018/7/4
 */

package common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import wang.smalleyes.sm.common.util.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 〈一句话功能简述〉
 * <p>〈功能详细描述〉</p>
 *
 * @author SmalleyesWong
 * @version 2018/7/4
 * @since
 */
public class AnalysisPartner {

    private List<String> messages = new ArrayList<>();

    private Map<String, Integer> patners = new HashMap<>();

    @Before
    public void init() {
        String read = FileUtils.read("E:\\Project\\smalleyes\\smwechat\\src\\test\\resources\\failure.json");
        JSONObject jsonObject = JSON.parseObject(read);
        JSONArray jsonArray =
                ((JSONObject) jsonObject.getJSONArray("responses").get(0)).getJSONObject("hits").getJSONArray("hits");
        for (Object obj : jsonArray) {
            JSONObject hit = (JSONObject) obj;
            String string = hit.getJSONObject("_source").getString("message");
            messages.add(string);
        }

    }

    @Test
    public void test() {
        for (String message : messages) {
            String s = StringUtils.substringBetween(message, "4312errmsg:", ",ticket");
            patners.putIfAbsent(s, 0);
            patners.put(s, patners.get(s) + 1);
        }

        for (Map.Entry<String, Integer> result : patners.entrySet()) {
            System.err.println(result.getKey() + "---" + result.getValue());
        }
    }

    @Test
    public void testJiequ(){

    }
}

