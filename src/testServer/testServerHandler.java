package testServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import orderCock_Server_DTO.History;
import orderCock_Server_DTO.Manager;
import orderCock_Server_DTO.PopulList;
import orderCock_Server_DTO.Stamp;
import orderCock_Server_DTO.User;
import orderCock_Server_DTO.orderMenu;


public class testServerHandler extends Thread{
	
	private BufferedReader br;
	private PrintWriter pw;
	private Socket socket;
	PopulList popul;
	User user;
	orderMenu menu;
	History history;
	Manager manager;
	Stamp stamp;
	
	private Connection conn;
	private PreparedStatement pstmt;
	
	
	private ArrayList<testServerHandler> allUserList;
	private ArrayList<orderMenu> orderMenuList = new ArrayList<orderMenu>();
	private ArrayList<PopulList> populMenuList = new ArrayList<PopulList>();
	private ArrayList<Manager> marketList = new ArrayList<Manager>();
	private ArrayList<History> historyList = new ArrayList<History>();
	private ArrayList<Stamp> stampList = new ArrayList<Stamp>();
	
	
	
	testServerHandler(Socket socket, ArrayList<testServerHandler> allUserList, Connection conn){
		this.socket = socket;
		try {
			this.user = new User();
			this.allUserList = allUserList;
			this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.conn = conn;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void run() {
		try {
			String line[] = null;
			while(true) {
					line = br.readLine().split("\\|");
					if(line[0].compareTo(Protocol.LOGIN) == 0) {
						boolean con = true;
						System.out.println("login");

						System.out.println(line[1] + "/" + line[2]);

						// 로그인후 대기실인원=waitUserList
						for (int i = 0; i < allUserList.size(); i++) {
							if ((allUserList.get(i).user.getId()).compareTo(line[1]) == 0) {
								System.out.println(allUserList.get(i).user);
								con = false; // 대기실ID랑, DB에 있는 ID랑 같을때 로그인이 되었다.
							}
						}
						if (con) {
								
							// ==============>   유저정보를 DB에서 가져옵니다 <=============
							
							String sql = "SELECT * FROM user_app WHERE UserId = '"+line[1]+"'"
									+ " and UserPw = '"+line[2]+"'";
							String test="";
							pstmt = conn.prepareStatement(sql);
							ResultSet rs = pstmt.executeQuery(sql);
							int count = 0;	
							
							while (rs.next()) { 
								
								user.setId(rs.getString("UserId"));
								user.setPw(rs.getString("UserPw"));
								user.setName(rs.getString("UserName"));
								user.setEmail(rs.getString("UserEmail"));
								user.setPhone(rs.getString("UserPhone"));
								user.setNick(rs.getString("UserNick"));
								user.setStamp(rs.getInt("UserStamp"));
								user.setBirth(rs.getString("UserBirth"));
								user.setTier(rs.getString("UserTier"));
								user.setExp(rs.getInt("UserExp"));
								user.setStar(rs.getString("UserStar"));
								user.setCoupon(rs.getInt("UserCoupon"));
								
								allUserList.add(this);
								
								count++;
								
							}
							if(count != 0) {
								String userS = "|" + new Gson().toJson(user);
								/*
								 *  로그인시 보내야할 데이터들
								 *  1. 판매메뉴목록
								 *  2. 가게리스트
								 *  3. 인기메뉴리스트
								 */
								sql = "SELECT * FROM other_menu";
								
								pstmt = conn.prepareStatement(sql);
								rs = pstmt.executeQuery(sql);
								while (rs.next()) {
									menu = new orderMenu();
									menu.setName(rs.getString("MenuName"));
									menu.setIndex(rs.getString("MenuIndex"));
									menu.setPrice(rs.getInt("MenuPrice"));
									menu.setKcal(rs.getInt("MenuKcal"));
									menu.setAllregy(rs.getString("MenuAllregy"));
									menu.setNumber(rs.getInt("MenuNumber"));
									orderMenuList.add(menu);
									
									count++;
									
									
								}
								String menuS = "|" + new Gson().toJson(orderMenuList);
								sql = "SELECT * FROM new_popul";
								
								pstmt = conn.prepareStatement(sql);
								rs = pstmt.executeQuery(sql);
								while (rs.next()) {
									popul = new PopulList();
									popul.setNumber(rs.getInt("MenuNumber"));
									popul.setNewmenu(rs.getInt("MenuNew"));
									popul.setPopul(rs.getInt("Menupopul"));
									populMenuList.add(popul);
									
									count++;
									
								}
								String populS = "|" + new Gson().toJson(populMenuList);
								sql = "SELECT * FROM market_manger ";
								pstmt = conn.prepareStatement(sql);
								rs = pstmt.executeQuery(sql);
								count = 0;	
								while (rs.next()) { 
									manager = new Manager();

									manager.setName(rs.getString("ManagerName"));
									manager.setTel(rs.getString("ManagerTel"));
									manager.setMarket(rs.getString("ManagerMarketName"));
									manager.setAdress(rs.getString("ManagerMarketAdress"));
									manager.setX(rs.getFloat("ManagerMarketX"));
									manager.setY(rs.getFloat("ManagerMarketY"));
									marketList.add(manager);
								
									count++;
									
								}
								String managerS = "|" + new Gson().toJson(marketList);
								
								sql = "SELECT * FROM order_history ";
								pstmt = conn.prepareStatement(sql);
								rs = pstmt.executeQuery(sql);
								count = 0;	
								while (rs.next()) { 
									history = new History();

									history.setWhere(rs.getString("History_where"));
									history.setPrice(rs.getInt("History_price"));
									history.setDate(rs.getDate("History_date"));

									historyList.add(history);
								
									count++;
									
								}
								String historyS = "|" + new Gson().toJson(historyList);
								
								sql = "SELECT * FROM order_stamp where stamp_id =  '" + user.getId() + "'";
			                     pstmt = conn.prepareStatement(sql);
			                     rs = pstmt.executeQuery(sql);

			                     while (rs.next()) {
			                        stamp = new Stamp();

			                        stamp.setDate(rs.getDate("stamp_date"));
			                        stampList.add(stamp);

			                        count++;
			                     }

			                     count = 0;
			                     String stampS = "|" + new Gson().toJson(stampList);
								con = false;
								pw.println(Protocol.ENTERLOGIN_OK + "|" + userS + "|" + menuS + "|" + populS + "|" + managerS + "|" + historyS + "|" + stampS);
								pw.flush();
								
								
							} else {
								System.out.println("로그인실패");
								pw.println(Protocol.ENTERLOGIN_NO + "|" + "로그인에 실패했습니다.");
								pw.flush();
							}
						} else {
							System.out.println("로그인중입니다");
							pw.println(Protocol.ENTERLOGIN_NO + "|" + "이미 로그인 중입니다.");
							pw.flush();
						}
					} else if(line[0].compareTo(Protocol.REGISTER) == 0){
						//			회원 가입
						String sql = "Insert into user_app (UserId, UserPw, UserName, UserEmail, UserPhone,"
								+ " UserNick, UserStamp, UserBirth, UserTier, UserExp, UserStar) VALUES "+
						"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, line[2]); // ID
						pstmt.setString(2, line[4]); // Password
						pstmt.setString(3, line[2]); // name
						pstmt.setString(4, line[1]); // email
						pstmt.setString(5, line[3]); // phone
						pstmt.setString(6, line[2]); // nick
						pstmt.setInt(7, 0); // stamp
						pstmt.setString(8, "null"); // birth
						pstmt.setString(9, "Bronze"); // tier
						pstmt.setInt(10, 0); // Exp
						pstmt.setString(11, ""); // Star
						
						System.out.println(pstmt.executeUpdate());
						//intent.putExtra("pw", Protocol.REGISTER +"|"+ editEmail.getText().toString() + "|" + editUser.getText().toString() +
                        //"|" +editPhone.getText().toString()+"|"+ editPassword.getText().toString());
						pw.println(Protocol.ENTERSIGNUP_OK + "|" + "회원가입 성공.");
						pw.flush();
					} else if(line[0].compareTo(Protocol.LOGOUT)==0) {
						// 0프로토콜 1유저이름 2스탬프 3exp 4star
						String sql = "UPDATE user_app SET"
								+ " UserStamp=?, UserTier=?, UserExp=?, UserStar=? WHERE UserId=?";
						
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, line[2]);
						System.out.println(line[2]);
						
						pstmt.setString(2, line[3]);
						System.out.println(line[3]);
						
						pstmt.setString(3, line[4]);
						System.out.println(line[4]);
						
						pstmt.setString(4, line[5]);
						System.out.println(line[5]);
						
						pstmt.setString(5, line[1]);
						pstmt.executeUpdate();
						
						pw.println(Protocol.LOGOUT + "|" + "로그아웃함.");
						pw.flush();
						for(int i=0; i<allUserList.size();i++) {
							if(allUserList.get(i).user.getId().compareTo(line[1]) == 0 ) {
								allUserList.remove(this);
							}
						}
					}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("익셉션발생");
		}
		
	}
	
	
	
	
	
	

}
