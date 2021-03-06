import React from "react";
import {Menu} from "antd";
import {useHistory, useLocation} from "react-router-dom";
import {TUTEE_SETTING_URL} from "../../config/constants";

const TuteeSettingsMenu = () => {
    const history = useHistory();
    const location = useLocation();
    const url = location.pathname.split("/");
    const activeKey = [url[url.length - 1]];
    return (
        <Menu
            mode="inline"
            defaultSelectedKeys={activeKey}
            onClick={(keys) => {
                const key = keys.key.toString();
                history.push(`${TUTEE_SETTING_URL}/${key}`);
            }}
            style={{width: "100%", marginRight: "20px"}}
        >
            <Menu.Item key="personal-details">Personal Details</Menu.Item>
        </Menu>
    );
};

export default TuteeSettingsMenu;
