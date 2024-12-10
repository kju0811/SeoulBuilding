import React from 'react'
import ReactDOM from 'react-dom/client'
import { useEffect, useRef } from "react";
import useGeolocation from "../hooks/useGeolocation.js";

export default function Navermap() {
    const mapRef = useRef(null);
    const markerRef = useRef(null);
    const mapInstance = useRef(null);
    const location = useGeolocation();

    useEffect(() => {
        const { naver } = window;
        if (!mapRef.current || !naver) return;

        const initialLocation = new naver.maps.LatLng(
            location.coordinates?.latitude || 37.3595704,
            location.coordinates?.longitude || 127.105399
        );

        mapInstance.current = new naver.maps.Map(mapRef.current, {
            center: initialLocation,
            zoom: 15,
            zoomControl: true,
            zoomControlOptions: {
                position: naver.maps.Position.TOP_RIGHT
            }
        });

        markerRef.current = new naver.maps.Marker({
            position: initialLocation,
            map: mapInstance.current,
            icon: {
                content: '<div style="width: 15px; height: 15px; background-color: #4A90E2; border-radius: 50%; border: 2px solid white;"></div>',
                anchor: new naver.maps.Point(10, 10)
            }
        });

    }, []);

    useEffect(() => {
        const { naver } = window;
        if (!mapInstance.current || !markerRef.current || !location.coordinates) return;

        const newPosition = new naver.maps.LatLng(
            location.coordinates.latitude,
            location.coordinates.longitude
        );

        markerRef.current.setPosition(newPosition);
        mapInstance.current.setCenter(newPosition);

    }, [location.coordinates]);

    return (
        <div style={{ padding: '1rem' }}>
            <div style={{
                backgroundColor: 'white',
                borderRadius: '8px',
                boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
                padding: '1rem'
            }}>
                <h2 style={{
                    fontSize: '1.5rem',
                    fontWeight: 'bold',
                    marginBottom: '1rem'
                }}>
                    실시간 위치 추적
                </h2>

                <div
                    ref={mapRef}
                    style={{
                        width: '100%',
                        height: '500px',
                        borderRadius: '8px',
                        overflow: 'hidden'
                    }}
                />

                {location.coordinates && (
                    <div style={{
                        padding: '1rem',
                        backgroundColor: '#f8f9fa',
                        borderRadius: '4px',
                        boxShadow: '0 1px 3px rgba(0,0,0,0.1)'
                    }}>
                        <div style={{ fontSize: '0.875rem', color: '#666' }}>현재 위치</div>
                        <div style={{ fontSize: '1rem' }}>
                            {location.coordinates.latitude?.toFixed(6)}, {location.coordinates.longitude?.toFixed(6)}
                        </div>
                        <div style={{ fontSize: '0.75rem', color: '#999' }}>실시간 GPS 좌표</div>
                    </div>
                )}

                {location.error && (
                    <div style={{
                        marginTop: '1rem',
                        padding: '1rem',
                        backgroundColor: '#ff4444',
                        color: 'white',
                        borderRadius: '4px'
                    }}>
                        {location.error}
                    </div>
                )}
            </div>
        </div>
    );
}