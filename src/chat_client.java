import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class chat_client extends JFrame {
	StringBuffer clientTexts = new StringBuffer();
	int textCount=0;
	int cliMoodPrediction = 1;
	private static JPanel contentPane;
	private static JTextArea msg_area;
	private static JTextArea msg_text;

	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	/**
	 * Launch the application.
	 */
	public static void startchat() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chat_client frame = new chat_client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		try{
			s = new Socket("127.0.0.1", 1201);
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			String msgin = "";
			while(!msgin.equals("exit"))
			{
				msgin = din.readUTF();
				msg_area.setText(msg_area.getText().trim()+ "\n" + msgin.trim());
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	/**
	 * Create the frame.
	 */
	public chat_client() {
		setTitle("Chat Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		
		msg_area = new JTextArea();
		msg_area.setBounds(10, 11, 414, 180);
		getContentPane().add(msg_area);
		msg_area.setColumns(10);
		
		msg_text = new JTextArea();
		msg_text.setBounds(10, 201, 340, 50);
		getContentPane().add(msg_text);
		msg_text.setColumns(10);
		
		Predictor pd = Predictor.getInstance();

		JButton send_btn = new JButton("Send");
		send_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msgout = "";
				msgout = msg_text.getText().trim();
				try {
					dout.writeUTF(msgout);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				msg_text.setText("");
				
				//change color based on mood
				if(textCount>5)
				{
				//setFlag(true);
				cliMoodPrediction = pd.getPrediction();
				System.out.println("Predicted class in client" + cliMoodPrediction);
				if(cliMoodPrediction == 0)
					contentPane.setBackground(Color.RED);
				else if(cliMoodPrediction == 1)
					contentPane.setBackground(Color.GREEN);
				else if(cliMoodPrediction == 3)
					contentPane.setBackground(SystemColor.menu);
				textCount=0;		
				}
				else
					{
					textCount++;
					System.out.println("Incrementing in client. TextCount: "+textCount);
					}

			}
		});
		send_btn.setBounds(359, 202, 65, 49);
		getContentPane().add(send_btn);
	}
}
