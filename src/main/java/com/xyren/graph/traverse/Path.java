package com.xyren.graph.traverse;

import com.xyren.graph.Graph;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 单源路径的优化版本，通过对dfs添加返回值，找到路径提前结束递归
 *
 * @author renxiaoya
 * @date 2022-02-24
 **/
public class Path {
    private Graph G;

    /**
     * 源点
     */
    private int s;

    /**
     * 目标点
     */
    private int t;

    /**
     * 节点是否被访问过
     */
    private boolean[] visited;
    /**
     * 节点的来源
     */
    private int[] pre;

    public Path(Graph G, int s, int t) {
        G.validateVertex(s);
        G.validateVertex(t);

        this.G = G;
        this.s = s;
        this.t = t;

        visited = new boolean[G.V()];
        pre = new int[G.V()];
        for (int i = 0; i < G.V(); i++) {
            pre[i] = -1;
        }

        dfs(s, s);
        for (int i = 0; i < G.V(); i++) {
            System.out.print(visited[i] + " ");
        }
        System.out.println();
    }

    /**
     * 通过返回是否找到源点到v的路径，可以提前结束循环，减少不必要的递归调用
     *
     * @param v      当前顶点
     * @param parent 上一个顶点
     * @return 是否找到源点到v的路径
     */
    private boolean dfs(Integer v, int parent) {
        visited[v] = true;
        pre[v] = parent;
        if (v == t) {
            return true;
        }
        for (int w : G.adj(v)) {
            if (!visited[w]) {
                if (dfs(w, v)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isConnectedTo() {
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
        if (!isConnectedTo()) {
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
        Path path1 = new Path(graph, 0, 6);
        System.out.println("0 -> 6 : " + path1.path(6));
        Path path2 = new Path(graph, 0, 5);
        System.out.println("0 -> 5 : " + path2.path(5));
    }
}
