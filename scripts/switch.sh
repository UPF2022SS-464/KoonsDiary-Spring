# !/usr/bin/env bash

# switch.sh
# nginx 연결 설정 스위치

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function find_idle_profile()
{
    # curl 결과로 연결할 서비스 결정
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

    if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면 (즉, 40x/50x 에러 모두 포함)
    then
        CURRENT_PROFILE=test2
    else
        CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    # IDLE_PROFILE : nginx와 연결되지 않은 profile
    if [ ${CURRENT_PROFILE} == test1 ]
    then
      IDLE_PROFILE=test2
    else
      IDLE_PROFILE=test1
    fi

    # bash script는 값의 반환이 안된다.
    # echo로 결과 출력 후, 그 값을 잡아서 사용한다.
    echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == test1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 Port: $IDLE_PORT"
    echo "> Port 전환"
    # nginx와 연결한 주소 생성
    # | sudo tee ~ : 앞에서 넘긴 문장을 service-url.inc에 덮어씀
    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

    echo "> 엔진엑스 Reload"
    # nignx reload. restart와는 다르게 설정 값만 불러옴
    sudo service nginx reload
}