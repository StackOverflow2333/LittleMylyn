package littlemylyn.views;

import java.util.ArrayList;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import littlemylyn.model.Node;
import taskContent.RelatedClass;
import taskContent.Task;

public class TaskListLabelProvider implements ILabelProvider {

	@Override
	public void addListener(ILabelProviderListener arg0) {

	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
		;
	}

	@Override
	public Image getImage(Object arg0) {
		return null;
	}

	@Override
	public String getText(Object arg0) {
		if (arg0 instanceof Node) {
			if (((Node) arg0).getData() instanceof Task)
				return ((Task) ((Node) arg0).getData()).getName();
			if (((Node) arg0).getData() instanceof String)
				return (String) ((Node) arg0).getData();
			if (((Node) arg0).getData() instanceof ArrayList) {
				return "Related Class[" + ((ArrayList<?>) ((Node) arg0).getData()).size() + "]";
			}
			if (((Node) arg0).getData() instanceof RelatedClass)
				return ((RelatedClass) ((Node) arg0).getData()).getName();
		}

		return null;
	}

}
