package littlemylyn.views;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;

import littlemylyn.model.Node;
import taskContent.Task;
import littlemylyn.model.Manager;
import littlemylyn.model.ManagerEvent;
import littlemylyn.model.ManagerListener;

public class TaskListContentProvider implements ITreeContentProvider, ManagerListener {
	private TreeViewer viewer;
	private Manager manager;

	public TaskListContentProvider() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (TreeViewer) viewer;
		if (manager != null) {
			manager.removeManagerListener(this);
		}
		manager = (Manager) newInput;
		if (manager != null) {
			manager.addManagerListener(this);
		}
	}

	// 获取树查看器的根节点
	@Override
	public Object[] getElements(Object arg0) {
		return manager.getItems();
	}

	@Override
	public Object[] getChildren(Object arg0) {
		return ((Node) arg0).getSons();
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
		if (((Node) arg0).getData() instanceof ArrayList)
			return true;
		return false;
	}

	@Override
	public void itemsChanged(final ManagerEvent event) {
		if (Display.getCurrent() != null) {
			updateViewer(event);
			return;
		}
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				updateViewer(event);
			}
		});

	}

	private void updateViewer(ManagerEvent event) {
		viewer.getTree().setRedraw(false);
		try {
			viewer.refresh();
		} finally {
			viewer.getTree().setRedraw(true);
		}

	}
}