package de.frittenburger.nlp;

import static org.junit.Assert.*;

import org.junit.Test;

import de.frittenburger.nlp.impl.AnnotationMapper;

public class AnnotationMapperTest {

	@Test
	public void testGetValue() {
		
		AnnotationMapper mapper = new  AnnotationMapper("*","VAL");
		
		assertTrue(mapper.match("hello"));
		assertEquals("VAL",mapper.getValue());
		
	}

	
	@Test
	public void testExact() {
		
		AnnotationMapper mapper = new  AnnotationMapper("X:X",null);
		
		assertTrue(mapper.match("X:X"));
		assertFalse(mapper.match("X:Y"));
		assertFalse(mapper.match("Y:X"));

	}
	
	
	@Test
	public void testWildCard1() {
		
		AnnotationMapper mapper = new  AnnotationMapper("X:*",null);
		
		assertTrue(mapper.match("X:Xaa"));
		assertTrue(mapper.match("X:Yaa"));
		assertFalse(mapper.match("Y:Xaa"));

	}
	
	@Test
	public void testWildCard2() {
		
		AnnotationMapper mapper = new  AnnotationMapper("*:X",null);
		
		assertTrue(mapper.match("Xaa:X"));
		assertFalse(mapper.match("Xaa:Y"));
		assertTrue(mapper.match("Yaa:X"));

	}
	
	@Test
	public void testTwoWildCards() {
		
		AnnotationMapper mapper = new  AnnotationMapper("X*:X",null);
		
		assertTrue(mapper.match("Xaa:X"));
		assertFalse(mapper.match("Xaa:Y"));
		assertFalse(mapper.match("Yaa:X"));

	}
	
	@Test
	public void testWithSpecialChars() {
		
		AnnotationMapper mapper = new  AnnotationMapper("X:$*",null);
		
		assertTrue(mapper.match("X:$?"));
		assertTrue(mapper.match("X:$."));
		assertFalse(mapper.match("X:.$"));

	}
	
	
	
	
}
