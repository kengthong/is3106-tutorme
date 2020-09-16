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
import LandingPage from "../pages/Common/LandingPage/LandingPage";
import NotFoundPage from "../pages/Common/NotFoundPage/NotFoundPage";

// services

const App = () => {
  return (
    <ErrorBoundary>
      <BrowserRouter>
        <Switch>
          <Route path="/login" component={Login} />
          <ProtectedRoute path="/login" component={Login} />
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
      <Redirect to={{ pathname: "/login" }} />
    );
  return <Route {...rest} render={routeComponent} />;
};

export default App;
