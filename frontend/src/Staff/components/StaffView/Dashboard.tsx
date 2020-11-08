import React from "react";
import {Card, Statistic, Timeline} from "antd";

const Dashboard = () => {
    return (
        <div>
            <div style={{ padding: "5px" }} className={"col-lg-12"}>
                <h3>
                    Overview
                </h3>
                <br />
            </div>

            <div style={{ padding: "5px" }} className={"col-lg-12"}>
                <div className={"col-lg-3"}>
                    <Card title="Tutor growth">
                        <Statistic
                            title="Per Month"
                            value={23}
                            valueStyle={{ color: '#3f8600' }}
                            prefix="+"
                        />
                    </Card>
                </div>

                <div className={"col-lg-3"}>
                    <Card title="Tutee growth">
                        <Statistic
                            title="Per Month"
                            value={64}
                            valueStyle={{ color: '#3f8600' }}
                            prefix="+"
                        />
                    </Card>
                </div>

                <div className={"col-lg-3"}>
                    <Card title="New Job Listings">
                        <Statistic
                            title="Per Month"
                            value={11.28}
                            precision={2}
                            valueStyle={{ color: '#3f8600' }}
                            suffix="%"
                            prefix="+"
                        />
                    </Card>
                </div>

                <div className={"col-lg-3"}>
                    <Card title="Offer Acceptance Rate">
                        <Statistic
                            title="Per Month"
                            value={20.11}
                            precision={2}
                            valueStyle={{ color: '#3f8600' }}
                            suffix="%"
                            prefix="+"
                        />
                    </Card>
                </div>
            </div>

            <div style={{ padding: "5px" }} className={"col-lg-12"}>
                <div className={"col-lg-3"}>
                    <Card title="Active Tutor">
                        <Statistic
                            title="Total"
                            value={281}
                            valueStyle={{ color: '#3f8600' }}
                        />
                    </Card>
                </div>

                <div className={"col-lg-3"}>
                    <Card title="Active Tutee">
                        <Statistic
                            title="Total"
                            value={4223}
                            valueStyle={{ color: '#3f8600' }}
                        />
                    </Card>
                </div>

                <div className={"col-lg-3"}>
                    <Card title="Banned Tutor">
                        <Statistic
                            title="Since Begin"
                            value={12}
                            valueStyle={{ color: '#cf1322' }}
                        />
                    </Card>
                </div>

                <div className={"col-lg-3"}>
                    <Card title="Banned Tutee">
                        <Statistic
                            title="Since July 2020"
                            value={25}
                            valueStyle={{ color: '#cf1322' }}
                        />
                    </Card>
                </div>
            </div>

            <div style={{ padding: "5px" }} className={"col-lg-12"}>
                <div className={"col-lg-6"}>
                    <Card title="2020 FY Key Milestone">
                        <Timeline>
                            <Timeline.Item color="green">Create a services site 2020-02-01</Timeline.Item>
                            <Timeline.Item color="green">Solve initial network problems 2020-03-01</Timeline.Item>
                            <Timeline.Item color="green">Technical testing 2020-03-24</Timeline.Item>
                            <Timeline.Item color="blue">Network problems being solved 2020-05-26</Timeline.Item>
                            <Timeline.Item color="blue">PDPA 2012 Audit 2020-07-26</Timeline.Item>
                            <Timeline.Item color="red">IS27001 Security Audit 2020-09-26</Timeline.Item>
                            <Timeline.Item color="green">Security Patch 2020-10-26</Timeline.Item>
                            <Timeline.Item color="green">IS27001 Security Audit 2020-10-29</Timeline.Item>
                            <Timeline.Item color="red">File Tax Returns 2020-11-29</Timeline.Item>
                        </Timeline>

                        Green = Completed, Blue = In-progress, Red = Incompleted/Failed
                    </Card>
                </div>

                <div className={"col-lg-6"}>
                    <Card title="2021 FY Key Milestone">
                        <Timeline>
                            <Timeline.Item color="red">Security Patch 2021-01-26</Timeline.Item>
                            <Timeline.Item color="red">Purchase Servers 2021-03-16</Timeline.Item>
                            <Timeline.Item color="red">Website Patch 2021-04-09</Timeline.Item>
                            <Timeline.Item color="red">Pro-Account promotion 2021-05-05</Timeline.Item>
                            <Timeline.Item color="red">Annual Performance Meeting 2021-06-30</Timeline.Item>
                            <Timeline.Item color="red">Revamp Design 2021-07-15</Timeline.Item>
                            <Timeline.Item color="red">PDPA 2012 Audit 2021-07-26</Timeline.Item>
                            <Timeline.Item color="red">IS27001 Security Audit 2021-10-29</Timeline.Item>
                            <Timeline.Item color="red">File Tax Returns 2021-11-29</Timeline.Item>
                        </Timeline>

                        Green = Completed, Blue = In-progress, Red = Incompleted/Failed
                    </Card>
                </div>
            </div>
            total jobListings
            new joblistings

            total offers
            new offers
            offer acceptance rate
            offer rejection rate

            total tutor
            new tutor

            total tutee
            new tuteess
        </div>
    )
}

export default Dashboard;