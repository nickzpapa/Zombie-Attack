package com.zombieattack;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class InfoText {
	
	public BitmapFont fontLabel;
	public static String str1 = "", str2 = "", str3= "";
	
	InfoText(){
		this.fontLabel = new BitmapFont(Gdx.files.internal("fonts/test.fnt"));
		empty();	
	}
	
	public static void setText(String s1, String s2, String s3){
		System.out.println("HERE");
		str1 = s1;
		str2 = s2;
		str3 = s3;
	}
	
	static void empty(){
		str1 = "";
		str2 = "";
		str3 = "";
	}
	
	void draw(SpriteBatch sb, int waveBreak){
		
		fontLabel.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		if(waveBreak > 0) {
			String timeInfo = "Next wave starting in " + waveBreak;
			fontLabel.draw(sb, timeInfo, 290, 520);
		}
		
		float saveX = fontLabel.getScaleX();
		float saveY = fontLabel.getScaleY();
		fontLabel.setScale(1f, 1f);
		fontLabel.draw(sb, str1, 550 , 60);
		fontLabel.draw(sb, str2, 550 , 40);
		fontLabel.draw(sb, str3, 550 , 20);	
		fontLabel.setScale(saveX, saveY);
	}
	
	void dispose(){
		this.fontLabel.dispose();
	}
	

}
