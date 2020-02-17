import java.io.IOException;
import java.util.ArrayList;

public class MatrixRepGraph {
    Edge[][] matrix;
    Vertex[] vertices;
    boolean[] visited;

    public MatrixRepGraph(String address) {
        ArrayList<Integer> edges = Utils.readFile(address);
        int size = Utils.findSize(edges);
        matrix = new Edge[size][size];
        vertices = new Vertex[size];
        visited = new boolean[size];
        setMatrix(edges);
        initializeVertices();
        setZAndC(vertices.length);

    }

    int cnt = 0;

    public void omitEdges(int sort, int N) {
        long startTime = System.currentTimeMillis();
        do {
            cnt++;
            setZAndC(vertices.length);
            ArrayList<Edge> edges = new ArrayList<>();
            for (int i = 0; i < vertices.length; i++) {
                for (int j = i; j < vertices.length; j++)
                    if (matrix[i][j] != null)
                        edges.add(matrix[i][j]);
            }
            edges = Utils.sort(sort, edges, N);
            Edge edge = edges.get(0);
            matrix[edge.v1.v - 1][edge.v2.v - 1] = null;
            matrix[edge.v2.v - 1][edge.v1.v - 1] = null;
            vertices[edge.v1.v - 1].setK(vertices[edge.v1.v - 1].k - 1);
            vertices[edge.v2.v - 1].setK(vertices[edge.v2.v - 1].k - 1);
        } while (isConnected());
        long endTime = System.currentTimeMillis();
        long readTime = endTime - startTime;
        System.out.println("5 step time: " + readTime + " ms");
        System.out.println("num of deleted edges: " + cnt);
        System.out.println(cnt);
        try {
            Utils.writeToFile(visited);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setMatrix(ArrayList<Integer> edges) {
        for (int i = 0; i < vertices.length; i++)
            vertices[i] = new Vertex(i + 1);
        for (int i = 0; i < edges.size(); i++) {
            matrix[edges.get(i) - 1][edges.get(++i) - 1] = new Edge(vertices[edges.get(i) - 1], vertices[edges.get(i - 1) - 1]);
        }
    }

    private void setZAndC(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++)
                if (matrix[i][j] != null) {
                    matrix[i][j].calculateZ(matrix, size);
                    matrix[i][j].calculateC();
                }
        }
    }


    private void initializeVertices() {
        for (int i = 0; i < vertices.length; i++) {
            int k = 0;
            for (int j = 0; j < vertices.length; j++) {
                if (matrix[i][j] != null)
                    k++;
            }
            vertices[i].setK(k);

        }
    }

    boolean isConnected() {

        for (int i = 0; i < visited.length; i++)
            visited[i] = false;
        int cnt = DFS(0, 1);
        if (cnt == vertices.length) {
            return true;
        }
        return false;
    }

    private int DFS(final int start, int cnt) {
        MyStack<Integer> stack = new MyStack<>();
        stack.push(start);
        while (stack.total != 0) {
            int v = stack.pop();
            stack.push(v);
            visited[v] = true;
            for (int i = 0; i < vertices.length; i++) {
                while (i < vertices.length && matrix[v][i] == null)
                    i++;
                if (i < vertices.length && !visited[i]) {
                    visited[i] = true;
                    cnt++;
                    v = i;
                    stack.push(i);
                    i = -1;
                }
            }
            stack.pop();
        }
        return cnt;
    }
}

