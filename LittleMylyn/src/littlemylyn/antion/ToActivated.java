package littlemylyn.antion;

import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;

import littlemylyn.model.Manager;
import littlemylyn.model.Node;
import taskContent.Task;

public class ToActivated extends Action {
	private TreeViewer vt;
	private Node node;

	public ToActivated(TreeViewer vt, Node node) {
		this.vt = vt;
		this.node = node;
		setText("to be activated");
	}

	public void run() {// 继承自Action的方法，动作代码写在此方法中
		Task task = (Task) node.getData();
		boolean hasActivated = false;
		ArrayList<Task> taskList = Manager.taskList;
		for (Task tem : taskList) {
			if (tem.getStatus().equals("Activated")) {
				hasActivated = true;
				break;
			}
		}
		if (hasActivated) {
			MessageDialog.openInformation(null, null, "There exist a ACTIVATED task, plaese finish it firstly");
		} else {
			task.setStatus("Activated");
			vt.refresh();
		}
	}
}
