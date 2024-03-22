package dao;

import java.sql.*;
import java.util.ArrayList;

import codr.*;
import dto.RentDTO;

public class RentDAO {
	private static RentDAO self = null;
	private PreparedStatement psmt = null;
	private CallDriver dr = new CallDriver();
	private CallConn co = new CallConn();

	public static RentDAO getInstance() {
		if (self == null) {
			self = new RentDAO();
		}
		return self;
	}

	private RentDAO() {
		dr.DriverLoad();
	}

	public void Call_psmt(String str) throws SQLException {
		psmt = co.getConn().prepareStatement(str);
	}

	public void Call_exeQ() throws SQLException {
		psmt.executeQuery();
	}

	public void Call_Close() {
		try {
			if (co.getConn() != null) {
				co.getConn().close();
			}
		} catch (Exception e) {
		}
	}

	public void Rent_CRUD(int x, RentDTO rdto, ArrayList<RentDTO> arrList) {
		if (co.checkConn()) {
			try {
				if (x == 1) {
					Booking(rdto);
				} else if (x == 2) {
					selectAll(arrList);
				}
			} catch (SQLException e) {
			} finally {
				Call_Close();
			}
		}
	}

	public void selectAll(ArrayList<RentDTO> arrList) throws SQLException {
		ResultSet rs = null;
		String sql = "select * from rent";
		Call_psmt(sql);
		rs = psmt.executeQuery();
		while (rs.next()) {
			RentDTO rdto = new RentDTO();
			rdto.setrNum(rs.getString("rNum"));
			rdto.setcType(rs.getString("cType"));
			rdto.setName(rs.getString("name"));
			rdto.setAge(rs.getInt("age"));
			rdto.setTel(rs.getString("tel"));
			rdto.setT(rs.getInt("t"));
			rdto.setPw(rs.getString("pw"));
			arrList.add(rdto);
		}
	}

	public void Booking(RentDTO rdto) throws SQLException {
		String sql = "insert into rent values (?,?,?,?,?,?,?)";
		Call_psmt(sql);
		psmt.setString(1, rdto.getrNum());
		psmt.setString(2, rdto.getcType());
		psmt.setString(3, rdto.getName());
		psmt.setInt(4, rdto.getAge());
		psmt.setString(5, rdto.getTel());
		psmt.setInt(6, rdto.getT());
		psmt.setString(7, rdto.getPw());
		Call_exeQ();
	}
}
