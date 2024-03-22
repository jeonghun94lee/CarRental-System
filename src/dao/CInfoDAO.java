package dao;

import java.sql.*;
import java.util.*;

import codr.*;
import dto.CInfoDTO;


public class CInfoDAO {
	private static CInfoDAO self = null;
	private PreparedStatement psmt = null;
	private CallDriver dr = new CallDriver();
	private CallConn co = new CallConn();

	public static CInfoDAO getInstance() {
		if (self == null) {
			self = new CInfoDAO();
		}
		return self;
	}

	private CInfoDAO() {
		dr.DriverLoad();
	}

	public void Call_psmt(String str) throws SQLException {
		psmt = co.getConn().prepareStatement(str);
	}

	public void Call_exeQ() throws SQLException {
		psmt.executeQuery();
	}
	
	public void Car_CRUD(int x,CInfoDTO cdto,ArrayList<CInfoDTO> arrList) {
		if (co.checkConn()) {
			try {
				if ( x == 1) { // addCar
					addCar(cdto);
				} else if ( x == 2) { // SelectAll
					selectAll(arrList);
				}
				
			} catch (Exception e) {
			} finally {
				try {
					if (co.getConn() != null) {
						co.getConn().close();
					}
				} catch (Exception e) {

				}
			}
		}
	}

	public void addCar(CInfoDTO cdto) throws SQLException {
		String sql = "insert into car values (?,?,?,?,?,?)";
		Call_psmt(sql);
		psmt.setString(1, cdto.getCType());
		psmt.setString(2, cdto.getCompany());
		psmt.setString(3, cdto.getOwner());
		psmt.setString(4, cdto.getTel());
		psmt.setString(5, cdto.getFuel());
		psmt.setInt(6, cdto.getFee());
		Call_exeQ();
	}

	public void selectAll(ArrayList<CInfoDTO> arrList) throws SQLException {
		ResultSet rs = null;
		String sql = "select * from car";
		Call_psmt(sql);
		rs = psmt.executeQuery();
		while (rs.next()) {
			CInfoDTO cd = new CInfoDTO();
			cd.setCType(rs.getString("CType"));
			cd.setCompany(rs.getString("Company"));
			cd.setOwner(rs.getString("Owner"));
			cd.setTel(rs.getString("Tel"));
			cd.setFuel(rs.getString("Fuel"));
			cd.setFee();
			arrList.add(cd);
		}
	}

}
