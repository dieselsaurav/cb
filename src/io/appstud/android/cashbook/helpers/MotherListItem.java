package io.appstud.android.cashbook.helpers;

import java.util.Date;

public class MotherListItem {

	private long db;
	private long cr;
	private long bal;
	private Date startDate;
	private Date endDate;

	public long getDb() {
		return db;
	}

	public void setDb(long db) {
		this.db = db;
	}

	public long getCr() {
		return cr;
	}

	public void setCr(long cr) {
		this.cr = cr;
	}

	public long getBal() {
		return bal;
	}

	public void setBal(long bal) {
		this.bal = bal;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
