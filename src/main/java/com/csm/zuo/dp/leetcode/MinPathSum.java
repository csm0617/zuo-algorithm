package com.csm.zuo.dp.leetcode;

import java.util.Arrays;

/**
 * LC 64.最小路径和
 * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 * <p>
 * 说明：每次只能向下或者向右移动一步。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
 * 输出：7
 * 解释：因为路径 1→3→1→1→1 的总和最小。
 * 示例 2：
 * <p>
 * 输入：grid = [[1,2,3],[4,5,6]]
 * 输出：12
 */
public class MinPathSum {

    public int minPathSum1(int[][] grid) {
        return f1(grid, grid.length - 1, grid[0].length - 1);
    }

    /**
     * 暴力递归：
     * (i,j)的位置一定来自于左边(i,j-1)向右移动一格过来，
     * 或者上边(i-1,j)向下移动一格过来
     *
     * @param grid
     * @param i
     * @param j
     * @return
     */

    //从(0,0)到(i,j)的最小路径和
    //一定每次只能向右或者向下
    public static int f1(int[][] grid, int i, int j) {
        //base case递归终止条件
        if (i == 0 && j == 0) {
            return grid[0][0];
        }
        int up = Integer.MAX_VALUE;
        int left = Integer.MAX_VALUE;
        //从上边来到我i,j的位置
        if (i > 0) {
            //递归进去，求来到(i-1,j)位置，上边和左边边的最短距离和
            up = f1(grid, i - 1, j);
        }
        //从左边来到我i,j的位置
        if (j > 0) {
            //递归，求来到(i,j-1)位置，上边和左边边的最短距离和
            left = f1(grid, i, j - 1);
        }
        //每次选择到我(i,j)的位置中最小的+我本来的距离
        return grid[i][j] + Math.min(up, left);
    }

    public int minPathSum2(int[][] grid) {
        int m = grid.length;//m行
        int n = grid[0].length;//n列
        //dp[i][j]:从(0,0)位置出发到dp[i,j]位置的最小距离和
        int[][] dp = new int[m][n];
        //初始化dp数组
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = -1;
            }
        }
        return f2(grid, m - 1, n - 1, dp);
    }

    /**
     * 暴力递归 改 记忆化搜索
     * 自顶向下的动态规划
     */
    public int f2(int[][] grid, int i, int j, int[][] dp) {
        //先查dp表，看是否被计算过
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        int ans;
        //base case
        if (i == 0 && j == 0) {
            ans = grid[0][0];
        } else {//不是base case
            int up = Integer.MAX_VALUE;
            int left = Integer.MAX_VALUE;
            //从上面来到(i,j)
            if (i > 0) {
                up = f2(grid, i - 1, j, dp);
            }
            //从左边来到(i,j)
            if (j > 0) {
                left = f2(grid, i, j - 1, dp);
            }
            //(i,j)自己的距离 + 决策：选择来到(i,j)位置最小的方案
            ans = grid[i][j] + Math.min(up, left);
        }
        //没计算过，放入缓存表
        dp[i][j] = ans;
        //向上返回调用的结果
        return ans;
    }

    /**
     *
     * 严格位置依赖的动态规划版本
     * 自底向下
     * 二维的和一维的类似都需要从简单的格子填起
     * 一维的需要先填好 base case的格子（一个） 因为一维往往只需要依赖某一项
     * 二维往往需要填好 一行一列的格子 二维依赖的是某两项
     */

    public int minPathSum3(int[][] grid) {
        int m = grid.length;//m行
        int n = grid[0].length;//n列
        //dp[i][j]:从(0,0)位置出发到dp[i,j]位置的最小距离和
        int[][] dp = new int[m][n];
        //初始化dp数组
        //base case
        dp[0][0] = grid[0][0];
        /*
            dp就是填格子，先找简单的格子填好
            根据题意，只能往下或者往右走，
            所以第一行的格子只能往右走，dp[0][i]依赖于dp[0,i-1]
            第一列的格子只能往下走,dp[i][0]依赖于dp[i-1][0]
         */
        //填第一行
        for (int i = 1; i < n; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }
        //填第一列
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        //填剩下的行和列
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                //当前dp[i][j]来自于自己的距离+决策：选择来自上方或者来自左边的最小的距离和的
                dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        //根据题意返回dp[m-1][n-1]也就是从(0,0)位置到右下角的最短距离和
        return dp[m - 1][n - 1];
    }

    /**
     * 严格位置依赖 改 空间压缩（滚动更新）
     */
    public static int minPathSum4(int[][] grid) {
        int m = grid.length;//m行
        int n = grid[0].length;//n列
        int[] dp = new int[n];
        dp[0] = grid[0][0];
        //先让dp表变成想象中的表的第0行数据
        for (int i = 1; i < n; i++) {
            dp[i] = dp[i - 1] + grid[0][i];
        }
        //滚动更新,从第一行开始,滚动至最后一行
        for (int i = 1; i < m; i++) {
            //i == 1 ,dp表变成想象中的第1行数据
            //i == 2 ,dp表变成想象中的第2行数据
            //...
            //i == m-1 ,dp表变成想象中的第m-1行数据
            dp[0] += grid[i][0];
            for (int j = 1; j < n; j++) {
                //更新策略:dp[j-1]是左边格子的最小距离和，dp[j]在未被更新前是上一个格子的最小距离和，决策：选择两种中最小的 + 自己的距离
                dp[j] = Math.min(dp[j - 1], dp[j]) + grid[i][j];
            }
        }
        return dp[n - 1];
    }

}
