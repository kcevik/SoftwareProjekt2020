package de.fhbielefeld.pmt.logout.impl.model;

import de.fhbielefeld.pmt.login.ILoginModel;

// Besser ILogoutModel?! -> Scheiss mal drauf
//TODO: Drauf scheissen
public class LogoutModel implements ILoginModel {

	@Override
	public boolean loginUser(String userId, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getUserRole(String userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserFirstName(String userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserLastName(String userID) {
		// TODO Auto-generated method stub
		return null;
	}

}
