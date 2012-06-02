package io.appstud.android.cashbook;

import io.appstud.android.cashbook.helpers.CashBookDataSource;
import android.app.Application;
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

	public CashBookDataSource getCashBookDataSource() {
		return cashBookDataSource;
	}

}
