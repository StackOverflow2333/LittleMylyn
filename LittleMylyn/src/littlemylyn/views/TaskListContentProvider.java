package littlemylyn.views;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import taskContent.Task;
import taskContent.TaskVector;

public class TaskListContentProvider implements ITreeContentProvider
{
	private TaskVector taskList;
	public TaskListContentProvider(){
		taskList = new TaskVector();
		
		//以下三行测试用
		taskList.add(new Task("task111","debug"));
		taskList.add(new Task("task222","debug"));
		taskList.add(new Task("task333","refactor"));
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] getChildren(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getElements(Object arg0) {
		// TODO Auto-generated method stub
		return taskList.toArray();
	}

	@Override
	public Object getParent(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}