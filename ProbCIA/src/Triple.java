import java.util.*;

/**	Classe que representa um inteiro e o seu custo
 	@author P2G4
	@version 28/10/2023
*/

public class Triple implements Ilayout, Cloneable{
	private int inicial;
	private int custo;

	/** Construtor da classe Triple	
		@param int j, int l	
	*/	
	
	public Triple (int j, int l) {
		this.inicial = j;
		this.custo = l;
	}

	@Override
	public int hashCode() {
		return Objects.hash(inicial);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Triple other = (Triple) obj;
		return inicial == other.inicial;
	}
	
	@Override
	public String toString() {
		return "" + this.inicial;
	}

	/* 	O metodo children retorna uma lista com os filhos do Triplo atual bem como o seu custo
		@return List<Ilayout> children
	*/
	
	@Override
	public List<Ilayout> children() {
		List<Ilayout> children = new ArrayList<>();
		children.add(new Triple(this.inicial + 1, 1));
		children.add(new Triple(this.inicial - 1, 2));
		children.add(new Triple(this.inicial * 2, 3));
		return children;
	}

	/* 	Verifica se o Triplo atual é igual ao layout l
		@param Ilayout l
		@return true/false
	*/
	
	@Override
	public boolean isGoal(Ilayout l) {
		if(this.equals(l))
			return true;
		else
			return false;
	}

	/* 	Getter para o custo
		@return double custo
	*/
	
	@Override
	public double getG() {
		return custo;
	}

	/* 	Este metodo vai calcular o valor da heuristica do no atual para um goal Ilayout l
		@param Ilayout l
		@return double
	*/

	@Override
	public double getHeuristic(Ilayout l) {
		if(inicial%2 == 0) {
			return l.getConfig()-inicial;
		}
		else {
			return l.getConfig()%inicial;
		}
	}

	/* 	Getter para o inicial
		@return int inicial
	*/
	
	@Override
	public int getConfig() {
		return this.inicial;
	}

}
