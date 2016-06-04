package littlemylyn.model;

import java.util.EventObject;

public class ManagerEvent extends EventObject {
	private static final long serialVersionUID = 3697053173951102953L;

	public ManagerEvent(Manager source) {
		super(source);
	}
}
