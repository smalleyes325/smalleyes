package common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import wang.smalleyes.sm.common.util.FileUtils;


import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Loger
 * Date: 2018-05-31
 * TIme: 9:37
 * Description :
 */
public class CountMain {

    private static Set<String> allIpSet;

    private static String currentIp = "";


    public static void getCurrentIp() {
        //已部署ip
        String read = FileUtils.read("E:\\Project\\smalleyes\\smwechat\\src\\test\\resources\\success.json");
        currentIp = read;
    }

    public static void getAllIp() {
        //总ip列表;
        String read = FileUtils.read("E:\\Project\\smalleyes\\smwechat\\src\\test\\resources\\all.txt");
        String[] split = read.split("\r\n");
        allIpSet = new HashSet<>();
        for (String ip : split) {
            if (StringUtils.isNotBlank(ip)) {
                allIpSet.add(ip);
            }
        }
    }

    public static void main(String[] args) {
        getCurrentIp();
        getAllIp();
        //Set<String> allIpSet = JSON.parseObject(ipSetStr, Set.class);
        JSONObject jsonObject = JSON.parseObject(currentIp);
        JSONArray rows = jsonObject.getJSONArray("rows");
        Set<String> currentIpSet = new HashSet<>();
        allIpSet = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows.size(); i++) {
            JSONObject obj = rows.getJSONObject(i);
            String ip = obj.getString("ip");
            currentIpSet.add(ip);
            if (!allIpSet.contains(ip)) {
                System.err.println("已部署ip,但不在列表中:" + ip);
                sb.append(ip + ";");
            }
        }
        System.out.println("服务器共计:" + JSON.toJSONString(allIpSet.size()));
        System.out.println("当前已部署:" + currentIpSet.size());
        System.out.println("需手动部署ip:");
        System.err.println(sb.toString());
        allIpSet.removeAll(currentIpSet);
        StringBuilder stringBuilder = new StringBuilder();
        for (String ip : allIpSet) {
            stringBuilder.append(ip).append(";");

        }
        System.out.println(stringBuilder.toString());
    }

}
