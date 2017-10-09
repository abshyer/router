package cn.shyman.router;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cn.shyman.library.router.Router;
import cn.shyman.library.router.annotations.Route;
import cn.shyman.router.databinding.ActivitySplashBinding;

@Route("app/activity/splash")
public class SplashActivity extends AppCompatActivity {
	private ActivitySplashBinding dataBinding;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
		
		this.dataBinding.setToFirstClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Router.getInstance()
						.build("app/activity/first")
						.navigation(SplashActivity.this);
			}
		});
		
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			return;
		}
		
		String content = bundle.getString("content");
		this.dataBinding.setContent(content);
		
		InfoProcessor infoProcessor = Router.getInstance()
				.build("app/processor/info")
				.process();
		Toast.makeText(this, infoProcessor.getInfo(), Toast.LENGTH_SHORT).show();
	}
}
