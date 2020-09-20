import { getLorumIpsum } from "../actions/home";

export class TutorService {
    getLorumIpsumAPI() {
        return getLorumIpsum('https://baconipsum.com/api/?type=meat-and-filler');
    }
};