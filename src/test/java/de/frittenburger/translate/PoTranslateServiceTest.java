package de.frittenburger.translate;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import de.frittenburger.translate.impl.PoTranslateServiceImpl;
import de.frittenburger.translate.interfaces.PoTranslateService;
import de.frittenburger.translate.interfaces.TranslateService;

public class PoTranslateServiceTest {

	@Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
	
	@Test
	public void test() throws IOException {
		
		ClassLoader classLoader = getClass().getClassLoader();

		File poFile = new File(classLoader.getResource("context.es.po").getFile());
		File outPoFile = tempFolder.newFile("context.es.po");
		
		PoTranslateService service = new PoTranslateServiceImpl(new TranslateService() {

			@Override
			public String translate(String msgid) {
				
				switch(msgid)
				{
					case "caballeros": return "Herren";
					case "bienvenidos": return "willkommen";
					case "nombre": return "Name";
					case "es": return "ist";
					case "pianista": return "Pianist";
				}
				
				throw new RuntimeException(msgid);
			}});
		
		service.translate(poFile,outPoFile);
		
		assertEquals(poFile.length() + 30,outPoFile.length());
	
	}

}
