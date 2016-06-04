package taskContent;

import java.io.Serializable;
import java.util.ArrayList;

public class Task implements Serializable {
	private static final long serialVersionUID = -6428918215267823317L;
	private String name;
	private String taskClass;
	private String status;
	private ArrayList<RelatedClass> relatedClass;

	public Task(String name, String taskClass) {
		this.name = name;
		this.taskClass = taskClass;
		relatedClass = new ArrayList<RelatedClass>();
		status = "New";

	}

	public String getName() {
		return name;
	}

	public String getTaskClass() {
		return taskClass;
	}

	public String getStatus() {
		return status;
	}

	public ArrayList<RelatedClass> getRelatedClass() {
		return relatedClass;
	}

	public void addRelatedClass(RelatedClass reClass) {
		relatedClass.add(reClass);
	}

	public void removeRelatedClass(RelatedClass relatedClass) {
		this.relatedClass.remove(relatedClass);
	}

	public void setTaskClass(String taskClass) {
		if (taskClass.equals("Debug") || taskClass.equals("New Feature") || taskClass.equals("Refactor"))
			this.taskClass = taskClass;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
