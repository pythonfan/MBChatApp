import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;

public class chat_server extends JFrame {

	StringBuffer serverTexts = new StringBuffer();
	int textCount=0;
	int moodPrediction = 1;
	private static JPanel contentPane;
	private static JTextArea msg_area;
	private static JTextArea msg_text;
	boolean textLimitFlag=false;
	
	static ServerSocket ss;
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	
	Predictor pd;
	
	
	/**
	 * Launch the application.
	 */
	public  void startchat() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chat_server frame = new chat_server();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		 
		
		
		
		String msgin = "";
		try{
			ss = new ServerSocket(1201);
			s = ss.accept();
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			while(!msgin.equals("exit"))
			{
				msgin = din.readUTF();
				msg_area.setText(msg_area.getText().trim()+"\n"+msgin);
				//System.out.println("in receiver textcount=" +textCount);
				if(textCount<5)
				{
					serverTexts.append(msgin+" ");
					textCount++;
				}
				else
				{
					//setFlag(true);
					moodPrediction = pd.predict(serverTexts.toString());
					System.out.println("Predicted class in main" + moodPrediction);
					if(moodPrediction == 0)
						contentPane.setBackground(Color.RED);
					else if(moodPrediction == 1)
						contentPane.setBackground(Color.GREEN);
					else if(moodPrediction == 3)
						contentPane.setBackground(Color.LIGHT_GRAY);
					serverTexts.delete(0, serverTexts.length());
					textCount=0;

					
				}
				
			}
			
			//Add message content to string buffer 
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	/**
	 * Create the frame.
	 */
	public chat_server() {
		setTitle("Chat Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		msg_area = new JTextArea();
		msg_area.setBounds(10, 11, 414, 174);
		contentPane.add(msg_area);
		msg_area.setColumns(10);
		
		msg_text = new JTextArea();
		msg_text.setBounds(10, 198, 341, 53);
		contentPane.add(msg_text);
		msg_text.setColumns(10);
		
		JButton send_btn = new JButton("Send");
		
		pd = Predictor.getInstance();
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
				//System.out.println("in sender textcount=" +textCount);

				if(textCount<5)
					{
						serverTexts.append(msgout+" ");
						textCount++;
					}
				else
				{
					//setFlag(true);
					moodPrediction = pd.predict(serverTexts.toString());
					System.out.println("Predicted class in main" + moodPrediction);
					if(moodPrediction == 0)
						contentPane.setBackground(Color.RED);
					else if(moodPrediction == 1)
						contentPane.setBackground(Color.GREEN);
					else if(moodPrediction == 3)
						contentPane.setBackground(SystemColor.menu);
					serverTexts.delete(0, serverTexts.length());
					textCount=0;

					
				}
				
			}
		});
		send_btn.setBounds(360, 196, 64, 55);
		contentPane.add(send_btn);
		
	}
	
	
}
