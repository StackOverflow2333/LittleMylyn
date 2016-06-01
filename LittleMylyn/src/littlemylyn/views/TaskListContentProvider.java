package littlemylyn.views;

import java.util.Vector;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import taskContent.Task;
import taskContent.TaskVector;

public class TaskListContentProvider implements ITreeContentProvider {
	Vector<Node> nodes = new Vector<Node>();
	TaskVector taskList = new TaskVector();
	
	public TaskListContentProvider(){
		//以下三行测试用
		taskList.add(new Task("task111","Debug"));
		taskList.add(new Task("task222","Debug"));
		taskList.add(new Task("task333","Refactor"));
		
		
		for (int i=0;i<taskList.size();i++)
		{
			Task task = taskList.get(i);
			String status = task.getStatus();
			String taskClass = task.getTaskClass();
			Vector<String> relatedClasses = task.getRelatedClass();

			Node taskNode = new Node(task);
			Node statusNode = new Node(status);
			Node taskClassNode = new Node(taskClass);
			Node relatedClassesNode = new Node(relatedClasses);
			Node[] classNodes = new Node[relatedClasses.size()];
			for (int j = 0; j < relatedClasses.size(); j++) {
				classNodes[j] = new Node(relatedClasses.get(j));
			}

			// 构建树
			taskNode.set(null, new Node[] { statusNode, taskClassNode, relatedClassesNode });
			statusNode.set(taskNode, null);
			taskClassNode.set(taskNode, null);
			relatedClassesNode.set(taskNode, classNodes);
			for (int j = 0; j < classNodes.length; j++) {
				classNodes[j].set(relatedClassesNode, null);
			}
			// 保存树
			nodes.add(taskNode);

		}

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
		return ((Node) arg0).getSons();
	}

	@Override
	public Object[] getElements(Object arg0) {
		return nodes.toArray();
	}

	@Override
	public Object getParent(Object arg0) {
		return ((Node) arg0).getFather();
	}

	@Override
	public boolean hasChildren(Object arg0) {
		// TODO Auto-generated method stub
		if (((Node) arg0).getData() instanceof Task)
			return true;
		if (((Node) arg0).getData() instanceof Vector)
			return true;
		return false;
	}
}