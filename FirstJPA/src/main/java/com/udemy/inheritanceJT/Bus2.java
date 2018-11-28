package com.udemy.inheritanceJT;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Column;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Bus2 extends Vehicle2
{
	@Column(name="Number_of_Passengers")
	private int num_of_passenger;
	
	public Bus2(){
		
	}
	
	public Bus2(String name, int num_of_passenger)
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
