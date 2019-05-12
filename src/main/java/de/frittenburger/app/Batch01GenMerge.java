package de.frittenburger.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.extract.impl.UnzipServiceImpl;
import de.frittenburger.srt.impl.EncodingDetectorServiceImpl;
import de.frittenburger.srt.impl.LanguageDetectorServiceImpl;
import de.frittenburger.srt.impl.SrtMergerImpl;
import de.frittenburger.srt.impl.SubtitleMergeServiceImpl;
import de.frittenburger.srt.interfaces.SubtitleMergeService;

public class Batch01GenMerge {

	private static final Logger logger = LogManager.getLogger(UnzipServiceImpl.class);

	private static void genMerge(File inDir,File mergeFile) throws IOException {
		
		final SubtitleMergeService mergeService = 
				new SubtitleMergeServiceImpl(new SrtMergerImpl(),new EncodingDetectorServiceImpl(),
				new LanguageDetectorServiceImpl());

		final List<File> files = new ArrayList<>();
		//unzip or copy
		for(File file : inDir.listFiles())
		{
			if(file.getName().endsWith(".zip"))
			{
				//ignore
			}
			else if(file.getName().endsWith(".srt"))
			{
				files.add(file);
			}
			else
			{
				throw new IOException("Unknown type" + file.getName());
			}				
		}
		
		mergeService.merge(files, mergeFile);
		
		
	}
	
	private static void validateMerge(File mergeFile) throws IOException {

		int error = 0;
		int check = 0;
		
		for(String line : Files.readAllLines(mergeFile.toPath()))
		{
			if(line.startsWith("#error"))
				error++;
			if(line.startsWith("#check"))
				check++;
		}
		System.out.println(mergeFile.getName()+" error="+error+" check="+check);
		
	}
	
	public static void main(String[] args) {

		
		
		final File working = new File("working");
		final File in = new File(working,"in");
		final File out = new File(working,"out");


		for(File dir : in.listFiles())
		{
			if(!dir.isDirectory()) continue;
			
			String movie = dir.getName();
			File outDir = new File(out,movie);
			outDir.mkdir();
			
			File mergeFile = new File(outDir,movie+".mrg.txt");
			try {
				genMerge(dir,mergeFile);
				validateMerge(mergeFile);
			} 
			catch(IOException e)
			{
				logger.error(e);
			}
			
		}
		
		
		
	}


	

	

}
