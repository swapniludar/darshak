package com.darshak.service;

import android.database.ContentObserver;
import android.net.Uri;
import android.util.Log;

import com.darshak.Application;
import com.darshak.constants.Event;
import com.darshak.modal.EventDetails;

/**
 * 
 * @author Swapnil Udar & Ravishankar Borgaonkar
 * 
 */
public class OutgoingSMSContentObserver extends ContentObserver {

	private static final String LOG_TAG = OutgoingSMSContentObserver.class
			.getSimpleName();

	private Application sApplication;

	public OutgoingSMSContentObserver(Application application) {
		super(null);
		this.sApplication = application;
	}

	@Override
	public void onChange(boolean selfChange) {
		onChange(selfChange, null);
	}
	
	@Override
	public void onChange(boolean selfChange, Uri uri) {
		Log.e(LOG_TAG, "Either SMS has been sent or received");
		// After trying for some time, settled on this dirty solution
		// Problem 1: When SMS sent content observer is invoked 6 times.
		// Problem 2: When SMS received content observer is invoked 2 times.
		// Problem 3: Couldn't identify whether content observer is invoked because of SMS
		// sent or received.
		if (sApplication.isSMSReceivedReceiverInvoked()) {
			setCallOrSMSStatus(Event.INCOMING_SMS);
			sApplication.setSMSReceivedReceiverInvoked(false);
			sApplication.resetContentObserverInvokedCount();
		} else {
			if (sApplication.getContentObserverInvokedCount() < 5) {
				sApplication.incrContentObserverInvokedCount();
			} else {
				setCallOrSMSStatus(Event.OUTGOING_SMS);
				sApplication.resetContentObserverInvokedCount();
			}
		}
	}

	public boolean deliverSelfNotifications() {
		return false;
	}

	private void setCallOrSMSStatus(Event event) {
		EventDetails eventDetails = new EventDetails(event,
				sApplication.getNwType(), sApplication.getNwOperator());
		sApplication.getDBHelper().insertEventDetails(eventDetails);
	}
}