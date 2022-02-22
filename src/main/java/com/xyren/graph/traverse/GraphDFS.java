package com.xyren.graph.traverse;

import com.xyren.graph.Graph;

import java.util.ArrayList;

/**
 * 邻接表的表遍历 - DFS
 *
 * @author renxiaoya
 * @date 2022-02-23
 **/
public class GraphDFS {

    private Graph G;

    /**
     * 前序遍历结果
     */
    private ArrayList<Integer> pre = new ArrayList<>();
    /**
     * 后序遍历结果
     */
    private ArrayList<Integer> post = new ArrayList<>();
    private boolean[] visited;

    public GraphDFS(Graph G) {
        this.G = G;

        visited = new boolean[G.V()];

        // 这里使用循环是为了保证非连通图也能对所有顶点进行遍历
        for (int i = 0; i < G.V(); i++) {
            if (!visited[i]) {
                dfs(i);
            }
        }
    }

    private void dfs(Integer v) {
        pre.add(v);
        visited[v] = true;
        for (int w : G.adj(v)) {
            if (!visited[w]) {
                dfs(w);
            }
        }
        post.add(v);
    }

    public ArrayList<Integer> pre() {
        return pre;
    }

    public ArrayList<Integer> post() {
        return post;
    }

    public static void main(String[] args) {
        Graph graph = new Graph("d.txt");
        GraphDFS graphDFS = new GraphDFS(graph);
        System.out.println(graphDFS.pre());
        System.out.println(graphDFS.post());
    }
}
