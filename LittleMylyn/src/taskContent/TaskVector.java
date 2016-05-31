package taskContent;

import java.util.Vector;

public class TaskVector extends Vector<Task>
{
	//读取全部Task
	public TaskVector(){
		
	}
	
	//返回激活的Task编号，无激活则返回-1
	public int getActivity(){
		return -1;
	}
	
	//删除Task
	public void delete(int i){
		
	}
}
