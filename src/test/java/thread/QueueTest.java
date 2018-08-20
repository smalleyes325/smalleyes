/*
 * 文件名：QueueTest
 * 版权：Copyright by www.baiqishi.com
 * 创建人：SmalleyesWong
 * 创建时间：2018/8/15
 */

package thread;

import wang.smalleyes.sm.common.log.LogUtils;

import java.util.NoSuchElementException;


/**
 * 〈一句话功能简述〉
 * <p>〈功能详细描述〉</p>
 *
 * @author SmalleyesWong
 * @version 2018/8/15
 * @since
 */
public class QueueTest {
    private static LogUtils LOG = LogUtils.newInstance(QueueTest.class);

    private static int errorCount = 0;

    public static void main(String[] args) {

        new Thread(() -> {

            new Thread(() -> {
                while (errorCount < 10){
                    String value = String.valueOf(Math.random());
                    QueueCache.alipayUaQueue.putBack(value);
                    LOG.info(String.format("放入队列:%s,当前队列size:%s",value,QueueCache.alipayUaQueue.size()));
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        LOG.error("休眠异常",e);
                    }
                }
            }).start();

            new Thread(() -> {
                try {
                    LOG.info("消费线程1启动,等待10s");
                    Thread.sleep(2 * 5000);
                } catch (InterruptedException e) {
                    LOG.error("",e);
                }
                LOG.info("消费线程1开始消费");
                while (errorCount < 10){
                    try {
                        LOG.info("消费线程1获得: "+QueueCache.alipayUaQueue.get());
                    }catch (NoSuchElementException e){
                        LOG.error("消费线程1未获取到数据,队列大小为:" + QueueCache.alipayUaQueue.size());
                        errorCount ++;
                    } catch (Exception e) {
                        LOG.error("",e);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(() -> {
                try {
                    LOG.info("消费线程2启动,等待10s");
                    Thread.sleep(2 * 5000);
                } catch (InterruptedException e) {
                    LOG.error("",e);
                }
                LOG.info("消费线程2开始消费");
                while (errorCount < 10){
                    try {
                        LOG.info("消费线程2获得: "+QueueCache.alipayUaQueue.get());
                    }catch (NoSuchElementException e){
                        LOG.error("消费线程2 未获取到数据,队列大小为:" + QueueCache.alipayUaQueue.size());
                        errorCount ++;
                    } catch (Exception e) {
                        LOG.error("",e);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }).start();
    }

}

