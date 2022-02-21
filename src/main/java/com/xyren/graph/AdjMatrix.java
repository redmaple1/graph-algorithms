package com.xyren.graph;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 邻接矩阵表示的图
 *
 * @author renxiaoya
 * @date 2022-02-20
 **/
public class AdjMatrix {

    /**
     * 图的顶点数
     */
    private int V;

    /**
     * 图的边数
     */
    private int E;

    /**
     * 存储图的邻接矩阵二维数组
     */
    private int[][] adj;

    public AdjMatrix(String fileName) {
        try {
            List<String> lines = FileUtils.readLines(new File(fileName), Charset.defaultCharset());
            for (int i = 0; i < lines.size(); i++) {
                String[] split = lines.get(i).split(" ");
                int first = Integer.parseInt(split[0]);
                int second = Integer.parseInt(split[1]);
                if (i == 0) {
                    V = first;
                    if (first < 0) {
                        throw new IllegalArgumentException("V is non-negative.");
                    }
                    adj = new int[V][V];
                    E = second;
                    if (second < 0) {
                        throw new IllegalArgumentException("E is non_negative.");
                    }
                } else {
                    validateVertex(first);
                    validateVertex(second);

                    if (first == second) {
                        throw new IllegalArgumentException("Self loop is detected.");
                    }
                    if (adj[first][second] == 1) {
                        throw new IllegalArgumentException("Parallel edge is detected.");
                    }
                    adj[first][second] = 1;
                    adj[second][first] = 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("Vertex " + v + " is invalid.");
        }
    }

    /**
     * 图中有几个顶点
     *
     * @return 顶点个数
     */
    public int V() {
        return V;
    }

    /**
     * 图中有几条边
     *
     * @return 边的个数
     */
    public int E() {
        return E;
    }

    /**
     * 顶点v，w之间是否右边相连
     *
     * @param v 顶点v
     * @param w 顶点w
     * @return 是否相连
     */
    public boolean hasEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return adj[v][w] == 1;
    }

    /**
     * 与定点v相邻的顶点
     *
     * @param v 顶点v
     * @return 相邻的定点集合
     */
    public List<Integer> adj(int v) {
        validateVertex(v);
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (adj[v][i] == 1) {
                res.add(i);
            }
        }
        return res;
    }

    /**
     * 顶点v的度数
     *
     * @param v 顶点v
     * @return v的度数
     */
    private int degree(int v) {
        return adj(v).size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("V = %d,E = %d\n", V, E));
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                sb.append(String.format("%d  ", adj[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        AdjMatrix adjMatrix = new AdjMatrix("d.txt");
        System.out.println(adjMatrix.toString());
    }
}
