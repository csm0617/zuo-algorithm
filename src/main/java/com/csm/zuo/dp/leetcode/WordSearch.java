package com.csm.zuo.dp.leetcode;

/**
 * LC79. 单词搜索
 * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
 * <p>
 * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
 */
public class WordSearch {
    public boolean exist(char[][] board, String word) {
        char[] w = word.toCharArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                //两层循环对网格的每一个位置作为起点开始尝试搜索
                if (f(board, i, j, w, 0)) {//只要有一个位置符合就返回true
                    return true;
                }
            }
        }
        //都不符合，也就是每个位置出发都匹配不上单词，返回false
        return false;
    }

    /**
     * 递归的含义 char[][] b是当前网格的状态， (i,j)是当前需要判断是否匹配的位置， char[] w需要匹配的单词， k是需要匹配完的下标
     * 来到一个格，如果没有匹配，向调用返回false，如果匹配了，记录下匹配的字符，标记为已访问过，然后就让上下左右的格子去匹配单词的第K+1个字符，
     * 已经被访问过的就不会和第k+1个匹配，最终如果 k来到单词结束了，说明字符全部匹配了。
     * @param b 网格的状态
     * @param i 当前位置(i,j)的横坐标 i
     * @param j 当前位置(i,j)的纵坐标 j
     * @param w 单词的字符数组
     * @param k 匹配到单词的哪个位置上了
     * @return
     */
    public static boolean f(char[][] b, int i, int j, char[] w, int k) {
        //递归终止位置，匹配到单词结束了，说明找到
        if (k == w.length) {
            return true;
        }
        //横纵坐标越界，或者网格当前位置(i,j)的字符和当前的word数组的当前字符不匹配
        if (i < 0 || i == b.length || j < 0 || j == b[0].length || b[i][j] != w[k]) {
            return false;
        }
        //经过上一个if,当前(i,j)和w[k]匹配上了 b[i][j] == w[k]
        //保存当前位置的字符，作为恢复的现场
        char temp = b[i][j];
        //把走过的当前(i,j)的格子标记为 0值，题意要求用过的格子不能重复使用，也就是走过的路不能回头走
        b[i][j] = '0';
        //再进行上下左右匹配单词的第k+1个字符
        boolean ans = f(b, i - 1, j, w, k + 1)
                || f(b, i + 1, j, w, k + 1)
                || f(b, i, j - 1, w, k + 1)
                || f(b, i, j + 1, w, k + 1);
        //恢复现场
        b[i][j] = temp;
        return ans;
    }

    public static void main(String[] args) {
        char[][] board = new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        String word = "ABCCED";
        System.out.println(new WordSearch().exist(board, word));
    }
}
