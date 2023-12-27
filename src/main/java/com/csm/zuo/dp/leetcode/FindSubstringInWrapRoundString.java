package com.csm.zuo.dp.leetcode;

/**
 * LC467. 环绕字符串中唯一的子字符串
 * 定义字符串 base 为一个 "abcdefghijklmnopqrstuvwxyz" 无限环绕的字符串，所以 base 看起来是这样的：
 * <p>
 * "...zabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd....".
 * 给你一个字符串 s ，请你统计并返回 s 中有多少 不同非空子串 也在 base 中出现。
 */
public class FindSubstringInWrapRoundString {
    //时间复杂度O（N），n是字符串s的长度，字符串base的长度是正无穷
    public int findSubstringInWraproundString(String str) {
        int n = str.length();
        int[] s = new int[n];
        //abcdef...z -> 0,1,2,3,4,5....,z ，a ~ z对应 0 ~ 25
        for (int i = 0; i < n; i++) {
            s[i] = str.charAt(i) - 'a';
        }
        //dp[0]: s中必须以'a'结尾的字串，最大延伸长度是多少，延伸一定要根据base串的规则
        int[] dp = new int[26];
        //s: c d e ...
        //   2 3 4
        dp[s[0]] = 1;//s[0]就是c，第一个字符c作为结尾，没办法往前延伸了
        //i=0时，dp[s[0]]已经计算过了，cur表示当前字符对应的数字，pre表示前一个字符对应的数字，len每个字符作为字串的最短长度都是1
        for (int i = 1, cur, pre, len = 1; i < n; i++) {
            cur = s[i];
            pre = s[i - 1];
            //判断前一个字母和后一个字母是否延续可以pre + 1 == cur ，特殊情况"za"也是连续 pre == 25 && cur == 0
            //连续的情况下
            if ((pre == 25 && cur == 0) || pre + 1 == cur) {
                len++;
            } else {//不连续
                len = 1;
            }
            //"zapxyzabc"  第一次dp["a"]==2 ,第二次dp["a"]==4,如果有更大的延伸，就更新当前dp[cur]的值
            dp[cur] = Math.max(dp[cur], len);
        }
        //根据题目要求累计dp表中的值返回
        int ans = 0;
        for (int i = 0; i < dp.length; i++) {
            ans += dp[i];
        }
        return ans;
    }
}
