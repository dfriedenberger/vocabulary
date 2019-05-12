package de.frittenburger.srt;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import de.frittenburger.srt.impl.DefaultFilter;
import de.frittenburger.srt.impl.SrtCluster;
import de.frittenburger.srt.impl.SrtMergerImpl;
import de.frittenburger.srt.impl.SrtReader;
import de.frittenburger.srt.impl.SrtRecord;
import de.frittenburger.srt.interfaces.SrtMerger;

public class SrtMergerTest {

	@Test
	public void test() throws IOException {

ClassLoader classLoader = getClass().getClassLoader();
		
		File file1 = new File(classLoader.getResource("part.es.utf8.srt").getFile());
		File file2 = new File(classLoader.getResource("part.de.utf8.srt").getFile());
		
		SrtMerger merger = new SrtMergerImpl();
	
		try(SrtReader reader1 = new SrtReader(file1,"UTF-8");
				SrtReader reader2 = new SrtReader(file2,"UTF-8"))
		{
			List<SrtRecord> records = reader1.read(new DefaultFilter(), "l1");
			List<SrtRecord> records2 = reader2.read(new DefaultFilter(), "l2");
			List<SrtCluster> clusterList = merger.merge(records, records2);
			assertEquals(8,clusterList.size());
		}
		
	}

}
