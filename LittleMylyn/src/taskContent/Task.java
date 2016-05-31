package taskContent;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Task implements Serializable
{
	private String name;
	private String taskClass;
	private String status;
	
	public Task(String name, String taskClass){
		this.name = name;
		this.taskClass = taskClass;
		status = "New";
	}
	
	//ͨ����ȡ�ļ��½�Task
	public Task(String name){
		
	}
	
	/*
	 * save()
	 * Ĭ�ϴ���·�� TaskList/name
	 * */
	public void save() throws IOException
	{
		String root = "TaskList";
		File folder = new File(root);
		if (!folder.isDirectory()) folder.mkdir();
		
		String fileName = root + "/" + name;
		File file = new File(fileName);
		
		if (!file.exists()){
			file.createNewFile();
		}
		
		//���濪ʼ���档����
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
	
	//����ĺ���ϣ���޸ģ���Ϊset�й���
	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
