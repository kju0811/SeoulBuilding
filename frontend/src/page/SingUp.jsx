import {useState} from "react";
import {signup} from "../api/AuthAPI.jsx";

export default function SignupPage() {
    const [values,setValues] = useState({
        username : "",
        password : "",
    });

    const handleChange = async (e) => {
        setValues({...values,
            [e.target.id]: e.target.value
        });
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await signup(values);
            window.location.href= "/api/login";
        } catch (error){
            console.log(error);
        }
    };

    return (
        <div className="d-flex justify-content-center" style={{minHeight: "100vh"}}>
            <div className="align-self-center">
                <form onSubmit={handleSubmit}>
                    <div className="form-group" style={{minWidth: "25vw"}}>
                        <label htmlFor="username">아이디</label>
                        <input type="text" className="form-control" id="username" onChange={handleChange}
                               value={values.username}/>
                    </div>
                    <div className="form-group" style={{minWidth: "25vw"}}>
                        <label htmlFor="password">비밀번호</label>
                        <input type="password" className="form-control" id="password" onChange={handleChange}
                               value={values.password}/>
                    </div>
                    <div className="form-group" style={{minWidth: "25vw"}}>
                        <button type="submit" style={{width: "100%"}}>회원가입</button>
                    </div>
                </form>
            </div>
        </div>
    );
}