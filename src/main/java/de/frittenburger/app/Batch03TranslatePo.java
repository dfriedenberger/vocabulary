package de.frittenburger.app;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.extract.impl.UnzipServiceImpl;
import de.frittenburger.translate.impl.AwsTranslateService;
import de.frittenburger.translate.impl.PoTranslateServiceImpl;
import de.frittenburger.translate.impl.TranslateCache;
import de.frittenburger.translate.interfaces.PoTranslateService;
import de.frittenburger.translate.interfaces.TranslateService;


public class Batch03TranslatePo {

	private static final Logger logger = LogManager.getLogger(UnzipServiceImpl.class);


	private static void translatePo(File poFileOrg,File poFileNew,String fromLang,String toLang) throws IOException {
		
	
		File cacheFile = new File("working/cache/cache_"+fromLang+"_"+toLang+".txt");
				
		TranslateCache cache = new TranslateCache(cacheFile.getAbsolutePath());
		
		
		TranslateService translateService = new AwsTranslateService(fromLang, toLang, cache);
		
		PoTranslateService service = new PoTranslateServiceImpl(translateService);
		
		service.translate(poFileOrg,poFileNew);

		
		
	}
	
	
	public static void main(String[] args) {

		final File working = new File("working");
		
		final File out = new File(working,"out");


		for(File dir : out.listFiles())
		{
			if(!dir.isDirectory()) continue;
						
			try {
				for(File poFile : dir.listFiles(new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".po");
					}}))
				{
					File poFileOrg = new File(dir,poFile.getName()+".org");
					
					String[] parts = poFile.getName().split("[.]");
					if(parts.length != 4 || !parts[3].equals("po"))
						throw new IOException(poFile.getName());
					
					Files.move(poFile.toPath(), poFileOrg.toPath(), StandardCopyOption.REPLACE_EXISTING);
					logger.info("translate {} from {} to{}",poFile,parts[1],parts[2]);
					translatePo(poFileOrg,poFile,parts[1],parts[2]);
				}
			} 
			catch(IOException e)
			{
				logger.error(e);
			}
			
			
		}
		
		
		
	}


	

	

}
