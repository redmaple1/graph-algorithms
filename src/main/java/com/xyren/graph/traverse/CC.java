package com.xyren.graph.traverse;

import com.xyren.graph.Graph;

import java.util.ArrayList;

/**
 * 联通分量 - Connected Component
 *
 * @author renxiaoya
 * @date 2022-02-23
 **/
public class CC {

    private Graph G;

//    private boolean[] visited;
    /**
     * int数组表示是否访问过，未访问过是-1，访问过同一个连通分量的visited值相等，可以方便求出图的所有联通分量
     */
    private int[] visited;
    /**
     * 联通分量数量
     */
    private int ccCount;

    public CC(Graph G) {
        this.G = G;

        visited = new int[G.V()];
        for (int i = 0; i < G.V(); i++) {
            visited[i] = -1;
        }

        // 这里使用循环是为了保证非连通图也能对所有顶点进行遍历
        for (int i = 0; i < G.V(); i++) {
            if (visited[i] == -1) {
                dfs(i, ccCount);
                ccCount++;
            }
        }
    }

    private void dfs(Integer v, int ccid) {
        visited[v] = ccid;
        for (int w : G.adj(v)) {
            if (visited[w] == -1) {
                dfs(w, ccid);
            }
        }
    }

    public int ccCount() {
        return ccCount;
    }

    public boolean isConnected(int v, int w) {
        G.validateVertex(v);
        G.validateVertex(w);
        return visited[v] == visited[w];
    }

    /**
     * 获取图中的所有联通分量
     *
     * @return 联通分量数组
     */
    public Iterable<Integer>[] getConnectedComponent() {
        ArrayList<Integer>[] res = new ArrayList[ccCount];
        for (int i = 0; i < ccCount; i++) {
            res[i] = new ArrayList<>();
        }

        for (int v = 0; v < G.V(); v++) {
            res[visited[v]].add(v);
        }
        return res;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("d.txt");
        CC cc = new CC(graph);
        System.out.println(cc.ccCount());
        System.out.println(cc.isConnected(0, 6));
        System.out.println(cc.isConnected(0, 5));
        Iterable<Integer>[] ccArray = cc.getConnectedComponent();
        System.out.println("联通分量：");
        for (Iterable<Integer> item : ccArray) {
            System.out.println(item);
        }
    }
}
