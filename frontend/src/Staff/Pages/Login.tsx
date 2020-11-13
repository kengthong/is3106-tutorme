import React, { useState } from "react";
import { Redirect, useHistory } from "react-router-dom";
import { REGISTRATION_URL } from "../../config/constants";
import logo from "../../assets/logo.jpg";
import { Button, Input } from "antd";
import { UserState } from "../../reducer/user-reducer";
import { useSelector } from "react-redux";
import { IRootState } from "../../store";
import { StaffService } from "../../services/Staff";

const StaffLogin = () => {
    const history = useHistory();
    const userState = useSelector<IRootState, UserState>((state) => state.userReducer);
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
        //Should call on
        //Skeleton
        setHasSubmit(true);
        StaffService.login(formData.email, formData.password);
    };

    return (
        <div className="custom-container flex-row align-center" style={{ height: '100vh' }}>
            <div className="border-e8" style={{ padding: '24px' }}>
                <div className='flex-col align-center'>
                    <img src={logo} style={{ width: '100px', height: '100px' }} alt='logo' />
                    <span
                        style={{
                            fontSize: "3rem",
                            padding: 50,
                        }}
                    >
                        Staff Login
                </span>
                </div>

                {userState.isAuthenticated && <Redirect to={"/staff/home"} />}
                <div style={{ fontSize: '16px', color: 'red' }}>
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
                        <div style={{ display: "flex", justifyContent: "center" }}>
                            <Button
                                type="primary"
                                className="p-button-raised p-pt-4"
                                onClick={handleSubmit}
                            >
                                Login
                            </Button>
                        </div>
                        <div style={{ display: "flex", justifyContent: "center" }}>
                            <Button
                                type="link"
                                className="p-button-link"
                                onClick={handleRedirectToRegister}
                            >
                                Registration
                            </Button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default StaffLogin;
