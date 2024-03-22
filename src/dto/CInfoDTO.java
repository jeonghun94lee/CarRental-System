package dto;

public class CInfoDTO {
	private String CType = null;
	private String Company = null;
	private String Owner = null;
	private String Tel = null;
	private String Fuel = null;
	private int Fee = 0;
	
	public String getCType() {
		return CType;
	}
	public void setCType(String cType) {
		CType = cType;
		setFee();
	}
	public String getCompany() {
		return Company;
	}
	public void setCompany(String company) {
		Company = company;
	}
	public String getOwner() {
		return Owner;
	}
	public void setOwner(String owner) {
		Owner = owner;
	}
	public String getTel() {
		return Tel;
	}
	public void setTel(String tel) {
		Tel = tel;
	}
	public String getFuel() {
		return Fuel;
	}
	public void setFuel(String fuel) {
		Fuel = fuel;
	}
	public int getFee() {
		return Fee;
	}
	public void setFee() {
		if ( CType.equals("�¿�")) {
			Fee = 10000;
		} else if ( CType.equals("SUV")) {
			Fee = 20000;
		} else if ( CType.equals("����")) {
			Fee = 30000;
		}
	}
	public void prt() {
		System.out.println("����: "+CType+" / ������: "+Company+" / ������: "+Owner+
				" / ����ȣ: "+Tel+" / ����Ÿ��: "+Fuel+" / �ʰ��ð�����: "+Fee);
	}
	
}
