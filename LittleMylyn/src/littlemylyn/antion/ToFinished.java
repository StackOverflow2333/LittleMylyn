package littlemylyn.antion;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;

import littlemylyn.model.Node;
import taskContent.Task;

public class ToFinished extends Action {
	private TreeViewer vt;
	private Node node;

	public ToFinished(TreeViewer vt, Node node) {
		this.vt = vt;
		this.node = node;
		setText("to be finished");
	}

	public void run() {// 继承自Action的方法，动作代码写在此方法中
		Task task = (Task) node.getData();
		if (task.getStatus().equals("Finished")) {
			MessageDialog.openInformation(null, null, "The task is already Finished");
		} else {
			task.setStatus("Finished");
			vt.refresh();
		}
	}
}