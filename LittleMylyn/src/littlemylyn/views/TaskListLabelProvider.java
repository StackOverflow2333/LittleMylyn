package littlemylyn.views;

import java.util.Vector;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import taskContent.Task;

public class TaskListLabelProvider implements ILabelProvider {

	@Override
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getImage(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText(Object arg0) {
		// TODO Auto-generated method stub
		if (arg0 instanceof Node){
			if (((Node) arg0).getData() instanceof Task) return ((Task) ((Node) arg0).getData()).getName();
			if (((Node) arg0).getData() instanceof String) return (String) ((Node) arg0).getData();
			if (((Node) arg0).getData() instanceof Vector){
				return "related class";
			}
		}
		
	
		return null;
	}

}
