package cn.shyman.router;

import android.app.Application;

import java.util.List;

import cn.shyman.library.router.BuildConfig;
import cn.shyman.library.router.Router;
import cn.shyman.library.router.template.IRouteRoot;

public class BasicAppImpl extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Router.init(BuildConfig.DEBUG, new IRouteRoot() {
			@Override
			public void loadRoute(List<String> routeModuleNameList) {
				routeModuleNameList.add("app");
			}
		});
	}
}
