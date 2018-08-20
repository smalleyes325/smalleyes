/*
 * 文件名：Article
 * 版权：Copyright by www.baiqishi.com
 * 创建人：SmalleyesWong
 * 创建时间：2018/8/6
 */

package wang.smalleyes.sm.wechat.domain;

/**
 * 〈一句话功能简述〉
 * <p>〈功能详细描述〉</p>
 *
 * @author SmalleyesWong
 * @version 2018/8/6
 * @since
 */
public class Article {
    private String title;

    private String content;

    private String contentWithTags;

    private Long publishDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentWithTags() {
        return contentWithTags;
    }

    public void setContentWithTags(String contentWithTags) {
        this.contentWithTags = contentWithTags;
    }

    public Long getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Long publishDate) {
        this.publishDate = publishDate;
    }
}

