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
	boolean chklog = false; // 로그인 안한상태 false , 한상태 true

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
							System.out.println("프로그램종료");
							break Label;
						}
					} else {
						if ( selNum.equals("5")) {
							Login();
						} else if ( selNum.equals("6")) {
							Join();
						} else {
							System.out.println("프로그램종료");
							break Label;
						}
					}
				}		
			}		
		}
	}

	private void Join() {
		MemDTO mdto = new MemDTO();
		System.out.println("아이디를 입력하세요.");
		mdto.setId(sc.nextLine());
		System.out.println("이름을 입력하세요.");
		mdto.setName(sc.nextLine());
		System.out.println("나이를 입력하세요.");
		mdto.setAge(sc.nextInt());
		sc.nextLine();
		System.out.println("전화번호를 입력하세요.");
		mdto.setTel(sc.nextLine());
		System.out.println("비밀번호를 입력하세요.");
		mdto.setPw(sc.nextLine());
		while (true) {
			System.out.println("면허종류를 입력하세요.(1종보통, 2종보통)");
			tmp = sc.nextLine();
			if (tmp.equals("1종보통") || tmp.equals("2종보통")) {
				mdto.setLic(tmp);
				break;
			} else {
				System.out.println("(1종보통, 2종보통) 만 입력이 가능합니다.");
			}
		}
		mdao.addMember(mdto);
		mdto = null;
		System.out.println("회원가입이 완료되었습니다.");
	}

	private void Login() {
		MemDTO mdto = new MemDTO();
		System.out.println("아이디를 입력하세요.");
		mdto.setId(sc.nextLine());
		System.out.println("비밀번호를 입력하세요.");
		mdto.setPw(sc.nextLine());
		mdto = mdao.f_RetName(mdto);
		if (mdto != null) {
			System.out.println("로그인성공");
			chklog = true;
			curUser = mdto;
		} else {
			System.out.println("로그인실패");
		}
	}
	
	private void Logout() {
		curUser = null;
		chklog = false;
		System.out.println("로그아웃되었습니다.");
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
		if (cType.equals("승용")) {
			rnt = 40000;
		} else if (cType.equals("SUV")) {
			rnt = 60000;
		} else if (cType.equals("승합")) {
			rnt = 100000;
		}
		return rnt + (time / 24 % 24) * perHour;
	}

	private void BookCar() {
		if (chklog) {
			ViewCList();
			while (true) {
				System.out.println("대여할 번호를 입력하세요.");
				int sel = sc.nextInt() - 1;
				int fee = 0;
				sc.nextLine();
				if (sel <= clist.size() && sel > 0) {
					String selCType = clist.get(sel).getCType();
					if (selCType.equals("승합") && curUser.getLic().equals("2종보통")) {
						System.out.println("2종보통따리는 승합차를 몰 수 없습니다.");
					} else {
						RentDTO rdto = new RentDTO();
						System.out.println("운전면허증 번호를 입력하세요.");
						rdto.setrNum(sc.nextLine());
						rdto.setcType(selCType);
						rdto.setName(curUser.getName());
						rdto.setAge(curUser.getAge());
						rdto.setTel(curUser.getTel());
						System.out.println("대여시간을 입력하세요.");
						rdto.setT(sc.nextInt());
						sc.nextLine();
						fee = CalcFee(rdto.getT(), clist.get(sel).getFee(), selCType);
						rdto.setPw(curUser.getPw());
						rdao.Rent_CRUD(1, rdto, null);
						rdto = null;
						System.out.println("렌트차량예약이 완료되었습니다... 납부하실 금액은 " + fee + " 원 입니다.");
					}
					break;
				} else {
					System.out.println("입력값이 범위를 벗어났습니다.");
				}
			}

		} else {
			System.out.println("로그인후 사용하실 수 있습니다.");
		}
		clist.clear();
	}

	private void RegisterCar() {
		CInfoDTO cdto = new CInfoDTO();
		System.out.println("차종을 입력하세요.");
		cdto.setCType(sc.nextLine());
		System.out.println("제조사를 입력하세요.");
		cdto.setCompany(sc.nextLine());
		System.out.println("소유자를 입력하세요.");
		cdto.setOwner(sc.nextLine());
		System.out.println("전화번호를 입력하세요.");
		cdto.setTel(sc.nextLine());
		System.out.println("연료타입을 입력하세요.");
		cdto.setFuel(sc.nextLine());
		cdao.Car_CRUD(1, cdto, null);
		System.out.println("렌트차량등록이 완료되었습니다.");
		cdto = null;
	}

	public String TabStr(String str) {
		return String.format("%-15s", str);
	}

	public void Menu() {
		System.out.println();
		if (chklog) {
			System.out.println(
					TabStr("1.렌트차량등록") + TabStr("2.렌트차량예약") + TabStr("3.렌트리스트")
					+ TabStr("4.렌트차량보기") + TabStr("5.로그아웃") + "\n 메뉴선택>>");
		} else {
			System.out.println(TabStr("1.렌트차량등록") + TabStr("2.렌트차량예약") + TabStr("3.렌트리스트") 
			+ TabStr("4.렌트차량보기") + TabStr("5.로그인") + TabStr("6.회원가입") + "\n 메뉴선택>>");
		}
		selNum = sc.nextLine();
	}
}
