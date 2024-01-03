package com.csm.zuo.dp.leetcode;

import java.util.Arrays;

/**
 * LC1143. 最长公共子序列
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
 * <p>
 * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 * <p>
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
 * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：text1 = "abcde", text2 = "ace"
 * 输出：3
 * 解释：最长公共子序列是 "ace" ，它的长度为 3 。
 * 示例 2：
 * <p>
 * 输入：text1 = "abc", text2 = "abc"
 * 输出：3
 * 解释：最长公共子序列是 "abc" ，它的长度为 3 。
 * 示例 3：
 * <p>
 * 输入：text1 = "abc", text2 = "def"
 * 输出：0
 * 解释：两个字符串没有公共子序列，返回 0 。
 */
public class LongestCommonSubsequence {

    /**
     * 暴力递归版本
     *
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return 0;
        }
        char[] s1 = text1.toCharArray();
        char[] s2 = text2.toCharArray();
        return f(s1, s2, s1.length, s2.length);
    }

    /**
     * 怎么去写递归呢？首先分析能不能拆分成子问题
     * 求两个串的最长公共子序列  s1[0 1 . . . . i] s2[0 1 . . . . j]
     * 从最后位置开始考虑：
     * 两个串的最后一个字符
     * 如果s1[i]==s2[j]
     * 那么最长的公共子序列串 来自于1 + s1[0...i-1]和s2[0...j-1]的最长公共子序列
     * 如果s1[i]!=s2[j]
     * 那么最长的公共子序列串 来自 s1[0...i-1]和s2[0...j] 或者 s1[0...i] s2[0...j-1]中最长的
     * 递归的含义 len1和len2分别是两个数组的长度
     *
     * @param s1
     * @param s2
     * @param len1
     * @param len2
     * @return
     */
    public int f(char[] s1, char[] s2, int len1, int len2) {
        if (len1 == 0 || len2 == 0) {
            return 0;
        }
        int ans;
        if (s1[len1 - 1] == s2[len2 - 1]) {
            ans = f(s1, s2, len1 - 1, len2 - 1) + 1;
        } else {
            ans = Math.max(f(s1, s2, len1 - 1, len2), f(s1, s2, len1, len2 - 1));
        }
        return ans;
    }

    /**
     * 暴力递归 改 记忆化搜索
     * 可以看到每个递归的状态都由 len1 和 len2 两个可变参数决定，
     * 并且一旦 len1 和 len2确定了，这次的结果就确定了，就不会影响下一次递归结果，所以dp表就确定
     * 因为是两个可变参数可以确定最终的结果，所以定义dp[][]
     */
    public int longestCommonSubsequence2(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return 0;
        }
        char[] s1 = text1.toCharArray();
        char[] s2 = text2.toCharArray();
        int m = s1.length;
        int n = s2.length;
        //dp[i][j]: 表示s1中前缀长度为i 和 s2中前缀长度为j 的公共子序列的长度
        int[][] dp = new int[m + 1][n + 1];
        //初始化dp表
        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                dp[i][j] = -1;
            }
        }
        dp[0][0] = 0;
        return f2(s1, s2, s1.length, s2.length, dp);
    }

    public int f2(char[] s1, char[] s2, int len1, int len2, int[][] dp) {
        if (len1 == 0 || len2 == 0) {
            return 0;
        }
        if (dp[len1][len2] != -1) {
            return dp[len1][len2];
        }
        int ans;
        if (s1[len1 - 1] == s2[len2 - 1]) {
            ans = f2(s1, s2, len1 - 1, len2 - 1, dp) + 1;
        } else {
            ans = Math.max(f2(s1, s2, len1 - 1, len2, dp), f2(s1, s2, len1, len2 - 1, dp));
        }
        dp[len1][len2] = ans;
        return ans;
    }

    /**
     * 方法三：自顶向下搜索 改 严格位置依赖版本动态规划
     */
    public int longestCommonSubsequence3(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return 0;
        }
        char[] s1 = text1.toCharArray();
        char[] s2 = text2.toCharArray();
        int m = s1.length;
        int n = s2.length;
         //dp[i][j]: 表示s1中前缀长度为i 和 s2中前缀长度为j 的公共子序列的长度
        int[][] dp = new int[m + 1][n + 1];
        //填表
        /**
         * 分析dp[][]的严格依赖关系
         * if s1[len1 - 1]==s2[len2 - 1],那么dp[len1][len2]依赖于dp[len-1][len2-1] 再 +1
         * if s[len1 - 1]!=s2[len2 - 1], 那么dp[len1][len2]依赖于max(dp[len1][len2 - 1],dp[len1 - 1][len2])
         * 特殊边界 if len1 == 0 || len2 == 0 , dp[len1][0] == 0 ,dp[0][len2] == 0
         */
        for (int i = 0; i < m; i++) {
            dp[i][0] = 0;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 0;
        }
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                dp[i][j] = s1[i - 1] == s2[j - 1] ? dp[i - 1][j - 1] + 1 : Math.max(dp[i][j - 1], dp[i - 1][j]);
            }
        }
        return dp[m][n];
    }

    /**
     * 方法四 ： 严格位置依赖 改 空间压缩 （滚动更新）
     */
    public int longestCommonSubsequence4(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return 0;
        }
        char[] s1, s2;
        //s1和s2的长度不一样，选择短的作为dp[]数组，可以节省更多的空间
        if (text1.length() > text2.length()) {
            s1 = text1.toCharArray();
            s2 = text2.toCharArray();
        } else {
            s1 = text2.toCharArray();
            s2 = text1.toCharArray();
        }
        int m = s1.length;
        int n = s2.length;
        /**
         * 分析dp[][]的严格依赖关系
         * if s1[len1 - 1]==s2[len2 - 1],那么dp[len1][len2]依赖于dp[len1 - 1][len2 - 1] 再 +1
         * if s[len1 - 1]!=s2[len2 - 1], 那么dp[len1][len2]依赖于max(dp[len1][len2 - 1],dp[len1 - 1][len2])
         * 特殊边界 if len1 == 0 || len2 == 0 , dp[len1][0] == 0 ,dp[0][len2] == 0
         */
        //根据二维dp[][]的依赖分析
        //假设存在一个想象中的二维表 m行 * n列
        // 当前的格子 要么依赖于 左上角的格子 + 1 ，要么依赖于 左边格子 和上边格子 最大的 那个
        int[] dp = new int[n + 1];
        //第0行的情况
        //初始化就是0
//        Arrays.fill(dp, 0);
        for (int i = 1; i <= m; i++) {
            int leftUp = dp[0];
            for (int j = 1; j <= n; j++) {
                // 使用一个临时变量保存当前 dp[j] 的值
                int temp = dp[j];
                dp[j] = s1[i - 1] == s2[j - 1] ? dp[j] = leftUp + 1 : Math.max(dp[j - 1], dp[j]);
                //更新prev为当前dp[j]的置
                leftUp = temp;
            }

        }
        return dp[n];
    }


    public static void main(String[] args) {
        String text1 = "abcde";
        String text2 = "ace";
        System.out.println(new LongestCommonSubsequence().longestCommonSubsequence4(text1, text2));
    }
}
