package com.udemy.callbacks;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@EntityListeners(ArticleListener.class)
public class Article
{
	@Id
	@GeneratedValue
	@Column(name="id")
	private int article_id;
	
	private String article_name;
	
	private Date date;
	
	public Article()
	{
		
	}
	
	public Article(String article_name)
	{
		this.article_name = article_name;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getArticle_id() {
		return article_id;
	}

	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}

	public String getArticle_name() {
		return article_name;
	}

	public void setArticle_name(String article_name) {
		this.article_name = article_name;
	}
}
