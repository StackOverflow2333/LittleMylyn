package littlemylyn.antion;

import java.io.IOException;

import org.eclipse.jface.action.Action;

import littlemylyn.model.Node;
import taskContent.RelatedClass;

public class OpenClass extends Action {
	private Node node;

	public OpenClass(Node node) {
		this.node = node;
		setText("open this class");
	}

	public void run() {// 继承自Action的方法，动作代码写在此方法中
		String location = ((RelatedClass) node.getData()).getLocation();
		String command = "eclipse.exe ";
		command = command + "\"" + location.replace("/", "\\") + "\"";
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
