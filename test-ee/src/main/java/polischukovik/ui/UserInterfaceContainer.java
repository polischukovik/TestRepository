package polischukovik.ui;

import java.util.List;

public class UserInterfaceContainer {
	
	private List<UserInterfaceSet> userInterfaceSets;

	public UserInterfaceContainer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserInterfaceContainer(List<UserInterfaceSet> userInterfaceSets) {
		super();
		this.userInterfaceSets = userInterfaceSets;
	}	

	public List<UserInterfaceSet> getUserInterfaceSets() {
		return userInterfaceSets;
	}

	public void setUserInterfaceSets(List<UserInterfaceSet> userInterfaceSets) {
		this.userInterfaceSets = userInterfaceSets;
	}
	

}
