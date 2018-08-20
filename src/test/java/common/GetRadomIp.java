/*
 * 文件名：GetRadomIp
 * 版权：Copyright by www.baiqishi.com
 * 创建人：SmalleyesWong
 * 创建时间：2018/8/9
 */

package common;

import org.apache.commons.lang3.StringUtils;
import wang.smalleyes.sm.common.util.FileUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * 〈一句话功能简述〉
 * <p>〈功能详细描述〉</p>
 *
 * @author SmalleyesWong
 * @version 2018/8/9
 * @since
 */
public class GetRadomIp {
    private static List<String> allIpSet;

    private static Set<String> resultIp = new HashSet<>();

    private static int totalSize;

    public static void getAllIp() {
        //总ip列表;
        String read = FileUtils.read("E:\\Project\\smalleyes\\smwechat\\src\\test\\resources\\all-tb.txt");
        String[] split = read.split("\r\n");
        allIpSet = new ArrayList<>();
        for (String ip : split) {
            if (StringUtils.isNotBlank(ip)) {
                allIpSet.add(ip);
            }
        }
        totalSize = allIpSet.size();
    }
    public static void getIp(int num){
        getAllIp();
        Random random = new Random();
        while(true){
            int index = random.nextInt(allIpSet.size());
            resultIp.add(allIpSet.remove(index));
            if(resultIp.size()==num || resultIp.size() == totalSize){
                break;
            }
        }
    }

    public static void main(String[] args) {
        getIp(100);

        StringBuilder stringBuilder = new StringBuilder();
        for (String ip : resultIp) {
            stringBuilder.append(ip).append(";");

        }
        System.out.println(stringBuilder.toString());
    }
}

