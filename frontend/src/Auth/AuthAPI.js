import axios from "axios";

const TOKEN_TYPE = localStorage.getItem("tokentype");
let ACCESS_TOKEN = localStorage.getItem("accesstoken");

// 커스텀 axios 인스턴스 제작
export const AuthApi = axios.create({
    baseURL : 'http://localhost:8080',
    headers: {
        'Content-Type' : 'application/json',
        'Authorization' : `${TOKEN_TYPE} ${ACCESS_TOKEN}`,
    },
});

// 로그인 API
export const login = async ({username,password}) =>{
    const data = { username,password };
    const response = await AuthApi.post("/api/login",data);
    return response.data;
}

//회원가입 API
export const signup = async ({username,password}) =>{
    const data = { username,password };
    const response = await AuthApi.post("/api/signup",data);
    return response.data;
}