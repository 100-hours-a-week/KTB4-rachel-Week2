package org.example;

public class OrderItem {
    // 필드
    String menuName; //주문한 메뉴 이름
    int orderAmount; // 주문한 메뉴의 수량
    public boolean isAvailable = true;

    // 생성자
    public OrderItem(String menuName, int orderAmount) {
        this.menuName = menuName;
        this.orderAmount = orderAmount;
    }

}
