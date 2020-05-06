package de.fhbielefeld.pmt.login.impl.model;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.math.NumberUtils;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Employee;
import de.fhbielefeld.pmt.domain.EmployeeService;
import de.fhbielefeld.pmt.login.ILoginModel;


public class LoginModel implements ILoginModel{
	
	private DatabaseService dbService;
	
	
	public LoginModel(DatabaseService dbService) {
		if (dbService == null) {
			throw new NullPointerException("Undefinierter DBService!");
		}
		this.dbService = dbService;
	}
	

	@Override
	public boolean loginUser(String userId, String password) {
		
		Employee employee = null;
		
		try {
			
			if (NumberUtils.isNumber(userId)) {
			employee = dbService.readSingleEmployee(Long.parseLong(userId));
			}
		}catch(NoResultException e) {
			System.out.println("Es existiert kein Benutzer mit dieser ID");
			return false;
		}
		
		if (employee != null && password.equals(employee.getPassword())) {
			return true;
		}else {
			return false;
		}
	}

}
