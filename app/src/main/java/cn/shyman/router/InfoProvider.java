package cn.shyman.router;

import android.content.Context;

import cn.shyman.library.router.annotations.Route;
import cn.shyman.library.router.template.IRouteProvider;

@Route("app/provider/info")
public class InfoProvider implements IRouteProvider {
	
	@Override
	public void initProvider(Context context) {
		
	}
	
	String getInfo() {
		return "Hello World!";
	}
}
