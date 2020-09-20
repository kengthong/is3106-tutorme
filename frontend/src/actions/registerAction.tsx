import { register } from "../serviceWorker";

import React, { useState } from "react";
import { Modal } from "antd";

//Algo: POST to server, if successful registration, sign in and return success message
//Skeleton awaiting plugin from API
const registerAction = () => {
  //Final return message
  Modal.success({
    content: "Account Registered",
  });
};

export default registerAction;
