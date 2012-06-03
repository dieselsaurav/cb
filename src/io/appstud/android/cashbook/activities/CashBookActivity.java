package io.appstud.android.cashbook.activities;

import io.appstud.android.cashbook.CashBook;
import io.appstud.android.cashbook.R;
import io.appstud.android.cashbook.helpers.Entry;
import io.appstud.android.cashbook.helpers.EntryAdapter;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class CashBookActivity extends ListActivity {

	private CashBook cashBook;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.cashbook_date);
		cashBook = (CashBook) getApplication();

		List<Entry> entries = new ArrayList<Entry>();

		entries = cashBook.getCashBookDataSource().getAllEntries();

		EntryAdapter adapter = new EntryAdapter(this, R.layout.row_entry_date,
				entries);

		setListAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case R.id.menuAdd:
			intent = new Intent(this, AddEntryActivity.class);
			intent.putExtra("ENTRY_FLAG", "NEW");
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

}