package com.udemy.inheritanceTablePerClass;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Car3 extends Vehicle3
{
	@Column(name="Speed")
	private int speed;
	
	public Car3()
	{
		
	}

	public Car3(String name, int speed)
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
