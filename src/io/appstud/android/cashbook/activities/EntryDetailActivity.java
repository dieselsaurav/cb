package io.appstud.android.cashbook.activities;

import io.appstud.android.cashbook.CashBook;
import io.appstud.android.cashbook.R;
import io.appstud.android.cashbook.helpers.Entry;
import io.appstud.android.cashbook.helpers.Tag;

import java.text.DateFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class EntryDetailActivity extends Activity {

	private long entryId;
	private Entry entry;
	private CashBook cashBook;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_entry);
		Bundle bundle = getIntent().getExtras();
		entryId = bundle.getLong("ENTRY_ID");
		cashBook = (CashBook) getApplication();
		entry = cashBook.getCashBookDataSource().findEntryById(entryId);
		setupViews();
	}

	private void setupViews() {

		TextView amountTextView = (TextView) findViewById(R.id.showAmount);
		amountTextView.setText(String.valueOf(entry.getAmount()));

		TextView dateTextView = (TextView) findViewById(R.id.showDate);
		String formattedDate = DateFormat.getDateInstance(DateFormat.MEDIUM)
				.format(entry.getDate());
		dateTextView.setText(formattedDate);

		TextView descTextView = (TextView) findViewById(R.id.showDesciption);
		String displayDescText = entry.getDesciption();
		if (displayDescText.equals(""))
			displayDescText = getString(R.string.no_description_found);

		descTextView.setText(displayDescText);

		TextView tagsTextView = (TextView) findViewById(R.id.showTags);
		String tagsString = "";

		for (Tag tag : entry.getTags()) {
			tagsString = tagsString + tag.getTag() + ", ";
		}

		if (tagsString.equals(""))
			tagsString = getString(R.string.no_tags_found);

		tagsTextView.setText(tagsString);

		if (entry.getFlag().equals(
				this.getResources().getString(R.string.credit))) {
			amountTextView.setTextColor(this.getResources().getColor(
					R.color.light_green));
		} else
			amountTextView.setTextColor(this.getResources().getColor(
					R.color.light_red));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_menu_entry_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			cashBook.goHome(cashBook);
			return true;
		case R.id.menuEntryDelete:
			showDeleteDialog();
			return true;
		case R.id.menuEntryEdit:
			Intent intent = new Intent(this, AddEntryActivity.class);
			intent.putExtra("ENTRY_ID", entryId);
			intent.putExtra("ENTRY_FLAG", "OLD");
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

	private void showDeleteDialog() {

		Builder deleteDialogBuilder = new Builder(this);
		deleteDialogBuilder.setTitle(getString(R.string.delete));
		deleteDialogBuilder
				.setMessage(getString(R.string.are_you_sure))
				.setPositiveButton(getString(android.R.string.yes),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								cashBook.getCashBookDataSource().deleteEntry(
										entryId);
								cashBook.goHome(cashBook);
							}
						})
				.setNegativeButton(getString(android.R.string.no), null);

		AlertDialog deleteDialog = deleteDialogBuilder.create();
		deleteDialog.show();

	}
}
