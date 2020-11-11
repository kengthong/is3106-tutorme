import React from "react";
import Header from "../../components/Header/Header";
import { Footer } from "../../components/LandingPage/Footer";
import AdminDash from "../components/StaffView";

const StaffHome = () => {
    return (
        <div style={{ height: "100%" }}>
            {/*<Header />*/}
            <div>
                <AdminDash />
            </div>

            {/*<Footer />*/}
        </div>
    );
};

export default StaffHome;
