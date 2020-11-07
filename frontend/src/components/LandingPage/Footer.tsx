import React from 'react';
import { Button } from "antd";

export const Footer = () => {
    return (
        <div style={{minHeight: '286px'}}>
            <div className="footer-area">
                <div className="container">
                    <div className="flex-row justify-space-around">
                        <div className="footer_bx links_txt">
                            <ul className="footer_list">
                                <li><a href="/about-us">About us</a></li>
                                <li><a href="/faq">FAQs</a></li>
                                <li><a href="/feedback">Feedback</a></li>
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
                            <Button href="/ContactUs" >
                                Contact Us
                            </Button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}