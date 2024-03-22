package exe;

import java.util.*;

import dto.*;
import dao.*;

public class Exe {
	Scanner sc = new Scanner(System.in);
	String selNum = null;
	String tmp = null;
	CInfoDAO cdao = CInfoDAO.getInstance();
	RentDAO rdao = RentDAO.getInstance();
	MemDAO mdao = MemDAO.getInstance();
	ArrayList<CInfoDTO> clist = new ArrayList<>();
	MemDTO curUser = null;
	boolean chklog = false; // �α��� ���ѻ��� false , �ѻ��� true

	public Exe() {
		Label : while (true) {
			while (true) {
				Menu();
				if (selNum.equals("1")) {
					RegisterCar();
				} else if (selNum.equals("2")) {
					BookCar();
				} else if (selNum.equals("3")) {
					ViewRList();
				} else if (selNum.equals("4")) {
					ViewCList();
					clist.clear(); // Reset
				} else {
					if ( chklog ) {
						if (selNum.equals("5")) {
							Logout();
							break;
						} else {
							System.out.println("���α׷�����");
							break Label;
						}
					} else {
						if ( selNum.equals("5")) {
							Login();
						} else if ( selNum.equals("6")) {
							Join();
						} else {
							System.out.println("���α׷�����");
							break Label;
						}
					}
				}		
			}		
		}
	}

	private void Join() {
		MemDTO mdto = new MemDTO();
		System.out.println("���̵� �Է��ϼ���.");
		mdto.setId(sc.nextLine());
		System.out.println("�̸��� �Է��ϼ���.");
		mdto.setName(sc.nextLine());
		System.out.println("���̸� �Է��ϼ���.");
		mdto.setAge(sc.nextInt());
		sc.nextLine();
		System.out.println("��ȭ��ȣ�� �Է��ϼ���.");
		mdto.setTel(sc.nextLine());
		System.out.println("��й�ȣ�� �Է��ϼ���.");
		mdto.setPw(sc.nextLine());
		while (true) {
			System.out.println("���������� �Է��ϼ���.(1������, 2������)");
			tmp = sc.nextLine();
			if (tmp.equals("1������") || tmp.equals("2������")) {
				mdto.setLic(tmp);
				break;
			} else {
				System.out.println("(1������, 2������) �� �Է��� �����մϴ�.");
			}
		}
		mdao.addMember(mdto);
		mdto = null;
		System.out.println("ȸ�������� �Ϸ�Ǿ����ϴ�.");
	}

	private void Login() {
		MemDTO mdto = new MemDTO();
		System.out.println("���̵� �Է��ϼ���.");
		mdto.setId(sc.nextLine());
		System.out.println("��й�ȣ�� �Է��ϼ���.");
		mdto.setPw(sc.nextLine());
		mdto = mdao.f_RetName(mdto);
		if (mdto != null) {
			System.out.println("�α��μ���");
			chklog = true;
			curUser = mdto;
		} else {
			System.out.println("�α��ν���");
		}
	}
	
	private void Logout() {
		curUser = null;
		chklog = false;
		System.out.println("�α׾ƿ��Ǿ����ϴ�.");
	}

	private void ViewRList() {
		int rIndex = 1;
		ArrayList<RentDTO> rlist = new ArrayList<>();
		rdao.Rent_CRUD(2, null, rlist);
		if (rlist.size() != 0) {
			for ( RentDTO rdto : rlist ) {
				System.out.print(rIndex + ". ");
				rdto.prt();
				rIndex++;
			}
		}
	}

	private void ViewCList() {
		int cIndex = 1;
		cdao.Car_CRUD(2, null, clist);
		if (clist.size() != 0) {
			for (CInfoDTO cdto : clist) {
				System.out.print(cIndex + ". ");
				cdto.prt();
				cIndex++;
			}
		}
	}
	
	private int CalcFee(int time, int perHour, String cType) {
		int rnt = 0;
		if (cType.equals("�¿�")) {
			rnt = 40000;
		} else if (cType.equals("SUV")) {
			rnt = 60000;
		} else if (cType.equals("����")) {
			rnt = 100000;
		}
		return rnt + (time / 24 % 24) * perHour;
	}

	private void BookCar() {
		if (chklog) {
			ViewCList();
			while (true) {
				System.out.println("�뿩�� ��ȣ�� �Է��ϼ���.");
				int sel = sc.nextInt() - 1;
				int fee = 0;
				sc.nextLine();
				if (sel <= clist.size() && sel > 0) {
					String selCType = clist.get(sel).getCType();
					if (selCType.equals("����") && curUser.getLic().equals("2������")) {
						System.out.println("2����������� �������� �� �� �����ϴ�.");
					} else {
						RentDTO rdto = new RentDTO();
						System.out.println("���������� ��ȣ�� �Է��ϼ���.");
						rdto.setrNum(sc.nextLine());
						rdto.setcType(selCType);
						rdto.setName(curUser.getName());
						rdto.setAge(curUser.getAge());
						rdto.setTel(curUser.getTel());
						System.out.println("�뿩�ð��� �Է��ϼ���.");
						rdto.setT(sc.nextInt());
						sc.nextLine();
						fee = CalcFee(rdto.getT(), clist.get(sel).getFee(), selCType);
						rdto.setPw(curUser.getPw());
						rdao.Rent_CRUD(1, rdto, null);
						rdto = null;
						System.out.println("��Ʈ���������� �Ϸ�Ǿ����ϴ�... �����Ͻ� �ݾ��� " + fee + " �� �Դϴ�.");
					}
					break;
				} else {
					System.out.println("�Է°��� ������ ������ϴ�.");
				}
			}

		} else {
			System.out.println("�α����� ����Ͻ� �� �ֽ��ϴ�.");
		}
		clist.clear();
	}

	private void RegisterCar() {
		CInfoDTO cdto = new CInfoDTO();
		System.out.println("������ �Է��ϼ���.");
		cdto.setCType(sc.nextLine());
		System.out.println("�����縦 �Է��ϼ���.");
		cdto.setCompany(sc.nextLine());
		System.out.println("�����ڸ� �Է��ϼ���.");
		cdto.setOwner(sc.nextLine());
		System.out.println("��ȭ��ȣ�� �Է��ϼ���.");
		cdto.setTel(sc.nextLine());
		System.out.println("����Ÿ���� �Է��ϼ���.");
		cdto.setFuel(sc.nextLine());
		cdao.Car_CRUD(1, cdto, null);
		System.out.println("��Ʈ��������� �Ϸ�Ǿ����ϴ�.");
		cdto = null;
	}

	public String TabStr(String str) {
		return String.format("%-15s", str);
	}

	public void Menu() {
		System.out.println();
		if (chklog) {
			System.out.println(
					TabStr("1.��Ʈ�������") + TabStr("2.��Ʈ��������") + TabStr("3.��Ʈ����Ʈ")
					+ TabStr("4.��Ʈ��������") + TabStr("5.�α׾ƿ�") + "\n �޴�����>>");
		} else {
			System.out.println(TabStr("1.��Ʈ�������") + TabStr("2.��Ʈ��������") + TabStr("3.��Ʈ����Ʈ") 
			+ TabStr("4.��Ʈ��������") + TabStr("5.�α���") + TabStr("6.ȸ������") + "\n �޴�����>>");
		}
		selNum = sc.nextLine();
	}
}
