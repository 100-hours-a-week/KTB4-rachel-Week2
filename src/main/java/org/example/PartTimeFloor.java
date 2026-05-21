package org.example;

public class PartTimeFloor extends Floor{
    // 필드
    private int baseSalary = 10320;
    public String EmploymentType = "part-time-job";
    private int bonusRate = 0;

    // 생성자
    public PartTimeFloor(String name) {
        super(name);

        System.out.println("👩🏻‍: " + super.job + " " + name + "이고, 저의 요리 숙련도는 " + super.serviceSkillLevel + "입니다.");
    }

    // 메서드
    @Override
    public void serve() {
        System.out.println("주문하신 메뉴 나왔습니다... 알바생 평가가 진행 중입니다. " + super.job + " 파트의 " + name + " 만족도 조사 부탁드립니다!");
    }




}
