package cn.shyman.library.router.template;

public class RouteMeta {
	private RouteType routeType;
	private String groupName;
	private String routePath;
	private Class<?> target;
	
	public static RouteMeta build(RouteType routeType, String routePath, Class<?> target) {
		RouteMeta routeMeta = new RouteMeta();
		routeMeta.routeType = routeType;
		routeMeta.routePath = routePath;
		routeMeta.target = target;
		return routeMeta;
	}
	
	public RouteType getRouteType() {
		return routeType;
	}
	
	public void setRouteType(RouteType routeType) {
		this.routeType = routeType;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getRoutePath() {
		return routePath;
	}
	
	public void setRoutePath(String routePath) {
		this.routePath = routePath;
	}
	
	public Class<?> getTarget() {
		return target;
	}
	
	public void setTarget(Class<?> target) {
		this.target = target;
	}
}
