package de.frittenburger.nlp;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import de.frittenburger.nlp.bo.Annotation;
import de.frittenburger.nlp.impl.NlpServiceImpl;
import de.frittenburger.nlp.interfaces.NlpService;

public class NlpServiceTest {

	@Test
	public void testDe() throws IOException {
		
		Map<String, String> unknown = new HashMap<>();
		NlpService service = new NlpServiceImpl("de",unknown);
		
		List<Annotation> annotations = service.parse("Ich wohne in Barcelona bei Hanna.");
		assertEquals(3,annotations.size());
		assertEquals(0, unknown.size());
		for(Annotation annotation : annotations)
				System.out.println(annotation);
	}

	
	@Test
	public void testEs() throws IOException {
		
		Map<String, String> unknown = new HashMap<>();
		NlpService service = new NlpServiceImpl("es",unknown);
		
		List<Annotation> annotations = service.parse("Vivo en Barcelona con Hanna.");
		assertEquals(3,annotations.size());
		assertEquals(0, unknown.size());
		for(Annotation annotation : annotations)
				System.out.println(annotation);
	}
	
	
	@Test
	public void testEn() throws IOException {
		
		Map<String, String> unknown = new HashMap<>();
		NlpService service = new NlpServiceImpl("en",unknown);
		
		List<Annotation> annotations = service.parse("I live in Barcelona with Hanna.");
		assertEquals(3,annotations.size());
		assertEquals(0, unknown.size());
		for(Annotation annotation : annotations)
				System.out.println(annotation);
	}
}
