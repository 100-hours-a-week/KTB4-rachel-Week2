package org.example;

public class pizzaCook implements CookMethod{
    @Override
    public void cook(int cookAmount) {

        System.out.println("🍕 피자" + cookAmount + "개 제조중..");
        try { Thread.sleep(8000); } catch (InterruptedException e) {}

        System.out.println("🔔 피자 나왔습니다. 서빙해주세요.");
    }
}
