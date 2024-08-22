
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class Board implements Ilayout, Cloneable{
	
	private static final int dim = 3;
	private int board[][];
	
	public Board()
	{
		board = new int[dim][dim];
	}
	
	public Board(String str) throws IllegalStateException
	{
		if(str.length() != dim*dim)
		{
			throw new IllegalStateException("Invalid arg in Board constructor");
		}
		
		board = new int[dim][dim];
		int si = 0;
		
		for(int i = 0; i < dim; i++)
		{
			for(int j = 0; j < dim; j++)
			{
				board[i][j] = Character.getNumericValue(str.charAt(si++));
			}
		}
	}
	
	public String toConfig()
	{
		String result = "";
		
		for(int i = 0; i < dim; i++)
		{
			for(int j = 0; j < dim; j++)
			{
				result += String.valueOf(board[i][j]);
			}
		}
		
		return result;
	}
	
	public String toString() 
	{
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter ( writer ) ;
		int counter = 0;
		
		for(int i = 0; i < dim; i++)
		{
			for(int j = 0; j < dim; j++)
			{
				if( board[i][j] == 0 )
				{
					pw.append(" ");
				}
				else
				{
					pw.append(String.valueOf(board[i][j]));
				}
				
				counter++;
				
				if(counter == 3 )
				{
					pw.println();
					counter = 0;
				}
			}
		}
		
		return writer.toString();
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
		
		Board n = (Board) o;
		
		return this.toConfig().equals(n.toConfig());
	}
	
	public int hashCode()
	{
		return toString().hashCode();
	}
	
	public static String charSwap(String s, int index1, int index2)
	{
		String result = "";
		
		for(int i = 0; i < s.length(); i++)
		{
			if(i == index1)
			{
				result += s.charAt(index2);
			}
			else if(i == index2)
			{
				result += s.charAt(index1);
			}
			else
			{
				result += s.charAt(i);
			}
		}
		
		return result;
	}
	
	public List<Ilayout>children()
	{
		List<Ilayout> children = new ArrayList<>();
		
		for(int i = 0; i < dim; i++)
			
		{
			for(int j = 0; j < dim; j++)
			{
				if(board[i][j] == 0)
				{
					if( i-1 >= 0 )
					{
						String current = toConfig();
						
						int zeroPresentIndex = current.indexOf(String.valueOf(board[i][j])); //index presente do zero
						int zeroFutureIndex = current.indexOf(String.valueOf(board[i-1][j])); //index futuro do zero
						
						String result = charSwap(current, zeroPresentIndex, zeroFutureIndex);
						
						children.add(new Board(result));				
					}
					if( i+1 <= 2 )
					{
						String current = toConfig();
						
						int zeroPresentIndex = current.indexOf(String.valueOf(board[i][j])); //index presente do zero
						int zeroFutureIndex = current.indexOf(String.valueOf(board[i+1][j])); //index futuro do zero
						
						String result = charSwap(current, zeroPresentIndex, zeroFutureIndex);
						
						children.add(new Board(result));
					}
					if( j+1 <= 2 )
					{
						String current = toConfig();
						
						int zeroPresentIndex = current.indexOf(String.valueOf(board[i][j])); //index presente do zero
						int zeroFutureIndex = current.indexOf(String.valueOf(board[i][j+1])); //index futuro do zero
						
						String result = charSwap(current, zeroPresentIndex, zeroFutureIndex);
						
						children.add(new Board(result));
					}
					if( j-1 >= 0 )
					{
						String current = toConfig();
						
						int zeroPresentIndex = current.indexOf(String.valueOf(board[i][j])); //index presente do zero
						int zeroFutureIndex = current.indexOf(String.valueOf(board[i][j-1])); //index futuro do zero
						
						String result = charSwap(current, zeroPresentIndex, zeroFutureIndex);
						
						children.add(new Board(result));
					}
				}
			}
		}
		
		return children;
	}
	
	public boolean isGoal(Ilayout l)
	{
		return this.equals(l);
	}

	public int getG()
	{
		return 1;
	}
}
