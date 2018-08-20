/*
 * 文件名：BaseQueue
 * 版权：Copyright by www.baiqishi.com
 * 创建人：SmalleyesWong
 * 创建时间：2018/8/15
 */

package thread;

import wang.smalleyes.sm.common.log.LogUtils;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 〈基队列〉
 * <p>〈功能详细描述〉</p>
 *
 * @author SmalleyesWong
 * @version 2018/8/15
 * @since
 */
abstract class BaseQueue<T> {
    private static final LogUtils LOG = LogUtils.newInstance(BaseQueue.class);

    private ConcurrentLinkedDeque<T> queue = new ConcurrentLinkedDeque<>();

    private int notNullCount = 0;

    public BaseQueue() {
        startMonitor();
    }

    /**
     * 监控 当队列长时间为空时 打印日志
     */
    private void startMonitor() {
        long initTime = System.currentTimeMillis();
        LOG.info(String.format("队列监控开启:%s",new Date().toString()));
        new Thread(() -> {
            while(true){
                if(queue.size() == 0){
                    if((System.currentTimeMillis() - initTime) > (2 * 60 * 1000L)){
                        LOG.warn("queue.size = 0;after 2min");
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            LOG.error("thread sleep error",e);
                        }
                    }
                }else{
                    notNullCount ++;
                }
            }
        }).start();
    }

    T get() {
        return queue.removeFirst();
    }

    void putBack(T t) {
        queue.addLast(t);
    }

    int size() {
        return queue.size();
    }

    boolean addAll(Collection<T> collection) {
        return queue.addAll(collection);
    }

}

