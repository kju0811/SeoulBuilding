import React, { useState, useEffect, useRef } from 'react';

export default function Navermap({ searchTerm }) {
    const mapRef = useRef(null);
    const mapInstance = useRef(null);
    const [buildings, setBuildings] = useState([]);
    const [searchSuccess, setSearchSuccess] = useState(false);
    const [userLocation, setUserLocation] = useState(null);
    const markersRef = useRef([]); // 마커를 저장할 배열
    const userMarkerRef = useRef(null); // 사용자 위치 마커 저장

    const fetchBuildings = async (searchTerm) => {
        try {
            const response = await fetch(`/api/search?building=${encodeURIComponent(searchTerm)}`);
            if (!response.ok) {
                throw new Error(`HTTP 오류! 상태: ${response.status}`);
            }
            const data = await response.json();
            console.log("검색된 건물 데이터:", data);

            const mappedData = data.map(building => {
                return {
                    latitude: building.lat,
                    longitude: building.lot,
                    name: building.fclt_NM,
                    etc: building.etc,
                    rdn: building.rdn_ADDR,
                    adr: building.lotno_ADDR
                };
            });

            console.log("매핑된 건물 데이터:", mappedData);
            setBuildings(mappedData);
            setSearchSuccess(mappedData.length > 0);
        } catch (error) {
            console.error("건물 정보를 가져오는 데 실패했습니다:", error);
            setSearchSuccess(false);
        }
    };

    // Haversine 공식을 사용하여 두 지점 간의 거리 계산 (단위: 킬로미터)
    const calculateDistance = (lat1, lon1, lat2, lon2) => {
        const R = 6371; // 지구 반경 (킬로미터)
        const dLat = (lat2 - lat1) * Math.PI / 180;
        const dLon = (lon2 - lon1) * Math.PI / 180;
        const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2);
        const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        const distance = R * c; // 킬로미터로 계산
        return distance;
    };

    useEffect(() => {
        if (searchTerm) {
            fetchBuildings(searchTerm); // searchTerm이 변경될 때마다 건물 정보 업데이트
        }
    }, [searchTerm]);

    useEffect(() => {
        const { naver } = window;
        if (!mapRef.current || !naver) return;

        // 지도 초기화
        const initialLocation = new naver.maps.LatLng(
            buildings[0]?.latitude || 37.3595704,
            buildings[0]?.longitude || 127.105399
        );

        mapInstance.current = new naver.maps.Map(mapRef.current, {
            center: initialLocation,
            zoom: 15,
            zoomControl: true,
            zoomControlOptions: {
                position: naver.maps.Position.TOP_RIGHT
            }
        });

        // 기존 마커들 제거
        markersRef.current.forEach(marker => {
            marker.setMap(null); // 마커를 지도에서 제거
        });

        // 마커들을 markersRef에 저장
        markersRef.current = buildings.map((location) => {
            const position = new naver.maps.LatLng(location.latitude, location.longitude);
            const marker = new naver.maps.Marker({
                position: position,
                map: mapInstance.current,
                title: location.name
            });

            const infoWindow = new naver.maps.InfoWindow({
                content: `
                    <div style="padding:10px; color: black;">${location.name}</div>
                    <div style="padding: 5px; color: #5F5F5F;">
                        도로명: ${location.rdn || '없음'}<br>
                        특이사항: ${location.etc || '없음'}<br>
                        주소: ${location.adr || '없음'}
                    </div>
                `
            });

            naver.maps.Event.addListener(marker, 'click', () => {
                infoWindow.open(mapInstance.current, marker);
            });

            return marker; // 마커를 배열에 추가
        });

        // 사용자 위치 마커 추가 (파란색 점, 흰색 테두리, 하늘색 배경)
        if (userLocation && !userMarkerRef.current) {
            const userPosition = new naver.maps.LatLng(userLocation.latitude, userLocation.longitude);
            userMarkerRef.current = new naver.maps.Marker({
                position: userPosition,
                map: mapInstance.current,
                title: '내 위치',
                icon: {
                    content: '<div style="background-color: #87CEFA; border-radius: 50%; border: 2px solid white; width: 20px; height: 20px;"></div>',
                    size: new naver.maps.Size(20, 20),
                    anchor: new naver.maps.Point(10, 10)
                }
            });
            mapInstance.current.panTo(userPosition); // 사용자 위치로 지도 이동
        }
    }, [buildings, userLocation]); // buildings와 userLocation이 변경될 때마다 지도와 마커 갱신

    // 사용자 위치 가져오기
    useEffect(() => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const { latitude, longitude } = position.coords;
                    setUserLocation({ latitude, longitude });
                },
                (error) => {
                    console.error("사용자 위치를 가져오는 데 실패했습니다:", error);
                }
            );
        } else {
            console.error("Geolocation을 지원하지 않는 브라우저입니다.");
        }
    }, []);

    // 목록 클릭 시 해당 마커로 이동하는 함수
    const handleMarkerClick = (index) => {
        const marker = markersRef.current[index];
        if (marker) {
            const position = marker.getPosition(); // 마커의 위치를 가져옴
            mapInstance.current.panTo(position); // 지도 중심을 마커로 이동
        }
    };

    // 돌아가기 버튼 클릭 시 내 위치로 지도 이동
    const handleGoBackToUserLocation = () => {
        if (userLocation && userMarkerRef.current) {
            const userPosition = new naver.maps.LatLng(userLocation.latitude, userLocation.longitude);
            mapInstance.current.panTo(userPosition); // 지도 중심을 사용자 위치로 이동
        }
    };

    return (
        <div className="flex">
            {/* 검색 목록 영역 */}
            <div className="w-1/4 p-4 overflow-auto max-h-screen" style={{ marginTop: '60px' }}>
                <h3 className="text-xl font-semibold mb-4">검색된 건물 목록</h3>
                {searchSuccess && buildings.length > 0 ? (
                    <ul className="space-y-4">
                        {buildings.map((building, index) => {
                            const distance = userLocation ? calculateDistance(
                                userLocation.latitude, userLocation.longitude,
                                building.latitude, building.longitude
                            ) : null;

                            return (
                                <li
                                    key={index}
                                    className="bg-white shadow-lg rounded-lg p-4 hover:bg-gray-100 cursor-pointer"
                                    onClick={() => handleMarkerClick(index)} // 클릭 시 해당 마커로 이동
                                >
                                    <strong className="text-lg text-gray-900">{building.name}</strong><br />
                                    도로명: {building.rdn || '없음'}<br />
                                    특이사항: {building.etc || '없음'}<br />
                                    주소: {building.adr || '없음'}<br />
                                    {distance && (
                                        <span className="text-sm text-gray-600">
                                            거리: {distance.toFixed(2)} km
                                        </span>
                                    )}
                                </li>
                            );
                        })}
                    </ul>
                ) : (
                    <div className="text-gray-500">검색된 결과가 없습니다.</div>
                )}
            </div>

            {/* 지도 영역 */}
            <div className="w-3/4 p-4" style={{ marginTop: '60px' }}>
                <div
                    ref={mapRef}
                    style={{
                        width: '100%',
                        height: '500px',
                        borderRadius: '8px',
                        overflow: 'hidden',
                    }}
                />

                {/* 돌아가기 버튼 */}
                {userLocation && (
                    <button
                        onClick={handleGoBackToUserLocation}
                        className="absolute bottom-4 right-4 bg-blue-500 text-white p-3 rounded-full shadow-lg"
                    >
                        돌아가기
                    </button>
                )}
            </div>
        </div>
    );
}