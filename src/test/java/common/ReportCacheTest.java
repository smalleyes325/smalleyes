/*
 * 文件名：ReportCacheTest
 * 版权：Copyright by www.baiqishi.com
 * 创建人：SmalleyesWong
 * 创建时间：2018/6/19
 */

package common;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * <p>〈功能详细描述〉</p>
 *
 * @author SmalleyesWong
 * @version 2018/6/19
 * @since
 */
public class ReportCacheTest {

    @Test public void test() {
        long startTime = System.currentTimeMillis();
        String mobileTag = ReportCache.getMobileTag(null);
        String mobileTag1 = ReportCache.getMobileTag("123");
        String mobileTag2 = ReportCache.getMobileTag("123");
        String mobileTag3 = ReportCache.getMobileTag("123");
        String mobileTag4 = ReportCache.getMobileTag("123");
        String mobileTag5 = ReportCache.getMobileTag("1");
        Map<String, List<String>> mobileTagMap = ReportCache.getMobileTagMap();
        mobileTagMap.remove("1");
        System.err.println(mobileTag);
        System.err.println("耗时:" + (System.currentTimeMillis() - startTime) + "ms");
    }
}

