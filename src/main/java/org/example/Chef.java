package org.example;

public class Chef extends Employee {
    // 필드
    private int baseSalary = 25000; // TODO: private랑 protected를 문법적 차이는 알겠는데 막상 서비스에 넣을려니까 좀 애매함.
    private int bonusRate = 0;
    public String job = "Chef";
    public int cookingSkillLevel = 0;

    // 셍성자
    public Chef(String name) {
        super(name);
        // this.cookingSkillLevel = cookingSkillLevel;

        System.out.println("👩🏻‍🍳: " + job + " " + name + "이고, 저의 요리 숙련도는 " + cookingSkillLevel + "입니다.");
    }

    // 메서드

    // 정산하기 - 오버로딩
    public int calculateSalary(){
        int dailySalary = super.dailyWorkingHours * baseSalary;

        return dailySalary;
    }

    public double calculateSalary(int bonusRate) {
        this.bonusRate = bonusRate;
        int baseWage = super.dailyWorkingHours * baseSalary;
        int bonus = baseWage * (this.bonusRate / 100);

        // 최종 지급액 = 기본 시급 + 보너스
        return baseWage + bonus;
    }

    public double calculateSalary(double bonusRate) {
        // this.bonusRate = bonusRate;  // TODO: bonusRate를 제네릭으로 만들어서 calculateSalary를 하나로 통일하자니 매개변수가 안들어올 수도 있고, 오버로드 하자니 bonusRate의 int와 double 타입이 겹친다. 여기어카지
        this.bonusRate = (int) bonusRate; // 일단 임시..
        int baseWage = super.dailyWorkingHours * baseSalary;
        double bonus = baseWage * bonusRate;

        return baseWage + bonus;
    }

    // 보너스 책정
    /*
     - 접근제어자
     - 반환값
     - 매개변수
     - 설명 : 원래 의도는 cookingSkillLevel가 높아질수록 보너스를 더 챙겨주려고 했음.. 하지만 시간이 부족해 그냥 cookingSkillLevel 메서드로 구현
    */


    // 요리하기 - interface를 실행하는 메서드
    public void processCook(CookMethod method, int cookAmount) {
        method.cook(cookAmount); // 호출 시, 예시로 pasta.cook(2); 이런식으로 하면 됨!
    }
}
