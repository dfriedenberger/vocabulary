package de.frittenburger.srt;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Test;

import de.frittenburger.srt.impl.LanguageDetectorServiceImpl;
import de.frittenburger.srt.interfaces.LanguageDetectorService;

public class LanguageDetectorServiceTest {

	@Test
	public void testSpanish() throws IOException {
		
		ClassLoader classLoader = getClass().getClassLoader();
			
	    File file = new File(classLoader.getResource("part.es.utf8.srt").getFile());
		byte[] data = Files.readAllBytes(file.toPath());
		
		LanguageDetectorService service = new LanguageDetectorServiceImpl();
	
		assertEquals("es",service.detect(new String(data,"UTF-8")));
		
	}

	
	@Test
	public void testGerman() throws IOException {
		
		ClassLoader classLoader = getClass().getClassLoader();
			
	    File file = new File(classLoader.getResource("part.de.utf8.srt").getFile());
		byte[] data = Files.readAllBytes(file.toPath());
		
		LanguageDetectorService service = new LanguageDetectorServiceImpl();
	
		assertEquals("de",service.detect(new String(data,"UTF-8")));
		
	}
}
