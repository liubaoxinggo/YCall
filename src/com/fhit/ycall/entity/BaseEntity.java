package com.fhit.ycall.entity;

import java.io.Serializable;

import com.google.gson.Gson;

public  class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7689470431466732310L;

	public String toGsonString(){
		return new Gson().toJson(this);
	}
}
