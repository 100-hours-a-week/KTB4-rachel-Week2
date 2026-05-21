package org.example;

public class pastaCook implements CookMethod {

    @Override
    public void cook(int cookAmount) {

        System.out.println("🍝 파스타" + cookAmount + "개 제조중..");
        try { Thread.sleep(5000); } catch (InterruptedException e) {}
        /*
            1. interrupt() 메서드를 호출
            2. 해당 스레드의 내부 불리언 플래그인 interrupted status가 true로 변경
            3. ?

            TODO: 다시 이해하기. 먼말이노.
        */
        System.out.println("🔔 파스타 나왔습니다. 서빙해주세요.");

        // TODO: cook 메소드인데, 서버에게 serve() 메소드 호출 여기서 해? 아님 Main?
    }
}
