package de.fhbielefeld.pmt.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * A entity object, like in any other Java application. In a typical real world
 * application this could for example be a JPA entity.
 */
@SuppressWarnings("serial")
public class Employee implements Serializable, Cloneable {

	private Long personalNr;
	private String firstName = "";
	private String lastName = "";
	private EmployeeStatus status;
	

	public Long getPersonalNr() {
		return personalNr;
	}

	public void setPersonalNr(Long personalNr) {
		this.personalNr = personalNr;
	}
	
	public String getPersonalNrAsString() {
		return String.valueOf(personalNr);
	}

	public void setPersonalNrAsString(String personalNr) {
		this.personalNr = Long.parseLong(personalNr);
	}

	/**
	 * Gibt den Wert der Variable status zurück
	 *
	 * @return the value of status
	 */
	public EmployeeStatus getStatus() {
		return status;
	}

	/**
	 * Setzt den Wert von status
	 *
	 * @param status
	 *            neuer Wert von status
	 */
	public void setStatus(EmployeeStatus status) {
		this.status = status;
	}

	/**
	 * Gibt den Wert der Variable lastName zurück
	 *
	 * @return den Wert von Lastname
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setzt den Wert von lastName
	 *
	 * @param lastName
	 *            neuer Wert von lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gibt den Wert der Variable firstName zurück
	 *
	 * @return den Wert von firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setzt den Wert von firstName
	 *
	 * @param firstName
	 *            neuer Wert von firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean isPersisted() {
		return personalNr != null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.personalNr == null) {
			return false;
		}

		if (obj instanceof Employee && obj.getClass().equals(getClass())) {
			return this.personalNr.equals(((Employee) obj).personalNr);
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + (personalNr == null ? 0 : personalNr.hashCode());
		return hash;
	}

	@Override
	public Employee clone() throws CloneNotSupportedException {
		return (Employee) super.clone();
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}