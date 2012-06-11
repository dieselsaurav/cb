package io.appstud.android.cashbook.helpers;

import io.appstud.android.cashbook.R;
import io.appstud.android.cashbook.activities.EntryDetailActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EntryAdapter extends ArrayAdapter<Entry> {

	private Context context;
	private int layoutResourceId;
	private List<Entry> entries = new ArrayList<Entry>();
	private static final String TAG = "EntryAdapter";

	public EntryAdapter(Context context, int layoutResourceId,
			List<Entry> entries) {
		super(context, layoutResourceId, entries);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.entries = entries;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		EntryHolder holder = null;
		String dateFormatted;

		if (row == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = layoutInflater.inflate(layoutResourceId, parent, false);

			holder = new EntryHolder();
			holder.date = (TextView) row.findViewById(R.id.singleRowDate);
			holder.total = (TextView) row.findViewById(R.id.singleRowAmount);

			row.setTag(holder);
		} else {
			holder = (EntryHolder) row.getTag();
		}

		Entry entry = entries.get(position);
		dateFormatted = DateFormat.getDateInstance(DateFormat.MEDIUM).format(
				entry.getDate());

		holder.date.setText(dateFormatted);
		holder.total.setText(String.valueOf(entry.getAmount()));

		if (entry.getFlag()) {
			holder.total.setTextColor(context.getResources().getColor(
					R.color.light_green));
		} else
			holder.total.setTextColor(context.getResources().getColor(
					R.color.light_red));
		row.setOnClickListener(new OnRowClickListener(entry.getId()));
		return row;
	}

	static class EntryHolder {
		TextView date;
		TextView total;
	}

	private class OnRowClickListener implements OnClickListener {
		private long mEntryId;

		Intent intent = new Intent();

		public OnRowClickListener(long entryId) {
			mEntryId = entryId;
		}

		@Override
		public void onClick(View v) {
			intent = new Intent(context, EntryDetailActivity.class);
			intent.putExtra("ENTRY_ID", mEntryId);
			context.startActivity(intent);
			Log.d(TAG, "onItemClick at Entry with id : " + mEntryId);
		}

	}

}
