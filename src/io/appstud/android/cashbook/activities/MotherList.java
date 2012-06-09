package io.appstud.android.cashbook.activities;

import io.appstud.android.cashbook.CashBook;
import io.appstud.android.cashbook.R;
import io.appstud.android.cashbook.helpers.Entry;
import io.appstud.android.cashbook.helpers.EntryAdapter;
import io.appstud.android.cashbook.helpers.Tag;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MotherList extends ListActivity {

	private static String TAG = "MotherList";

	private CashBook cashBook;
	List<Entry> entries;
	EntryAdapter adapter;

	protected Tag selectedTag;
	protected String selectedTimePeriod;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cashBook = (CashBook) getApplication();
		setContentView(R.layout.cashbook_date);
		entries = new ArrayList<Entry>();
		List<Tag> tags = new ArrayList<Tag>();

		tags = cashBook.getCashBookDataSource().findAllTags();
		entries = cashBook.getCashBookDataSource().findEntriesByTags(tags);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		setupList(entries);
	}

	private void setupList(List<Entry> entries) {
		adapter = new EntryAdapter(this, R.layout.row_entry_date, entries);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.motherlist_action_menu, menu);
		setupTagsSpinner(menu);
		setupTimeSpinner(menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case R.id.menu_add_entry:
			intent = new Intent(this, AddEntryActivity.class);
			intent.putExtra("ENTRY_FLAG", "NEW");
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

	private void setupTagsSpinner(Menu menu) {
		List<Tag> tags = cashBook.getCashBookDataSource().findAllTags();
		// get ActionView is mandatory for getting ids in actionbar
		Spinner tagsSpinner = (Spinner) menu.findItem(R.id.menu_tag_spinner)
				.getActionView();
		ArrayAdapter<Tag> tagAdapter = new ArrayAdapter<Tag>(this,
				android.R.layout.simple_list_item_1, tags);
		Tag t = new Tag();
		t.setTag(getString(R.string.all));
		tagAdapter.add(t);
		tagsSpinner.setAdapter(tagAdapter);

		int spinnerAllPos = tagAdapter.getPosition(t);
		tagsSpinner.setSelection(spinnerAllPos);

		tagsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				selectedTag = (Tag) parent.getItemAtPosition(pos);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});

	}

	private void setupTimeSpinner(Menu menu) {

		Spinner timeSpinner = (Spinner) menu.findItem(
				R.id.menu_timeperiod_spinner).getActionView();
		Spinner tagsSpinner = (Spinner) menu.findItem(R.id.menu_tag_spinner)
				.getActionView();

		ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getResources()
						.getStringArray(R.array.timeperiod_array));

		timeSpinner.setAdapter(timeAdapter);

		timeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				Calendar c = Calendar.getInstance();
				Date endDate = c.getTime();
				Date fistDateOfThisMonth = new Date(endDate.getYear(), endDate
						.getMonth(), 1);

				entries = cashBook.getCashBookDataSource()
						.findEntriesByTimePeriod(fistDateOfThisMonth.getTime(),
								endDate.getTime());
				setupList(entries);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
	}
}
