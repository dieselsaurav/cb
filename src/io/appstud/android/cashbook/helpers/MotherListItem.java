package io.appstud.android.cashbook.helpers;

public class MotherListItem {

	private long db;
	private long cr;
	private long curBal;
	private long prevBal;
	private long startDate;
	private long endDate;
	private long id;

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

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCurBal() {
		return curBal;
	}

	public void setCurBal(long curBal) {
		this.curBal = curBal;
	}

	public long getPrevBal() {
		return prevBal;
	}

	public void setPrevBal(long prevBal) {
		this.prevBal = prevBal;
	}

}
