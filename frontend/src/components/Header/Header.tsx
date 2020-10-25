import React, { ChangeEvent, useEffect, useState } from "react";
import logo from "../../assets/logo.jpg";
import { UserService } from "../../services/User";
import { UserState } from "../../reducer/user-reducer";



const Header = () => {
  const [currUser, setCurrUser] = useState(""); //Need to Update this
  const [isTutor, setisTutor] = useState(false); //Need to Update this

  const handleLogout = () => {
    UserService.logout();
  }

  return (
    <div className="navbar navbar-inverse navbar-fixed-top">
      <div className="container">
        <div className="col-lg-2 col-sm-1">
          <div className="navbar-header">
            <button
              type="button"
              className="navbar-toggle"
              data-toggle="collapse"
              data-target="#bs-example-navbar-collapse-1"
            >
              <span className="sr-only">Toggle navigation</span>{" "}
              <span className="icon-bar"></span>{" "}
              <span className="icon-bar"></span>{" "}
              <span className="icon-bar"></span>
            </button>
            <a className="navbar-brand" href="/">
              <img
                src={logo}
                alt="logo"
                style={{ width: "70px", height: "70px" }}
              />
            </a>
          </div>
        </div>
        <div
          className="col-lg-6 col-sm-8"
          style={{ paddingTop: "20px" }}
        >
          <ul className="nav navbar-nav">
            <li>
              <a href="/">Home</a>
            </li>
            <li>
              <a href="/search">Search</a>
            </li>
            <li>
              <a href="/FAQ">FAQs</a>
            </li>

            <li>
              <a href="contact_us.html">Contact us</a>
            </li>

            {currUser == "" ?
              <li>
                <a href="/registration">Register</a>
              </li>
              :

              isTutor == false ?
                <li>
                  <a href="/tuteeProfile">My Profile</a>
                </li>
                :
                <li>
                  <a href="/tutorMenu">Menu</a>
                </li>

            }



          </ul>
        </div>

        <div
          className="col-lg-4 col-sm-3"
          style={{ paddingTop: "20px" }}
        >

          <ul className="nav navbar-nav myProfileButton">
            {currUser != "" ?
              <li>
                <a onClick={() => handleLogout()}>Sign Out</a>
              </li>
              : <li>
                <a href="/login">Sign In</a>
              </li>
            }
          </ul>

        </div>

      </div>
    </div>
  );
};

export default Header;
