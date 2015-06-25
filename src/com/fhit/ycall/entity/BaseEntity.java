package com.fhit.ycall.entity;

import com.google.gson.Gson;

public  class BaseEntity {

	public String toGsonString(){
		return new Gson().toJson(this);
	}
}
