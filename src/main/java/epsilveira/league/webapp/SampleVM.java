package epsilveira.league.webapp;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

public class SampleVM {

	private String name;
	
	private int counter;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	@Command
	@NotifyChange({"counter", "name"})
	public void increment() {
		if (this.name != null) {
			this.name = new String(this.name);
		}
		this.counter ++;
	}
	
	
}
