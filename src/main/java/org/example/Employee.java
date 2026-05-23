package org.example;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Employee {
    // 필드
    public String name;
    public int dailyWorkingHours = 0;

    MenuService menu = new MenuService();

    // 생성자
    public Employee(String name) {
        this.name = name;
        clockIn(name);
        }

    // 메서드(기능)
    // 출근하기
    public void clockIn(String name) {
        System.out.println("🔹 안녕하세요, " + name + " 출근했습니다.");
    }

    // 퇴근하기
    public int clockOut(String name) {
        System.out.println("🔹" + name + "퇴근하겠습니다. 저의 오늘 노동시간은 " + dailyWorkingHours + "입니다..");
        return dailyWorkingHours;
    }

    // TODO: 정산받기
//    public double calculateSalary(int dailyWorkingHours) {
//        // chef에서 bonusRate가 있음. 자식클래스의 값을 불러오는 것은 단방향 상속에 어긋남
//        // 그래서 인터페이스 구현해야할 것 같음.
//
//    }

    // 결제하기
    // TODO: 손님이 먹은 음식을 Map<Integer(테이블명), List(또는 배열로 넣어서)> processPayment 함수 안에서 totalPrice를 구하는게 맞는지
    public void processPayment(GuestOrder visitingGuest) {
        Scanner sc = new Scanner(System.in);
        List<OrderItem> bill = visitingGuest.getValidItemsForPayment(); // 결제 할 최종 주문서

        // totalPrice 계산하기
        int totalPrice = 0;
        for (OrderItem order : bill) {

            System.out.println(order.menuName + ": " + order.orderAmount + "개");
            totalPrice += menu.getPrice(order.menuName) * order.orderAmount;
        }


        // 결제 금액 받아오기 - 음수값 및 예외처리
        System.out.println("- 총 결제 금액: " + totalPrice);

        int paymentAmount;
        int currentPrice;
        int charge;


        while (true)
        {
            try {
                System.out.println("결제할 금액을 작성해주세요: "); //
                paymentAmount = Integer.parseInt(sc.nextLine().trim());

                if (paymentAmount < 0) {
                    throw new IllegalArgumentException("❌ 결제 금액은 음수일 수 없습니다.");
                }

                currentPrice = paymentAmount;
                charge = totalPrice - currentPrice;

                break;

            } catch (NumberFormatException e) {
                System.out.println("❌ 숫자만 입력 가능합니다. 다시 입력해주세요.");
            } catch (IllegalArgumentException e) {
                // 우리가 위에서 throw new ...로 던진 음수 예외가 잡히는 곳
                System.out.println(e.getMessage() + " 다시 입력해주세요.");
            }

        }

        System.out.println("- 현재 지불한 금액: " + currentPrice);

        while(true)
        {

            if (currentPrice == totalPrice) {
                System.out.println("딱 맞게 돈을 주셨네요. 결제 됐습니다. 또 오세요~");
                return;
            } else if (currentPrice > totalPrice) {
                System.out.println(paymentAmount + "원 지불하셔서 거스름돈 " + charge + " 드립니다. 또 오세요~");
                return;
            } else {
                System.out.println("지불한 금액이 부족합니다. " + (totalPrice-currentPrice) + "원을 더 지불하세요.");
                System.out.println("추가로 지불할 금액: ");
                int additionalPrice = sc.nextInt();
                currentPrice += additionalPrice;
            }
        }



    }


}
