package dto;

public class RentDTO {
	private String rNum = null; // 자동차운전면허증번호
	private String cType = null; // 차종
	private String name = null;
	private int age = 0;
	private String tel = null;
	private int t = 0; // 대여시간
	private String pw = null;
	
	public String getrNum() {
		return rNum;
	}
	public void setrNum(String rNum) {
		this.rNum = rNum;
	}
	public String getcType() {
		return cType;
	}
	public void setcType(String cType) {
		this.cType = cType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public int getT() {
		return t;
	}
	public void setT(int t) {
		this.t = t;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public void prt() {
		System.out.println("면허번호 :"+rNum+" / 차종 : "+cType+" / 이름 : "+name+" / 나이 : "+age+
				"세 / 대여시간 : "+t+"시간");
	}
}
