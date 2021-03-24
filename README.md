설명
 - 입력컨트롤러(inserver), Biz컨트롤러(bizserver), 출력컨틀롤러(outcontroller)를 
   각각의 RestApi 형식으로 분리구성하였으며 런타임시에 서로 다른 프로세스로 기동되어 http 통신합니다.
   각서버는 필요한만큼 다중으로 기동가능하여 확장성 및 가용성에 용이합니다.
   
   각서버들은 기본적으로 spring boot(v2.4.4)를 사용하였으며 별도의 oss를 추가설치 구성하였습니다.
	- haproxy : 1.8.19
	- logstash : 5.2
	- elastic search : 6.5.1
	- kibana : 7.12.0
	- scouter : 2.12.0
	- sqllite : 3.0
	
   테스트를 위해서는 2가지 방법으로 구성가능합니다.
   - 첫번째 : 별도의 oss설치가 필요없이 서버3개의 jar파일들만 독자적 실행 후 화면을 통한 테스트
   - 두번째 : 위의 oss가 설치되어 있는 구글클라우드(GCP)에 기동되어 있는 화면에 접속하여 테스트 
   - 위의 2가지 방법 모두 모니터링을 위한 scouter client 가 있으면 접속 가능합니다.(없어도 무방)


 - 첫번째 : Only jar파일들로 테스트 하는 방법
   1. 위의 inserver-1.0.jar, bizserver-1.0.jar, outserver-1.0.jar 을 다운받습니다.
   2. 아래의 명령어를 통하여 각 모듈을 1개씩 기동합니다.
   
       # inserver
       - java -Dbiz.host=localhost -Dbiz.port=8088 -Dout.host=localhost -Dout.port=8090  -Din.port=8080 -Dlogs="./logs" -jar inserver-1.0.jar

       # bizserver
       - java -Dbiz.port=8088 -Dlogs="./logs" -jar bizserver-1.0.jar

       # outserver
       - java -Dout.port=8090 -Dlogs="./logs" -jar outserver-1.0.jar
	
       (필수아님)scouter가 있다면 아래의 옵션을 경로에 맞게 변경 후 추가하여 모니터링 가능
	-javaagent:"D:\kakaobank\scouter\agent.java\scouter.agent.jar" -Dscouter.config="D:\kakaobank\scouter\agent.java\conf\scouter.conf" -Dobj_name=outServer

    3. 기동 후 브라우저를 통하여 http://localhost:8080 으로 접속 후 '제출' 버튼 클릭 (여러번 송신가능)
	![로컬_화면_1](https://user-images.githubusercontent.com/25301903/112365714-4ff4aa80-8d1b-11eb-982d-a1611e64b585.PNG)
	
        송신후에는 다음과 같이 수신내역이 보입니다.
	![로컬_화면_2](https://user-images.githubusercontent.com/25301903/112365910-8c280b00-8d1b-11eb-9acb-e3e8f40da9b8.PNG)
