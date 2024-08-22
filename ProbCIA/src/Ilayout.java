import java.util.List;

/**	Interface para implementar problemas de State space search
	@author P2G4
	@version 28/10/2023
*/

public interface Ilayout {
	
	/** Metodo para buscar os filhos do Ilayout que chama a funcao
		@return List<Ilayout>
	*/
	List<Ilayout> children();
	
	
	/** Metodo para veridicar se o Ilayout que chama a funcao é igual ao Ilayout l
	 	@param Ilayout l  
		@return true/false
	*/
	boolean isGoal(Ilayout l);
	
	
	/** Metodo para retornar o cust de ir desde a input config ate ao receiver
	@return double
	*/
	double getG();


	
	
	/** Metodo para retornar a heuristica desde o Ilayout que chama a funcao ate ao Ilayout l
	 	@param Ilayout l
		@return double 
	*/
	double getHeuristic(Ilayout l);
	
	/** Metodo para retornar a config do Ilayout que chama a funcao
		@return Config
	*/
	int getConfig();
}
