import React from 'react';
import {Space, Table} from "antd";

type tableProps = {
    type: string;
}

const columns = [
    {
        title: 'Name',
        dataIndex: 'name',
    },
    {
        title: 'Age',
        dataIndex: 'age',
        sorter: (a: { age: number; }, b: { age: number; }) => a.age - b.age,
    },
    {
        title: 'Address',
        dataIndex: 'address',
        filters: [
            {
                text: 'London',
                value: 'London',
            },
            {
                text: 'New York',
                value: 'New York',
            },
        ],
        onFilter: (value: any, record: { address: any}) => record.address.indexOf(value) === 0,
    },
    {
        title: 'Action',
        key: 'action',
        sorter: true,
        render: () => (
            <Space size="middle">
                <a>Delete</a>
            </Space>
        ),
    },
];

const data:any[] = [];
for (let i = 1; i <= 10; i++) {
    data.push({
        key: i,
        name: 'John Brown',
        age: `${i}2`,
        address: `New York No. ${i} Lake Park`,
        description: `My name is John Brown, I am ${i}2 years old, living in New York No. ${i} Lake Park.`,
    });
}
const expandable = { expandedRowRender: (record: { description: string; }) => <p>{record.description}</p> };

const TutorTable = (props: tableProps) => {
    return (
        <Table
            columns={columns}
            dataSource={data}
            scroll={scroll}
        />
    )
}

export default TutorTable;