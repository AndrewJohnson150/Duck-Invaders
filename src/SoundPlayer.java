import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;


/**
 * This plays sound in a new thread, which gets rid of those pesky freezes that keep happening
 * @author Andrew Johnson
 */
public class SoundPlayer implements Runnable {

	private String fileName;

	/**
	 * Creates a new SoundPlayer, which automatically plays the sound passed to it in its constructor
	 * @param soundName - the name of the sound to play
	 */
	public SoundPlayer(String soundName) {
		fileName = soundName;
		Thread t = new Thread(this, fileName);
		t.start();
	}
	
	/**
	 * plays the sound passed in the constructor
	 */
	@Override
	public void run() {
		 URL url = Enemy.class.getResource(
	                "/sounds/"+fileName);
	    try
	    {
	        final Clip clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
	        clip.addLineListener(new LineListener()
	        {
	            @Override
	            public void update(LineEvent event)
	            {
	                if (event.getType() == LineEvent.Type.STOP)
	                    clip.close();
	            }
	        });
	        clip.open(AudioSystem.getAudioInputStream(url));
	        clip.start();
	    }
	    catch (Exception exc)
	    {
	        exc.printStackTrace(System.out);
	    }
	}

}
