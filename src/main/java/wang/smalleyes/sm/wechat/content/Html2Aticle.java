/*
 * 文件名：Html2Aticle
 * 版权：Copyright by www.baiqishi.com
 * 创建人：SmalleyesWong
 * 创建时间：2018/8/6
 */

package wang.smalleyes.sm.wechat.content;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import wang.smalleyes.sm.common.log.LogUtils;
import wang.smalleyes.sm.wechat.domain.Article;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 〈正文分析〉
 * <p>〈解析Html页面的文章正文内容,基于文本密度的HTML正文提取类〉</p>
 *
 * @author SmalleyesWong
 * @version 2018/8/6
 * @since
 */
public class Html2Aticle {
    private static final LogUtils LOG = LogUtils.newInstance(Html2Aticle.class);

    private static final String[][] filters = {
                    {"<script.*?>.*?</script>", ""},
                    {"<style.*?>.*?</style>", ""},
                    {"<!--.*?-->", ""},
                    {"</a>", "</a>\n"}
            };

    //  是否使用追加模式，默认为false
    //  使用追加模式后，会将符合过滤条件的所有文本提取出来
    private static boolean _appendMode = false;

    public static boolean isAppendMode() {
        return _appendMode;
    }

    public static void setAppendMode(boolean appendMode) {
        _appendMode = appendMode;
    }

    // 按行分析的深度,默认为6
    private static int _depth = 6;

    private static int get_depth() {
        return _depth;
    }

    private static void set_depth(int depth) {
        _depth = depth;
    }

    //字符限定数，当分析的文本数量达到限定数则认为进入正文内容,默认为180
    private static int _limitCount = 180;

    public static int get_limitCount() {
        return _limitCount;
    }

    public static void set_limitCount(int limitCount) {
        _limitCount = limitCount;
    }

    // 确定文章正文头部时，向上查找，连续的空行到达_headEmptyLines，则停止查找
    private static int _headEmptyLines = 2;
    // 用于确定文章结束的字符数
    private static int _endLimitCharCount = 20;

    public static Article getArticle(String html) {
        if (StringUtils.countMatches(html, "\r\n") < 10) {
            html = StringUtils.replacePattern(html, ">", ">\r\n");
        }
        String body = "";
        String bodyFilter = "(?is)<body.*?</body>";
        Pattern pattern = Pattern.compile(bodyFilter);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            body = matcher.group();
        }
        if (StringUtils.isBlank(body)) {
            LOG.error("过滤html 失败");
        }
        //过滤样式,脚本等标签
        for (String[] filter : filters) {
            body = StringUtils.replacePattern(body, filter[0], filter[1]);
        }

        //body = formatTag(body);


        Article article = getContent(body);

        article.setTitle(getTitle(html));
        article.setPublishDate(getPublishDate(body));


        return article;
    }

    /**
     * 获取文章发布日期
     * @param body
     * @return
     */
    private static Long getPublishDate(String body) {
        //过滤html 标签,防止标签对日期提取产生影响
        String text = StringUtils.replacePattern(body,"(?is)<.*?>","");
        Pattern pattern = Pattern.compile("((\\d{4}|\\d{2})(\\-|\\/)\\d{1,2}\\3\\d{1,2})(\\s?\\d{2}:\\d{2})?|(\\d{4}年\\d{1,2}月\\d{1,2}日)(\\s?\\d{2}:\\d{2})?",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        Long dateTime = 0L;
        if(matcher.find()){
            try{
                String dateStr = "";
                for(int i = 0; i < matcher.groupCount(); i++){
                    dateStr = matcher.group(i);
                    if(StringUtils.isNotBlank(dateStr)){
                        break;
                    }
                    //对中文日期的处理
                    if (StringUtils.contains(dateStr,"年")) {
                        StringBuilder sb = new StringBuilder();
                        for(char ch : dateStr.toCharArray())
                        {
                            if (ch == '年' || ch == '月') {
                                sb.append("/");
                                continue;
                            }else if (ch == '日') {
                                sb.append(' ');
                                continue;
                            }else{
                                sb.append(ch);
                            }
                        }
                        dateStr = sb.toString();
                    }
                }
                dateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(dateStr).getTime();

            }catch (Exception e){
                LOG.error("获取文章发布日期失败",e);
            }
        }
        return dateTime;
    }

    /**
     * 获取标题
     * @param html
     * @return
     */
    private static String getTitle(String html) {
        String titleFilter = "<title>[\\s\\S]*?</title>";
        String h1Filter = "<h1.*?>.*?</h1>";
        String clearFilter = "<.*?>";
        String title = "";
        Pattern pattern = Pattern.compile(titleFilter,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            title = StringUtils.replacePattern(matcher.group(0),clearFilter,"");
        }
        // 正文的标题一般在h1中，比title中的标题更干净
        Pattern h1Pattern = Pattern.compile(h1Filter,Pattern.CASE_INSENSITIVE);
        if (matcher.find()){
            String h1 = StringUtils.replacePattern(matcher.group(0),clearFilter,"");
            if(StringUtils.isNotBlank(h1) && StringUtils.startsWith(title,h1)){
                title = h1;
            }
        }
        return (title == null?"":title);
    }

    /**
     * 从body标签文本中分析正文内容
     *
     * @param body 只过滤了script 和 style 标签的body文本内容
     */
    public static Article getContent(String body) {
        String content = "";
        String contentWithTags = "";
        Article article = new Article();
        if (StringUtils.isBlank(body)) {
            return null;
        }
        String[] orgLines = null; //保存原始内容,按行存储
        String[] lines = null;    //保存干净的文本内容,不包含标签
        orgLines = StringUtils.split(body, "\n");
        lines = new String[orgLines.length];
        //去除每行的空白字符,剔除标签
        for (int i = 0; i < orgLines.length; i++) {
            String lineInfo = orgLines[i];
            //处理回车,使用[crlf]作为回车标记符,最后统一处理
            lineInfo = StringUtils.replacePattern(lineInfo, "(?is)</p>|<br.*?/>", "[crlf]");
            lines[i] = StringUtils.trim(StringUtils.replacePattern(lineInfo, "(?is)<.*?>", ""));
        }

        StringBuilder sb = new StringBuilder();
        StringBuilder orgSb = new StringBuilder();

        int preTextLen = 0;     // 记录上一次统计的字符数量
        int startPos = -1;      // 记录文章正文的起始位置

        for (int i = 0; i < lines.length - _depth; i++) {
            int len = 0;
            for (int j = 0; j < _depth; j++) {
                len += lines[i + j].length();
            }
            // 还没有找到文章起始位置，需要判断起始位置
            if (startPos == -1) {
                // 如果上次查找的文本数量超过了限定字数，且当前行数字符数不为0，则认为是开始位置
                if (preTextLen > _limitCount && len > 0) {
                    // 查找文章起始位置, 如果向上查找，发现2行连续的空行则认为是头部
                    int emptyCount = 0;
                    for (int j = i - 1; j > 0; j--) {
                        if (StringUtils.isBlank(lines[j])) {
                            emptyCount++;
                        } else {
                            emptyCount = 0;
                        }
                        if (emptyCount == _headEmptyLines) {
                            startPos = j + _headEmptyLines;
                            break;
                        }
                    }
                    // 如果没有定位到文章头，则以当前查找位置作为文章头
                    if (startPos == -1) {
                        startPos = i;
                    }
                    //填充发现的文章起始部分
                    for (int j = startPos; j <= i; j++) {
                        sb.append(lines[j]);
                        orgSb.append(orgLines[j]);
                    }
                }
            } else {
                //if (len == 0 && preTextLen == 0)    // 当前长度为0，且上一个长度也为0，则认为已经结束
                // 当前长度为0，且上一个长度也为0，则认为已经结束
                if (len <= _endLimitCharCount && preTextLen < _endLimitCharCount) {
                    if (!_appendMode) {
                        //如果不是追加模式,退出查找
                        break;
                    }
                    startPos = -1;
                }
                sb.append(lines[i]);
                orgSb.append(orgLines[i]);
            }
            preTextLen = len;
        }

        String result = sb.toString();
        content = StringUtils.replacePattern(result, "[crlf]", System.getProperty("line.separator"));
        //TODO 将字符html格式化
        //content = System.Web.HttpUtility.HtmlDecode(content);
        contentWithTags = orgSb.toString();
        article.setContent(content);
        article.setContentWithTags(contentWithTags);
        return article;
    }

    /**
     * 格式化标签,剔除匹配标签中的回车符
     *
     * @param body html文本
     * @return 返回剔除后的文本
     */
    private static String formatTag(String body) {

        String regex = "(<[^<>]+)\\s*\\n\\s*";
//        String regex = "\r\n";
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(body);
        while (matcher.find()) {
            String group = matcher.group();
            if (StringUtils.contains(group, "\r") || StringUtils.contains(group, "\n")) {
                continue;
            }
            sb.append(group);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
       /* String url = "http://news.ifeng.com/a/20180807/59678113_0.shtml";
        String body = "";
        try{
            Connection.Response execute = Jsoup.connect(url).method(Connection.Method.GET).execute();
            body = execute.body();
        }catch (Exception e){
            LOG.error("",e);
        }*/
        Document doc = Jsoup.parse("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                          <tbody><tr>\n" +
                "                            <td><p>&nbsp;&nbsp;&nbsp; 春季是动物疫病多发季节，万合永镇政府结合动物防疫工作实际，采取有力措施，切实抓好口蹄疫、禽流感等重大动物疫病防治，控制重大动物疫病发生，确保畜牧业生产健康发展，打好春季动物防疫攻坚战。<br>\n" +
                "&nbsp;&nbsp;&nbsp; 一是精心安排组织，明确工作责任。镇政府及时组织召开春季动物防疫专题会议，全面安排部署全镇春季动物防疫工作，强化责任追究制度，做好督促检查工作，保证动物防疫工作的顺利完成。<br>\n" +
                "&nbsp;&nbsp;&nbsp; 二是完善防控措施，确保免疫密度。组织全镇防疫工作力量分组包片实行拉网式集中免疫，规范防疫行为，严格操作程序，坚持做到一畜一针，杜绝空针和飞针现象。同时，认真做好免疫档案登记和管理，适时查缺补漏，做到不漏一户、一畜、一禽，对集中免疫时不宜免疫的畜禽、新生及新补栏畜禽定期及时进行补免，确保禽畜免疫率100%。<br>\n" +
                "&nbsp;&nbsp;&nbsp; 三是加强宣传监测，提高预警能力。大力宣传和科普动物防疫知识和技术，增强群众对防疫工作重要性的认识和应急处理能力。同时，完善疫情报告网络体系，建立健全巡查制度和定点、定人、包片负责的疫情监测制度，随时掌握疫情动态。<br>\n" +
                "&nbsp;&nbsp;&nbsp; 目前，我镇口蹄疫、羊痘、禽流感、猪瘟、猪蓝耳病等疫（菌）苗已全面免疫接种，共防疫生猪5800口，羊10.8万只，牛2600头，禽类1.7万羽，全镇春季动物防疫工作已经全面完成。</p></td>\n" +
                "                          </tr>\n" +
                "                        </tbody></table>");
        Article article = getContent(doc.html());
//        Article article = getArticle(body);
        System.err.println(article.getTitle());
        System.out.println("---------------------------------------------");
        System.err.println(article.getPublishDate());
        System.out.println("---------------------------------------------");
        System.err.println(article.getContent());
        System.out.println("---------------------------------------------");
        System.err.println(article.getContentWithTags());
    }
}

