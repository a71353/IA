import java.util.*;

class BestFirst {
	
	private static final BestFirst.State New = null;
	protected Queue<State> abertos;
	private Map<Ilayout, State> fechados;
	private State actual;

	private int nodesExpandeduniform;

	private int nodesGenerateduniform;

	private int nodesExpandedAStarSearch;

	private int nodesGeneratedAStarSearch;

	private int nodesExpandedIDAStarSearch;

	private int nodesGeneratedIDAStarSearch;

	private int solutionLength;

	static class State {
	private Ilayout layout;
	private State father;
	private double g;
	
	public State(Ilayout l, State n) {
		layout = l;
		father = n;
		if (father!=null)
			g = father.g + l.getG();
		else g = 0.0;
	}
	
	public String toString() {
		return layout.toString();
	}
	
	public double getG() {
		return g;
	}
	
	public int hashCode() {
		return toString().hashCode();
	}
	
	public boolean equals (Object o) {
		if (o==null) 
			return false;
		if (this.getClass() != o.getClass()) 
			return false;
		State n = (State) o;
		return this.layout.equals(n.layout);
	}
	}
	
	final private List<State> sucessores(State n) {
		List<State> sucs = new ArrayList<>();
		List<Ilayout> children = n.layout.children();
			for(Ilayout e: children) {
				if (n.father == null || !e.equals(n.father.layout)){
					State nn = new State(e, n);
					sucs.add(nn);
				}
			}
		return sucs;
	}
	
	final private List<State> backtrackSolution(State n) {
        List<State> path = new ArrayList<>();
        State current = n;
		solutionLength = 0;
		while (current != null) {
            path.add(current);
			solutionLength++;
            current = current.father;
        }
        Collections.reverse(path);
        return path;
    }
	
	public Iterator<State> solve(Ilayout s, Ilayout goal) {
		abertos = new PriorityQueue<>(10,(s1, s2) -> (int) Math.signum(s1.getG()-s2.getG()));
		fechados = new HashMap<> ();
		abertos.add(new State(s, null));
		nodesExpandeduniform=0;
		nodesGenerateduniform=0;
		List<State> sucs;
		while (true) {
			if ( abertos.isEmpty()) {
				break;
			}
			actual = abertos.remove();
			nodesExpandeduniform++;
			if (actual.equals(new State(goal, null))) {
				return backtrackSolution(actual).iterator();
			}
			else {
				sucs = sucessores(actual);
				fechados.put(actual.layout, actual);
				for(State suc : sucs) {
					if ((!fechados.containsValue(suc) && !abertos.contains(suc))) {
						abertos.add(suc);
						nodesGenerateduniform+=3;
					}
					if (abertos.contains(suc)) {
						for(State a : abertos) {
							if(a.equals(suc) && suc.getG() < a.getG()) {
								abertos.remove(a);
								abertos.add(suc);
								break;
							}
						}
					}
					if (fechados.containsValue(suc) && fechados.get(suc.layout).getG() < suc.getG()) {
						fechados.replace(suc.layout, suc);
					}
				}
			}
		}
		return null;
	 }
	
	public Iterator<State> solveAStar(Ilayout s, Ilayout goal) {
		abertos = new PriorityQueue<>(10, (s1, s2) -> {
	        double f1 = Math.round(s1.getG() + s1.layout.getHeuristic(goal));
	        double f2 = Math.round(s2.getG() + s2.layout.getHeuristic(goal));
	        return (int) Math.signum(f1 - f2);
	    });
		fechados = new HashMap<> ();
		abertos.add(new State(s, null));
		List<State> sucs;
		nodesExpandedAStarSearch=0;
		nodesGeneratedAStarSearch=0;
		while (true) {
			if( abertos.isEmpty()) {
				break;
			}
			actual = abertos.remove();
			nodesExpandedAStarSearch++;
			if (actual.equals(new State(goal, null))) {
				return backtrackSolution(actual).iterator();
			}
			else {
				sucs = sucessores(actual);
				fechados.put(actual.layout, actual);
				for(State suc : sucs) {
					if ((!fechados.containsValue(suc) && !abertos.contains(suc))) {
						nodesGeneratedAStarSearch+=3;
						abertos.add(suc);
					}
				}
			}
		}
		return null;
	 }
	
	//@see https://chat.openai.com/share/83b2629a-edc0-42fe-b015-0b9cf1786218
	public Iterator<State> solveIDAStar(Ilayout start, Ilayout goal) {
	    actual = new State(start, null);
	    double threshold = actual.layout.getHeuristic(goal) + actual.getG();
	    Stack<State> stack = new Stack<>();
		nodesExpandedIDAStarSearch = 0;
		nodesGeneratedIDAStarSearch = 0;

	    while (true) {	        
	        stack.clear();
	        stack.push(actual);
	        double min = Double.MAX_VALUE;
	        
	        while (!stack.isEmpty()) {
	            State node = stack.pop();
				nodesExpandedIDAStarSearch++;

	            double estimate = node.getG() + node.layout.getHeuristic(goal);
	            if (estimate > threshold) {
	                min = Math.min(estimate, min);
	            } else if (node.layout.isGoal(goal)) {
	                actual = node;
	                return backtrackSolution(actual).iterator();
	            } else {
                    for (State suc : sucessores(node)) {
						nodesGeneratedIDAStarSearch += 3;
                        stack.push(suc);
                    }
                }
            }
            if (min == Double.MAX_VALUE) {
                return null; 
            }
            threshold = min;
        }
	}

	public int getNodesExpandeduniform() {
		return nodesExpandeduniform;
	}

	public int getNodesGenerateduniform(){
		return nodesGenerateduniform;
	}

	public int getNodesExpandedAStarSearch() {
		return nodesExpandedAStarSearch;
	}

	public int getNodesGeneratedAStarSearch(){
		return nodesGeneratedAStarSearch;
	}

	public int getNodesExpandedIDAStarSearch() {
		return nodesExpandedIDAStarSearch;
	}

	public int getNodesGeneratedIDAStarSearch(){
		return nodesGeneratedIDAStarSearch;
	}

	public int getSolutionLengthIDAStarSearch(){
		return solutionLength;
	}
}