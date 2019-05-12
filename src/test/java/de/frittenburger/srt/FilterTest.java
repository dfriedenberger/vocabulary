package de.frittenburger.srt;

import static org.junit.Assert.*;

import org.junit.Test;

import de.frittenburger.srt.impl.DefaultFilter;
import de.frittenburger.srt.interfaces.Filter;

public class FilterTest {

	@Test
	public void testSquareBrackets() {
		
		Filter filter = new DefaultFilter();
		
		assertEquals(" ",filter.filter("[golpeteo sobre un micrófono]"));
		assertEquals("",filter.filter("- [estática de micrófono]"));
		
	}
	
	@Test
	public void testOnlyUpperCases() {
		
		Filter filter = new DefaultFilter();
		
		assertEquals("",filter.filter("APRIL 2008"));
		
	}
	
	@Test
	public void testInteruption() {
		
		Filter filter = new DefaultFilter();
		
		assertEquals("Damas y caballeros",filter.filter("Damas y caballeros..."));
		assertEquals("esta noche les propongo un experimento.",filter.filter("...esta noche les propongo un experimento."));

	}

	
}
