package party;

import java.io.Serializable;

public class Date implements Serializable {
	private int days;
	private int monthes;
	private int years;
	
	public Date(int days, int monthes, int years)throws DateException {
		if(days>31 || days<1) {
			throw new DaysException();
		}
		else {
			this.days=days;
		}
		if(monthes>12 || monthes<1) {
			throw new MonthesException();
		}
		else {
			this.monthes=monthes;
		}
		this.years=years;
	}
	
	
	public int getDays() {
		return days;
	}

	public int getMonthes() {
		return monthes;
	}

	
	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}

	public Date getDate() {
		return this;
	}

	@Override
	public String toString() {
		return days+"/"+monthes+"/"+years;
	}
	
	

}
