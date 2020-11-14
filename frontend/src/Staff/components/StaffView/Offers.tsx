import React, { useEffect, useState } from "react";
import { Button, Table, Tag } from "antd";
import moment from "antd/node_modules/moment";
import { JobListingService } from "../../../services/JobListing";
import { useHistory } from "react-router-dom";
import { StaffService } from "../../../services/Staff";

const OfferListingTable = () => {
	const [tableData, setTableData] = useState([]);
	const history = useHistory();
	const getAllOffers = async () => {
		const data = await StaffService.getOfferList();
		setTableData(data);
	};
	useEffect(() => {
		getAllOffers();
	}, []);

	const viewJobListing = (id: number) => {
		history.push("/job?id=" + id);
	};

	const _columns = [
		{
			title: "Offer Status",
			dataIndex: "offerStatus",
			render: (offerStatus: string) => {
				const color =
					offerStatus === "ACCEPTED"
						? "success"
						: offerStatus === "REJECTED"
						? "error"
						: offerStatus === "WITHDRAW"
						? "default"
						: "warning";
				return (
					<span>
						<Tag color={color}>{offerStatus}</Tag>
					</span>
				);
			},
		},
		{
			title: "ID",
			dataIndex: "offerId",
		},
		{
			title: "Subject",
			dataIndex: "chosenSubject",
			render: (subject: any) => {
				return <span>{subject.subjectName}</span>;
			},
		},
		{
			title: "Created Date",
			dataIndex: "createdDate",
			render: (date: string) => {
				return <span>{moment(date.split("[")[0]).format("DD-MM-YYYY")}</span>;
			},
		},
		{
			title: "Tutor",
			dataIndex: "jobListing",
			render: (record: any) => {
				console.log('record =', record)
				const lastName = record && record.tutor && record.tutor.lastName || "";
				const firstName = record && record.tutor && record.tutor.firstName || "";
				return <span> {firstName + " " + lastName}</span>;
			},
		},
		{
			title: "Tutee",
			dataIndex: "tutee",
			render: (record: any) => {
				const firstName = record && record.firstName || "";
				const lastName = record && record.lastName || "";
				return <span> {firstName + " " + lastName}</span>;
			},
		},
		{
			title: "Hours/Session",
			dataIndex: "numHoursPerSession",
			render: (numHoursPerSession: number) => {
				return <span>{numHoursPerSession}</span>;
			},
		},
		{
			title: "Additional Note",
			dataIndex: "additionalNote",
			render: (notes: string) => {
				return <span>{notes}</span>;
			},
		},
		{
			title: "No. of Sessions",
			dataIndex: "numSessions",
		},
		{
			title: "Offered Rate ($)",
			dataIndex: "offeredRate",
			render: (rate: number) => {
				return <span>{rate} /hr</span>;
			},
		},
		{
			title: "Rating",
			dataIndex: "rating",
			render: (rating: any) => {
				return <span>{rating ? rating.ratingValue : ""}</span>;
			},
		},
		{
			title: "Action",
			render: (record: any) => (
				<div className="flex-row justify-center">
					<Button
						size="small"
						className="fs-12 bold"
						onClick={() => viewJobListing(record.jobListing.jobListingId)}
						type="primary"
					>
						View JobListing
					</Button>
				</div>
			),
		},
	];

	if (tableData.length === 0) {
		return (
			<div style={{ marginTop: "20px", fontSize: "16px" }}>Loading...</div>
		);
	}

	console.log('test =', tableData)
	// return (<div> in development</div>)
	return (
		<Table
			columns={_columns}
			dataSource={tableData}
			scroll={{ x: true }}
			rowKey="offerId"
		/>
	);
};

export default OfferListingTable;
