package codr;

public class CallDriver {
	public CallDriver() {
		
	}
	public void DriverLoad() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로드 성공");
		} catch (Exception e) {
			System.out.println("드라이버 로드 실패");
		}
	}
}
