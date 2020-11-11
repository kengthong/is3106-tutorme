import React, {useEffect, useState} from 'react';
import {Button, Table, Tag} from "antd";
import moment from 'antd/node_modules/moment';
import {JobListingService} from "../../../services/JobListing";
import { useHistory } from 'react-router-dom';

const JobListingsTable = () => {
    const [tableData, setTableData] = useState<jobListingType[]>([])
    const history = useHistory();
    const getAllJobListing = async () => {
        const data: jobListingType[] = await JobListingService.getJobListingListWithParams({});
        setTableData(data)
    }
    useEffect(() => {
        getAllJobListing()
    },[])

    const viewJobListing = (id?: number) => {
        if(id) history.push('/job?id=' + id)
    }
    const _columns = [
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
            title: 'Tutor',
            dataIndex: 'tutor',
            render: (record: any) => {
              return (<span> {record.firstName + " " + record.lastName}</span>)
            },
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
            title: 'Email',
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
                <div className="flex-row justify-center">
                    <Button size="small" className="fs-12 bold" onClick={() => viewJobListing(record.jobListingId)} type="primary">View</Button>
                </div>
            ),
        }
    ]

    if(tableData.length === 0) {
        return <div style={{marginTop: '20px', fontSize: '16px'}}>Loading...</div>
    }
    return (
        <Table
            columns={_columns}
            dataSource={tableData}
            scroll={{x: true}}
            rowKey="jobListingId"
        />
    )
}

export default JobListingsTable;