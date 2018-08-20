/*
 * 文件名：AnalysisFailure
 * 版权：Copyright by www.baiqishi.com
 * 创建人：SmalleyesWong
 * 创建时间：2018/8/8
 */

package common;

import org.apache.commons.lang3.StringUtils;
import wang.smalleyes.sm.common.util.FileUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 〈一句话功能简述〉
 * <p>〈功能详细描述〉</p>
 *
 * @author SmalleyesWong
 * @version 2018/8/8
 * @since
 */
public class AnalysisFailure {
    private static Map<String, Integer> resend = new HashMap<>();

    private static Map<String, Integer> login = new HashMap<>();

    private static Map<String, Integer> loginFailed = new HashMap<>();

    private static int total;

    private static int failed;

    private static int loginFailedCount;

    public static void init() {
        String resendSmsTxt = FileUtils.read("E:\\Project\\smalleyes\\smwechat\\src\\test\\resources\\resendSms.txt");

        String loginTxt = FileUtils.read("E:\\Project\\smalleyes\\smwechat\\src\\test\\resources\\login.txt");

        String[] resendSms = StringUtils.split(resendSmsTxt, "\r\n");
        for (String str : resendSms) {
            String[] split = StringUtils.split(str, "\t");
            resend.put(split[0].trim(), Integer.valueOf(split[1].trim()));
            failed += Integer.valueOf(split[1].trim());
        }

        String[] loginArr = StringUtils.split(loginTxt, "\r\n");
        for (String str : loginArr) {
            String[] split = StringUtils.split(str, "\t");
            login.put(split[0].trim(), Integer.valueOf(split[1].trim()));
            if (StringUtils.startsWith(split[0], "登录失败，请重试")) {
                loginFailed.put(split[0].trim(), Integer.valueOf(split[1].trim()));
                loginFailedCount += Integer.valueOf(split[1].trim());
            } failed += Integer.valueOf(split[1].trim());
        }
    }

    public static void main(String[] args) {
        init();
        System.err.println("失败数: " + failed);
        System.err.println(String.format("登录失败数: %s, 占比: %s", loginFailedCount, loginFailedCount / (double) failed));

        List<Map.Entry<String,Integer>> list = new ArrayList<>(loginFailed.entrySet());
        //升序排序
        list.sort(Comparator.comparing(Map.Entry::getValue));
        System.err.println("以下是所有登录失败返回码 及其占比:");
        System.err.println("");
        for(Map.Entry<String,Integer> entry:list){
            System.err.println(entry.getKey() + "    " + entry.getValue() + "    " + entry.getValue()/(double)failed);
        }
        System.err.println(" ");
        System.err.println(" ");
        System.err.println(" ");
        List<Map.Entry<String,Integer>> resendList = new ArrayList<>(resend.entrySet());
        //升序排序
        resendList.sort(Comparator.comparing(Map.Entry::getValue));

        System.err.println("以下是所有重发验证码阶段失败返回码 及其占比:");
        System.err.println("");

        for(Map.Entry<String,Integer> entry:resendList){
            System.err.println(entry.getKey() + "    " + entry.getValue() + "    " + entry.getValue()/(double)failed);
        }

        System.err.println(" ");
        System.err.println(" ");
        System.err.println(" ");
        List<Map.Entry<String,Integer>> loginList = new ArrayList<>(login.entrySet());
        //升序排序
        loginList.sort(Comparator.comparing(Map.Entry::getValue));

        System.err.println("以下是所有登录阶段失败返回码 及其占比:");
        System.err.println("");

        for(Map.Entry<String,Integer> entry:loginList){
            System.err.println(entry.getKey() + "    " + entry.getValue() + "    " + entry.getValue()/(double)failed);
        }
    }
}

