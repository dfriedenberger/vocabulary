package de.frittenburger.nlp.bo;

import java.util.List;

public class Snippet {
	
	private String text;
	private String translation;
	private List<Annotation> annotations;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTranslation() {
		return translation;
	}
	public void setTranslation(String translation) {
		this.translation = translation;
	}
	public List<Annotation> getAnnotations() {
		return annotations;
	}
	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}
	
	
}
