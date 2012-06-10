package io.appstud.android.cashbook.activities;

import io.appstud.android.cashbook.CashBook;
import io.appstud.android.cashbook.R;
import io.appstud.android.cashbook.helpers.Entry;
import io.appstud.android.cashbook.helpers.MotherListAdapter;
import io.appstud.android.cashbook.helpers.MotherListHelper;
import io.appstud.android.cashbook.helpers.MotherListItem;

import java.util.ArrayList;
import java.util.List;

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

	// private static String TAG = "MotherList";

	private CashBook cashBook;
	MotherListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cashBook = (CashBook) getApplication();
		setContentView(R.layout.mother_list);

	}

	private void setUpList(List<MotherListItem> motherList) {
		adapter = new MotherListAdapter(this, R.layout.row_mother_list,
				motherList);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mother_list_action_menu, menu);
		Spinner groupBySpinner = (Spinner) menu.findItem(
				R.id.menu_spinner_groupby).getActionView();
		ArrayAdapter<String> groupBySpinnerAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1, getResources()
						.getStringArray(R.array.array_spinner_groupby));

		groupBySpinner.setAdapter(groupBySpinnerAdapter);

		groupBySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parenr, View view,
					int pos, long id) {
				List<MotherListItem> motherList = new ArrayList<MotherListItem>();
				MotherListHelper motherListHelper = new MotherListHelper();
				List<Entry> entries = cashBook.getCashBookDataSource()
						.findAllEntries();
				char groupBy;
				switch (pos) {
				case 0:
					groupBy = 'D';
					break;
				case 1:
					groupBy = 'W';
					break;
				case 2:
					groupBy = 'M';
					break;
				case 3:
					groupBy = 'Y';
					break;
				default:
					groupBy = 'X';
				}

				motherList = motherListHelper.groupMotherList(entries, groupBy);
				setUpList(motherList);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		return true;
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

}
