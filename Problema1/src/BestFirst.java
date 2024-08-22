import java.util.*;

public class BestFirst {

    protected Queue<State> abertos;
    private Map<Ilayout, State> fechados;
    private State actual;
    private Ilayout objective;

    static class State
    {
        private Ilayout layout;
        private State father;
        private int g;


        public State(Ilayout l, State n)
        {
            layout = l;
            father = n;

            if (father!=null)
            {
                g = father.g + l.getG();
            }
            else
            {
                g = 0;
            }
        }

        public String toString()
        {
            return layout.toString();
        }

        public int getG()
        {
            return g;
        }

        public int hashCode()
        {
            return toString().hashCode();
        }

        public boolean equals(Object o)
        {
            if(o == null)
            {
                return false;
            }
            if( this.getClass() != o.getClass() )
            {
                return false;
            }

            State n = (State) o;

            return this.layout.equals(n.layout);
        }
    }

    final private List<State> sucessores(State n)
    {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();

        for(Ilayout e: children)
        {
            if(n.father == null || !e.equals(n.father.layout))
            {
                State nn = new State(e, n);
                sucs.add(nn);
            }
        }

        return sucs;
    }

    class IT implements Iterator<State>
    {
        State last;
        Stack<State> stack;

        IT()
        {
            last = actual;
            stack = new Stack<State>();

            while(last != null)
            {
                stack.push(last);
                last = last.father;
            }
        }

        public boolean hasNext()
        {
            return !stack.isEmpty();
        }

        public State next()
        {
            return stack.pop();
        }
    }

    final public Iterator<State> solve(Ilayout s, Ilayout goal)
    {
        objective = goal;
        abertos = new PriorityQueue<>(10, (s1, s2) -> (int) Math.signum(s1.getG()-s2.getG()));
        fechados = new HashMap<>();
        abertos.add(new State(s, null));
        List<State> sucs;

        boolean goalFound = false;

        while(goalFound == false)
        {
            if( abertos.isEmpty() )
            {
                throw new IllegalStateException("Erro");
            }

            actual = abertos.poll();

            if( actual.layout.isGoal(objective) )
            {
                goalFound = true;
                break;
            }
            else
            {
                sucs = sucessores(actual);
                fechados.put(actual.layout, actual);

                for(int i = 0; i < sucs.size(); i++)
                {
                    if( !fechados.containsKey(sucs.get(i).layout))
                    {
                        abertos.add(sucs.get(i));
                    }
                }
            }
        }
        Iterator<State> iterator = new IT();
        return iterator;
    }
}

