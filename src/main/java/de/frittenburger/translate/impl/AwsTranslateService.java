package de.frittenburger.translate.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.translate.interfaces.TranslateService;


public class AwsTranslateService implements TranslateService {

	private final Logger logger = LogManager.getLogger(AwsTranslateService.class);

	private final AmazonTranslate translate;
	private final String toLang;
	private final String fromLang;
	private final TranslateCache cache;
	
	public  AwsTranslateService(String fromLang, String toLang,TranslateCache cache) throws IOException
	{
		this.fromLang = fromLang;
		this.toLang = toLang;
		this.cache = cache;
		Map<String,String> config = new ObjectMapper().readValue(new File("config/aws.json"),new TypeReference<HashMap<String,String>>() {});
		
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(config.get("accessKeyId"), config.get("secretAccessKey"));
       
        translate = AmazonTranslateClient.builder()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(config.get("region"))
                .build();

	}
	@Override
	public String translate(String text) {
		
		
		String translatedText = cache.get(text);
		
		if(translatedText != null)
			return translatedText;
		
	    TranslateTextRequest request = new TranslateTextRequest()
        .withText(text)
        .withSourceLanguageCode(fromLang)
        .withTargetLanguageCode(toLang);

		TranslateTextResult result  = translate.translateText(request);
		translatedText = result.getTranslatedText();
		cache.add(text, translatedText);
	
		return translatedText;
	}

}
