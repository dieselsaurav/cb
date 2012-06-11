package io.appstud.android.cashbook.activities;

import io.appstud.android.cashbook.CashBook;
import io.appstud.android.cashbook.R;
import io.appstud.android.cashbook.helpers.Entry;
import io.appstud.android.cashbook.helpers.EntryAdapter;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
		actionBar.setDisplayHomeAsUpEnabled(true);
		setupList(entries);
	}

	private void setupList(List<Entry> entries) {
		adapter = new EntryAdapter(this, R.layout.row_entry_date, entries);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mother_list_action_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			cashBook.goHome(cashBook);
			return true;
		case R.id.menu_add_entry:
			Intent intent = new Intent(this, AddEntryActivity.class);
			intent.putExtra("ENTRY_FLAG", "NEW");
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

}
