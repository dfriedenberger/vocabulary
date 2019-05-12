package de.frittenburger.srt;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import de.frittenburger.srt.impl.SrtCluster;
import de.frittenburger.srt.impl.SrtRecord;
import de.frittenburger.srt.impl.SubtitleMergeServiceImpl;
import de.frittenburger.srt.interfaces.EncodingDetectorService;
import de.frittenburger.srt.interfaces.LanguageDetectorService;
import de.frittenburger.srt.interfaces.SrtMerger;
import de.frittenburger.srt.interfaces.SubtitleMergeService;

public class SubtitleMergeServiceTest {

	@Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
	
	@Test
	public void test() throws IOException {
		
		SubtitleMergeService service = new SubtitleMergeServiceImpl(new SrtMerger() {
			
			@Override
			public List<SrtCluster> merge(List<SrtRecord> records,
					List<SrtRecord> records2) {
				
				List<SrtCluster> srtCluster = new ArrayList<>();
				SrtCluster cl = new SrtCluster();
				srtCluster.add(cl);
				return srtCluster;
				
				
			}
		}, new EncodingDetectorService() {

		@Override
		public String detect(byte[] b) {
			return "utf8";
		}}, new LanguageDetectorService() {

				@Override
				public String detect(String string) {
					return "xx";
				}});
			
		final List<File> inputs = new ArrayList<>();

		ClassLoader classLoader = getClass().getClassLoader();
		
		inputs.add(new File(classLoader.getResource("part.es.utf8.srt").getFile()));
		inputs.add(new File(classLoader.getResource("part.de.utf8.srt").getFile()));
		
		File mergeFile = tempFolder.newFile("merge.txt");
		service.merge(inputs, mergeFile);
		
		
		assertTrue(mergeFile.length() > 10);	
	}

}
