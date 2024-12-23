import axios from "axios";

const TOKEN_TYPE = localStorage.getItem('tokentype');
let ACCESS_TOKEN = localStorage.getItem('accesstoken');
let REFRESH_TOKEN = localStorage.getItem('refreshtoken');

export const UserApi = axios.create({
    baseURL : 'http://localhost:8080',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': `${TOKEN_TYPE} ${ACCESS_TOKEN}`,
        'REFRESH_TOKEN': REFRESH_TOKEN,
    },
});

//토큰 갱신
const refreshAccessToken = async () => {
    const response = await UserApi.get('api/user/refresh');
    ACCESS_TOKEN = response.data;
    localStorage.setItem('accesstoken', ACCESS_TOKEN);
    UserApi.defaults.headers.common['Authorization'] =  `${TOKEN_TYPE} ${ACCESS_TOKEN}`;
}

//토큰 유효성 검사
UserApi.interceptors.response.use((response) =>{
    return response;
}, async (error) => {
    const originalRequest = error.config;
    if (error.response.status ===  403 && !originalRequest._retry) {
        await refreshAccessToken();
        return UserApi(originalRequest);
    }
    return Promise.reject(error);
});

//회원정보 API
export const fetchUser = async () =>{
    const response = await UserApi.get('api/user');
    return response.data;
}

//회원정보 수정 API
export const updateUser = async (data) => {
    const response = await UserApi.put('api/user/');
    return response.data;
}

//회원탈퇴 API
export const deleteUse = async () => {
    await UserApi.delete('api/user/');
}