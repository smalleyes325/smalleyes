/*
 * 文件名：GetIp
 * 版权：Copyright by www.baiqishi.com
 * 创建人：SmalleyesWong
 * 创建时间：2018/8/9
 */

package common;

import org.apache.commons.lang3.StringUtils;
import wang.smalleyes.sm.common.util.FileUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * <p>〈功能详细描述〉</p>
 *
 * @author SmalleyesWong
 * @version 2018/8/9
 * @since
 */
public class GetIp {

    private static Map<String,String> ipMap = new HashMap<>();

    private static String ip = "10.30.220.25";

    private static void init(){
        String ipStr = FileUtils.read("E:\\Project\\smalleyes\\smwechat\\src\\test\\resources\\ip");
        String[] split = ipStr.split("\r\n");
        for (String s : split) {
            if(StringUtils.isBlank(s)){
                continue;
            }
            String[] ips = s.split("\t");
            ipMap.put(ips[0].trim(),ips[1].trim());
        }
    }

    public static String getIp(String ip){
        init();
        return ipMap.get(ip);
    }

    public static void main(String[] args) {
        System.err.println(getIp(ip));
    }
}

