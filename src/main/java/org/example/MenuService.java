package org.example;

import java.util.Map;

public class MenuService {
    /*
        - 메뉴판 Map 불러오기
        - Floor가 소개하는 메뉴 설명 Map 불러오기
        - dailyMap 이거..는 Main에서 휘발성으만 써야겠죠?
    */
    private static Map<String, Integer> MENU = Map.of ( // TODO: 왜 int가 아니라 Integer을 사용할까?// TODO: 일단 static final로 메뉴를 구성했다. final은 또 뺌. 수정할 수 있으니까. 메뉴는 원래 잘 안바뀌니까 근데 신메뉴가 가~끔 나올땐 어떻게 해야하지?
            "풍기 피자", 22000,
            "토마토 피자", 20000,
            "알리오 올리오 파스타", 18000,
            "크림 파스타", 18000,
            "리코타 프루타 샐러드", 15000,
            "시저 샐러드", 16000
    );

    public Map<String, String> MENU_INFO = Map.of (
            "풍기 피자", "버섯을 들들 볶아 고소하면서 버섯 특유의 풍미를 느낄 수 있습니다.",
            "토마토 피자", "직접 재배한 토마토를 장시간 끓여 만든 토마토 피자입니다.",
            "알리오 올리오 파스타", "마늘과 올리브오일의 조화가 굳인 파스타입니다.",
            "크림 파스타", "더플레이스만의 매쉬드 크림을 베이스로 한 파스타입니다.",
            "리코타 프루타 샐러드", "제철 과일과 리코타치즈가 올라간 샐러드입니다.",
            "시저 샐러드", "견과류와 야채가 섞인 샐러드입니다."
    );

    // 생성자
    // 알아서 생기겟죠

    // 메서드
    // 메뉴판 불러오기(캡슐화)
    public Map<String, Integer> getMenu() {
        return MENU;
    }

    public Integer getPrice(String menuName) {
        return MENU.get(menuName);
    }

    // 손님이 주문한 음식이 메뉴판 유효검사
    public boolean haspMenu(String menuName) {
        return MENU.containsKey(menuName);
    }

    // 소개멘트
    public void TellMenuInfo (String orderInfo) {
        // TODO: try-catch 구문 정리
        // 알아간 것: java Map은 없는 key를 get메서드로 조회하면 조용히 null 반환. 그래서 문제
        try {
            System.out.println("💁🏼‍♀️[server]: " + MENU_INFO.get(orderInfo));
        } catch (NullPointerException e) {
            System.out.println("MENU에 없는 값이라 null값을 반환하였습니다. 메뉴명을 띄어쓰기 없이 제대로 입력해주세요!");
        }
    }

}
