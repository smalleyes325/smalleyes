/*
 * 文件名：ReportCache
 * 版权：Copyright by www.baiqishi.com
 * 创建人：SmalleyesWong
 * 创建时间：2018/6/19
 */

package common;


import org.apache.commons.lang3.StringUtils;
import wang.smalleyes.sm.common.log.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
public final class ReportCache {
    private static final LogUtils LOG = LogUtils.newInstance(ReportCache.class);
    private static final String LOL = "lol";
    private static Map<String, List<String>> MOBILE_TAG_MAP =
            Collections.unmodifiableMap(new HashMap<String, List<String>>() {{
                put("热线电话", new ArrayList<>(
                        Arrays.asList("游戏平台", "网络游戏", "汽车品牌", "外卖热线", "酒店预订平台", "便民服务", "快递热线", "银行热线", "经济连锁酒店",
                                "高端连锁酒店", "租车服务", "企业热线", "保险热线", "报警求助", "证券热线", "运营商", "电商购物", "代驾服务", "投诉举报", "健康咨询",
                                "航空公司", "新闻媒体", "手游", "基金热线", "旅游热线", "物流热线", "网络宽带", "家用电器", "教育热线", "知名网站", "其他热线",
                                "手机数码", "打车服务")));
                put("结婚丽人", new ArrayList<>(
                        Arrays.asList("个体写真", "美容美发/spa", "瘦身纤体", "美甲", "其他结婚丽人", "婚纱摄影", "婚庆公司", "婚纱礼服", "彩妆造型",
                                "婚车租赁", "婚介中心", "婚庆用品")));
                put("工业制造", new ArrayList<>(
                        Arrays.asList("机械设备", "五金工具", "包装印刷", "电气设备", "电工照明", "电子器件", "汽修汽配", "其他工业品", "汽车制造", "非机动车",
                                "焊接切割", "网络通信", "建筑设备", "消防", "纸业", "广电设备", "环保", "包装", "安防", "水工业", "汽车用品", "太阳能",
                                "仪器仪表", "物流设备", "船舶", "管道", "工业耗材", "加工")));
                put("其他生活服务", new ArrayList<>(Collections.singletonList("其他生活服务")));
                put("休闲娱乐", new ArrayList<>(
                        Arrays.asList("KTV", "电影院", "酒吧", "咖啡厅", "茶馆", "剧场", "台球厅", "网吧", "棋牌桌游", "电玩城", "足疗按摩", "温泉洗浴",
                                "夜总会", "商务会所", "DIY手工坊", "其他休闲娱乐", "密室/轰趴馆", "游乐园")));
                put("消费品", new ArrayList<>(
                        Arrays.asList("服装内衣", "鞋包配饰", "家纺家居", "食品农业", "医疗制药", "其他消费品", "家电数码", "礼品", "古玩", "珠宝", "美容美发",
                                "制鞋", "体育用品", "影音设备", "酒店用品", "家具", "宠物相关", "皮具", "玩具", "保健品", "二手物品", "IT设备", "智能设备",
                                "办公用品", "文教用品")));
                put("原材料", new ArrayList<>(
                        Arrays.asList("化工", "橡胶", "冶金", "其他原材料", "建材", "卫浴", "陶瓷", "超硬材料", "塑料", "表面处理", "钢铁", "石油",
                                "房地产", "能源", "涂料", "石材", "玻璃", "丝网", "纺织", "皮革")));
                put("服务", new ArrayList<>(
                        Arrays.asList("建筑装饰", "其他服务", "电子通信", "旅游公司", "金融信贷", "IT互联网", "广告", "教育", "维修", "个人贷款", "展会",
                                "翻译", "物流运输", "管理咨询", "贸易", "批发零售")));
                put("医疗健康", new ArrayList<>(
                        Arrays.asList("体检中心", "口腔门诊", "中医院", "妇幼/儿童医院", "综合医院", "社区医院", "药店/诊所", "其他医疗机构", "专科医院",
                                "宠物医院", "口腔医院")));
                put("政府机关", new ArrayList<>(
                        Arrays.asList("人民政府", "派出所/公安局", "工商税务", "行政部门", "劳动保障部门", "水利供电部门", "图书/档案馆", "司法部门", "财政部门",
                                "人才市场", "其他政府机关", "慈善福利机构", "卫生部门", "国际组织", "国务院", "居委会")));
                put("教育培训", new ArrayList<>(
                        Arrays.asList("幼儿园", "小学", "中学", "家教", "职业教育", "大学", "早教中心", "艺术培训", "成人教育", "留学/出国", "外语培训",
                                "其他教育机构")));
                put("旅游出行", new ArrayList<>(
                        Arrays.asList("售票点", "景点/公园", "客运站", "机场航空", "展览/博物馆", "采摘园", "户外拓展", "火车站", "其他旅游出行")));
                put("日常生活", new ArrayList<>(
                        Arrays.asList("公证处", "宠物", "数码快印", "干洗店", "物业", "房屋地产", "彩票点", "殡葬墓地", "律师事务所", "生活配送",
                                "小区/写字楼", "其他日常生活", "装饰/装修", "水电燃气", "电信宽带", "报刊杂志", "快递物流", "电视广播", "亲子")));
                put("汽车服务", new ArrayList<>(
                        Arrays.asList("驾校", "维修保养", "汽车租赁", "加油站", "4S店/汽车销售", "机动车检测场", "配件/车饰", "停车场", "其他汽车服务",
                                "摩托车/非机动车", "焊接切割")));
                put("家政服务", new ArrayList<>(Arrays.asList("搬家", "其他家政服务", "月嫂", "保姆", "保洁", "小时工", "家电/家居维修", "管道疏通")));
                put("社会团体", new ArrayList<>(Arrays.asList("民主党派", "社团/联合会", "工会/协会", "基金会", "宗教团体", "其他社会团体")));
                put("购物", new ArrayList<>(
                        Arrays.asList("购物中心", "食品茶酒", "超市/便利店", "服饰鞋包", "珠宝饰品", "化妆品", "运动户外", "母婴儿童", "品牌折扣店", "数码家电",
                                "焊接切割", "家居建材", "特色集市", "书店", "钟表眼镜", "花店", "乐器", "批发/旧货市场", "水产生鲜", "品牌售后",
                                "其他购物服务")));
                put("运动健身", new ArrayList<>(
                        Arrays.asList("健身房", "游泳馆", "瑜伽/舞蹈", "羽毛球馆", "乒乓球馆", "网球场", "篮球场", "足球场", "高尔夫场", "保龄球馆",
                                "武术场馆", "体育场馆", "溜冰场", "滑雪场", "其他运动场馆")));
                put("金融银行", new ArrayList<>(Arrays.asList("保险", "证券基金", "典当拍卖", "银行", "其他金融", "投资理财")));
                put("餐饮美食", new ArrayList<>(
                        Arrays.asList("小吃快餐", "京津菜", "鲁菜", "川菜", "湘菜", "湖北菜", "江浙菜", "粤菜", "东北菜", "清真菜", "西北菜", "贵州菜",
                                "上海菜", "徽菜", "云南菜", "闽菜", "西藏菜", "台湾菜", "西餐", "素菜", "火锅", "海鲜", "日韩料理", "东南亚菜", "自助餐",
                                "面包甜点", "家常菜", "甜品饮料", "饺子馆", "粉面馆", "其他美食", "山西菜", "江西菜", "河南菜", "海南菜", "广西菜",
                                "烧烤烤肉")));
                put("酒店", new ArrayList<>(
                        Arrays.asList("经济型酒店", "星级酒店", "公寓式酒店", "度假村", "农家院/客栈", "青年旅舍", "旅行社", "其他酒店", "招待所")));
                put("本地热线", new ArrayList<>(Arrays.asList("市民生活热线", "监督投诉热线", "旅游交通热线", "电视新闻热线", "金融保险热线", "其他本地热线")));
                put("农业", new ArrayList<>(
                        Arrays.asList("农业机械", "农业化工", "水果", "养殖", "食品机械", "食品", "水产养殖", "园林", "种子", "饲料", "蔬菜", "茶叶",
                                "烟草", "其他农业")));
                put("反查", new ArrayList<>(
                        Arrays.asList("电话反查")));
                put("其他企业", new ArrayList<>(
                        Arrays.asList("其他企业")));
            }});

    public static Map<String, List<String>> getMobileTagMap() {
        return MOBILE_TAG_MAP;
    }

    public static String getMobileTag(String tagName) {
        long startTime = System.currentTimeMillis();
        try {
            if (StringUtils.isBlank(tagName)) {
                return "未知";
            }
            if (MOBILE_TAG_MAP.size() <= 0) {
                return "未知";
            }
            if (MOBILE_TAG_MAP.containsKey(tagName)) {
                return tagName;
            }
            for (Map.Entry<String, List<String>> entry : MOBILE_TAG_MAP.entrySet()) {
                List<String> values = entry.getValue();
                if (values != null && values.size() > 0) {
                    if (values.contains(tagName)) {
                        return entry.getKey();
                    }
                }
            }
        } finally {
            LOG.info(String.format("号码标签分类查询耗时:%s ms", System.currentTimeMillis() - startTime));
        }
        return "未知";
    }
}

