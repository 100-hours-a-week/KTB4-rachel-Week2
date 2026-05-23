package org.example;

import java.util.Map;

public class Guest {
    // 필드
    protected String name;
    protected String phoneNum; // 전화번호는 int로 하면 의미없는 숫자가 된다. 그래서 String으로 받는다.
    protected int visitedNum; // 누적 방문횟수

    public static final Map<Integer, String> GUEST_RANK = Map.of(
            1, "general",
            2, "VIP",
            3, "VVIP"
    );
    public String guestRank; // VVIP, VIP, 일반고객 분류. TODO: 어떤 자료구조로 해야하지?


    // 생성자
    public Guest(String name, String phoneNum) {
        this.name = name;
        this.phoneNum = phoneNum;
        guestRank =  GUEST_RANK.get(1);
    }

    //메서드

    // 어느정도 누적 방문횟수가 늘어나면 VIP 손님으로 승격. 할인 5% 주기
    public void GetCupon(int visitedNum) {
        // 다른 결제 클래스의 메소드랑 어떻게 연결하지?

        if (visitedNum > 10) {
            System.out.println("VVIP 고객으로 선정됐습니다. 10% 할인권과 무료 에이드 1잔을 드립니다.");
            GUEST_RANK.get(3);
            // 선택지 1) Main에서 Pay 클래스 호출하고 매개변수로 GetCupon 넘겨주기
            // 선택지 2) Main 갈 필요없이 바로 Pay 클래스에서 GetCupon 호출하기
        } else if (visitedNum > 5) {
            System.out.println("VIP 고객으로 선정됐습니다. 5% 할인권을 드립니다.");
            GUEST_RANK.get(2);

        } else {
            System.out.println("최소 6번 이상 방문해주셔야 프라이빗 쿠폰이 제공됩니다.");
            GUEST_RANK.get(1);
        }
    }



}
