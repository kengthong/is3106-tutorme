import {Badge, Menu} from 'antd';
import React from 'react';
import {useHistory} from 'react-router-dom';

const { SubMenu } = Menu;

const DashboardMenu = (props: any) => {
    const {params, currentUser} = props;
    const history = useHistory();

    const handleClick = (val: any) => {
        const keys = val.key.split("-")
        history.push('/dashboard?type=' + keys[0] + "&status="+ keys[1]);
    }

    const activeKey = params && params.status && params.type && params.type + "-" + params.status || "offers-all";
    return (
        <Menu
            onClick={handleClick}
            style={{ width: 256 }}
            className="border-e8"
            defaultSelectedKeys={['1']}
            selectedKeys={[activeKey]}
            defaultOpenKeys={['offers', 'jobListings']}
            mode="inline"
        >
            <SubMenu
                key="offers"
                title={<span className="fs-18" style={{color: 'black', fontWeight: 300, opacity: 0.85}}>Offers</span>}
            >
                <Menu.Item key="offers-all">
                    <span style={{paddingLeft: '16px'}}>All Offers</span>
                </Menu.Item>
                <Menu.Item key="offers-accepted">
                    <Badge status="success" /> Accepted
                </Menu.Item>
                <Menu.Item key="offers-pending">
                    <Badge status="warning" /> Pending
                </Menu.Item>
                <Menu.Item key="offers-rejected">
                    <Badge status="error" /> Rejected
                </Menu.Item>
                <Menu.Item key="offers-withdrawn">
                    <Badge status="default" /> Withdrawn
                </Menu.Item>
            </SubMenu>

            {currentUser && currentUser.personEnum == "TUTOR"?
                <SubMenu
                    key="jobListings"
                    title={<span className="fs-18" style={{color: 'black', fontWeight: 300, opacity: 0.85}}>Job Listings</span>}
                >
                    <Menu.Item key="jobListings-all">
                        <span style={{paddingLeft: '16px'}}>All Job Listings</span>
                    </Menu.Item>
                </SubMenu>
                :
                null
            }
        </Menu>
    )
}

export default DashboardMenu;