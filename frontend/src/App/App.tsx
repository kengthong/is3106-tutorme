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
import TutorProfilePage from "../pages/Tutor/Settings/Profile";
import TutorDetailsPage from "../pages/Tutor/Settings/PersonalDetails";
import SearchPage from "../pages/Search/SearchPage";

// services

const App = () => {
  return (
    <ErrorBoundary>
      <BrowserRouter>
        <Switch>
          <Route path="/login" component={Login} />
          <Route path="/" exact component={LandingPage} isAuthenticated={true}/>
          <Route path='/search:params?' component={SearchPage} />
          <ProtectedRoute path="/login" component={Login} />
          <ProtectedRoute path="/tutor/settings/profile" allowedUser='tutor' exact component={TutorProfilePage} isAuthenticated={true}/>
          <ProtectedRoute path="/tutor/settings/personal-details" allowedUser='tutor' exact component={TutorDetailsPage} isAuthenticated={true}/>
          <ProtectedRoute path="/tutor-profile" allowedUser='tutee' exact component={TutorProfilePage} isAuthenticated={true}/>

          <Route path="/registration" component={Registration} />
          <Route path="/tuteeProfile" component={tuteeProfile} />
          {/*<ProtectedRoute path="/settings" component={Settings}/>*/}
          {/*<ProtectedRoute component={Dashboard}/>*/}
          <Route path="/error" exact={true} component={NotFoundPage} />
          {/*<Redirect to="/error" exact={true} />*/}
        </Switch>
      </BrowserRouter>
    </ErrorBoundary>
  );
};

const ProtectedRoute = ({ component, isAuthenticated, allowedUser, ...rest }: any) => {
  const userType = 'tutor';
  // If user is null, it means the page is a common page. Else, check if user is a tutee or a tutor, and if this user
  // is authorized to view the page
  const isAppropriateUser = (!allowedUser) || (allowedUser && userType === allowedUser);

  const routeComponent = (props: any) =>
    isAuthenticated?
        isAppropriateUser ?
            ( React.createElement(component, props))
        : ( <Redirect to={{ pathname: "/" }} /> )
    : (<Redirect to={{ pathname: "/login" }} /> );
  return <Route {...rest} render={routeComponent} />;
};

export default App;
