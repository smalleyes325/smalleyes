package wang.smalleyes.sm.wechat.domain;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SmalleyesWong
 * Date: 2018/6/15
 * Time: 9:45
 * Description:
 */
public class BufferCache {
    private List<String> nameCache;

    private static BufferCache self;

    public static BufferCache getInstance() {
        if (self == null) {
            self = new BufferCache();
            return self;
        } else {
            return self;
        }
    }

    public List<String> getNameCache() {
        return nameCache;
    }

    public void setNameCache(List<String> nameCache) {
        this.nameCache = nameCache;
    }
}
