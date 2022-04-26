package com.xyren.graph.traverse.bfs;

import com.xyren.graph.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 无向无权图的bfs遍历
 *
 * @author renxiaoya
 * @date 2022-02-28
 **/
public class GraphBFS {

    private Graph G;

    private boolean[] visited;
    private ArrayList<Integer> order = new ArrayList<>();

    public GraphBFS(Graph G) {
        this.G = G;
        visited = new boolean[G.V()];

        for (int i = 0; i < G.V(); i++) {
            if (!visited[i]) {
                bfs(i);
            }
        }
    }

    private void bfs(int s) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.remove();
            order.add(v);
            for (int w : G.adj(v)) {
                if (!visited[w]) {
                    queue.add(w);
                    visited[w] = true;
                }
            }
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    public static void main(String[] args) {
        Graph g = new Graph("g.txt");
        GraphBFS graphBFS = new GraphBFS(g);
        System.out.println("BFS order : " + graphBFS.order());
    }

}
