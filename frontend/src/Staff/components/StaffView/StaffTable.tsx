import { Button, DatePicker, Form, Input, InputNumber, message, Modal, Radio, Select, Table, Tag } from 'antd';
import moment from 'antd/node_modules/moment';
import React, { useEffect, useState } from 'react'
import { useHistory } from 'react-router-dom';
import { StaffService } from '../../../services/Staff';



const StaffTable = () => {
    const [tableData, setTableData] = useState<staffDataType[]>([]);
    const [showModal, setShowModal] = useState(false);
    const [form] = Form.useForm();
    const history = useHistory();
    const getAllStaff = async () => {
        const data: staffDataType[] = await StaffService.getAllStaff();
        setTableData(data);
    }
    useEffect(() => {
        getAllStaff()
    }, [])

    const updateStaffDetails = async (id: number, action: string) => {
        let processed;
        switch (action) {
            case "ban":
                processed = await StaffService.banUser(id)
                break;
            case "unBan":
                processed = await StaffService.unBanUser(id);
        }

        if (processed) {
            getAllStaff();
            message.success("Successfully updated user");
            return true;
        } else {
            message.error("Failed to update user")
            return false;
        }
    }

    const banUser = (id: number) => {
        updateStaffDetails(id, 'ban');
    }

    const unBanUser = (id: number) => {
        updateStaffDetails(id, 'unBan');
    }

    const _columns = [
        {
            title: 'Active Status',
            dataIndex: 'activeStatus',
            render: (activeStatus: boolean) => {
                return (
                    <span>
                        {activeStatus ?
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
            dataIndex: 'personId'
        },
        {
            title: 'First Name',
            dataIndex: 'firstName',

        },
        {
            title: 'Last Name',
            dataIndex: 'lastName',
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
            title: 'Gender',
            dataIndex: 'gender'
        },
        {
            title: 'DOB',
            dataIndex: 'dob'
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
            dataIndex: 'staffPositionEnum'
        },
        {
            title: 'Action',
            render: (record: staffDataType) => (
                <div className="flex-row justify-center">
                    {record.activeStatus ?
                        <Button size="small" className="fs-12 bold" onClick={() => banUser(record.personId)} type="primary" danger>DEACTIVATE</Button>
                        :
                        <Button size="small" className="fs-12 bold" onClick={() => unBanUser(record.personId)} type="default">REACTIVATE</Button>
                    }
                </div>
            ),
        }

    ]
    if (tableData.length === 0) {
        return <div style={{ marginTop: '20px', fontSize: '16px' }}>Loading...</div>
    }

    /*
    * CREATE STAFF
    *
    */
    const onFinish = (fieldsValue: any) => {
        const values = {
            ...fieldsValue,
            accountType: "staff",
            date: moment(fieldsValue.date).format("DD-MM-YYYY")
        }
        console.log("fields value =", values);

    }


    return (
        <div>
            <Table
                columns={_columns}
                dataSource={tableData}
                scroll={{ x: true }}
                rowKey="personId"
            />

            <div>
                <Button type="primary" onClick={() => setShowModal(true)}>
                    Create Staff
                </Button>
            </div>

            <Modal
                title="Create New Staff"
                visible={showModal}
                onOk={() => setShowModal(false)}
                onCancel={() => setShowModal(false)}
                width={800}
                footer={null}
                style={{ display: "flex", flexDirection: "column" }}
            >
                <Form
                    style={{ display: "flex", justifyContent: "space-evenly" }}
                    form={form}
                    onFinish={onFinish}
                    name="createNewStaff"
                >

                    <div>
                        <Form.Item
                            name="firstName"
                            label="First Name"
                            rules={[{
                                required: true,
                                message: "Please input your first name!"
                            }]}
                        >
                            <Input />
                        </Form.Item>

                        <Form.Item
                            name="lastName"
                            label="Last Name"
                            rules={[{
                                required: true,
                                message: "Please input your last name!"
                            }]}
                        >
                            <Input />
                        </Form.Item>

                        <Form.Item
                            name="password"
                            label="Password"
                            rules={[{
                                required: true,
                                message: "Please input your password!"
                            }]}
                        >
                            <Input.Password />
                        </Form.Item>

                        <Form.Item
                            name="email"
                            label="Email"
                            rules={[{
                                required: true,
                                type: "email",
                                message: "Please enter a valid email!"
                            }]}
                        >
                            <Input />
                        </Form.Item>

                        <Form.Item
                            name="phoneNumber"
                            label="Mobile number"
                            rules={[{
                                required: true,
                                message: "Please enter a valid phone number!"
                            }]}
                        >
                            <InputNumber />
                        </Form.Item>

                        <Form.Item
                            name="gender"
                            label="Select your gender"
                            rules={[{
                                required: true,
                                message: "Select an option!"
                            }]}
                        >
                            <Radio value="Male">Male</Radio>
                            <Radio value="Female">Female</Radio>
                        </Form.Item>

                        <Form.Item
                            name="date"
                            label="Date of Birth"
                            rules={[{
                                required: true,
                                message: "Select Date"
                            }]}
                        >
                            <DatePicker format="DD-MM-YYYY" />
                        </Form.Item>

                        <Form.Item>
                            <Button type="primary" htmlType="submit">
                                Submit
                            </Button>
                        </Form.Item>
                    </div>
                </Form>
            </Modal>



        </div >
    )
}




export default StaffTable;
