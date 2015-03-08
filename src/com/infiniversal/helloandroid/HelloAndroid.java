package com.infiniversal.helloandroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;


public class HelloAndroid extends Activity implements OnClickListener {
    
	int lastinterval;
	MediaPlayer mp;
	
	Button[] intervalbutton;
	TextView[] intervaltext;
	int[] intervaloccurrences;
	int[] intervalcorrects;
	
	int totalintervals;
	int totalcorrects;
	
	Button hearagainbutton;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TextView tv = new TextView(this);
        //tv.setText("Hello, Will");
        setContentView(R.layout.main);
        
        hearagainbutton = (Button)findViewById(R.id.button);
        hearagainbutton.setOnClickListener((OnClickListener) this);

        intervalbutton = new Button[13];
        intervaltext = new TextView[13];
        intervaloccurrences = new int[13];
        intervalcorrects = new int[13];
        
        totalintervals = 0;
        totalcorrects = 0;
        
        intervalbutton[0] = (Button)findViewById(R.id.interval0);
        intervalbutton[1] = (Button)findViewById(R.id.interval1);
        intervalbutton[2] = (Button)findViewById(R.id.interval2);
        intervalbutton[3] = (Button)findViewById(R.id.interval3);
        intervalbutton[4] = (Button)findViewById(R.id.interval4);
        intervalbutton[5] = (Button)findViewById(R.id.interval5);
        intervalbutton[6] = (Button)findViewById(R.id.interval6);
        intervalbutton[7] = (Button)findViewById(R.id.interval7);
        intervalbutton[8] = (Button)findViewById(R.id.interval8);
        intervalbutton[9] = (Button)findViewById(R.id.interval9);
        intervalbutton[10] = (Button)findViewById(R.id.interval10);
        intervalbutton[11] = (Button)findViewById(R.id.interval11);
        intervalbutton[12] = (Button)findViewById(R.id.interval12);
        
        intervaltext[0] = (TextView)findViewById(R.id.intervalresults0);
        intervaltext[1] = (TextView)findViewById(R.id.intervalresults1);
        intervaltext[2] = (TextView)findViewById(R.id.intervalresults2);
        intervaltext[3] = (TextView)findViewById(R.id.intervalresults3);
        intervaltext[4] = (TextView)findViewById(R.id.intervalresults4);
        intervaltext[5] = (TextView)findViewById(R.id.intervalresults5);
        intervaltext[6] = (TextView)findViewById(R.id.intervalresults6);
        intervaltext[7] = (TextView)findViewById(R.id.intervalresults7);
        intervaltext[8] = (TextView)findViewById(R.id.intervalresults8);
        intervaltext[9] = (TextView)findViewById(R.id.intervalresults9);
        intervaltext[10] = (TextView)findViewById(R.id.intervalresults10);
        intervaltext[11] = (TextView)findViewById(R.id.intervalresults11);
        intervaltext[12] = (TextView)findViewById(R.id.intervalresults12);
        
        
        int i;
        for (i = 0; i < 13; i++) {
        	intervaloccurrences[i] = 0;
        	intervalcorrects[i] = 0;
        	intervalbutton[i].setOnClickListener((OnClickListener) this);	
        }
              
      
        lastinterval = -1;
        mp = new MediaPlayer();
        
        generateRandomInterval();
        playInterval();
    }

    public static byte[] hexStringToByteArray(String s) { 
        int len = s.length(); 
        byte[] data = new byte[len / 2]; 
        for (int i = 0; i < len; i += 2) { 
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) 
                                 + Character.digit(s.charAt(i+1), 16)); 
        } 
        return data; 
    } 

    private void generateRandomInterval()
    {
    	String midfiledata = "4D54686400000006000000010060" +
        "4D54726B00000026" +
        "00FF580404021808" +
        "00FF510307A120" +
        "00C001" +
        "00904C60" +
        "60804C40" +
        "00904F60" +
        "60804F40" +
        "00FF2F00"; 	


    	byte[] midibytes = hexStringToByteArray(midfiledata);

		Random randomGenerator = new Random();
		int basenote = randomGenerator.nextInt(20) + 60;
		int interval = randomGenerator.nextInt(13);
		lastinterval = interval;
		
		midibytes[46] = (byte)basenote;
		midibytes[42] = (byte)basenote;
		midibytes[50] = (byte) (basenote + interval);
		midibytes[54] = (byte) (basenote + interval);
		
		
		FileOutputStream fos = null;
		try {
			fos = openFileOutput("interval.mid", Context.MODE_PRIVATE);
		} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fos.write(midibytes);
			fos.close();
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    
    private void playInterval()
    {
    	try {
			 File file = new File(getFilesDir() + "/interval.mid");
			  FileInputStream fis = new FileInputStream(file);
			    mp.setDataSource(fis.getFD());
			    
			//mp.setDataSource(getFilesDir() + "/interval.mid");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mp.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mp.start(); 
		//MediaPlayer mp = MediaPlayer.create(this, R.raw.interval);
		//mp.start();	// TODO Auto-generated method stub
				// TODO Auto-generated method stub
   	
    }
    
	public void onClick(View arg0) {

		if (lastinterval > -1) {
        	mp.stop();
			mp.reset();

		  if (arg0 != hearagainbutton) {
        	
			TextView t=(TextView)findViewById(R.id.statusline);
        	
        	String[] IntervalStrings = {
        			"Unison",
        			"Minor second",
        			"Major second",
        			"Minor third",
        			"Major third",
        			"Perfect fourth",
        			"Tritone",
        			"Perfect fifth",
        			"Minor sixth",
          			"Major sixth",
        			"Minor seventh",
        			"Major seventh",
        			"Octave"  };
            
	        totalintervals++;
	        intervaloccurrences[lastinterval]++;
	        	        
	        if (arg0 == intervalbutton[lastinterval]) {
	          totalcorrects++;
	          intervalcorrects[lastinterval]++;
                       	        	
	        }
	        
	        t.setText(totalcorrects + " / " + totalintervals + 
	        		  "  Last interval: " + IntervalStrings[lastinterval]);
	        
	        intervaltext[lastinterval].setText(intervalcorrects[lastinterval] + " / " +
	        		                           intervaloccurrences[lastinterval]);
		  
		  
		    generateRandomInterval();
		  }
	
		playInterval();
		}
	}
}

