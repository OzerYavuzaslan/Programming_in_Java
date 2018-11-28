package com.udemy.inheritanceJT;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Car2 extends Vehicle2
{
	@Column(name="Speed")
	private int speed;
	
	public Car2()
	{
		
	}

	public Car2(String name, int speed)
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
