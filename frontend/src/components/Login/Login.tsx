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
  const userState = useSelector<IRootState, UserState>((state) => state.userReducer);
  const [ hasSubmit, setHasSubmit] = useState(false);
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
    UserService.login(formData.email, formData.password);
  };

  return (
    <div>
      <img src={logo} style={{ padding: 100 }} alt='logo'/>
      <h1
        style={{
          fontSize: "3rem",
          padding: 50,
        }}
      >
        Welcome, Please log in
      </h1>

      {userState.isAuthenticated && <Redirect to={"/"} />}
      <div style={{fontSize: '16px', color: 'red'}}>
        {hasSubmit && userState.error? userState.errorMsg: null }
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
  );
};

export default Login;
