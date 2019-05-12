package de.frittenburger.app;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.extract.impl.UnzipServiceImpl;
import de.frittenburger.extract.interfaces.UnzipService;

public class Batch00Unzip {

	private static final Logger logger = LogManager.getLogger(UnzipServiceImpl.class);

	private static void unzip(File dir) throws IOException {
		

		final UnzipService unzipService = new UnzipServiceImpl();
		//unzip or copy
		for(File file : dir.listFiles())
		{
			if(file.getName().endsWith(".zip"))
			{
				//unzip
				unzipService.extract(file, dir, ".srt");
			}
			else if(file.getName().endsWith(".srt"))
			{
				//ignore
			}
			else
			{
				throw new IOException("Unknown type" + file.getName());
			}				
		}
	}
	
	
	public static void main(String[] args) {

		
		
		final File working = new File("working");
		final File in = new File(working,"in");


		for(File dir : in.listFiles())
		{
			if(!dir.isDirectory()) continue;
			
			try {
				unzip(dir);
			} 
			catch(IOException e)
			{
				logger.error(e);
			}
			
		}
		
		
		
	}

	

}
