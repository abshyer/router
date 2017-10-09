package cn.shyman.library.router.template;

import java.util.Map;

public interface IRouteGroup {
	
	void loadRoute(Map<String, RouteMeta> routeMetaMap);
}
