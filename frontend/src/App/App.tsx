import React from "react";
import { BrowserRouter, Redirect, Route, Switch } from "react-router-dom";

// CSS
import "./App.css";

import "./css/bootstrap.css";
import "./css/bootstrap.min.css";
import "./css/font-awesome.min.css";
import "./css/style.css";
import "./css/responsive.css";
// components
import ErrorBoundary from "../components/ErrorBoundary/ErrorBoundary";
import Login from "../pages/Common/Login/Login";
import Registration from "../pages/Common/Login/Registration";
import LandingPage from "../pages/Common/LandingPage/LandingPage";
import NotFoundPage from "../pages/Common/NotFoundPage/NotFoundPage";
import tuteeProfile from "../pages/Common/Tutee/TuteeProfile";

// services

const App = () => {
  return (
    <ErrorBoundary>
      <BrowserRouter>
        <Switch>
          <Route path="/login" component={Login} />
          <Route path="/registration" component={Registration} />
          <Route path="/tuteeProfile" component={tuteeProfile} />
          <ProtectedRoute
            path="/"
            exact
            component={LandingPage}
            isAuthenticated={true}
          />
          {/*<ProtectedRoute path="/settings" component={Settings}/>*/}
          {/*<ProtectedRoute component={Dashboard}/>*/}
          <Route path="/error" exact={true} component={NotFoundPage} />
          <Redirect to="/error" exact={true} />
        </Switch>
      </BrowserRouter>
    </ErrorBoundary>
  );
};

const ProtectedRoute = ({ component, isAuthenticated, ...rest }: any) => {
  const routeComponent = (props: any) =>
    isAuthenticated ? (
      React.createElement(component, props)
    ) : (
      <Redirect to={{ pathname: "/Login" }} />
    );
  return <Route {...rest} render={routeComponent} />;
};

export default App;
