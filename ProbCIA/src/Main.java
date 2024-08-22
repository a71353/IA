import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Scanner;

/**	Classe responsavel por inputs/outputs na consola e por inicializar objetos das diferentes classes do projeto
	@author P2G4
	@version 28/10/2023
*/

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
	public static void main (String [] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		BestFirst s = new BestFirst();
		int j = sc.nextInt();
		long startTime, endTime;
		double duration;

		startTime = System.currentTimeMillis();
		//Iterator<BestFirst.State> it = s.solve(new Triple(j,0),new Triple(j*3,0));
		Iterator<BestFirst.State> it = s.solveAStar(new Triple(j,0),new Triple(j*3,0));
		//Iterator<BestFirst.State> it = s.solveIDAStar(new Triple(j,0),new Triple(j*3,0));
		endTime = System.currentTimeMillis();


		duration = (endTime - startTime) / 1000.0;
		System.out.println("Tempo de execução do: " + duration + " segundos");

		//double[] Testes = s.solveAStarTestes(new Triple(j,0),new Triple(j*3,0));
		//double[] Testes = s.solveIDAStarTestes(new Triple(j,0),new Triple(j*3,0));
		//double[] Testes = s.solveTestes(new Triple(j,0),new Triple(j*3,0));

		while(it.hasNext()) {
			BestFirst.State i = it.next();
			System.out.println(i.toString());
			System.out.println("Heuristica: " + i.getH(new Triple(j*3,0)));
			if (!it.hasNext()) {
				System.out.println();
				System.out.println((int)i.getG());
			}
		}

		/*DecimalFormat formato = new DecimalFormat("0.00");
			System.out.println();
			System.out.println("Nos expandidos : " + (int)Testes[0]);
			System.out.println("Nos generados : " + (int)Testes[1]);
			System.out.println("Comprimento da solucao : " + (int)Testes[2]);
			System.out.println("Penetrancia : " + formato.format(Testes[3]));
			System.out.println();*/

		sc.close();
	}
}
