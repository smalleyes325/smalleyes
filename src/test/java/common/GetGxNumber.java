/*
 * 文件名：GetGxNumber
 * 版权：Copyright by www.baiqishi.com
 * 创建人：SmalleyesWong
 * 创建时间：2018/7/18
 */

package common;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import wang.smalleyes.sm.common.log.LogUtils;

import java.io.IOException;

/**
 * 〈一句话功能简述〉
 * <p>〈功能详细描述〉</p>
 *
 * @author SmalleyesWong
 * @version 2018/7/18
 * @since
 */
public class GetGxNumber {
    private static final LogUtils LOG = LogUtils.newInstance(GetGxNumber.class);

    @Test
    public void test(){
        String url = "";
        try {
            Connection.Response execute = Jsoup.connect(url).ignoreContentType(true).execute();
            String body = execute.body();
            Document parse = Jsoup.parse(body);
        } catch (IOException e) {
            LOG.error("发生异常");
        }
    }
}

