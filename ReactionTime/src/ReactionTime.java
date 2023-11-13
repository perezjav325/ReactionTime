import javafx.util.Pair;

import java.util.ArrayList;
import com.aspose.cells.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.SpinnerNumberModel;

public class ReactionTime extends JFrame {

	private JPanel contentPane;
	private long lastTickTime;
	long runningTime;
	int randReact;
	int average;
	int temp;
	int count = 0;
	double hoursSlept;
	ArrayList<Pair<Double, Integer>> list = new ArrayList<Pair<Double, Integer>>();

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		ReactionTime frame = new ReactionTime();
		frame.setVisible(true);
	}

	public int getRand() {
		Random rand = new Random();
		return rand.nextInt(4000) + 1000;
	}

	public ReactionTime() {
		final Workbook workbook = new Workbook();
		final Worksheet worksheet = workbook.getWorksheets().get(0);

		final Color Green = new Color(50, 205, 50);
		final Color White = new Color(255, 255, 255);
		final Color Red = new Color(192, 5, 5);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1297, 737);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBackground(White);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		final JPanel panelReact = new JPanel();
		panelReact.setBounds(0, 0, 1283, 700);
		panelReact.setBackground(White);
		panelReact.setVisible(false);

		final JPanel panelReset = new JPanel();
		panelReset.setBounds(0, 0, 1283, 700);
		panelReset.setBackground(new Color(255, 255, 255));
		panelReset.setVisible(false);

		final JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1283, 700);
		panel.setBackground(White);
		contentPane.add(panel);
		panel.setLayout(null);

		final JSpinner spinnerHours = new JSpinner();
		spinnerHours.setModel(new SpinnerNumberModel(0, 0, 12, 1));
		spinnerHours.setBounds(612, 300, 155, 28);
		spinnerHours.setFocusable(false);
		panel.add(spinnerHours);

		JLabel lblHours = new JLabel("Hours");
		lblHours.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblHours.setBounds(506, 306, 94, 16);
		panel.add(lblHours);

		final JSpinner spinnerMinutes = new JSpinner();
		spinnerMinutes.setModel(new SpinnerNumberModel(0, 0, 45, 15));
		spinnerMinutes.setBounds(612, 340, 155, 28);
		panel.add(spinnerMinutes);
		((JSpinner.DefaultEditor) spinnerMinutes.getEditor()).getTextField().setEditable(false);

		JLabel lblMinutes = new JLabel("Minutes");
		lblMinutes.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblMinutes.setBounds(506, 344, 94, 16);
		panel.add(lblMinutes);

		JLabel lblNewLabel = new JLabel("Approximately how long did you sleep last night?");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel.setBounds(0, 206, 1283, 28);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);

		JButton btnConfirm = new JButton("Confirm");

		btnConfirm.setBounds(511, 401, 261, 28);
		panel.add(btnConfirm);

		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double hours = (Integer) spinnerHours.getValue();
				double mins = (Integer) spinnerMinutes.getValue();
				hoursSlept = (double) ((hours * 60) + mins) / 60;

				panel.setVisible(false);
				panelReact.setVisible(true);
			}
		});
		contentPane.add(panelReset);
		panelReset.setLayout(null);

		JButton btnReset = new JButton("Next Participant");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(true);
				panelReset.setVisible(false);
			}
		});
		btnReset.setBounds(511, 419, 261, 28);
		panelReset.add(btnReset);

		JLabel lblNewLabel_1 = new JLabel("Thank you for your participation!");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(11, 327, 1261, 45);
		panelReset.add(lblNewLabel_1);

		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					worksheet.getCells().importArrayList(list, 0, 0, true);
					workbook.save("logs/Output.xlsx");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnExport.setBounds(1210, 6, 62, 28);
		panelReset.add(btnExport);
		contentPane.add(panelReact);
		panelReact.setLayout(new BorderLayout(0, 0));

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBackground(White);
		panelReact.add(panel_1_1, BorderLayout.SOUTH);
		panel_1_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		panel_1_1.add(btnStart);

		final JLabel label = new JLabel("Press Spacebar when screen turns green");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("SansSerif", Font.PLAIN, 30));
		panelReact.add(label, BorderLayout.CENTER);

		final Timer timer = new Timer(0, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				contentPane.setBackground(Green);
				label.setVisible(false);
				runningTime = System.currentTimeMillis() - lastTickTime - randReact;
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
				if (!timer.isRunning()) {
					timer.setInitialDelay(randReact);
					lastTickTime = System.currentTimeMillis();
					timer.start();
					panelReact.setVisible(false);
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
						panelReact.setVisible(true);
						temp += runningTime;
						count++;
						if (count == 5) {
							count = 0;
							average = temp / 5;
							temp = 0;
							Pair<Double, Integer> p = new Pair<Double, Integer>(hoursSlept, average);
							list.add(p);
							panelReact.setVisible(false);
							label.setText("Press Spacebar when screen turns green");
							panelReset.setVisible(true);
						}
					} else if (contentPane.getBackground().equals(Red)) {
						timer.stop();
						contentPane.setBackground(White);
						label.setForeground(null);
						label.setText("Too soon!");
						label.setVisible(true);
						panelReact.setVisible(true);
					}
				}
			}
		});
	}
}
