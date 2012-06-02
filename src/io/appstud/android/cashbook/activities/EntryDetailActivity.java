package io.appstud.android.cashbook.activities;

import io.appstud.android.cashbook.CashBook;
import io.appstud.android.cashbook.R;
import io.appstud.android.cashbook.helpers.Entry;
import io.appstud.android.cashbook.helpers.Tag;

import java.text.DateFormat;

import android.app.Activity;
import android.os.Bundle;
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
		entry = cashBook.getCashBookDataSource().getEntryById(entryId);
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
}
