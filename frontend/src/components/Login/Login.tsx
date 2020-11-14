import React, {useState} from "react";
import {Redirect, useHistory} from "react-router-dom";
import {REGISTRATION_URL} from "../../config/constants";
import logo from "../../assets/logo.jpg";
import {Button, Input} from "antd";
import {UserService} from "../../services/User";
import {UserState} from "../../reducer/user-reducer";
import {useSelector} from "react-redux";
import {IRootState} from "../../store";

const Login = () => {
    const history = useHistory();
    const userState = useSelector<IRootState, UserState>(
        (state) => state.userReducer
    );
    const [hasSubmit, setHasSubmit] = useState(false);
    const handleRedirectToRegister = () => history.push(REGISTRATION_URL);

    const [formData, setFormData] = useState({
        email: "",
        password: "",
    });

    const handleChange = (e: any) => {
        const name = e && e.target && e.target.name ? e.target.name : "";
        const value = e && e.target && e.target.value ? e.target.value : "";
        setFormData((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleSubmit = (e: any) => {
        setHasSubmit(true);
        UserService.login(formData.email, formData.password);
    };

    return (
        <div className="custom-container flex-row align-center" style={{height: '100vh'}}>
            <div className="border-e8" style={{padding: '24px', maxWidth: '400px'}}>
                <div className='flex-col align-center'>

                    <img src={logo} style={{width: '100px', height: '100px'}} alt='logo'/>

                    <div
                        style={{
                            fontSize: "2rem",
                            padding: 50,
                            textAlign: 'center'
                        }}
                    >
                      <div className="flex-row justify-center w-100">
                        Welcome
                      </div>
                      <div className="flex-row justify-center w-100">
                          Please log in
                        </div>
                    </div>
                </div>

                {userState.isAuthenticated && <Redirect to={"/"}/>}
                <div style={{fontSize: "16px", color: "red"}}>
                    {hasSubmit && userState.error ? userState.errorMsg : null}
                </div>
                <form onSubmit={handleSubmit}>
                    <div className="p-fluid">
                        <div className="p-field">
                            <label htmlFor="email">Email</label>
                            <Input
                                name="email"
                                id="emailinput"
                                type="text"
                                onPressEnter={handleSubmit}
                                onChange={(e) => handleChange(e)}
                            />
                        </div>
                        <div className="p-field">
                            <label htmlFor="password">Password</label>
                            <Input.Password
                                name="password"
                                id="passwordinput"
                                type="password"
                                onPressEnter={handleSubmit}
                                onChange={(e) => handleChange(e)}
                            />
                        </div>
                        <br></br>
                        <div style={{display: "flex", justifyContent: "center"}}>
                            <Button
                                type="primary"
                                className="p-button-raised p-pt-4"
                                onClick={handleSubmit}
                            >
                                Login
                            </Button>
                        </div>
                        <div style={{display: "flex", justifyContent: "center"}}>
                            <Button
                                type="link"
                                className="p-button-link"
                                onClick={handleRedirectToRegister}
                            >
                                Registration
                            </Button>
                        </div>
                        <br/>
                        <br/>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default Login;
