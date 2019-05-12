package de.frittenburger.nlp.interfaces;

import java.util.List;

import de.frittenburger.nlp.bo.Annotation;

public interface NlpService {

	List<Annotation> parse(String text);


}
