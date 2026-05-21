package org.example;

public class FullTimeFloor extends Floor{
    // 필드
    private int baseSalary = 22000;
    public String EmploymentType = "full-time-job";
    private int bonusRate = 0;

    // 생성자
    public FullTimeFloor(String name) {
        super(name);

        System.out.println("👩🏻‍: " + super.job + " " + name + "이고, 저의 요리 숙련도는 " + super.serviceSkillLevel + "입니다.");
    }

    // 매장 인력 관리: 요리 시간 재촉하기


}
