package littlemylyn.wizards;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (task).
 */

public class TaskWizardPage extends WizardPage {

	private Text taskNameText;
	private Combo taskClass;
	private ISelection selection;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public TaskWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("Little Mylyn Task Creating");
		setDescription("This wizard creates a new task.");
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;

		Label label = new Label(container, SWT.NULL);
		label.setText("任务名称：");

		taskNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		taskNameText.setLayoutData(gd);
		taskNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("任务类别：");
		taskClass = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
		taskClass.add("Debug");
		taskClass.add("New Feature");
		taskClass.add("Refactor");
		taskClass.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				dialogChanged();
			}
		});

		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * initialize
	 */

	private void initialize() {

		taskNameText.setText("new_task");
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {

		String taskName = getTaskName();
		String taskClass = getTaskClass();

		if (taskName.length() == 0) {
			updateStatus("Task name must be specified");
			return;
		}
		if (taskName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus("Task name must be valid");
			return;
		}
		if (taskClass.length() == 0) {
			updateStatus("Task class must be specified");
			return;
		}

		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getTaskName() {
		return taskNameText.getText();
	}

	public String getTaskClass() {
		return taskClass.getText();
	}
}