import java.util.*;

public class Triple implements Ilayout, Cloneable{
	private int inicial;
	private int custo;

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

	@Override
	public List<Ilayout> children() {
		List<Ilayout> children = new ArrayList<>();
		children.add(new Triple(this.inicial + 1, 1));
		children.add(new Triple(this.inicial - 1, 2));
		children.add(new Triple(this.inicial * 2, 3));
		return children;
	}

	@Override
	public boolean isGoal(Ilayout l) {
		if(this.equals(l))
			return true;
		else
			return false;
	}

	@Override
	public double getG() {
		return custo;
	}

	@Override
	public double getHeuristic(Ilayout l) {
		if(inicial >= 0) {
			if(Math.abs(l.getConfig()/3) <= Math.abs(inicial) && Math.abs(inicial) <= Math.abs(l.getConfig()/2)) {
				return Math.abs(l.getConfig()/2-inicial);
			}
			else
				return Math.abs(l.getConfig()-inicial);
		}
		else {
			if(Math.abs((l.getConfig()/4 + l.getConfig()%4) - inicial) < Math.abs(l.getConfig()/2 - inicial)) {
				return Math.abs(Math.floor(l.getConfig()/4 + l.getConfig()%4) - inicial);
			}
			else if(Math.abs(inicial) <= Math.abs(Math.floor(l.getConfig()/2 + l.getConfig()%2)) + 1) {
				return Math.abs(Math.floor(l.getConfig()/2 + l.getConfig()%2) - inicial);
			}
			else {
				return Math.abs(l.getConfig() - inicial);	
			
			}	
		}
		
	}

	@Override
	public int getConfig() {
		return this.inicial;
	}

}
