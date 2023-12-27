package com.csm.zuo.dp.leetcode;

/**
 * LC264. 丑数 II
 * 给你一个整数 n ，请你找出并返回第 n 个 丑数 。
 * <p>
 * 丑数 就是质因子只包含 2、3 和 5 的正整数。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：n = 10
 * 输出：12
 * 解释：[1, 2, 3, 4, 5, 6, 8, 9, 10, 12] 是由前 10 个丑数组成的序列。
 * 示例 2：
 * <p>
 * 输入：n = 1
 * 输出：1
 * 解释：1 通常被视为丑数。
 */
public class NthUglyNumberII {
    /**
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        //存放丑数的dp表
        //dp[0]位置不作记录
        //从dp[1]开始一直填满到dp[n]
        int[] dp = new int[n + 1];
        dp[1] = 1;
        //i2,i3,i5分别是是指向*2，*3，*5的丑数的指针
        for (int i = 2, i2 = 1, i3 = 1, i5 = 1, a, b, c, cur; i <= n; i++) {
            //根据指针求当前丑数
            a = dp[i2] * 2;
            b = dp[i3] * 3;
            c = dp[i5] * 5;
            cur = Math.min(Math.min(a, b), c);//当前的丑数是三者中最小的
            /**
             * 计算出当前丑数后，如果三个指针所指向的丑数（这种可能已经过期了）就应该更新指针
             */
            if (cur == a) {
                i2++;
            }
            if (cur == b) {
                i3++;
            }
            if (cur == c) {
                i5++;
            }
            //在缓存表中填入当前丑数
            dp[i] = cur;
        }
        return dp[n];
    }
}
