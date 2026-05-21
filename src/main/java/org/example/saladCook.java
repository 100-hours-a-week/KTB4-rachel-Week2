package org.example;

public class saladCook implements CookMethod {
    @Override
    public void cook(int cookAmount) {

        System.out.println("🥗 샐러드 " + cookAmount + "개 제조중..");
        try { Thread.sleep(3000); } catch (InterruptedException e) {}

        System.out.println("🔔 샐러드 나왔습니다. 서빙해주세요.");
    }
}
