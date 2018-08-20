/*
 * 文件名：Press
 * 版权：Copyright by www.baiqishi.com
 * 创建人：SmalleyesWong
 * 创建时间：2018/8/3
 */

package common;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import wang.smalleyes.sm.common.log.LogUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 〈一句话功能简述〉
 * <p>〈功能详细描述〉</p>
 *
 * @author SmalleyesWong
 * @version 2018/8/3
 * @since
 */
public class Press extends Thread{

    private static final LogUtils LOG = LogUtils.newInstance(Press.class);

    private List<String> arrs = new ArrayList<>();


    @Override
    public void run() {
        synchronized (this){
            while(true){
                if(arrs.size() > 0){
                    arrs.remove(0);
                }else{
                    arrs.add(UUID.randomUUID().toString());
                }
                LOG.info("----" + arrs.size());
            }
        }
    }

    public static void main(String[] args) {
        Press thread1 = new Press();
        Press thread2 = new Press();
        Press thread3 = new Press();
        Press thread4 = new Press();

//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();

        try {
            String encode = URLEncoder.encode("张圳", "utf-8");
            System.err.println(URLDecoder.decode(encode,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

