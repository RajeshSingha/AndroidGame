package com.sust.cse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bubble {
	Sprite b1,b2,b3;
	
	public Bubble(float x , float y){
		b1 = new Sprite(new Texture(Gdx.files.internal("bubble.png")));
		b1.setPosition(x, y);
		b1.setScale(0.3f);
//		
//		b2 = new Sprite(new Texture(Gdx.files.internal("bubble.png")));
//		b2.setScale(0.9f);
//		
//		b3 = new Sprite(new Texture(Gdx.files.internal("bubble.png")));
	}
	
	public void update(){
		b1.translateY(2.0f);
	}
	
	public void render(SpriteBatch batch){
		b1.draw(batch);
	}
}
