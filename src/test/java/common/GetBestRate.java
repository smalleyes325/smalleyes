/*
 * 文件名：GetBestRate
 * 版权：Copyright by www.baiqishi.com
 * 创建人：SmalleyesWong
 * 创建时间：2018/7/9
 */

package common;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈获取最优解〉
 * <p>〈功能详细描述〉</p>
 *
 * @author SmalleyesWong
 * @version 2018/7/9
 * @since
 */
public class GetBestRate {

    //总数
    private static final int tutou = 1;

    private static final int tujiao = 4;

    private static final int jitou = 1;

    private static  final int jijiao =2;

    public static void main(String[] args) {
        /*List<String> answer1 = getAnswer1(100000, 255000);
        System.err.println(answer1);*/
        long startTime = System.currentTimeMillis();
        System.err.println(plus(3,9));
        System.err.println(String.format("计算耗时:%s ms",System.currentTimeMillis() - startTime));
    }

    public static int plus(int a,int b){
        if(b==0){
            return a;
        }
        int sum = a^b;
        int carry =(a&b)<<1;
        return plus(sum,carry);
    }




    public static List<String> getAnswer1(int tou, int jiao){
        long startTime = System.currentTimeMillis();
        List<String> answers = new ArrayList<>();

        for(int tu = 0; tu <= tou/jitou; tu ++){
            for(int ji = 0;ji <= (tou - tu);ji++){
                if(checkAnswer(tu,ji,tou,jiao)){
                    answers.add("ji:"+ji+"---tu:"+tu);
                    break;
                }
            }
        }
        System.err.println(String.format("计算耗时:%s ms",System.currentTimeMillis() - startTime));
        return answers;
    }

    public static List<String> getAnswer2(int tou, int jiao){
        long startTime = System.currentTimeMillis();
        List<String> answers = new ArrayList<>();

        //头是固定的 都是1个
        int center = tou / 2;
        for(int tu = center;tu <= tou; tu ++){
            for(int ji = 0;ji <= (tou - tu);ji++){
                if(checkAnswer(tu,ji,tou,jiao)){
                    answers.add("ji:"+ji+"---tu:"+tu);
                    break;
                }
            }
        }
        for(int tu = center;tu >= 0; tu --){
            for(int ji = 0;ji <= (tou - tu);ji++){
                if(checkAnswer(tu,ji,tou,jiao)){
                    answers.add("ji:"+ji+"---tu:"+tu);
                    break;
                }
            }
        }

        System.err.println(String.format("计算耗时:%s ms",System.currentTimeMillis() - startTime));
        return answers;
    }

    private static boolean checkAnswer(int tu, int ji, int tou, int jiao) {
        return (tu * tutou + ji * jitou) == tou && (tu * tujiao + ji * jijiao) == jiao;
    }

}

