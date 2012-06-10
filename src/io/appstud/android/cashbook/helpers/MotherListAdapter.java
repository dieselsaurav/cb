package io.appstud.android.cashbook.helpers;

import io.appstud.android.cashbook.R;
import io.appstud.android.cashbook.activities.SubList;

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

public class MotherListAdapter extends ArrayAdapter<MotherListItem> {

	private Context context;
	private int layoutResourceId;
	private List<MotherListItem> motherList = new ArrayList<MotherListItem>();
	private static final String TAG = "EntryAdapter";

	public MotherListAdapter(Context context, int layoutResourceId,
			List<MotherListItem> motherList) {
		super(context, layoutResourceId, motherList);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.motherList = motherList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		MotherListItemHolder holder = null;

		if (row == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = layoutInflater.inflate(layoutResourceId, parent, false);

			holder = new MotherListItemHolder();

			holder.startDate = (TextView) row
					.findViewById(R.id.text_view_start_date);
			holder.endDate = (TextView) row
					.findViewById(R.id.text_view_end_date);
			holder.credit = (TextView) row.findViewById(R.id.text_view_credit);
			holder.debit = (TextView) row.findViewById(R.id.text_view_debit);
			holder.bal = (TextView) row.findViewById(R.id.text_view_bal);
			row.setTag(holder);

		} else {
			holder = (MotherListItemHolder) row.getTag();
		}

		MotherListItem motherListItem = motherList.get(position);
		holder.startDate.setText(DateFormat.getDateInstance(DateFormat.MEDIUM)
				.format(motherListItem.getStartDate()));
		holder.endDate.setText(DateFormat.getDateInstance(DateFormat.MEDIUM)
				.format(motherListItem.getEndDate()));
		holder.bal.setText(String.valueOf(motherListItem.getCurBal()));
		holder.credit.setText(String.valueOf(motherListItem.getCr()));
		holder.debit.setText(String.valueOf(motherListItem.getDb()));

		row.setOnClickListener(new OnRowClickListener(motherListItem
				.getStartDate(), motherListItem.getEndDate()));
		return row;
	}

	static class MotherListItemHolder {
		TextView groupBy;
		TextView startDate;
		TextView endDate;
		TextView credit;
		TextView debit;
		TextView bal;
	}

	private class OnRowClickListener implements OnClickListener {
		long endDate;
		long startDate;
		Intent intent = new Intent();

		public OnRowClickListener(long startDate, long endDate) {
			this.startDate = startDate;
			this.endDate = endDate;
		}

		@Override
		public void onClick(View v) {
			intent = new Intent(context, SubList.class);
			intent.putExtra("START_DATE", startDate);
			intent.putExtra("END_DATE", endDate);
			context.startActivity(intent);
			Log.d(TAG,
					"onItemClick at  Startdate : " + String.valueOf(startDate));
		}

	}

}
