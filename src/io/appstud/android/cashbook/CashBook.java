package io.appstud.android.cashbook;

import io.appstud.android.cashbook.activities.MotherList;
import io.appstud.android.cashbook.helpers.CashBookDataSource;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CashBook extends Application {

	private static final String TAG = "CashBook";
	private CashBookDataSource cashBookDataSource;

	@Override
	public void onCreate() {
		cashBookDataSource = new CashBookDataSource(this);
		cashBookDataSource.open();
		Log.d(TAG, "Cashbook Created");
		super.onCreate();
	}

	public void goHome(Context context) {
		Intent intent = new Intent(context, MotherList.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	public CashBookDataSource getCashBookDataSource() {
		return cashBookDataSource;
	}

}
