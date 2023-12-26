package com.csm.zuo.dp.leetcode;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 983 最低票价
 * 在一个火车旅行很受欢迎的国度，你提前一年计划了一些火车旅行。在接下来的一年里，你要旅行的日子将以一个名为 days 的数组给出。每一项是一个从 1 到 365 的整数。
 * <p>
 * 火车票有 三种不同的销售方式 ：
 * <p>
 * 一张 为期一天 的通行证售价为 costs[0] 美元；
 * 一张 为期七天 的通行证售价为 costs[1] 美元；
 * 一张 为期三十天 的通行证售价为 costs[2] 美元。
 * 通行证允许数天无限制的旅行。 例如，如果我们在第 2 天获得一张 为期 7 天 的通行证，那么我们可以连着旅行 7 天：第 2 天、第 3 天、第 4 天、第 5 天、第 6 天、第 7 天和第 8 天。
 * <p>
 * 返回 你想要完成在给定的列表 days 中列出的每一天的旅行所需要的最低消费 。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：days = [1,4,6,7,8,20], costs = [2,7,15]
 * 输出：11
 * 解释：
 * 例如，这里有一种购买通行证的方法，可以让你完成你的旅行计划：
 * 在第 1 天，你花了 costs[0] = $2 买了一张为期 1 天的通行证，它将在第 1 天生效。
 * 在第 3 天，你花了 costs[1] = $7 买了一张为期 7 天的通行证，它将在第 3, 4, ..., 9 天生效。
 * 在第 20 天，你花了 costs[0] = $2 买了一张为期 1 天的通行证，它将在第 20 天生效。
 * 你总共花了 $11，并完成了你计划的每一天旅行。
 * 示例 2：
 * <p>
 * 输入：days = [1,2,3,4,5,6,7,8,9,10,30,31], costs = [2,7,15]
 * 输出：17
 * 解释：
 * 例如，这里有一种购买通行证的方法，可以让你完成你的旅行计划：
 * 在第 1 天，你花了 costs[2] = $15 买了一张为期 30 天的通行证，它将在第 1, 2, ..., 30 天生效。
 * 在第 31 天，你花了 costs[0] = $2 买了一张为期 1 天的通行证，它将在第 31 天生效。
 * 你总共花了 $17，并完成了你计划的每一天旅行。
 */
public class MinCostTickets {
    /**
     * 方法一：暴力递归
     *
     * @param days
     * @param costs
     * @return
     */
    public static int[] durations = {1, 7, 30};
    public static HashMap<Integer, Integer> selection = new HashMap<>();

    public int mincostTickets(int[] days, int[] costs) {
        return recursion(days, costs, 0);
    }

    /**
     * 暴力尝试：
     * 假设i是当前的天数，i有三种选择的方案durations[k]，对每一个方案进行尝试，遍历k，以当前花费cost[k]+下次递归调用的花费，
     * 把每次当前i的k种方案花费最小的返回。
     *
     * @param days
     * @param costs
     * @param i
     * @return
     */
    //days[i...开始最小需要花费多少
    public static int recursion(int[] days, int[] costs, int i) {
        //base case 来到数组末尾返回0
        if (i == days.length) {
            return 0;
        }
        //假设ans一开始就是当前i最大花费
        int ans = Integer.MAX_VALUE;
        for (int k = 0, j = i; k < 3; k++) {
            //k是方案的编号：0，1，2  方案0买1的票，方案2买7天的票，方案3买30天的票
            //找到下一个需要买票的day[j]
            while (j < days.length && days[i] + durations[k] > days[j]) {
                j++;
            }
            //经过k次方案，选择当前花费costs[k] + 下次花费recursion(days, costs, j)最小的一个方案
            ans = Math.min(ans, costs[k] + recursion(days, costs, j));
        }
        //向第i-1次调用，返回第i次的结果
        return ans;
    }

    /**
     * ！！！补充：什么情况下适合改为一维动态规划，什么时候不适合？
     *  当递归中存在多次重复调用的时候就适合改为一维动态规划，当重复调用的次数少时就不适合改动态规划
     */

    /**
     * 方法二：暴力尝试改记忆化搜索
     * 从顶到底的动态规划
     */
    public static int minCostTickets(int[] days, int[] costs) {
        int[] dp = new int[days.length];
        //初始化记忆数组，用最大值，-1来填充都可以，看具体的
        for (int i = 0; i < days.length; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        return recursion(days, costs, 0, dp);
    }

    public static int recursion(int[] days, int[] costs, int i, int[] dp) {
        if (i == days.length) {
            return 0;
        }
        //检索记忆数组中存在,就直接返回
        if (dp[i] != Integer.MAX_VALUE) {
            return dp[i];
        }
        //不存在就需要计算
        int ans = Integer.MAX_VALUE;
        for (int k = 0, j = i; k < 3; k++) {
            while (j < days.length && days[i] + durations[k] > days[j]) {
                j++;
            }
            ans = Math.min(ans, costs[k] + recursion(days, costs, j, dp));
        }
        //计算好放入记忆数组
        dp[i] = ans;
        return ans;
    }

    //严格位置依赖的动态规划
    //从底到顶的动态规划
    public static int MAXN = 366;
    public static int[] dp = new int[MAXN];

    public static int minCostTickets3(int[] days, int[] costs) {
        int n = days.length;
        Arrays.fill(dp, 0, n + 1, Integer.MAX_VALUE);
        //dp[0...n-1 n]
        dp[n] = 0;//超出数组长度的部分
        for (int i = n - 1; i > 0; i--) {
            for (int k = 0, j = i; k < 3; k++) {
                while (j < days.length && days[i] + durations[k] > days[j]) {
                    j++;
                }
                dp[i] = costs[k] + dp[j];
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        int[] days = new int[]{1, 4, 6, 7, 8, 20};
        int[] cost = new int[]{2, 7, 15};
        System.out.println(minCostTickets(days, cost));
        System.out.println(selection);
    }
}
