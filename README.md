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

    3. 기동 후 브라우저를 통하여 http://localhost:8080 으로 접속 후 '제출' 버튼 클릭
    
	![로컬_화면_1](https://user-images.githubusercontent.com/25301903/112353323-fab29c00-8d0e-11eb-83da-271877db0c4d.PNG)

	![로컬_화면_2](https://user-images.githubusercontent.com/25301903/112353324-fab29c00-8d0e-11eb-96e4-a2901e505beb.PNG)


![GCP_화면_2](https://user-images.githubusercontent.com/25301903/112353315-f8e8d880-8d0e-11eb-862a-7ea8e5185982.PNG)
![haproxy_검증_재기동](https://user-images.githubusercontent.com/25301903/112353319-f9816f00-8d0e-11eb-9901-da60dfdfe08f.PNG)
![haproxy_설정PNG](https://user-images.githubusercontent.com/25301903/112353322-fa1a0580-8d0e-11eb-9e8c-fdfc893d5ed8.PNG)
![서버목록](https://user-images.githubusercontent.com/25301903/112353326-fb4b3280-8d0e-11eb-936f-717b7bf99992.PNG)
![GCP_scouter](https://user-images.githubusercontent.com/25301903/112353327-fbe3c900-8d0e-11eb-8417-b4ea39bf7584.PNG)
![GCP_로그](https://user-images.githubusercontent.com/25301903/112353330-fd14f600-8d0e-11eb-87b8-a8843c1e668b.PNG)
![GCP_에러로그샘플](https://user-images.githubusercontent.com/25301903/112353334-fdad8c80-8d0e-11eb-98ff-ce746372fe56.PNG)
![GCP_출력로그샘플](https://user-images.githubusercontent.com/25301903/112353335-fdad8c80-8d0e-11eb-9d6a-77f519ee4777.PNG)
![GCP_화면_1](https://user-images.githubusercontent.com/25301903/112353338-fe462300-8d0e-11eb-8a92-8dd2f563ff0f.PNG)
