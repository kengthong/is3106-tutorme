import {Badge, Menu} from 'antd';
import { AppstoreOutlined, MailOutlined, SettingOutlined } from '@ant-design/icons';
import React, {useEffect, useState} from 'react';
import { useHistory, useLocation } from 'react-router-dom';

const { SubMenu } = Menu;

const DashboardMenu = (props: any) => {
    const {params} = props;
    const history = useHistory();

    const handleClick = (val: any) => {
        console.log('val =', val)
        history.push('/dashboard?type=' + val.keyPath[1] + "&status="+ val.keyPath[0]);
    }

    return (
        <Menu
            onClick={handleClick}
            style={{ width: 256 }}
            className="border-e8"
            defaultSelectedKeys={['1']}
            selectedKeys={[params && params.type || "offers", params && params.status || "all"]}
            defaultOpenKeys={['offers']}
            mode="inline"
        >
            <SubMenu
                key="offers"
                title={<span className="fs-18" style={{color: 'black', fontWeight: 300, opacity: 0.85}}>Offers</span>}
            >
                <Menu.Item key="all">
                    <span style={{paddingLeft: '16px'}}>All Offers</span>
                </Menu.Item>
                <Menu.Item key="accepted">
                    <Badge status="success" /> Accepted
                </Menu.Item>
                <Menu.Item key="pending">
                    <Badge status="warning" /> Pending
                </Menu.Item>
                <Menu.Item key="rejected">
                    <Badge status="error" /> Rejected
                </Menu.Item>
                <Menu.Item key="withdrawn">
                    <Badge status="default" /> Withdrawn
                </Menu.Item>
            </SubMenu>
        </Menu>
    )
}

export default DashboardMenu;