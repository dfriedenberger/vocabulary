package de.frittenburger.extract;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import de.frittenburger.extract.impl.UnzipServiceImpl;
import de.frittenburger.extract.interfaces.UnzipService;

public class UnzipServiceTest {

	@Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
	
	@Test
	public void test() {
		
		UnzipService service = new UnzipServiceImpl();
		
		ClassLoader classLoader = getClass().getClassLoader();

		File zipFile = new File(classLoader.getResource("test.zip").getFile());
		
		service.extract(zipFile, tempFolder.getRoot(), ".srt");
		assertTrue(new File(tempFolder.getRoot(),"/part.es.utf8.srt").length() > 10);
	}

}
