import React from "react";
import Header from "../../../components/Header/Header";
import Dashboard from "../../../components/Dashboard";

const OffersPage = () => {
    return (
        <div>
            <Header />
            <div className='flex-row w-100'>
                <Dashboard />
            </div>
        </div>
    );
};

export default OffersPage;
