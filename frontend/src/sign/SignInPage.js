import {useState} from "react";
import {login} from "../Auth/AuthAPI";

export default function SignInPage() {
    const [values, setValues] = useState({
        username: "",
        password: "",
    });
    const handleChange = async (e) => {
        setValues({...values,
            [e.target.id]: e.target.value,
        });
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        try{
            const response = await login(values);
                localStorage.clear();
                localStorage.setItem('tokentype', response.tokentype);
                localStorage.setItem('accesstoken', response.accesstoken);
                localStorage.setItem('refreshtoken', response.refreshtoken);

                window.location.href = `/`;
            }catch(error) {
            console.log(error);
        }
    }
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
                        <button type="submit" style={{width: "100%"}}>로그인</button>
                    </div>
                </form>
            </div>
        </div>
    );
}