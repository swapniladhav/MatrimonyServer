package com.nisum.domain;

import java.io.Serializable;

public class MyClass implements Serializable{
	public String data;
	int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
 
	

}
