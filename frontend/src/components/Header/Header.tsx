import React from "react";
import logo from "../../assets/logo.jpg";
import { UserService } from "../../services/User";
import { UserState } from "../../reducer/user-reducer";
import { useSelector } from "react-redux";
import { IRootState } from "../../store";
import { Avatar, Popover, Row } from "antd";
import { useHistory } from "react-router-dom";

const Header = () => {
    const userState = useSelector<IRootState, UserState>((state) => state.userReducer);
    const history = useHistory();
    const handleLogout = () => {
        UserService.logout();
        history.push("/");
    }

    const handleProfileClick = () => {

    }

    return (
        <div className="navbar navbar-inverse navbar-fixed-top" style={{ zIndex: 0 }}>
            <div className="container">
                <div className="col-lg-2 col-sm-2">
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
                    className="col-lg-7 col-sm-8"
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
                            <a href="/feedback">Feedback</a>
                        </li>

                        <li>
                            <a href="/contact-us">Contact us</a>
                        </li>

                        {!userState.isAuthenticated ?
                            <li>
                                <a href="/registration">Register</a>
                            </li>
                            :

                            null

                        }
                        {userState.isAuthenticated ?
                            <li>
                                <a href="/chat">Chat</a>
                            </li>
                            :
                            null
                        }
                    </ul>
                </div>

                <div
                    className="col-lg-3 col-sm-2 flex-row align-center"
                    style={{ paddingTop: "20px" }}
                >
                    {userState.isAuthenticated && userState.currentUser ?
                        <Popover placement="bottomRight"
                            content={ProfileContent(userState.currentUser.personEnum, userState, handleLogout)}
                            trigger="click" className='clickable'>
                            <div className='flex-row align-center justify-center border-e8'
                                style={{ minWidth: '100px', padding: '8px 16px', borderRadius: '4px' }}
                                onClick={handleProfileClick}
                            >
                                <div>
                                    <Avatar style={{ backgroundColor: '#C2175B' }}>
                                        {userState.currentUser?.firstName.substring(0, 1).toUpperCase()}
                                    </Avatar>
                                </div>

                                <div style={{ paddingLeft: '8px' }}>
                                    {userState.currentUser?.firstName}
                                </div>
                            </div>
                        </Popover>

                        :
                        <div>
                            <div>
                                <ul className="nav navbar-nav" style={{ display: 'flex', flexDirection: 'row' }}>
                                    <li className="myProfileButton">
                                        <a href="/login">Sign In</a>
                                    </li>
                                    <li style={{ color: 'white' }}>
                                        b
                                    </li>
                                    <li className="myProfileButton">
                                        <a href="/staff/login">Staff Sign In</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    }
                    {/*<ul className="nav navbar-nav myProfileButton">*/}
                    {/*  {userState.isAuthenticated ?*/}
                    {/*    <li>*/}
                    {/*      <a onClick={() => handleLogout()}>Sign Out</a>*/}
                    {/*    </li>*/}
                    {/*    : <li>*/}
                    {/*      <a href="/login">Sign In</a>*/}
                    {/*    </li>*/}
                    {/*  }*/}
                    {/*</ul>*/}

                </div>

            </div>
        </div>
    );
};

const ProfileContent = (personEnum: string, userState: UserState, handleLogout: () => void) => {
    return (
        <div style={{ width: '150px' }}>
            {personEnum === 'TUTOR' ?
                <>
                    <div className='clickable highlightable'>
                        <a className='selection w-100' href={'/tutor-profile'}>
                            My Profile
                        </a>
                    </div>
                    <div className='clickable highlightable'>
                        <a className='selection w-100' href="/tutor/create-new-listing">
                            New Listing
                        </a>
                    </div>
                    <div className='clickable highlightable'>
                        <a className='selection w-100' href="/dashboard">
                            Dashboard
                        </a>
                    </div>
                </>
                :
                <div className='clickable highlightable'>
                    <a className='selection w-100' href="/dashboard">
                        Dashboard
                    </a>
                </div>
            }
            <div className='clickable highlightable'>
                <a className='selection w-100'
                    href={`/${personEnum === 'TUTEE' ? 'tutee/settings/personal-details' : 'tutor/settings/personal-details'}`}>
                    Settings
                </a>
            </div>

            <div className='clickable highlightable'>
                <div className='selection w-100' onClick={handleLogout}>
                    <a>Log Out</a>
                </div>
            </div>
        </div>
    )
};

export default Header;
