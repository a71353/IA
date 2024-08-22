import java.util.*;

/**	Classe que tem os metodos com os algoritmos de procura
 	@author P2G4
	@version 28/10/2023
*/

class BestFirst {
	
	private static final BestFirst.State New = null;
	protected Queue<State> abertos;
	private Map<Ilayout, State> fechados;
	private State actual;
	
	/**	Classe que representa o estado atual, tem um layout, um estado pai e um custo
	 	@author P2G4
		@version 28/10/2023
	*/
	
	static class State {
	private Ilayout layout;
	private State father;
	private double g;

	private double h;


	/** Construtor da classe State
		@param Ilaout l, State n
	*/
	
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
	
	/* 	Getter do g
		@return double g
	*/

	public double getG() {
		return g;
	}

		public double getH(Ilayout goal) {
			return layout.getHeuristic(goal);
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
	
	/* 	Sucessores vai criar uma lista com os filhos do State n, onde o pai de n seja null ou o filho nao seja igual ao pai
		@param State n
		@return List<State> sucs
	*/

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
	
	/* 	backtrackSolution serve para meter numa lista o caminho desde o inicio ate a solucao
		@param State n
		@return List<State> path
	*/
	
	final private List<State> backtrackSolution(State n) {
        List<State> path = new ArrayList<>();
        State current = n;
        while (current != null) {
            path.add(current);
            current = current.father;
        }
        Collections.reverse(path);
        return path;
    }
	
	/* 	Este metodo usa o algoritmo Uniform cost search para achar o caminho ate a solucao a partir de s
		@param Ilayout s, Ilayout goal
		@return Iterator<State>
	*/
	
	public Iterator<State> solve(Ilayout s, Ilayout goal) {
		abertos = new PriorityQueue<>(10,(s1, s2) -> (int) Math.signum(s1.getG()-s2.getG()));
		fechados = new HashMap<> ();
		abertos.add(new State(s, null));
		List<State> sucs;
		while (true) {
			if ( abertos.isEmpty()) {
				break;
			}
			actual = abertos.remove();
			if (actual.equals(new State(goal, null))) {
				return backtrackSolution(actual).iterator();
			}
			else {
				sucs = sucessores(actual);
				fechados.put(actual.layout, actual);
				for(State suc : sucs) {
					if ((!fechados.containsValue(suc) && !abertos.contains(suc))) {
						abertos.add(suc);
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
	
	/* Serve para teste o numero de nos expandidos, generados, a distancia para a solucao e a penetrancia para o algoritmo Uniform cost search
		@param Ilayout s, Ilayout goal
		@return double[] result
	*/
	
	public double[] solveTestes(Ilayout s, Ilayout goal) {
		abertos = new PriorityQueue<>(10,(s1, s2) -> (int) Math.signum(s1.getG()-s2.getG()));
		fechados = new HashMap<> ();
		abertos.add(new State(s, null));
		List<State> sucs;
		double E = 0.0; // Expanded nodes
	    double G = 0.0; // Generated nodes
	    double L = 0.0; // Length of the solution
	    double P = 0.0; // Penetrance L/G
	    
		while (true) {
			if ( abertos.isEmpty()) {
				break;
			}
			actual = abertos.remove();
			E++;
			if (actual.equals(new State(goal, null))) {
				L = backtrackSolution(actual).size();
				P = L/G;
				double[] result = {E, G, L, P};	
				return result;
			}
			else {
				sucs = sucessores(actual);
				fechados.put(actual.layout, actual);
				for(State suc : sucs) {
					G++;
					if ((!fechados.containsValue(suc) && !abertos.contains(suc))) {
						abertos.add(suc);
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

	/* 	Este metodo usa o algoritmo A* para achar o caminho ate a solucao a partir de s
		@param Ilayout s, Ilayout goal
		@return Iterator<State>
	*/

	public Iterator<State> solveAStar(Ilayout s, Ilayout goal) {
		// Fila de prioridades para armazenar os estados abertos. A prioridade é determinada pela função de comparação.
		abertos = new PriorityQueue<>(10, (s1, s2) -> {
			// Calcula o custo estimado 'f' para cada estado. 'f' é a soma do custo real 'g' até o estado e a heurística 'h' do estado até o objetivo.
			double f1 = Math.round(s1.getG() + s1.layout.getHeuristic(goal));
			double f2 = Math.round(s2.getG() + s2.layout.getHeuristic(goal));
			// Ordena os estados na fila com base em seus valores 'f' (menor 'f' tem maior prioridade).
			return (int) Math.signum(f1 - f2);
		});

		// HashMap para armazenar os estados já explorados (fechados).
		fechados = new HashMap<> ();
		// Adiciona o estado inicial à fila de abertos.
		abertos.add(new State(s, null));
		List<State> sucs;

		// Loop principal de busca.
		while (true) {
			// Se não houver mais estados para explorar, termina o loop.
			if(abertos.isEmpty()) {
				break;
			}
			// Remove o estado com a maior prioridade (menor valor de 'f') da fila de abertos.
			actual = abertos.remove();

			// Se o estado atual for o objetivo, usa a função de backtracking para construir a solução.
			if (actual.equals(new State(goal, null))) {
				return backtrackSolution(actual).iterator();
			}
			else {
				// Gera todos os estados sucessores do estado atual.
				sucs = sucessores(actual);
				// Marca o estado atual como explorado, adicionando-o aos fechados.
				fechados.put(actual.layout, actual);

				// Processa cada estado sucessor.
				for(State suc : sucs) {
					// Se o sucessor não estiver em abertos ou fechados, adiciona-o à fila de abertos.
					if ((!fechados.containsValue(suc) && !abertos.contains(suc))) {
						abertos.add(suc);
					}
				}
			}
		}
		// Retorna null se uma solução não for encontrada.
		return null;
	}
	
	/* Serve para teste o numero de nos expandidos, generados, a distancia para a solucao e a penetrancia para o algoritmo A*
		@param Ilayout s, Ilayout goal
		@return double[] result
	*/
	
	public double[] solveAStarTestes(Ilayout s, Ilayout goal) {
		abertos = new PriorityQueue<>(10, (s1, s2) -> {
	        double f1 = Math.round(s1.getG() + s1.layout.getHeuristic(goal));
	        double f2 = Math.round(s2.getG() + s2.layout.getHeuristic(goal));
	        return (int) Math.signum(f1 - f2);
	    });
		fechados = new HashMap<> ();
		abertos.add(new State(s, null));
		List<State> sucs;
		double E = 0.0; // Expanded nodes
	    double G = 0.0; // Generated nodes
	    double L = 0.0; // Length of the solution
	    double P = 0.0; // Penetrance L/G
	    
		while (true) {
			if(abertos.isEmpty()) {
				break;
			}
			actual = abertos.remove();
			E++;
			if (actual.equals(new State(goal, null))) {
				L = backtrackSolution(actual).size();
				P = L/G;
				double[] result = {E, G, L, P};	
				return result;
			}
			else {
				sucs = sucessores(actual);
				fechados.put(actual.layout, actual);
				for(State suc : sucs) {
					G++;
					if ((!fechados.containsValue(suc) && !abertos.contains(suc))) {
						abertos.add(suc);
					}
				}
			}
		}
		return null;
	 }
	
	/* 	Este metodo usa o algoritmo IDA* para achar o caminho ate a solucao a partir de s
		@param Ilayout s, Ilayout goal
		@return Iterator<State>
		@see https://chat.openai.com/share/83b2629a-edc0-42fe-b015-0b9cf1786218
	*/
	
	public Iterator<State> solveIDAStar(Ilayout start, Ilayout goal) {
	    actual = new State(start, null);
	    double threshold = actual.layout.getHeuristic(goal) + actual.getG();
	    Stack<State> stack = new Stack<>();

	    while (true) {	        
	        stack.clear();
	        stack.push(actual);
	        double min = Double.MAX_VALUE;
	        
	        while (!stack.isEmpty()) {
	            State node = stack.pop();

	            double estimate = node.getG() + node.layout.getHeuristic(goal);
	            if (estimate > threshold) {
	                min = Math.min(estimate, min);
	            } else if (node.layout.isGoal(goal)) {
	                actual = node;
	                return backtrackSolution(actual).iterator();
	            } else {
                    for (State suc : sucessores(node)) {
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
	
	/* Serve para teste o numero de nos expandidos, generados, a distancia para a solucao e a penetrancia para o algoritmo IDA*
		@param Ilayout s, Ilayout goal
		@return double[] result
	*/
	
	public double[] solveIDAStarTestes(Ilayout start, Ilayout goal) {
	    actual = new State(start, null);
	    double threshold = actual.layout.getHeuristic(goal) + actual.getG();
	    Stack<State> stack = new Stack<>();
	    double E = 0.0; // Expanded nodes
	    double G = 0.0; // Generated nodes
	    double L = 0.0; // Length of the solution
	    double P = 0.0; // Penetrance L/G

	    while (true) {	        
	        stack.clear();
	        stack.push(actual);
	        double min = Double.MAX_VALUE;
	        
	        while (!stack.isEmpty()) {
	            State node = stack.pop();
	            E++;
	            double estimate = node.getG() + node.layout.getHeuristic(goal);
	            if (estimate > threshold) {
	                min = Math.min(estimate, min);
	            } else if (node.layout.isGoal(goal)) {
	                actual = node;
	                L = backtrackSolution(actual).size();
	                P = L/G;
	                double[] result = {E, G, L, P};	
	                return result;
	            } else {
                    for (State suc : sucessores(node)) {
                    	G++;
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
}