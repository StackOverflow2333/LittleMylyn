package littlemylyn.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import littlemylyn.Log;
import taskContent.RelatedClass;
import taskContent.Task;

public class Manager implements IResourceChangeListener {
	private static Manager manager;
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<ManagerListener> listeners = new ArrayList<ManagerListener>();
	public static ArrayList<Task> taskList = new ArrayList<Task>();

	private Manager() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}

	@SuppressWarnings("unchecked")
	public static Manager getManager() {
		if (manager == null) {
			manager = new Manager();
			try {
				String filename = "TaskList.dat";
				// read the vector from local place
				FileInputStream fis = new FileInputStream(filename);
				ObjectInputStream ois = new ObjectInputStream(fis);
				try {
					taskList = (ArrayList<Task>) ois.readObject();
					ois.close();
				} catch (ClassNotFoundException e) {
					ois.close();
				}
			} catch (FileNotFoundException e) {

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return manager;
	}

	public static void shutdown() {
		if (manager != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(manager);
			manager.saveTasks();
			manager = null;
		}
	}

	// 返回给TaskListContentProvider的getElements
	public Node[] getItems() {
		// 每次直接重建
		loadItems();
		return nodes.toArray(new Node[nodes.size()]);
	}

	private void loadItems() {
		nodes.clear();
		// 根据taskList构造树
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			String status = task.getStatus();
			String taskClass = task.getTaskClass();
			ArrayList<RelatedClass> relatedClasses = task.getRelatedClass();

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

	public void addManagerListener(ManagerListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	public void removeManagerListener(ManagerListener listener) {
		listeners.remove(listener);
	}

	public void fileTasksChanged() {
		ManagerEvent event = new ManagerEvent(this);
		for (Iterator<ManagerListener> iter = listeners.iterator(); iter.hasNext();)
			iter.next().itemsChanged(event);
	}

	public void saveTasks() {
		String filename = "TaskList.dat";
		// save the vector by serializable way
		try {
			FileOutputStream fileout = new FileOutputStream(filename);
			ObjectOutputStream objectout = new ObjectOutputStream(fileout);
			objectout.writeObject(taskList);
			objectout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// 处理POST_CHANGE事件
		if (event.getType() != IResourceChangeEvent.POST_CHANGE) {
			return;
		}
		// 获得改变的对象
		IResourceDelta docDelta = event.getDelta();
		if (docDelta == null)
			return;

		IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
			public boolean visit(IResourceDelta delta) throws CoreException {
				// 是java文件
				if ("java".equalsIgnoreCase(delta.getResource().getFileExtension())) {
					String name = delta.getResource().getName();
					String location = delta.getResource().getLocation().toString();
					// 若删除，则将所有task下关联该文件的删除
					if (delta.getKind() == IResourceDelta.REMOVED) {
						for (int i = 0; i < taskList.size(); i++) {
							Task tem = taskList.get(i);
							for (int j = 0; j < tem.getRelatedClass().size(); j++) {
								if (name.equals(tem.getRelatedClass().get(j).getName())) {
									tem.getRelatedClass().remove(j);
									break;
								}
							}
						}
					}
					// 若新增文件，则添加到当前激活的task的关联类下
					else if (delta.getKind() == IResourceDelta.ADDED) {
						for (int i = 0; i < taskList.size(); i++) {
							if (taskList.get(i).getStatus().equals("Activated")) {
								taskList.get(i).addRelatedClass(new RelatedClass(location, name));
								break;
							}
						}
					}
					// 若改变文件，则添加到当前激活的task的关联类下，若已有则不添加
					else if (delta.getKind() == IResourceDelta.CHANGED) {
						for (int i = 0; i < taskList.size(); i++) {
							if (taskList.get(i).getStatus().equals("Activated")) {
								boolean has = false;
								for (int j = 0; j < taskList.get(i).getRelatedClass().size(); j++) {
									if (name.equals(taskList.get(i).getRelatedClass().get(j).getName())) {
										has = true;
										break;
									}
								}
								if (!has) {
									taskList.get(i).addRelatedClass(new RelatedClass(location, name));
								}
								break;
							}
						}
					}
				}
				return true;
			}
		};
		try {
			// 通过Visitor模式，遍历docDelta
			docDelta.accept(visitor);
		} catch (CoreException e) {
			Log.logError(e);
		}

		fileTasksChanged();

	}

}
