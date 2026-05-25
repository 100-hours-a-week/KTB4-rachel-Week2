# 회고
## 1. "어느 부분에 비동기 방식을 구현할 것인가?"에 대한 고민
레스토랑 타이쿤을 구현하여 비동기 방식을 초기에 '손님' 기준으로 스레드를 만들려고 하였다. 즉, 여러 손님을 동시에 받고자 하였는데 현실 세계와 다르게 CLI 프로그램에서는 user가 손님인 척 주문을 입력을 해야하기 때문에 비동기 방식이 불가하였다. 그래서 요리사(Chef)에서 '샐러드 파트', '파스타 파트', '피자 파트'를 동시에 조리할 수 있는 동시성을 구현하였다. 코드는 조리한다(cookMethod 인터페이스의 cook();) 동사로 적혀있지만, 쉽게 설명하면 '샐러드 요리사', '파스타 요리사', '피자 요리사'가 각각 따로 있다고 생각하면 된다. 각 파트별로 주문이 들어오면 파트별 요리사들은 동시에 각자의 요리를 조리한다.

## 2. synchronized가 아니라 lock 객체로 구현체마다 부여한 이유
synchronized는 클래스 또는 메서드마다 객체 기준으로 lock을 걸 수 있다. 하지만 본인 프로그램은 구현체마다 유연한 lock을 걸 수 있도록 하고 싶었고, 수업 시간에도 synchronized 보다는 실무에서 lock 키워드를 더 많이 쓴다고 하여 ReentrantLock의 파트별 lock을 만들었다.

- saladCookLock
- pastaCookLock
- pizzaCookLock
## 3. 그치만, join()을 if-else문 때문에 적절하게 사용을 못하고 있다.
주문 받은 내역을 for문을 돌아가며 스레드를 실행시키는 거라 join() 메서드를 사용하여 메인 스레드의 실행을 제어하기가 애매한 상황이다.

<구현하고 싶은 시나리오>
- 주문을 받고, 조리를 다음 Chef 클래스의 processCook() 메서드를 실행시킨다. 
- 그리고 '샐러드', '파스타', '피자' 메뉴가 주문이 되어 있으면 각각의 파트 요리사들이 동시에 조리한다. 
- 모든 메뉴가 준비돼야 서버가 서빙한다.(Floor class의 serve() 메서드)

그런데 현재 조리 기능을 담당하는 prcoesCook()에서 반복문을 돌면서 메뉴를 확인하기 때문에 다음과 같은 문제에 직면하였다.

문제 1) join 메서드를 if-else 구문마다 넣어줄때: '샐러드', '파스타', '피자' 조리가 비동기 방식으로 조리 되지않는다. 스레드를 만든 게 무의미함.
```
// 요리 제어하는 메서드: '샐러드', '파스타', '피자' 파트별로 주방사가 있음. 비동기 방식으로 처리 가능함.
    public void processCook(GuestOrder visitingGuest) {
        List<OrderItem> allItems = visitingGuest.getOrder();
        System.out.println("Chef에게 들어온 매뉴 리스트: " + allItems);

        // 메뉴 조리 성공 여부 boolean 변수
        // boolean IsAvailable 변수를 하나로 퉁치면 하나의 스레드가 먼저 끝나면 의도치않게 IsAvailable 값이 변경 될 것 같아, lock을 건 메소드마다 boolean 변수를 만들었습니다.


        // 각 메서드는 lock 객체 구현
        for (OrderItem item : allItems) {
            String menuName = item.menuName;
            int cookAmount = item.orderAmount;

            System.out.println("🔥 [" + menuName + " 조리 들어갔습니다.]");

            if (menuName.contains("샐러드")) {
                cookmethod = new saladCook();
                
                Thread saladThread = new Thread(() -> {
                    boolean saladIsAvailable = cookmethod.cook(cookAmount);
                    if(!saladIsAvailable) {
                        System.out.println("[샐러드 재료가 부족하여, 샐러드 메뉴는 나오지 못하였습니다.]");
                        item.isAvailable = false;
                    }
                });

                saladThread.start();
                // 샐러드 스레드 join()
//                try {
//                    saladThread.join(); // saladThread가 끝날 때까지 main 스레드가 여기서 대기
//
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }

            } else if (menuName.contains("파스타")) {
                cookmethod = new pastaCook();
                
                Thread pastaThread = new Thread(() -> {
                    boolean pastaIsAvailable = cookmethod.cook(cookAmount);
                    if(!pastaIsAvailable){
                        System.out.println("[파스타 재료가 부족하여, 파스타 메뉴는 나오지 못하였습니다.]");
                        item.isAvailable = false;
                    }
                });

                pastaThread.start();
                // 파스타 스레드 join()
//                try {
//                    pastaThread.join(); // pastaThread 끝날 때까지 main 스레드가 여기서 대기
//
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }

            } else {
                cookmethod = new pizzaCook();
                
                Thread pizzaThread = new Thread(() -> {
                    boolean pizzaIsAvailable = cookmethod.cook(cookAmount);
                    if(!pizzaIsAvailable) {
                        System.out.println("[피자 재료가 부족하여, 피자 메뉴는 나오지 못하였습니다.]");
                        item.isAvailable = false;
                    }
                });

                pizzaThread.start();
                
                // 피자 스레드 join() 
//                try {
//                    pizzaThread.join(); // pizzaThread 끝날 때까지 main 스레드가 여기서 대기
//
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
            }

           

        }

    }
            

```

문제 2) if-else문이 끝나고 마지막에 한꺼변 join()할 때: if-else 안에서 선언한 스레드이기 때문에 if-else 밖에서 join()을 실행할 수가 없다. '샐러드', '파스타', '피자'가 주문이 안들어올 수도 있고, 들어올 수도 있고 예측이 불가하다. 그래서 예측이 불가한 상황이라 if-else를 넣고 각각 Thread를 생성했었다. 

```

// if-else 문 끝나고 밖에


             try {
                saladThread.join();
                pastaThread.join();
                pizzaThread.join();
            } catch (catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

```

## 4. 주문서(bill)를 기존의 Map 컬렉션에서 클래스의 객체로
기존에는 Main 메서드와 서버(Floor), 요리사(Chef)가 손님이 주문한 내역을 Map<String, Integer> 타입으로 매개변수를 통해 전달하였다. 처음에 주문한 내역이 딕셔너리처럼 저장되고 중간에 수정없이 그대로 결제할때 빌지로도 사용되었다.

  - 손님이 주문한 내역: {"알리오 올리오 파스타", 2,
                    "풍기 피자", 3}
  - 결제 내역: {"알리오 올리오 파스타", 2,
    "풍기 피자", 3}

하지만 '요리사 조리하다가 재료가 소진돼어 주문 반려'라는 시나리오를 하나더 만들었다. 이렇게 되면 처음에 손님이 주문 내역과 결제 내역이 달라질 수가 있다. 
초기에는 Map 하나로 '손님이 주문한 내역'과 '결제 내역'을 하나로 퉁 쳤다면, 이후에는 반려된 메뉴에 대해서 boolean으로 표시를 하고 싶었다.

### 그래서 고민한 방법은
방법 1) Map의 value에 리스트로 넣기 {"알리오 올리오 파스타", [2, true],
                                "풍기 피자", [3, false]}

방법 2) 손님주문에 대한 클래스를 따로 넣어 List<손님주문객체>으로 넣기

### 선택한 방법
방법 2를 이용하여 손님 주문에 관한 것도 Map이 아닌 객체지향적으로 만들고 매개변수로 전달하기로 하였다. 방법 1은 솔직히 코드 수정도 적어 간단하였다. 이미 여러 클래스 간 Map을 매개변수로 주고 받고 있었기 때문이다. 그런데 이후에 주문 내역에 대한 각각의 '수량'과 '반려여부' 등 세부적으로 제어하기 위해서는 결국엔 클래스로 만드는 것이 좋다고 생각하여 선택하였다.

## 5. 정답은 없겠지만 잘 짜여진 코드가 무엇일까라는 고민
- contains을 이용해서 메뉴를 확인하는 것이 맞는지
- 반복문이나 if-else 구문을 필요이상으로 쓰고 있는 것은 아닌지
- 굳이 매개변수로 안넘겨도 되는 것인지, 해당 클래스에 다른 클래스의 객체를 선언해야 하는지
- '레스토랑 타이쿤'이라는 컨셉에서 PartTimeJob 서버가 바로 위에 부모자식과의 차별점이 무엇인지..(아직 해결 안됨)

라는 고민을 해봤습니다.

# 피드백 받고 싶은 점
1. join()을 적절하게 사용하는 방법
