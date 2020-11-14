import React from "react";
import {Avatar, Col, Rate, Row} from "antd";
import {UserOutlined} from '@ant-design/icons';

type basicDetailsProp = {
    user?: tutorDataType,
}
const BasicDetails = (props: basicDetailsProp) => {
    const {user} = props;
    const value = 5;
    if (!user) {
        return (
            <div>
                Please log in
            </div>
        )
    }
    return (
        <div>
            <Row style={{margin: "32px 0"}} gutter={10}>
                <Col span={6}>
                    {user.profileImage ?
                        <img
                            alt="user profile pic"
                            src={user?.profileImage}
                            width="120px"
                            height="120px"
                        />
                        :
                        <div className='flex-row justify-center align-center'>
                            <Avatar style={{backgroundColor: "#cccccc63"}} shape="square" size={120}
                                    icon={<UserOutlined/>}/>
                        </div>
                    }
                </Col>
                <Col span={18}>
                    <Row>
                        <Row
                            style={{fontSize: "18px", fontWeight: 600, width: "100%"}}
                            justify="space-between"
                        >
                            <span>{user?.firstName} {user?.lastName}</span>
                        </Row>

                        <Row>
                            {user?.highestQualification}
                        </Row>
                    </Row>

                    <div>
                        <Rate value={value} disabled/> 10 Reviews
                    </div>
                </Col>
            </Row>

            <div style={{fontSize: "16px", fontWeight: 600}}>About me</div>

            <Row>
                {user?.profileDesc}
            </Row>
        </div>
    );
};

export default BasicDetails;
