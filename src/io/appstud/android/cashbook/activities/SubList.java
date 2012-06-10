package io.appstud.android.cashbook.activities;

import io.appstud.android.cashbook.CashBook;
import io.appstud.android.cashbook.R;
import io.appstud.android.cashbook.helpers.Entry;
import io.appstud.android.cashbook.helpers.EntryAdapter;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ListActivity;
import android.os.Bundle;

public class SubList extends ListActivity {

	// private static String TAG = "SubList";

	private CashBook cashBook;
	List<Entry> entries;
	EntryAdapter adapter;
	long startDate;
	long endDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cashBook = (CashBook) getApplication();
		setContentView(R.layout.cashbook_date);
		Bundle bundle = getIntent().getExtras();
		startDate = bundle.getLong("START_DATE");
		endDate = bundle.getLong("END_DATE");
		entries = new ArrayList<Entry>();
		entries = cashBook.getCashBookDataSource().findEntriesByTimePeriod(
				startDate, endDate);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		setupList(entries);
	}

	private void setupList(List<Entry> entries) {
		adapter = new EntryAdapter(this, R.layout.row_entry_date, entries);
		setListAdapter(adapter);
	}

}
