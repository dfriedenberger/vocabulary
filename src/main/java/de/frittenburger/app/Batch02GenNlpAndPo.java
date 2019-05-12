package de.frittenburger.app;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.extract.impl.UnzipServiceImpl;
import de.frittenburger.nlp.impl.NlpServiceImpl;
import de.frittenburger.nlp.impl.TextParseServiceImpl;
import de.frittenburger.nlp.interfaces.TextParseService;
import de.frittenburger.srt.impl.SrtMergeReader;
import de.frittenburger.srt.impl.SrtMergeReaderWrapper;

public class Batch02GenNlpAndPo {

	private static final Logger logger = LogManager.getLogger(UnzipServiceImpl.class);

	private static void genNLPAndPo(File mergeFile,File outDir) throws IOException {
		
		
		final SrtMergeReader reader = new SrtMergeReader(mergeFile);
		final SrtMergeReaderWrapper wrapper = new SrtMergeReaderWrapper(reader);
		
		List<String> languages = wrapper.getLanguages();
		
		
		String lang0 = languages.get(0);
		String lang1 = languages.get(1);

		
		Map<String,String> unknown0 = new HashMap<>();
		final TextParseService textParseService0 = new TextParseServiceImpl(lang0,lang1,new NlpServiceImpl(lang0,unknown0));
		textParseService0.analyse(wrapper, outDir);
	
		Map<String,String> unknown1 = new HashMap<>();
		final TextParseService textParseService1 = new TextParseServiceImpl(lang1,lang0,new NlpServiceImpl(lang1,unknown1));
		textParseService1.analyse(wrapper, outDir);
		
	    //Workaround to catch all Annotations
		List<String> lines0 = unknown0.values().stream().sorted().collect(Collectors.toList());
		if(lines0.size() > 0) Files.write(new File(outDir,"unknown_"+lang0).toPath(),lines0);
		List<String> lines1 =unknown1.values().stream().sorted().collect(Collectors.toList());
		if(lines1.size() > 0) Files.write(new File(outDir,"unknown_"+lang1).toPath(),lines1);

		
		
	}
	
	
	public static void main(String[] args) {

		final File working = new File("working");

		final File in = new File("datasets");

		
		final File out = new File(working,"out");


		for(File dir : in.listFiles())
		{
			if(!dir.isDirectory()) continue;
			
			
			String movie = dir.getName();
			File outDir = new File(out,movie);
			outDir.mkdir();
			
			try {
				for(File mergeFile : dir.listFiles(new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".mrg.txt");
					}}))
				{
					
					genNLPAndPo(mergeFile,outDir);
				}
			} 
			catch(IOException e)
			{
				logger.error(e);
			}
			
			
		}
		
		
		
	}


	

	

}
