package com.zombieattack.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zombieattack.ZombieAttack;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Zombie Attack!";
        config.height = (int)ZombieAttack.SCREEN_HEIGHT;
        config.width = (int)ZombieAttack.SCREEN_WIDTH;
		new LwjglApplication(new ZombieAttack(), config);
	}
}
