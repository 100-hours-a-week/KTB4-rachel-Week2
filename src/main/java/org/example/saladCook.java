package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class saladCook implements CookMethod {

    private final Lock saladCookLock = new ReentrantLock();
    private int saladIngrediment = 12;

    @Override
    public boolean cook(int cookAmount) {

        int AmountUsed = cookAmount * 2; // 샐러드 메뉴 1개당 재료 2씩 소진

        if (saladIngrediment - AmountUsed >= 0) {
            saladCookLock.lock();
            try {
                System.out.println("=========[" + Thread.currentThread().getName() + "]=========" );
                System.out.println("🥗 샐러드 " + cookAmount + "개 제조중..");
                Thread.sleep(5000);
                System.out.println("🔔 샐러드 나왔습니다. 서빙해주세요.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                saladCookLock.unlock();
            }

            return true;
        } else {
            System.out.println("🥗🚫 현재 샐러드 재료가 부족합니다.");
            return false;
        }

    }
}
