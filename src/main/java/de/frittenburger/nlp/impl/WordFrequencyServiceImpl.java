package de.frittenburger.nlp.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.frittenburger.nlp.interfaces.WordFrequencyService;


public class WordFrequencyServiceImpl implements WordFrequencyService {

	
	private List<Set<String>> levels = new ArrayList<>();

	public WordFrequencyServiceImpl(InputStream is) throws IOException {
		
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8))) 
        	{
	        	while(true)
	        	{
	        		String line = reader.readLine();  
	        		if(line == null) break;
					if(line.trim().startsWith("#")) continue;
					Set<String> words = new HashSet<String>();
					
					for(String word :line.split(","))
					{
						String w = word.trim();
						if(w.isEmpty()) continue;
						words.add(w);
					}
					
					if(words.size() == 0) continue;
					levels.add(words);
	        	}
		}	
	}

	@Override
	public int level(String word) {
		
		int i = 0;
		while(i < levels.size())
		{
			if(levels.get(i).contains(word.toLowerCase())) break;
			i++;
		}
		
		return i;
	}
	
}
