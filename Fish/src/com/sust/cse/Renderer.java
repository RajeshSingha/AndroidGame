package com.sust.cse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Renderer {
	
	GameWorld world;
	SpriteBatch batch;
	
	public Renderer(GameWorld w, SpriteBatch b){
		world = w;
		batch = b;
	}
	
	public void render(){
		
		world.background.draw(batch);
		world.back.render(batch);
		
		for(int i = 0; i < world.enemys.size(); ++i){
			world.enemys.get(i).render(batch);
		}
		
		world.player.render(batch);
		
		for(int i = 0; i < world.bbls.size(); ++i)
			world.bbls.get(i).render(batch);
		
		if(!world.player.isAlive){			
			world.scoreSprite.draw(batch);
			
			float x = world.scoreSprite.getX();
			float y = world.scoreSprite.getY();
			
			Preferences pref = Gdx.app.getPreferences(Constants.HSCORE);
			
			int hscore = pref.getInteger(Constants.HSCORE, 0);
			if(hscore < world.score)
				hscore  = world.score;
			
			world.font.draw(batch, "" + world.score, x + 105, y + 110);
			world.font.draw(batch, "" + hscore, x + 105, y);
			
			pref.putInteger(Constants.HSCORE, hscore);
			pref.flush();
		}
		
		world.font.draw(batch, "" + world.score ,Gdx.graphics.getWidth() / 2 - 10, Gdx.graphics.getHeight() - 10);
		
		if(!world.player.isAlive)
			return;
		if(world.isPaused)
			world.startbtn.draw(batch);
		else
			world.pausebtn.draw(batch);
	}
	
}
