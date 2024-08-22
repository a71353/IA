import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception{

        Scanner sc = new Scanner(System.in);
        BestFirst s = new BestFirst();
        int x = sc.nextInt();
        int cost = 0;
        Iterator<BestFirst.State> it = s.solve(new Triple(x, cost), new Triple(x*3, cost));

        if (it==null)
        {
            System.out.println("no solution found");
        }
        else
        {
            while(it.hasNext())
            {
                BestFirst.State i = it.next();
                System.out.println(i);

                if (!it.hasNext())
                {
                    System.out.println();
                    System.out.println(i.getG());

                }
            }
        }
        sc.close();

    }

}
