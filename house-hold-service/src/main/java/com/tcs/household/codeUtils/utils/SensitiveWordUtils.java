package com.tcs.household.codeUtils.utils;


import com.tcs.household.codeUtils.enums.SensitiveWordFilterEnum;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;

import java.util.*;

/**
 * @author lushaozhong
 * @version 1.0
 * @date 2017-11-07 13:41
 */
public class SensitiveWordUtils {
    public static final String IS_END = "isEnd";
    public static final String END_TRUE = "1";
    public static final String END_FALSE = "0";
    public static final String TOTAL = "total";
    public static final String COMMA_SEPARATOR = ",";
    public static final HashMap<String, Map<String, String>> SENSITIVE_WORD_MAP = new HashMap<>();

    /**
     * 敏感词map初始化
     * @param keyWordSet 敏感词列表
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<String, String> addSensitiveWordToHashMap(Set<String> keyWordSet) {
        Map<String, String> sensitiveWordMap = new HashMap(keyWordSet.size());
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            key = iterator.next();
            nowMap = sensitiveWordMap;
            for(int i = 0 ; i < key.length() ; i++){
                char keyChar = key.charAt(i);
                Object wordMap = nowMap.get(keyChar);

                if(wordMap != null){
                    //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                }else{
                    //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap();
                    newWorMap.put(IS_END, END_FALSE);
                    //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if(i == key.length() - 1){
                    nowMap.put(IS_END, END_TRUE);
                    //最后一个
                }
            }
        }
        return sensitiveWordMap;
    }


    /**
     * 敏感词匹配
     * @param txt 需要校验的字符串
     * @param matchType 匹配类型
     * @return
     */
    @SuppressWarnings({ "rawtypes"})
    public static int checkSensitiveWord(String txt, SensitiveWordFilterEnum matchType, Map<String, String> sensitiveWordMap){
        boolean  flag = false;
        //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;
        int useMapFlag = 0;
        //匹配标识数默认为0
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for(int i = 0; i < txt.length() ; i++){
            if (useMapFlag == 0 || nowMap == null) {
                nowMap = sensitiveWordMap;
            }
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);
            //获取指定key
            if(nowMap != null){
                //存在，则判断是否为最后一个
                useMapFlag++;
                //找到相应key，匹配标识+1
                if("1".equals(nowMap.get(IS_END))){
                    //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;
                    matchFlag++;
                    useMapFlag = 0;
                    //结束标志位为true
                    if(SensitiveWordFilterEnum.MIN_MATCH_TYPE == matchType){
                        //最小规则，直接返回,最大规则还需继续查找
                        break;
                    }
                }
            }
            else{
                //未完整匹配的敏感词, 则进行回退
                if (useMapFlag > 0 && (i - useMapFlag) >= 0) {
                    i = i - useMapFlag;
                    useMapFlag = 0;
                }
                continue;
            }
        }
        if(matchFlag < 1 && !flag){
            matchFlag = 0;
        }
        return matchFlag;
    }

    /**
     * 敏感词匹配
     * @param txt 需要校验的字符串
     * @param sensitiveWordMap 结构化敏感词库
     * @return
     */
    @SuppressWarnings({ "rawtypes"})
    public static Map<String, Integer> checkSensitiveWordCount(String txt, Map<String, String> sensitiveWordMap){
        Map<String, Integer> returnMap = new HashedMap();
        boolean  flag = false;
        //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;
        int useMapFlag = 0;
        StringBuffer sensitiveWord = new StringBuffer();
        //匹配标识数默认为0
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for(int i = 0; i < txt.length() ; i++){
            if (useMapFlag == 0 || nowMap == null) {
                nowMap = sensitiveWordMap;
            }
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);
            //获取指定key
            if(nowMap != null){
                sensitiveWord.append(word);
                //存在，则判断是否为最后一个
                useMapFlag++;
                //找到相应key，匹配标识+1
                if("1".equals(nowMap.get(IS_END))){
                    //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;
                    matchFlag++;
                    useMapFlag = 0;
                    Integer matchCount = returnMap.get(sensitiveWord.toString());
                    if (matchCount == null) {
                        matchCount = 0;
                    }
                    returnMap.put(sensitiveWord.toString(), ++matchCount);
                    sensitiveWord.setLength(0);
                }
            } else {
                //未完整匹配的敏感词, 则进行回退
                if (useMapFlag > 0 && (i - useMapFlag) >= 0) {
                    i = i - useMapFlag;
                    useMapFlag = 0;
                    sensitiveWord.setLength(0);
                }
                continue;
            }
        }
        if(matchFlag < 1 && !flag){
            matchFlag = 0;
        }
        returnMap.put(TOTAL, matchFlag);
        return returnMap;
    }


    public static void main(String[] args) {
        Set<String> wordSet = new HashSet<>();
        wordSet.add("警");
        wordSet.add("法院");
        wordSet.add("骗");
        wordSet.add("富士康");
        wordSet.add("专线");
        wordSet.add("贷");
        if (MapUtils.isEmpty(SensitiveWordUtils.SENSITIVE_WORD_MAP.get("敏感"))) {
            Map<String, String> sensitiveWordMap = SensitiveWordUtils.addSensitiveWordToHashMap(wordSet);
            SensitiveWordUtils.SENSITIVE_WORD_MAP.put("敏感", sensitiveWordMap);
        }
        System.out.println("敏感词的数量：" + wordSet.size());
        String string = "毛东骗字难贷过就躺在富士康某一富士康个人的怀里富士康贷贷贷富士康贷富士康贷尽情的警述心扉或贷者手机卡复贷制器一个人一杯红酒一部法院电影在夜贷毛骗 深人静的晚上，关上电话静静的发呆着。";
        System.out.println("待检测语句字数：" + string.length());
        long beginTime = System.currentTimeMillis();
        int cot = 0;
        Map<String, Integer> returnMap = SensitiveWordUtils.checkSensitiveWordCount(string, SensitiveWordUtils.SENSITIVE_WORD_MAP.get("敏感"));
        long endTime = System.currentTimeMillis();
        System.out.println("语句中包含敏感词的个数为：" + returnMap.get(TOTAL) + "。");
        System.out.println("总共消耗时间为：" + (endTime - beginTime) + "毫秒");
    }
}
