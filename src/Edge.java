import java.util.ArrayList;

/**
 * Created by Windows 10 on 1/19/2018.
 */
public class Edge {
    boolean putInList = false;
    Vertex v1, v2;
    int c, z;

    public Edge(Vertex v1, Vertex v2) {
        z = 0;
        this.v1 = v1;
        this.v2 = v2;
    }

    public void calculateZ(Edge[][] graph, int numOfV) {
        z = 0;
        int firstV = Math.min(v1.v, v2.v);
        int secondV = Math.max(v1.v, v2.v);
        int thirdV = 1;
        while (thirdV <= numOfV) {
            if (graph[firstV- 1][thirdV - 1] != null && thirdV != secondV) {
                if (graph[secondV - 1][thirdV - 1] != null) {
                    z++;
                }

            }
            thirdV++;
        }

    }

    public void calculateZ(ArrayList<Edge>[] graph) {
        z = 0;
        int firstV, secondV, thirdV;
        if (graph[v1.v  - 1].size() < graph[v2.v - 1].size()) {
            firstV = v1.v;
            secondV = v2.v;
        } else {
            firstV = v2.v;
            secondV = v1.v;

        }

        for (Edge e : graph[firstV - 1]) {
            if (secondV != e.v1.v && secondV != e.v2.v)
                if (e.v1.v != firstV)
                    thirdV = e.v1.v;
                else
                    thirdV = e.v2.v;
            else
                continue;
            if (graph[thirdV - 1].size() < graph[secondV - 1].size())
                for (Edge edge : graph[thirdV - 1]) {
                    if (edge.v1.v == secondV || edge.v2.v == secondV) {
                        z++;
                        break;
                    }
                }
            else
                for (Edge edge : graph[secondV  - 1]) {
                    if (edge.v1.v == thirdV || edge.v2.v == thirdV) {
                        z++;
                        break;
                    }
                }


        }
    }

    public void calculateC() {
        if (v1.k == 1 || v2.k == 1) {
            c = Integer.MAX_VALUE;
            return;
        }
        c = (z + 1) / Math.min(v1.k - 1, v2.k - 1);

    }

}
