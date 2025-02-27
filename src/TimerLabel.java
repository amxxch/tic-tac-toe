import java.awt.Font;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

/**
 * The TimerLabel class represents JLabel that displays the current time, 
 * updating every second.
 */
public class TimerLabel extends JLabel {
	
	/**
     * Constructs a TimerLabel and starts the timer 
     * to update the displayed time.
     */
	public TimerLabel() {
        
        Timer timer = new Timer(1000, e -> updateTime());
        timer.start();
        updateTime();
		
        setFont(new Font("SF Pro", Font.PLAIN, 21));
        setVisible(true);
	}
	
	/**
     * Updates the displayed time to the current local time.
     * The time is formatted as "HH:mm:ss".
     */
	private void updateTime() {
		LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        setText("Current Time: " + formattedTime);
	}
	
}
