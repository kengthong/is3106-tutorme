import React from 'react';
import { Button } from "antd";

export const Footer = () => {
    return (
        <div>
            <div className="footer-area">
                <div className="container">
                    <div className="flex-row justify-space-around">
                        <div className="footer_bx links_txt">
                            <ul className="footer_list">
                                <li><a href="/company-information">Company information</a></li>
                                <li><a href="/about-us">About us</a></li>
                                <li><a href="/FAQ">FAQs</a></li>
                                <li><a href="#">Contact Support</a></li>
                                <li><a href="#">Report Abuse</a></li>
                            </ul>
                        </div>
                        <div className="flex-col align-center">
                            <span style={{ fontSize: '40px', fontWeight: 300 }}>
                                We're here to help
                            </span>
                            <br />
                            <span>
                                support@tutorme.com
                            </span>
                            <br />
                            <Button>
                                Contact Us
                            </Button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}