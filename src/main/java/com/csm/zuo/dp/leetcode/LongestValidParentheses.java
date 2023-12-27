package com.csm.zuo.dp.leetcode;

/**
 * LC 32.最长有效括号
 *给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 *
 *
 *
 * 示例 1：
 *
 * 输入：s = "(()"
 * 输出：2
 * 解释：最长有效括号子串是 "()"
 * 示例 2：
 *
 * 输入：s = ")()())"
 * 输出：4
 * 解释：最长有效括号子串是 "()()"
 * 示例 3：
 *
 * 输入：s = ""
 * 输出：0
 *
 */
public class LongestValidParentheses {
    public int longestValidParentheses(String str) {
        char[] s = str.toCharArray();
        //dp[0...n-1]
        //dp[i] ：字串必须以i位置的字符结尾的情况下，往左最多推多远能整体有效
        int[] dp = new int[s.length];//初始化数组默认为0
        int ans = 0;
        //当i在0位置时肯定dp[0]==0
        for (int i = 1, p; i < s.length; i++) {
            if (s[i] == ')') {// "("左括号没办法和前面的串匹配，是0，正好dp[]的初始化是0，只用管i是")"的情况
                //    ?          )
                //    p          i
                p = i - dp[i - 1] - 1;
                if (p >= 0 && s[p] == '(') {//只有p没越界，并且p是"("才能和i位置匹配有效，否则i位置dp[i]==0
                    dp[i] = dp[i - 1] + 2 + (p - 1 >= 0 ? dp[p - 1] : 0);//如果p-1的位置没越界，还需要把p-1的位置的最长有效括号长度dp[p-1]加上
                }
                ans=Math.max(ans,dp[i]);//同时在循环中更新最长的有小括号长度
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String str1="()()()()))(((()))()()()())())()()(()()()(";
        String str2="()()((((((((((())))))))))))(())())()()(()()()()";
        String str3="()()((((((((((())))))))))))()()()(())))((())()))";
        System.out.println(new LongestValidParentheses().longestValidParentheses(str1));
        System.out.println(new LongestValidParentheses().longestValidParentheses(str2));
        System.out.println(new LongestValidParentheses().longestValidParentheses(str3));
    }
}
