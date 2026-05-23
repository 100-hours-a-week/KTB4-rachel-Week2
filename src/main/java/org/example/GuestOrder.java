package org.example;

import java.util.ArrayList;
import java.util.List;

public class GuestOrder {
    private List<OrderItem> items = new ArrayList<>();
    String guestName;

    public GuestOrder(String guestName) {
        this.guestName = guestName;
    }

    // 게스트 이름 내보내기
    public String getName() {
        return guestName;
    }

    // 메뉴 추가하기 (List에 객체로 추가)
    public void addItem(String name, int amount) {
        OrderItem exisitingItem = null; //TODO: 이 부분은 지피티의 도움을 받았다. null을 넣을 생각을 하지않았다.

        for (OrderItem item : items) {
            if (item.menuName.equals(name)) {
                exisitingItem = item;
                break;
            }
        }

        if (exisitingItem != null) {
            exisitingItem.orderAmount += amount;
            System.out.println("➕ 기존 주문에 수량이 누적되었습니다. (총: " + exisitingItem.orderAmount + "개)");
        } else {
            items.add(new OrderItem(name, amount));
        }

    }

    // 주문한 메뉴 내보내기
    public List<OrderItem> getOrder() {
        return items;
    }



    // 결제할때 최종 메뉴 보내기
    public List<OrderItem> getValidItemsForPayment() {
        List<OrderItem> validItems = new ArrayList<>();
        for (OrderItem item : items) {
            if (item.isAvailable) {
                validItems.add(item);
            }
        }

        return validItems;
    }


}
