package cn.shyman.library.router.template;

public enum RouteType {
	ACTIVITY {
		@Override
		public String build(String groupName, String routePath) {
			return groupName + "/" + ACTIVITY + "/" + routePath;
		}
	},
	PROCESSOR {
		@Override
		public String build(String groupName, String routePath) {
			return groupName + "/" + PROCESSOR + "/" + routePath;
		}
	};
	
	public abstract String build(String groupName, String routePath);
}
