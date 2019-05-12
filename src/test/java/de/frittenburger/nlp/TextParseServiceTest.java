package de.frittenburger.nlp;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.nlp.bo.Annotation;
import de.frittenburger.nlp.bo.Snippet;
import de.frittenburger.nlp.impl.TextParseServiceImpl;
import de.frittenburger.nlp.interfaces.NlpService;
import de.frittenburger.nlp.interfaces.TextParseService;
import de.frittenburger.srt.impl.SrtMergeReader;
import de.frittenburger.srt.impl.SrtMergeReaderWrapper;

public class TextParseServiceTest {

	@Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
	
	@Test
	public void test() throws IOException {
		
		TextParseService service = new TextParseServiceImpl("de","es",new NlpService() {
			
			@Override
			public List<Annotation> parse(String text0) {
				
				List<Annotation> list = new ArrayList<Annotation>();
				Annotation a = new Annotation();
				a.setKey("testkey");
				a.setType("testtype");
				a.setLevel(0);

				list.add(a);
				
				Annotation b = new Annotation();
				b.setKey("testkey2");
				b.setType("entity");
				b.setLevel(-1);
				list.add(b);
				
				
				Annotation c = new Annotation();
				c.setKey("testkey3");
				c.setType("testtype3");
				c.setLevel(99);
				list.add(c);
				return list;
			}
		});
		
		ClassLoader classLoader = getClass().getClassLoader();

		File mergeFile = new File(classLoader.getResource("merge_de_es.txt").getFile());
		SrtMergeReader reader = new SrtMergeReader(mergeFile);
		SrtMergeReaderWrapper wrapper = new SrtMergeReaderWrapper(reader);

		
		service.analyse(wrapper, tempFolder.getRoot());
		
		File[] files = tempFolder.getRoot().listFiles();
		Arrays.sort(files);
		assertEquals(2,files.length);
		
		
		assertEquals("context.de.es.po",files[0].getName());
		assertEquals(6920,Files.readAllLines(files[0].toPath()).size());
		
		assertEquals("context.de.json",files[1].getName());
		List<Snippet> snippetsDe = new ObjectMapper().readValue( files[1], new TypeReference<List<Snippet>>(){});
		assertEquals(692,snippetsDe.size());
		
		
	
		
		
	

		
	}

}
