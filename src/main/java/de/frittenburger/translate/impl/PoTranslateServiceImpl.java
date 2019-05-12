package de.frittenburger.translate.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;





import de.frittenburger.translate.interfaces.PoTranslateService;
import de.frittenburger.translate.interfaces.TranslateService;

public class PoTranslateServiceImpl implements PoTranslateService {

	private final Pattern pattern = Pattern.compile("^[msgidstr]+\\s+\"(.*)\"$");
	private final TranslateService translateService;

	public PoTranslateServiceImpl(TranslateService translateService) {
		this.translateService = translateService;
	}

	@Override
	public void translate(File poFile, File outPoFile) throws IOException {

		
		
		try(BufferedReader in = new BufferedReader(
				   new InputStreamReader(
		                      new FileInputStream(poFile), "UTF8"));
			PrintStream out = new PrintStream(outPoFile, "UTF8"))
		{
				        
				String line;
				String msgid = null;
				while ((line = in.readLine()) != null) {
					
	              
					if(line.startsWith("msgid"))
					{
						msgid = match(line);
					}
					if(line.startsWith("msgstr"))
					{
						String msgstr = match(line);
						if(msgstr.equals(""))
						{
							msgstr = translateService.translate(msgid);
							line = String.format("msgstr \"%s\"", msgstr);
						}
					}
					
				    out.println(line);
				}
				        
		}
		
	}

	private String match(String str) throws IOException {
		  Matcher matcher = pattern.matcher(str);
          if(!matcher.find())
        	  throw new IOException(str);
          return matcher.group(1);
	}

}
