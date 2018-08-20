package wang.smalleyes.sm.wechat.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SmalleyesWong
 * Date: 2018/6/15
 * Time: 9:44
 * Description:
 */
public class NormalBuffer {

    private static BufferCache bufferCache;

    static {
        bufferCache = BufferCache.getInstance();
    }

    public static List<String> getCache() {
        return bufferCache.getNameCache();
    }

    public static void setCache() {
        bufferCache.setNameCache(new ArrayList<>());
    }

    public static void main(String[] args) {
        System.err.println(NormalBuffer.getCache());
        NormalBuffer.setCache();
        System.err.println(NormalBuffer.getCache());
    }

}
