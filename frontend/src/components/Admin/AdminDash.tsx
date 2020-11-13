import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { Input, Button, Card, Tabs, Statistic, Timeline, Calendar } from 'antd'
import Dashboard from "../../Staff/components/StaffView/Dashboard";

export const AdminDash = () => {

    
    const { TabPane } = Tabs;

    return (

        <div style={{ height: '100%', minHeight: "1080px" }}>
            <br />

            <div className={"col-lg-12"}>
                <Tabs defaultActiveKey="1" tabPosition="left">

                    <TabPane tab="Overview" key="1">
                        <Dashboard/>
                    </TabPane>

                    <TabPane tab="Tutee" key="2">
                        <h3>
                            Tutee
                        </h3>
                    </TabPane>

                    <TabPane tab="Tutor" key="3">
                        <h3>
                            Tutor
                        </h3>
                    </TabPane>

                    <TabPane tab="Staff" key="4">
                        <h3>
                            Staff
                        </h3>
                    </TabPane>
                </Tabs>
            </div>

            <br />
        </div>

    )

}

export default AdminDash;