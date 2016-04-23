import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class chat_server extends JFrame {

	private static JPanel contentPane;
	private static JTextArea msg_area;
	private static JTextArea msg_text;

	
	static ServerSocket ss;
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
					chat_server frame = new chat_server();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		 
		
		//Start client
		Thread t2 = new Thread(new Runnable(){
			public void run()
			{
				chat_client cc = new chat_client();
				cc.startchat();

			}
			
		});
		t2.start();
		
		
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
	public chat_server() {
		setTitle("Chat Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
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
				
			}
		});
		send_btn.setBounds(360, 196, 64, 55);
		contentPane.add(send_btn);
		
	}

}
