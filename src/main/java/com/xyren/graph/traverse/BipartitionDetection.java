package com.xyren.graph.traverse;

import com.xyren.graph.Graph;

/**
 * 二分图检测
 *
 * @author renxiaoya
 * @date 2022-02-26
 **/
public class BipartitionDetection {
    private Graph G;

    private boolean[] visited;
    private int[] colors;
    private boolean isBipartite = true;

    public BipartitionDetection(Graph G) {
        this.G = G;

        visited = new boolean[G.V()];
        colors = new int[G.V()];
        for (int i = 0; i < G.V(); i++) {
            colors[i] = -1;
        }

        // 这里使用循环是为了保证非连通图也能对所有顶点进行遍历
        for (int i = 0; i < G.V(); i++) {
            if (!visited[i]) {
                if (!dfs(i, 0)) {
                    isBipartite = false;
                    break;
                }
            }
        }
    }

    private boolean dfs(Integer v, int color) {
        visited[v] = true;
        colors[v] = color;
        for (int w : G.adj(v)) {
            if (!visited[w]) {
                if (!dfs(w, 1 - color)) {
                    return false;
                }
            } else if (colors[w] == colors[v]) {
                return false;
            }
        }
        return true;
    }

    public boolean isBipartite() {
        return isBipartite;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("d.txt");
        BipartitionDetection bipartitionDetection = new BipartitionDetection(graph);
        System.out.println(bipartitionDetection.isBipartite());

        Graph graph2 = new Graph("non-bipartite.txt");
        BipartitionDetection bipartitionDetection2 = new BipartitionDetection(graph2);
        System.out.println(bipartitionDetection2.isBipartite());
    }
}
