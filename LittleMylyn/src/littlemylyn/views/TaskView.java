package littlemylyn.views;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import littlemylyn.model.Node;
import taskContent.RelatedClass;
import taskContent.Task;
import littlemylyn.antion.*;
import littlemylyn.model.Manager;

public class TaskView extends ViewPart {
	private TreeViewer viewer;

	public static final String ID = "littlemylyn.views.TaskView";

	public TaskView() {

	}

	@Override
	public void createPartControl(Composite arg0) {
		viewer = new TreeViewer(arg0, SWT.SINGLE);
		viewer.setContentProvider(new TaskListContentProvider());
		viewer.setLabelProvider(new TaskListLabelProvider());
		viewer.setInput(Manager.getManager());

		//双击事件
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				// TODO Auto-generated method stub
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (!selection.isEmpty()) {
					Node node = (Node) selection.getFirstElement();
					if (node.getData() instanceof RelatedClass && node.getFather().getData() instanceof ArrayList<?>) {
						String location = ((RelatedClass) node.getData()).getLocation();
						System.out.println(location);

						String command = "eclipse.exe ";
						command = command + "\"" + location.replace("/", "\\") + "\"";
						try {
							Runtime.getRuntime().exec(command);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});

		//菜单事件
		final MenuManager mgr = new MenuManager();
		mgr.setRemoveAllWhenShown(true);

		mgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				IStructuredSelection selection = viewer.getStructuredSelection();
				if (!selection.isEmpty()) {
					Node node = (Node) selection.getFirstElement();
					if (node.getData() instanceof String) {
						// 如果状态为 new
						if (((String) node.getData()).equals("New")) {
							mgr.add(new ToActivated(viewer, node.getFather()));
							mgr.add(new ToFinished(viewer, node.getFather()));
						}
						// 如果状态为 activated
						else if (((String) node.getData()).equals("Activated"))
							mgr.add(new ToFinished(viewer, node.getFather()));
						// 如果状态为 finished
						else if (((String) node.getData()).equals("Finished"))
							mgr.add(new ToActivated(viewer, node.getFather()));

					}
					// 如果是关联的类
					else if (node.getData() instanceof RelatedClass) {
						mgr.add(new OpenClass(node));
						mgr.add(new DeleteClass(viewer, node));
					} else if (node.getData() instanceof Task) {
						mgr.add(new DeleteTask(viewer, node));
					}

				}
			}
		});
		viewer.getControl().setMenu(mgr.createContextMenu(viewer.getControl()));

	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

}
