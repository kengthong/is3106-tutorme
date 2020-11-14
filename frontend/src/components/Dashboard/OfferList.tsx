import React from "react";
import { Button, Table, Tag } from "antd";
import { useSelector } from "react-redux";
import { UserState } from "../../reducer/user-reducer";
import { IRootState } from "../../store";
import moment from "antd/node_modules/moment";
const OfferListComponent = (props: any) => {
  const {data, userState} = props;

  const columns = [
    {
      title: "Status",
      dataIndex: "offerStatus",
      render: (offerStatus: string) => {
        const color = offerStatus === "ACCEPTED"? "success" : offerStatus === "REJECTED"? "error" : offerStatus === "WITHDRAWN"? "default": "warning";
        return (
            <span>
                <Tag color={color}>{offerStatus}</Tag>
            </span>
        )
      }
    },
    {
      title: 'ID',
      dataIndex: 'offerId',
    },
    {
      title: 'Subject',
      dataIndex: 'chosenSubject',
      render: (subject: any) => {
        return (
            <span>
                    {subject.subjectName}
                </span>
        )
      }
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
      dataIndex: 'jobListing',
      render: (jobListing: any) => {
        const { firstName, lastName } = jobListing && jobListing.tutor || "";
        return (<span> {firstName + " " + lastName}</span>)
      },
    },
    {
      title: 'Hours/Session',
      dataIndex: 'numHoursPerSession',
      render: (numHoursPerSession: number) => {
        return (
            <span>
                {numHoursPerSession}
            </span>
        )
      }
    },
    {
      title: 'Additional Note',
      dataIndex: 'additionalNote',
      render: (notes: string) => {
        return (
            <span>
                    {notes}
                </span>
        )
      }
    },
    {
      title: 'No. of Sessions',
      dataIndex: 'numSessions',
    },
    {
      title: 'Offered Rate ($)',
      dataIndex: 'offeredRate',
      render: (rate: number) => {
        return (<span>{rate} /hr</span>)
      }
    },
    {
      title: 'Rating',
      dataIndex: 'rating',
      render: (rating: any) => {
        return (<span>{rating && rating.ratingValue || ""}</span>)
      }
    },
    {
      title: "Action",
      key: "action",
      render: (record: any) => {
        const status = record && record.offerStatus || "";
        console.log('recor d=', record)
        if (userState.currentUser && userState.currentUser.personEnum === "TUTEE") {
          return (
              <>
                <Button type="primary" size="small" style={{marginRight: "4px"}} onClick={() => props.viewJobListing(record.jobListing.jobListingId)}>
                  <span> View</span>
                </Button>
                <Button danger size="small" disabled={["ACCEPTED", "REJECTED", "WITHDRAWN"].includes(status)}
                onClick={() => props.withdrawOffer(record.offerId)}>
                  <span> Withdraw </span>
                </Button>
              </>
          )
        } else {
          return (
              <>
                <Button type="primary" size="small" style={{marginRight: "4px"}} onClick={() => props.viewJobListing(record.jobListing.jobListingId)}>
                  <span> View</span>
                </Button>
                <Button type="primary" size="small" style={{marginRight: "4px"}} onClick={() => props.acceptOffer(record.offerId)}
                        disabled={["ACCEPTED", "REJECTED", "WITHDRAWN"].includes(status)}>
                  <span> Accept </span>
                </Button>
                <Button danger size="small" onClick={() => props.rejectOffer(record.offerId)}
                        disabled={["ACCEPTED", "REJECTED", "WITHDRAWN"].includes(status)}>
                  <span> Reject </span>
                </Button>
              </>
          )
        }
      }
    },
  ];

  console.log('table d =', data)
  return (
        <Table columns={columns} dataSource={data} rowKey="offerId" />
  );
};

export default OfferListComponent;