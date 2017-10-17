package cn.shyman.library.router;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

import cn.shyman.library.router.template.RouteMeta;

public class Postcard {
	public static final int DEFAULT_FLAGS = 0;
	public static final int DEFAULT_REQUEST_CODE = 0;
	
	private final String groupName;
	private final String routePath;
	private RouteMeta routeMeta;
	private Bundle mExtras;
	private int flags = DEFAULT_FLAGS;
	private Bundle optionsBundle;
	
	public Postcard(String routePath) {
		this.groupName = extractGroup(routePath);
		this.routePath = routePath;
	}
	
	private String extractGroup(String routPath) {
		if (TextUtils.isEmpty(routPath)) {
			return null;
		}
		
		try {
			String groupName = routPath.substring(0, routPath.indexOf("/", 0));
			if (TextUtils.isEmpty(groupName)) {
				return null;
			} else {
				return groupName;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getGroupName() {
		return this.groupName;
	}
	
	public String getRoutePath() {
		return routePath;
	}
	
	public RouteMeta getRouteMeta() {
		return routeMeta;
	}
	
	public void setRouteMeta(RouteMeta routeMeta) {
		this.routeMeta = routeMeta;
	}
	
	public Postcard putExtra(String name, boolean value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putBoolean(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, byte value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putByte(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, char value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putChar(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, short value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putShort(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, int value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putInt(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, long value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putLong(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, float value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putFloat(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, double value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putDouble(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, String value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putString(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, CharSequence value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putCharSequence(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, Parcelable value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putParcelable(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, Parcelable[] value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putParcelableArray(name, value);
		return this;
	}
	
	public Postcard putParcelableArrayListExtra(String name, ArrayList<? extends Parcelable> value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putParcelableArrayList(name, value);
		return this;
	}
	
	public Postcard putIntegerArrayListExtra(String name, ArrayList<Integer> value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putIntegerArrayList(name, value);
		return this;
	}
	
	public Postcard putStringArrayListExtra(String name, ArrayList<String> value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putStringArrayList(name, value);
		return this;
	}
	
	public Postcard putCharSequenceArrayListExtra(String name, ArrayList<CharSequence> value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putCharSequenceArrayList(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, Serializable value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putSerializable(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, boolean[] value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putBooleanArray(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, byte[] value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putByteArray(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, short[] value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putShortArray(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, char[] value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putCharArray(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, int[] value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putIntArray(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, long[] value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putLongArray(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, float[] value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putFloatArray(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, double[] value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putDoubleArray(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, String[] value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putStringArray(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, CharSequence[] value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putCharSequenceArray(name, value);
		return this;
	}
	
	public Postcard putExtra(String name, Bundle value) {
		if (mExtras == null) {
			mExtras = new Bundle();
		}
		mExtras.putBundle(name, value);
		return this;
	}
	
	public boolean hasExtra(String name) {
		return mExtras != null && mExtras.containsKey(name);
	}
	
	public boolean getBooleanExtra(String name, boolean defaultValue) {
		return mExtras == null ? defaultValue : mExtras.getBoolean(name, defaultValue);
	}
	
	public byte getByteExtra(String name, byte defaultValue) {
		return mExtras == null ? defaultValue : mExtras.getByte(name, defaultValue);
	}
	
	public short getShortExtra(String name, short defaultValue) {
		return mExtras == null ? defaultValue : mExtras.getShort(name, defaultValue);
	}
	
	public char getCharExtra(String name, char defaultValue) {
		return mExtras == null ? defaultValue : mExtras.getChar(name, defaultValue);
	}
	
	public int getIntExtra(String name, int defaultValue) {
		return mExtras == null ? defaultValue : mExtras.getInt(name, defaultValue);
	}
	
	public long getLongExtra(String name, long defaultValue) {
		return mExtras == null ? defaultValue : mExtras.getLong(name, defaultValue);
	}
	
	public float getFloatExtra(String name, float defaultValue) {
		return mExtras == null ? defaultValue : mExtras.getFloat(name, defaultValue);
	}
	
	public double getDoubleExtra(String name, double defaultValue) {
		return mExtras == null ? defaultValue : mExtras.getDouble(name, defaultValue);
	}
	
	public String getStringExtra(String name) {
		return mExtras == null ? null : mExtras.getString(name);
	}
	
	public CharSequence getCharSequenceExtra(String name) {
		return mExtras == null ? null : mExtras.getCharSequence(name);
	}
	
	public <T extends Parcelable> T getParcelableExtra(String name) {
		return mExtras == null ? null : mExtras.<T>getParcelable(name);
	}
	
	public <T extends Parcelable> T[] getParcelableArrayExtra(String name) {
		// noinspection unchecked
		return mExtras == null ? null : (T[]) mExtras.getParcelableArray(name);
	}
	
	public <T extends Parcelable> ArrayList<T> getParcelableArrayListExtra(String name) {
		return mExtras == null ? null : mExtras.<T>getParcelableArrayList(name);
	}
	
	public Serializable getSerializableExtra(String name) {
		return mExtras == null ? null : mExtras.getSerializable(name);
	}
	
	public ArrayList<Integer> getIntegerArrayListExtra(String name) {
		return mExtras == null ? null : mExtras.getIntegerArrayList(name);
	}
	
	public ArrayList<String> getStringArrayListExtra(String name) {
		return mExtras == null ? null : mExtras.getStringArrayList(name);
	}
	
	public ArrayList<CharSequence> getCharSequenceArrayListExtra(String name) {
		return mExtras == null ? null : mExtras.getCharSequenceArrayList(name);
	}
	
	public boolean[] getBooleanArrayExtra(String name) {
		return mExtras == null ? null : mExtras.getBooleanArray(name);
	}
	
	public byte[] getByteArrayExtra(String name) {
		return mExtras == null ? null : mExtras.getByteArray(name);
	}
	
	public short[] getShortArrayExtra(String name) {
		return mExtras == null ? null : mExtras.getShortArray(name);
	}
	
	public char[] getCharArrayExtra(String name) {
		return mExtras == null ? null : mExtras.getCharArray(name);
	}
	
	public int[] getIntArrayExtra(String name) {
		return mExtras == null ? null : mExtras.getIntArray(name);
	}
	
	public long[] getLongArrayExtra(String name) {
		return mExtras == null ? null : mExtras.getLongArray(name);
	}
	
	public float[] getFloatArrayExtra(String name) {
		return mExtras == null ? null : mExtras.getFloatArray(name);
	}
	
	public double[] getDoubleArrayExtra(String name) {
		return mExtras == null ? null : mExtras.getDoubleArray(name);
	}
	
	public String[] getStringArrayExtra(String name) {
		return mExtras == null ? null : mExtras.getStringArray(name);
	}
	
	public CharSequence[] getCharSequenceArrayExtra(String name) {
		return mExtras == null ? null : mExtras.getCharSequenceArray(name);
	}
	
	public Bundle getBundleExtra(String name) {
		return mExtras == null ? null : mExtras.getBundle(name);
	}
	
	public Bundle getExtras() {
		return mExtras;
	}
	
	public int getFlags() {
		return this.flags;
	}
	
	public Postcard setFlags(int flags) {
		this.flags = flags;
		return this;
	}
	
	public Bundle getOptionsBundle() {
		return this.optionsBundle;
	}
	
	public Postcard setOptionsBundle(Bundle optionsBundle) {
		this.optionsBundle = optionsBundle;
		return this;
	}
	
	public void navigation(Context context) {
		Router.getInstance().navigation(this, context, DEFAULT_REQUEST_CODE);
	}
	
	public void navigation(Context context, int requestCode) {
		Router.getInstance().navigation(this, context, requestCode);
	}
	
	public void navigation(Fragment fragment) {
		Router.getInstance().navigation(this, fragment, DEFAULT_REQUEST_CODE);
	}
	
	public void navigation(Fragment fragment, int requestCode) {
		Router.getInstance().navigation(this, fragment, requestCode);
	}
	
	public <T> T provide() {
		return Router.getInstance().provide(this);
	}
	
}
