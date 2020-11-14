import React, { useEffect, useState } from "react";
import DashboardMenu from "./DashboardMenu";
import { useLocation, useHistory } from "react-router-dom";
import { BodyContainer } from "../Layout/BodyContainer";
import { OfferService } from "../../services/Offer";
import { useSelector } from "react-redux";
import { IRootState } from "../../store";
import { UserState } from "../../reducer/user-reducer";
import qs from "qs";
import OfferListComponent from "./OfferList";
import { message } from "antd";
import { JobListingService } from "../../services/JobListing";
import JobListingsComponent from "./JobListings";

const Dashboard = () => {
	const location = useLocation();
	const history = useHistory();
	// App state
	const [type, setType] = useState("offers");
	const [params, setParams] = useState();

	// Offers
	const [allOffers, setAllOffers] = useState<offerType[]>([]);
	const [offersData, setOffersData] = useState<offerType[]>([]);

	// JobListing
	const [jobListingsData, setJobListingsData] = useState<jobListingType[]>([]);

	const userState = useSelector<IRootState, UserState>(
		(state) => state.userReducer
	);
	const { currentUser } = userState;

	const getAllOffers = async () => {
		const data = await OfferService.getOffers();
		setAllOffers(data);
		const params: { [key: string]: any } = qs.parse(location.search, {
			ignoreQueryPrefix: true,
		});
		setType(params.type);
		console.log("data =", data);
		console.log("poarams type =", params);
		console.log("location  =", location);
		if (params.type === "offers" && data.length > 0) {
			filterOffers(params.status, data);
		}
	};

	const getAllJobListings = async () => {
		const data = await JobListingService.getMyJobListings();
		setJobListingsData(data);
		const params: { [key: string]: any } = qs.parse(location.search, {
			ignoreQueryPrefix: true,
		});
		setType(params.type);
	};

	useEffect(() => {
		if (location.search === "") {
			history.push("/dashboard?type=offers&status=all");
		}
		console.log("data =", allOffers);
		if (!currentUser) {
			return;
		}

		if (allOffers.length == 0) {
			getAllOffers();
		}

		if (currentUser.personEnum == "TUTOR") {
			getAllJobListings();
		}

		const params: { [key: string]: any } = qs.parse(location.search, {
			ignoreQueryPrefix: true,
		});
		console.log("params =", params);
		setParams(params);
		setType(params.type);
		if (params.type === "offers") {
			filterOffers(params.status);
		}
	}, [location]);

	const filterOffers = (status: string, data?: null) => {
		const _data = data ? data : allOffers;
		if (status === "all") {
			setOffersData(_data);
			return;
		}
		const filteredOfferData =
			_data.length > 0
				? _data.filter((d) => d.offerStatus === status.toUpperCase())
				: [];
		setOffersData(filteredOfferData);
	};

	const withdrawOffer = async (offerId: number) => {
		const success = await OfferService.withdrawOffer(offerId);
		if (userState.currentUser && success) {
			message.success("Successfully withdraw offer");
			getAllOffers();
		} else {
			message.error("Unable to withdraw offer");
		}
	};

	const rejectOffer = async (offerId: number) => {
		const success = await OfferService.rejectOffer(offerId);
		if (userState.currentUser && success) {
			message.success("Successfully rejected offer");
			getAllOffers();
		} else {
			message.error("Unable to reject offer");
		}
	};

	const acceptOffer = async (offerId: number) => {
		const success = await OfferService.acceptOffer(offerId);
		if (userState.currentUser && success) {
			message.success("Successfully accepted offer");
			getAllOffers();
		} else {
			message.error("Unable to accept offer");
		}
	};

	const viewJobListing = (id?: number) => {
		if (id) history.push("/job?id=" + id);
	};

	const activateJobListing = async (jobListingId: number) => {
		const success = await JobListingService.activateJobListing(jobListingId);
		if (userState.currentUser && success) {
			message.success("Successfully activated job listing");
			getAllJobListings();
		} else {
			message.error("Unable to activate job listing");
		}
	};

	const deactivateJobListing = async (jobListingId: number) => {
		const success = await JobListingService.deactivateJobListing(jobListingId);
		if (userState.currentUser && success) {
			message.success("Successfully deactivated job listing");
			getAllJobListings();
		} else {
			message.error("Unable to deactivate job listing");
		}
	};

	return (
		<div className="w-100 flex-row" style={{ height: "100vh" }}>
			<DashboardMenu params={params} currentUser={userState.currentUser} />
			<div
				className="w-100 flex-col "
				style={{
					minHeight: "calc(100vh -90px)",
					backgroundColor: "rgb(237 247 255)",
				}}
			>
				<div
					className="fs-24 w-100"
					style={{
						fontWeight: 300,
						backgroundColor: "#fff",
						padding: "16px 32px",
						borderTop: "1px solid #e8e8e8",
					}}
				>
					{type === "offers" ? (
						<span>My Offers</span>
					) : (
						<span>My Job Listings</span>
					)}
				</div>
				<div className="w-100 flex-col align-center margin-top-btm-12">
					{type === "offers" ? (
						<OfferListComponent
							data={offersData}
							userState={userState}
							withdrawOffer={withdrawOffer}
							rejectOffer={rejectOffer}
							acceptOffer={acceptOffer}
						/>
					) : (
						<JobListingsComponent
							data={jobListingsData}
							viewJobListing={viewJobListing}
							activateJobListing={activateJobListing}
							deactivateJobListing={deactivateJobListing}
						/>
					)}
				</div>
			</div>
		</div>
	);
};

export default Dashboard;
