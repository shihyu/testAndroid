package com.doov.guaguaka;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.doov.guaguaka.GuaView.GuaViewCompleteListener;
import com.example.demo1.R;

public class GuaGuaKaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guaguaka);
		
		GuaView guaView = (GuaView)this.findViewById(R.id.guaview);
		guaView.setGuaViewCompleteListener(new GuaViewCompleteListener() {
			
			@Override
			public void onComplete() {
				Toast.makeText(getApplicationContext(), "不要灰心再来一次", Toast.LENGTH_SHORT).show();
				
			}
		});
	}

	

}
