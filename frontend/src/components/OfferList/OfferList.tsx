import React from "react";
import { Button, Table, Tag } from "antd";
import { useSelector } from "react-redux";
import { UserState } from "../../reducer/user-reducer";
import { IRootState } from "../../store";

const OfferListComponent = (props: any) => {
  const userState = useSelector<IRootState, UserState>((state) => state.userReducer);
  const data = [
    {
      id: "1",
      name: "Wayne",
      status: "pending",
    },
    {
      id: "2",
      name: "Terrence",
      status: "pending",
    },
    {
      id: "3",
      name: "Damion",
      status: "expired",
    },
  ];
  return (
    <div
      style={{
        width: "100%",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
      }}
    >
      <div style={{ fontSize: "24px", fontWeight: 600, marginTop: "20px", marginBottom: "12px" }}>
        Tutee Offers
      </div>

      <OfferTable data={data} />
    </div>
  );
};

type OfferTableProps = {
  data: {
    name: string;
    status: string;
  }[];
};

const AcceptOffer = (props: any) => {


}

const OfferTable = (props: OfferTableProps) => {
  const data = props.data;
  const columns = [
    {
      title: "Name",
      dataIndex: "name",
      key: "name",
      render: (text: string) => (
        <div style={{ maxWidth: "100px" }}>
          {" "}
          <span>{text}</span>{" "}
        </div>
      ),
    },
    {
      title: "Status",
      key: "status",
      dataIndex: "status",
      render: (status: string) => {
        const color = status === "pending" ? "orange" : "default";
        return <Tag color={color}>{status}</Tag>;
      },
    },
    {
      title: "Action",
      key: "action",
      render: (text: any, record: any) => (
        <>
          <Button type="primary" size="small" style={{ marginRight: "4px" }}>
            <span> Accept </span>
          </Button>
          <Button danger size="small">
            <span> Reject </span>
          </Button>
        </>
      ),
    },

    {
      title: "Details",
      // key: "detail"
    }

  ];
  return (
    <Table columns={columns} dataSource={data} size={"small"} rowKey="id" />
  );
};

export default OfferListComponent;
