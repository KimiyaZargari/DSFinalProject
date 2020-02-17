import java.util.Scanner;

/**
 * Created by Windows 10 on 1/20/2018.
 */
public class Main {
    public static void main(String[] args) {
        ListRepGraph listRepGraph = null;
        MatrixRepGraph matrixRepGraph = null;
        Scanner reader = new Scanner(System.in);
        //ListRepGraph matrixRepGraph = new ListRepGraph("C:\\Users\\Windows 10\\Desktop\\test1.txt");
        System.out.println("enter num of test case");
        int add = reader.nextInt();
        String address = "test" + add + ".txt";
        System.out.println("enter '1' for List representation and '2' for Matrix representation");
        int c = reader.nextInt();


        System.out.println("1) insertion \n2) merge\n3) bubble\n4)optimum insertion\n5)optimum bubble\n6) quick");
        int i = reader.nextInt();
        int N = 0;
        if(i == 5 || i == 4){
            System.out.println("enter N");
            N = reader.nextInt();
        }
        long start = System.currentTimeMillis();
        if (c == 1){
            long startTime = System.currentTimeMillis();
            listRepGraph = new ListRepGraph(address);
            long endTime   = System.currentTimeMillis();
            long readTime = endTime - startTime;
            System.out.println("read time: "+  readTime + " ms");
            listRepGraph.omitEdges(i, N);
        } else{
            long startTime = System.currentTimeMillis();
            matrixRepGraph = new MatrixRepGraph(address);
            long endTime   = System.currentTimeMillis();
            long readTime = endTime - startTime;
            System.out.println("read time: "+  readTime + " ms");
            matrixRepGraph.omitEdges(i, N);
        }

        long end   = System.currentTimeMillis();
        long time = end - start;
        System.out.println("complete time: " + time + "ms");


    }
}
