package com.revature.models;

import java.util.Objects;

public class Customers {
	
	private String initialDate;
	private String customerSource;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String occupation;
	private int timesTrained;
	private String trainedDate;
	private String notes;
	
	public Customers(String initialDate, String customerSource, String firstName, String lastName, String phone,
			String email, String occupation, int timesTrained, String trainedDate, String notes) {
		super();
		this.initialDate = initialDate;
		this.customerSource = customerSource;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.occupation = occupation;
		this.timesTrained = timesTrained;
		this.trainedDate = trainedDate;
		this.notes = notes;
	}
	
	
	public Customers() {
		super();
	}


	public String getInitialDate() {
		return initialDate;
	}
	public void setInitialDate(String initialDate) {
		this.initialDate = initialDate;
	}
	public String getCustomerSource() {
		return customerSource;
	}
	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public int getTimesTrained() {
		return timesTrained;
	}
	public void setTimesTrained(int timesTrained) {
		this.timesTrained = timesTrained;
	}
	public String getTrainedDate() {
		return trainedDate;
	}
	public void setTrainedDate(String trainedDate) {
		this.trainedDate = trainedDate;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}


	@Override
	public int hashCode() {
		return Objects.hash(customerSource, email, firstName, initialDate, lastName, notes, occupation, phone,
				timesTrained, trainedDate);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customers other = (Customers) obj;
		return Objects.equals(customerSource, other.customerSource) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(initialDate, other.initialDate)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(notes, other.notes)
				&& Objects.equals(occupation, other.occupation) && Objects.equals(phone, other.phone)
				&& timesTrained == other.timesTrained && Objects.equals(trainedDate, other.trainedDate);
	}


	@Override
	public String toString() {
		return "Customers [initialDate=" + initialDate + ", customerSource=" + customerSource + ", firstName="
				+ firstName + ", lastName=" + lastName + ", phone=" + phone + ", email=" + email + ", occupation="
				+ occupation + ", timesTrained=" + timesTrained + ", trainedDate=" + trainedDate + ", notes=" + notes
				+ "]";
	}
	
	
	
}
