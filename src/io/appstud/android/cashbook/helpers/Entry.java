package io.appstud.android.cashbook.helpers;

import java.util.List;

public class Entry {

	private long id;
	private long amount;
	private long date;
	private boolean flag;
	private String desciption;
	private List<Tag> tags;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getDesciption() {
		return desciption;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

}
