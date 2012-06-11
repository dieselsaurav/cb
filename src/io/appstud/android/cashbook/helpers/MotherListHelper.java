package io.appstud.android.cashbook.helpers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import android.util.Log;

public class MotherListHelper {

	private static String TAG = "MotherListHelper";

	public List<MotherListItem> groupMotherList(List<Entry> entries,
			char groupBy) {
		List<MotherListItem> groupedList = new ArrayList<MotherListItem>();
		TreeSet<Long> ts = new TreeSet<Long>();
		long credit = 0;
		long debit = 0;

		for (Entry entry : entries) {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(entry.getDate());
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.clear(Calendar.MINUTE);
			c.clear(Calendar.SECOND);
			c.clear(Calendar.MILLISECOND);
			switch (groupBy) {
			case 'D':
				break;
			case 'W':
				c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
				break;
			case 'M':
				c.set(Calendar.DAY_OF_MONTH, 1);
				break;
			case 'Y':
				c.set(Calendar.DAY_OF_MONTH, 1);
				c.set(Calendar.MONTH, 0);
				break;
			default:
				Log.e(TAG, "GroupBy token not selected properly");
				break;
			}

			ts.add(c.getTimeInMillis());
		}

		Log.d(TAG, "HashSet values are :" + String.valueOf(ts));
		Log.d(TAG, "HashSet size is :" + ts.size());

		Iterator<Long> it = ts.iterator();

		while (it.hasNext()) {
			Calendar c = Calendar.getInstance();
			MotherListItem motherListItem = new MotherListItem();
			c.setTimeInMillis(it.next());
			motherListItem.setStartDate(c.getTimeInMillis());
			c.set(Calendar.HOUR_OF_DAY,
					c.getActualMaximum(Calendar.HOUR_OF_DAY));
			c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
			c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND));
			c.set(Calendar.MILLISECOND,
					c.getActualMaximum(Calendar.MILLISECOND));
			switch (groupBy) {
			case 'D':
				break;
			case 'W':
				c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 6);
				break;
			case 'M':
				c.set(Calendar.DAY_OF_MONTH,
						c.getActualMaximum(Calendar.DAY_OF_MONTH));
				break;
			case 'Y':
				c.set(Calendar.DAY_OF_MONTH,
						c.getActualMaximum(Calendar.DAY_OF_MONTH));
				c.set(Calendar.MONTH, c.getActualMaximum(Calendar.MONTH));
				break;
			default:
				Log.e(TAG, "GroupBy token not selected properly");
				break;

			}
			motherListItem.setEndDate(c.getTimeInMillis());

			for (Entry entry : entries) {
				if (entry.getDate() >= motherListItem.getStartDate()
						& entry.getDate() <= motherListItem.getEndDate()) {
					if (entry.getFlag()) {
						credit += entry.getAmount();
					} else
						debit += entry.getAmount();
				}
			}

			motherListItem.setCr(credit);
			motherListItem.setDb(debit);

			Log.d(TAG,
					motherListItem.getStartDate() + " - "
							+ motherListItem.getEndDate());

			groupedList.add(motherListItem);
		}

		return groupedList;
	}
}
