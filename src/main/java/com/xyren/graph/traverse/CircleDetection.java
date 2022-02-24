package com.xyren.graph.traverse;

import com.xyren.graph.Graph;

/**
 * 无向无权图的环检测
 *
 * @author renxiaoya
 * @date 2022-02-24
 **/
public class CircleDetection {
    private Graph G;

    private boolean[] visited;
    /**
     * 图中是否有环
     */
    private boolean hasCircle;

    public CircleDetection(Graph G) {
        this.G = G;

        visited = new boolean[G.V()];

        // 这里使用循环是为了保证非连通图也能对所有顶点进行遍历
        for (int i = 0; i < G.V(); i++) {
            if (!visited[i]) {
                if (dfs(i, i)) {
                    hasCircle = true;
                }
            }
        }
    }

    private boolean dfs(Integer v, int parent) {
        visited[v] = true;
        for (int w : G.adj(v)) {
            if (!visited[w]) {
                if (dfs(w, v)) {
                    return true;
                }
            } else if (v == parent) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCircle() {
        return hasCircle;
    }

    public static void main(String[] args) {
        Graph graph1 = new Graph("d.txt");
        CircleDetection circleDetection1 = new CircleDetection(graph1);
        System.out.println(circleDetection1.hasCircle());

        Graph graph2 = new Graph("d2.txt");
        CircleDetection circleDetection2 = new CircleDetection(graph2);
        System.out.println(circleDetection2.hasCircle());
    }
}
