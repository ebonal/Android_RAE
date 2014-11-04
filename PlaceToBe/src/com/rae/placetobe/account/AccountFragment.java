package com.rae.placetobe.account;

import rx.Observable;
import rx.android.events.OnTextChangeEvent;
import rx.android.observables.ViewObservable;
import rx.functions.Action1;
import rx.functions.Func1;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rae.placetobe.R;
import com.rae.placetobe.sqlite.PlaceToBeContract.Users;
import com.rae.placetobe.sqlite.PlaceToBeHelper;
import com.rae.placetobe.util.SharedPreferencesUtil;

public class AccountFragment extends Fragment
{
	private String name;
	private String email;

	@InjectView(R.id.bt_save)
	Button btSave;
	@InjectView(R.id.editTextName)
	EditText editTextName;
	@InjectView(R.id.editTextEmail)
	EditText editTextEmail;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		// View creation from the layout
		View rootView = inflater.inflate(R.layout.fragment_account, container,
				false);

		name = SharedPreferencesUtil.getAccountName(getActivity());
		email = SharedPreferencesUtil.getAccountMail(getActivity());

		ButterKnife.inject(this, rootView);

		editTextName.setText(name);
		editTextEmail.setText(email);

		/*
		 * // We save name and email values in preferences
		 * btSave.setOnClickListener(new View.OnClickListener() { public void
		 * onClick(View v) { String name = editTextName.getText().toString();
		 * String mail = editTextEmail.getText().toString();
		 * SharedPreferencesUtil.accountSave(getActivity(), name, mail);
		 * Toast.makeText(getActivity(), "Validé !", Toast.LENGTH_SHORT).show();
		 * } });
		 */

		btSave.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				PlaceToBeHelper helper = new PlaceToBeHelper(getActivity());
				SQLiteDatabase db = helper.getWritableDatabase();

				ContentValues values = new ContentValues();
				values.put(Users.COLUMN_NAME_NAME, editTextName.getText()
						.toString());
				values.put(Users.COLUMN_NAME_EMAIL, editTextEmail.getText()
						.toString());
				values.put(Users.COLUMN_NAME_USER_ID, 0);

				long newRowId = db.insert(Users.TABLE_NAME, null, values);

				Toast.makeText(getActivity(), "SQLite Validé !", Toast.LENGTH_SHORT)
						.show();
			}
		});

		Action1<String> onEditNameText = new Action1<String>()
		{
			@Override
			public void call(String s)
			{
				editTextEmail.setText(s);
			}
		};

		Func1<OnTextChangeEvent, String> reverseName = new Func1<OnTextChangeEvent, String>()
		{
			@SuppressLint("DefaultLocale")
			// lowercase return a warning for not set the default language
			@Override
			public String call(OnTextChangeEvent s)
			{
				return new StringBuffer(s.text.toString().toLowerCase())
						.reverse().toString();
			}
		};

		Func1<String, String> addMail = new Func1<String, String>()
		{
			@Override
			public String call(String s)
			{
				if (!s.isEmpty())
					s = s + "@gmail.com";
				return s;
			}
		};

		// Observable on EditText name
		Observable<OnTextChangeEvent> o = ViewObservable.text(editTextName,
				false);
		Observable<String> o2;
		o2 = o.map(reverseName);
		o2 = o2.map(addMail);
		o2.subscribe(onEditNameText);

		return rootView;
	}
}
