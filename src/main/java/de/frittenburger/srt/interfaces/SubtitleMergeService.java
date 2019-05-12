package de.frittenburger.srt.interfaces;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface SubtitleMergeService {
	
	public void merge(List<File> files,File mergeFile) throws IOException;
	
}
