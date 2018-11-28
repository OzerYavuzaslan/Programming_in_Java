package com.udemy.mappedSuperClass;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractStudentPerson
{
	protected String drivingLicence;
	
	public AbstractStudentPerson()
	{
		
	}

	public String getDrivingLicence() {
		return drivingLicence;
	}

	public void setDrivingLicence(String drivingLicence) {
		this.drivingLicence = drivingLicence;
	}
}
