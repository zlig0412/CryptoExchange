const CASDOOR_ENDPOINT = "http://localhost:8000";
const CALLBACK_URL = "http://localhost:3000/logincallback";
const CLIENT_ID = "0c722340868bbc334971";

export function getCasdoorAuthUrl () {
    const state = Math.random().toString(32).substring(2, 15);
    const url = `${CASDOOR_ENDPOINT}/login/oauth/authorize?client_id=${CLIENT_ID}&response_type=code&redirect_uri=${encodeURIComponent(CALLBACK_URL)}&scope=openid profile email&state=${state}`;

    return url;
}