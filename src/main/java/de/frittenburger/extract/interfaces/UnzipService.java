package de.frittenburger.extract.interfaces;

import java.io.File;

public interface UnzipService {

	File extract(File file, File path, String extensionFilter);

}
