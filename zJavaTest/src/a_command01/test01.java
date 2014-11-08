package a_command01;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class test01 {
	
	// -----------------------------------------------------	생성자 생성
	
	static class Score implements Serializable { 

		String name;
		int kor;
		int eng;
		int math;

		public Score() {
		}

		public Score(String name, int kor, int eng, int math) {
			this.name = name;
			this.kor = kor;
			this.eng = eng;
			this.math = math;

		}
	}

	static ArrayList<Score> list = new ArrayList<Score>();		// ArrayList를 <Score>안에 담아서 받을꺼에요. 뭉탱이로! (이게 이해가 안된다면, 내일 오셔서 설명 30초?정도만들으시면 되요. 이거 이해 혹시 안되도 아래꺼 보는데 많이 지장은 없어요.)
	static Score score;											// 여러개  홍길동 100 100 100 이렇게 받을시 하나로 뭉탱이를 하나의 인덱스로 받는다고 이해하시면..괜찮을 듯 싶네요! (이 자세한 설명은 나래양에게...)
	static Scanner scanner = new Scanner(System.in);
	
	// -----------------------------------------------------	main 함수

	public static void main(String[] args) throws Exception {
		File f = new File("score.dat");							// 여기다가 이렇게 한 이유는 
		if (f.isFile()) {										// score.dat 파일이 존재한다면, 이 클래스를 실행시에 로딩하려고 한 것이여요.
			FileInputStream in = new FileInputStream("score.dat");
			ObjectInputStream in2 = new ObjectInputStream(in);

			list = (ArrayList<Score>)/*형변환*/ in2.readObject();			// in2 = score.dat 파일을 오브젝트 객체화해서 list에다가 담은거에요.

			in2.close();
			in.close();
			
		}
		while (true) {
			System.out.print("명령> ");
			String keyborad = scanner.nextLine();
			String str[] = keyborad.split(" ");					// 아래에서 0번째 배열 1번째 배열 이렇게 지정할 수 있는 구분 자를 공백으로 만들기위해 한거에요
																// 예를들어 명령창에 add 홍길동 100 90 80 이면, add는 배열 0번째 입니다.
			switch (str[0]) { // --------------------------- 0번째 입력되는 값을 통하여 출력할 수 있도록 설정해 놓은 것이에요. 이것이 str[0]번째 배열값을 받는다는 것이에요.

			case "list":
				list();
				break;

			case "view":
				view(Integer.parseInt(str[1]));
				break;

			case "add":		// str[0]은 명령을 받는 곳이니 이곳에는 안적었구요. 그 다음부터 설정값을 받으니까  str[1] 부터 시작합니다. (예를들어 : 홍길동) // 다음 str[2]부터는 점수들이니 parseInt로 형변환 시켜줘야 합니다! 숫자값니까요.
				add(str[1], Integer.parseInt(str[2]), Integer.parseInt(str[3]),
						Integer.parseInt(str[4]));
				break;

			case "delete":
				delete(Integer.parseInt(str[1]));
				break;

			case "update":
				update(Integer.parseInt(str[1]));
				break;

			case "help":
				showMenu();
				break;

			case "exit":
				exit();
				break;

			default:
				System.out.println("잘못된 명령어 입니다.");
				System.out.println(" ");

			} // swithch

		} // while

	} // public static void main
	
	
	
	
	
	// 이 곳 부 터 는 죄 다 명 령 어 별 로 나 눠 놨 습 니 다.  ( 자세한 설명은 내일 강사님이 한번더 해주실꺼고요. 대충만이라도 이해하고 오셔야 내일 고생안해요. 오늘 이거 만드는것만 해서 대충만 이해하고 오셔도 괜찮을 듯 싶어요. )
	

	// ----------------------------------------------------- str[0]번쨰 파일이 list 값과 일치 할 시 호출되는 함수에요. 나머지 아랫것들도 똑같아요.

	public static void list() {		
		for (int i = 0; i < list.size(); i++) {
			score = list.get(i);
			System.out.print(i + "  ");
			System.out.print("이름 : " + score.name + "  ");
			System.out.print("국어 : " + score.kor + "  ");
			System.out.print("수학 : " + score.math + "  ");
			System.out.print("영어 : " + score.eng + "  \n");

		}

	}
	
	// ----------------------------------------------------- view 호출

	public static void view(int index) {
		score = list.get(index);	// --------------------- list에 저장된 값을 불러와서 score에 저장하고 그걸 다시 view는 명령어로 디테일하게 보여주는 것입니다.
		int sum = score.eng + score.kor + score.math;
		float average = sum / 3.0f;
		System.out.println("인덱스 :" + index);
		System.out.println("이름 :" + score.name);
		System.out.println("국어 :" + score.kor);
		System.out.println("영어 :" + score.eng);
		System.out.println("수학 :" + score.math);
		System.out.println("합계 :" + sum);
		System.out.println("평균 :" + average);
	}
	
	// ----------------------------------------------------- add 호출  // add 홍길동 100 90 80 으로 예를들어 입력했을떄 list 배열에 저장하는 것이에요.

	public static void add(String name, int kor, int eng, int math) {
		list.add(new Score(name, kor, eng, math));
		System.out.println("저장되었습니다.");
	}
	
	// ----------------------------------------------------- add에 의해 저장되었고, list으로 확인가능한 값들을 변경시킬수 있는 함수입니다. 사용하시려면,   명령> update 0 이라던가 list에 담겨있는 원하는 인덱스를 업데이트랑 같이 입력해 주시면 되요.
																																			
	public static void update(int index) {
		if (index >= list.size()) {	// --------------------- 누나가 예를들어 add로 2개의 값을 저장시켰는데 누나가 뜬금없이 update로 3번째를 업데이트하겠다고 찾게되면, 에러뜨게 한 코드에요.
			System.out.println("존재하지 않는 인덱스 입니다.");
			return;
		}
		score = list.get(index);			// ------ 값을 받아서 수정할수 있습니다. 
		String name, kor, eng, math;				
		System.out.println("이름 (" + score.name + ") ?");
		name = scanner.nextLine();
		System.out.println("국어(" + score.kor + ") ?");
		kor = scanner.nextLine();
		System.out.println("영어(" + score.eng + ") ?");
		eng = scanner.nextLine();
		System.out.println("수학(" + score.math + ") ?");
		math = scanner.nextLine();
		System.out.print("정말 변경하시겠습니까?(y/n)");
		String a = scanner.nextLine();
		
		// 이 아래 코드는 다 수정하고나서 바꿀것인지 y/n 여부를 물어보는데 누나가 공백이나 n값을 입력하면 수정 안되게 한 코드에요.
		if (a.equalsIgnoreCase("y")) {
			if (!name.equals(""))		//혹시 모르실까봐 적는데 "" 이건 공백 값이라면 안받는 다는 의미입니다. 반드시 y값만 받는 것이지요.
				score.name = name;
			if (!kor.equals(""))
				score.kor = Integer.parseInt(kor);
			if (!eng.equals(""))
				score.eng = Integer.parseInt(eng);
			if (!math.equals(""))
				score.math = Integer.parseInt(math);
		} else if (a.equalsIgnoreCase("n")) {
			System.out.println("변경 취소하였습니다.");
		} else {
			System.out.println("잘못 입력하셨습니다.");
		}

	}

	// ----------------------------------------------------- delete 코드에요. 배열 인덱스 통해서 그 줄을 삭제합니다.
	
	public static void delete(int index) {
		if (index >= list.size()) {
			System.out.println("존재하지 않는 인덱스 입니다.");
			return;
		}
		System.out.print("정말 삭제하시겠습니까?(y/n)");
		String a = scanner.nextLine();
		if (a.equalsIgnoreCase("y")) {			// 아까 본거랑 조금 틀리죠? 이건 대소문자 구분 안한다는 것이에요.
			list.remove(index);
			System.out.println("삭제하였습니다.");
		} else if (a.equalsIgnoreCase("n")) {
			System.out.println("삭제 취소하였습니다.");
		}

	}

	// -----------------------------------------------------	//  help입력시 누나가 쓸수 있는 명령어 적어놓은 코드들이에요. 일종의 가이드~@
	
	public static void showMenu() { // help
		System.out.println("list");
		System.out.println("view 인덱스");
		System.out.println("add 이름 국어 영어 수학");
		System.out.println("delete 인덱스");
		System.out.println("update 인덱스");
		System.out.println("exit");
		System.out.println("");
	}

	// -----------------------------------------------------	// exit 입력시 종료하는 코드에요.

	public static void exit() throws IOException { // exit
		FileOutputStream out = new FileOutputStream("score.dat");	// 누나가 exit입력시 종료되는 동시에 파일 저장하게 만드는 것이에요. 다음에 다시 누나가 콘솔을 실행 시켯을때 list라는 명령어
		ObjectOutputStream out2 = new ObjectOutputStream(out);		// 전에 저장했던것들이 그대로 있다는 것을 알 수 있어요.
		out2.writeObject(list);

		out2.close();
		out.close();

		System.out.println("종료합니다.");
		System.exit(0);	// 종료시키는 코드입니다.
	}

}
	// -----------------------------------------------------