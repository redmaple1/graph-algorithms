package com.xyren.graph.traverse;

import com.xyren.graph.Graph;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 单源路径
 *
 * @author renxiaoya
 * @date 2022-02-23
 **/
public class SingleSourcePath {
    private Graph G;

    /**
     * 源点
     */
    private int s;

    /**
     * 节点是否被访问过
     */
    private boolean[] visited;
    /**
     * 节点的来源
     */
    private int[] pre;

    public SingleSourcePath(Graph G, int s) {
        G.validateVertex(s);

        this.G = G;
        this.s = s;

        visited = new boolean[G.V()];
        pre = new int[G.V()];
        for (int i = 0; i < G.V(); i++) {
            pre[i] = -1;
        }

        dfs(s, s);
    }

    private void dfs(Integer v, int parent) {
        visited[v] = true;
        pre[v] = parent;
        for (int w : G.adj(v)) {
            if (!visited[w]) {
                dfs(w, v);
            }
        }
    }

    public boolean isConnectedTo(int t) {
        G.validateVertex(t);
        return visited[t];
    }

    /**
     * 从源s到t的一套路径
     *
     * @param t 目标节点
     * @return 路径
     */
    public Iterable<Integer> path(int t) {
        ArrayList<Integer> res = new ArrayList<>();
        if (!isConnectedTo(t)) {
            return res;
        }
        int cur = t;
        while (cur != s) {
            res.add(cur);
            cur = pre[cur];
        }
        res.add(s);

        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("d.txt");
        SingleSourcePath ssPath = new SingleSourcePath(graph, 0);
        System.out.println("0 -> 6 : " + ssPath.path(6));
        System.out.println("0 -> 5 : " + ssPath.path(5));
    }
}
