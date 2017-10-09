package cn.shyman.library.router.compiler;

import javax.lang.model.element.Element;

class RouteInfo {
	private String routeType;
	private Element routeElement;
	
	private String groupName;
	private String routePath;
	
	RouteInfo(String routeType, Element routeElement, String routePath) {
		this.routeType = routeType;
		this.routeElement = routeElement;
		this.routePath = routePath;
	}
	
	String getRouteType() {
		return routeType;
	}
	
	void setRouteType(String routeType) {
		this.routeType = routeType;
	}
	
	Element getRouteElement() {
		return routeElement;
	}
	
	void setRouteElement(Element routeElement) {
		this.routeElement = routeElement;
	}
	
	String getGroupName() {
		return groupName;
	}
	
	void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	String getRoutePath() {
		return routePath;
	}
	
	void setRoutePath(String routePath) {
		this.routePath = routePath;
	}
}
