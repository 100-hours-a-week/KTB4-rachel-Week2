package org.example;

import java.util.Random;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        // 텍스트용 손님 pool
        Guest[] guestPool = {
                new Guest("김철수", "010-1111-2222"),
                new Guest("이영희", "010-3333-4444"),
                new Guest("박민수", "010-5555-6666"),
                new Guest("이준성", "010-2222-4444"),
                new Guest("rachel", "010-4444-5555"),

        };

        System.out.println("======================================================");
        System.out.println("                      레스토랑 타이쿤                     ");
        System.out.println("======================================================");

        System.out.println("""
                ‼️어세오세요! 이태리 레스토랑 더플레이스 타이쿤입니다‼️
                
                - 테이블 당 손님 1명 이용시간은 1시간, 총 영업시간은 4시간입니다. 총 3일에 걸쳐 진행됩니다.
                - 1. 손님이 메뉴를 보고 주문할 메뉴명과 수량을 입력합니다.
                - 2. 음료 주문 여부를 선택합니다.
                - 3. 영업시간이 끝나면 직원들의 성과(요리 숙련도, 서비숙련도에 맞게 성과 발표)
                - 4. 각 직원 정산. 게임 끝
                
                """);

        // 변수 또는 객체 초기화
        MenuService menu = new MenuService();

        CookMethod cookmethod; // TODO: CookMethod cookmethod = new 메뉴카테고리Cook(); 문법 이해하기

        System.out.println("👩🏻‍🍳 요리사 이름을 정해주세요: ");
        Chef chef = new Chef(sc.nextLine());

        System.out.println("💁🏼‍♀️ 서버 이름을 정해주세요: ");
        PartTimeFloor server = new PartTimeFloor(sc.nextLine());

        Map<String, Integer> todaySales = new HashMap<>();
        int totalRevenue = 0;
        int dailyLoop = 4; // 이것도 입력받기로 수정할 것
        int current = 1;

        // 반복문으로 dailyLoop 만들며 운영하기.
        while(current < dailyLoop) {
            System.out.println("----------[💛"+ current +"일차💛]-----------");
            int dailyGuests = random.nextInt(5) + 1; // 랜덤으로 1명~2명

            for (int i=0; i < dailyGuests; i++) { // TODO: 멀티스레드 구현, 스레드 1개 = 손님 1명
                Guest visitingGuest = guestPool[random.nextInt(guestPool.length)];
                Map<String, Integer> orderPerGuest = new HashMap<>(); // Heap Area에 있지만, 반복문 끝나면 휘발

                System.out.println("어서오세요, " + visitingGuest.name + "님. 오늘이 " + visitingGuest.visitedNum + "번째 방문이시네요! 메뉴판을 보시고 주문해주세요.");
                System.out.println(menu.getMenu());

                System.out.println("💁🏼‍♀️[server]: 메뉴 설명 들어보시겠어요?(Y/n): ");
                String response = sc.nextLine().trim();

                // 메뉴 설명 여부
                if (response.isEmpty() || response.equalsIgnoreCase("y")) {
                    server.explainMenu();
                }
                else if (response.equalsIgnoreCase("n")) {
                    System.out.println("메뉴 설명없이 주문 들어갑니다.");
                } else {
                    System.out.println("잘못된 입력값입니다. 메뉴 설명없이 주문 들어갑니다.");
                }

                //1. 주문 받기 -> TODO: 이거 클래스 메소드로 만드러야 할듯 위에서부터 takeOrder 전체를
                server.takeOrder(orderPerGuest); // TODO: 만약 비동기방식으로 바꾼다면 어떤 Guest 객체의 orderPerGuest인지 다시 작성해여해.

                //2. chef.processCook(메소드 Map이런걸로) 넘겨줌
                for (Map.Entry<String, Integer> entry : orderPerGuest.entrySet()) {
                    String menuName = entry.getKey();
                    int count = entry.getValue();

                    if (menuName.contains("샐러드")) {
                        cookmethod = new saladCook();
                    } else if (menuName.contains("파스타")) {
                        cookmethod = new pastaCook();
                    } else {
                        cookmethod = new pizzaCook();
                    }

                    System.out.print("🔥 [" + menuName + "] "); //TODO: 비동기 방식으로 받기
                    chef.processCook(cookmethod, count);

                }

                // 3. 서빙하기
                server.serve();
                System.out.println("[" + visitingGuest.name + " 손님]: (음식 식사 중...)");
                try {Thread.sleep(2000);} catch (InterruptedException e) {}

                // 4. 결제하기
                System.out.println("🔖 결제를 시작합니다.");
                server.processPayment(orderPerGuest);
            }
        }


    }

}