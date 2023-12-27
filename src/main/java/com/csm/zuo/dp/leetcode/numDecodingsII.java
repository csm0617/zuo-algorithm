package com.csm.zuo.dp.leetcode;

import java.util.Arrays;

/**
 * LC639. 解码方法II
 * 一条包含字母 A-Z 的消息通过以下的方式进行了 编码 ：
 * <p>
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 * 要 解码 一条已编码的消息，所有的数字都必须分组，然后按原来的编码方案反向映射回字母（可能存在多种方式）。例如，"11106" 可以映射为：
 * <p>
 * "AAJF" 对应分组 (1 1 10 6)
 * "KJF" 对应分组 (11 10 6)
 * 注意，像 (1 11 06) 这样的分组是无效的，因为 "06" 不可以映射为 'F' ，因为 "6" 与 "06" 不同。
 * <p>
 * 除了 上面描述的数字字母映射方案，编码消息中可能包含 '*' 字符，可以表示从 '1' 到 '9' 的任一数字（不包括 '0'）。例如，编码字符串 "1*" 可以表示 "11"、"12"、"13"、"14"、"15"、"16"、"17"、"18" 或 "19" 中的任意一条消息。对 "1*" 进行解码，相当于解码该字符串可以表示的任何编码消息。
 * <p>
 * 给你一个字符串 s ，由数字和 '*' 字符组成，返回 解码 该字符串的方法 数目 。
 * <p>
 * 由于答案数目可能非常大，返回 109 + 7 的 模 。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "*"
 * 输出：9
 * 解释：这一条编码消息可以表示 "1"、"2"、"3"、"4"、"5"、"6"、"7"、"8" 或 "9" 中的任意一条。
 * 可以分别解码成字符串 "A"、"B"、"C"、"D"、"E"、"F"、"G"、"H" 和 "I" 。
 * 因此，"*" 总共有 9 种解码方法。
 * 示例 2：
 * <p>
 * 输入：s = "1*"
 * 输出：18
 * 解释：这一条编码消息可以表示 "11"、"12"、"13"、"14"、"15"、"16"、"17"、"18" 或 "19" 中的任意一条。
 * 每种消息都可以由 2 种方法解码（例如，"11" 可以解码成 "AA" 或 "K"）。
 * 因此，"1*" 共有 9 * 2 = 18 种解码方法。
 * 示例 3：
 * <p>
 * 输入：s = "2*"
 * 输出：15
 * 解释：这一条编码消息可以表示 "21"、"22"、"23"、"24"、"25"、"26"、"27"、"28" 或 "29" 中的任意一条。
 * "21"、"22"、"23"、"24"、"25" 和 "26" 由 2 种解码方法，但 "27"、"28" 和 "29" 仅有 1 种解码方法。
 * 因此，"2*" 共有 (6 * 2) + (3 * 1) = 12 + 3 = 15 种解码方法。
 */
public class numDecodingsII {
    /**
     * 暴力递归
     *
     * @param s
     * @return
     */
    public int numDecodings(String s) {
        return f1(s.toCharArray(), 0);
    }

    //s[i...]位置开始后面有多少种有效的转化
    public static int f1(char[] s, int i) {
        if (i == s.length) {
            return 1;
        }
        if (s[i] == '0') {
            return 0;
        }
        //s[i] != 0
        // 2) i单独作为一种转化,如果s[i]是 ‘*’ 有9种，不是 * 只有1种转化 *
        int ans = f1(s, i + 1) * (s[i] == '*' ? 9 : 1);
        //有i+1的位置
        // 3) i 和 i+1 一起作为一种转化
        if (i + 1 < s.length) {
            if (s[i] != '*') {
                if (s[i + 1] != '*') {
                    //num num
                    // i  i+1
                    if (((s[i] - '0') * 10 + s[i + 1] - '0') <= 26) {
                        ans += f1(s, i + 2);
                    }
                } else {
                    //num   *
                    // i   i+1
                    if (s[i] == '1') {
                        ans += f1(s, i + 2) * 9;
                    }
                    if (s[i] == '2') {
                        ans += f1(s, i + 2) * 6;
                    }
                }
            } else {
                if (s[i + 1] != '*') {
                    // *   num
                    // i   i+1
                    if (s[i + 1] <= '6') {//如果i+1位置是0~6，那么i位置的*只有两种可能 1 或者 2
                        ans += f1(s, i + 2) * 2;
                    } else {//i+1位置>6 那么i位置只能是1
                        ans += f1(s, i + 2);
                    }
                } else {
                    // *   *
                    // i  i+1
                    //11 ~ 19 ，21 ~ 26 共15种可能
                    ans += f1(s, i + 2) * 15;
                }
            }
        }
        return ans;
    }

    //题目要求取模
    public static long mod = 1000000007;

    /**
     * 暴力递归改记忆化搜索
     * 自顶向下的搜索
     */
    public static int numDecodings2(String str) {
        char[] s = str.toCharArray();
        long[] dp = new long[s.length];
        Arrays.fill(dp, -1);
        return (int) f2(s, 0, dp);
    }

    public static long f2(char[] s, int i, long[] dp) {
        if (i == s.length) {
            return 1;
        }
        if (s[i] == '0') {
            return 0;
        }
        if (dp[i] != -1) {
            return dp[i];
        }
        //s[i] != 0
        // 2) i单独作为一种转化,如果s[i]是 ‘*’ 有9种，不是 * 只有1种转化 *
        long ans = f2(s, i + 1, dp) * (s[i] == '*' ? 9 : 1);
        //有i+1的位置
        // 3) i 和 i+1 一起作为一种转化
        if (i + 1 < s.length) {
            if (s[i] != '*') {
                if (s[i + 1] != '*') {
                    //num num
                    // i  i+1
                    if (((s[i] - '0') * 10 + s[i + 1] - '0') <= 26) {
                        ans += f2(s, i + 2, dp);
                    }
                } else {
                    //num   *
                    // i   i+1
                    if (s[i] == '1') {
                        ans += f2(s, i + 2, dp) * 9;
                    }
                    if (s[i] == '2') {
                        ans += f2(s, i + 2, dp) * 6;
                    }
                }
            } else {
                if (s[i + 1] != '*') {
                    // *   num
                    // i   i+1
                    if (s[i + 1] <= '6') {//如果i+1位置是0~6，那么i位置的*只有两种可能 1 或者 2
                        ans += f2(s, i + 2, dp) * 2;
                    } else {//i+1位置>6 那么i位置只能是1
                        ans += f2(s, i + 2, dp);
                    }
                } else {
                    // *   *
                    // i  i+1
                    //11 ~ 19 ，21 ~ 26 共15种可能
                    ans += f2(s, i + 2, dp) * 15;
                }
            }
        }
        ans %= mod;
        dp[i] = ans;
        return ans;
    }

    /**
     * 严格位置依赖的动态规划版本
     * 位置 i 依赖于 i+1 和 i+2
     * dp[n] 位置是 base case
     * 从dp[n n-1 ... 0]一直从 dp[n] 填到 dp[0] 位置
     * 最终返回dp[0]位置
     */
    public static int numDecodings3(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        long[] dp = new long[s.length + 1];
        dp[n] = 1;//越界位置，也就是base case
        for (int i = n - 1; i >= 0; i--) {
            //把暴力递归的逻辑全抄过来，把调递归的地方改成调dp[]
            if (s[i] != '0') {
                dp[i] = dp[i + 1] * (s[i] == '*' ? 9 : 1);
                //有i+1的位置
                // 3) i 和 i+1 一起作为一种转化
                if (i + 1 < s.length) {
                    if (s[i] != '*') {
                        if (s[i + 1] != '*') {
                            //num num
                            // i  i+1
                            if (((s[i] - '0') * 10 + s[i + 1] - '0') <= 26) {
                                dp[i] += dp[i + 2];
                            }
                        } else {
                            //num   *
                            // i   i+1
                            if (s[i] == '1') {
                                dp[i] += dp[i + 2] * 9;
                            }
                            if (s[i] == '2') {
                                dp[i] += dp[i + 2] * 6;
                            }
                        }
                    } else {
                        if (s[i + 1] != '*') {
                            // *   num
                            // i   i+1
                            if (s[i + 1] <= '6') {//如果i+1位置是0~6，那么i位置的*只有两种可能 1 或者 2
                                dp[i] += dp[i + 2] * 2;
                            } else {//i+1位置>6 那么i位置只能是1
                                dp[i] += dp[i + 2];
                            }
                        } else {
                            // *   *
                            // i  i+1
                            //11 ~ 19 ，21 ~ 26 共15种可能
                            dp[i] += dp[i + 2] * 15;
                        }
                    }
                }
            }
            dp[i] %= mod;
        }
        return (int) dp[0];
    }

    /**
     * 严格位置依赖的动态规划版本+空间压缩（不用dp表，只用有限几个变量滚动更新）
     * 位置 i 依赖于 i+1 和 i+2
     * 用 next 代替 dp[i+1] ，用 nextNext 代替 dp[i+2]
     * 滚动更新用cur代替dp[i]
     * 最终返回dp[0]位置
     */
    public static int numDecodings4(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        long cur = 0, next = 1, nextNext = 0;
        //
        for (int i = n - 1; i >= 0; i--) {
            //把暴力递归的逻辑全抄过来，把调递归的地方改成调dp[]
            if (s[i] != '0') {
                cur = next * (s[i] == '*' ? 9 : 1);
                //有i+1的位置
                // 3) i 和 i+1 一起作为一种转化
                if (i + 1 < s.length) {
                    if (s[i] != '*') {
                        if (s[i + 1] != '*') {
                            //num num
                            // i  i+1
                            if (((s[i] - '0') * 10 + s[i + 1] - '0') <= 26) {
                                cur += nextNext;
                            }
                        } else {
                            //num   *
                            // i   i+1
                            if (s[i] == '1') {
                                cur += nextNext * 9;
                            }
                            if (s[i] == '2') {
                                cur += nextNext * 6;
                            }
                        }
                    } else {
                        if (s[i + 1] != '*') {
                            // *   num
                            // i   i+1
                            if (s[i + 1] <= '6') {//如果i+1位置是0~6，那么i位置的*只有两种可能 1 或者 2
                                cur += nextNext * 2;
                            } else {//i+1位置>6 那么i位置只能是1
                                cur += nextNext;
                            }
                        } else {
                            // *   *
                            // i  i+1
                            //11 ~ 19 ，21 ~ 26 共15种可能
                            cur += nextNext * 15;
                        }
                    }
                }
                cur%=mod;
            }
            //当前i更新结束，滚动更新i-1位置
            nextNext = next;
            next = cur;
            cur=0;
        }
        return (int) next;
    }


}
