package com.example.doortester;

import android.os.Bundle;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity  extends BlunoLibrary {
	private Button buttonScan;
	private TextView serialReceivedText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.keypad);

		TextView tv = (TextView) this.findViewById(R.id.serialReveicedText);
		tv.setMovementMethod(new ScrollingMovementMethod());

		onCreateProcess();														//onCreate Process by BlunoLibrary
        
        serialBegin(115200);													//set the Uart Baudrate on BLE chip to 115200

        serialReceivedText = (TextView) findViewById(R.id.serialReveicedText);	//initial the EditText of the received data

        buttonScan = (Button) findViewById(R.id.buttonScan);					//initial the button for scanning the BLE device
        buttonScan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buttonScanOnClickProcess();										//Alert Dialog for selecting the BLE device
			}
		});
	}

    // Map to button press to the door code, and send it.
    public void onButtonPress(View v) {
        Button b = (Button) v;
        byte c = 0x0;
        switch (b.getId()) {
            case R.id.key_1:
                c = 0x31;
                break;
            case R.id.key_2:
                c = 0x32;
                break;
            case R.id.key_3:
                c = 0x33;
                break;
            case R.id.key_4:
                c = 0x34;
                break;
            case R.id.key_5:
                c = 0x35;
                break;
            case R.id.key_6:
                c = 0x36;
                break;
            case R.id.key_7:
                c = 0x37;
                break;
            case R.id.key_8:
                c = 0x38;
                break;
            case R.id.key_9:
                c = 0x39;
                break;
            case R.id.key_0:
                c = 0x30;
                break;
            case R.id.key_cancel:
                c = 0x2A;
                break;
            case R.id.key_enter:
                c = 0x23;
                break;
            default:
                return;
        }

        serialSend(new byte[] {c});
    }

	protected void onResume(){
		super.onResume();
		System.out.println("BlUNOActivity onResume");
		onResumeProcess();														//onResume Process by BlunoLibrary
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		onActivityResultProcess(requestCode, resultCode, data);					//onActivityResult Process by BlunoLibrary
		super.onActivityResult(requestCode, resultCode, data);
	}
	
    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();														//onPause Process by BlunoLibrary
    }
	
	protected void onStop() {
		super.onStop();
		onStopProcess();														//onStop Process by BlunoLibrary
	}
    
	@Override
    protected void onDestroy() {
        super.onDestroy();	
        onDestroyProcess();														//onDestroy Process by BlunoLibrary
    }

	@Override
	public void onConectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
		switch (theConnectionState) {											//Four connection state
		case isConnected:
			buttonScan.setText("Connected");
			break;
		case isConnecting:
			buttonScan.setText("Connecting");
			break;
		case isToScan:
			buttonScan.setText("Scan");
			break;
		case isScanning:
			buttonScan.setText("Scanning");
			break;
		case isDisconnecting:
			buttonScan.setText("isDisconnecting");
			break;
		default:
			break;
		}
	}

	@Override
	public void onSerialReceived(String theString) {							//Once connection data received, this function will be called
		// TODO Auto-generated method stub
		serialReceivedText.append(theString);							//append the text into the EditText
		//The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
					
	}

}