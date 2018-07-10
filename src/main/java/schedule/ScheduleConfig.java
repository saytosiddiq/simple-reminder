package schedule;

import java.time.LocalDateTime;

public class ScheduleConfig {

	private String seconds;

	private String minutes;

	private String hours;

    private String dayOfMonth;
   
    private String month;

    private String dayOfWeek;

    private String year;

    private boolean twelveHours;
    
    public ScheduleConfig() {
    	LocalDateTime dateTime = LocalDateTime.now();
    	this.seconds = "0";
    	this.minutes = "0";
    	this.hours = "?";
    	this.dayOfMonth = String.valueOf(dateTime.getDayOfMonth());
    	this.month = String.valueOf(dateTime.getMonthValue());
    	this.dayOfWeek = "?";
    	this.year = String.valueOf(dateTime.getYear());
    }

	public String getSeconds() {
		return seconds;
	}

	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public boolean isTwelveHours() {
		return twelveHours;
	}

	public void setTwelveHours(boolean twelveHours) {
		this.twelveHours = twelveHours;
	}
}
