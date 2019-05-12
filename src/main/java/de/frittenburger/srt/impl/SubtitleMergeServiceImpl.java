package de.frittenburger.srt.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.srt.interfaces.EncodingDetectorService;
import de.frittenburger.srt.interfaces.LanguageDetectorService;
import de.frittenburger.srt.interfaces.SrtMerger;
import de.frittenburger.srt.interfaces.SubtitleMergeService;

public class SubtitleMergeServiceImpl implements SubtitleMergeService {

	
	private final Logger logger = LogManager.getLogger(SubtitleMergeServiceImpl.class);

	private final EncodingDetectorService encodingDetectorService;
	private final LanguageDetectorService languageDetectorService;
	
	private final SrtMerger srtMerger;

	
	public SubtitleMergeServiceImpl(SrtMerger srtMerger, EncodingDetectorService encodingDetectorService ,LanguageDetectorService languageDetectorService )
	{
		this.srtMerger = srtMerger;
		this.encodingDetectorService = encodingDetectorService;
		this.languageDetectorService = languageDetectorService;
	}

	@Override
	public void merge(List<File> files, File mergeFile) throws IOException {

		if(files.size() != 2)
			throw new IllegalArgumentException("can only process 2 files");
		
        @SuppressWarnings("unchecked")
		List<SrtRecord>[] records = new List[2]; 

		
		for(int i = 0;i < 2;i++)
		{
			File srtFile = files.get(i);
			byte[] data = Files.readAllBytes(srtFile.toPath());
			String encoding = encodingDetectorService.detect(data);
			
			if(encoding == null)
				throw new IllegalArgumentException("could not detect encoding "+srtFile.getName());
			
			
			String language = languageDetectorService.detect(new String(data,encoding));
			if(language == null)
				throw new IllegalArgumentException("could not detect language "+srtFile.getName());
			
			//read 
			if (logger.isInfoEnabled()) {
				logger.info("call reader {} {} {}" , srtFile.getName(), language, encoding);
			}
	
			//str - reader
			try(SrtReader srtReader = new SrtReader(srtFile,encoding))
			{
				records[i] = srtReader.read(new DefaultFilter(),language);
			}
			//dump			
		}
		
		//Merge and add to Bucket
		List<SrtCluster> clusterList = srtMerger.merge(records[0],records[1]);
		
		
		SrtMergeWriter writer = new SrtMergeWriter(mergeFile);
	
		for(SrtCluster c : clusterList)
		{
			writer.write(c);
		}
		writer.close();
	
		
		
	}

}
