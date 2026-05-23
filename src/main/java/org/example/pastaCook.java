package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class pastaCook implements CookMethod {


    private final Lock pastaCookLock = new ReentrantLock();
    private int pastaIngrediment = 12;

    @Override
    public boolean cook(int cookAmount) {

        int AmountUsed = cookAmount * 2; // 샐러드 메뉴 1개당 재료 2씩 소진

        if (pastaIngrediment - AmountUsed >= 0) {
            pastaCookLock.lock();
            try {
                System.out.println("=========[" + Thread.currentThread().getName() + "]=========" );
                System.out.println("🍝 파스타" + cookAmount + "개 제조중..");
                Thread.sleep(5000);
                System.out.println("🔔 파스타 나왔습니다. 서빙해주세요.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                pastaCookLock.unlock();
            }

            return true;
        } else {
            System.out.println("🍝🚫 현재 파스타 재료가 부족합니다.");
            return false;
        }

    }




}
