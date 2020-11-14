import React, { useEffect, useState } from "react";
import { Button, message, Table, Tag } from "antd";
import moment from "antd/node_modules/moment";
import { StaffService } from "../../../services/Staff";

const TuteeTable = () => {
	const [tableData, setTableData] = useState([]);
	const getAllTutees = async () => {
		const data = await StaffService.getAllTutees();
		setTableData(data);
	};
	useEffect(() => {
		getAllTutees();
	}, []);
	const updateTuteeDetails = async (id: number, action: string) => {
		let processed;
		switch (action) {
			case "ban":
				processed = await StaffService.banUser(id);
				break;

			case "unBan":
				processed = await StaffService.unBanUser(id);
		}

		if (processed) {
			getAllTutees();
			message.success("Successfully updated user");
			return true;
		} else {
			message.error("Unable to updated user");
			return false;
		}
	};

	const banUser = (id: number) => {
		updateTuteeDetails(id, "ban");
	};

	const unBanUser = (id: number) => {
		updateTuteeDetails(id, "unBan");
	};

	const _columns = [
		{
			title: "Active Status",
			dataIndex: "activeStatus",
			render: (activeStatus: boolean) => {
				return (
					<span>
						{activeStatus ? (
							<Tag color="success">Active</Tag>
						) : (
							<Tag color="error">Inactive</Tag>
						)}
					</span>
				);
			},
		},
		{
			title: "ID",
			dataIndex: "personId",
		},
		{
			title: "First Name",
			dataIndex: "firstName",
			sorter: (a: tuteeDataType, b: tuteeDataType) => {
				if (a.firstName < b.firstName) {
					return -1;
				}
				if (a.firstName > b.firstName) {
					return 1;
				}
				return 0;
			},
		},
		{
			title: "Last Name",
			dataIndex: "lastName",
		},
		{
			title: "CreatedDate",
			dataIndex: "createdDate",
			render: (date: string) => {
				const newD = moment(date.split("[")[0]);
				return <span>{newD.format("DD-MM-YYYY")}</span>;
			},
		},
		{
			title: "DOB",
			dataIndex: "dob",
			render: (date: string) => {
				const newD = moment(date.split("[")[0]);
				return <span>{newD.format("DD-MM-YYYY")}</span>;
			},
		},
		{
			title: "Email",
			dataIndex: "email",
		},
		{
			title: "Gender",
			dataIndex: "gender",
		},
		{
			title: "HP",
			dataIndex: "mobileNum",
		},
		{
			title: "Action",
			render: (record: tuteeDataType) => (
				<div className="flex-row justify-center">
					{record.activeStatus ? (
						<Button
							size="small"
							className="fs-12 bold"
							onClick={() => banUser(record.personId)}
							type="primary"
							danger
						>
							DEACTIVATE
						</Button>
					) : (
						<Button
							size="small"
							className="fs-12 bold"
							onClick={() => unBanUser(record.personId)}
							type="default"
						>
							REACTIVATE
						</Button>
					)}
				</div>
			),
		},
	];

	if (tableData.length === 0) {
		return (
			<div style={{ marginTop: "20px", fontSize: "16px" }}>Loading...</div>
		);
	}
	return (
		<Table
			columns={_columns}
			dataSource={tableData}
			scroll={{ x: true }}
			rowKey="personId"
		/>
	);
};

export default TuteeTable;
