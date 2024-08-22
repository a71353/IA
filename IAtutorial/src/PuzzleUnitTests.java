import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.jupiter.api.Test;

import java.util.*;

public class PuzzleUnitTests {

	@Test
	public void testConstructor() {

		 Board b = new Board("023145678");
		 StringWriter writer = new StringWriter();
		 PrintWriter pw = new PrintWriter ( writer ) ;
		 pw.println(" 23");
		 pw.println("145"); 
		 pw.println("678");
		 assertEquals(b.toString(), writer.toString()); 
		 pw.close(); 
	}
	@Test 
	public void testConstructor2() {
		Board b = new Board("123485670");      
		StringWriter writer = new StringWriter();      
		PrintWriter pw = new PrintWriter (writer) ; 
		pw.println("123");      
		pw.println("485");      
		pw.println("67 "); 
		assertEquals(b.toString(), writer.toString());      
		pw.close();
	}
	
	@Test
	public void test_toConfig()
	{
		Board a = new Board("123456780");
		Board b = new Board("123045678");

		String aResult = a.toConfig();
		String bResult = b.toConfig();
		
		assertEquals("123456780", aResult);
		assertEquals("123045678", bResult);
	}
	
	@Test
	public void test_BoardEquals()
	{
		Board a = new Board("123456780");
		Board b = new Board("123045678");
		
		Board a2 = new Board("123456780");
		Board b2 = new Board("123045678");

		assert(a.equals(a2));
		assert(b.equals(b2));
	}
	
	@Test
	public void test_isGoal()
	{
		Board aB = new Board("123456780");
		Board bB = new Board("123045678");
		Board cB = new Board("102345678");
		Board dB = new Board("123450678");
		
		Ilayout aI = new Board("123456780");
		Ilayout bI = new Board("123045678");
		Ilayout cI = new Board("102345678");
		Ilayout dI = new Board("123450678");
		
		Board a2B = new Board("123456780");
		Board b2B = new Board("123045678");
		Board c2B = new Board("102345678");
		Board d2B = new Board("123450678");
		
		Ilayout a2I = new Board("123456780");
		Ilayout b2I = new Board("123045678");
		Ilayout c2I = new Board("102345678");
		Ilayout d2I = new Board("123450678");
		
		
		assert(aB.isGoal(a2B));
		assert(bB.isGoal(b2B));
		assert(cB.isGoal(c2B));
		assert(dB.isGoal(d2B));
		
		assert(aI.isGoal(a2I));
		assert(bI.isGoal(b2I));
		assert(cI.isGoal(c2I));
		assert(dI.isGoal(d2I));
		
		assert(aB.isGoal(a2I));
		assert(bB.isGoal(b2I));
		assert(cB.isGoal(c2I));
		assert(dB.isGoal(d2I));
		
		assert(aI.isGoal(a2B));
		assert(bI.isGoal(b2B));
		assert(cI.isGoal(c2B));
		assert(dI.isGoal(d2B));
	}
	
	@Test
	public void test_charSwap()
	{
		String a = "123456780";
		String b = "123045678";
		String c = "123456708";
		String d = "123456078";
		
		String aSwapped = "123056784";
		String bSwapped = "123745608";
		String cSwapped = "023456718";
		String dSwapped = "123456870";
		
		assertEquals(Board.charSwap(a, 8, 3), aSwapped);
		assertEquals(Board.charSwap(b, 3, 7), bSwapped);
		assertEquals(Board.charSwap(c, 7, 0), cSwapped);
		assertEquals(Board.charSwap(d, 6, 8), dSwapped);
		
	}
	
	@Test
	public void test_children()
	{
		Board aB = new Board("012345678");
		Board bB = new Board("102345678");
		Board cB = new Board("120345678");
		Board dB = new Board("123045678");
		Board eB = new Board("123405678");
		Board fB = new Board("123450678");
		Board gB = new Board("123456078");
		Board hB = new Board("123456708");
		Board iB = new Board("123456780");
		
		Ilayout aI = new Board("012345678");
		Ilayout bI = new Board("102345678");
		Ilayout cI = new Board("120345678");
		Ilayout dI = new Board("123045678");
		Ilayout eI = new Board("123405678");
		Ilayout fI = new Board("123450678");
		Ilayout gI = new Board("123456078");
		Ilayout hI = new Board("123456708");
		Ilayout iI = new Board("123456780");
		
		List<Ilayout> aB_children = new ArrayList<>();
		aB_children.add(new Board("312045678"));
		aB_children.add(new Board("102345678"));
		
		List<Ilayout> bB_children = new ArrayList<>();
		bB_children.add(new Board("142305678"));
		bB_children.add(new Board("120345678"));
		bB_children.add(new Board("012345678"));
		
		List<Ilayout> cB_children = new ArrayList<>();
		cB_children.add(new Board("125340678"));
		cB_children.add(new Board("102345678"));
		
		List<Ilayout> dB_children = new ArrayList<>();
		dB_children.add(new Board("023145678"));
		dB_children.add(new Board("123645078"));
		dB_children.add(new Board("123405678"));
		
		List<Ilayout> eB_children = new ArrayList<>();
		eB_children.add(new Board("103425678"));
		eB_children.add(new Board("123475608"));
		eB_children.add(new Board("123450678"));
		eB_children.add(new Board("123045678"));
		
		List<Ilayout> fB_children = new ArrayList<>();
		fB_children.add(new Board("120453678"));
		fB_children.add(new Board("123458670"));
		fB_children.add(new Board("123405678"));
		
		List<Ilayout> gB_children = new ArrayList<>();
		gB_children.add(new Board("123056478"));
		gB_children.add(new Board("123456708"));
		
		List<Ilayout> hB_children = new ArrayList<>();
		hB_children.add(new Board("123406758"));
		hB_children.add(new Board("123456780"));
		hB_children.add(new Board("123456078"));
		
		List<Ilayout> iB_children = new ArrayList<>();
		iB_children.add(new Board("123450786"));
		iB_children.add(new Board("123456708"));
		
		assertEquals(aB.children(), aB_children);
 		assertEquals(bB.children(), bB_children);
     	assertEquals(cB.children(), cB_children);
		assertEquals(dB.children(), dB_children);
		assertEquals(eB.children(), eB_children);
		assertEquals(fB.children(), fB_children);
		assertEquals(gB.children(), gB_children);
		assertEquals(hB.children(), hB_children);
		assertEquals(iB.children(), iB_children);
		
		List<Ilayout> aI_children = new ArrayList<>();
		aI_children.add(new Board("312045678"));
		aI_children.add(new Board("102345678"));
		
		List<Ilayout> bI_children = new ArrayList<>();
		bI_children.add(new Board("142305678"));
		bI_children.add(new Board("120345678"));
		bI_children.add(new Board("012345678"));
		
		List<Ilayout> cI_children = new ArrayList<>();
		cI_children.add(new Board("125340678"));
		cI_children.add(new Board("102345678"));
		
		List<Ilayout> dI_children = new ArrayList<>();
		dI_children.add(new Board("023145678"));
		dI_children.add(new Board("123645078"));
		dI_children.add(new Board("123405678"));
		
		List<Ilayout> eI_children = new ArrayList<>();
		eI_children.add(new Board("103425678"));
		eI_children.add(new Board("123475608"));
		eI_children.add(new Board("123450678"));
		eI_children.add(new Board("123045678"));
		
		List<Ilayout> fI_children = new ArrayList<>();
		fI_children.add(new Board("120453678"));
		fI_children.add(new Board("123458670"));
		fI_children.add(new Board("123405678"));
		
		List<Ilayout> gI_children = new ArrayList<>();
		gI_children.add(new Board("123056478"));
		gI_children.add(new Board("123456708"));
		
		List<Ilayout> hI_children = new ArrayList<>();
		hI_children.add(new Board("123406758"));
		hI_children.add(new Board("123456780"));
		hI_children.add(new Board("123456078"));
		
		List<Ilayout> iI_children = new ArrayList<>();
		iI_children.add(new Board("123450786"));
		iI_children.add(new Board("123456708"));
		
		assertEquals(aI.children(), aI_children);
 		assertEquals(bI.children(), bI_children);
     	assertEquals(cI.children(), cI_children);
		assertEquals(dI.children(), dI_children);
		assertEquals(eI.children(), eI_children);
		assertEquals(fI.children(), fI_children);
		assertEquals(gI.children(), gI_children);
		assertEquals(hI.children(), hI_children);
		assertEquals(iI.children(), iI_children);
		
	}
}
