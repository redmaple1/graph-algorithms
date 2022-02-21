package com.xyren.graph;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.TreeSet;

/**
 * 邻接表（TreeSet实现），作为默认实现，命名为Graph
 *
 * @author renxiaoya
 * @date 2022-02-21
 **/
public class Graph {
    /**
     * 图的顶点数
     */
    private int V;

    /**
     * 图的边数
     */
    private int E;

    /**
     * 存储图的邻接表的TreeSet数组
     */
    private TreeSet<Integer>[] adj;

    public Graph(String fileName) {
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
                    adj = new TreeSet[V];
                    for (int j = 0; j < V; j++) {
                        adj[j] = new TreeSet<>();
                    }
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
                    if (adj[first].contains(second)) {
                        throw new IllegalArgumentException("Parallel edge is detected.");
                    }
                    adj[first].add(second);
                    adj[second].add(first);
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
        return adj[v].contains(w);
    }

    /**
     * 与定点v相邻的顶点
     *
     * @param v 顶点v
     * @return 相邻的定点集合
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * 顶点v的度数
     *
     * @param v 顶点v
     * @return v的度数
     */
    private int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("V = %d,E = %d\n", V, E));
        for (int i = 0; i < V; i++) {
            sb.append(String.format("%d : ", i));
            for (int w : adj[i]) {
                sb.append(String.format("%d  ", w));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Graph graph = new Graph("d.txt");
        System.out.println(graph.toString());
    }
}
