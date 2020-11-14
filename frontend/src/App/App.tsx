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
import TuteeProfilePage from "../pages/Tutee/Settings/TuteeProfile"
import TutorProfilePage from "../pages/Tutor/Settings/Profile";
import TutorDetailsPage from "../pages/Tutor/Settings/PersonalDetails";
import SearchPage from "../pages/Search/SearchPage";
import ListingPage from "../pages/Tutee/ListingPage";
import CreateJobListing from "../pages/Tutor/CreateListingPage";
import Chat from "../pages/Common/Chat/Chat"
import FAQ from "../pages/Common/FAQs"
import AboutUs from "../pages/Common/AboutUs"
import Feedback from "../pages/Common/Feedback"
import ContactUs from "../pages/Common/ContactUs"
import { StaffRoutes } from "../Staff";
// services
import { useSelector } from "react-redux";
import { UserState } from "../reducer/user-reducer";
import { IRootState } from "../store";
import DashboardPage from "../pages/Common/Dashboard/Dashboard";

const App = () => {
  return (
    <ErrorBoundary>
      <BrowserRouter>
        <Switch>

          <Route path="/job" component={ListingPage} />
          <Route path="/login" component={Login} />
          <Route path="/" exact component={LandingPage} />
          <Route path='/search:params?' component={SearchPage} />
          <Route path="/faq" component={FAQ} />
          <Route path="/about-us" component={AboutUs} />
          <Route path="/contact-us" component={ContactUs} />
          <Route path="/feedback" component={Feedback} />
          <Route path="/registration" component={Registration} />
          <ProtectedRoute path="/chat" component={Chat} />
          <ProtectedRoute path="/tutor/settings/personal-details" allowedUser='tutor' exact component={TutorDetailsPage} />
          <ProtectedRoute path="/tutor/create-new-listing" allowedUser='tutor' exact component={CreateJobListing} />

          {/*For viewing tutor profile directly*/}
          <ProtectedRoute path="/tutor-profile" exact component={TutorProfilePage} />
          <ProtectedRoute path="/tutee/settings/personal-details" allowedUser='tutee' component={TuteeProfilePage} />
          <ProtectedRoute path="/dashboard" component={DashboardPage} />

          {/*<StaffRoutes/>*/}
          <Route path="/staff" component={StaffRoutes} />
          <Route component={NotFoundPage} />
          {/*<Redirect to="/error" exact={true} />*/}
        </Switch>
      </BrowserRouter>
    </ErrorBoundary>
  );
};

export const ProtectedRoute = ({ component, allowedUser, ...rest }: any) => {
  const userState = useSelector<IRootState, UserState>((state) => state.userReducer);
  const userType = userState.currentUser?.personEnum.toLowerCase();
  const isAuthenticated = userState.isAuthenticated;
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
