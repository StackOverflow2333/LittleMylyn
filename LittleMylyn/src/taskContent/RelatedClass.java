package taskContent;

import java.io.Serializable;

public class RelatedClass implements Serializable {
	private static final long serialVersionUID = -4679352654880221551L;
	private String location;
	private String name;

	public RelatedClass(String location, String name) {
		this.location = location;
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

}
