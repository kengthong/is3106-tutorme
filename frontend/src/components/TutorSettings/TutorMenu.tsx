import React from "react";
import { Menu } from "antd";
import { useHistory, useLocation } from "react-router-dom";
import { TUTOR_SETTING_URL } from "../../config/constants";

const TutorSettingsMenu = () => {
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
        history.push(`${TUTOR_SETTING_URL}/${key}`);
      }}
      style={{ width: "100%", marginRight: "20px" }}
    >
      <Menu.Item key="profile">Profile</Menu.Item>
      <Menu.Item key="personal-details">Personal Details</Menu.Item>
    </Menu>
  );
};

export default TutorSettingsMenu;
