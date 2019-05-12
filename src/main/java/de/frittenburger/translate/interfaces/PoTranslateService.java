package de.frittenburger.translate.interfaces;

import java.io.File;
import java.io.IOException;

public interface PoTranslateService {

	void translate(File poFile, File outPoFile) throws IOException;

}
