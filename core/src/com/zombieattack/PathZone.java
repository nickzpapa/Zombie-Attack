package com.zombieattack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class PathZone extends ArrayList<Rectangle>{

	private String storagePath;
	
	PathZone(String fileName){
		
		//getting storage path of no click zones
//		storagePath = Gdx.files.getLocalStoragePath();
//		storagePath = storagePath.replaceFirst("desktop", "core");
//		storagePath = storagePath + "assets/" + fileName;
//		System.out.println(storagePath);
		
		parseFile();
		
	}
	
	
	public void parseFile(){
		
		Scanner reader = null;
		
		try {
			reader = new Scanner(new File(storagePath));						
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(reader.hasNext()) {
			
			float x1 =  reader.nextFloat();
			float y1 = 	reader.nextFloat();
			float x2 = 	reader.nextFloat();
			float y2 = 	reader.nextFloat();
			
			float width  = 	(112 > Math.abs(x1 - x2)) ? 112 : Math.abs(x1 - x2);
			float height =	(112 > Math.abs(y1 - y2)) ? 112 : Math.abs(y1 - y2);			
			
			this.add(new Rectangle(x1, y1, width, height));
			
		}
		this.add(new Rectangle (0, 512, 800, 88));
		this.add(new Rectangle (0, 0, 800, 94));
		
		for(Rectangle i : this)
			System.out.println(i.toString());
		
	}
	
}
