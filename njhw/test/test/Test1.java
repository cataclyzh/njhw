package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Test1 {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test1() {
		System.out.println((true & false) + ".");
		
		String s = "AAAAAAAAAA";
		System.out.println(s.replace("AAA", "LLL"));
	}

}
