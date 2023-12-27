package com.csm.zuo.dp.leetcode;

import java.util.Arrays;

/**
 * LC91. 解码方法I
 * 一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
 * <p>
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 * 要 解码 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）。例如，"11106" 可以映射为：
 * <p>
 * "AAJF" ，将消息分组为 (1 1 10 6)
 * "KJF" ，将消息分组为 (11 10 6)
 * 注意，消息不能分组为  (1 11 06) ，因为 "06" 不能映射为 "F" ，这是由于 "6" 和 "06" 在映射中并不等价。
 * <p>
 * 给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。
 * <p>
 * 题目数据保证答案肯定是一个 32 位 的整数。
 */
public class NumDecodingsI {
    public int numDecodings(String s) {
        return recursion(s.toCharArray(), 0);
    }

    /**
     * 暴力递归:
     * 假设0...i-1位置都已经解码了，看i位置
     *
     * @param s 数组
     * @param i 来到i位置
     * @return 向上返回当前i的结果
     */
    public static int recursion(char[] s, int i) {
        //base case当i来到数组结束时，一种方式已经确定
        if (i == s.length) {
            return 1;
        }
        int ans;
        //i为0时不可以单独作为字符，也不能06这样作为前缀和其他数字组和
        if (s[i] == '0') {
            ans = 0;
        } else {
            //i自己作为单独作为一个字符
            ans = recursion(s, i + 1);
            //当i+1没有越界，且i和i+1组合作为一个字符
            if (i + 1 < s.length && ((s[i] - '0') * 10 + s[i + 1] - '0') <= 26) {
                ans += recursion(s, i + 2);
            }
        }
        //返回上一级调用
        return ans;
    }

    /**
     * 方法二：改动态规划记忆法搜索
     * 从顶到底的动态规划
     */

    public static int numDecodings2(String s) {
        //初始化缓存数组
        int[] dp = new int[s.length()];
        Arrays.fill(dp, -1);
        return recursion2(s.toCharArray(), 0, dp);
    }

    /**
     * @param s
     * @param i
     * @param dp
     * @return
     */
    public static int recursion2(char[] s, int i, int[] dp) {
        if (i == s.length) {
            return 1;
        }
        //如果缓存数组中有，就从缓存数组中拿数据
        if (dp[i] != -1) {
            return dp[i];
        }
        //缓存中没有，计算后更新缓存
        int ans;
        if (s[i] == '0') {
            ans = 0;
        } else {
            ans = recursion2(s, i + 1, dp);
            if (i + 1 < s.length && ((s[i] - '0') * 10 + s[i + 1] - '0') <= 26) {
                ans += recursion2(s, i + 2, dp);
            }
        }
        dp[i] = ans;//更新缓存
        return ans;
    }

    /**
     * 严格位置依赖的动态规划
     *
     * @param str
     * @return
     */
    public static int numDecodings3(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[] dp = new int[n + 1];//比s.length多1
        //当i来当n位置(s.length)了，就确定一种方案
        dp[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (s[i] == '0') {
                dp[i] = 0;
            } else {
                dp[i] = dp[i + 1];
                if (i + 1 < s.length && ((s[i] - '0') * 10 + s[i + 1] - '0') <= 26) {
                    dp[i] += dp[i + 2];
                }
            }

        }
        return dp[0];
    }

    /**
     * 严格位置依赖的动态规划 + 空间压缩 滚动更新
     *
     * @param str
     * @return
     */
    public static int numDecodings4(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int next = 1;   //n 位置
        int nextNext = 0;   //n+1位置
        for (int i = n - 1, cur; i >= 0; i--) {
            if (s[i] == '0') {
                cur = 0;
            } else {
                cur = next;
                if (i + 1 < s.length && ((s[i] - '0') * 10 + s[i + 1] - '0') <= 26) {
                    cur += nextNext;
                }
            }
            nextNext = next;
            next = cur;

        }
        //最后i来到0位置,cur就是从0位置开始计算
        return next;
    }


}
