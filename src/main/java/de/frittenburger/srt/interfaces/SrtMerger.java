package de.frittenburger.srt.interfaces;

import java.util.List;

import de.frittenburger.srt.impl.SrtCluster;
import de.frittenburger.srt.impl.SrtRecord;

public interface SrtMerger {
	
	List<SrtCluster> merge(List<SrtRecord> records, List<SrtRecord> records2);

}
