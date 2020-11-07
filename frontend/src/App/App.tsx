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
import TuteeListingPage from "../pages/Tutee/TuteeListingPage";
import TutorListingPage from "../pages/Tutor/TutorListingPage";
import CreateJobListing from "../pages/Tutor/CreateListingPage";
import Chat from "../pages/Common/Chat/Chat"
import FAQ from "../pages/Common/FAQs"
import AboutUs from "../pages/Common/AboutUs"
import Feedback from "../pages/Common/Feedback"
import ContactUs from "../pages/Common/ContactUs"
// services

const App = () => {
  return (
    <ErrorBoundary>
      <BrowserRouter>
        <Switch>
          <Route path="/tutee/listings" component={TuteeListingPage} />
          <Route path="/tutor/listings" component={TutorListingPage} />
          <Route path="/login" component={Login} />
          <Route path="/" exact component={LandingPage} isAuthenticated={true} />
          <Route path='/search:params?' component={SearchPage} />
          <Route path="/tutor/createListing" component={CreateJobListing} />
          <Route path="/FAQ" component={FAQ} />
          <Route path="/AboutUs" component={AboutUs} />
          <Route path="/ContactUs" component={ContactUs} />
          <Route path="/Feedback" component={Feedback} />
          <ProtectedRoute path="/chat" component={Chat} isAuthenticated={true} />
          <ProtectedRoute path="/login" component={Login} />
          <ProtectedRoute path="/tutor/settings/profile" allowedUser='tutor' exact component={TutorProfilePage} isAuthenticated={true} />
          <ProtectedRoute path="/tutor/settings/personal-details" allowedUser='tutor' exact component={TutorDetailsPage} isAuthenticated={true} />
          {/*For viewing tutor profile directly*/}
          {/*<ProtectedRoute path="/tutor-profile" allowedUser='tutor' exact component={TutorProfilePage} isAuthenticated={true} />*/}

          <Route path="/registration" component={Registration} />
          <Route path="/tutee-profile" component={tuteeProfile} />
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
    isAuthenticated ?
      isAppropriateUser ?
        (React.createElement(component, props))
        : (<Redirect to={{ pathname: "/" }} />)
      : (<Redirect to={{ pathname: "/login" }} />);
  return <Route {...rest} render={routeComponent} />;
};

export default App;
