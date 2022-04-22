# Ticketing Machine

롯데월드의 티켓 요금 정책을 참고하여 티켓팅 머신을 만들었습니다.

## Class 소개

티켓팅 머신은 두가지의 패키지로 나누어져있습니다:
- 티켓팅 Ticketing
- 데이터 분석 Data Analysis

### 티켓팅 Ticketing 
사용자의 입력을 받아 티켓결과를 출력하고 csv파일에 기록합니다.
```
LotteWorldTicketing.java : 프로그램의 시작을 담당하고, TicketSystem.java 클래스를 불러옵니다.
```
```
TicketSystem.java : 반복문과 함수를 불러서 전체를 제어하는 클래스
```
####
```
AskStuff.java : 입력 클래스, 사용자에게 여러가지를 물어보는 함수가 담긴 클래스
```
```
CalculateStuff.java : 계산 클래스, 받은 입력값으로 출력값을 계산하는 클래스
```
```
PrintStuff.java : 출력 클래스, 출력값을 print하고 csv파일에 기록하는 클래스
```
####
```
OrderData.java : 출력값을 위한 변수가 모여있는 클래스
```
```
StaticValue.java : 변하지않는 상수가 모여있는 클래스
```




### 데이터 분석 Data Analysis
기록된 csv파일을 바탕으로 여러형태의 데이터를 출력하고 다른 csv파일들에 나누어 다시 기록합니다.

```
DataAnalysis.java : 프로그램의 시작을 담당하고, 함수를 불러 전체를 제어합니다.
```
####
```
ReportStuff.java : 결과값을 print하는 클래스
```
```
WriteStuff.java : 결과값을 csv파일에 기록하는 클래스
```

## 실행화면

### Ticketing Machine

![image](https://user-images.githubusercontent.com/31551276/164574649-4548f3f5-7c76-4285-8200-bb5fb6298c3e.png)\
![image](https://user-images.githubusercontent.com/31551276/164574764-2baa625b-2baf-4488-99e7-cf089d134fff.png)\
![image](https://user-images.githubusercontent.com/31551276/164574993-93d80715-60cf-4bbc-89e0-403aca089c7f.png)

### Data Analysis

![image](https://user-images.githubusercontent.com/31551276/164575257-f9b46340-1eb5-48b9-991d-200c84dc5df5.png)\
![image](https://user-images.githubusercontent.com/31551276/164575274-443d63bd-fb8f-420f-a26f-d1f95a01dfe4.png)\
![image](https://user-images.githubusercontent.com/31551276/164575306-c2b1ac21-c859-4e85-b62e-b32875fa4cf7.png)\
![image](https://user-images.githubusercontent.com/31551276/164575321-407e8e04-831b-4fad-95fb-a881cc309be9.png)

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE.md](LICENSE.md) file for details
