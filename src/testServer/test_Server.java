package testServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;



public class test_Server {
	

	private ServerSocket serverSocket;
	
	String protocol = "jdbc:mariadb://";
	String ip = "localhost";
	String port = "3307";
	String db = "order";
	
	private ArrayList<testServerHandler> allUserList;
	private Connection conn;
	private String driver = "org.mariadb.jdbc.Driver";
	private String url = String.format("%s%s:%s/%s", protocol, ip, port, db);
	private String user = "root";
	private String password = "7984";

	
	public test_Server() {
		try {
			allUserList = new ArrayList<testServerHandler>();
			Class.forName(driver);
			//		db연동 Connect

			conn = DriverManager.getConnection(url, user, password);
			System.out.println("서버 실행됨");
	
			//		서버 포트지정
			serverSocket = new ServerSocket(9500);
			/*
			 * 		각 리스트에 담아준다.
			 * 		룸 리스트 저장
			 */
		
		
			while(true) {
				/*
				 * 		소켓 
				 */
				Socket socket = serverSocket.accept();		
				
				testServerHandler handler = new testServerHandler(socket, allUserList, conn);
				handler.start();
				
				if(socket.isClosed()) {
					System.out.print("close");
				}
				
				System.out.println(socket.getInetAddress());
				
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new test_Server();
	}
	
	
}
