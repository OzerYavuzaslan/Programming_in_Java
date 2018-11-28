package com.udemy.inheritance;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="C")
public class Car extends Vehicle
{
	@Column(name="Speed")
	private int speed;
	
	public Car()
	{
		
	}

	public Car(String name, int speed)
	{
		//super(name);
		super.name = name;
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
