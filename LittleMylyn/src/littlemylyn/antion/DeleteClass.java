package littlemylyn.antion;

import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;

import littlemylyn.model.Node;
import taskContent.RelatedClass;

public class DeleteClass extends Action {
	private TreeViewer vt;
	private Node node;

	public DeleteClass(TreeViewer vt, Node node) {
		this.vt = vt;
		this.node = node;
		setText("delete this class");
	}

	public void run() {// 继承自Action的方法，动作代码写在此方法中
		ArrayList<RelatedClass> tem = (ArrayList<RelatedClass>) node.getFather().getData();
		tem.remove((RelatedClass) node.getData());
		vt.refresh();
	}
}
