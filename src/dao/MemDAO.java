package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import codr.CallConn;
import codr.CallDriver;
import dto.MemDTO;

public class MemDAO {
	private static MemDAO self = null;
	private PreparedStatement psmt = null;
	private CallDriver dr = new CallDriver();
	private CallConn co = new CallConn();

	public static MemDAO getInstance() {
		if (self == null) {
			self = new MemDAO();
		}
		return self;
	}

	private MemDAO() {
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

	public MemDTO f_RetName(MemDTO mdto) {
		ResultSet rs = null;
		MemDTO ret = null;
		if (co.checkConn()) {
			String sql = "select * from member where id=? AND pw=?";
			try {
				Call_psmt(sql);
				psmt.setString(1, mdto.getId());
				psmt.setString(2, mdto.getPw());
				rs = psmt.executeQuery();
				if (rs.next()) {
					mdto.setId(rs.getString("id"));
					mdto.setName(rs.getString("name"));
					mdto.setAge(rs.getInt("age"));
					mdto.setTel(rs.getString("tel"));
					mdto.setPw(rs.getString("pw"));
					mdto.setLic(rs.getString("lic"));
					ret = mdto;
				}
			} catch (SQLException e) {
			} finally {
				Call_Close();
			}
		}
		return ret;
	}

	public void addMember(MemDTO mdto) {
		if (co.checkConn()) {
			String sql = "insert into member values (?,?,?,?,?,?)";
			try {
				Call_psmt(sql);
				psmt.setString(1, mdto.getId());
				psmt.setString(2, mdto.getName());
				psmt.setInt(3, mdto.getAge());
				psmt.setString(4, mdto.getTel());
				psmt.setString(5, mdto.getPw());
				psmt.setString(6, mdto.getLic());
				Call_exeQ();
			} catch (SQLException e) {
			} finally {
				Call_Close();
			}
		}
	}
}
