package littlemylyn.views;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;



public class TaskView extends ViewPart {

	
	public static final String ID = "littlemylyn.views.TaskView";
	private TreeViewer viewer;
	
	@Override
	public void createPartControl(Composite arg0) {
		
		viewer = new TreeViewer(arg0, SWT.SINGLE);	
		
		viewer.setContentProvider(new TaskListContentProvider());	
		viewer.setLabelProvider(new TaskListLabelProvider());	
		
		
		
		//viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());		
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		viewer.getControl().setFocus();
	}	
	
	public TaskView(){
	}
	
	

}


