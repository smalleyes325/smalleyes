/*
 * 文件名：CountCity
 * 版权：Copyright by www.baiqishi.com
 * 创建人：SmalleyesWong
 * 创建时间：2018/7/6
 */

package common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import wang.smalleyes.sm.common.util.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * <p>〈功能详细描述〉</p>
 *
 * @author SmalleyesWong
 * @version 2018/7/6
 * @since
 */
public class CountCity {

    @Test
    public void test(){
        Map<String,List<String>> cityMap = new HashMap<>();
        String read = FileUtils.read("E:\\Project\\smalleyes\\smwechat\\src\\test\\resources\\city.json");
        JSONObject jsonObject = JSON.parseObject(read);
        JSONArray data = jsonObject.getJSONArray("data");

        for(Object obj : data){
            JSONObject obj1 = (JSONObject) obj;
            String name = obj1.getString("name");
            List<String> strings = cityMap.get(name);
            if(strings == null){
                strings = new ArrayList<>();
                cityMap.put(name,strings);
            }

            JSONArray cities = obj1.getJSONArray("cities");
            for (Object city :cities){
                JSONObject cityObject = (JSONObject)city;
                String name1 = cityObject.getString("name");
                List<String> strings1 = cityMap.get(name);
                if(strings1 == null){
                    strings = new ArrayList<>();
                    cityMap.put(name,strings);
                }
                strings1.add(name1);
            }
        }

        for(Map.Entry<String,List<String>> entry : cityMap.entrySet()){
            System.err.print(entry.getKey() + " : ");
            List<String> value = entry.getValue();
            for(String string : value){
                System.err.print(string + "、");
            }
            System.err.println(" ");
        }
    }
}

