package com.tcs.household.codeUtils.utils;

import java.util.Random;

/**
 * @author lushaozhong
 * @version 1.0
 * @date 2018-05-07 18:04
 */
public class RandomCodeCreateUtil {
    private static final char[] code = new char[]{'W', '8', 'D', 'Z', 'E', 'X', '9', 'P', 'Q', '5', 'K', 'S', 'L', '3', 'M', 'J', '7', 'U', 'F', 'R', '4', 'C', 'V', 'Y', 'N', '6', 'B', 'G', '2', 'H', 'T'};
    /** 定义一个字符用来补全邀请码长度（该字符前面是计算出来的邀请码，后面是用来补全用的） */
    private static final char comCode = 'A';

    /** 进制长度 */
    private static final int binLen = code.length;

    /** 邀请码长度 */
    private static final int randomCodeLength = 6;

    /**
     * 根据id生成6位由大写字母和数字组成的6位随机码(id超过887503680会出现7位及以上)
     * @param id
     * @return
     */
    public static String toRandomCode(long id) {
        char[] buf = new char[binLen];
        int charPos = binLen;

        while((id / binLen) > 0) {
            int ind = (int)(id % binLen);
            buf[--charPos] = code[ind];
            id /= binLen;
        }
        buf[--charPos] = code[(int)(id % binLen)];
        String str = new String(buf, charPos, (binLen - charPos));
        // 不够长度的自动随机补全
        if(str.length() < randomCodeLength) {
            StringBuilder sb=new StringBuilder();
            sb.append(comCode);
            Random rnd = new Random();
            for(int i=1; i < randomCodeLength - str.length(); i++) {
                sb.append(code[rnd.nextInt(binLen)]);
            }
            str += sb.toString();
        }
        return str;
    }

    /**
     * 将随机码解码成id
     * @param randomCode
     * @return
     */
    public static long randomCodeToId(String randomCode) {
        char chs[] = randomCode.toCharArray();
        long res = 0L;
        for (int i = 0; i < chs.length; i++) {
            int ind = 0;
            for (int j = 0; j < binLen; j++) {
                if (chs[i] == code[j]) {
                    ind = j;
                    break;
                }
            }
            if (chs[i] == comCode) {
                break;
            }
            if (i > 0) {
                res = res * binLen + ind;
            } else {
                res = ind;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String randomCode = toRandomCode(887503680);
        System.out.println(randomCode);
        System.out.println(31L*31L*31L*31L*31L*31L);
        long id = randomCodeToId(randomCode);
        System.out.println(id);
        /*for (int i = 0; i < 10; i++) {
            String randomCode = toRandomCode(i);
            System.out.println(randomCode);
            long id = randomCodeToId(randomCode);
            System.out.println(id);
        }*/
    }
}
