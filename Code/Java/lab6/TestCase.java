import static org.junit.Assert.*;

import org.junit.Test;

public class TestCase {

	@Test
	public void test() {
		fail("Not yet implemented");
		Main m=new Main();
		String s=m.callURL("http://api.fixer.io/latest");
		assertEquals(s,null);
	}

}
