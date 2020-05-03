package de.fhbielefeld.pmt.login.impl.model;

import de.fhbielefeld.pmt.domain.EmployeeService;
import de.fhbielefeld.pmt.login.ILoginModel;


public class LoginModel implements ILoginModel{
	
	private EmployeeService employeeService;
	
	
	public LoginModel(EmployeeService employeeService) {
		if (employeeService == null) {
			throw new NullPointerException("Undefinierter UserService!");
		}
		this.employeeService = employeeService;
	}
	

	@Override
	public boolean loginUser(String userId, String password) {
		return this.employeeService.checkCredentials(userId, password);
	}

}
