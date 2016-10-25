package model;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.data.format.Formats;

public class Customer implements Comparable<Customer>{
	
	DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZZZZ");
	
	private int id;
	private String name;
	
	@Formats.DateTime(pattern = "yyyy-MM-ddTHH:mm:ssXXXX")	
	private DateTime dueTime;
	
	@Formats.DateTime(pattern = "yyyy-MM-ddTHH:mm:ssXXXX")
	private DateTime joinTime;

	public Customer() {

	}
	
	public Customer(int id, String name, DateTime duetime, DateTime jointime) {
		this.id = id;
		this.name = name;
		this.dueTime = duetime;
		this.joinTime = jointime;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getDuetime() {
		return dateFormat.print(this.dueTime);
	}
	
	@JsonIgnore
	public DateTime getDueTime() {
		return dueTime;
	}

	public void setDueTime(DateTime dueTime) {
		this.dueTime = dueTime;
	}

	
	public String getJointime() {
		return dateFormat.print(this.joinTime);
	}
	
	@JsonIgnore
	public DateTime getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(DateTime joinTime) {
		this.joinTime = joinTime;
	}

	
	@Override
	public int compareTo(Customer other) {
	  DateTimeComparator comparator = DateTimeComparator.getInstance();
	  return comparator.compare(dueTime, other.dueTime);
	}
}
