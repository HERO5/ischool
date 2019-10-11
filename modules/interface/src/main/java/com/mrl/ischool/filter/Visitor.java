package com.mrl.ischool.filter;

public class Visitor {
	
	private String ip = null;
	private ArrayTime requestTimeQueue= new ArrayTime();

	public Visitor() {}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return this.ip;
	}

	public void setRequestTimeQueue(ArrayTime requestTimeQueue) {
		this.requestTimeQueue = requestTimeQueue;
	}

	public ArrayTime getRequestTimeQueue() {
		return this.requestTimeQueue;
	}
}
