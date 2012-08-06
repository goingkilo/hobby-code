package edi;

import java.util.List;

public class Document {
	
	boolean saved;
	
	String name;
	String path;
	String data;
	String displayName;
	
	List<String> diff;

	// encoding ?

	public Document() {
		this.data = "";
	}

	public Document(String name) {
		this.name = name;
		this.data = "";
	}

	public Document(boolean saved, String name, String path, String data) {
		this.saved = saved;
		this.name = name;
		this.path = path;
		this.data = data;
	}

}
