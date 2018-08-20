/*
 * 文件名：ReportCenterTest
 * 版权：Copyright by www.baiqishi.com
 * 创建人：SmalleyesWong
 * 创建时间：2018/8/17
 */

package thread;

import java.net.URLEncoder;

/**
 * 〈一句话功能简述〉
 * <p>〈功能详细描述〉</p>
 *
 * @author SmalleyesWong
 * @version 2018/8/17
 * @since
 */
public class ReportCenterTest {

    public static void main(String[] args) {
        String name = "王相";
        String encode = URLEncoder.encode(name);

        System.err.println(name);
    }
}

