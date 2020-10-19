import React, { useState } from "react";
import { LOGIN_URL } from "../../config/constants";
import { useHistory } from "react-router-dom";
import logo from "../../assets/logo.jpg";
import { Input, Button, Radio, DatePicker, Modal } from "antd";
import {UserService} from "../../services/User";

const RegisterComponent = () => {
  const [formData, setFormData] = useState({
    confirmPassword: "",
    date: "",
    email: "",
    firstName: "",
    gender: "Male",
    lastName: "",
    password: "",
    phoneNumber: "",
  });

  const history = useHistory();

  const verifyForm = () => {
    let a = formData.firstName != "";
    let b = formData.lastName != "";
    let c = formData.password != "";
    let d = formData.confirmPassword != "";
    let e = formData.phoneNumber != "";
    let f = formData.date != "";
    let g = formData.email != "";
    let h = formData.password == formData.confirmPassword;
    return a && b && c && d && e && f && g && h;
  };

  //To be implemented
  const handleSubmit = (e: any) => {
    //Should call on API
    //Skeleton
    if (verifyForm()) {
      UserService.register();
    } else if (formData.password != formData.confirmPassword) {
      Modal.error({
        title: "Error",
        content: "Please ensure password is identical to retyped password",
      });
    } else {
      console.log("failed");
      Modal.error({
        title: "Error",
        content: "Please ensure all fields are filled",
      });
    }
  };

  const handleRedirectToLogin = () => history.push(LOGIN_URL);

  const handleChange = (e: any) => {
    console.log(e);
    const name = e && e.target && e.target.name ? e.target.name : "";
    const value = e && e.target && e.target.value ? e.target.value : "";
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));

    console.log(formData);
  };

  //Need this to be separated because DatePicker returns 2 values
  const handleDateChange = (dateData: any, dateString: any) => {
    setFormData((prevState) => ({
      ...prevState,
      date: dateString,
    }));
  };

  return (
    <div>
      <img src={logo} style={{ padding: 100 }} />
      <h1
        style={{
          fontSize: "3rem",
          padding: 50,
          textAlign: "center",
        }}
      >
        Registration page
      </h1>

      <form>
        <div className="p-fluid ">
          <div className="p-field p-col-12 p-md-6">
            <label htmlFor="firstname">First name</label>
            <Input
              name="firstName"
              type="text"
              onChange={(e) => handleChange(e)}
              required={true}
            />
          </div>
          <div className="p-field p-col-12 p-md-6">
            <label htmlFor="lastname">Last name</label>
            <Input
              name="lastName"
              type="text"
              onChange={(e) => handleChange(e)}
              required={true}
            />
          </div>

          <div className="p-field ">
            <label htmlFor="email">Email</label>
            <Input
              name="email"
              type="text"
              onChange={(e) => handleChange(e)}
              required={true}
            />
          </div>
          <div className="p-field ">
            <label htmlFor="password">Password</label>
            <Input.Password
              name="password"
              type="text"
              onChange={(e) => handleChange(e)}
              required={true}
            />
          </div>
          <div className="p-field ">
            <label htmlFor="password">Retype Password</label>
            <Input.Password
              name="confirmPassword"
              type="text"
              onChange={(e) => handleChange(e)}
              required={true}
            />
          </div>
          <div className="p-field ">
            <label htmlFor="phonenumber">Phone Number</label>
            <Input
              name="phoneNumber"
              type="text"
              onChange={(e) => handleChange(e)}
              required={true}
            />
          </div>
          <div className="p-field ">
            <label htmlFor="gender">Gender</label>
          </div>
          <div className="p-field-radiobutton">
            <Radio
              value="Male"
              name="gender"
              onChange={(e) => handleChange(e)}
              checked={formData.gender === "Male"}
            />
            <label htmlFor="gen1">Male</label>
          </div>
          <div className="p-field-radiobutton">
            <Radio
              value="Female"
              name="gender"
              onChange={(e) => handleChange(e)}
              checked={formData.gender === "Female"}
            />
            <label htmlFor="gen2">Female</label>
          </div>
          <div className="p-field ">
            <label htmlFor="dob">Date of Birth</label>
            <DatePicker
              name="date"
              format="DD-MM-YYYY"
              onChange={handleDateChange}
            ></DatePicker>
          </div>
        </div>

        <br></br>
        <div style={{ display: "flex", justifyContent: "center" }}>
          <Button type="primary" onClick={handleSubmit}>
            Register
          </Button>
        </div>
      </form>

      <div style={{ display: "flex", justifyContent: "center" }}>
        <Button type="link" onClick={handleRedirectToLogin}>
          Login
        </Button>
      </div>
    </div>
  );
};

export default RegisterComponent;
