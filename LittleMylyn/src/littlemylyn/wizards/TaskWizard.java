package littlemylyn.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.operation.*;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.core.runtime.CoreException;

import java.io.*;

import org.eclipse.ui.*;

import taskContent.Task;

/**
 * This is a sample new wizard. Its role is to create a new file resource in the
 * provided container. If the container resource (a folder or a project) is
 * selected in the workspace when the wizard is opened, it will accept it as the
 * target container. The wizard creates one file with the extension "task". If a
 * sample multi-page editor (also available as a template) is registered for the
 * same extension, it will be able to open it.
 */

public class TaskWizard extends Wizard implements INewWizard {
	private TaskWizardPage page;
	private ISelection selection;

	/**
	 * Constructor for TaskWizard.
	 */
	public TaskWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new TaskWizardPage(selection);
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	public boolean performFinish() {

		final String taskName = page.getTaskName();
		final String taskClass = page.getTaskClass();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(taskName, taskClass, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * The worker method. It will find the container, create the file if missing
	 * or just replace its contents, and open the editor on the newly created
	 * file.
	 */

	private void doFinish(String taskName, String taskClass, IProgressMonitor monitor) throws CoreException {
		// create a sample file
		monitor.beginTask("Creating " + taskName, 2);

		String root = "TaskList";
		File folder = new File(root);
		if (!folder.isDirectory())
			folder.mkdir();
		String fileName = root + "/" + taskName;
		File taskFile = new File(fileName);
		if (!taskFile.exists()) {
			Task newTask = new Task(taskName, taskClass);

			try {
				newTask.save();
			} catch (Exception e) {
				e.printStackTrace();
				throwCoreException("Task Save error");
			}
		} else
			throwCoreException("Task has been exist");

		monitor.worked(1);

		monitor.done();
	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "LittleMylyn", IStatus.OK, message, null);
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
}