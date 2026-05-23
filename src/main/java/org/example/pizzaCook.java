package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class pizzaCook implements CookMethod {

    private final Lock pizzaCookLock = new ReentrantLock();
    private int pizzaIngrediment = 10;

    @Override
    public boolean cook(int cookAmount) {

        int AmountUsed = cookAmount * 2; // 피자 메뉴 2개당 재료 소진

        if (pizzaIngrediment - AmountUsed >= 0) {
            pizzaCookLock.lock();
            try {
                System.out.println("=========[" + Thread.currentThread().getName() + "]=========");
                System.out.println("🍕피자" + cookAmount + "개 제조중..");
                Thread.sleep(5000);
                System.out.println("🔔 피자 나왔습니다. 서빙해주세요.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                pizzaCookLock.unlock();
            }

            return true;
        } else {
            System.out.println("🍕🚫 현재 피자 재료가 부족합니다.");
            return false;
        }


    }
}

