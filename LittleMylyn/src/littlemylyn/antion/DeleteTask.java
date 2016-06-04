package littlemylyn.antion;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;

import littlemylyn.model.Manager;
import littlemylyn.model.Node;
import taskContent.Task;

public class DeleteTask extends Action {
	private TreeViewer vt;
	private Node node;

	public DeleteTask(TreeViewer vt, Node node) {
		this.vt = vt;
		this.node = node;
		setText("delete this task");
	}

	public void run() {// 继承自Action的方法，动作代码写在此方法中
		Task task = (Task) node.getData();
		Manager.taskList.remove(task);
		vt.refresh();
	}
}
