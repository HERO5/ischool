package com.mrl.ischool.filter;

public class ArrayTime {
	private long[] time;
	private int length = 10;
	public ArrayTime(){}

	public void init(){
		time = new long[length];
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int len) {
		this.length = len;
	}

	public long getLast() {
		return this.time[length-1];
	}

	public long getFirst() {
		return this.time[0];
	}

	public long getElement(int i) {
		return time[i];
	}

	public void insert(long nextTime) {
		if (this.getLast() != 0) {
			for(int i = 0 ;i < this.length-1;i++) {
				time[i] = time[i+1];
			}
			this.time[length-1] = nextTime;
		} else {
			int j=0;
			while(time[j] != 0) {
				j++;
			}
			time[j] = nextTime;
		}
	}
}
