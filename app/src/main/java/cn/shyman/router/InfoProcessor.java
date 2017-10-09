package cn.shyman.router;

import cn.shyman.library.router.annotations.Route;
import cn.shyman.library.router.template.IRouteProcessor;

@Route("app/processor/info")
public class InfoProcessor implements IRouteProcessor {
	
	String getInfo() {
		return "Hello World!";
	}
}
