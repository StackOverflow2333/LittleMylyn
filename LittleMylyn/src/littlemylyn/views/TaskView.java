package littlemylyn.views;

import java.util.ArrayList;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class TaskView extends ViewPart implements IResourceChangeListener {

	public static final String ID = "littlemylyn.views.TaskView";
	private TreeViewer viewer;

	@Override
	public void createPartControl(Composite arg0) {

		viewer = new TreeViewer(arg0, SWT.SINGLE);

		viewer.setContentProvider(new TaskListContentProvider());
		viewer.setLabelProvider(new TaskListLabelProvider());

		// viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		viewer.getControl().setFocus();
	}

	public TaskView() {
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// TODO Auto-generated method stub
		// 处理POST_CHANGE事件
		if (event.getType() != IResourceChangeEvent.POST_CHANGE) {
			return;
		}
		// 获得改变的对象
		IResourceDelta docDelta = event.getDelta();
		if (docDelta == null)
			return;
		// 创建改变的列表
		final ArrayList addChanged = new ArrayList();
		final ArrayList removed = new ArrayList();

		IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
			public boolean visit(IResourceDelta delta) throws CoreException {
				if ("java".equalsIgnoreCase(delta.getResource().getFileExtension())) {
					if (delta.getKind() == IResourceDelta.ADDED) {
						addChanged.add(delta.getResource());
						System.out.println("Added:" + delta.getResource());
					}
					// 处理IResourceDelta.CHANGED事件
					else if (delta.getKind() == IResourceDelta.CHANGED) {
						addChanged.add(delta.getResource());
						System.out.println("Changed:" + delta.getResource());
					}

					else if (delta.getKind() == IResourceDelta.REMOVED) {
						removed.add(delta.getResource());
						System.out.println("Removed:" + delta.getResource());
					}
				}

				return true;
			}
		};
		try {
			// 通过Visitor模式，遍历docDelta
			docDelta.accept(visitor);
		} catch (CoreException e) {

		}

		if (addChanged.size() != 0) {

		}
	}

}
