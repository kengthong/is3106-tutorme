import React, {useEffect, useState} from 'react';
import DashboardMenu from "./DashboardMenu";
import { useLocation, useHistory } from 'react-router-dom';
import {BodyContainer} from "../Layout/BodyContainer";
import {OfferService} from "../../services/Offer";
import {useSelector} from "react-redux";
import {IRootState} from "../../store";
import {UserState} from "../../reducer/user-reducer";
import qs from "qs";
import OfferListComponent from "./OfferList";
import {message} from "antd";

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

    const userState = useSelector<IRootState, UserState>((state) => state.userReducer);
    const {currentUser} = userState;

    const getAllOffers = async(personId: number) => {
        const data = await OfferService.getOffers(personId);
        setAllOffers(data);
        const params: { [key: string]: any } = qs.parse(location.search, { ignoreQueryPrefix: true });
        if(params.type ==="offers" && data.length >0) {
            filterOffers(params.status, data);
        }
    }

    useEffect(() => {
        if(currentUser) {
            getAllOffers(currentUser.personId);
        }
    },[])

    useEffect(() => {
        if(location.search === "") {
            history.push("/dashboard?type=offers&status=all");
        }
        const params: { [key: string]: any } = qs.parse(location.search, { ignoreQueryPrefix: true });
        setParams(params);
        if(params.type ==="offers") {
            filterOffers(params.status);
        }
    }, [location])

    const filterOffers = (status: string, data?: null) => {
        const _data = data? data: allOffers;
        if(status === "all") {
            setOffersData(_data);
            return;
        }
        const filteredOfferData = allOffers.length >0? allOffers.filter( d => d.offerStatus === status.toUpperCase()) : [];
        setOffersData(filteredOfferData);
    }

    const withdrawOffer = async (offerId: number) => {
        const success = await OfferService.withdrawOffer(offerId);
        if(userState.currentUser && success) {
            message.success("Successfully withdraw offer");
            getAllOffers(userState.currentUser.personId);
        } else {
            message.error("Unable to withdraw offer");
        }
    }

    const rejectOffer = async (offerId: number) => {
        const success = await OfferService.rejectOffer(offerId);
        if(userState.currentUser && success) {
            message.success("Successfully rejected offer");
            getAllOffers(userState.currentUser.personId);
        } else {
            message.error("Unable to reject offer");
        }
    }

    const acceptOffer = async (offerId: number) => {
        const success = await OfferService.acceptOffer(offerId);
        if(userState.currentUser && success) {
            message.success("Successfully rejected offer");
            getAllOffers(userState.currentUser.personId);
        } else {
            message.error("Unable to reject offer");
        }
    }

    return (
        <div className="w-100 flex-row" style={{height: '100vh'}}>
            <DashboardMenu params={params}/>
            <div className="w-100 flex-row justify-center" style={{minHeight: "calc(100vh -90px)", backgroundColor: "rgb(237 247 255)",}}>
                <div >
                    {type ==="offers"?
                        <OfferListComponent data={offersData} userState={userState}
                                            withdrawOffer={withdrawOffer} rejectOffer={rejectOffer} acceptOffer={acceptOffer}/>
                        :
                        null
                    }
                </div>
            </div>
        </div>
    )
}

export default Dashboard;