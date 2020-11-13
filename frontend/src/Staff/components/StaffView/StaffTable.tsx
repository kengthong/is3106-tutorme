import { Table } from 'antd';
import moment from 'antd/node_modules/moment';
import React, { useEffect, useState } from 'react'
import { useHistory } from 'react-router-dom';
import { StaffService } from '../../../services/Staff';



const StaffTable = () => {
    const [tableData, setTableData] = useState<staffDataType[]>([]);
    const history = useHistory();
    const getAllStaff = async () => {
        const data: staffDataType[] = await StaffService.getAllStaff();
        setTableData(data);
    }
    useEffect(() => {
        getAllStaff()
    }, [])


    const _columns = [
        {
            title: 'ID',
            dataIndex: 'personId'
        },
        {
            title: 'Staff',
            dataIndex: 'staffName',

        },
        {
            title: 'E-Mail',
            dataIndex: 'email'
        },
        {
            title: 'Mobile No.',
            dataIndex: 'mobileNum'
        },
        {
            title: 'Created Date',
            dataIndex: 'createdDate',
            render: (date: string) => {
                return (<span>{moment(date.split('[')[0]).format("DD-MM-YYYY")}</span>)
            }
        },
        {
            title: 'Position',
            dataIndex: 'position'

        }

    ]
    if (tableData.length === 0) {
        return <div style={{ marginTop: '20px', fontSize: '16px' }}>Loading...</div>
    }

    return (
        <div>
            <Table
                columns={_columns}
                dataSource={tableData}
                scroll={{ x: true }}
                rowKey="personId"
            />
        </div>
    )
}

export default StaffTable;
