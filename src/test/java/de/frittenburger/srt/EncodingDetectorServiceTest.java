package de.frittenburger.srt;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Test;

import de.frittenburger.srt.impl.EncodingDetectorServiceImpl;
import de.frittenburger.srt.interfaces.EncodingDetectorService;

public class EncodingDetectorServiceTest {

	@Test
	public void test() throws IOException {
		
	    ClassLoader classLoader = getClass().getClassLoader();
		
		File file = new File(classLoader.getResource("part.es.utf8.srt").getFile());
		byte[] data = Files.readAllBytes(file.toPath());
		EncodingDetectorService service = new EncodingDetectorServiceImpl();
		
		assertEquals("UTF-8",service.detect(data));
	}

}
