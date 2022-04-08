package client.utils;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundHandler {
	/**
	 *  Plays the background music while the app is running
	 */
	public static void playMusic() {
		playSound("background.mp3", true);
	}

	/**
	 * Plays the joker sounds when a joker is clicked
	 *  in multiplayer
	 */
	public static void jokerSound() {
		playSound("joker.mp3", false);
	}

	/**
	 * Plays the joker sounds when a joker is clicked
	 *  in multiplayer
	 */
	public static void clickSound() {
		playSound("click.mp3", false);
	}

	/**
	 *  Plays the media found in path
	 * @param path the path of the media to be played
	 * @param loop whether the media loops after it ends
	 */
	private static void playSound(String path, boolean loop) {
		String finalPath = "./src/main/resources/sound/" + path;

		Thread thread = new Thread(() -> {
			Media sound = new Media(new File(finalPath).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);

			if (loop) { // makes sure the media keeps on playing
				mediaPlayer.setOnEndOfMedia(new Runnable() {
					@Override
					public void run() {
						mediaPlayer.seek(Duration.ZERO);
						mediaPlayer.play();
					}
				});
			}

			mediaPlayer.play();
		});

		thread.run();
	}
}
