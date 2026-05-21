package org.example;

import java.util.Map;
import java.util.Scanner;

public class Floor extends Employee{
    Scanner sc = new Scanner(System.in);
    MenuService menu = new MenuService(); // TODO: 불변 변수마냥 호출되는 클래스인데, Main메서드에도 선언하고 클래스 내에서도 선언 해도 불필요하지않겠죠?
    // 필드
    protected static final String job = "Server"; // TODO: 접근제어자와 메모리공유영역 final 여부 고민함
    public int serviceSkillLevel = 0; // 보너스 여부와 정규직 or 아르바이트가 갈림

    //생성자
    public Floor(String name) { //TODO: 자식클래스 생성자 매개변수에는 부모클래스 생성자 매개변수 + 자식클래스의 고유값?이면 돼?
        super(name);

        System.out.println("👩🏻‍🍳: " + job + " " + name + "입니다.");
    }

    // 메서드
    // 메뉴 설명하기
    public void explainMenu() {

        while(true) {
            System.out.println("설명을 들을 메뉴 이름을 그대로 적어주세요 (주문을 마치려면 '종료' 또는 엔터 입력):");
            String foodName = sc.nextLine().trim();
            if(foodName.isEmpty() || foodName.equals("종료")) {
                return;
            }
            else {
                menu.TellMenuInfo(foodName);
            }
        }

        // MenuService menuService = new MenuService();
        // menuService.TellMenuInfo(orderInfo); // 메뉴 설명
        // TODO: 이렇게 맨날 menuService 객체를 생성하는게 맞음?

    }

    // 주문받기
    public Map<String, Integer> takeOrder(Map<String, Integer> order) {

        while (true) {
            System.out.println("주문하실 메뉴를 띄어쓰기와 함께 그대로 적어주세요.(주문을 완료하려면 '종료' 또는 엔터): ");
            String orderFood = sc.nextLine().trim();

            if (orderFood.isEmpty() || orderFood.equals("종료")) {break;}

            if(!menu.haspMenu(orderFood)) { // 메뉴에 없다면
                System.out.println("❌ 저희 매장 메뉴판에 없는 음식입니다. 다시 입력해주세요!");
                continue;
            }

            System.out.print("🔢 [" + orderFood + "] 몇 개 주문하시겠습니까?: ");
            int orderAmount = Integer.parseInt(sc.nextLine().trim());

            // 누적으로 주문하기
            order.put(orderFood, order.getOrDefault(orderFood, 0) + orderAmount);
            System.out.println("➕ 주문서에 담겼습니다.");

        }

        return order;
    }

    // 서빙하기
    public void serve() {
        System.out.println("주문하신 메뉴 나왔습니다.");
    }

    // 음료만들기
    public void makeDrink(Guest guest, int drinkNum) {

        if (guest.guestRank == "VVIP") {
            System.out.println("VVIP이시네요. 무료 에이드 1잔과 커피 " + drinkNum + "잔 드릴게요~!");
            return;
        }

        System.out.println("커피" + drinkNum + "잔 드릴게요.");

    }

}
