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
  };

  //To be implemented
  const handleSubmit = (e: any) => {
    //Should call on API
    //Skeleton
    console.log("Submitted");
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
