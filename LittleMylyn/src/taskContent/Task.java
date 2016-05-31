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
	
	//用读取文件的方式新建Task
	public Task(String name){
		
	}
	
	/*
	 * save()
	 * 默认存储地址 TaskList/name
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
		
		//接下来要完成存档
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
	
	//建议修改以下函数，因为set有规则
	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
