package com.bjy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

public class DBConnectionDAO {
	private ArrayList<ConnectionObject> connections = new ArrayList<ConnectionObject>(10);
	private String _driver = "oracle.jdbc.driver.OracleDriver";
	private String _url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
	private String _user = "hotel_bjy";
	private String _password = "1234";
	
	private boolean _traceOn = false;		// 사용가능 여부
	private boolean initialize = false;		// 초기화
	private int _openConnections = 50;		// 최대 가능 갯수
	
	private static DBConnectionDAO instance = null; // DBConnectionMgr 객체는 어플리케이션에서 단 하나만 존재 (싱글톤 패턴 사용)
	
	private DBConnectionDAO() {}
	
	public static DBConnectionDAO getInstance() {
		if(instance == null) {
			instance = new DBConnectionDAO();
		}
		return instance;
	}

	/** 열려 있는 Connection 객체 수 설정 **/
	public void setOpenConnectionCount(int count) {
		_openConnections = count;
	}
	
	/** 사용 가능  여부 설정 **/
	public void setEnableTrace(boolean enable) {
		_traceOn = enable;
	}
	
	/** ArrayList<ConnectionObject> connections 를 반환 **/
	public ArrayList<ConnectionObject> getConnectionList(){
		return connections;
	}
	
	/** 현재 열려 있는 Connection 객체 수 반환 **/
	public int getConnectionCount() {
		return connections.size();
	}
	
	/** 새로운 Connection 객체를 생성, 접근 제한자 private : 외부에서 Connection 객체 생성 못하게 함 **/
	private Connection createConnection() {
		Connection conn = null;
		
		try {
			if(_user == null) { _user = ""; }			// 여러 개의 사용자 계정을 쓸 경우를 대비해 사용자 계정을 변경할 경우 먼저 초기화 시켜줌
			if(_password == null) { _password = ""; }	
			
			Properties props = new Properties();
			props.put("user", _user);
			props.put("password", _password);
			
			conn = DriverManager.getConnection(_url, props);
	
		}catch(SQLException e) {
			System.err.println("createConnection() ERR : " + e.getMessage());
		}
		return conn;
	}
	
	/** 모든 연결을 닫고(해제) 연결 풀??**/
	public void finalize() {
		ConnectionObject conns = null;
		
		for(int i=0; i < connections.size(); i++) {
			conns = (ConnectionObject)connections.get(i);			
			try {
				conns.connection.close();	// Vector 안의 ConnectionObject 내의 Connection 객체 자원해제 (close())
			}catch(SQLException e) {
				System.err.println("finalize() close() ERR : " + e.getMessage());
			}
			conns = null;
		}
		connections.clear();	// Vector 안의 모든 객체 제거
	}
	
	/** 현재 사용되지 않지만 연결되어 있는 Connection 자원 해제 **/
	public void releaseFreeConnection() {
		ConnectionObject conns = null;
		
		for(int i=0;i<connections.size();i++) {
			conns = (ConnectionObject)connections.get(i); // 형변환 안해도 가능 아닌가 ? 객체로 반환되기에 형변환 필요하다고 함
			if(!conns.inuse) { removeConnection(conns.connection); }
		}
	}
	
	/** 사용되지 않는 Connection 객체 제거**/
	public void removeConnection(Connection c) {
		if(c==null) { return; }
		
		ConnectionObject conns = null;
		for(int i=0;i<connections.size();i++) {
			conns = (ConnectionObject)connections.get(i);
			
			if(c==conns.connection) {
				try {
					c.close();
					connections.clear();
				}catch(SQLException e) {
					System.err.println("removeConnection() close() ERR : " + e.getMessage());
				}
				break;
			} // end of if()
		
		} // end of for()
		
	} // end of removeConnection()
	
	
	/** PreparedStatement, Statement, ResultSet 다양한 자원 해제 메서드 **/
	/** 매개변수가 다르기에 오버로딩하여 작성 **/
	public void freeConnection(PreparedStatement pstmt, ResultSet rs, Connection c) {
		try {
			if(rs != null) { rs.close(); }
			if(pstmt != null) { pstmt.close(); }
			freeClose(c);
		}catch(SQLException e) {
			System.err.println("freeConnection() pstmt,rs close ERR : " + e.getMessage());
		}
	}
	
	public void freeConnection(Statement stmt, ResultSet rs, Connection c) {
		try {
			if(rs != null) { rs.close(); }
			if(stmt != null) { stmt.close(); }
			freeClose(c);
		}catch(SQLException e) {
			System.err.println("freeConnection() stmt, rs close ERR : " + e.getMessage());
		}
	}
	
	public void freeConnection(Statement stmt, Connection c) {
		try {
			if(stmt != null) { stmt.close(); }
			freeClose(c);
		}catch(SQLException e) {
			System.err.println("freeConnection() stmt close ERR : " + e.getMessage());
		}
	}
	
	public void freeConnection(PreparedStatement pstmt, Connection c) {
		try {
			if(pstmt != null) { pstmt.close(); }
			freeClose(c);
		}catch(SQLException e) {
			System.err.println("freeConnection() pstmt close ERR : " + e.getMessage());
		}
	}
	
	public void freeClose(Connection c) {
		try {
			if (c != null) { c.close(); }
		} catch (SQLException e) {
			System.err.println("DBConnectionDAO - freeClose() err : " + e.getMessage());
		}
	}
	
	
	
	/** 새로운(현재 사용하지 않거나) Connection 객체를 반환**/
	public Connection getConnection() {
		Connection conn = null;
		ConnectionObject conns = null;
		
		if(!initialize) {
		
			try {
				Class.forName(_driver);
				conn = DriverManager.getConnection(_url, _user, _password);
			}catch(ClassNotFoundException e) {
				System.err.println("getConnection() driver ERR : " + e.getMessage());
			}catch(SQLException e) {
				System.err.println("getConnection() connect ERR : " + e.getMessage());
			}
		}
		
		boolean badConnection = false; // 연결이 잘못되었을 경우 상태값 저장
		
		for(int i=0;i<connections.size();i++) {
			conns = (ConnectionObject)connections.get(i);
			
			// 연결이 유효한지 테스트
			if(!conns.inuse) {
				try {
					badConnection = conns.connection.isClosed();	// 기존 Connection 객체가 닫혀있는지 
					
					if(!badConnection) {
						badConnection = (conns.connection.getWarnings() != null);	// getWarnings() : 경고 발생 
					}
				}catch(SQLException e) {
					System.err.println("getConnection() validate ERR : " + e.getMessage());
				}
				
				// 잘못연결된 Connection 객체의 경우 pool(Vector)에서 제거
				if(badConnection) {
					connections.clear();
					continue;
				}
				
				conn = conns.connection;
				conns.inuse = true;
				
				break;
				
			} // end of if(!conns.inuse)
			
			if(conn == null) {
				conn = createConnection();
				conns = new ConnectionObject(conn, true);
				connections.add(conns);
			}
		}
		
		return conn;
	}
	
} // end of DBConnectionMgr


/** Connection 객체와 그 객체의 사용 여부를 저장할 수 있는 클래스 선언 
 * Sub Class 로 (클래스 선언시 접근 제한자 사용불가, 왜냐면 대표 클래스가 접근 제한자를 가지고 있기 때문) **/
class ConnectionObject{
	public Connection connection = null;	// Connection 객체
	public boolean inuse = false;			// 사용여부
	
	public ConnectionObject(Connection c, boolean useFlag) {
		connection = c;
		inuse = useFlag;
	}
	
}
