package cn.shyman.library.router;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.shyman.library.router.template.IRouteGroup;
import cn.shyman.library.router.template.IRouteModule;
import cn.shyman.library.router.template.IRouteProvider;
import cn.shyman.library.router.template.IRouteRoot;
import cn.shyman.library.router.template.RouteMeta;
import cn.shyman.library.router.template.RouteType;

public class Router {
	private static final String FORMAT_ROUTE_MODULE = "cn.shyman.library.router.impl.%s$$RouteModule";
	
	@SuppressLint("StaticFieldLeak")
	private static volatile Router INSTANCE;
	
	public static void init(Context context, IRouteRoot routeRoot, boolean isDebug) {
		if (INSTANCE != null) {
			return;
		}
		
		INSTANCE = new Router(context.getApplicationContext(), isDebug);
		List<String> routeModuleNameList = new ArrayList<>();
		routeRoot.loadRoute(routeModuleNameList);
		
		for (String routeModuleName : routeModuleNameList) {
			try {
				Class<?> clazz = Class.forName(String.format(Locale.CHINA, FORMAT_ROUTE_MODULE, captureName(routeModuleName)));
				if (clazz == null) {
					if (INSTANCE.isDebug) {
						throw new RuntimeException("can't find [" + routeModuleName + "] at Router init");
					}
					continue;
				}
				IRouteModule iRouteModule = (IRouteModule) clazz.newInstance();
				iRouteModule.loadRoute(INSTANCE.routeGroupMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Router getInstance() {
		if (INSTANCE == null) {
			throw new RuntimeException("must init before to use!");
		}
		return INSTANCE;
	}
	
	private Context context;
	private boolean isDebug = false;
	private Map<String, Class<? extends IRouteGroup>> routeGroupMap = new HashMap<>();
	private Map<String, RouteMeta> routeMetaMap = new HashMap<>();
	private Map<Object, IRouteProvider> routeProviderMap = new HashMap<>();
	
	private Router(Context context, boolean isDebug) {
		this.context = context;
		this.isDebug = isDebug;
	}
	
	public Postcard build(String routePath) {
		return new Postcard(routePath);
	}
	
	public void navigation(Postcard postcard, Context context, int requestCode) {
		completion(postcard);
		
		RouteMeta routeMeta = postcard.getRouteMeta();
		if (routeMeta.getRouteType() != RouteType.ACTIVITY) {
			throw new RuntimeException("routeType must be RouteType.ACTIVITY");
		}
		
		int flags = postcard.getFlags();
		Bundle optionsBundle = postcard.getOptionsBundle();
		try {
			Intent intent = new Intent(context, routeMeta.getTarget());
			Bundle extras = postcard.getExtras();
			if (extras != null) {
				intent.putExtras(postcard.getExtras());
			}
			if (flags != Postcard.DEFAULT_FLAGS) {
				intent.setFlags(flags);
			}
			
			if (requestCode != Postcard.DEFAULT_REQUEST_CODE && context instanceof Activity) {
				ActivityCompat.startActivityForResult((Activity) context, intent, requestCode, optionsBundle);
			} else {
				ActivityCompat.startActivity(context, intent, optionsBundle);
			}
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}
	}
	
	public void navigation(Postcard postcard, Fragment fragment, int requestCode) {
		completion(postcard);
		
		RouteMeta routeMeta = postcard.getRouteMeta();
		if (routeMeta.getRouteType() != RouteType.ACTIVITY) {
			if (this.isDebug) {
				throw new RuntimeException("routeType must be RouteType.ACTIVITY");
			}
			return;
		}
		
		int flags = postcard.getFlags();
		Bundle optionsBundle = postcard.getOptionsBundle();
		try {
			Intent intent = new Intent(fragment.getContext(), routeMeta.getTarget());
			Bundle extras = postcard.getExtras();
			if (extras != null) {
				intent.putExtras(postcard.getExtras());
			}
			if (flags != Postcard.DEFAULT_FLAGS) {
				intent.setFlags(flags);
			}
			
			if (requestCode != Postcard.DEFAULT_REQUEST_CODE) {
				fragment.startActivityForResult(intent, requestCode, optionsBundle);
			} else {
				fragment.startActivity(intent, optionsBundle);
			}
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}
	}
	
	public synchronized <T> T provide(Postcard postcard) {
		completion(postcard);
		
		RouteMeta routeMeta = postcard.getRouteMeta();
		if (routeMeta.getRouteType() != RouteType.PROVIDER) {
			throw new RuntimeException("routeType must be RouteType.PROVIDER");
		}
		
		IRouteProvider iRouteProvider = this.routeProviderMap.get(routeMeta.getTarget());
		if (iRouteProvider == null) {
			try {
				iRouteProvider = (IRouteProvider) routeMeta.getTarget().newInstance();
				IRouteProvider oldIRouteProvider = this.routeProviderMap.put(routeMeta.getTarget(), iRouteProvider);
				if (oldIRouteProvider != null) {
					throw new RuntimeException("multi routeProvider");
				}
				iRouteProvider.initProvider(this.context);
			} catch (Exception ignored) {
				throw new RuntimeException(ignored);
			}
		}
		// noinspection unchecked
		return (T) iRouteProvider;
	}
	
	private synchronized void completion(Postcard postcard) {
		RouteMeta routeMeta = this.routeMetaMap.get(postcard.getRoutePath());
		if (routeMeta == null) {
			Class<? extends IRouteGroup> routeGroup = this.routeGroupMap.get(postcard.getGroupName());
			if (routeGroup == null) {
				throw new RuntimeException("There is no route match the path [" + postcard.getRoutePath() + "] in group [" + postcard.getGroupName() + "]");
			}
			try {
				IRouteGroup iRouteGroup = routeGroup.newInstance();
				iRouteGroup.loadRoute(this.routeMetaMap);
				this.routeGroupMap.remove(postcard.getGroupName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			completion(postcard);
		} else {
			postcard.setRouteMeta(routeMeta);
		}
	}
	
	private static String captureName(String name) {
		char[] charArray = name.toCharArray();
		charArray[0] -= 32;
		return String.valueOf(charArray);
	}
}
