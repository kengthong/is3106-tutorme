import React from "react";
import { Route, Switch } from "react-router-dom";
import StaffLogin from "./Pages/Login";
import { ProtectedRoute } from "../App/App";
import StaffHome from "./Pages/Home";

export const StaffRoutes = () => {
    return (
        <>
            <Route path="/staff/login" exact component={StaffLogin} />
            <ProtectedRoute path="/staff/home" allowedUser='staff' component={StaffHome} isAuthenticated={true} />
            {/*<Route path="*" component={NotFoundPage} />*/}

        </>
    )
};