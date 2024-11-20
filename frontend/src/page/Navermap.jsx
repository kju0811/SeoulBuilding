import { useEffect, useRef } from "react";

export default function Navermap() {
    const mapRef = useRef(null);

    // 위도와 경도를 숫자로 설정
    const lat = 37.3595704; // 원하는 위도 값
    const lng = 127.105399; // 원하는 경도 값

    useEffect(() => {
        const { naver } = window; // 네이버 지도 API가 로드되었는지 확인
        if (mapRef.current && naver) {
            // 지도의 중심 위치
            const location = new naver.maps.LatLng(lat, lng);

            // 지도 생성
            const map = new naver.maps.Map(mapRef.current, {
                center: location, // 중심 좌표
                zoom: 10,         // 확대 정도
            });

            // 마커 추가
            new naver.maps.Marker({
                position: location,
                map,
            });

        }

    }, [lat, lng]); // lat과 lng가 바뀔 때마다 effect 재실행

    return (
        <div
            ref={mapRef}
            style={{ width: "1000px", height: "500px" }}
        ></div>
    );
}