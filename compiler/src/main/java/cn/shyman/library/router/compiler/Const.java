package cn.shyman.library.router.compiler;

class Const {
	static final String SEPARATOR = "$$";
	
	static final String MODULE_NAME = "moduleName";
	
	static final String ANNOTATION_TYPE_ROUTE = "cn.shyman.library.router.annotations.Route";
	
	static final String PACKAGE_IMPLEMENT = "cn.shyman.library.router.impl";
	static final String PACKAGE_TEMPLATE = "cn.shyman.library.router.template";
	static final String PACKAGE_ANNOTATIONS = "cn.shyman.library.router.annotations";
	
	static final String TYPE_I_ROUTE_MODULE = "cn.shyman.library.router.template.IRouteModule";
	static final String TYPE_I_ROUTE_GROUP = "cn.shyman.library.router.template.IRouteGroup";
	static final String TYPE_I_ROUTE_PROCESSOR = "cn.shyman.library.router.template.IRouteProcessor";
	static final String TYPE_ROUTE_META = "cn.shyman.library.router.annotations.RouteMeta";
	static final String TYPE_ROUTE_TYPE = "cn.shyman.library.router.annotations.RouteType";
	
	static final String TYPE_ANDROID_ACTIVITY = "android.app.Activity";
	
	static final String METHOD_LOAD_ROUTE = "loadRoute";
	static final String METHOD_LOAD_ROUTE_GROUP_PARAMETER = "routeGroupMap";
	static final String METHOD_LOAD_ROUTE_GROUP_STATEMENT = "routeGroupMap.put($S, $T.class)";
	static final String METHOD_LOAD_ROUTE_META_PARAMETER = "routeMetaMap";
	static final String METHOD_LOAD_ROUTE_META_STATEMENT = "%s.put($S, $T.build($T.%s, $S, $T.class))";
	
	static final String ROUTE_MODULE = "RouteModule";
	static final String ROUTE_GROUP = "RouteGroup";
	static final String ROUTE_META = "RouteMeta";
	static final String ROUTE_TYPE = "RouteType";
	
	static final String ROUTE_TYPE_ACTIVITY = "ACTIVITY";
	static final String ROUTE_TYPE_PROCESSOR = "PROCESSOR";
	
}
