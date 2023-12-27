package com.csm.zuo.dp.leetcode;
//todo 了解同余原理
/**
 * LC940.不同的子序列 II
 * 给定一个字符串 s，计算 s 的 不同非空子序列 的个数。因为结果可能很大，所以返回答案需要对 10^9 + 7 取余 。
 * <p>
 * 字符串的 子序列 是经由原字符串删除一些（也可能不删除）字符但不改变剩余字符相对位置的一个新字符串。
 * <p>
 * 例如，"ace" 是 "abcde" 的一个子序列，但 "aec" 不是。
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "abc"
 * 输出：7
 * 解释：7 个不同的子序列分别是 "a", "b", "c", "ab", "ac", "bc", 以及 "abc"。
 * 示例 2：
 * <p>
 * 输入：s = "aba"
 * 输出：6
 * 解释：6 个不同的子序列分别是 "a", "b", "ab", "ba", "aa" 以及 "aba"。
 * 示例 3：
 * <p>
 * 输入：s = "aaa"
 * 输出：3
 * 解释：3 个不同的子序列分别是 "a", "aa" 以及 "aaa"。
 */
public class DistinctSubSeqII {
    //时间复杂度O(n)
    public int distinctSubseqII(String s) {

        if (s == null || s.length() == 0) {
            return 0;
        }
        int mod = 1000000007;
        char[] str = s.toCharArray();
        int[] cnt = new int[26];
        /**
         * 假设一开始有一个空集 {} all=1
         */
        int all = 1, newAdd;
        for (char x : str) {
            //去重
            //假设x之前都已经计算了字串，现在要在之前的字串上附加上当前x有 当前的总记录数 - 当前字符原有的记录数cnt[x-'a']，表示在附加当前x的新增newAdd
            newAdd = (all - cnt[x - 'a'] + mod) % mod;
            //把新增的更新到当前字符结尾的记录上
            cnt[x - 'a'] = (cnt[x - 'a'] + newAdd) % mod;
            //更新总的记录
            all = (all + newAdd) % mod;
        }
        //最后的结果需要减去空集
        return (all - 1 + mod) % mod;
    }
}
