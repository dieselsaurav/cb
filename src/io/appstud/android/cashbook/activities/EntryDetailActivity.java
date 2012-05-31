package io.appstud.android.cashbook.activities;

import io.appstud.android.cashbook.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EntryDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_entry);
		setupViews();
	}

	private void setupViews() {
		TextView tv = (TextView) findViewById(R.id.textView1);
		Bundle bundle = getIntent().getExtras();
		long entryId = bundle.getLong("ENTRY_ID");
		tv.setText(String.valueOf(entryId));
	}

}
