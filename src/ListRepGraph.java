import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Windows 10 on 1/19/2018.
 */
public class ListRepGraph {
    ArrayList<Edge>[] list;
    Vertex[] vertices;
    boolean[] visited;

    ListRepGraph(String address) {

        ArrayList<Integer> edges = Utils.readFile(address);
        int size = Utils.findSize(edges);
        list = new ArrayList[size];
        vertices = new Vertex[size];
        visited = new boolean[size];
        setList(edges);
        initializeVertices();
        setZAndC(vertices.length);

    }

    int cnt = 0;

    public void omitEdges(int sort, int N) {
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < vertices.length; i++) {
            for (Edge e : list[i]) {
                if (!e.putInList) {
                    edges.add(e);
                    e.putInList = true;
                }
            }
        }
        long startTime = System.currentTimeMillis();
        do {
            cnt++;
            setZAndC(vertices.length);
            edges = Utils.sort(sort, edges, N);
            Edge edge = edges.get(0);
            list[edge.v1.v - 1].remove(edge);
            list[edge.v2.v - 1].remove(edge);
            vertices[edge.v1.v - 1].setK(vertices[edge.v1.v - 1].k - 1);
            vertices[edge.v2.v - 1].setK(vertices[edge.v2.v - 1].k - 1);
            edges.remove(0);
            System.out.println(cnt);
        } while (isConnected());

        long endTime = System.currentTimeMillis();
        long readTime = endTime - startTime;
        System.out.println("5 step time: " + readTime + " ms");
        System.out.println("num of deleted edges: " + cnt);
        try {
            Utils.writeToFile(visited);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setList(ArrayList<Integer> edges) {
        for (int i = 0; i < edges.size(); i++) {
            if (vertices[edges.get(i) - 1] == null) {
                vertices[edges.get(i) - 1] = new Vertex(edges.get(i));
                list[edges.get(i) - 1] = new ArrayList<>();
            }
            if (vertices[edges.get(++i) - 1] == null) {
                vertices[edges.get(i) - 1] = new Vertex(edges.get(i));
                list[edges.get(i) - 1] = new ArrayList<>();
            }
            if (edges.get(i - 1) > edges.get(i))
                continue;
            Edge edge = new Edge(vertices[edges.get(i - 1) - 1], vertices[edges.get(i) - 1]);
            list[edges.get(i - 1) - 1].add(edge);
            list[edges.get(i) - 1].add(edge);
        }
    }

    private void initializeVertices() {
        for (int i = 0; i < vertices.length; i++) {
            if (list[i] != null)
                vertices[i].setK(list[i].size());
            else {
                vertices[i] = new Vertex(i);
            }

        }
    }

    private void setZAndC(int size) {
        for (int i = 0; i < size; i++)
            for (Edge e : list[i]) {
                e.calculateZ(list);
                e.calculateC();
            }

    }

    boolean isConnected() {
        for (int i = 0; i < visited.length; i++)
            visited[i] = false;
        int counter = DFS(0);
        return counter == vertices.length;
    }

    private int DFS(final int start) {
        int counter = 1;
        //System.out.println("F DFS: " + counter);
        MyStack<Integer> stack = new MyStack<>();
        stack.push(start);
        while (stack.total != 0) {
            int v = stack.pop();
            stack.push(v);
            visited[v] = true;
            for (int i = 0; i < list[v].size(); i++) {
                int v2;
                if (list[v].get(i).v1.v == v + 1)
                    v2 = list[v].get(i).v2.v - 1;
                else
                    v2 = list[v].get(i).v1.v - 1;
                if (!visited[v2]) {
                    visited[v2] = true;
                    counter++;
                    v = v2;
                    stack.push(v2);
                    i = -1;
                }
            }
            stack.pop();
        }
        //System.out.println("DFS" + counter);
        return counter;
    }
}
