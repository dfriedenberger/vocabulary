package de.frittenburger.nlp.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import de.frittenburger.nlp.interfaces.AnnotationWrapper;
import de.frittenburger.nlp.interfaces.WordFrequencyService;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class Factory {

	public static WordFrequencyService getWordFrequencyServiceInstance(String language) throws IOException 
	{
		
		ClassLoader cl = Factory.class.getClassLoader();
		
		String path = language+"/frequency.txt";
		try(InputStream is = cl.getResourceAsStream(path))
		{
			if(is == null) throw new IOException(path + " not found");
			return new WordFrequencyServiceImpl(is);
		}
		
	}

	
	public static StanfordCoreNLP getPipelineInstance(String language) throws IOException 
	{
	
		Properties properties = new Properties();
		ClassLoader cl = Factory.class.getClassLoader();
		
		String path = language+"/StanfordCoreNLP.properties";
		
		try(InputStream is = cl.getResourceAsStream(path))
		{
			if(is == null) throw new IOException(path + " not found");
	    	properties.load(is);
		}
    	
    	// build pipeline
    	StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
		return pipeline;
		
	}


	public static AnnotationWrapper getAnnotationWrapperInstance(String language) throws IOException
    {
	
		ClassLoader cl = Factory.class.getClassLoader();
		String path = language+"/nlp.txt";
		try(InputStream is = cl.getResourceAsStream(path))
		{
			if(is == null) throw new IOException(path + " not found");
			return new DefaultAnnotationWrapper(is);
		}
		
    }
}
