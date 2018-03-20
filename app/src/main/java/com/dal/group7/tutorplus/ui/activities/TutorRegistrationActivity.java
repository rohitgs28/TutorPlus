package com.dal.group7.tutorplus.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.test.mock.MockPackageManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.constants.Constants;
import com.dal.group7.tutorplus.db.DBhelper;
import com.dal.group7.tutorplus.model.LocationModel;
import com.dal.group7.tutorplus.model.SubjectModel;
import com.dal.group7.tutorplus.model.TimingAvailablity;
import com.dal.group7.tutorplus.model.TutorModel;
import com.dal.group7.tutorplus.model.UserModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.vision.text.Text;
import android.widget.TextView.OnEditorActionListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.dal.group7.tutorplus.R.id.text;

public class TutorRegistrationActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener, LocationListener {
	private  String task ="";
	final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
	final CharSequence[] items = {Constants.CAMERA, Constants.GALLERY,
			Constants.CANCEL };
	private int CAMERA = 0, GALLERY = 1;
	EditText name, age, phone, qualification, tutorExperience;
	private String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private String phonePattern = "^[+][0-9]{10,13}$";
	Resources res;
	TutorModel tutorModel=new TutorModel();;
	EditText locshowtv;
	String loc;
	private TextView txtOutputLatitude, txtOutputLongitude, mCurrentAddress;
	private Location mLastLocation;
	public static int tutorId;
	private GoogleApiClient mGoogleApiClient;
	private LocationRequest mLocationRequest;
	private String lat, lon;
	private static final int REQUEST_CODE_PERMISSION = 2;
	private String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;
	public static String add = "";
	TextView tvlocation;
	int flag = 1;
	SearchView simpleSearchView;
	LocationModel ob=new LocationModel();
	LocationManager locationManager;
	DBhelper dbHelper = new DBhelper(TutorRegistrationActivity.this);
	GoogleLogin_Activity logindetails = new GoogleLogin_Activity();
	String email = logindetails.personEmail;
	String user_name = logindetails.personName;
	String role_choose = logindetails.role;
	Bitmap bm;
	Bitmap thumbnail;
	Spinner spinnerCategory;
	Spinner spinnerLevel;
	Spinner spinnerPrice;
	Context context;
	private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
	private ImageView ivImage;
	private String userChoosenTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutor_registration_page);

		Toast.makeText(this,email+" "+user_name+" "+role_choose,Toast.LENGTH_SHORT).show();
		ivImage = (ImageView) findViewById(R.id.ivImage);
		ivImage.setImageResource(R.drawable.profilepic);


		ivImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				selectImage();
			}
		});

		checkLocationPermission();
		res = getResources();
		init();
		isLocationEnabled();
		if(!isLocationEnabled()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(TutorRegistrationActivity.this);
			builder.setTitle("GPS is Not Enabled")
					.setMessage("Turn on?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		}
		setSpinnerCategoryValues();
		setSpinnerLevelValues();
		setSpinnerPriceValues();

		Gps_Activity ob = new Gps_Activity();
		String loc=ob.add;
	}
	protected boolean isLocationEnabled(){
		String le = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) getSystemService(le);
		if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
			return false;
		} else {
			return true;
		}
	}

	public void setSpinnerPriceValues(){
		ArrayList list = new ArrayList<String>();
		list.add("20");
		list.add("40");
		list.add("60");
		list.add("80+");

		ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item , list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPrice.setAdapter(adapter);
	}

	public void setSpinnerCategoryValues(){
		ArrayList list = new ArrayList<String>();

		list.add("Maths");
		list.add("Physics");
		list.add("Germany");
		list.add("English");
		list.add("JAVA");
		list.add("C++");
		list.add("Science");

		ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item , list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCategory.setAdapter(adapter);
	}
	public void setSpinnerLevelValues(){
		ArrayList list = new ArrayList<String>();
		list.add("Level 1");
		list.add("Level 2");
		list.add("Level 3");
		list.add("Level 4");
		list.add("Level 5");

		ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item , list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerLevel.setAdapter(adapter);
	}

	private void init() {
		name = (EditText) findViewById(R.id.name);
		age = (EditText) findViewById(R.id.age);
		phone = (EditText) findViewById(R.id.phone);
		phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		qualification = (EditText) findViewById(R.id.qualification);
		tutorExperience = (EditText) findViewById(R.id.tutorExperience);
		locshowtv = (EditText) findViewById(R.id.locshowtv);

		spinnerCategory = (Spinner)findViewById(R.id.spinCategory);
		spinnerLevel = (Spinner)findViewById(R.id.spinLevel);
		spinnerPrice = (Spinner)findViewById(R.id.spinPricePerSession);
	}

	public void onSubmitClick(View v) {
		switch (v.getId()) {
			case R.id.next_tutor:
				if(name.getText().toString().equals("")){
					name.setError("Field cannot be blank");
					flag = 0;
				}
				else
					flag=1;
				if(age.getText().toString().equals("")){
					age.setError("Field cannot be blank");
					flag = 0;

				}
				else
					flag=1;
				if(phone.getText().toString().equals("")){
					phone.setError("Field cannot be blank");
					flag = 0;

				}
				else
					flag=1;
				if(qualification.getText().toString().equals("")){
					qualification.setError("Field cannot be blank");
					flag = 0;

				}
				else
					flag=1;
				if(tutorExperience.getText().toString().equals("")){
					tutorExperience.setError("Field cannot be blank");
					flag = 0;

				}
				else
					flag=1;
				if (ivImage.getDrawable().getConstantState().equals(ivImage.getContext().getDrawable(R.drawable.profilepic).getConstantState())){
					Toast.makeText(this,"Enter Profile Picture",Toast.LENGTH_SHORT).show();
					flag = 0;

				}
				else
					flag=1;
				if (flag ==0){

				}
				else {
					Intent intent = new Intent(this, Main_Calendar_Activity.class);
					setDatatoTutorModels();
					sampleInsertions();
					Toast.makeText(TutorRegistrationActivity.this,"Profile Created",Toast.LENGTH_SHORT).show();
					intent.putExtra("tutorModel", tutorModel);
					startActivity(intent);
				}
				break;
			default:
				break;
		}

	}

	private void sampleInsertions() {
		// user table insertion
		UserModel userModel = new UserModel(email,user_name,role_choose);
		dbHelper.insertUser(dbHelper.getWritableDatabase(),userModel);
		Log.d("user insertion","insertions");
		// subject table insertions
		SubjectModel subjectModelPhysics = new SubjectModel(0,spinnerCategory.getSelectedItem().toString());
		dbHelper.insertSubject(dbHelper.getWritableDatabase(),subjectModelPhysics);
		Log.d("user insertion","insertions");
		// Location Insertion
		//LocationModel locationModel = new LocationModel(0,add);
		LocationModel locationModel = new LocationModel(0,add);
		dbHelper.insertLocation(dbHelper.getWritableDatabase(),locationModel);
		Log.d("location insertion","insertions");
		// Tutor Insertion
		insertTutor();
	}

	private void insertTutor() {
		// fetch subjectId
		int subjectId = dbHelper.getSubjectBasedOnSubjectName(dbHelper.getReadableDatabase(),tutorModel.getSubject().getSubjectName());
		tutorModel.getSubject().setSubjectId(subjectId);
		// TODO first do location insertion copy the above method's code (Nibir) , you will get location id from insertion

		// fetch locationId
		int locationId = dbHelper.getLocationBasedOnLocationName(dbHelper.getReadableDatabase(),tutorModel.getLocationModel().getLocationName());
		tutorModel.getLocationModel().setLocationId(locationId);

		tutorId = dbHelper.insertTutor(dbHelper.getWritableDatabase(),tutorModel);

	}

	private void setDatatoTutorModels() {
		//Set values to the fields
		UserModel userModel = new UserModel(email,user_name,role_choose);
		SubjectModel subjectModel = new SubjectModel(0,spinnerCategory.getSelectedItem().toString());
		LocationModel locatioModel = new LocationModel(0,add);

		tutorModel.setUserModel(userModel);
		tutorModel.setName(name.getText().toString());
		tutorModel.setAge(age.getText().toString());
		tutorModel.setPhone(phone.getText().toString());
		tutorModel.setSubject(subjectModel);
		tutorModel.setLevel(spinnerLevel.getSelectedItem().toString());
		tutorModel.setQualification(qualification.getText().toString());
		tutorModel.setPriceperSession(spinnerPrice.getSelectedItem().toString());
		tutorModel.setTutorExperience(tutorExperience.getText().toString());
		tutorModel.setLocationModel(locatioModel);
	}
	private void checkLocationPermission() {

		try {
			if (ActivityCompat.checkSelfPermission(this, mPermission)
					!= MockPackageManager.PERMISSION_GRANTED) {

				ActivityCompat.requestPermissions(this,
						new String[]{mPermission}, REQUEST_CODE_PERMISSION);

				// If any permission above not allowed by user, this condition will execute every time, else your else part will work
			} else {
				buildGoogleApiClient();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onConnected(Bundle bundle) {

		mLocationRequest = LocationRequest.create();
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

		if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			return;
		}
		LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
		mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

		if (mLastLocation != null) {
			lat = String.valueOf(mLastLocation.getLatitude());
			lon = String.valueOf(mLastLocation.getLongitude());

		}
	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onLocationChanged(Location location) {

		lat = String.valueOf(location.getLatitude());
		lon = String.valueOf(location.getLongitude());

		double latitude = Double.parseDouble(lat);
		double longitude = Double.parseDouble(lon);
		getAddress(latitude,longitude);
		//updateUI();


	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		buildGoogleApiClient();
	}

	synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();
	}


	@Override
	protected void onResume() {
		super.onResume();
		if (mGoogleApiClient != null) {
			mGoogleApiClient.connect();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mGoogleApiClient != null) {
			mGoogleApiClient.disconnect();
		}
	}

	public String getAddress(double lat, double lng) {
		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
		try {
			List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
			if (addresses != null && addresses.size()>0) {
				Address obj = addresses.get(0);

				add = obj.getLocality();
				Toast.makeText(this,"Current Location Set", Toast.LENGTH_SHORT).show();
				locshowtv.setText(add);



			}
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		return add;

	}
	//Function to take in input of back key button
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitByBackKey();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	//Function to set up dialogue box on pressing back key
	protected void exitByBackKey() {
		AlertDialog alertbox = new AlertDialog.Builder(this)
				.setMessage("Going back will delete all records.Continue?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						finish();
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}
				})
				.show();
	}

	private void selectImage() {
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TutorRegistrationActivity.this);
		builder.setTitle("Choose Photo");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				setUserChosenTask(items[item]);
				boolean result=checkPermission();
				if (items[item].equals(Constants.CAMERA) && result) {
					cameraAction();
				} else if (items[item].equals(Constants.GALLERY) && result) {
					galleryAction();
				} else if (items[item].equals(Constants.CANCEL)) {
					dialog.dismiss();
				}
			}
		});
		builder.show();	}

	private boolean checkPermission() {
		int hasStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
		if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
							0);
				}
			}
		} else {
			return true;
		}
		return false;
	}

	private void setUserChosenTask(CharSequence item) {
		switch(item.toString()){
			case "Camera":
				task = "Camera";
				break;
			case "Choose from Gallery":
				task = "Choose from Gallery";
				break;
		}
	}

	private void cameraAction()
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, CAMERA);
	}
	private void galleryAction()
	{
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Choose File"), GALLERY);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					if(task.equals("Camera"))
						cameraAction();
					else if(task.equals("Choose from Gallery"))
						galleryAction();
				} else {
					Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
				}
				break;

			case REQUEST_CODE_PERMISSION:
				super.onRequestPermissionsResult(requestCode, permissions, grantResults);
				if (grantResults.length == 1 &&
						grantResults[0] == MockPackageManager.PERMISSION_GRANTED) {

					// Success Stuff here
					buildGoogleApiClient();
				} else {
					// Failure Stuff
				}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == GALLERY)
				galleryResult(data);
			else if (requestCode == CAMERA)
				cameraResult(data);
		}
	}

	private void galleryResult(Intent data) {
		bm=null;
		if (data != null) {
			try {
				String realPath = getRealPathFromURI(data.getData());
				setImageFromRealPath(realPath);
				setImageTODB(realPath);
				bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ivImage.setImageBitmap(bm);
	}

	private void cameraResult(Intent data) {
		// extract image to store it in db
		Bitmap b = (Bitmap)  data.getExtras().get("data");
		Uri dataUri = getImageUri(this,b);
		setImageTODB(dataUri.toString());
		thumbnail = (Bitmap) data.getExtras().get("data");
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
		File destination = new File(Environment.getExternalStorageDirectory(),
				System.currentTimeMillis() + ".jpg");
		FileOutputStream fo;
		try {
			destination.createNewFile();
			fo = new FileOutputStream(destination);
			fo.write(bytes.toByteArray());
			fo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ivImage.setImageBitmap(thumbnail);
	}

	public Uri getImageUri(Context context, Bitmap photo)
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		photo.compress(Bitmap.CompressFormat.PNG, 80, bytes);
		String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), photo, "Title", null);
		return Uri.parse(path);
	}

	private void setImageTODB(String tutorImage){
		tutorModel.setImage(tutorImage);
	}

	void setImageFromRealPath(String path){
		Uri uriFromPath = Uri.fromFile(new File(path));

		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriFromPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	private String getRealPathFromURI(Uri contentURI) {
		String filePath = "";
		String wholeID = DocumentsContract.getDocumentId(contentURI);
		String id = wholeID.split(":")[1];

		String[] column = { MediaStore.Images.Media.DATA };

		String sel = MediaStore.Images.Media._ID + "=?";

		Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				column, sel, new String[]{ id }, null);

		int columnIndex = cursor.getColumnIndex(column[0]);

		if (cursor.moveToFirst()) {
			filePath = cursor.getString(columnIndex);
		}
		cursor.close();
		return filePath;
	}
}






