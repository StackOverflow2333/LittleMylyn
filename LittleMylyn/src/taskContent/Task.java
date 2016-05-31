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
	
	//通过读取文件新建Task
	public Task(String name){
		
	}
	
	/*
	 * save()
	 * 默认储存路径 TaskList/name
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
		
		//下面开始保存。。。
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
	
	//下面的函数希望修改，因为set有规则
	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
