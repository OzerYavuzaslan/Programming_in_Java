package com.udemy.inheritance;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue(value="B")
public class Bus extends Vehicle
{
	@Column(name="Number_of_Passengers")
	private int num_of_passenger;
	
	public Bus(){
		
	}
	
	public Bus(String name, int num_of_passenger)
	{
		super.name = name;
		this.num_of_passenger = num_of_passenger;
	}

	public int getNum_of_passenger() {
		return num_of_passenger;
	}

	public void setNum_of_passenger(int num_of_passenger) {
		this.num_of_passenger = num_of_passenger;
	}
}
