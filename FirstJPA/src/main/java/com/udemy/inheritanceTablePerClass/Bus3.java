package com.udemy.inheritanceTablePerClass;

import javax.persistence.Entity;
import javax.persistence.Column;

@Entity
public class Bus3 extends Vehicle3
{
	@Column(name="Number_of_Passengers")
	private int num_of_passenger;
	
	public Bus3(){
		
	}
	
	public Bus3(String name, int num_of_passenger)
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
