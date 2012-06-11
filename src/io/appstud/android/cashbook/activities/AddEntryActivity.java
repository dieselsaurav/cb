package io.appstud.android.cashbook.activities;

import io.appstud.android.cashbook.CashBook;
import io.appstud.android.cashbook.R;
import io.appstud.android.cashbook.helpers.Entry;
import io.appstud.android.cashbook.helpers.Tag;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class AddEntryActivity extends Activity {

	private static final String TAG = "AddEntryActivity";

	private CashBook cashBook;

	private static final int DATE_DIALOG_ID = 0;

	private Date date;
	private long entryId;
	private String entryFlag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cashBook = (CashBook) getApplication();
		setContentView(R.layout.add_entry);
		Bundle bundle = getIntent().getExtras();
		entryFlag = bundle.getString("ENTRY_FLAG");
		entryId = bundle.getLong("ENTRY_ID");
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setupViews();
	}

	private void setupViews() {
		setupDatePicker();
		setupTagSelector();
		if (!entryFlag.equals("NEW")) {
			Entry entry = cashBook.getCashBookDataSource().findEntryById(
					entryId);
			setEntryToUI(entry);
		}
	}

	private Entry getEntryFromUI() {
		EditText amount = (EditText) findViewById(R.id.addAmountET);
		Button date = (Button) findViewById(R.id.addDate);
		EditText description = (EditText) findViewById(R.id.desciptionEditText);
		ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleCredit);
		LinearLayout tagsLinearLayout = (LinearLayout) findViewById(R.id.tagsLinearLayout);
		Entry entry = new Entry();

		Date dateAdded = null;
		try {
			dateAdded = DateFormat.getDateInstance().parse(
					date.getText().toString());
		} catch (ParseException e) {
			Log.d(TAG, "Date cannot be parsed");
			e.printStackTrace();
		}

		entry.setAmount(Long.valueOf(amount.getText().toString()));
		entry.setDate(dateAdded.getTime());
		entry.setDesciption(description.getText().toString());
		entry.setFlag(toggleButton.isChecked());
		entry.setTags(getSelectedTags(tagsLinearLayout));
		return entry;
	}

	private void setEntryToUI(Entry entry) {
		EditText amount = (EditText) findViewById(R.id.addAmountET);
		Button date = (Button) findViewById(R.id.addDate);
		EditText description = (EditText) findViewById(R.id.desciptionEditText);
		ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleCredit);
		LinearLayout tagsLinearLayout = (LinearLayout) findViewById(R.id.tagsLinearLayout);
		List<Tag> tags = new ArrayList<Tag>();
		tags = entry.getTags();
		List<String> tagsList = new ArrayList<String>();
		for (Tag t : tags) {
			tagsList.add(t.getTag());
		}

		amount.setText(String.valueOf(entry.getAmount()));
		date.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(
				entry.getDate()));
		description.setText(entry.getDesciption());

		if (entry.getFlag()) {
			toggleButton.setChecked(true);
		} else {
			toggleButton.setChecked(false);
		}

		for (int i = 0; i < tagsLinearLayout.getChildCount(); i++) {
			View v = tagsLinearLayout.getChildAt(i);

			String t;
			if (v.getClass() == ToggleButton.class) {
				ToggleButton tg = (ToggleButton) v;
				t = tg.getText().toString();
				if (tagsList.contains(t)) {
					tg.setChecked(true);
				}
			}
		}

	}

	public void showAddTagDialog(View v) {

		DialogFragment addTagDialog = AddTagDialogFragment.newInstance();
		addTagDialog.show(getFragmentManager(),
				getResources().getString(R.string.create_new_tag));

	}

	private void setupTagSelector() {
		LinearLayout tagsLinearLayout = (LinearLayout) findViewById(R.id.tagsLinearLayout);
		ToggleButton toggleButton;
		List<Tag> tags = new ArrayList<Tag>();

		tags = cashBook.getCashBookDataSource().findAllTags();

		for (Tag tag : tags) {
			toggleButton = new ToggleButton(this);
			toggleButton.setText(tag.getTag());
			toggleButton.setTextOn(tag.getTag());
			toggleButton.setTextOff(tag.getTag());
			tagsLinearLayout.addView(toggleButton);
		}

	}

	private List<Tag> getSelectedTags(LinearLayout tagsLinearLayout) {
		List<Tag> selectedTags = new ArrayList<Tag>();
		tagsLinearLayout = (LinearLayout) findViewById(R.id.tagsLinearLayout);
		for (int i = 0; i < tagsLinearLayout.getChildCount(); i++) {
			View v = tagsLinearLayout.getChildAt(i);
			if (((ToggleButton) v).isChecked()) {
				Tag selectedTag = new Tag();
				selectedTag.setTag(((ToggleButton) v).getText().toString());
				selectedTags.add(selectedTag);
			}
		}
		return selectedTags;
	}

	private void setupDatePicker() {
		Button dateButton = (Button) findViewById(R.id.addDate);

		Calendar c = Calendar.getInstance();
		date = c.getTime();

		String today = DateFormat.getDateInstance(DateFormat.MEDIUM).format(
				c.getTime());
		dateButton.setText(today);
		dateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			Calendar c = Calendar.getInstance();
			c.set(year, monthOfYear, dayOfMonth);
			date = c.getTime();
			updateDate();
		}
	};

	private void updateDate() {
		Button dateButton = (Button) findViewById(R.id.addDate);
		Date updatedDate = new Date();
		updatedDate = date;
		String updatedDateStr = DateFormat.getDateInstance(DateFormat.MEDIUM)
				.format(updatedDate);
		dateButton.setText(updatedDateStr);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		switch (id) {
		case DATE_DIALOG_ID:
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			dialog = new DatePickerDialog(this, mDateSetListener,
					c.get(Calendar.YEAR), c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH));
			break;
		default:
			dialog = null;
		}

		return dialog;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_addmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		EditText amount = (EditText) findViewById(R.id.addAmountET);
		boolean error = false;
		Entry entry = getEntryFromUI();

		switch (item.getItemId()) {
		case android.R.id.home:
			cashBook.goHome(cashBook);
			return true;
		case R.id.actionbar_addmenu_save:
			if (amount.getText().toString().length() == 0) {
				amount.setError(getString(R.string.please_enter_an_amount));
				error = true;
			}
			if (!error) {
				if (entryFlag.equals("NEW")) {
					cashBook.getCashBookDataSource().createEntry(entry);
					cashBook.goHome(cashBook);
				} else {
					cashBook.getCashBookDataSource()
							.updateEntry(entryId, entry);
					cashBook.goHome(cashBook);
				}
			}
			return true;
		case R.id.actionbar_addmenu_cancel:
			cashBook.goHome(cashBook);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

}