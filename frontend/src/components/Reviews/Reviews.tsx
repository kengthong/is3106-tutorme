import React from "react";
import { Col, Rate, Row } from "antd";

type reviewProps = {
    ratings?: offerType[],
    avgRating: number;
    ratingCount?: number;
}
const ReviewsComponent = (props: reviewProps) => {
  const value = 5;
  console.log(props.ratings)
  const ratingData = props.ratings && props.ratings.map(r => {
    return {
        rating: r.rating.ratingValue,
        name: r.tutee.firstName,
        comment: r.rating.comments
    }
  }) || [];
  return (
    <div style={{ marginTop: "50px" }}>
      <div style={{ fontSize: "16px", fontWeight: "bold", padding: "10px 0" }}>
        Ratings & Review
      </div>

      <Row
        justify={"space-between"}
        align={"middle"}
        style={{
          padding: "5px 0 20px 0px",
          borderBottom: "1px solid #e8e8e8",
          borderTop: "1px solid #e8e8e8",
        }}
      >
        <Col span={70}>
          <div style={{ fontSize: "72px", fontWeight: 300, color: "#fada15" }}>
              {props.avgRating}
          </div>
          <div>
            <Rate value={value} disabled /> {props.ratingCount} Reviews
          </div>
        </Col>
        <Col span={30}>
          <RatingBreakdown ratingData={ratingData} />
        </Col>
      </Row>

      <Row>
        <ReviewList ratingData={ratingData} />
      </Row>
    </div>
  );
};

type ratingDataProps = [
  {
    rating: number;
    name: string;
    comment: string;
  }
];

const RatingBreakdown = (props: any) => {
  const data: ratingDataProps = props.ratingData;
  type dataDictType = {
    [key: number]: number;
  };

  const dataDict: dataDictType = {
    1: 0,
    2: 0,
    3: 0,
    4: 0,
    5: 0,
  };

  data.forEach((d) => {
    dataDict[d.rating] += 1;
  });
  const progressBars: any[] = [];
  for (let i = 5; i >= 1; i--) {
    progressBars.push(
      <ProgressBar
        key={i}
        star={i}
        current={dataDict[i] || 0}
        total={data.length}
      />
    );
  }
  return <div>{progressBars}</div>;
};

const ProgressBar = (props: any) => {
  const { current, total, star } = props;
  const totalWidth = 120;
  const width = (current / total) * totalWidth;
  const faintStyle = { fontSize: "12px", paddingLeft: "4px", opacity: 0.7 };
  return (
    <div style={{ margin: "0px 0", display: "flex", alignItems: "center" }}>
      <span style={{ ...faintStyle, paddingRight: "4px", width: "15px" }}>
        {star}
      </span>
      <div
        style={{
          width: `${totalWidth}px`,
          borderRadius: "4px",
          backgroundColor: "#e8e8e8",
          height: "10px",
        }}
      >
        <div
          style={{
            height: "100%",
            width: `${width}px`,
            backgroundColor: "#FADB15",
          }}
        />
      </div>
      <span
        style={
          current === 0
            ? faintStyle
            : {
                fontSize: "12px",
                paddingLeft: "4px",
                color: "#542eff",
                fontWeight: "bold",
              }
        }
      >
        {current}
      </span>
    </div>
  );
};

const ReviewList = (props: any) => {
  const ratingData: ratingDataProps = props.ratingData;

  return (
    <div style={{ width: "100%" }}>
      {ratingData.map((d, i) => {
        return (
          <div
            key={i}
            style={{
              display: "flex",
              flexDirection: "column",
              height: "80px",
              justifyContent: "center",
              borderTop: "1px solid #e8e8e8",
              borderBottom: "1px solid #e8e8e8",
              width: "100%",
            }}
          >
            <div>{d.name}</div>
            <div>{d.comment}</div>
          </div>
        );
      })}
    </div>
  );
};

export default ReviewsComponent;
