import React from 'react';
import { Button, Table, Tag } from "antd";
import { useSelector } from "react-redux";
import { UserState } from "../../reducer/user-reducer";
import { IRootState } from "../../store";
import moment from "antd/node_modules/moment";

const JobListingsComponent = (props: any) => {
    const {data, viewJobListing} = props;

    const columns = [
        {
            title: 'Active Status',
            dataIndex: 'activeStatus',
            render: (activeStatus: boolean) => {
                return (
                    <span>
                    {activeStatus?
                        <Tag color="success">Active</Tag>
                        :
                        <Tag color="error">Inactive</Tag>
                    }
                </span>
                )
            }
        },
        {
            title: 'ID',
            dataIndex: 'jobListingId',
        },
        {
            title: 'Created Date',
            dataIndex: 'createdDate',
            render: (date: string) => {
                return (<span>{moment(date.split('[')[0]).format("DD-MM-YYYY")}</span>)
            }
        },
        {
            title: 'Rates ($)',
            dataIndex: 'hourlyRates',
            render: (rates: number) => {
                return (<span>{rates} /hr</span>)
            }
        },
        {
            title: 'No. of Offers',
            dataIndex: 'numOffers',
        },
        {
            title: 'No. of Subjects',
            dataIndex: 'numSubjects',
        },
        {
            title: 'Review Count',
            dataIndex: 'reviewCount',
        },
        {
            title: 'Average Rating',
            dataIndex: 'reviewScore',
        },
        {
            title: 'Subjects',
            dataIndex: 'subjects',
            render: (record: subjectResponseType) => {
                return (<span>{record.length >0? record[0].subjectName : null}</span>)
            }
        },
        {
            title: 'Action',
            render: (record: jobListingType) => (
                <>
                    <div className="flex-row justify-center">
                        <Button size="small" className="fs-12 bold" onClick={() => viewJobListing(record.jobListingId)} type="primary">View</Button>
                        {
                            record.activeStatus?
                                <Button danger size="small" onClick={() => props.deactivateJobListing(record.jobListingId)}>
                                    <span> Deactivate </span>
                                </Button>
                                :
                                <Button type="default" size="small" onClick={() => props.activateJobListing(record.jobListingId)}>
                                    <span> Reactivate </span>
                                </Button>
                        }
                    </div>

                </>
            ),
        }
    ];

    console.log('table d =', data)
    return (
        <Table columns={columns} dataSource={data} rowKey="offerId" />
    );
};

export default JobListingsComponent;