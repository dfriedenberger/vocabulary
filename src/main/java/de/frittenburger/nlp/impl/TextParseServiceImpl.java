package de.frittenburger.nlp.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.nlp.bo.Annotation;
import de.frittenburger.nlp.bo.Snippet;
import de.frittenburger.nlp.interfaces.NlpService;
import de.frittenburger.nlp.interfaces.TextParseService;
import de.frittenburger.srt.impl.SrtMergeReaderWrapper;

public class TextParseServiceImpl implements TextParseService {

	
	private final NlpService nlpService;
	private final String lang0;
	private final String lang1;


	public TextParseServiceImpl(String lang0,String lang1,NlpService nlpService)
	{
		this.lang0 = lang0;
		this.lang1 = lang1;
		this.nlpService = nlpService;
	}
	
	
	
	public void analyse(SrtMergeReaderWrapper mergeWrapper, File path) throws IOException {

		
	
		List<String> languages = mergeWrapper.getLanguages();
		
		if(languages.size() != 2)
			throw new IOException("only can process 2 languages");
		if(!languages.contains(lang0))
			throw new IOException("merge data not contains language "+lang0);
		if(!languages.contains(lang1))
			throw new IOException("merge data not contains language "+lang1);

		List<Snippet> snippets = new ArrayList<>();
		List<String> poLines = new ArrayList<>();
		
		for(int i = 0;i < mergeWrapper.size();i++)
		{
			String text0 = mergeWrapper.getText(i,lang0);
			String text1 = mergeWrapper.getText(i,lang1);

			List<Annotation> annotations = nlpService.parse(text0);
			Snippet snippet = new Snippet();
			snippet.setText(text0);
			snippet.setTranslation(text1);
			snippet.setAnnotations(annotations);
			snippets.add(snippet);
			
			
			//poLines
			/*
			 * white-space
				#  translator-comments
				#. extracted-comments
				#: reference…
				#, flag…
				#| msgid previous-untranslated-string
				msgid untranslated-string
				msgstr translated-string
			*/
			for(Annotation annotation : annotations)
			{
				if(annotation.getLevel() < 0) continue; //TODO Entity?
				poLines.add("");
				poLines.add("#. "+text0);
				poLines.add("#. "+text1);
				poLines.add("msgid \""+annotation.getKey()+"\"");
				poLines.add("msgstr \""+"\"");
			}
			
			
		}
		
		
		//save as ContextFile
		File contextFile = new File(path,"context."+lang0+".json");
		new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(contextFile, snippets);

		//create PoFile
		File poFile = new File(path,"context."+lang0+"."+lang1+".po");
		Files.write(poFile.toPath(), poLines);
		
	}

}
