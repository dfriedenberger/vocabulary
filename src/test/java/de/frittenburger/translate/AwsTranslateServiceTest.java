package de.frittenburger.translate;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import de.frittenburger.translate.impl.AwsTranslateService;
import de.frittenburger.translate.impl.TranslateCache;
import de.frittenburger.translate.interfaces.TranslateService;

public class AwsTranslateServiceTest {

	 
	@Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void testOhneCache() throws IOException {
		
		
		File cacheFile = new File(tempFolder.getRoot(),"cache.txt");
		
		
		
		TranslateCache cache = new TranslateCache(cacheFile.getAbsolutePath());
		TranslateService service = new AwsTranslateService("es", "de", cache);
		
		
		assertEquals("Welt",service.translate("mundo"));
		assertEquals(13,cacheFile.length());
		
	}

	@Test
	public void testMitCache() throws IOException {
		
		
		File cacheFile = new File(tempFolder.getRoot(),"cache.txt");
		try(PrintWriter pw = new PrintWriter(cacheFile))
		{
			pw.println("mundo :: cacheValue");
		}
		
		
		TranslateCache cache = new TranslateCache(cacheFile.getAbsolutePath());
		TranslateService service = new AwsTranslateService("es", "de", cache);
		
		
		assertEquals("cacheValue",service.translate("mundo"));
		
	}
	
}
