package com.udemy.callbacks;

import java.util.Date;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;


public class ArticleListener
{
	

	@PrePersist
	public void before_persist_method(Article article)
	{
		article.setDate(new Date());
	}
	
	@PostPersist
	public void after_persist_method(Article article)
	{
		System.out.println("After persisting article...");
	}
}
