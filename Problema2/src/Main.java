import java.util.Iterator;
//import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class Main {
	public static void main (String [] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		BestFirst s = new BestFirst();
		int j = sc.nextInt();
		//long startTime = System.nanoTime();
		//Iterator<BestFirst.State> it = s.solveAStar(new Triple(j,0),new Triple(j*3,0));
		Iterator<BestFirst.State> it = s.solve(new Triple(j,0),new Triple(j*3,0));
		//Iterator<BestFirst.State> it = s.solveIDAStar(new Triple(j,0),new Triple(j*3,0));
		//long endTime = System.nanoTime();
        //long elapsedTime = endTime - startTime;
		
			while(it.hasNext()) {
				BestFirst.State i = it.next();
				System.out.println(i.toString());
				if (!it.hasNext()) {
					System.out.println();
					System.out.println((int)i.getG());
				}

			}

		int nodesExpandeduniform = s.getNodesExpandeduniform();
		System.out.println("Nodes Expanded uniformSearch: " + nodesExpandeduniform);

		int nodesGenerateduniform = s.getNodesGenerateduniform();
		System.out.println("Nodes Generated uniformSearch: " + nodesGenerateduniform);

		//int nodesExpandedAStar = s.getNodesExpandedAStarSearch();
		//System.out.println("Nodes Expanded AStarSearch: " + nodesExpandedAStar);

		//int nodesGeneratedAStar = s.getNodesGeneratedAStarSearch();
		//System.out.println("Nodes Generated AStarSearch: " + nodesGeneratedAStar);

		//int nodesExpandedIDAStar = s.getNodesExpandedIDAStarSearch();
		//System.out.println("Nodes Expanded IDAStarSearch: " + nodesExpandedIDAStar);

		//int nodesGeneratedIDAStar = s.getNodesGeneratedIDAStarSearch();
		//System.out.println("Nodes Generated IDAStarSearch: " + nodesGeneratedIDAStar);

		int solutionlength = s.getSolutionLengthIDAStarSearch();
		System.out.println("Solution length: " + solutionlength);

			 //long elapsedTimeInMillis = TimeUnit.NANOSECONDS.toMillis(elapsedTime);

		        //System.out.println("Method execution time: " + elapsedTimeInMillis + " milliseconds");
		sc.close();
	}
}