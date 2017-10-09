package cn.shyman.router;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.shyman.library.router.Router;
import cn.shyman.library.router.annotations.Route;
import cn.shyman.router.databinding.ActivitySecondBinding;

@Route("app/activity/second")
public class SecondActivity extends AppCompatActivity {
	private ActivitySecondBinding dataBinding;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_second);
		
		this.dataBinding.setBackToSplashClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Router.getInstance()
						.build("app/activity/splash")
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
						.putExtra("content", "test")
						.navigation(SecondActivity.this);
			}
		});
	}
	
}
