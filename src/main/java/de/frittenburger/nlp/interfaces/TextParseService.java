package de.frittenburger.nlp.interfaces;

import java.io.File;
import java.io.IOException;

import de.frittenburger.srt.impl.SrtMergeReaderWrapper;

public interface TextParseService {

	
	public void analyse(SrtMergeReaderWrapper wrapper, File path)  throws IOException;
	
}
