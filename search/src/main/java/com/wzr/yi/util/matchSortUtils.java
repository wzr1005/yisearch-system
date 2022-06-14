package com.wzr.yi.util;

/**
 * @autor zhenrenwu
 * @date 2022/6/11 11:59 上午
 */
public class matchSortUtils {
    /**
     * 计算两个文本的编辑距离，因为Java只适用英文字符，故中文编辑距离需要启用python服务rpc调用
     * @param query
     * @param target
     * @return
     */
    public static int getEditDistance(String query, String target){
        char[] queryArr = query.toCharArray();
        char[] targetArr = target.toCharArray();
        int len1 = queryArr.length;
        int len2 = targetArr.length;
        //  hor
        //o
        //r
        //or 消去r 等价于h和""，当前字符相同等于不需要编辑。继承两者最小
        int[][] dist = new int[len1+1][len2+1];
        //初始化状态
        for (int i = 0; i < len1 + 1; i++) {dist[i][0] = i;}
        for (int i = 0; i < len2 + 1; i++) {dist[0][i] = i;}
        for (int i = 1; i < len1 + 1; i++) {
            for (int j = 1; j < len2 + 1; j++) {
                if(queryArr[i-1] == targetArr[j-1]) dist[i][j] = dist[i-1][j-1];
                else {
                    dist[i][j] = Math.min(dist[i-1][j-1], Math.min(dist[i][j-1], dist[i-1][j])) + 1;
                }
            }
        }
        return dist[len1][len2];
    }
    // 单测
    public static void main(String[] args) {
        String query = "长津湖";
        String target = "长津湖之水门桥";
        Integer res = getEditDistance(query, target);
        System.out.println(res);
    }
}
