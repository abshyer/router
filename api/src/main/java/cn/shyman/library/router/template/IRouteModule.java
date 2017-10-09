package cn.shyman.library.router.template;

import java.util.Map;

public interface IRouteModule {
	
	void loadRoute(Map<String, Class<? extends IRouteGroup>> routeGroupMap);
}
