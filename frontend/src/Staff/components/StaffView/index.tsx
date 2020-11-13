import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { Input, Button, Card, Tabs, Statistic, Timeline, Calendar } from 'antd'
import Dashboard from "./Dashboard";
import TutorTable from "./Tutor";
import TuteeTable from "./Tutee";
import JobListingsTable from "./JobListings";
import OfferListingTable from "./Offers";
import { UserService } from "../../../services/User";


export const AdminDash = () => {

    const history = useHistory();

    const handleLogout = () => {
        UserService.logout();
        history.push("/");
    }

    const { TabPane } = Tabs;

    return (

        <div style={{ height: '100%', minHeight: "1080px" }}>
            <br />

            <div className={"col-lg-12"}>
                <Tabs defaultActiveKey="1" tabPosition="left">

                    <TabPane tab="Overview" key="1">
                        <Dashboard />
                    </TabPane>

                    <TabPane tab="Tutee" key="2">
                        <h3>
                            Tutee
                        </h3>
                        <div className="margin-top-btm-12">
                            <TuteeTable />
                        </div>
                    </TabPane>

                    <TabPane tab="Tutor" key="3">
                        <h3>
                            Tutor List
                        </h3>
                        <div className="margin-top-btm-12">
                            <TutorTable />
                        </div>

                    </TabPane>

                    <TabPane tab="Staff" key="4">
                        <h3>
                            Staff

                            CRUD staff account

                        </h3>
                    </TabPane>

                    <TabPane tab="Job Listing" key="5">
                        <h3>
                            Job Listing
                        </h3>
                        <div className="margin-top-btm-12">
                            <JobListingsTable />
                        </div>
                    </TabPane>

                    <TabPane tab="Offer" key="6">
                        <h3>
                            Offer
                        </h3>
                        <div className="margin-top-btm-12">
                            <OfferListingTable />
                        </div>
                    </TabPane>

                    <TabPane tab="Chat" key="7">
                        <h3>
                            Chat
                        </h3>
                    </TabPane>

                    <TabPane tab="Logout" key="8" >
                        <h3>
                            Confirm Logout?
                        </h3>

                        <br />

                        <Button danger onClick={handleLogout}>
                            Logout
                        </Button>
                    </TabPane>
                </Tabs>
            </div>

            <br />
        </div>

    )

}

export default AdminDash;