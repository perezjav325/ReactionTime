import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.util.Random;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.Font;

public class ReactionTime extends JFrame {

	private static JPanel contentPane;
	private long lastTickTime;
	int randReact;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReactionTime frame = new ReactionTime();
					frame.setVisible(true);
					frame.addWindowListener(new WindowAdapter() {
						public void windowOpened(WindowEvent e) {
							contentPane.requestFocus();
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws InterruptedException 
	 */
	public int getRand() {
        Random rand = new Random();
        return rand.nextInt(4000) + 1000;
		} 
	public ReactionTime() throws InterruptedException {
		 
        
		
		setResizable(false);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		Color Green = new Color(50, 205, 50);
		Color White = new Color(255, 255, 255);
		Color Red = new Color(192, 5, 5);
		
		contentPane = new JPanel();
		contentPane.setFocusCycleRoot(true);
		
		contentPane.setBackground(White);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnStart = new JButton("Start");

		btnStart.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		panel.add(btnStart);
		btnStart.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		
        JLabel label = new JLabel("Press Spacebar when screen turns green");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(label);
        

        Timer timer = new Timer(0, new ActionListener() {

			@Override
            public void actionPerformed(ActionEvent e) {
				contentPane.setBackground(Green);
				label.setVisible(false);
                long runningTime = System.currentTimeMillis() - lastTickTime - randReact;
                Duration duration = Duration.ofMillis(runningTime);
                long millis = duration.toMillis();
                long seconds = millis / 1000;
                millis -= (seconds * 1000);
                label.setText(String.format("%02d.%03d", seconds, millis));
            }
        });
        
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				randReact = getRand();
				contentPane.setBackground(Red);
				label.setForeground(White);
				label.setText("Wait for Green");
				if(!timer.isRunning()) {
					timer.setInitialDelay(randReact);
					lastTickTime = System.currentTimeMillis();
					timer.start();
					panel.setVisible(false);
					contentPane.requestFocus();
				}
			}
		});
		
		contentPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (contentPane.getBackground().equals(Green)) {
					lastTickTime = System.currentTimeMillis();
					timer.stop();
					contentPane.setBackground(White);
					label.setForeground(null);
					label.setVisible(true);
					panel.setVisible(true);
					}
					else if(contentPane.getBackground().equals(Red)){
						timer.stop();
						contentPane.setBackground(White);
						label.setForeground(null);
						label.setText("Too soon!");
						label.setVisible(true);
						panel.setVisible(true);
					}
				}
			}
		});
	}

	

}
