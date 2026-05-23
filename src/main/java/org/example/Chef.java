package org.example;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chef extends Employee {
    // 필드
    private int baseSalary = 25000; // TODO: private랑 protected를 문법적 차이는 알겠는데 막상 서비스에 넣을려니까 좀 애매함.
    private int bonusRate = 0;
    public String job = "Chef";
    public int cookingSkillLevel = 0;

    CookMethod cookmethod; // TODO: 여기에 이렇게 선언해도 되나요?



    // 셍성자
    public Chef(String name) {
        super(name);
        // this.cookingSkillLevel = cookingSkillLevel;

        System.out.println("👩🏻‍🍳: " + job + " " + name + "이고, 저의 요리 숙련도는 " + cookingSkillLevel + "입니다.");
    }

    // 메서드

    // 정산하기 - 오버로딩
    public int calculateSalary() {
        int dailySalary = super.dailyWorkingHours * baseSalary;

        return dailySalary;
    }

    public double calculateSalary(int bonusRate) {
        this.bonusRate = bonusRate;
        int baseWage = super.dailyWorkingHours * baseSalary;
        int bonus = baseWage * (this.bonusRate / 100);

        // 최종 지급액 = 기본 시급 + 보너스
        return baseWage + bonus;
    }

    public double calculateSalary(double bonusRate) {
        // this.bonusRate = bonusRate;  // TODO: bonusRate를 제네릭으로 만들어서 calculateSalary를 하나로 통일하자니 매개변수가 안들어올 수도 있고, 오버로드 하자니 bonusRate의 int와 double 타입이 겹친다. 여기어카지
        this.bonusRate = (int) bonusRate; // 일단 임시..
        int baseWage = super.dailyWorkingHours * baseSalary;
        double bonus = baseWage * bonusRate;

        return baseWage + bonus;
    }

    // 보너스 책정
    /*
     - 접근제어자
     - 반환값
     - 매개변수
     - 설명 : 원래 의도는 cookingSkillLevel가 높아질수록 보너스를 더 챙겨주려고 했음.. 하지만 시간이 부족해 그냥 cookingSkillLevel 메서드로 구현
    */

    // 요리 제어하는 메서드: '샐러드', '파스타', '피자' 파트별로 주방사가 있음. 비동기 방식으로 처리 가능함.
    public void processCook(GuestOrder visitingGuest) {
        List<OrderItem> allItems = visitingGuest.getOrder();
        System.out.println("Chef에게 들어온 매뉴 리스트: " + allItems);

        // 메뉴 조리 성공 여부 boolean 변수
        // boolean IsAvailable 변수를 하나로 퉁치면 하나의 스레드가 먼저 끝나면 의도치않게 IsAvailable 값이 변경 될 것 같아, lock을 건 메소드마다 boolean 변수를 만들었습니다.


        // 주문 메뉴 보며 조리하기 - '샐러드', '파스타', '피자' 파트별 쉐프가 비동기 방식으로 조리(lock 객체이용)
        for (OrderItem item : allItems) {
            String menuName = item.menuName;
            int cookAmount = item.orderAmount;

            System.out.println("🔥 [" + menuName + " 조리 들어갔습니다.]");

            if (menuName.contains("샐러드")) {
                cookmethod = new saladCook();
                // saladIsAvailable = cookmethod.cook(cookAmount); // 각 메서드는 lock 객체 구현
                Thread saladThread = new Thread(() -> {
                    boolean saladIsAvailable = cookmethod.cook(cookAmount);
                    if(!saladIsAvailable) {
                        System.out.println("[샐러드 재료가 부족하여, 샐러드 메뉴는 나오지 못하였습니다.]");
                        item.isAvailable = false;
                    }
                });

                saladThread.start();

//                try {
//                    saladThread.join(); // saladThread가 끝날 때까지 main 스레드가 여기서 대기
//
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }

            } else if (menuName.contains("파스타")) {
                cookmethod = new pastaCook();
                // pastaIsAvailable = cookmethod.cook(cookAmount);
                Thread pastaThread = new Thread(() -> {
                    boolean pastaIsAvailable = cookmethod.cook(cookAmount);
                    if(!pastaIsAvailable){
                        System.out.println("[파스타 재료가 부족하여, 파스타 메뉴는 나오지 못하였습니다.]");
                        item.isAvailable = false;
                    }
                });

                pastaThread.start();

//                try {
//                    pastaThread.join(); // pastaThread 끝날 때까지 main 스레드가 여기서 대기
//
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }

            } else {
                cookmethod = new pizzaCook();
                // pizzaIsAvailable = cookmethod.cook(cookAmount);
                Thread pizzaThread = new Thread(() -> {
                    boolean pizzaIsAvailable = cookmethod.cook(cookAmount);
                    if(!pizzaIsAvailable) {
                        System.out.println("[피자 재료가 부족하여, 피자 메뉴는 나오지 못하였습니다.]");
                        item.isAvailable = false;
                    }
                });

                pizzaThread.start();

//                try {
//                    pizzaThread.join(); // pizzaThread 끝날 때까지 main 스레드가 여기서 대기
//
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
            }

        }



    }

}