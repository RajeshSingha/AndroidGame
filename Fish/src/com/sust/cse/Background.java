package com.sust.cse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Background {

	Sprite grass;
	Animation grass2Anim;
	TextureRegion grass2Frames[];
	
	float grass1Time, grass2Time, grass3Time, grass4Time, grass5Time; 

	public Background() {
		loadAnim();
		
	}

	private void loadAnim() {
		
		grass1Time = 0;
		grass2Time = 0.3f;
		grass3Time = 0.4f;
		grass4Time = 0.5f;
		grass5Time = 0.6f;
		
		grass2Frames = new TextureRegion[24];
		String name1 = "grass2/grass2_000";

		for (int i = 0; i < 24; ++i) {
			if (i < 10) {
				grass2Frames[i] = new TextureRegion(new Texture(
						Gdx.files.internal(name1 + "0" + i + ".png")));
			} else {
				grass2Frames[i] = new TextureRegion(new Texture(
						Gdx.files.internal(name1 + i + ".png")));
			}

		}
		grass2Anim = new Animation(2.0f/24, grass2Frames);

	}
	
	public void render(SpriteBatch batch){
		grass = new Sprite(grass2Anim.getKeyFrame(grass1Time, true));
		grass.setPosition(30, 0);
		grass.draw(batch);
		
		grass = new Sprite(grass2Anim.getKeyFrame(grass2Time, true));
		grass.setPosition(330, 0);
		grass.draw(batch);
		
		grass = new Sprite(grass2Anim.getKeyFrame(grass3Time, true));
		grass.setPosition(500, 0);
		grass.draw(batch);
		
		grass = new Sprite(grass2Anim.getKeyFrame(grass4Time, true));
		grass.setPosition(750, 0);
		grass.draw(batch);
		
		grass = new Sprite(grass2Anim.getKeyFrame(grass5Time, true));
		grass.setPosition(850, 0);
		grass.draw(batch);
	}
	
	public void update(){
		grass1Time += Gdx.graphics.getDeltaTime();
		grass2Time += Gdx.graphics.getDeltaTime();
		grass3Time += Gdx.graphics.getDeltaTime();
		grass4Time += Gdx.graphics.getDeltaTime();
		grass5Time += Gdx.graphics.getDeltaTime();
	}
}
