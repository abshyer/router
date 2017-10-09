package cn.shyman.router;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.shyman.library.router.Router;
import cn.shyman.library.router.annotations.Route;
import cn.shyman.router.databinding.ActivityFirstBinding;

@Route("app/activity/first")
public class FirstActivity extends AppCompatActivity {
	private ActivityFirstBinding dataBinding;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_first);
		
		this.dataBinding.setToSecondClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Router.getInstance()
						.build("app/activity/second")
						.navigation(FirstActivity.this);
			}
		});
	}
	
}
