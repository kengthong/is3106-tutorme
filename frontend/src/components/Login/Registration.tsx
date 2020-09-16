<<<<<<< HEAD
import React, { useEffect, useState, Component } from "react";
import { Button } from "primereact/button";
import { Calendar } from "primereact/calendar";
import { InputText } from "primereact/inputtext";
import { Password } from "primereact/password";
import { RadioButton } from "primereact/radiobutton";

const Registration = () => {
  const [firstname, setfirstname] = useState("");
  const [lastname, setlastname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [password2, setPassword2] = useState("");
  const [gender, setGender] = useState("Male");
  const [dob, setdob] = useState(new Date());
  const [phone, setPhone] = useState("");
  const [submitted, setSubmitted] = useState(false);

  //Need help with this validation too
  function validateForm() {
    return email.length > 0 && password.length > 0;
  }
  const verifyPassword = (e: any) => {
    return password == password2;
=======
import React, {useState} from "react";
import {Button} from "primereact/button";
import {Calendar} from "primereact/calendar";
import {InputText} from "primereact/inputtext";
import {Password} from "primereact/password";
import {RadioButton} from "primereact/radiobutton";
import {LOGIN_URL} from "../../config/constants";
import {useHistory} from "react-router-dom";
import logo from "../../assets/logo.jpg";

const RegisterComponent = () => {
  const [formData, setFormData] = useState({
    confirmPassword: "3",
    date: null,
    email: "5",
    firstName: "2",
    gender: "Male",
    lastName: "2",
    name: "",
    password: "43",
    phoneNumber: "3",
  });

  const history = useHistory();

  const verifyPassword = (password1: string, password2: string) => {
    return password1==password2;
>>>>>>> 97efe2f032568363c535e853747b10fcbf772217
  };

  //To be implemented
  const handleSubmit = (e: any) => {
    //Should call on API
    //Skeleton
    console.log("Submitted");
<<<<<<< HEAD
  };

  const handlefirstnameChange = (e: any) => {
    const value = e && e.target && e.target.value ? e.target.value : "";
    setfirstname(value);
  };

  const handlelastnameChange = (e: any) => {
    const value = e && e.target && e.target.value ? e.target.value : "";
    setlastname(value);
  };

  const handleEmailChange = (e: any) => {
    const value = e && e.target && e.target.value ? e.target.value : "";
    setEmail(value);
  };

  const handlePasswordChange = (e: any) => {
    const value = e && e.target && e.target.value ? e.target.value : "";
    setPassword(value);
  };

  //Need some updates to this
  const handleRetypePasswordChange = (e: any) => {
    const value = e && e.target && e.target.value ? e.target.value : "";
    setPassword2(value);
    verifyPassword(value);
  };

  const handlePhoneNumChange = (e: any) => {
    const value = e && e.target && e.target.value ? e.target.value : "";
    setPhone(value);
  };

  const handleGenderChange = (e: any) => {
    const value = e && e.target && e.target.value ? e.target.value : "";
    setGender(value);
  };

  const handleDateChange = (e: any) => {
    const value = e && e.target && e.target.value ? e.target.value : "";
    setdob(e);
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="p-fluid ">
        <div className="p-field p-col-12 p-md-6">
          <label htmlFor="firstname">First name</label>
          <InputText
            id="firstname"
            type="text"
            keyfilter="alphanum"
            onChange={(e) => handlefirstnameChange(e)}
            required={true}
          />
        </div>
        <div className="p-field p-col-12 p-md-6">
          <label htmlFor="lastname">Last name</label>
          <InputText
            id="lastname"
            type="text"
            keyfilter="alphanum"
            onChange={(e) => handlelastnameChange(e)}
            required={true}
          />
        </div>

        <div className="p-field ">
          <label htmlFor="email">Email</label>
          <InputText
            id="emailinput"
            type="text"
            keyfilter="email"
            onChange={(e) => handleEmailChange(e)}
            required={true}
          />
        </div>
        <div className="p-field ">
          <label htmlFor="password">Password</label>
          <Password
            id="password"
            type="text"
            onChange={(e) => handlePasswordChange(e)}
            required={true}
          />
        </div>
        <div className="p-field ">
          <label htmlFor="password">Retype Password</label>
          <Password
            id="password"
            type="text"
            onChange={(e) => handleRetypePasswordChange(e)}
            required={true}
          />
        </div>
        <div className="p-field ">
          <label htmlFor="phonenumber">Phone Number</label>
          <InputText
            id="phone"
            type="text"
            keyfilter="int"
            onChange={(e) => handlePhoneNumChange(e)}
            required={true}
          />
        </div>
        <div className="p-field ">
          <label htmlFor="gender">Gender</label>
        </div>
        <div className="p-field-radiobutton">
          <RadioButton
            value="Male"
            name="gender"
            onChange={(e) => handleGenderChange(e)}
            checked={gender === "Male"}
          />
          <label htmlFor="gen1">Male</label>
        </div>
        <div className="p-field-radiobutton">
          <RadioButton
            value="Female"
            name="gender"
            onChange={(e) => handleGenderChange(e)}
            checked={gender === "Female"}
          />
          <label htmlFor="gen2">Female</label>
        </div>
        <div className="p-field ">
          <label htmlFor="dob">Date of Birth</label>
          <Calendar
            onChange={(e) => handleDateChange({ date: e.value })}
            required={true}
          ></Calendar>
        </div>
      </div>

      <br></br>
      <div className="p-fluid p-formgrid p-grid">
        <Button
          label="Register"
          className="p-button-raised p-pt-4"
          onClick={handleSubmit}
        />
      </div>
    </form>
  );
};

export default Registration;
=======
    console.log('form data=', formData)
  };

  const handleRedirectToLogin = () => history.push(LOGIN_URL);

  const handleChange = (e: any) => {
    console.log(e)
    const name = e && e.target && e.target.name ? e.target.name : '';
    const value = e && e.target && e.target.value ? e.target.value : '';
    setFormData( prevState => ({
      ...prevState,
      [name]: value
    }))

    console.log(formData)
  };

  return (
    <div>
      <img src={logo} style={{ padding: 100 }} />
      <h1
          style={{
            fontSize: "3rem",
            padding: 50,
          }}
      >
        Registration page
      </h1>

      <form>
        <div className="p-fluid ">
          <div className="p-field p-col-12 p-md-6">
            <label htmlFor="firstname">First name</label>
            <InputText
                name='firstName'
                type="text"
                keyfilter="alphanum"
                onChange={(e) => handleChange(e)}
                required={true}
            />
          </div>
          <div className="p-field p-col-12 p-md-6">
            <label htmlFor="lastname">Last name</label>
            <InputText
                name='lastName'
                type="text"
                keyfilter="alphanum"
                onChange={(e) => handleChange(e)}
                required={true}
            />
          </div>

          <div className="p-field ">
            <label htmlFor="email">Email</label>
            <InputText
                name='email'
                type="text"
                keyfilter="email"
                onChange={(e) => handleChange(e)}
                required={true}
            />
          </div>
          <div className="p-field ">
            <label htmlFor="password">Password</label>
            <Password
                name='password'
                type="text"
                onChange={(e) => handleChange(e)}
                required={true}
            />
          </div>
          <div className="p-field ">
            <label htmlFor="password">Retype Password</label>
            <Password
                name='confirmPassword'
                type="text"
                onChange={(e) => handleChange(e)}
                required={true}
            />
          </div>
          <div className="p-field ">
            <label htmlFor="phonenumber">Phone Number</label>
            <InputText
                name='phoneNumber'
                type="text"
                keyfilter="int"
                onChange={(e) => handleChange(e)}
                required={true}
            />
          </div>
          <div className="p-field ">
            <label htmlFor="gender">Gender</label>
          </div>
          <div className="p-field-radiobutton">
            <RadioButton
                value="Male"
                name="gender"
                onChange={(e) => handleChange(e)}
                checked={formData.gender === "Male"}
            />
            <label htmlFor="gen1">Male</label>
          </div>
          <div className="p-field-radiobutton">
            <RadioButton
                value="Female"
                name="gender"
                onChange={(e) => handleChange(e)}
                checked={formData.gender === "Female"}
            />
            <label htmlFor="gen2">Female</label>
          </div>
          <div className="p-field ">
            <label htmlFor="dob">Date of Birth</label>
            <Calendar
                dateFormat="dd/mm/yy"
                name='date'
                onChange={(e) => handleChange(e)}
                required={true}
            ></Calendar>
          </div>
        </div>

        <br></br>
        <div className="p-fluid p-formgrid p-grid">
          <Button
              label="Register"
              className="p-button-raised p-pt-4"
              onClick={handleSubmit}
          />
        </div>
      </form>

      <div style={{display: 'flex', justifyContent: 'center'}}>
        <Button
            label="Login"
            className="p-button-link"
            onClick={handleRedirectToLogin}
        />
      </div>
    </div>
  );
};

export default RegisterComponent;
>>>>>>> 97efe2f032568363c535e853747b10fcbf772217
